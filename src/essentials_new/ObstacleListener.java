package essentials_new;

/**
 * An interface for something that listens to the obstacle detector.
 * 
 * @author nate.kb
 *
 */
public interface ObstacleListener {

	
	/**
	 * This method will be called when the obstacle detector finds an
	 * obstacle, whether through a touch sensor or an ultrasonic sensor.
	 * 
	 * @param objectLocation the location of the object in polar coordinates
	 * @return true if a new object is found, false otherwise
	 */
	public boolean objectFound(PolarPoint objectLocation);
}
