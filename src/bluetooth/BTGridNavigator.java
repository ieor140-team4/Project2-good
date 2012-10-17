package bluetooth;

import java.awt.Point;
import java.io.IOException;

import essentials_new.GridNavigator;
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
	
}
