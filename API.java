package serial;

import java.io.IOException;
import java.lang.reflect.Constructor;

public class API {
	
	//Leeres byteArray für commands ohne daten
	public final static byte[] noData = {};

	//Commands
	public final static byte  CMD_READ_PARAMS = 82;
	public final static byte  CMD_WRITE_PARAMS = 87;
	public final static byte  CMD_REALTIME_DATA = 68;
	public final static byte  CMD_BOARD_INFO = 86;
	public final static byte  CMD_CALIB_ACC = 65;
	public final static byte  CMD_CALIB_GYR = 103;
	public final static byte  CMD_CALIB_EXT_GAIN = 71;
	public final static byte  CMD_USE_DEFAULTS = 70;
	public final static byte  CMD_CALIB_POLES = 80;
	public final static byte  CMD_RESET = 114;
	public final static byte  CMD_HELPER_DATA = 72;
	public final static byte  CMD_CALIB_OFFSET = 79;
	public final static byte  CMD_CALIB_BAT  = 66;
	public final static byte  CMD_MOTORS_ON = 77;
	public final static byte  CMD_MOTORS_OFF = 109;
	public final static byte  CMD_CONTROL = 67;
	public final static byte  CMD_TRIGGER_PIN = 84;
	public final static byte  CMD_EXECUTE_MENU = 69;
	public final static byte  CMD_GET_ANGLES = 73;
	public final static byte  CMD_CONFIRM = 67;
	// Board v3.x only
	public final static byte  CMD_BOARD_INFO_3 = 20;
	public final static byte  CMD_READ_PARAMS_3 = 21;
	public final static byte  CMD_WRITE_PARAMS_3 = 22;
	public final static byte  CMD_REALTIME_DATA_3 = 23;
	public final static byte  CMD_REALTIME_DATA_4 = 25;
	public final static byte  CMD_SELECT_IMU_3 = 24;
	public final static byte  CMD_READ_PROFILE_NAMES = 28;
	public final static byte  CMD_WRITE_PROFILE_NAMES = 29;
	public final static byte  CMD_QUEUE_PARAMS_INFO_3 = 30;
	public final static byte  CMD_SET_ADJ_VARS_VAL = 31;
	public final static byte  CMD_SAVE_PARAMS_3 = 32;
	public final static byte  CMD_READ_PARAMS_EXT = 33;
	public final static byte  CMD_WRITE_PARAMS_EXT = 34;
	public final static byte  CMD_AUTO_PID = 35;
	public final static byte  CMD_SERVO_OUT = 36;
	public final static byte  CMD_I2C_WRITE_REG_BUF = 39;
	public final static byte  CMD_I2C_READ_REG_BUF = 40;
	public final static byte  CMD_WRITE_EXTERNAL_DATA = 41;
	public final static byte  CMD_READ_EXTERNAL_DATA = 42;
	public final static byte  CMD_READ_ADJ_VARS_CFG = 43;
	public final static byte  CMD_WRITE_ADJ_VARS_CFG = 44;
	public final static byte  CMD_API_VIRT_CH_CONTROL = 45;
	public final static byte  CMD_ADJ_VARS_STATE = 46;
	public final static byte  CMD_EEPROM_WRITE = 47;
	public final static byte  CMD_EEPROM_READ = 48;
	public final static byte  CMD_CALIB_INFO = 49;
	public final static byte  CMD_BOOT_MODE_3 = 51;
	public final static byte  CMD_SYSTEM_STATE = 52;
	public final static byte  CMD_READ_FILE = 53;
	public final static byte  CMD_WRITE_FILE = 54;
	public final static byte  CMD_FS_CLEAR_ALL = 55;
	public final static byte  CMD_AHRS_HELPER = 56;
	public final static byte  CMD_RUN_SCRIPT = 57;
	public final static byte  CMD_SCRIPT_DEBUG = 58;
	public final static byte  CMD_CALIB_MAG = 59;
	public final static byte  CMD_GET_ANGLES_EXT = 61;
	public final static byte  CMD_READ_PARAMS_EXT2 = 62;
	public final static byte  CMD_WRITE_PARAMS_EXT2 = 63;
	public final static byte  CMD_GET_ADJ_VARS_VAL = 64;
	public final static byte  CMD_CALIB_MOTOR_MAG_LINK = 74;
	public final static byte  CMD_GYRO_CORRECTION = 75;
	public final static byte  CMD_DATA_STREAM_INTERVAL = 85;
	public final static byte  CMD_REALTIME_DATA_CUSTOM = 88;
	public final static byte  CMD_BEEP_SOUND = 89;
	public final static byte  CMD_ENCODERS_CALIB_OFFSET_4 = 26;
	public final static byte  CMD_ENCODERS_CALIB_FLD_OFFSET_4 = 27;
	public final static byte  CMD_MAVLINK_INFO = (byte) 250;
	public final static byte  CMD_MAVLINK_DEBUG = (byte) 251;
	public final static byte  CMD_DEBUG_VARS_INFO_3 = (byte) 253;
	public final static byte  CMD_DEBUG_VARS_3 = (byte) 254;
	public final static byte  CMD_ERROR = (byte) 255;
	
	// Control modes für steuer cmds
	public final static int SBGC_CONTROL_MODE_NO = 0;
	public final static int SBGC_CONTROL_MODE_SPEED = 1;
	public final static int SBGC_CONTROL_MODE_ANGLE = 2;
	public final static int SBGC_CONTROL_MODE_SPEED_ANGLE = 3;
	public final static int SBGC_CONTROL_MODE_RC = 4;
	public final static int SBGC_CONTROL_MODE_ANGLE_REL_FRAME = 5;


	//Controls
	////////////////Einheiten Umrechnung/////////////////
	public final static int SBGC_ANGLE_FULL_TURN = 16384;
	//Conversion von grad/sek zu ausdrücken, die der gimbal versteht
	public final static float SBGC_SPEED_SCALE =  1.0f/0.1220740379f;
	public final static float SBGC_DEGREE_ANGLE_SCALE = ((float)SBGC_ANGLE_FULL_TURN/360.0f);
	public final static float SBGC_ANGLE_DEGREE_SCALE = (360.0f/(float)SBGC_ANGLE_FULL_TURN);
	
	public final static int ROLL = 0;
	public final static int PITCH = 1;
	public final static int YAW = 2;


	//Conversions for angle in degrees to angle in SBGC 14bit representation, and back
	public static float SBGC_DEGREE_TO_ANGLE(float val){
		return val*SBGC_DEGREE_ANGLE_SCALE;
	}
	
	public static float SBGC_ANGLE_TO_DEGREE(float val){
		return val*SBGC_ANGLE_DEGREE_SCALE;
	}
	

	//The same, optimized for integers
	public static int SBGC_DEGREE_TO_ANGLE_INT(int val){
		return ((int)val*SBGC_ANGLE_FULL_TURN/360);
	}
	
	public static int SBGC_DEGREE_01_TO_ANGLE_INT(int val){
		return ((int)val*SBGC_ANGLE_FULL_TURN/3600);
	}
	
	public static int SBGC_ANGLE_TO_DEGREE_INT(int val){
		return ((int)val*360/SBGC_ANGLE_FULL_TURN);
	}
	
	public static int SBGC_ANGLE_TO_DEGREE_01_INT(int val){
		return ((int)val*3600/SBGC_ANGLE_FULL_TURN);
	}
	
	/*
	 * Byte zu Integer umwandlungen, da es in Java kein signed und unsigned gibt...
	 * 
	 */
	public static byte[] intTo2signedByte(int val){
		byte[] returnvalue = new byte[2];
		returnvalue[0] = (byte) (val & 0xFF); 
		returnvalue[1] = (byte)	((val >> 8) & 0xFF);	
		return returnvalue;
	}
	
	public static int convertTwoBytesToInt(byte b1, byte b2) {
	    return (int) ((b2 << 8) | (b1 & 0xFF));
	}
	
}


	

