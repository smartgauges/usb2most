package org.smartgauges.usb2most;

interface SerialListener {
    void onSerialConnectError(Exception e);
    void onSerialNewData(byte[] data);
    void onSerialIoError(Exception e);
}
