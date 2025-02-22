package org.smartgauges.usb2most;

import android.content.Context;
import android.hardware.usb.UsbDeviceConnection;
import android.util.Log;

import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.util.SerialInputOutputManager;

import java.io.IOException;
import java.util.concurrent.Executors;

public class SerialSocket implements SerialInputOutputManager.Listener
{
	private static final String TAG = Constants.APPLICATION_ID + ":Sock";

	private static final int WRITE_WAIT_MILLIS = 2000; // 0 blocked infinitely

	private SerialListener listener;
	private UsbDeviceConnection usbdev_connection;
	private UsbSerialPort serialPort;
	private SerialInputOutputManager ioManager;

	SerialSocket()
	{
		Log.d(TAG, "SerialSocket()");
	}

	String getName()
	{
		return serialPort.getDriver().getClass().getSimpleName().replace("SerialDriver","");
	}

	void connect(SerialListener listener, UsbDeviceConnection connection, UsbSerialPort serialPort) throws IOException
	{
		Log.d(TAG, "connect()");
		if (this.serialPort != null)
			throw new IOException("already connected");
		this.listener = listener;
		this.usbdev_connection = connection;
		this.serialPort = serialPort;
		serialPort.open(connection);
		serialPort.setParameters(115200, UsbSerialPort.DATABITS_8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
		serialPort.setDTR(true);
		serialPort.setRTS(true);
		ioManager = new SerialInputOutputManager(serialPort, this);
		Executors.newSingleThreadExecutor().submit(ioManager);
	}

	void disconnect()
	{
		Log.d(TAG, "disconnect()");

		if (listener != null) {

			listener = null;
		}

		if (ioManager != null) {

			ioManager.setListener(null);
			ioManager.stop();
			ioManager = null;
		}

		if (serialPort != null) {
			try {
				serialPort.close();
			} catch (Exception ignored) {
			}
			serialPort = null;
		}

		if (usbdev_connection != null) {

			usbdev_connection.close();
			usbdev_connection = null;
		}
	}

	void sendData(byte[] data) throws IOException
	{
		//Log.d(TAG, "sendData()");
		if(serialPort == null)
			throw new IOException("not connected");

		serialPort.write(data, WRITE_WAIT_MILLIS);
	}

	@Override
	public void onNewData(byte[] data)
	{
		if (listener != null)
			listener.onSerialNewData(data);
	}

	@Override
	public void onRunError(Exception e)
	{
		Log.d(TAG, "onRunError()");
		if (listener != null)
			listener.onSerialIoError(e);
	}
}
