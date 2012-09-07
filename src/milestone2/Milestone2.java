package milestone2;

import essentials.*;
import lejos.robotics.navigation.*;
import lejos.nxt.*;
import milestone1.Tracker;

public class Milestone2 {

	public static void main(String[] args) {
		
		
		//Configure the Differential Pilot using the measurements of our robot.
		double trackWidth = 9.2 + 2.6;
		double wheelDiam = 5.56;
		DifferentialPilot pilot = new DifferentialPilot(wheelDiam,trackWidth,Motor.A,Motor.B); //need to fix
		LightSensor leftEye = new LightSensor(SensorPort.S1);
		LightSensor rightEye = new LightSensor(SensorPort.S4);
		
		//Assign the axle-to-sensor distance so that our robot knows how far to go.
		double length = 6.9;
		
		
		//Assign a tracker.
		Tracker tracker = new Tracker(pilot, leftEye, rightEye, length);
		
		//Calibrate.
		System.out.println("Begin calibration.");
		tracker.myCalibrate();
		System.out.println("Done with calibration. Press any button to begin.");

		LCD.clear();
		
		//Lets us read the values of the light sensors for a while before letting
		//the robot go by pressing a button.
		while(Button.readButtons() == 0) {
			LCD.drawInt(leftEye.readValue(), 4, 0, 0);
			LCD.drawInt(rightEye.readValue(), 4, 1, 6);
			LCD.refresh();
		}
		
		//2 squares * 4 sides/square = 8 sides
		for (int i = 0; i < 8; i++) {
			//Track the line, and we need to cross over a black square in the middle of the transit.
			tracker.trackLine();
			tracker.crossBlack();
			tracker.trackLine();
			//This will rotate 90 degrees each time since the increment over the last heading is 90.
			tracker.rotateTo(90 * (i+1));
		}
		
		
		tracker.rotateTo(90);
		
		//2 squares * 4 sides/square = 8 sides
		for (int i = 0; i < 8; i++) {
			//Track the line, and we need to cross over a black square in the middle of the transit.
			tracker.trackLine();
			tracker.crossBlack();
			tracker.trackLine();
			//This will rotate 90 degrees each time since the increment over the last heading is 90.
			tracker.rotateTo(-90 * i);
		}
	}

}
