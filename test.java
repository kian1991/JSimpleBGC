package serial;

import java.io.IOException;


/**
 * 1u – 1 byte unsigned
• 1s – 1 byte signed
• 2u – 2 byte unsigned (little-endian order)
• 2s – 2 byte signed (little-endian order)
• 4f – float (IEEE-754 standard)
• 4s – 4 bytes signed (little-endian order)
• string – ASCII character array, first byte is array size
• Nb – byte array size N
 * @author Kian_
 *	522 0.04
 *	-70.42 0.00
 *	-102.59
 */
public class test {
	
	public static Com com = Com.getInstance();
	

	public static void main(String[] args) throws InterruptedException, IOException {
		
		
		Com.getInstance().sendCMD(API.CMD_MOTORS_ON, API.noData);
		ControlPack c = new ControlPack();

		
		c.pack();
		c.send();

		Thread.sleep(3000);
		
		Com.getInstance().sendCMD(API.CMD_MOTORS_OFF, API.noData);
		Com.getInstance().close();

		
		
		
		
	}
	

}
