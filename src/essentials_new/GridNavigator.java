package essentials_new;

import lejos.nxt.Sound;

public class GridNavigator implements ObstacleListener {

	private Tracker tracker;
	private Grid grid;
	private Node position;
	private ObstacleDetector detector;
	private int heading; //0, 1, 2, or 3. Corresponds to that * 90.

	public GridNavigator(Tracker myTracker, ObstacleDetector myDetector) {
		tracker = myTracker;
		detector = myDetector;

		grid = new Grid(6,8);
		position = grid.nodes[0][0];
		heading = 0;
	}

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

				while (destination.equals(position) || destination.isBlocked()) {


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
					detector.scanForObstacles();

				}


			}

			tracker.trackLine();
			tracker.crossBlack();
			position = destination;
		}

	}

	public int getXPos() {
		return position.getX();
	}

	public int getYPos() {
		return position.getY();
	}

	public int getHeading() {
		return heading;
	}

	public void objectFound(PolarPoint objectLocation) {
		
		Sound.playNote(Sound.PIANO, 200, 5);
		Sound.playNote(Sound.PIANO, 250, 5);
		Sound.playNote(Sound.PIANO, 300, 5);
		Sound.playNote(Sound.PIANO, 350, 5);
		Sound.playNote(Sound.PIANO, 400, 5);
		Sound.playNote(Sound.PIANO, 450, 5);
		
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
			grid.nodes[obstacleX][obstacleY].block();
			grid.recalc();
		}
	}

	public String displayGrid() {
		String output = "";

		for(int y = grid.getHeight()-1;y>=0;y--)// one line at a time, top down
		{
			output += "\n ";// new line
			for(int x = 0;x<grid.getWidth();x++)
			{
				Node n = grid.nodes[x][y];
				if(n.isBlocked()) output +="   "+"*";
				else
				{
					int dist = n.getDistance();
					if(dist<10)
						output += "   "+dist ;
					else
						output += " "+dist;
				}
			}
		}

		return output;

	}
}
