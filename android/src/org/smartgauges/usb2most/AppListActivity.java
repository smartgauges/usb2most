package org.smartgauges.usb2most;

import java.util.List;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class AppListActivity extends Activity
{ 
	private ListView lv = null;
	private ArrayList<PInfo> list = null;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_applist);

		lv = (ListView)findViewById(R.id.applist);

		list = ConfActivity.get_app_list();

		lv.setAdapter(new AppAdapter(this, list));

		lv.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Intent intent = new Intent();

				intent.putExtra("position", position);
				intent.putExtra("id", id);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}
}

