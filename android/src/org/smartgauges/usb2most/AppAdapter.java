package org.smartgauges.usb2most;

import java.util.List;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
public class AppAdapter extends BaseAdapter
{
	List<PInfo> list;
	Activity context;

	public AppAdapter(Activity context, List<PInfo> list)
	{
		super();
		this.context = context;
		this.list = list;
	}

	private class ViewHolder
	{
		TextView apkName;
	}

	public int getCount()
	{
		return list.size();
	}

	public Object getItem(int position)
	{
		return list.get(position);
	}

	public long getItemId(int position)
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;

		if (convertView == null) {

			LayoutInflater inflater = context.getLayoutInflater();

			convertView = inflater.inflate(R.layout.applist_item, null);
			holder = new ViewHolder();

			holder.apkName = (TextView) convertView.findViewById(R.id.appname);
			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		PInfo info = (PInfo)getItem(position);
		Drawable appIcon = info.icon;
		String appName = info.appname;
		appIcon.setBounds(0, 0, 40, 40);
		holder.apkName.setCompoundDrawables(appIcon, null, null, null);
		holder.apkName.setCompoundDrawablePadding(15);
		holder.apkName.setText(appName);

		return convertView;
	}
}

