package essentials_new;


public class Node {

	private int x;
	private int y;
	private int distance;
	private boolean isBlocked;
	
	/**
	 * Creates a new node. The default is unblocked.
	 * 
	 * @param _x the x coordinate
	 * @param _y the y coordinate
	 */
	public Node (int _x, int _y) {
		x = _x;
		y = _y;
		isBlocked = false;
	}
	
	/**
	 * 
	 * @return the x coordinate
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * 
	 * @return the y coordinate
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * 
	 * @return the distance stored as the distance to the destination
	 */
	public int getDistance() {
		return distance;
	}
	
	/**
	 * Sets a new distance to be stored by this node.
	 * 
	 * @param newDistance 
	 */
	public void setDistance(int newDistance) {
		distance = newDistance;
	}
	
	/**
	 * Sets the node being blocked to true.
	 */
	public void block() {
		isBlocked = true;
	}
	
	/**
	 * Sets the node being blocked to false.
	 */
	public void unblock() {
		isBlocked = false;
	}
	
	/**
	 * Does nothing.
	 */
	public void reset() {
	}
	
	/**
	 * 
	 * @return whether or not the node is blocked
	 */
	public boolean isBlocked() {
		return isBlocked;
	}
	
}
