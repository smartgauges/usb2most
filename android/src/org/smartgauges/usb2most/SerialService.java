package org.smartgauges.usb2most;

import android.app.PendingIntent;
import android.app.Notification;
//import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.support.v4.app.NotificationCompat;
import android.view.KeyEvent;
import android.net.Uri;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Arrays;
import java.util.Random;

import android.widget.Toast;
import android.content.pm.PackageManager;
import android.content.pm.ApplicationInfo;

import android.media.AudioManager;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import java.nio.ByteBuffer;

import java.util.Timer;
import java.util.TimerTask;

public class SerialService extends Service implements SerialListener
{
	private static final String TAG = Constants.APPLICATION_ID + ":Srv";

	public static final int MSG_ATTACH = 1;
	public static final int MSG_DETACH = 2;
	public static final int MSG_CONNECTED = 3;
	public static final int MSG_DISCONNECTED = 4;
	public static final int MSG_DATA = 5;

	private Messenger messenger = new Messenger(new IncomingHandler());
	private Messenger activityMessenger = null;

	private BroadcastReceiver broadcastReceiver;
	private SerialSocket socket;
	private Timer timer;

	private boolean connected = false;
	private boolean flag_send_request = false;

	private SerialMsg SerMsg;
	private String strFreq = "";
	private Msg.msg_src_t.e_src_t src = Msg.msg_src_t.e_src_t.e_src_none;
	private int station = 0;

	private WindowCar m_car;
	private WindowMedia m_media;

	private int door_cnt = 0;
	private byte radar_cnt = 0;

	private Timer emulate_timer = null;

	/**
	 * Lifecylce
	 */
	public SerialService()
	{
		Log.d(TAG, "SerialService()");
		SerMsg = new SerialMsg();
	}

	@Override
	public void onCreate()
	{
		Log.d(TAG, "onCreate()");
		super.onCreate();

		m_car = new WindowCar(getApplicationContext());
		m_media = new WindowMedia(getApplicationContext());

		/*emulate_timer = new Timer();
		emulate_timer.schedule(new TimerTask() {

			@Override
			public void run() {

				int volume = (new Random()).nextInt(35);
				byte[] data0 = { 11, MSG_TYPE_VOL, (byte)volume, 0, 0, 0, 0, 0, 0, 0, 0 };
				process_msg(data0);

				int src = (new Random()).nextInt(5);
				byte[] data1 = { 4, MSG_TYPE_SRC, (byte)src, (byte)(volume % 9) };
				process_msg(data1);
			}

		}, 5000, 5000);*/

		createNotification();

		broadcastReceiver = new BroadcastReceiver()
		{
			@Override
			public void onReceive(Context context, Intent intent)
			{
				if (intent.getAction().equals(Constants.ACTION_GRANT_USB)) {

					Boolean granted = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false);
					if (granted) {

						Log.d(TAG, "GRANT_USB: permission granted");
					}
					else {
						Log.d(TAG, "GRANT_USB: permission denied");
					}
				}
				else if (intent.getAction().equals(Constants.ACTION_USB_DEVICE_ATTACHED)) {

					Log.d(TAG, "USB_DEVICE_ATTACHED");
				}
			}
		};

		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.ACTION_GRANT_USB);
		filter.addAction(Constants.ACTION_USB_DEVICE_ATTACHED);
		registerReceiver(broadcastReceiver, filter);

		Widget.update_info(this, "startService");

		connect();
		if (!connected)
			startTimerConnect();
	}

	private void startTimerConnect()
	{
		stopTimerConnect();

		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				connect();
			}

		}, 0, 1000);
	}

	private void stopTimerConnect()
	{
		if (timer != null) {

			timer.cancel();
			timer = null;
		}
	}

	@Override
	public void onDestroy()
	{
		Log.d(TAG, "onDestroy()");
		unregisterReceiver(broadcastReceiver);

		disconnect();
		stopTimerConnect();

		stopSelf();

		Widget.update_info(this, "stopService");

		super.onDestroy();
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent)
	{
		return messenger.getBinder();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Log.d(TAG, "onStartCommand");
		return START_STICKY;
	}

	@Override
	public void onTaskRemoved(Intent rootIntent)
	{
		Log.d(TAG, "onTaskRemoved");

		/*Intent restartServiceTask = new Intent(getApplicationContext(), this.getClass());
		restartServiceTask.setPackage(getPackageName());    
		PendingIntent restartPendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceTask, PendingIntent.FLAG_ONE_SHOT);
		AlarmManager myAlarmService = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		myAlarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 1000, restartPendingIntent);
		*/

		super.onTaskRemoved(rootIntent);
	}

	class IncomingHandler extends Handler
	{
		@Override
		public void handleMessage(Message msg)
		{
			Message message;
			Bundle bundle = new Bundle();

			switch (msg.what) {
				case MSG_ATTACH:
					activityMessenger = msg.replyTo;
					send_status();
					Log.d(TAG, "attach " + activityMessenger);
					break;

				case MSG_DETACH:
					activityMessenger = null;
					Log.d(TAG, "detach " + activityMessenger);
					break;

				case MSG_DATA:
					//Log.d(TAG, "cmd data");
					byte[] data = msg.getData().getByteArray("data");
					sendData(data);
					break;
				default:
					super.handleMessage(msg);
			}

		}
	}

	private void send_status()
	{
		if (activityMessenger != null) {

			int status = MSG_CONNECTED;
			if (!connected)
				status = MSG_DISCONNECTED;

			Message message = Message.obtain(null, status);
			try {
				activityMessenger.send(message);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	synchronized private void connect()
	{
		Log.d(TAG, "connect()" + " connected:" + connected);

		if (connected) {

			stopTimerConnect();
			return;
		}

		Widget.update_info(this, "try connect");

		UsbManager usbManager = (UsbManager)getSystemService(Context.USB_SERVICE);
		UsbSerialProber usbDefaultProber = UsbSerialProber.getDefaultProber();

		Log.d(TAG, "devices:" + usbManager.getDeviceList().values());

		for (UsbDevice dev : usbManager.getDeviceList().values()) {

			String devName = dev.getDeviceName();

			if (!((0x1219 == dev.getVendorId() && 0xabad == dev.getProductId()) || (0x1a86 == dev.getVendorId() && 0x5523 == dev.getProductId())))
				continue;

			boolean perm = usbManager.hasPermission(dev);

			Log.d(TAG, "dev: " + devName + " " + dev.getProductName() + " " + Integer.toString(dev.getProductId()) + " " + Integer.toString(dev.getDeviceId()) + " perm:" + perm);

			if (!perm) {

				if (!flag_send_request) {

					Log.d(TAG, "Requesting permission to use device " + devName);
					Intent req = new Intent(Constants.ACTION_GRANT_USB);
					PendingIntent usbPermissionIntent = PendingIntent.getBroadcast(this, 0, req, 0);
					usbManager.requestPermission(dev, usbPermissionIntent);
					flag_send_request = true;
				}

				continue;
			}

			UsbSerialDriver driver = usbDefaultProber.probeDevice(dev);
			if (driver != null) {

				UsbSerialPort port = driver.getPorts().get(0);
				Log.d(TAG, "get port");
				UsbDeviceConnection usbConnection = usbManager.openDevice(driver.getDevice());
				if (usbConnection == null) {

					if (!usbManager.hasPermission(driver.getDevice()))
						Log.d(TAG, "connection failed: permission denied");
					else
						Log.d(TAG, "connection failed: open failed");
					continue;
				}

				socket = new SerialSocket();
				try {
					socket.connect(this, usbConnection, port);
				} catch (Exception e) {
					onSerialConnectError(e);
					continue;
				}

				connected = true;
				Widget.update_info(this, "connected");

				send_status();
				stopTimerConnect();
				createNotification();
				break;
			}
		}
	}

	private void disconnect()
	{
		Log.d(TAG, "diconnect()");
		connected = false;
		if (socket != null)
			socket.disconnect();
		socket = null;

		Widget.update_info(this, "disconnect");

		send_status();

		createNotification();

		startTimerConnect();
	}

	private void sendData(byte[] data)
	{
		//Log.d(TAG, "sendData()");
		try {
			if (socket != null)
				socket.sendData(data);
		} catch (Exception e) {
			onSerialIoError(e);
		}
	}

	private void createNotification()
	{
		NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		/*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
		  NotificationChannel nc = new NotificationChannel(Constants.NOTIFICATION_CHANNEL, "Background service", NotificationManager.IMPORTANCE_LOW);
		//nc.setShowBadge(false);

		nc.setDescription("Channel description");
		nc.enableLights(true);
		//nc.setLightColor(Color.RED);
		nc.setVibrationPattern(new long[]{0, 1000, 500, 1000});
		nc.enableVibration(true);

		nm.createNotificationChannel(nc);
		}*/

		Intent restartIntent = new Intent();
		restartIntent.setClassName(this, Constants.INTENT_CLASS_MAINACTIVITY);
		restartIntent.setAction(Intent.ACTION_MAIN);
		restartIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		PendingIntent startPendingIntent = PendingIntent.getActivity(this, 1, restartIntent,  PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		/*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
		  builder = new NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL);
		}
		else {
		builder = new NotificationCompat.Builder(this);
		}*/
		builder.setSmallIcon(R.drawable.ic_notification);
		builder.setColor(getResources().getColor(R.color.colorPrimary));
		builder.setContentTitle(getResources().getString(R.string.app_name));
		builder.setContentText(socket != null ? "Connected to " + socket.getName() : "Wait device");
		builder.setContentIntent(startPendingIntent);
		builder.setOngoing(true);
		// @drawable/ic_notification created with Android Studio -> New -> Image Asset using @color/colorPrimaryDark as background color
		// Android < API 21 does not support vectorDrawables in notifications, so both drawables used here, are created as .png instead of .xml
		Notification notification = builder.build();

		//NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		//notificationManager.notify(1, notification);
		startForeground(Constants.NOTIFY_MANAGER_START_FOREGROUND_SERVICE, notification);
	}

	/**
	 * SerialListener
	 */
	public void onSerialConnectError(Exception e)
	{
		Log.d(TAG, "onSerialConnectError()" + e);
		disconnect();
	}

	public void onSerialIoError(Exception e)
	{
		Log.d(TAG, "onSerialIoError()" + connected);
		disconnect();
	}

	public void onSerialNewData(byte[] data)
	{
		//Log.d(TAG, "onSerialNewData()");
		for (int i = 0; i < data.length; i++) {

			byte ch = data[i];
			byte[] m = SerMsg.get_msg(ch);

			if (m != null && m.length > 0) {

				process_msg(m);
			}
		}
	}

	private void click_btn_setup()
	{
		Log.d(TAG, "click_btn_setup");

		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	private void click_btn_home()
	{
		Log.d(TAG, "click_btn_home");
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	static private class GPS
	{
		private static boolean pGps, pNetwork;
		private static LocationManager locManager;
		private static String provider;
		private static double longitude;
		private static double latitude;

		private static void updateAvailability()
		{
			try {
				pNetwork = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
				provider = LocationManager.NETWORK_PROVIDER;
			} catch (Exception ex) {
				Log.w(TAG,"Ex getting NETWORK provider");
			}
			try {
				pGps = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
				provider = LocationManager.GPS_PROVIDER;
			} catch (Exception ex) {
				Log.w(TAG,"Ex getting GPS provider");
			}
		}

		public static Location getLastLocation(Context ctx)
		{
			Location loc = null;
			if(ctx != null){
				if(locManager == null){
					locManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
				}
				updateAvailability();
				if(provider!=null){
					loc = locManager.getLastKnownLocation(provider);
				}
			}

			return loc;
		}       
	}

	private static boolean isAppInstalled(Context context, String packageName)
	{
		PackageManager pm = context.getPackageManager();
		try {
			pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			return true;
		} catch (PackageManager.NameNotFoundException ignored) {
		}
		return false;
	}

	private static boolean isAppEnabled(Context context, String packageName)
	{
		boolean appStatus = false;
		try {
			PackageManager pm = context.getPackageManager();
			ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
			if (ai != null) {
				appStatus = ai.enabled;
			}
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return appStatus;
	}

	private void openApp(final Context context, final String btn)
	{
		String packageName = Settings.getAppByBtn(getApplicationContext(), btn);

		Log.d(TAG, "click openApp " + btn + " " + packageName);

		if (isAppInstalled(context, packageName)) {

			if (isAppEnabled(context, packageName))
				context.startActivity(context.getPackageManager().getLaunchIntentForPackage(packageName));
			else {
				
				new Handler(Looper.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {

						Toast.makeText(context, btn + " app is not enabled.", Toast.LENGTH_SHORT).show();
					}
				});

			}
		}
		else {

			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(context, btn + " app is not installed.", Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

	private void click_btn_nav()
	{
		Log.d(TAG, "click_btn_nav");

		openApp(getApplicationContext(), "nav");
	}

	private void click_btn_music()
	{
		Log.d(TAG, "click_btn_music");

		openApp(getApplicationContext(), "music");
	}

	private void click_btn_media()
	{
		Log.d(TAG, "click_btn_media");

		openApp(getApplicationContext(), "media");
	}

	private void click_btn_next()
	{
		Log.d(TAG, "click_btn_next");

		AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		audio.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_NEXT));
		audio.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_NEXT));
	}

	private void click_btn_prev()
	{
		Log.d(TAG, "click_btn_prev");

		AudioManager audio = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		audio.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PREVIOUS));
		audio.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PREVIOUS));
	}

	private void click_btn_phone()
	{
		Log.d(TAG, "click_btn_phone");

		openApp(getApplicationContext(), "phone");
	}

	private void click_btn_tune_up()
	{
		Log.d(TAG, "click_btn_tune_up");
	}

	private void click_btn_tune_down()
	{
		Log.d(TAG, "click_btn_tune_down");
	}

	private void update_src(Msg.msg_src_t.e_src_t src, int station)
	{
		String strSrc = "";

		switch (src) {

			case e_src_aux:
				strSrc = "AUX";
				Widget.update_info(this, "");
				break;

			case e_src_cd:
				strSrc = "CD";
				Widget.update_info(this, "");
				break;

			case e_src_fm1:
				strSrc = String.format("FM1.%d %s", station, strFreq);
				break;

			case e_src_fm2:
				strSrc = String.format("FM2.%d %s", station, strFreq);
				break;

			case e_src_am:
				strSrc = String.format("AM.%d %s", station, strFreq);
				Widget.update_info(this, "");
				break;

			case e_src_uac:
				strSrc = "UAC";
				Widget.update_info(this, "");
				break;

			case e_src_toslink:
				strSrc = "TOSLINK";
				Widget.update_info(this, "");
				break;
		}

		Widget.update_src(this, strSrc);
		process_src(strSrc);
	}

	private void process_door(final int state)
	{
		if (m_car != null) {

			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					m_car.ctrlDoor(state);
				}
			});
		}
	}

	private void process_radar(final boolean radarshow, final byte[] front, final byte[] back)
	{
		if (m_car != null) {

			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					m_car.ctrlRadar(radarshow, front, back);
				}
			});
		}
	}

	private void process_volume(final int volume)
	{
		if (m_media != null) {

			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					m_media.ctrlVolume(volume);
				}
			});
		}

	}

	private void process_src(final String src)
	{
		if (m_media != null) {

			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					m_media.ctrlMedia(src);
				}
			});
		}

	}

	private float scale(float value, float in_min, float in_max, float out_min, float out_max)
	{
		return (((value - in_min) * (out_max - out_min)) / (in_max - in_min)) + out_min;
	}

	private void process_msg(byte[] data)
	{
		if (data.length < 2)
			return;

		//Log.d(TAG, "onSerialNewData()");
		if (activityMessenger != null) {

			Message message = Message.obtain(null, SerialService.MSG_DATA);
			Bundle bundle = new Bundle();
			bundle.putByteArray("data", data);
			message.replyTo = messenger;
			message.setData(bundle);
			try {
				activityMessenger.send(message);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		Msg.msg_t msg = Msg.msg_t.convert(data);

		//Log.d(TAG, String.format("msg len:%d type:%s length:%d", msg.len, msg.type.name(), data.length));

		if (msg.type == Msg.e_msg_types.e_type_log) {

			Msg.msg_log_t m = Msg.msg_log_t.convert(msg.data);
			String str = new String(m.data);
			//Log.d(TAG, "lvl:" + m.lvl + " dbg:" + str);
		}
		else if (msg.type == Msg.e_msg_types.e_type_src && msg.len == Msg.msg_src_t.sizeof()) {

			Msg.msg_src_t m = Msg.msg_src_t.convert(msg.data);

			src = m.src;
			station = m.station;
			//Log.d(TAG, "src:" + src);
			update_src(src, station);
		}
		else if (msg.type == Msg.e_msg_types.e_type_volume && msg.len == Msg.msg_volume_t.sizeof()) {

			Msg.msg_volume_t m = Msg.msg_volume_t.convert(msg.data);
			//Log.d(TAG, "lvl:" + m.lvl);

			Widget.update_vol(this, String.format("%d", m.lvl));
			process_volume(m.lvl);
		}
		else if (msg.type == Msg.e_msg_types.e_type_tuner_rds && msg.len == Msg.msg_tuner_rds_t.sizeof()) {

			if ((src == Msg.msg_src_t.e_src_t.e_src_fm1) || (src == Msg.msg_src_t.e_src_t.e_src_fm2) || (src == Msg.msg_src_t.e_src_t.e_src_am)) {

				Msg.msg_tuner_rds_t m = Msg.msg_tuner_rds_t.convert(msg.data);

				String rds = "";
				if (m.name[0] != 0)
					rds = new String(m.name);

				int info = (int)m.info;
				Log.d(TAG, String.format("freq:%d info:%d text:%s", m.freq, info, rds));

				strFreq = String.format("%d.%d", m.freq/1000, (m.freq%1000)/100);
				update_src(src, station);
				Widget.update_info(this, rds);
			}
		}
		else if (msg.type == Msg.e_msg_types.e_type_car_state && msg.len == Msg.msg_car_state_t.sizeof()) {

			boolean en_doors = Settings.getShowDoors(getApplicationContext());
			//Log.d(TAG, String.format("en_doors:%b", en_doors));
			if (en_doors) {

				int door = 0;
	
				/*door_cnt++;
				if ((door_cnt % 20) < 10)
					door |= WindowCar.DOOR_FL | WindowCar.DOOR_BONNET;
				*/

				Msg.msg_car_state_t m = Msg.msg_car_state_t.convert(msg.data);

				boolean fl_door = Msg.msg_car_state_t.sym_get(m.symbols, Msg.e_sym_t.e_sym_fl_door);
				boolean fr_door = Msg.msg_car_state_t.sym_get(m.symbols, Msg.e_sym_t.e_sym_fr_door);
				boolean rl_door = Msg.msg_car_state_t.sym_get(m.symbols, Msg.e_sym_t.e_sym_rl_door);
				boolean rr_door = Msg.msg_car_state_t.sym_get(m.symbols, Msg.e_sym_t.e_sym_rr_door);
				boolean tailgate = Msg.msg_car_state_t.sym_get(m.symbols, Msg.e_sym_t.e_sym_tailgate);
				boolean bonnet = Msg.msg_car_state_t.sym_get(m.symbols, Msg.e_sym_t.e_sym_bonnet);

				if (fl_door)
					door |= WindowCar.DOOR_FL;
				if (fr_door)
					door |= WindowCar.DOOR_FR;
				if (rl_door)
					door |= WindowCar.DOOR_RL;
				if (rr_door)
					door |= WindowCar.DOOR_RR;
				if (tailgate)
					door |= WindowCar.DOOR_TAILGATE;
				if (bonnet)
					door |= WindowCar.DOOR_BONNET;

				/*
				   if (belt_fl != 0)
				   door |= WindowCar.DOOR_BELT_FL;
				   if (belt_fr != 0)
				   door |= WindowCar.DOOR_BELT_FR;
				   */

				process_door(door);
			}

			boolean en_radar = Settings.getShowRadar(getApplicationContext());
			if (en_radar) {

				Msg.msg_car_state_t m = Msg.msg_car_state_t.convert(msg.data);

				//Log.d(TAG, "radar: " + m.radar[0] + " " + m.radar[1] + " " + m.radar[2] + " " + m.radar[3] + " " + m.radar[4] + " " + m.radar[5] + " " + m.radar[6] + " " + m.radar[7]);

				boolean park_is_on = ((0x70 == (m.radar[0] & 0xf0)) || (0x60 == (m.radar[0] & 0xf0))) ? true : false;

				byte[] front = { 0, 0, 0, 0, };
				byte[] back = { 0, 0, 0, 0, };
				if (park_is_on) {

					long f = ((long)m.radar[5] << 16)&0xff0000 | ((long)m.radar[6] << 8)&0xff00 | ((long)m.radar[7]) & 0xff;
					byte f0 = (byte)((f >> 15) & 0x1f);
					byte f1 = (byte)((f >> 10) & 0x1f);
					byte f2 = (byte)((f >> 5) & 0x1f);
					byte f3 = (byte)(f & 0x1f);

					front[0] = (byte)(WindowCar.MAX_RADAR - scale(f3, 0, 31, 0, WindowCar.MAX_RADAR));
					front[1] = (byte)(WindowCar.MAX_RADAR - scale(f2, 0, 31, 0, WindowCar.MAX_RADAR));
					front[2] = (byte)(WindowCar.MAX_RADAR - scale(f1, 0, 31, 0, WindowCar.MAX_RADAR));
					front[3] = (byte)(WindowCar.MAX_RADAR - scale(f0, 0, 31, 0, WindowCar.MAX_RADAR));

					long r = ((long)m.radar[2] << 16)&0xff000 | ((long)m.radar[3] << 8)&0xff00 | ((long)m.radar[4])&0xff;
					byte r0 = (byte)((r >> 15) & 0x1f);
					byte r1 = (byte)((r >> 10) & 0x1f);
					byte r2 = (byte)((r >> 5) & 0x1f);
					byte r3 = (byte)(r & 0x1f);

					back[0] = (byte)(WindowCar.MAX_RADAR - scale(r0, 0, 31, 0, WindowCar.MAX_RADAR));
					back[1] = (byte)(WindowCar.MAX_RADAR - scale(r1, 0, 31, 0, WindowCar.MAX_RADAR));
					back[2] = (byte)(WindowCar.MAX_RADAR - scale(r2, 0, 31, 0, WindowCar.MAX_RADAR));
					back[3] = (byte)(WindowCar.MAX_RADAR - scale(r3, 0, 31, 0, WindowCar.MAX_RADAR));
				}

				/*
				   if ((door_cnt % 20) < 10) {

				   if ((door_cnt % 20) == 0)
				   radar_cnt++;

				   showradar = true;

				   if (radar_cnt >= WindowCar.MAX_RADAR)
				   radar_cnt = 0;
				   }

				   front[0] = radar_cnt;
				   front[1] = radar_cnt;
				   front[2] = radar_cnt;
				   front[3] = radar_cnt;

				   back[0] = radar_cnt;
				   back[1] = radar_cnt;
				   back[2] = radar_cnt;
				   back[3] = radar_cnt;
				   */

				process_radar(park_is_on, front, back);
			}
		}
		else if (msg.type == Msg.e_msg_types.e_type_btn && msg.len == Msg.msg_btn_t.sizeof()) {

			Msg.msg_btn_t m = Msg.msg_btn_t.convert(msg.data);

			//Log.d(TAG, "bnt:" + m.btn);
			switch (m.btn) {

				case e_btn_home:
					click_btn_home();
					break;
				case e_btn_nav:
					click_btn_nav();
					break;
				case e_btn_phone:
					click_btn_phone();
					break;
				case e_btn_media:
					click_btn_media();
					break;
				case e_btn_tune_dec:
					click_btn_tune_down();
					break;
				case e_btn_tune_inc:
					click_btn_tune_up();
					break;
				case e_btn_mode:
					//mode;
					break;
				case e_btn_music:
					click_btn_music();
					break;
				case e_btn_prev:
					click_btn_prev();
					break;
				case e_btn_next:
					click_btn_next();
					break;
				case e_btn_setup:
					click_btn_setup();
					break;
			}
		}
	}
}

