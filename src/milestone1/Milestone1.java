package milestone1;

import lejos.robotics.navigation.*;
import lejos.nxt.*;
import lejos.util.*;
import essentials.Tracker;
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
		
		//Lets us read the values of the light sensors for a while before letting
		//the robot go by pressing a button.
		while(Button.readButtons() == 0) {
			LCD.drawInt(leftEye.readValue(), 4, 0, 0);
			LCD.drawInt(rightEye.readValue(), 4, 1, 6);
			LCD.refresh();
		}
		
		//It will count for 8 black spots to know how many laps it's completed.
		for (int i = 0; i < 8; i++) {
			
			tracker.trackLine();
			Sound.playTone(1200,100); //Beeps for audio feedback
			
		}
		
		//Now it rotates 180 degrees and does 4 laps the other way.
		tracker.rotateTo(180, false);
		
		for (int i = 0; i < 8; i++) {
			
			tracker.trackLine();
			Sound.playTone(1200,100);
		}
		
		//Now we do 4 figure eights. In each figure eight we go halfway around the track,
		//turn, go down the straight line, turn again, go the other halfway around the track,
		//turn yet again, go down the straight line again, and turn.
		for (int i = 0; i < 4; i++) {
			//Semi circle + left turn
			tracker.trackLine();
			Sound.playTone(1400,30);
			tracker.rotateTo(270, true);
			
			//First straight line + right turn
			tracker.trackLine();
			Sound.playTone(1400,30);
			tracker.rotateTo(180, true);
			
			//Semi circle + right turn
			tracker.trackLine();
			Sound.playTone(1400,30);
			tracker.rotateTo(90, true);
			
			//Second straight line + left turn
			tracker.trackLine();
			Sound.playTone(1400,30);
			tracker.rotateTo(180, true);
			
			
		}
		
		
	}
	
}
