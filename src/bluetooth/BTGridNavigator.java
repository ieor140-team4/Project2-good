package bluetooth;

import java.awt.Point;
import java.io.IOException;

import essentials_new.GridNavigator;
import essentials_new.Node;
import essentials_new.ObstacleDetector;
import essentials_new.Tracker;

public class BTGridNavigator extends GridNavigator {

	private BTCommunicator bt;

	/** 
	 * An updated constructor. Takes in the tracker and detector like GridNavigator,
	 * as well as a BTCommunicator to handle the newfangled bluetooth communications.
	 * 
	 * @param tracker
	 * @param detector
	 * @param comm
	 */
	public BTGridNavigator(Tracker tracker, ObstacleDetector detector, BTCommunicator comm) {
		super(tracker, detector);
		bt = comm;
	}

	/**
	 * Waits to receive a point from the bluetooth communication, if an exception
	 * occurs returns null.
	 * 
	 * @return the received point
	 */
	public Point getDestination() {
		try {
			return bt.receive();
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * Blocks the specified node, and tells the computer that the node has been blocked.
	 * 
	 */
	public void updateObstaclePosition(Node obstacleNode) {
		obstacleNode.block();
		try {
			bt.send(1, obstacleNode.getX(), obstacleNode.getY());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** 
	 * Updates the robot position, and tells the computer the robot's new position.
	 */
	public void updateRobotPosition(Node destination) {
		position = destination;
		try {
			bt.send(0,  position.getX(), position.getY());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Runs stuff.
	 * 
	 * Continuously waits to receive new destination instructions from the computer,
	 * then moves there. Etc etc etc
	 * 
	 */
	public void go() {

		Point destination = null;
		
		while (true) {

			while (destination == null) {
				destination = getDestination();
			}
			
			moveTo(destination.x, destination.y);
			
			System.out.println("Moved! Waiting for new destination.");
			destination = null;
		}
	}

}
