package essentials;

import java.awt.*;
import lejos.nxt.*;
import lejos.util.*;

public class GridNavigator {
	private Point position;
	private Tracker tracker;
	
	public GridNavigator(Tracker myTracker) {
		position = new Point(0,0);
		
		tracker = myTracker;
	}
	
	/* Performs a series of moves from the current position to the input position.
	 * 
	 */
	public void moveTo(Point newPos) {
		//Move x first;
		if (newPos.x != position.x) {
			executeMove(newPos.x - position.x, true);
		}
		if (newPos.y != position.y) {
			executeMove(newPos.y - position.y, false);
		}
	}
	
	
	/*Performs a move in one direction over a given distance. The argument
	* "isInXDirection" is true if we're going +/-x and false if we're going
	* +/-y.
	*/
	private void executeMove(int distance, boolean isInXDirection) {
		boolean hasMoved = false;
		int angle = 0;
		
		/* Figure out what angle we need to head by figuring out positive or
		*  negative, x or y.
		*  0 = positive x direction
		*  90 = positive y direction
		*  180 = negative x direction
		*  270 = negative y direction
		*  We use distance > 0 to check if we're positive or negative.
		*/
		if (isInXDirection) {
			if (distance > 0) {
				angle = 0;
			} else {
				angle = 180;
			}
		} else {
			if (distance > 0) {
				angle = 90;
			} else {
				angle = 270;
			}
		}
		
		
		//Since we've found the angle, we can take the absolute value now.
		boolean isInPosDirection = distance > 0;
		distance = Math.abs(distance);
		
		tracker.rotateTo(angle, false); //Turn to the direction we want to head.
		System.out.println(angle);
		
		//As long as there is still distance to travel, we continue to move in (1,0) line segments.
		while (distance > 0) {
			System.out.println(distance);
			tracker.crossBlack();
			tracker.trackLine();
			distance--;
			updatePosition(1, isInXDirection, isInPosDirection);
			hasMoved = true;
			System.out.println("Currently at: (" + position.x + "," + position.y + ")");
		}
		
		if (hasMoved) {
		tracker.rotateTo(angle, true);
		}
	}
	
	private void updatePosition(int distance, boolean isInXDirection, boolean isInPosDirection) {

		//Check if we just moved in x or y direction, then "add" the distance we traveled
		//to our position. If the distance is negative, then it will decrease as expected.
		if (isInXDirection) {
			if (isInPosDirection) {
				position.x += distance;
			} else {
				position.x -= distance;
			}
		} else {
			if (isInPosDirection) {
				position.y += distance;
			} else {
				position.y -= distance;
			}
		}
		
	}
	
	
	public Point getPosition() {
		return position;
	}

}
