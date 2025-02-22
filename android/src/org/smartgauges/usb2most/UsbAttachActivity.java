package org.smartgauges.usb2most;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbManager;
import android.widget.TextView;
import android.app.Notification;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class UsbAttachActivity extends Activity
{   
	private static final String TAG = Constants.APPLICATION_ID + ":Usb";

	private int counter = 0;
	private Timer timer = null;

	private void tryStartService()
	{
		Intent intent = new Intent(this, SerialService.class);
		ComponentName r = startService(intent);

		TextView tv = (TextView)findViewById(R.id.textView);
		String s = "startService counter:" + counter + " ret:" + r;
		tv.setText(s);
		Log.d(TAG, s);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setSmallIcon(R.drawable.ic_notification);
		builder.setContentTitle(getResources().getString(R.string.app_name));
		builder.setContentText(s);
		Notification notification = builder.build();

		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationManager.notify(0, notification);

		counter++;

		if (r != null)
			stopTimerConnect();
	}

	private void startTimerConnect()
	{
		stopTimerConnect();

		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {

				runOnUiThread(new Runnable(){

					@Override
					public void run(){

						tryStartService();
					}
				});
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
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		setContentView(R.layout.activity_usb);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		Log.d(TAG, "onResume");

		TextView tv = (TextView)findViewById(R.id.textView);

		Intent intent = getIntent();
		if (intent != null)
		{
			tv.setText("!null");

			if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED))
			{
				tv.setText("ACTION_USB_DEVICE_ATTACHED");

				startTimerConnect();

				Parcelable usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

				// Create a new intent and put the usb device in as an extra
				Intent broadcastIntent = new Intent(Constants.ACTION_USB_DEVICE_ATTACHED);
				broadcastIntent.putExtra(UsbManager.EXTRA_DEVICE, usbDevice);

				// Broadcast this event so we can receive it
				sendBroadcast(broadcastIntent);

				getIntent().setAction("");
			}
		}

		moveTaskToBack(true);
	}
}

