package milestone1;

import lejos.robotics.navigation.*;
import lejos.nxt.*;
import lejos.util.*;
import experimentation.LineDataCollect;

public class Milestone1 {

	public static void main(String[] args){
		
		//Configure the Differential Pilot using the measurements of our robot.
		double trackWidth = 9.2 + 2.6;
		double wheelDiam = 5.56;
		DifferentialPilot pilot = new DifferentialPilot(wheelDiam,trackWidth,Motor.A,Motor.B); //need to fix
		LightSensor leftEye = new LightSensor(SensorPort.S1);
		LightSensor rightEye = new LightSensor(SensorPort.S4);
		
		//Assign the axle-to-sensor distance so that our robot knows how far to go.
		double length = 7.0;
		
		
		//Assign a tracker.
		Tracker tracker = new Tracker(pilot, leftEye, rightEye, length);
		
		//Calibrate.
		System.out.println("Begin calibration.");
		tracker.myCalibrate();
		System.out.println("Done with calibration. Press any button to begin.");

		LCD.clear();
		
		while(Button.readButtons() == 0) {
			LCD.drawInt(leftEye.readValue(), 4, 0, 0);
			LCD.drawInt(rightEye.readValue(), 4, 1, 6);
			LCD.refresh();
		}
		
		//It will count for 8 black spots to know how far it's gone.
		for (int i = 0; i < 8; i++) {
			
			tracker.trackLine();
			Sound.playTone(1200,100);
			while (tracker.minValues() < -10) {
				pilot.steer(0);
			}
		}
		
		pilot.rotate(180);
		
		for (int i = 0; i < 8; i++) {
			
			tracker.trackLine();
			Sound.playTone(1200,100);
			while (tracker.minValues() < -10) {
				pilot.steer(0);
			}
		}
		
		for (int i = 0; i < 4; i++) {
			tracker.trackLine();
			Sound.playTone(1200,30);
			Sound.playTone(1400,30);
			Sound.playTone(1300,40);
			pilot.travel(length);
			pilot.rotate(90);
			
			tracker.trackLine();
			Sound.playTone(1200,30);
			Sound.playTone(1400,30);
			Sound.playTone(1300,40);
			pilot.travel(length);
			pilot.rotate(-90);
			
			tracker.trackLine();
			Sound.playTone(1200,30);
			Sound.playTone(1400,30);
			Sound.playTone(1300,40);
			pilot.travel(length);
			pilot.rotate(-90);
			
			tracker.trackLine();
			Sound.playTone(1200,30);
			Sound.playTone(1400,30);
			Sound.playTone(1300,40);
			pilot.travel(length);
			pilot.rotate(90);
			
			
		}
		
		
	}
	
}
