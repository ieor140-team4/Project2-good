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
			
			//Now we need to get off the black dot, so we continue going forward until
			//the minimum of the values is > -10, which should mean we're on the blue line again.
			while (tracker.minValues() < -10) {
				pilot.steer(0);
			}
		}
		
		//Now it rotates 180 degrees and does 4 laps the other way.
		pilot.rotate(180);
		
		for (int i = 0; i < 8; i++) {
			
			tracker.trackLine();
			Sound.playTone(1200,100);

			//Now we need to get off the black dot, so we continue going forward until
			//the minimum of the values is > -10, which should mean we're on the blue line again.
			while (tracker.minValues() < -10) {
				pilot.steer(0);
			}
		}
		
		//Now we do 4 figure eights. In each figure eight we go halfway around the track,
		//turn, go down the straight line, turn again, go the other halfway around the track,
		//turn yet again, go down the straight line again, and turn.
		for (int i = 0; i < 4; i++) {
			//Semi circle + left turn
			tracker.trackLine();
			Sound.playTone(1400,30);
			pilot.travel(length);
			pilot.rotate(90);
			
			//First straight line + right turn
			tracker.trackLine();
			Sound.playTone(1400,30);
			pilot.travel(length);
			pilot.rotate(-90);
			
			//Semi circle + right turn
			tracker.trackLine();
			Sound.playTone(1400,30);
			pilot.travel(length);
			pilot.rotate(-90);
			
			//Second straight line + left turn
			tracker.trackLine();
			Sound.playTone(1400,30);
			pilot.travel(length);
			pilot.rotate(90);
			
			
		}
		
		
	}
	
}
