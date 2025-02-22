package org.smartgauges.usb2most;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.content.res.Resources;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;

import android.app.Notification;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class StartReceiver extends BroadcastReceiver
{
	private static final String TAG = Constants.APPLICATION_ID + ":Boot";

	@Override
	public void onReceive(Context context, Intent intent)
	{
		if ((intent != null) && (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED) || intent.getAction().equals(Intent.ACTION_MY_PACKAGE_REPLACED))) {

			//ComponentName r = startService(new Intent(this, SerialService.class));
			Intent mBootIntent = new Intent(context, SerialService.class);
			mBootIntent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
			ComponentName r = context.startService(mBootIntent);

			String s = "BOOT_COMPLETED: ret:" + r;
			Log.d(TAG, s);

			NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
			builder.setSmallIcon(R.drawable.ic_notification);
			builder.setContentTitle(context.getResources().getString(R.string.app_name));
			builder.setContentText(s);
			Notification notification = builder.build();

			NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
			notificationManager.notify(0, notification);
		}
	}
}

