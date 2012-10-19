package essentials_new;

import lejos.nxt.Sound;

public class GridNavigator implements ObstacleListener {

	private Tracker tracker;
	private Grid grid;
	protected Node position;
	private ObstacleDetector detector;
	private int heading; //0, 1, 2, or 3. Corresponds to that * 90.

	/**
	 * A constructor. It also initializes a 6x8 grid for the navigator to navigate
	 * the robot on, since that is the size of the grid we're using in class.
	 * 
	 * @param myTracker the tracker the navigator uses
	 * @param myDetector the obstacle detector the navigator uses
	 */
	public GridNavigator(Tracker myTracker, ObstacleDetector myDetector) {
		tracker = myTracker;
		detector = myDetector;

		grid = new Grid(6,8);
		position = grid.nodes[0][0];
		heading = 0;
	}

	/** 
	 * Tells the robot to move to a specific location, given by x,y coordinates
	 * that correspond to the grid the robot is navigating on.
	 * 
	 * @param x the x-coordinate, from 0 to gridWidth-1
	 * @param y the y-coordinate, from 0 to gridHeight-1
	 */
	public void moveTo(int x, int y) {
		grid.setDestination(x, y);
		grid.recalc();

		while (position.getDistance() > 0) {
			detector.scanForObstacles();
			

			Node[] neighbors = grid.getNeighborsToNode(position);
			int shortestDistance = 1000;
			int bestHeading = 0;
			Node destination = position;
			
			for (int i = 0; i < neighbors.length; i++) {
				if ((neighbors[i] != null) && (neighbors[i].getDistance() < shortestDistance)) {
					shortestDistance = neighbors[i].getDistance();
					bestHeading = i;
				}
			}


			if ((neighbors[heading] != null) &&
					(neighbors[heading].getDistance() == shortestDistance)
					&& (!neighbors[heading].isBlocked())) {

				destination = neighbors[heading];
				bestHeading = heading;

			} else {

				while (destination.equals(position) || detector.scanForObstacles()) {


					shortestDistance = 1000;

					for (int i = 0; i < neighbors.length; i++) {
						if ((neighbors[i] != null) && (!neighbors[i].isBlocked())
								&& (neighbors[i].getDistance() < shortestDistance)) {
							shortestDistance = neighbors[i].getDistance();
							bestHeading = i;
						}
					}

					destination = neighbors[bestHeading];

					tracker.rotateTo(bestHeading * 90, false);
					heading = bestHeading;

				}


			}

			tracker.trackLine();
			tracker.crossBlack();
			updateRobotPosition(destination);
		}

	}

	/**
	 * Updates the status of the node containing the obstacle.
	 * 
	 * @param obstacleNode the obstacle node to block
	 */
	public void updateObstaclePosition(Node obstacleNode) {
		obstacleNode.block();
	}
	
	/**
	 * Updates the destination that the grid navigator has stored.
	 * 
	 * @param destination the node that we are now at
	 */
	public void updateRobotPosition(Node destination) {
		position = destination;
	}
	
	/**
	 * 
	 * @return the x coordinate of the grid navigator's current position
	 */
	public int getXPos() {
		return position.getX();
	}

	/**
	 * 
	 * @return the y coordinate of the grid navigator's current position
	 */
	public int getYPos() {
		return position.getY();
	}

	/**
	 * Gives the heading of the robot.
	 * 0 - positive x-direction
	 * 1 - positive y-direction
	 * 2 - negative x-direction
	 * 3 - negative y-direction
	 * 
	 * @return the heading of the robot right now, 0-3
	 */
	public int getHeading() {
		return heading;
	}

	/**
	 * Blocks the node of the grid that corresponds to the node that the
	 * obstacle was detected at. We figure out the position of the obstacle
	 * by looking at the robot's current position, its heading, and the distance
	 * that the obstacle detector saw it at.
	 * 
	 * @return true if a new obstacle is found that we didn't see before, false else
	 */
	public boolean objectFound(PolarPoint objectLocation) {

		int obstacleX = position.getX();
		int obstacleY = position.getY();

		if (heading == 0) {
			obstacleX += objectLocation.dist;
		} else if (heading == 2) {
			obstacleX -= objectLocation.dist;
		} else if (heading == 1) {
			obstacleY += objectLocation.dist;
		} else if (heading == 3) {
			obstacleY -= objectLocation.dist;
		} else {
			obstacleX = 100;
			obstacleY = 100;
		}

		if (((obstacleX < grid.getWidth()) && (obstacleY < grid.getHeight()))
				&& (obstacleX >= 0) && (obstacleY >= 0)){
			if (!grid.nodes[obstacleX][obstacleY].isBlocked()) {
				Node obstacleNode = grid.nodes[obstacleX][obstacleY];
				updateObstaclePosition(obstacleNode);
				grid.recalc();
				
				if (objectLocation.dist == 1) {
					Sound.playNote(Sound.PIANO, 200, 5);
					Sound.playNote(Sound.PIANO, 300, 5);
					Sound.playNote(Sound.PIANO, 400, 5);
					Sound.playNote(Sound.PIANO, 700, 5);
				} else if (objectLocation.dist == 2) {
					Sound.playNote(Sound.PIANO, 250, 5);
					Sound.playNote(Sound.PIANO, 350, 5);
					Sound.playNote(Sound.PIANO, 450, 5);
					Sound.playNote(Sound.PIANO, 800, 5);
				} else if (objectLocation.dist == 3) {
					Sound.playNote(Sound.PIANO, 300, 5);
					Sound.playNote(Sound.PIANO, 400, 5);
					Sound.playNote(Sound.PIANO, 500, 5);
					Sound.playNote(Sound.PIANO, 900, 5);
				} else {
					Sound.playNote(Sound.PIANO, 100, 1000);
				}
				
				return true;
			}
			grid.recalc();
		}
		
		return false;
	}

}
