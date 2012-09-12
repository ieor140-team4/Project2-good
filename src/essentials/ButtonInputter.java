package essentials;

import lejos.nxt.*;
import lejos.util.*;
import java.awt.*;

public class ButtonInputter {

	private int x, y, focus;
	// focus: x = 0, y = 1, save = 2
	
	void display() {
		while(Button.readButtons() != 0){
			if(Button.RIGHT.isDown()){
				scroll(true);
			}else if(Button.LEFT.isDown()){
				scroll(false);
			}else if(Button.ESCAPE.isDown()){
				// scroll focus
			}else if(Button.ENTER.isDown()){
				if(focus ==2){
					save();
					return;
				}
			}else{
				return;
			}
			
			drawScreen();
		}
		
	}
	
	private void drawScreen() {
		LCD.drawString(" x= "+x+" ",1,1);
		LCD.drawString(" y= "+y+" ",1,2);
		LCD.drawString(" save", 1, 3);
		if (focus == 1) {
			LCD.drawString(">", 1, 1);
			LCD.drawString("<", 1, 4);
			LCD.drawString(">", 1, 6);
		} else if (focus == 2) {
			LCD.drawString(">", 2, 1);
			LCD.drawString("<", 2, 4);
			LCD.drawString(">", x, y)
		}
	}
	
	void scroll(boolean isRight) {
		if(focus == 0){
			if(isRight){
				// scroll up
			}else{
				// scroll down
			}
		}else if(focus == 1){
			if(isRight){
				// scroll up
			}else{
				// scroll down
			}
		}
	}

	Point save() {
		return new Point(x, y);
	}
}
