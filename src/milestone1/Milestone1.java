package milestone1;

import lejos.robotics.navigation.*;
import lejos.nxt.*;
import lejos.util.*;

public class Milestone1 {

	public static void main(String[] args){
		
		
		Button.waitForAnyPress();

		double trackWidth = 9.2 + 2.6;
		double wheelDiam = 5.56;
		DifferentialPilot pilot = new DifferentialPilot(wheelDiam,trackWidth,Motor.A,Motor.B); //need to fix
		LightSensor leftEye = new LightSensor(SensorPort.S1);
		LightSensor rightEye = new LightSensor(SensorPort.S4);
		
		double length = 7.0;
		
		Tracker tracker = new Tracker(pilot, leftEye, rightEye, length);
		
		//tracker.myCalibrate();
		
		for (int i = 0; i < 8; i++) {

	          LCD.drawInt(leftEye.readValue(), 4, 6, 1 + i);
	          LCD.drawInt(rightEye.readValue(), 4, 12, 1 + i);
			
			tracker.trackLine();
			pilot.travel(0.5*length);
		}
		
		
	}
	
}
