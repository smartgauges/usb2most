package org.smartgauges.usb2most;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends Activity implements ServiceConnection
{
	private static final String TAG = Constants.APPLICATION_ID + ":Main";

	private final Messenger mActivityMessenger = new Messenger(new ResponseHandler());
	private static Messenger mServiceMessenger = null;
	private static boolean connected = false;
	private boolean isBound = false;
	private SerialMsg SerMsg;
	private TextView m_log;

	public MainActivity()
	{
		Log.d(TAG, "MainActivity()");
		SerMsg = new SerialMsg();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		m_log = (TextView)findViewById(R.id.log);
		m_log.setText("MainAcivity created\n");
		m_log.setMovementMethod(new ScrollingMovementMethod());
	}

	@Override
	public void onStart()
	{
		Log.d(TAG, "onStart() " + isBound);
		super.onStart();

		if (!isBound) {

			// start service here
			Intent intent = new Intent(this, SerialService.class);
			bindService(intent, this, 0);
			startService(intent);
		}
	}

	@Override
	public void onStop()
	{
		Log.d(TAG, "onStop()");

		super.onStop();

		if (isBound && (mServiceMessenger != null)) {

			Message message = Message.obtain(null, SerialService.MSG_DETACH);
			Bundle bundle = new Bundle();
			message.setData(bundle);
			try {
				mServiceMessenger.send(message);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		try
		{
			unbindService(this);
		}
		catch(Exception ignored) {}

		isBound = false;
		mServiceMessenger = null;
	}

	@Override
	public void onDestroy()
	{
		Log.d(TAG, "onDestroy()");

		System.exit(0);
		super.onDestroy();
	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder binder)
	{
		Log.d(TAG, "onServiceConnected()");

		mServiceMessenger = new Messenger(binder);
		isBound = true;

		Message message = Message.obtain(null, SerialService.MSG_ATTACH);
		Bundle bundle = new Bundle();
		message.setData(bundle);
		message.replyTo = mActivityMessenger;
		try {
			mServiceMessenger.send(message);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onServiceDisconnected(ComponentName name)
	{
		Log.d(TAG, "onServiceDisconnected()");
		isBound = false;
		mServiceMessenger = null;
		//nativeDevDisconnected();
	}

	private void process_msg(byte[] data)
	{
		if (data.length < 2)
			return;

		Msg.msg_t msg = Msg.msg_t.convert(data);

		if (msg.type == Msg.e_msg_types.e_type_log) {

			Msg.msg_log_t m = Msg.msg_log_t.convert(msg.data);
			String str = new String(m.data);
			Log.d(TAG, "lvl:" + m.lvl + " dbg:" + str);
			m_log.append(str + "\n");
		}
	}

	private class ResponseHandler extends Handler
	{
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
				case SerialService.MSG_DATA:
					byte[] data = msg.getData().getByteArray("data");
					//nativeNewData(data);
					process_msg(data);
					break;
				case SerialService.MSG_DISCONNECTED:
					connected = false;
					//nativeDevDisconnected();
					break;
				case SerialService.MSG_CONNECTED:
					connected = true;
					break;
				default:
					super.handleMessage(msg);
			}
		}
	}

	/*
	 * Serial + UI
	 */
	public static void sendData(byte[] data)
	{
		//Log.d(TAG, "sendData()");
		if (mServiceMessenger != null) {

			Message message = Message.obtain(null, SerialService.MSG_DATA);
			Bundle bundle = new Bundle();
			bundle.putByteArray("data", data);
			message.setData(bundle);
			try {
				mServiceMessenger.send(message);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean openDev()
	{
		Log.d(TAG, "openDev()");

		return connected;
	}

	public void click_minus(View view)
	{
		Log.d(TAG, "click_volume_minus");
		byte[] cmd = { 0x02, /*e_type_dec_vol*/24 };
		byte[] msg = SerMsg.create_msg(cmd);
		sendData(msg);
	}

	public void click_plus(View view)
	{
		Log.d(TAG, "click_volume_plus");
		byte[] cmd = { 0x02, /*e_type_inc_vol*/23 };
		byte[] msg = SerMsg.create_msg(cmd);
		sendData(msg);
	}

	public void click_restart(View view)
	{
		Log.d(TAG, "click_restart");
		//e_type_start
		byte[] cmd = { 0x02, /*e_type_start*/0x06 };
		byte[] msg = SerMsg.create_msg(cmd);
		sendData(msg);
	}
}

