package milestone1;

import lejos.nxt.*;
import lejos.util.*;
import lejos.robotics.navigation.*;

public class Tracker {
	
	private DifferentialPilot dp;
	private LightSensor leftEye;
	private LightSensor rightEye;
	private double sensorToAxleLength;
	
	public Tracker(DifferentialPilot pilot, LightSensor leftLS, LightSensor rightLS, double length) {
		dp = pilot;
		leftEye = leftLS;
		rightEye = rightLS;
		
		dp.setTravelSpeed(15);
		dp.setRotateSpeed(360);
		dp.setAcceleration(2000);
		
		sensorToAxleLength = length;
	}
	
	private int diffValues() {
		int leftval = leftEye.readValue();
		int rightval = rightEye.readValue();
		
		int diff = leftval - rightval; //Subtracts left from right: negative value means
		//we are right of the line, positive value means we are left of the line.
		
		return diff;
	}
	
	private int sumValues() {
		int leftval = leftEye.readValue();
		int rightval = rightEye.readValue();
		
		return leftval + rightval;
	}
	
	public int minValues() {
		if (leftEye.readValue() > rightEye.readValue()) {
			return rightEye.readValue();
		} else {
			return leftEye.readValue();
		}
	}
	
	public void trackLine() {
		
		boolean rightOfLine = false;
		
		float gainConstant = 1.0f;
		
		while (minValues() > -15) {
			dp.steer(gainConstant * -1 * diffValues());
			LCD.drawString("" + rightOfLine, 0, 0);
			LCD.drawInt(leftEye.readValue(), 0, 1);
			LCD.drawInt(rightEye.readValue(), 6, 1);
			LCD.drawInt(diffValues(), 0, 2);
			LCD.drawInt(sumValues(), 6, 2);
		}
		
		
		/*
		while (sumValues() > -40) {
			if ((sumValues() > 100) && (sumValues() < 150) && (diffValues() < 10)) {
				dp.steer(0);
			} else if (diffValues() >= 10) {
				dp.steer(-10 * diffValues());
				rightOfLine = diffValues() < 0; //because negative difference means we are right of line
			} else if (sumValues() >= 150) {
				if (rightOfLine) {
					dp.steer(50);
				} else {
					dp.steer(-50);
				}
			}*/
			
		//}
	}
	
	   public void myCalibrate()
	   {
		   
		   Delay.msDelay(3000);

		   leftEye.calibrateLow();
	       rightEye.calibrateLow();
	       Sound.playTone(1000 + 200, 100);
	       System.out.println("LOW: " + leftEye.getLow() + " " + rightEye.getLow());
	       
	       Delay.msDelay(2000);
	       
	       leftEye.calibrateHigh();
	       rightEye.calibrateHigh();
	       Sound.playTone(1000 + 200, 100);
	       System.out.println("HIGH: " + leftEye.getHigh() + " " + rightEye.getHigh());
	       
	   }
	   

}
