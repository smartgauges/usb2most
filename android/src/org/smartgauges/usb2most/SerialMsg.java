package org.smartgauges.usb2most;

import java.util.Arrays;
import java.nio.ByteBuffer;

public class SerialMsg
{
	//enum
	final private int RX_ST_WAIT_START = 1;
	final private int RX_ST_DATA = 2;
	final private int RX_ST_ESCAPE = 3;

	private int rx_state = RX_ST_WAIT_START;

	final private byte HDLC_FD = 0x7E;
	final private byte HDLC_ESCAPE = 0x7D;

	private static final int BUFSIZ = 1024;
	private final ByteBuffer ba = ByteBuffer.allocate(BUFSIZ);

	public byte[] get_msg(byte ch)
	{
		switch (rx_state) {

			case RX_ST_WAIT_START:

				if (ch != HDLC_FD)
					break;

				ba.clear();
				rx_state = RX_ST_DATA;
				//Log.d(TAG, "start");
				break;

			case RX_ST_DATA:

				if (ch == HDLC_ESCAPE) {

					/* drop the escape octet, but change state */
					rx_state = RX_ST_ESCAPE;
					break;
				}
				else if (ch == HDLC_FD) {

					//Log.d(TAG, "finish");
					/* message is finished */
					/* start all over again */
					rx_state = RX_ST_WAIT_START;
					int ba_len = ((short)(ba.position() & (short)0xff));
					if (ba_len >= 2) {

						int crc = 0;
						for (int i = 0; i < (ba_len - 1); i++)
							crc += ((short)(ba.get(i) & (short)0xff));
						crc ^= 0xff;
						crc &= 0xff;

						int _crc = ((short)(ba.get(ba_len - 1) & (short)0xff));
						if (crc != _crc) {

							//Log.d(TAG, "bad crc");
							return null;
						}

						int msg_len = ((short)(ba.get(0) & (short)0xff));
						int msg_type = ((short)(ba.get(1) & (short)0xff));
						//Log.d(TAG, String.format("len:%d type:%d", msg_len, msg_type));
						if (msg_len == (ba_len - 1)) {

							//Log.d(TAG, String.format("new msg len:%d type:%d", msg_len, msg_type));
							byte[] data = new byte[msg_len];
							//ba.get(data, 0, msg_len);
							for (int i = 0; i < msg_len; i++)
								data[i] = ba.get(i);
							//ba.clear();
							return data;
						}
						else {

							//Log.d(TAG, String.format("mismatch msg size len:%d != %d type:%d", msg_len, ba_len, msg_type));
						}
					} else {

						//Log.d(TAG, "msg too short");
						ba.clear();
						rx_state = RX_ST_DATA;
						return null;
					}
					break;
				}

				/* default case: store the octet */
				ba.put(ch);

				break;

			case RX_ST_ESCAPE:

				/* transition back to normal DATA state */
				rx_state = RX_ST_DATA;

				/* store bif-5-inverted octet in buffer */
				ch |= (1 << 5);
				ba.put(ch);

				break;
		}

		return null;
	}

	public byte[] create_msg(byte[] data)
	{
		ByteBuffer ba = ByteBuffer.allocate(data.length * 2 + 4);
		ba.put((byte)HDLC_FD);
		int crc = 0;
		for (int i = 0; i < data.length; i++) {

			crc += data[i];
			if (data[i] == HDLC_FD || data[i] == HDLC_ESCAPE) {

				ba.put((byte)HDLC_ESCAPE);
				ba.put((byte)(data[i] & ~(1 << 5)));
			}
			else
				ba.put(data[i]);
		}

		crc ^= 0xff;
		crc &= 0xff;
		if (crc == HDLC_FD || crc == HDLC_ESCAPE) {

			ba.put((byte)HDLC_ESCAPE);
			ba.put((byte)(crc & ~(1 << 5)));
		}
		else
			ba.put((byte)crc);

		ba.put((byte)HDLC_FD);

		int len = ba.position();
		byte[] out = new byte[len];
		for (int i = 0; i < len; i++)
			out[i] = ba.get(i);

		return out;
	}
}

