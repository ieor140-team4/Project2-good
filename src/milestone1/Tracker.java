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
		return true;
	}
	
	private boolean isWhite() {
		return true;
	}
	
	private boolean isBlack() {
		return false;
	}
	
	private void findLine() {
		//rotate a little bit left and a little bit right to find the line
	}
	
	public void trackLine() {
		while (!isBlack()) {
			dp.steer(-diffValues());
		}
	}

}
