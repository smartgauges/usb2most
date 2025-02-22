package org.smartgauges.usb2most;

import java.util.List;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.content.ComponentName;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Switch;
import android.graphics.drawable.Drawable;

class PInfo
{
	public String appname = "";
	public String pname = "";
	public Drawable icon;
	Boolean sys;
};

public class ConfActivity extends Activity
{
	private static final String TAG = Constants.APPLICATION_ID + ":Conf";
	private final int REQUEST_CODE_MUSIC = 0;
	private final int REQUEST_CODE_NAV = 1;
	private final int REQUEST_CODE_PHONE = 2;
	private final int REQUEST_CODE_MEDIA = 3;
	private final int REQUEST_CODE_NUMS = 4;

	private PackageManager pm = null;
	private static ArrayList<PInfo> list = null;

	static Switch sw_sysapps;
	static Switch sw_doors;
	static Switch sw_radar;

	public static ArrayList<PInfo> get_app_list()
	{
		Boolean show_sysapps = sw_sysapps.isChecked();
		Log.d(TAG, "Show sysapps: " + show_sysapps);

		ArrayList<PInfo> l = new ArrayList<PInfo>();
		for (int i = 0; i < list.size(); i++) {

			PInfo info = list.get(i);

			if (info.sys && !show_sysapps)
				continue;

			l.add(info);
		}

		return l;
	}

	boolean isSystemPackage(PackageInfo pkgInfo)
	{
		return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true : false;
	}

	private ArrayList<PInfo> getInstalledApps(boolean getSysPackages)
	{
		ArrayList<PInfo> al = new ArrayList<PInfo>();
		List<PackageInfo> packs = pm.getInstalledPackages(0);
		for (int i = 0; i < packs.size(); i++) {

			PackageInfo p = packs.get(i);
			Boolean sys = isSystemPackage(p);
			if (!getSysPackages && sys)
				continue ;

			if (null == pm.getLaunchIntentForPackage(p.packageName))
				continue;

			PInfo newInfo = new PInfo();
			newInfo.appname = p.applicationInfo.loadLabel(pm).toString();
			newInfo.pname = p.packageName;
			newInfo.icon = p.applicationInfo.loadIcon(pm);
			newInfo.sys = sys;
			al.add(newInfo);
		}

		return al; 
	}

	private void set_icon(String btn_name, int btn_id)
	{
		String pname = Settings.getAppByBtn(getApplicationContext(), btn_name);

		Log.d(TAG, "search app for btn:" + btn_name + " pname:" + pname + " list.size():" + list.size());

		for (int i = 0; i < list.size(); i++) {

			PInfo pi = list.get(i);

			if (pi.pname.equals(pname)) {

				Log.d(TAG, "found app:" + pname + " for btn:" + btn_name);

				Button btn = (Button)findViewById(btn_id);
				if (btn != null) {

					Log.d(TAG, "set icon for btn:" + btn_name);
					//pi.icon.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					pi.icon.setBounds(0, 0, 40, 40);
					btn.setCompoundDrawables(pi.icon, null, null, null);
				}
				break;
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");

		pm = getPackageManager();

		setContentView(R.layout.activity_conf);

		sw_sysapps = (Switch)findViewById(R.id.conf_switch_apps);
		sw_doors = (Switch)findViewById(R.id.conf_switch_doors);
		sw_radar = (Switch)findViewById(R.id.conf_switch_radar);

		boolean show_sysapps = Settings.getShowSysApps(getApplicationContext());
		boolean show_doors = Settings.getShowDoors(getApplicationContext());
		boolean show_radar = Settings.getShowRadar(getApplicationContext());

		sw_sysapps.setChecked(show_sysapps);
		sw_doors.setChecked(show_doors);
		sw_radar.setChecked(show_radar);

		new AsyncTask<Void, Void, ArrayList<PInfo>>()
		{
			ProgressDialog progress = null;

			protected void onPreExecute()
			{
				progress = ProgressDialog.show(ConfActivity.this, null, getString(R.string.list_loads), true);
			}

			protected ArrayList<PInfo> doInBackground(Void... params)
			{
				list = getInstalledApps(true);
				return list;
			}

			protected void onPostExecute(ArrayList<PInfo> result)
			{
				set_icon("music", R.id.conf_music);
				set_icon("nav", R.id.conf_nav);
				set_icon("phone", R.id.conf_phone);
				set_icon("media", R.id.conf_media);

				progress.dismiss();
			}
		}.execute();
	}

	public void onPause()
	{
		Log.d(TAG, "onPause() in");

		Settings.setShowSysApps(getApplicationContext(), sw_sysapps.isChecked());
		Settings.setShowDoors(getApplicationContext(), sw_doors.isChecked());
		Settings.setShowRadar(getApplicationContext(), sw_radar.isChecked());

		Log.d(TAG, "onPause() out");
		super.onDestroy();
	}

	private void launch_app(String app)
	{
		if (app == "")
			return;

		Intent intent = pm.getLaunchIntentForPackage(app);
		startActivity(intent);
	}

	private PInfo select_app()
	{
		return new PInfo();
	}

	public void click(int id)
	{
		Log.d(TAG, "click id:" + id);

		Intent intent = new Intent(this, AppListActivity.class);
		startActivityForResult(intent, id);
	}

	public void click_music(View view)
	{
		Log.d(TAG, "click_music");

		click(REQUEST_CODE_MUSIC);
	}

	public void click_nav(View view)
	{
		Log.d(TAG, "click_nav");

		click(REQUEST_CODE_NAV);
	}

	public void click_phone(View view)
	{
		Log.d(TAG, "click_phone");

		click(REQUEST_CODE_PHONE);
	}

	public void click_media(View view)
	{
		Log.d(TAG, "click_media");

		click(REQUEST_CODE_MEDIA);
	}

	private void click_clear(String btn_name, int btn_id)
	{
		Log.d(TAG, "click_clear " + btn_name);

		Settings.setAppByBtn(getApplicationContext(), btn_name, "");

		Button btn = (Button)findViewById(btn_id);
		if (btn != null) {

			btn.setCompoundDrawables(null, null, null, null);
		}
	}

	public void click_clear_music(View view)
	{
		click_clear("music", R.id.conf_music);
	}

	public void click_clear_nav(View view)
	{
		click_clear("nav", R.id.conf_nav);
	}

	public void click_clear_phone(View view)
	{
		click_clear("phone", R.id.conf_phone);
	}

	public void click_clear_media(View view)
	{
		click_clear("media", R.id.conf_media);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		Log.d(TAG, "click_result: requestCode:" + requestCode + " resultCode:" + resultCode);

		//RESULT_CANCELED
		if (resultCode != RESULT_OK)
			return;

		if (data == null)
			return;

		int pos = data.getIntExtra("position", -1);

		ArrayList<PInfo> l = get_app_list();

		if ((pos == -1) || (pos > l.size()))
			return;

		String btn_name = null;
		int btn_id = 0;

		switch (requestCode) {
			case REQUEST_CODE_MUSIC:
				btn_name = "music";
				btn_id = R.id.conf_music;
				break;
			case REQUEST_CODE_NAV:
				btn_name = "nav";
				btn_id = R.id.conf_nav;
				break;
			case REQUEST_CODE_PHONE:
				btn_name = "phone";
				btn_id = R.id.conf_phone;
				break;
			case REQUEST_CODE_MEDIA:
				btn_name = "media";
				btn_id = R.id.conf_media;
				break;
		}

		PInfo pi = l.get(pos);

		Log.d(TAG, "select: appname:" + pi.appname + " pname:" + pi.pname);

		if (btn_name != null) {

			Log.d(TAG, "save: btn:" + btn_name + " = " + pi.pname);

			Settings.setAppByBtn(getApplicationContext(), btn_name, pi.pname);
		}

		Button btn = (Button)findViewById(btn_id);
		if (btn != null) {

			Log.d(TAG, "found btn");

			btn.setCompoundDrawables(pi.icon, null, null, null);
		}
	}
}

