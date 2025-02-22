package org.smartgauges.usb2most;

import android.view.WindowManager;

class Constants
{
	// values have to be globally unique
        static final String APPLICATION_ID = "org.smartgauges.usb2most";
	static final String INTENT_ACTION_MSG = APPLICATION_ID + ".Msg";
	static final String INTENT_CLASS_MAINACTIVITY = APPLICATION_ID + ".MainActivity";
	static final String NOTIFICATION_CHANNEL = APPLICATION_ID + ".Channel";
	static final String ACTION_GRANT_USB = APPLICATION_ID + ".ACTION_GRANT_USB";
	static final String ACTION_USB_DEVICE_ATTACHED = APPLICATION_ID + ".ACTION_USB_DEVICE_ATTACHED";

	// values have to be unique within each app
	static final int NOTIFY_MANAGER_START_FOREGROUND_SERVICE = 1001;

	static final int TYPE_OVERLAY = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;

	private Constants() {}
}

