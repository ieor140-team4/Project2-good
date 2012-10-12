package essentials_new;

import lejos.nxt.UltrasonicSensor;

public class ObstacleDetector {

	private UltrasonicSensor sensor;
	private ObstacleListener listener;

	public ObstacleDetector(UltrasonicSensor us_sensor) {
		sensor = us_sensor;
	}

	public void addObstacleListener(ObstacleListener ol) {
		listener = ol;
	}

	public int getDistanceToObstacle() {
		return sensor.getDistance();
	}

	public void scanForObstacles() {
		int distance = getDistanceToObstacle();
		if (distance < 30) {
			listener.objectFound(new PolarPoint(1, 0)); //This represents 1 unit distance away
		} else if (distance < 60) {
			listener.objectFound(new PolarPoint(2, 0)); //2 units
		} else if (distance < 100) {
			listener.objectFound(new PolarPoint(3, 0));
		}
	}

}
