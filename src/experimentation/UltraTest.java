package experimentation;

import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import essentials_new.ObstacleDetector;

public class UltraTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S3);
		ObstacleDetector det = new ObstacleDetector(us);
		
		while (true) {
			Button.waitForAnyPress();
			
			System.out.println("Distance: " + det.getDistanceToObstacle());
		}
		
	}

}
