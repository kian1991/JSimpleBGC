package serial;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;

import com.fazecast.jSerialComm.SerialPort;

/**
 * Diese Klasse repräsentiert die Verbindung zum Gimbal und ist für das Versenden, Packen, und Entpacken von Commands und Responses 
 * zuständig
 * 
 * !Sie wird als Singleton implementiert, da es nur eine Verbindung aufeinmal geben kann!
 * 
 * @author Kian Lütke
 *
 */

public class Com {

	//Constants
	private SerialPort comPort = null;
	private OutputStream out = null;
	private InputStream in = null;
	
	//SINGLETON IMPLEMENTIERUNG
	public static Com instance = null;
	
	public static Com getInstance(){
		if(instance == null){
			instance = new Com(1);
		}
		return instance;
	}
	
	
	
	/**
	 * Konstruktor für die connection
	 */
	private Com(int x) throws ArrayIndexOutOfBoundsException {
		comPort = SerialPort.getCommPorts()[x];//x TODO
		comPort.openPort();
		comPort.setBaudRate(115200);
		comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);

		in = comPort.getInputStream();
	}

	public void close(){
		comPort.closePort();
	}


	/**
	 * Diese funktion wird zum senden von commands an den gimbal verwendet, sie gibt ein Antwort ByteArray zurück
	 * @param cmd command übergeben (siehe API)
	 * @param data Datanpaket dranhängen
	 * @return byteArray welches vom gimbal kommt
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public byte[] sendCMD(int cmd, byte[] data) throws IOException, InterruptedException{
		byte[] response = null; //Antwort vom gimbal
		//Anlegen der Checksumme
		int checksum = 0;
		//DataSize ist die Länge des array(für berechnung der Checksumme)
		int dataSize =  data.length;

		out = comPort.getOutputStream();

		//Header
		out.write('>');//char initiiert die communikation mit dem gimbal
		out.write(cmd);//byte
		out.write(dataSize);//byte
		out.write(cmd+dataSize%256);//checksum berechnung
		//Body
		for(int i = 0; i < dataSize; i++){
			checksum += data[i];
			out.write((data)[i]);
		}
		out.write(checksum%256);

		Thread.sleep(200); //200ms warten
		response = new byte[comPort.bytesAvailable()];//Array die größe der verfügbaren bytes geben

		comPort.readBytes(response, comPort.bytesAvailable());

		return response;

	}
	
	public double getBatteryV() throws IOException, InterruptedException{
		byte[] response = this.sendCMD(API.CMD_REALTIME_DATA_3, API.noData); //Command ohne Daten absetzen(benötigt keine Daten)
		byte a = response[59]; //in diesen beiden bytes stehen die Information der aktuellen spannung
		byte b = response[60];
		int c = API.convertTwoBytesToInt(a, b);
		System.out.println("Battery: " + c * 0.01 + "V");
		return  c * 0.01;
	}
	
	


}
