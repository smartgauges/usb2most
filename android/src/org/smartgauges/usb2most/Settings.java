package org.smartgauges.usb2most;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

class Settings
{
	private static final String APP_PREFERENCES = "conf";
	
	private static final String show_sysapps = "show_sysapps";
	private static final String show_doors = "show_doors";
	private static final String show_radar = "show_radar";

	private Settings() {}

	private static SharedPreferences getSharedPreferences(Context context)
	{
		return context.getSharedPreferences(APP_PREFERENCES, /*Context.MODE_MULTI_PROCESS*/Context.MODE_PRIVATE);
	}

	public static String getAppByBtn(Context context, String btn)
	{
		return getSharedPreferences(context).getString(btn, null);
	}

	public static void setAppByBtn(Context context, String btn, String v)
	{
		final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
		editor.putString(btn, v);
		editor.commit();
	}

	public static boolean getShowSysApps(Context context)
	{
		return getSharedPreferences(context).getBoolean(show_sysapps, false);
	}

	public static void setShowSysApps(Context context, boolean v)
	{
		final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
		editor.putBoolean(show_sysapps, v);
		editor.commit();
	}

	public static boolean getShowDoors(Context context)
	{
		return getSharedPreferences(context).getBoolean(show_doors, false);
	}

	public static void setShowDoors(Context context, boolean v)
	{
		final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
		editor.putBoolean(show_doors, v);
		editor.commit();
	}

	public static boolean getShowRadar(Context context)
	{
		return getSharedPreferences(context).getBoolean(show_radar, false);
	}

	public static void setShowRadar(Context context, boolean v)
	{
		final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
		editor.putBoolean(show_radar, v);
		editor.commit();
	}
}

