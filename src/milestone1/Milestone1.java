package milestone1;

import lejos.robotics.navigation.*;
import lejos.nxt.*;
import lejos.util.*;
import experimentation.LineDataCollect;

public class Milestone1 {

	public static void main(String[] args){
		
		

		double trackWidth = 9.2 + 2.6;
		double wheelDiam = 5.56;
		DifferentialPilot pilot = new DifferentialPilot(wheelDiam,trackWidth,Motor.A,Motor.B); //need to fix
		LightSensor leftEye = new LightSensor(SensorPort.S1);
		LightSensor rightEye = new LightSensor(SensorPort.S4);
		
		double length = 7.0;
		
		Tracker tracker = new Tracker(pilot, leftEye, rightEye, length);
		
		System.out.println("Begin calibration.");
		tracker.myCalibrate();
		System.out.println("Done with calibration. Press any button to begin.");

		//tracker.myCalibrate();

		System.out.println("\n\n\n\n\n\n\n");
		
		for (int i = 0; i < 8; i++) {
			
			tracker.trackLine();
			pilot.travel(0.5*length);
		}
		
		
	}
	
}
