package essentials_new;

import lejos.nxt.*;
import lejos.util.*;
import lejos.robotics.navigation.*;

/* This class represents the tracker that our robot uses to track the line.
 * The tasks that this class needs to perform are various measurements of the error
 * that we observe, as well as a trackLine() method that monitors this error and
 * uses a gain constant to control it in a stable system.
 */
public class Tracker {
	
	private DifferentialPilot dp; //the pilot used
	private LightSensor leftEye; //the left light sensor
	private LightSensor rightEye; // the right light sensor
	private double sensorToAxleLength;
	private double heading; //the direction in angles from starting orientation that the robot is heading.
	
	/**
	 * Initializes our tracker
	 * 
	 * @param pilot the differential pilot used to steer
	 * @param leftLS the left light sensor
	 * @param rightLS the right light sensor
	 * @param length the length from the light sensors to the axle
	 */
	public Tracker(DifferentialPilot pilot, LightSensor leftLS, LightSensor rightLS, double length) {
		dp = pilot;
		leftEye = leftLS;
		rightEye = rightLS;
		
		dp.setTravelSpeed(15);
		dp.setRotateSpeed(360);
		dp.setAcceleration(2000);
		
		sensorToAxleLength = length;
		
		heading = 0;
	}
	
	/**
	 * 
	 * @return the difference between the values seen by the two eyes
	 */
	private int diffValues() {
		int leftval = leftEye.readValue();
		int rightval = rightEye.readValue();
		
		int diff = leftval - rightval; //Subtracts left from right: negative value means
		//we are right of the line, positive value means we are left of the line.
		
		return diff;
	}
	
	/**
	 * Sets the heading of the tracker to 0.
	 */
	public void resetHeading() {
		heading = 0;
	}
	
	/**
	 * @return the sum of the values the eyes see.
	 * 
	 */
	private int sumValues() {
		int leftval = leftEye.readValue();
		int rightval = rightEye.readValue();
		
		return leftval + rightval;
	}
	
	/**
	 * 
	 * @return the minimum of the two values the eyes see.
	 */
	public int minValues() {
		if (leftEye.readValue() > rightEye.readValue()) {
			return rightEye.readValue();
		} else {
			return leftEye.readValue();
		}
	}

	/**
	 * Tracks the blue line until we see a black square. When this method exits,
	 * the robot's light sensors are directly above the black square.
	 */
	public void trackLine() {
		
		//This gain constant worked well.
		float gainConstant = 1.0f;
		
		
		//This loop goes while no eye sees a value <-15, which should mean the blue lines.
		//The loop exits when an eye sees a low value, which means we're on a black line and
		//the Milestone1.main() method can handle us for now.
		while (minValues() > -15) {
			dp.steer(gainConstant * -1 * diffValues());
		}

	       Sound.playNote(Sound.PIANO, 1200, 50);
		
	}

	/**
	 * This method rotates the robot to a certain new heading, given in
	 * degrees from 0 to 360.
	 * 
	 * @param newHeading the heading we want to rotate to, where 0 is the positive x
	 * direction and 180 is the negative x direction. Can handle negative angles and angles
	 * above 360, as well.
	 * 
	 * @param move true if the robot should move before making the turn so that the axle
	 * is above the black square as opposed to the light sensors, false otherwise
	 */
	public void rotateTo(double newHeading, boolean move) {
		
		//We take off multiples of 360 so that the new heading is in 0<x<360
		while (newHeading > 360) {
			newHeading -= 360;
		}
		while (newHeading < -360) {
			newHeading += 360;
		}
		
		
		if (move) {
			dp.travel(sensorToAxleLength); //travel so that the axle is above the black square
		}
		
		//Finds the shortest angle to rotate through in order to make the turn.
		double rotationAngle = newHeading - heading;
		if (Math.abs(rotationAngle) > 180) {
			if (rotationAngle > 0) {
				rotationAngle -= 360;
			} else {
				rotationAngle += 360;
			}
		}
		dp.rotate(rotationAngle); //now we rotate.
		
		//Now we set the heading to where we face now, and take off multiples of 360.
		heading = newHeading;
		while (heading > 360) {
			heading -= 360;
		}
		while (heading < -360) {
			heading += 360;
		}
	}
	
	public void stop() {
		dp.stop();
	}
	
	/**
	 * Rotate function without an option to move or not.
	 * @param angle
	 */
	public void rotate(double angle) {
		dp.rotate(angle);
	}
	
	/**
	 * Rotate function with an option to move or not.
	 * @param angle
	 * @param move
	 */
	public void rotate(double angle, boolean move) {
		if (move) {
			dp.travel(sensorToAxleLength);
		}
		dp.rotate(angle);
	}
	
	/**
	 * This method tells the tracker to cross a black square that just stopped our
	 * navigation. It moves a set distance so that the center of the axle is now over
	 * the black square, as opposed to our light sensors.
	 */
	public void crossBlack() {
		dp.travel(sensorToAxleLength);
	}
	
	
	/**
	 * A calibration method that we use to get our robot's eyes calibrated. Essentially the
	 * same as the calibration method given to us.
	 * 
	 */
	   public void myCalibrate() {
		   
		   Button.waitForAnyPress();

		   leftEye.calibrateLow();
	       rightEye.calibrateLow();
	       Sound.playTone(1000 + 200, 100);
	       System.out.println("LOW: " + leftEye.getLow() + " " + rightEye.getLow());
	       
	       Button.waitForAnyPress();
	       
	       leftEye.calibrateHigh();
	       rightEye.calibrateHigh();
	       Sound.playTone(1000 + 200, 100);
	       System.out.println("HIGH: " + leftEye.getHigh() + " " + rightEye.getHigh());
	       
	   }
	   

}
