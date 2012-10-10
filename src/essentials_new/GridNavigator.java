package essentials_new;

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

		boolean moveBeforeRotate = false;

		while (position.getDistance() > 0) {
			detector.scanForObstacles();

			Node[] neighbors = grid.getNeighborsToNode(position);
			int shortestDistance = 1000;
			int shortestDistanceIndex = 0;

			for (int i = 0; i < neighbors.length; i++) {
				if ((neighbors[i] != null) && (neighbors[i].getDistance() < shortestDistance)) {
					shortestDistance = neighbors[i].getDistance();
					shortestDistanceIndex = i;
				}
			}

			tracker.rotateTo(shortestDistanceIndex * 90, moveBeforeRotate);
			moveBeforeRotate = true;

			tracker.trackLine();
			position = neighbors[shortestDistanceIndex];
			heading = shortestDistanceIndex;
		}

		tracker.crossBlack();
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
		int obstacleX = position.getX();
		int obstacleY = position.getY();

		if ((heading == 0) || (heading == 2)) {
			obstacleX += objectLocation.dist;
		} else {
			obstacleY += objectLocation.dist;
		}

		grid.nodes[obstacleX][obstacleY].block();
		grid.recalc();
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
