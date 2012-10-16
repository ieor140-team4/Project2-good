package bluetooth;

import lejos.nxt.*;
import lejos.nxt.comm.*;
import java.io.*;

/**
 * taken from examples 
 * use with  BTSend on your PC
 * @author Roger Glassey
 */
public class BTReceive {

   public static void main(String [] args)  throws Exception 
   {
      String connected = "Connected";
      String waiting = "Waiting";

      while (true)
      {
         LCD.drawString(waiting,0,0);
         LCD.refresh();

         BTConnection btc = Bluetooth.waitForConnection(); // this method is very patient. 

         LCD.clear();
         LCD.drawString(connected,0,0);
         LCD.refresh();	

         InputStream is = btc.openInputStream();
         OutputStream os = btc.openOutputStream();
         DataInputStream dis = new DataInputStream(is);
         DataOutputStream dos = new DataOutputStream(os);
         int x = dis.readInt();
         int y = dis.readInt();
         
         System.out.println(x + " " + y);
         // LCD.drawInt(x,4,0,y%8);
         LCD.refresh();
         // dos.writeInt(-data[i]);
         // LCD.drawInt(i,0,14,0);

         dos.flush();// without this, no data is sent. 
         
         dis.close();
         dos.close();
         btc.close();
         Button.waitForAnyPress();
      }
   }
}


