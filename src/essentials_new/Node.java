package essentials_new;


public class Node {

	private int x;
	private int y;
	private int distance;
	private boolean isBlocked;
	
	public Node (int _x, int _y) {
		x = _x;
		y = _y;
		isBlocked = false;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public void setDistance(int newDistance) {
		distance = newDistance;
	}
	
	public void block() {
		isBlocked = true;
	}
	
	public void unblock() {
		isBlocked = false;
	}
	
	public void reset() {
	}
	
	public boolean isBlocked() {
		return isBlocked;
	}
	
}
