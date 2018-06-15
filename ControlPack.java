package serial;

import java.io.IOException;

/**
 * Diese Klasse stellt ein Control Objekt da, in ihr kann man die parameter zur steuerung übergeben 
 * und in ein für den gimbal verständliches byte array packen.
 * @author Kian_
 *
 */
public class ControlPack {

	private int modeR;
	private int modeP;
	private int modeY;
	private int speedROLL;
	private int angleROLL;
	private int speedPITCH;
	private int anglePITCH;
	private int speedYAW;
	private int angleYAW;

	
	byte[] pack = null;



	public ControlPack(int modeR, int modeP, int modeY, int speedROLL, int angleROLL, int speedPITCH, int anglePITCH,
			int speedYAW, int angleYAW) {
		this.modeR = modeR;
		this.modeP = modeP;
		this.modeY = modeY;
		this.speedROLL = speedROLL;
		this.angleROLL = angleROLL;
		this.speedPITCH = speedPITCH;
		this.anglePITCH = anglePITCH;
		this.speedYAW = speedYAW;
		this.angleYAW = angleYAW;
	}
	
	/**
	 * Constructor um mit Standardwerten zu initialisieren 
	 * Zudem ist der Control Mode auf Angle gestellt und
	 * speed voreingestellt
	 */
	public ControlPack(){
		modeP = modeR = modeY = API.SBGC_CONTROL_MODE_ANGLE;
		speedROLL = speedPITCH = speedYAW = (int) (30 * API.SBGC_SPEED_SCALE);
		this.angleROLL = API.SBGC_DEGREE_TO_ANGLE_INT(0);
		this.anglePITCH = API.SBGC_DEGREE_TO_ANGLE_INT(0);
		this.angleYAW = API.SBGC_DEGREE_TO_ANGLE_INT(0);
	}



	public byte[] pack(){
		byte[] pack = new byte[15];
		byte[] aux = null; //zwischenspeicher für umwandlung
		//Modi für die achsen verpacken
		pack[0] = (byte) modeR;
		pack[1] = (byte) modeP;
		pack[2] = (byte) modeY;
		//umwandlung der int werte in je zwei bytes 
		aux = API.intTo2signedByte(speedROLL);
		pack[3] = aux [0];
		pack[4] = aux [1];
		aux = API.intTo2signedByte(speedPITCH);
		pack[5] = aux [0];
		pack[6] = aux [1];
		aux = API.intTo2signedByte(speedYAW);
		pack[7] = aux [0];
		pack[8] = aux [1];
		aux = API.intTo2signedByte(angleROLL);
		pack[9] = aux [0];
		pack[10] = aux [1];
		aux = API.intTo2signedByte(anglePITCH);
		pack[11] = aux [0];
		pack[12] = aux [1];
		aux = API.intTo2signedByte(angleYAW);
		pack[13] = aux [0];
		pack[14] = aux [1];

		this.pack = pack;
		return pack;
	}

	public void send() throws IOException, InterruptedException{
		Com.getInstance().sendCMD(API.CMD_CONTROL, this.pack);		
	}
	
	/**
	 * Getter und Setter sektion
	 */
	
	public void setModeALL(int modeAll){
		this.modeP = modeAll;
		this.modeR = modeAll;
		this.modeY = modeAll;
	}

	public void setModeR(int modeR) {
		this.modeR = modeR;
	}

	public void setModeP(int modeP) {
		this.modeP = modeP;
	}

	public void setModeY(int modeY) {
		this.modeY = modeY;
	}

	public void setSpeedROLL(int speedROLL) {
		this.speedROLL = speedROLL;
	}

	public void setAngleROLL(int angleROLL) {
		this.angleROLL = angleROLL;
	}

	public void setSpeedPITCH(int speedPITCH) {
		this.speedPITCH = speedPITCH;
	}

	public void setAnglePITCH(int anglePITCH) {
		this.anglePITCH = anglePITCH;
	}

	public void setSpeedYAW(int speedYAW) {
		this.speedYAW = speedYAW;
	}

	public void setAngleYAW(int angleYAW) {
		this.angleYAW = angleYAW;
	}


}
