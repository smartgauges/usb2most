#ifndef MSG_H
#define MSG_H

#include <inttypes.h>

#define HDLC_FD      0x7E
#define HDLC_ESCAPE  0x7D

enum e_log_level
{
	e_log_warn = 0,
	e_log_info = 1,
	e_log_debug = 2,
	e_log_all
};

#pragma pack(1)
typedef struct msg_t
{
	uint8_t len;
	uint8_t type;
	uint8_t data[0];
} __attribute__ ((__packed__)) msg_t;

typedef struct msg_bl_status_t
{
	uint8_t version;
	uint32_t uptime;
} __attribute__ ((__packed__)) msg_bl_status_t;

typedef struct msg_flash_t
{
	uint32_t addr;
	uint8_t num;
	uint16_t data[0];
} __attribute__ ((__packed__)) msg_flash_t;

typedef struct msg_log_t
{
	uint8_t len;
	uint8_t lvl;
	uint8_t data[0];
} __attribute__ ((__packed__)) msg_log_t;

#ifndef BL
typedef struct cpu_stat_t
{
	uint32_t all;
	uint32_t usb;
	uint32_t most;
	uint32_t com;
	uint32_t idle;
} __attribute__ ((__packed__)) cpu_stat_t;

typedef struct msg_app_status_t
{
	uint8_t version;
	uint32_t usb_cnt_fn_err;
	uint32_t usb_cnt_overwrite;
	uint32_t usb_cnt_iso;
	uint32_t usb_cnt_iso_fn_err;
	uint32_t usb_cnt_iisoixfer;
	uint32_t usb_cnt_iisooxfer;

	uint32_t can1_msgs;
	uint32_t can2_msgs;
	uint32_t can3_msgs;
	uint32_t tick_cnt;
	uint32_t wakeup_cnt;
	uint32_t uptime;
	uint16_t swc_mode;
	int16_t temp;
	cpu_stat_t stat;
} __attribute__ ((__packed__)) msg_app_status_t;

enum e_can_types
{
	e_can_simple = 0x0,
	e_can_statistic = 0x1,
	e_can_odd = 0x2,
	e_can_ext = 0x40,
	e_can_rtr = 0x80,
};

typedef struct msg_can_t
{
	uint32_t id;
	uint32_t num;
	uint8_t type;
	uint8_t len;
	uint8_t data[8];
} __attribute__ ((__packed__)) msg_can_t;

enum e_car_t
{
	e_manual = 0, 
	e_lr2_2007my,
	e_lr2_i6_2007my,
	e_lr2_2013my,
	e_lr3_2006my,
	e_lr4_2010my,
	e_rrs_2006my,
	e_rrs_2010my,
	e_rr_2006my,
	e_rr_2010my,
	e_rr_2013my,
	e_xj_2010my,
	e_car_nums,
};

enum e_most_t
{
	e_most_hmi,
	e_most_master,
	e_most_amp,
	e_most_tuner,
	e_most_proxy_amp,
	e_most_ext_hmi,
	e_most_ext_master,
	e_most_ext_amp,
	e_most_ext_tuner,
	e_most_off,
};

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
	e_src_nums
};

enum e_amp_t
{
	e_amp_none,
	e_amp_lr2_hi,
	e_amp_lr2_premium,
	e_amp_aud3_8,
	e_amp_aud3_12,
	e_amp_aud3_16,
	e_amp_lr3_hi,
	e_amp_lr3_premium,
	e_amp_nums
};

enum e_tuner_t
{
	e_tuner_none,
	e_tuner_lr2,
	e_tuner_iam21_1,
	e_tuner_iam21_3,
	e_tuner_nums
};

enum e_surround_t
{
	e_surround_2ch,
	e_surround_3ch,
	e_surround_dpl2,
	e_surround_neo6,
	e_surround_meridian,
	e_surround_nums
};

typedef struct msg_most_status_t
{
	uint8_t type;
	uint8_t state;
	uint32_t version;
	uint8_t in;

	uint8_t msgs;
	uint8_t xsr;
	uint8_t cm2;
	uint8_t cm3;
	uint8_t xts;
	uint8_t mpr;
	uint8_t npr;

	uint32_t rst_cnt;
	uint32_t err_cnt;
	uint32_t alc_cnt;
	uint32_t i2c_err_rst;
	uint32_t i2c_err;
	uint32_t i2c_ok;
	uint32_t tx_cnt;
	uint32_t err_tx_cnt;
	uint32_t rx_cnt;
	uint32_t isr_cnt;
} __attribute__ ((__packed__)) msg_most_status_t;

typedef struct msg_most_type_t
{
	uint8_t type;
} __attribute__ ((__packed__)) msg_most_type_t;

typedef struct msg_most_reg_t
{
	uint16_t addr;
	uint16_t reg;
	uint8_t value;
} __attribute__ ((__packed__)) msg_most_reg_t;

typedef struct msg_most_regs_t
{
	uint16_t addr;
	uint16_t reg;
	uint8_t values[8];
} __attribute__ ((__packed__)) msg_most_regs_t;

typedef struct msg_most_ctrl_t
{
	uint8_t prio;
	uint8_t type;
	uint8_t addrh;
	uint8_t addrl;
	uint8_t data[17];
} __attribute__ ((__packed__)) msg_most_ctrl_t;

typedef struct msg_time_t
{
	uint8_t id;
	uint8_t year;
	uint8_t month;
	uint8_t day;
	uint8_t hour;
	uint8_t min;
	uint8_t sec;
} __attribute__ ((__packed__)) msg_time_t;

typedef struct msg_most_alloc_t
{
	uint8_t num;
} __attribute__ ((__packed__)) msg_most_alloc_t;

typedef struct msg_set_vol_t
{
	uint8_t lvl;
} __attribute__ ((__packed__)) msg_set_vol_t;

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

typedef struct msg_delay_t
{
	uint8_t req;
	uint16_t fl;
	uint16_t c;
	uint16_t fr;
	uint16_t rl;
	uint16_t rr;
	uint16_t sl;
	uint16_t sr;
	uint16_t sub;
} __attribute__ ((__packed__)) msg_delay_t;

typedef struct crossover_t
{
	uint16_t lo_freq;
	uint16_t hi_freq;
	uint8_t lo_order;
	uint8_t hi_order;
} __attribute__((packed)) crossover_t;

typedef struct msg_crossover_t
{
	uint8_t idx;
	crossover_t cross;
} __attribute__ ((__packed__)) msg_crossover_t;

typedef struct msg_src_t
{
	uint8_t src;
	uint8_t station;
} __attribute__ ((__packed__)) msg_src_t;

typedef struct msg_tuner_t
{
	uint8_t bseek;
	uint8_t prev;
	uint8_t bstep;
	uint8_t fstep;
	uint8_t next;
	uint8_t fseek;
	uint8_t station;
} __attribute__ ((__packed__)) msg_tuner_t;

typedef struct msg_tuner_rds_t
{
	uint8_t name[9];
	uint8_t info;
	uint32_t freq;
	uint8_t pty;
	uint16_t pi;
	uint8_t tp;
} __attribute__ ((__packed__)) msg_tuner_rds_t;

typedef struct msg_tuner_rdt_t
{
	uint8_t text[64];
} __attribute__ ((__packed__)) msg_tuner_rdt_t;

typedef struct eq_t
{
	int8_t gain;
	uint16_t freq;
	uint8_t q;
} __attribute__((packed)) eq_t;

typedef struct msg_eq_t
{
	uint8_t num;
	uint8_t idx;
	struct eq_t eq;
} __attribute__((packed)) msg_eq_t;

enum e_can_ifaces
{
	e_can_off,
	e_can_ms,
	e_can_hs,
	e_can_key,
	e_can_spi,
	e_can_nums,
};

typedef struct msg_can_iface_t
{
	uint8_t iface;
} __attribute__((packed)) msg_can_iface_t;

enum e_selector_t
{
	e_selector_p = 0x00,
	e_selector_r = 0x01,
	e_selector_n = 0x02,
	e_selector_d = 0x03,
	e_selector_s = 0x04,
	e_selector_m = 0x08,
	e_selector_1 = 0x10,
	e_selector_2 = 0x20,
	e_selector_3 = 0x30,
	e_selector_4 = 0x40,
	e_selector_5 = 0x50,
	e_selector_6 = 0x60,
	e_selector_7 = 0x70,
	e_selector_8 = 0x80,
	e_selector_9 = 0x90,
};

enum e_terrain_t
{
	e_terrain = 0x00,

	e_terrain_general = 0x01,
	e_terrain_snow = 0x02,
	e_terrain_mud = 0x03,
	e_terrain_sand = 0x04,
	e_terrain_rock = 0x05,

	e_terrain_preselect = 0x08,
	e_terrain_select = 0x10,
	e_terrain_notavailable = 0x80,
	e_terrain_ledproblem = 0xa8,

	e_terrain_sel_general = (e_terrain_general << 8),
	e_terrain_sel_snow = (e_terrain_snow << 8),
	e_terrain_sel_mud = (e_terrain_mud << 8),
	e_terrain_sel_sand = (e_terrain_sand << 8),
	e_terrain_sel_rock = (e_terrain_rock << 8),

	e_terrain_undef = 0xffff,
};

enum e_suspension_t
{
	e_suspension = 0x00,
	e_suspension_crawl = 0x01,
	e_suspension_access = 0x02,
	e_suspension_onroad = 0x03,
	e_suspension_normal = 0x04,
	e_suspension_offroad = 0x05,
	e_suspension_extend = 0x06,
	e_suspension_highspeed = 0x07,
};

enum e_sym_state_t
{
	e_sym_state_off = 0,
	e_sym_state_on = 1,
	e_sym_state_flash = 2,
	e_sym_state_undef = 3,
	e_sym_state_bits = 2,
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

#define STATE_UNDEF 0xff
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

enum e_btn_t
{
	e_btn_pwr = 0,
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
	e_btn_sw,
};

typedef struct msg_btn_t
{
	uint8_t btn;
	uint8_t l;
} __attribute__ ((__packed__)) msg_btn_t;

enum e_most_wakeup_t
{
	e_most_wakeup_pwr = 0,
	e_most_wakeup_key,
	e_most_wakeup_acc,
};

enum e_canbox_t
{
	e_cb_none = 0, 
	e_cb_raise_vw_pq,
	e_cb_raise_vw_mqb,
	e_cb_od_bmw_nbt_evo,
	e_cb_hiworld_vw_mqb,
	e_cb_nums,
};

typedef struct msg_conf_t
{
	uint8_t req;
	uint8_t car_in;
	uint8_t car_out;
	uint8_t use_sleep;
	uint8_t ignore_uac_ctrls;
	uint8_t wait_all_devs;
	uint8_t most_type;
	uint8_t amp_type;
	uint8_t amp_proxy;
	uint8_t tuner_type;
	uint8_t button_type;
	uint8_t clock_type;
	uint8_t wb_type;
	uint8_t most_wakeup;
	uint8_t music_btn;
	uint8_t canbox;
	uint16_t mode_sources;
} __attribute__ ((__packed__)) msg_conf_t;

typedef struct msg_surround_t
{
	uint8_t req;
	uint8_t type;
} __attribute__ ((__packed__)) msg_surround_t;

typedef struct msg_balance_t
{
	uint8_t req;
	int8_t center;
	int8_t balance;
	int8_t fader;
	int8_t surround;
	int8_t bass;
	int8_t treble;
	int8_t sub;
} __attribute__ ((__packed__)) msg_balance_t;

typedef struct can_stat_t
{
	uint32_t irq_rx_cnt;
	uint32_t irq_tx_cnt;
	uint32_t rx_cnt;
	uint32_t tx_cnt;
	uint32_t mcr;
	uint32_t msr;
	uint32_t tsr;
	uint32_t rf0r;
	uint32_t rf1r;
	uint32_t esr;
} __attribute__ ((__packed__)) can_stat_t;

typedef struct msg_can_stat_t
{
	struct can_stat_t stat[4];
} __attribute__((packed)) msg_can_stat_t;

#endif
#pragma pack()

enum e_msg_types
{
	//bl 
	e_type_ping = 0,
	e_type_pong = 1,
	e_type_bl_status = 2,
	e_type_erase = 3,
	e_type_write = 4,
	e_type_read = 5,
	e_type_start = 6,
	e_type_log = 7,
	e_type_bl_end = e_type_log,
#ifndef BL
	//app
	e_type_app_status = 8,
	e_type_can = 9,
	e_type_most_status = 10,
	e_type_most_pwr_off = 11,
	e_type_most_pwr_on = 12,
	e_type_most_rst = 13,
	e_type_most_reg = 14,
	e_type_most_regs = 15,
	e_type_most_ctrl = 16,
	e_type_most_type = 17,
	e_type_time = 18,
	e_type_most_alloc = 19,
	e_type_most_dealloc = 20,
	e_type_conf = 21,
	e_type_most_dump = 22,
	e_type_inc_vol = 23,
	e_type_dec_vol = 24,
	e_type_set_vol = 25,
	e_type_src = 26,
	e_type_volume = 27,
	e_type_tuner = 28,
	e_type_tuner_rds = 29,
	e_type_tuner_rdt = 30,
	e_type_eq = 31,
	e_type_can_iface = 32,
	e_type_car_state = 33,
	e_type_delay = 34,
	e_type_crossover = 35,
	e_type_btn = 36,
	e_type_surround = 37,
	e_type_balance = 38,
	e_type_man_state = 39,
	e_type_can_stat = 40,
	e_type_unknown,
#endif
};

#endif

