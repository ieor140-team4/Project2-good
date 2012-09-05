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
		dp.setRotateSpeed(180);
		dp.setAcceleration(80);
		
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
	
	
	private boolean isBlue() {
		return (!isWhite() && !isBlack());
	}
	
	private boolean isWhite() {
		
		boolean sumIsHigh = sumValues() > 160;
		boolean differenceIsHigh = (Math.abs(sumValues()) > 50);
		
		return (sumIsHigh || differenceIsHigh);
	}
	
	private boolean isBlack() {
		return sumValues() < 0;
	}
	
	private void findLine() {
		//rotate a little bit left and a little bit right to find the line
	}
	
	public void trackLine() {
		while (!isBlack()) {

	          LCD.drawInt(leftEye.readValue(), 4, 6, 1);
	          LCD.drawInt(rightEye.readValue(), 4, 12, 2);
			
			if (isWhite()) {
				dp.steer(-15*diffValues());
			} else if (isBlue()){
				dp.steer(-2 * diffValues());
			}
		}
		
	}
	
	   public void myCalibrate()
	   {
		   Delay.msDelay(10000);
		   
		   leftEye.calibrateLow();
	       rightEye.calibrateLow();
	       Sound.playTone(1000 + 200, 100);
	       System.out.println("LOW: " + leftEye.getLow() + " " + rightEye.getLow());
	       
	       Delay.msDelay(5000);
	       
	       leftEye.calibrateHigh();
	       rightEye.calibrateHigh();
	       Sound.playTone(1000 + 200, 100);
	       System.out.println("HIGH: " + leftEye.getHigh() + " " + rightEye.getHigh());
	       
	   }
	   

}
