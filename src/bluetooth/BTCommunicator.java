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

public class BTCommunicator {
	
   private BTConnection btc;
   private DataInputStream dis;
   private DataOutputStream dos;
   
   public BTCommunicator() {
	   connect();
	   
	   OutputStream os = btc.openOutputStream();
       dos = new DataOutputStream(os);
       
       InputStream is = btc.openInputStream();
	   dis = new DataInputStream(is);
	   
	   System.out.println("Data stream opened.");
   }
   
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
   
   public void open() {
	   OutputStream os = btc.openOutputStream();
       dos = new DataOutputStream(os);
       
       InputStream is = btc.openInputStream();
	   dis = new DataInputStream(is);
	   
	   System.out.println("Data stream opened.");
   }
   
   public void send() throws IOException {  
       //System.out.println(x + " " + y);

       dos.flush(); 
   }
   
   public Point receive() throws IOException {
	   
	   int x = dis.readInt();
       int y = dis.readInt();
       
       return new Point(x, y);
   }
   
   public void exit() throws IOException {
       dis.close();
       dos.close();
       btc.close();
   }
   
}


