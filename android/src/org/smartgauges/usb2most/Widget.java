package org.smartgauges.usb2most;

import java.util.Arrays;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;
import android.content.Intent;
import android.widget.Toast;

public class Widget extends AppWidgetProvider
{
	private static final String TAG = Constants.APPLICATION_ID + ":Wdg";

	@Override
	public void onEnabled(Context context)
	{
		super.onEnabled(context);
		Log.d(TAG, "onEnabled");
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
	{
		//super.onUpdate(context, appWidgetManager, appWidgetIds);
		for (int i = 0; i < appWidgetIds.length; i++) {

			int appWidgetId = appWidgetIds[i];

			Log.d(TAG, "onUpdate " + Arrays.toString(appWidgetIds));

			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

			// When we click the widget, we want to open our main activity.
			Intent intentQ = new Intent(context, MainActivity.class);
			PendingIntent pintentQ = PendingIntent.getActivity(context, 0, intentQ, 0);
			views.setOnClickPendingIntent(R.id.ll_widget, pintentQ);

			// When we click the conf image, we want to open our conf activity.
			Intent intentConf = new Intent(context, ConfActivity.class);
			PendingIntent pintentConf = PendingIntent.getActivity(context, 0, intentConf, 0);
			views.setOnClickPendingIntent(R.id.imageView_conf, pintentConf);

			//ComponentName thisWidget = new ComponentName(context, Widget.class);
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}

	public void onReceive(Context context, Intent intent)
	{
		super.onReceive(context, intent);
		Log.d(TAG, "onReceived " + intent);
		
		Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds)
	{
		super.onDeleted(context, appWidgetIds);
		Log.d(TAG, "onDeleted " + Arrays.toString(appWidgetIds));
	}

	@Override
	public void onDisabled(Context context)
	{
		super.onDisabled(context);
		Log.d(TAG, "onDisabled");
	}

	static private void update_widget(RemoteViews view, Context context)
	{
		//Log.d(TAG, "update widget");

		if (context == null)
			return;

		ComponentName widget = new ComponentName(context, Widget.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(context);
		manager.updateAppWidget(widget, view);
	}

	static public void update_info(Context context, String s)
	{
		if (context == null)
			return;

		RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.widget);
		view.setTextViewText(R.id.tv_info, s);
		update_widget(view, context);
	}

	static public void update_src(Context context, String s)
	{
		if (context == null)
			return;

		RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.widget);
		view.setTextViewText(R.id.tv_src, s);
		update_widget(view, context);
	}

	static public void update_vol(Context context, String s)
	{
		if (context == null)
			return;

		RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.widget);
		view.setTextViewText(R.id.tv_vol, s);
		update_widget(view, context);
	}
}

