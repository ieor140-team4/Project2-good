package essentials_new;
import java.util.ArrayList;


public class Grid {

	public Node[][] nodes;
	private Node destination;
	
	/**
	 * Creates an x-by-y grid full of nodes that range from
	 * (0,0) to (x-1, y-1)
	 * 
	 * @param x the width of the grid in the x-direction
	 * @param y the width of the grid in the y-direction
	 */
	public Grid(int x, int y) {
		nodes = new Node[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				nodes[i][j] = new Node(i, j);
			}
		}
	}
	
	/**
	 * 
	 * @param x the x-location of the destination node on the grid
	 * @param y the y-location of the destination node on the grid
	 */
	public void setDestination(int x, int y) {
		destination = nodes[x][y];
	}
	
	public void recalc() {
		
		ArrayList<Node> nodesList = new ArrayList<Node>();
		
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes[0].length; j++) {
				
				nodes[i][j].setDistance(100000);
				nodesList.add(nodes[i][j]);
				
			}
		}
		
		destination.setDistance(0);
		
		while (nodesList.size() > 0) {
			
			int shortestDist = 100000;
			int startNodeIndex = 0;
			
			for (int i = 0; i < nodesList.size(); i++) {
				int nodeDistance = nodesList.get(i).getDistance();
				if (nodeDistance < shortestDist) {
					shortestDist = nodeDistance;
					startNodeIndex = i;
				}
			}
			
			Node thisNode = nodesList.get(startNodeIndex);
			Node[] neighbors = getNeighborsToNode(thisNode);
			nodesList.remove(thisNode);
			for (int i = 0; i < neighbors.length; i++) {
				if (neighbors[i] != null) {
					int oldDistance = neighbors[i].getDistance();
					int newDistance = thisNode.getDistance() + 1;
					if (newDistance < oldDistance) {
						neighbors[i].setDistance(newDistance);
					}
				}
			}
			
		}
		
	}
	
	/**
	 * 
	 * @return the number of x entries in the grid
	 */
	public int getWidth() {
		return nodes.length;
	}
	
	/**
	 * 
	 * @return the number of y entries in the grid
	 */
	public int getHeight() {
		return nodes[0].length;
	}
	
	/**
	 * 
	 * @param n the node that we're finding the neighbors of
	 * @return the neighbors in an array. neighbors[0] is the node to the right (positive x),
	 * neighbors[3] is the node below (negative y)
	 */
	public Node[] getNeighborsToNode(Node n) {
		Node[] neighbors = new Node[4];
		if (n.getX() > 0) {
			Node leftNode = nodes[n.getX() - 1][n.getY()];
			if (!leftNode.isBlocked()) {
				neighbors[2] = leftNode;
			} else {
				neighbors[2] = null;
			}
		} else {
			neighbors[2] = null;
		}
		
		if (n.getX() < getWidth() - 1) {
			Node rightNode = nodes[n.getX() + 1][n.getY()];
			if (!rightNode.isBlocked()) {
				neighbors[0] = rightNode;
			} else {
				neighbors[0] = null;
			}
		} else {
			neighbors[0] = null;
		}
		
		
		if (n.getY() > 0) {
			Node botNode = nodes[n.getX()][n.getY() - 1];
			if (!botNode.isBlocked()) {
				neighbors[3] = botNode;
			} else {
				neighbors[3] = null;
			}
		} else {
			neighbors[3] = null;
		}
		
		
		if (n.getY() < getHeight() - 1) {
			Node topNode = nodes[n.getX()][n.getY() + 1];
			if (!topNode.isBlocked()) {
				neighbors[1] = topNode;
			} else {
				neighbors[1] = null;
			}
		} else {
			neighbors[1] = null;
		}
		
		return neighbors;
	}
	
}
