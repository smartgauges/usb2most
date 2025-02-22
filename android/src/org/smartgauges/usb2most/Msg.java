package org.smartgauges.usb2most;

import java.nio.ByteBuffer;
import java.util.Arrays;

class Msg
{
	enum e_msg_types
	{
		//bl 
		e_type_ping,
		e_type_pong,
		e_type_bl_status,
		e_type_erase,
		e_type_write,
		e_type_read,
		e_type_start,
		e_type_log,
		//app
		e_type_app_status,
		e_type_can,
		//10
		e_type_most_status,
		e_type_most_pwr_off,
		e_type_most_pwr_on,
		e_type_most_rst,
		e_type_most_reg,
		e_type_most_regs,
		e_type_most_ctrl,
		e_type_most_type,
		e_type_time,
		e_type_most_alloc,
		//20
		e_type_most_dealloc,
		e_type_conf,
		e_type_most_dump,
		e_type_inc_vol,
		e_type_dec_vol,
		e_type_set_vol,
		e_type_src,
		e_type_volume,
		e_type_tuner,
		e_type_tuner_rds,
		//30
		e_type_tuner_rdt,
		e_type_eq,
		e_type_can_iface,
		e_type_car_state,
		e_type_delay,
		e_type_crossover,
		e_type_btn,
		e_type_surround,
		e_type_balance,
		e_type_man_state,
		//40
		e_type_can_stat,
		e_type_unknown;

		public static e_msg_types convert(byte value)
		{
			return e_msg_types.values()[value];
		}
	};

	/*
	typedef struct msg_t
	{
		uint8_t len;
		uint8_t type;
		uint8_t data[0];
	} __attribute__ ((__packed__)) msg_t;
	*/
	static class msg_t
	{
		public int len;
		public e_msg_types type;
		public byte data[];
		public static int sizeof() { return 2; }

		static msg_t convert(byte[] _data)
		{
			msg_t m = new msg_t();
			m.len = _data[0] & 0xff;
			m.type = e_msg_types.convert(_data[1]);
			if (m.len > 2)
				m.data = Arrays.copyOfRange(_data, 2, 2 + (m.len - 2));
			return m;
		}
	};

	/*
	typedef struct msg_log_t
	{
		uint8_t len;
		uint8_t lvl;
		uint8_t data[0];
	} __attribute__ ((__packed__)) msg_log_t;
	*/
	static class msg_log_t
	{
		public int len;
		public byte lvl;
		public byte data[];

		static msg_log_t convert(byte[] _data)
		{
			msg_log_t m = new msg_log_t();
			m.len = _data[0] & 0xff;
			m.lvl = _data[1];
			if (_data.length >= 2)
				m.data = Arrays.copyOfRange(_data, 2, 2 + (_data.length - 2));
			if (m.data.length < m.len)
				m.len = m.data.length;

			return m;
		}
	};

	/*
	typedef struct msg_volume_t
	{
		uint8_t mute;
		uint8_t lvl;
		uint8_t max_lvl;
		int8_t fl;
		int8_t c;
		int8_t fr;
		int8_t rl;
		int8_t rr;
		int8_t sl;
		int8_t sr;
		int8_t sub;
	} __attribute__ ((__packed__)) msg_volume_t;
	*/
	static class msg_volume_t
	{
		public byte mute;
		public byte lvl;
		public byte max_lvl;
		public byte fl;
		public byte c;
		public byte fr;
		public byte rl;
		public byte rr;
		public byte sl;
		public byte sr;
		public byte sub;
		public static int sizeof() { return msg_t.sizeof() + 11; }

		static msg_volume_t convert(byte[] data)
		{
			msg_volume_t m = new msg_volume_t();
			m.mute = data[0];
			m.lvl = data[1];
			m.max_lvl = data[2];
			m.fl = data[3];
			m.c = data[4];
			m.fr = data[5];
			m.rl = data[6];
			m.rr = data[7];
			m.sl = data[8];
			m.sr = data[9];
			m.sub = data[10];
			return m;
		}
	};

	/*
	typedef struct msg_btn_t
	{
		uint8_t btn;
		uint8_t l;
	} __attribute__ ((__packed__)) msg_btn_t;
	*/
	static class msg_btn_t
	{
		enum e_btn_t
		{
			e_btn_pwr,
			e_btn_volume_inc,
			e_btn_volume_dec,
			e_btn_home,
			e_btn_nav,
			e_btn_phone,
			e_btn_media,
			e_btn_tune_inc,
			e_btn_tune_dec,
			e_btn_mode,
			e_btn_music,
			e_btn_prev,
			e_btn_next,
			e_btn_setup,
			e_btn_eject,
			e_btn_up,
			e_btn_down,
			e_btn_left,
			e_btn_right,
			e_btn_ok,
			e_btn_sw;

			public static e_btn_t convert(byte value)
			{
				return e_btn_t.values()[value];
			}
		};

		public e_btn_t btn;
		public byte l;
		public static int sizeof() { return msg_t.sizeof() + 2; }

		static msg_btn_t convert(byte[] data)
		{
			msg_btn_t m = new msg_btn_t();
			m.btn = e_btn_t.convert(data[0]);
			m.l = data[1];
			return m;
		}
	};

	/*
	typedef struct msg_src_t
	{
		uint8_t src;
		uint8_t station;
	} __attribute__ ((__packed__)) msg_src_t;
	*/
	static class msg_src_t
	{
		enum e_src_t
		{
			e_src_none,
			e_src_fm1,
			e_src_fm2,
			e_src_am,
			e_src_aux,
			e_src_cd,
			e_src_uac,
			e_src_toslink,
			e_src_bt,
			e_src_usb,
			e_src_nums;

			public static e_src_t convert(byte value)
			{
				return e_src_t.values()[value];
			}
		};

		public e_src_t src;
		public byte station;
		public static int sizeof() { return msg_t.sizeof() + 2; }

		static msg_src_t convert(byte[] data)
		{
			msg_src_t m = new msg_src_t();
			m.src = e_src_t.convert(data[0]);
			m.station = data[1];
			return m;
		}
	};

	/*
	typedef struct msg_tuner_rds_t
	{
		uint8_t name[9];
		uint8_t info;
		uint32_t freq;
		uint8_t pty;
		uint16_t pi;
		uint8_t tp;
	} __attribute__ ((__packed__)) msg_tuner_rds_t;
	*/
	static class msg_tuner_rds_t
	{
		public byte name[];
		public byte info;
		public long freq;
		public byte pty;
		public int pi;
		public byte tp;

		public static int sizeof() { return msg_t.sizeof() + 18; }

		static msg_tuner_rds_t convert(byte[] data)
		{
			msg_tuner_rds_t m = new msg_tuner_rds_t();
			m.name = Arrays.copyOfRange(data, 0, 9);
			m.info = data[14];
			//little endian
			m.freq = ((data[13] & 0xff) << 24 ) | ((data[12] & 0xff) << 16) | ((data[11] & 0xff) << 8) | (data[10] & 0xff);
			m.pty = data[14];
			//little endian
			m.pi = ((int)data[16] << 8) + data[15];
			m.tp = data[17];
			return m;
		}
	};

	enum e_sym_state_t
	{
		e_sym_state_off(0),
		e_sym_state_on(1),
		e_sym_state_flash(2),
		e_sym_state_undef(3),
		e_sym_state_bits(2);

		private int v;

		private e_sym_state_t(int v)
		{
			this.v = v;
		}

		public int value()
		{
			return v;
		}
	};

	enum e_sym_t
	{
		//0
		e_sym_left,
		e_sym_right,
		e_sym_hdc,
		e_sym_hdc_flash,
		//1
		e_sym_dsc,
		e_sym_dsc_off,
		e_sym_battery_1,
		e_sym_ebd,
		//2
		e_sym_eba,
		e_sym_low_break,
		e_sym_park_lights,
		e_sym_park_break,
		//3
		e_sym_low_wash,
		e_sym_fog,
		e_sym_rear_fog,
		e_sym_far,
		//4
		e_sym_near,
		e_sym_fl_door,
		e_sym_fr_door,
		e_sym_rl_door,
		//5
		e_sym_rr_door,
		e_sym_tailgate,
		e_sym_bonnet,
		e_sym_low_fuel,
		//6
		e_sym_coolant_high,
		e_sym_low_coolant,
		e_sym_abs,
		e_sym_test_airbag,
		//7
		e_sym_airbag,
		e_sym_belt,
		e_sym_belt1,
		e_sym_battery,
		//8
		e_sym_brake,
		e_sym_oil,
		e_sym_check_engine,
		e_sym_temp_engine,
		//9
		e_sym_immo,
		e_sym_cruise,
		e_sym_glow,
		e_sym_low_range,
		//10
		e_sym_hi_range,
		e_sym_trip,
		e_sym_afs,
		e_sym_tpms,
		//11
		e_sym_trailer,
		e_sym_heater,

		e_sym_nums,
	};

	/*
typedef struct msg_car_state_t
{
	//0
	uint8_t alarm;
	uint8_t key;
	uint8_t acc;
	uint8_t ign;
	uint8_t starter;
	uint8_t engine;
	uint16_t speed;
	uint16_t rpm;
	//10
	uint8_t radar[8];
	uint8_t selector;
	uint8_t illum;
	//20
	uint16_t terrain;
	uint8_t suspension;
	int8_t wheel;
	int8_t ext_temp;
	uint8_t in_light;
	uint8_t ext_light;

	//27
	uint8_t voltage;
	uint8_t fuel;
	int16_t coolant_temp;
	int16_t tcm_temp;
	int16_t pcm_temp;
	uint8_t pcm_load;
	uint16_t speed_fl;
	uint16_t speed_fr;
	uint16_t speed_rl;
	uint16_t speed_rr;

	//44
	uint8_t pressure_turbo;

	uint8_t mpg;
	uint8_t mpg_curr;

	uint32_t odo;

	//51
	uint8_t year;
	uint8_t month;
	uint8_t day;
	uint8_t hour;
	uint8_t min;
	uint8_t sec;

	//57
	uint8_t symbols[(e_sym_nums * e_sym_state_bits)/8 + 1];
} __attribute__((packed)) msg_car_state_t;
*/
	static class msg_car_state_t
	{
		public byte alarm;
		public byte key;
		public byte acc;
		public byte ign;
		public byte starter;
		public byte engine;
		public short speed;
		public short rpm;
		public byte radar[];
		public byte selector;
		public byte illum;
		public short terrain;
		public byte suspension;
		public byte wheel;
		public byte ext_temp;
		public byte in_light;
		public byte ext_light;

		public byte voltage;
		public byte fuel;
		public byte coolant_temp;
		public short tcm_temp;
		public short pcm_temp;
		public byte pcm_load;
		public short speed_fl;
		public short speed_fr;
		public short speed_rl;
		public short speed_rr;

		public byte pressure_turbo;

		public byte mpg;
		public byte mpg_curr;
		public int odo;

		public byte year;
		public byte month;
		public byte day;
		public byte hour;
		public byte min;
		public byte sec;

		public byte symbols[];

		public static int sizeof() { return msg_t.sizeof() + 57 + 11 + 1; }

		static msg_car_state_t convert(byte[] data)
		{
			msg_car_state_t m = new msg_car_state_t();

			m.alarm = data[0];
			m.key = data[1];
			m.acc = data[2];
			m.ign = data[3];
			m.starter = data[4];
			m.engine = data[5];
			//little endian
			m.speed = (short)(((short)data[7] << 8) + data[6]);
			//little endian
			m.rpm = (short)(((short)data[9] << 8) + data[8]);
			m.radar = Arrays.copyOfRange(data, 10, 10 + 8);

			m.symbols = Arrays.copyOfRange(data, 57, 57 + Msg.e_sym_t.e_sym_nums.ordinal()/4);
			return m;
		}

		static boolean sym_get(byte[] data, e_sym_t sym)
		{
			int byte_index = (sym.ordinal() * e_sym_state_t.e_sym_state_bits.value()) / 8;
			int sym_index = (sym.ordinal() * e_sym_state_t.e_sym_state_bits.value()) % 8;

			int b = data[byte_index];
			b = (b >> sym_index) & 0x3;

			return (b == 0x1);
		}
	};

	private float scale(float value, float in_min, float in_max, float out_min, float out_max)
	{
		return (((value - in_min) * (out_max - out_min)) / (in_max - in_min)) + out_min;
	}
}

