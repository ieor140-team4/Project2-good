package essentials_new;

import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;

public class ObstacleDetector {

	private UltrasonicSensor sensor;
	private ObstacleListener listener;

	/**
	 * A constructor.
	 * 
	 * @param us_sensor the ultrasonic sensor of the robot
	 */
	public ObstacleDetector(UltrasonicSensor us_sensor) {
		sensor = us_sensor;
	}

	/**
	 * Tells the obstacle detector who to call when there's something strange
	 * in the neighborhood.
	 * 
	 * @param ol the obstacle listener that wants to listen to this detector
	 */
	public void addObstacleListener(ObstacleListener ol) {
		listener = ol;
	}

	/**
	 * 
	 * @return the distance that the ultrasonic sensor reads
	 */
	public int getDistanceToObstacle() {
		return sensor.getDistance();
	}

	/**
	 * Uses the ultrasonic sensor to see if there is an obstacle in the robot's path.
	 * If an obstacle is found, it calls the obstacleListener's object found method.
	 * An obstacle within 30 cm of the robot is 1 unit distance away, within 60 cm is
	 * two unit distances away, and 100 cm is three unit distances away.
	 * 
	 * @return true if a new obstacle is detected, false otherwise
	 */
	public boolean scanForObstacles() {
		int distance = getDistanceToObstacle();
		boolean b = false;
		
		if (distance < 30) {

			b = listener.objectFound(new PolarPoint(1, 0)); //This represents 1 unit distance away
			return b;
			
		} else if (distance < 60) {

			b = listener.objectFound(new PolarPoint(2, 0)); //2 units
			return b;
			
		} else if (distance < 100) {

			b = listener.objectFound(new PolarPoint(3, 0));
			return b;
			
		} else {
			return b;
		}
	}

}
