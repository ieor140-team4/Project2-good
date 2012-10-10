package milestone4;

import java.awt.Point;

import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import essentials_new.ButtonInputter;
import essentials_new.GridNavigator;
import essentials_new.ObstacleDetector;
import essentials_new.Tracker;

public class Milestone4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Create our button inputter to take button input.
		ButtonInputter bi = new ButtonInputter();

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
		
		//Create a detector
		ObstacleDetector detector = new ObstacleDetector(new UltrasonicSensor(SensorPort.S3));

		//Calibrate.
		System.out.println("Begin calibration.");
		tracker.myCalibrate();
		System.out.println("Done with calibration. Press any button to begin.");

		LCD.clear();

		GridNavigator gn = new GridNavigator(tracker, detector);
		detector.addObstacleListener(gn);
		
		while (true) {
			Point destination = bi.display(gn.getXPos(), gn.getYPos());
			gn.moveTo(destination.x, destination.y);
		}
	}


	
}
