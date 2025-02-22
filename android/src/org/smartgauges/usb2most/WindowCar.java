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

public class WindowCar
{
	private static final String TAG = Constants.APPLICATION_ID + ":Car";

	public static final int DOOR_FL = 0x80;
	public static final int DOOR_FR = 0x40;
	public static final int DOOR_RL = 0x20;
	public static final int DOOR_RR = 0x10;
	public static final int DOOR_TAILGATE = 0x08;
	public static final int DOOR_BONNET = 0x04;
	public static final int DOOR_BELT_FL = 0x02;
	public static final int DOOR_BELT_FR = 0x01;

	public static final byte MAX_RADAR = 10;

	private Context mContext;

	private WindowManager mWManager = null;

	private View mDoorView = null;
	private WindowManager.LayoutParams mDoorParams = null;
	private ImageView v_door_fl;
	private ImageView v_door_fr;
	private ImageView v_door_rl;
	private ImageView v_door_rr;
	private ImageView v_door_bonnet;
	private ImageView v_door_tailgate;
	private ImageView v_belt_pass;
	private ImageView v_belt_driver;
	private int door;
	private boolean door_f_flag = false;
	private boolean door_fr_falg = false;
	private boolean door_r_flag = false;
	private int door_show = 0;

	private View mRadarView = null;
	private WindowManager.LayoutParams mRadarParams = null;
	private int[] b_imgageId = { R.id.back_left_img, R.id.back_left_mid_img, R.id.back_right_mid_img, R.id.back_right_img };
	private int[] b_l_ImgId = { R.drawable.rr_one_d, R.drawable.rr_two_d, R.drawable.rr_three_d, R.drawable.rr_four_d, R.drawable.rr_five_d, R.drawable.rr_six_d, R.drawable.rr_seven_d, R.drawable.rr_eight_d, R.drawable.rr_nine_d, R.drawable.rr_ten_d };
	private int[] b_m_l_ImgId = { R.drawable.rr_one_dd, R.drawable.rr_two_dd, R.drawable.rr_three_dd, R.drawable.rr_four_dd, R.drawable.rr_five_dd, R.drawable.rr_six_dd, R.drawable.rr_seven_dd, R.drawable.rr_eight_dd, R.drawable.rr_nine_dd, R.drawable.rr_ten_dd };
	private int[] b_m_r_ImgId = { R.drawable.rr_one_uu, R.drawable.rr_two_uu, R.drawable.rr_three_uu, R.drawable.rr_four_uu, R.drawable.rr_five_uu, R.drawable.rr_six_uu, R.drawable.rr_seven_uu, R.drawable.rr_eight_uu, R.drawable.rr_nine_uu, R.drawable.rr_ten_uu };
	private int[] b_r_ImgId = { R.drawable.rr_one_u, R.drawable.rr_two_u, R.drawable.rr_three_u, R.drawable.rr_four_u, R.drawable.rr_five_u, R.drawable.rr_six_u, R.drawable.rr_seven_u, R.drawable.rr_eight_u, R.drawable.rr_nine_u, R.drawable.rr_ten_u };
	private ImageView[] b_mImgage = new ImageView[b_imgageId.length];
	private ArrayList<int[]> back_radar_list = new ArrayList<>();
	private int[] f_imgageId = { R.id.front_left_img, R.id.front_left_mid_img, R.id.front_right_mid_img, R.id.front_right_img };
	private int[] f_l_ImgId = { R.drawable.lr_one_d, R.drawable.lr_two_d, R.drawable.lr_three_d, R.drawable.lr_four_d, R.drawable.lr_five_d, R.drawable.lr_six_d, R.drawable.lr_seven_d, R.drawable.lr_eight_d, R.drawable.lr_nine_d, R.drawable.lr_ten_d };
	private int[] f_m_l_ImgId = { R.drawable.lr_one_dd, R.drawable.lr_two_dd, R.drawable.lr_three_dd, R.drawable.lr_four_dd, R.drawable.lr_five_dd, R.drawable.lr_six_dd, R.drawable.lr_seven_dd, R.drawable.lr_eight_dd, R.drawable.lr_nine_dd, R.drawable.lr_ten_dd };
	private int[] f_m_r_ImgId = { R.drawable.lr_one_uu, R.drawable.lr_two_uu, R.drawable.lr_three_uu, R.drawable.lr_four_uu, R.drawable.lr_five_uu, R.drawable.lr_six_uu, R.drawable.lr_seven_uu, R.drawable.lr_eight_uu, R.drawable.lr_nine_uu, R.drawable.lr_ten_uu };
	private int[] f_r_ImgId = { R.drawable.lr_one_u, R.drawable.lr_two_u, R.drawable.lr_three_u, R.drawable.lr_four_u, R.drawable.lr_five_u, R.drawable.lr_six_u, R.drawable.lr_seven_u, R.drawable.lr_eight_u, R.drawable.lr_nine_u, R.drawable.lr_ten_u };
	private ImageView[] f_mImgage = new ImageView[f_imgageId.length];
	private ArrayList<int[]> front_radar_list = new ArrayList<>();
	private int radar_show = 0;

	public WindowCar(Context context)
	{
		mContext = context;
		door = 0;
		door_show = 0;
		radar_show = 0;

		mWManager = (WindowManager)mContext.getSystemService("window");
		mDoorParams = new WindowManager.LayoutParams(200, 400, 0, 0,
			Constants.TYPE_OVERLAY,
			WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
			PixelFormat.RGBA_8888);
		mDoorParams.gravity = Gravity.CENTER | Gravity.RIGHT;

		mRadarParams = new WindowManager.LayoutParams(200, 400, 0, 0,
			Constants.TYPE_OVERLAY,
			WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
			PixelFormat.RGBA_8888);
		mRadarParams.gravity = Gravity.CENTER | Gravity.RIGHT;

		mDoorView = LayoutInflater.from(mContext).inflate(R.layout.window_door, null);
		Log.d(TAG, "mDoorView:" + mDoorView);
		v_door_fl = (ImageView)mDoorView.findViewById(R.id.door_fl);
		v_door_fr = (ImageView)mDoorView.findViewById(R.id.door_fr);
		v_door_rl = (ImageView)mDoorView.findViewById(R.id.door_rl);
		v_door_rr = (ImageView)mDoorView.findViewById(R.id.door_rr);
		v_door_tailgate = (ImageView)mDoorView.findViewById(R.id.door_tailgate);
		v_door_bonnet = (ImageView)mDoorView.findViewById(R.id.door_bonnet);
		v_belt_driver = (ImageView)mDoorView.findViewById(R.id.belt_driver);
		v_belt_pass = (ImageView)mDoorView.findViewById(R.id.belt_pass);

		mDoorView.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent)
			{
				removeDoor();
				return false;
			}
		});

		mRadarView = LayoutInflater.from(mContext).inflate(R.layout.window_radar, null);
		Log.d(TAG, "mRadarView:" + mRadarView);
		for (int i = 0; i < f_imgageId.length; i++)
			f_mImgage[i] = (ImageView)mRadarView.findViewById(f_imgageId[i]);
		for (int i = 0; i < b_imgageId.length; i++)
			b_mImgage[i] = (ImageView)mRadarView.findViewById(b_imgageId[i]);

		front_radar_list.clear();
		front_radar_list.add(f_l_ImgId);
		front_radar_list.add(f_m_l_ImgId);
		front_radar_list.add(f_m_r_ImgId);
		front_radar_list.add(f_r_ImgId);

		back_radar_list.clear();
		back_radar_list.add(b_l_ImgId);
		back_radar_list.add(b_m_l_ImgId);
		back_radar_list.add(b_m_r_ImgId);
		back_radar_list.add(b_r_ImgId);

		mRadarView.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent)
			{

				removeRadar();
				return false;
			}
		});
	}

	private void removeDoor()
	{
		if (door_show == 1) {

			door_show = 0;

			if (mDoorView != null) {

				try {
					mWManager.removeView(mDoorView);
				} catch (Exception e) {

					Log.d(TAG, "removeView e:" + e);
				}
			}

			Log.d(TAG, "removeView door_show:" + door_show);
		}
	}

	private void showDoor()
	{
		if (door_show == 0) {

			door_show = 1;

			try {

				mWManager.addView(mDoorView, mDoorParams);
			} catch (Exception e) {

				Log.d(TAG, "exception e:" + e);
			}

			Log.d(TAG, "addView door_show:" + door_show);
		}
	}

	public void ctrlDoor(int da)
	{
		if (da == door)
			return;
		door = da;

		//Log.d(TAG, "da:" + da + " door_show:" + door_show);

		if (da == 0) {

			removeDoor();
			return;
		}

		if ((da & 0x80) == 0x80) {
			v_door_fl.setVisibility(View.VISIBLE);
		} else {
			v_door_fl.setVisibility(View.INVISIBLE);
		}

		if ((da & 0x40) == 0x40) {
			v_door_fr.setVisibility(View.VISIBLE);
		} else {
			v_door_fr.setVisibility(View.INVISIBLE);
		}

		if ((da & 0x20) == 0x20) {
			v_door_rl.setVisibility(View.VISIBLE);
		} else {
			v_door_rl.setVisibility(View.INVISIBLE);
		}

		if ((da & 0x10) == 0x10) {
			v_door_rr.setVisibility(View.VISIBLE);
		} else {
			v_door_rr.setVisibility(View.INVISIBLE);
		}

		if ((da & 0x08) == 0x08) {
			v_door_tailgate.setVisibility(View.VISIBLE);
		} else {
			v_door_tailgate.setVisibility(View.INVISIBLE);
		}

		if ((da & 0x04) == 0x04) {
			v_door_bonnet.setVisibility(View.VISIBLE);
		} else {
			v_door_bonnet.setVisibility(View.INVISIBLE);
		}

		if ((da & 0x02) == 0x02) {
			v_belt_driver.setVisibility(View.VISIBLE);
		} else {
			v_belt_driver.setVisibility(View.INVISIBLE);
		}

		if ((da & 0x01) == 0x01) {
			v_belt_pass.setVisibility(View.VISIBLE);
		} else {
			v_belt_pass.setVisibility(View.INVISIBLE);
		}

		showDoor();
	}

	private void removeRadar()
	{
		if (radar_show == 1) {

			radar_show = 0;

			if (mRadarView != null) {
				try {
					mWManager.removeView(mRadarView);
				} catch (Exception e) {

					Log.d(TAG, "exception e:" + e);
				}
			}

			Log.d(TAG, "removeView radar_show:" + radar_show);
		}
	}

	private void showRadar()
	{
		if (radar_show == 0) {

			radar_show = 1;

			try {

				mWManager.addView(mRadarView, mRadarParams);
			} catch (Exception e) {

				radar_show = 0;
				Log.d(TAG, "exception e:" + e);
			}

			Log.d(TAG, "addView radar_show:" + radar_show);
		}
	}

	private void drawRadarImgFront_da(byte[] da)
	{

		for (int i = 0; i < 4; i++) {

			int n = da[i] & 0xff;
			if (n >= MAX_RADAR) {

				f_mImgage[i].setVisibility(View.INVISIBLE);
			}
			else {

				f_mImgage[i].setVisibility(View.VISIBLE);
				f_mImgage[i].setBackgroundResource(front_radar_list.get(i)[n]);
			}
		}
	}

	private void drawRadarImgBack_da(byte[] da)
	{
		for (int i = 0; i < 4; i++) {

			int n = da[i] & 0xff;
			if (n >= MAX_RADAR) {
				
				b_mImgage[i].setVisibility(View.INVISIBLE);
			}
			else {

				b_mImgage[i].setVisibility(View.VISIBLE);
				b_mImgage[i].setBackgroundResource(back_radar_list.get(i)[n]);
			}
		}
	}

	public void ctrlRadar(boolean radarshow, byte[] front, byte[] back)
	{
		//Log.d(TAG, "radarshow:" + radarshow + " front:" + front[0] + front[1] + front[2] + front[3] + " back:" + back[0] + back[1] + back[2] + back[3]);

		if (radarshow) {

			drawRadarImgFront_da(front);
			drawRadarImgBack_da(back);

			showRadar();
		}
		else
			removeRadar();
	}
}

