package essentials;

import lejos.nxt.*;
import lejos.util.*;
import java.awt.*;

public class ButtonInputter {

	private int x, y, focus;
	// focus: x = 0, y = 1, save = 2
	
	public ButtonInputter() {
		focus = 0;
		x = 0;
		y = 0;
	}
	
	public Point display() {
		while(true){
			
			drawScreen();
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
					return savedPt;
				}
			}
		}
	}
	
	private void drawScreen() {
		LCD.drawString(" x= "+x+" ",1,1);
		LCD.drawString(" y= "+y+" ",1,2);
		LCD.drawString(" save", 1, 3);
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

	private Point save() {
		return new Point(x, y);
	}
}
