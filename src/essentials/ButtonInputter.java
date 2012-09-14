package essentials;

import lejos.nxt.*;
import lejos.util.*;
import java.awt.*;

public class ButtonInputter {

	private int x, y, focus;
	// focus: x = 0, y = 1, save = 2
	
	public ButtonInputter() {
		focus = 0;
		/* focus 0 = scrolling through x
		 * focus 1 = scrolling through y
		 * focus 2 = save
		 */
		x = 0;
		//ranges from 0 to 5
		y = 0;
		//ranges from 0 to 7
	}
	
	
	/* Takes in x and y so that it knows where the robot is currently.
	*  Then displays input that allows the user to input x and y coordinates
	*  that the robot will navigate to. This method then returns a Point object
	*  which corresponds to the Point the user entered for navigation.
	*/
	public Point display(int xPos, int yPos) {
		while(true){
			
			drawScreen(xPos,yPos);
			int id = Button.waitForAnyPress();
			
			LCD.drawString("Press! " + id, 1, 4);
			
			if(id == 4){
				scroll(true);
				Sound.playNote(Sound.PIANO, 262, 50);
			}else if(id == 2){
				scroll(false);
				Sound.playNote(Sound.PIANO, 294, 50);
			}else if(id == 8){
				if (focus < 2) {
					focus++;
				} else {
					focus = 0;
				}
				Sound.playNote(Sound.PIANO, 440, 50);
			}else if(id == 1){
				if(focus ==2){
					Point savedPt = save();
					Sound.playNote(Sound.PIANO, 523, 50);
					focus = 0;
					return savedPt;
				}
			}
		}
	}
	
	/* Displays the screen and menu according to which function
	 * is being focused on. First, a blank menu is printed and then
	 * arrows are overlaid on it that show which function the user
	 * is "looking" at.
	 */
	private void drawScreen(int xPos, int yPos) {
		LCD.clear();
		LCD.drawString(" x= "+x+" ",1,1);
		LCD.drawString(" y= "+y+" ",1,2);
		LCD.drawString(" save", 1, 3);
		LCD.drawString("Position: (" + xPos + "," + yPos + ")", 1,5);
		if (focus == 0) {
			LCD.drawString(">", 1, 1);
			LCD.drawString("<", 4, 1);
			LCD.drawString(">", 6, 1);
		} else if (focus == 1) {
			LCD.drawString(">", 1, 2);
			LCD.drawString("<", 4, 2);
			LCD.drawString(">", 6, 2);
		} else if (focus == 2) {
			LCD.drawString(">", 1, 3);
		}
		
	}
	
	
	/* This method changes x or y when the user increases or decreases
	*  them in the menu. Since we are working with a 6x8 grid, x ranges
	*  from 0 to 5 and y ranges from 0 to 7. If the number would go below 0,
	*  it wraps around to the maximum instead, and vice versa. The argument
	*  tells the method whether the user is increasing the number (right) or
	*  decreasing it (left).
	*/
	private void scroll(boolean isRight) {
		if(focus == 0){
			if(isRight){
				if (x < 5) {
					x++;
				} else {
					x = 0;
				}
			}else{
				if (x > 0) {
					x--;
				} else {
					x = 5;
				}
			}
		}else if(focus == 1){
			if(isRight){
				if (y < 7) {
					y++;
				} else {
					y = 0;
				}
			}else{
				if (y > 0) {
					y--;
				} else {
					y = 7;
				}
			}
		}
	}

	//Passes the currently inputted x and y into a Point object.
	private Point save() {
		return new Point(x, y);
	}
}
