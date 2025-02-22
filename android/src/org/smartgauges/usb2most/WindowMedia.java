package org.smartgauges.usb2most;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.PixelFormat;
import android.view.Gravity;
import java.util.ArrayList;

import android.util.Log;
import java.util.Timer;
import java.util.TimerTask;

public class WindowMedia
{
	private static final String TAG = Constants.APPLICATION_ID + ":Media";

	private Context mContext;

	private WindowManager mWManager = null;

	private WindowManager.LayoutParams mVolumeParams = null;
	private View mVolumeView = null;
	private TextView tv_volume;
	private int volume = 0;
	private int volume_show = 0;
	private Timer volume_timer = null;

	private WindowManager.LayoutParams mMediaParams = null;
	private View mMediaView = null;
	private TextView tv_media;
	private String media = "";
	private int media_show = 0;
	private Timer media_timer = null;

	public WindowMedia(Context context)
	{
		mContext = context;
		volume = 0;
		volume_show = 0;

		mWManager = (WindowManager)mContext.getSystemService("window");

		mVolumeParams = new WindowManager.LayoutParams(192, 192, 0, 0,
			Constants.TYPE_OVERLAY,
			WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
			PixelFormat.RGBA_8888);
		mVolumeParams.gravity = Gravity.CENTER;

		mVolumeView = LayoutInflater.from(mContext).inflate(R.layout.window_volume, null);
		//Log.d(TAG, "mVolumeView:" + mVolumeView);
		tv_volume = (TextView)mVolumeView.findViewById(R.id.level);

		mMediaParams = new WindowManager.LayoutParams(336, 112, 0, -192/2 - 112/2,
			Constants.TYPE_OVERLAY,
			WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
			PixelFormat.RGBA_8888);
		mMediaParams.gravity = Gravity.CENTER;

		mMediaView = LayoutInflater.from(mContext).inflate(R.layout.window_media, null);
		//Log.d(TAG, "mMediaView:" + mMediaView);
		tv_media = (TextView)mMediaView.findViewById(R.id.media);
	}

	private void removeVolume()
	{
		//Log.d(TAG, "removeVolume");

		if (volume_show != 0) {

			volume_show = 0;

			if (mVolumeView != null) {

				try {
					mWManager.removeView(mVolumeView);
				} catch (Exception e) {

					Log.d(TAG, "removeView e:" + e);
				}
			}

			//Log.d(TAG, "removeView volume_show:" + volume_show);
		}
	}

	private void showVolume()
	{
		//Log.d(TAG, "showVolume");

		volume_show++;

		if (volume_show == 1) {

			try {

				mWManager.addView(mVolumeView, mVolumeParams);

				if (volume_timer != null) {

					volume_timer.cancel();
					volume_timer.purge();
					volume_timer = null;
				}

				volume_timer = new Timer();

				//Log.d(TAG, "schedule new Task");

				volume_timer.schedule(new TimerTask() {

					@Override
					public void run() {

						if (volume_show == 1) {

							volume_timer.cancel();
							volume_timer.purge();
							volume_timer = null;

							removeVolume();
						}
						else
							volume_show = 1;
					}

				}, 1000, 1000);

			} catch (Exception e) {

				Log.d(TAG, "exception e:" + e);
			}

			//Log.d(TAG, "addView volume_show:" + volume_show);
		}
	}

	public void ctrlVolume(int v)
	{
		if (v == volume)
			return;

		volume = v;

		//Log.d(TAG, "v:" + v + " volume_show:" + volume_show);

		tv_volume.setText(String.format("%d", volume));

		showVolume();
	}

	private void removeMedia()
	{
		//Log.d(TAG, "removeMedia");

		if (media_show != 0) {

			media_show = 0;

			if (mMediaView != null) {

				try {
					mWManager.removeView(mMediaView);
				} catch (Exception e) {

					Log.d(TAG, "removeView e:" + e);
				}
			}

			//Log.d(TAG, "removeView media_show:" + media_show);
		}
	}

	private void showMedia()
	{
		//Log.d(TAG, "showMedia");

		media_show++;

		if (media_show == 1) {

			try {

				mWManager.addView(mMediaView, mMediaParams);

				if (media_timer != null) {

					media_timer.cancel();
					media_timer.purge();
					media_timer = null;
				}

				media_timer = new Timer();

				//Log.d(TAG, "schedule new Task");

				media_timer.schedule(new TimerTask() {

					@Override
					public void run() {

						if (media_show == 1) {

							media_timer.cancel();
							media_timer.purge();
							media_timer = null;

							removeMedia();
						}
						else
							media_show = 1;
					}

				}, 1000, 1000);

			} catch (Exception e) {

				Log.d(TAG, "exception e:" + e);
			}

			//Log.d(TAG, "addView media_show:" + media_show);
		}
	}

	public void ctrlMedia(String m)
	{
		if (m.equals(media))
			return;

		media = m;

		//Log.d(TAG, "m:" + m + "media:" + media + " media_show:" + media_show);

		tv_media.setText(media);

		showMedia();
	}
}

