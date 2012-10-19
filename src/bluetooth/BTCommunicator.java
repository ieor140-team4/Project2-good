package bluetooth;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

/**
 * A class to handle communication between the robot and the computer
 * 
 * @author raymondma
 *
 */
public class BTCommunicator {
	
   private BTConnection btc;
   private DataInputStream dis;
   private DataOutputStream dos;
   
   /**
    * Creates a BTCommunicator object and connects it to the computer,
    * then sets up the data streams and such.
    */
   public BTCommunicator() {
	   connect();
	   
	   OutputStream os = btc.openOutputStream();
       dos = new DataOutputStream(os);
       
       InputStream is = btc.openInputStream();
	   dis = new DataInputStream(is);
	   
	   System.out.println("Data stream opened.");
   }
   
   /**
    * Establishes a bluetooth connection with the computer.
    */
   public void connect() {
	   String waiting = "Waiting for connection...";
	   String connected = "Connected!";
	   
	   LCD.drawString(waiting,0,0);
       LCD.refresh();

       btc = Bluetooth.waitForConnection(); 

       LCD.clear();
       LCD.drawString(connected,0,0);
       LCD.refresh();	
   }
   
   /**
    * Sends a message to the computer specified by header, x, and y
    * 
    * @param header 0 if location info, 1 if obstacle info
    * @param x        the x coordinate of the information
    * @param y        the y coordinate of the information
    * @throws IOException
    */
   public void send(int header, int x, int y) throws IOException {  
	   // send point at or obstacles found
	   dos.writeInt(header);
	   dos.writeInt(x);
	   dos.writeInt(y);
	   //dos.write
       dos.flush(); 
   }
   
   /** 
    * Obtains a point from the computer
    * 
    * @return a point object corresponding to the x,y sent by the computer
    * @throws IOException
    */
   public Point receive() throws IOException {
	   
	   int x = dis.readInt();
       int y = dis.readInt();
       
       return new Point(x, y);
   }
   
   /**
    * Exits stuff.
    * 
    * @throws IOException
    */
   public void exit() throws IOException {
       dis.close();
       dos.close();
       btc.close();
   }
   
}


