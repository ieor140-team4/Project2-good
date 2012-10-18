package bluetooth;

import java.awt.Point;
import java.io.IOException;

import essentials_new.GridNavigator;
import essentials_new.Node;
import essentials_new.ObstacleDetector;
import essentials_new.Tracker;

public class BTGridNavigator extends GridNavigator {

	private BTCommunicator bt;

	public BTGridNavigator(Tracker tracker, ObstacleDetector detector, BTCommunicator comm) {
		super(tracker, detector);
		bt = comm;
	}

	public Point getDestination() {
		try {
			return bt.receive();
		} catch (IOException e) {
			return null;
		}
	}
	
	public void updateObstaclePosition(Node obstacleNode) {
		obstacleNode.block();
		try {
			bt.send(1, obstacleNode.getX(), obstacleNode.getY());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateRobotPosition(Node destination) {
		position = destination;
		try {
			bt.send(0,  position.getX(), position.getY());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
	
	/**
	 * 
	 * @return true if the send is a success, false otherwise
	 */
	public boolean sendData() {
		return true;
	}

}
