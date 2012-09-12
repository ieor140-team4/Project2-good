package milestone3;

import essentials.*;
import java.awt.*;
import lejos.nxt.*;

public class InputTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ButtonInputter bi = new ButtonInputter();
		Point x = bi.display();
		System.out.println("Saved point: (" + x.x + "," +  x.y + ") !!!!");
		Button.waitForAnyPress();
	}

}
