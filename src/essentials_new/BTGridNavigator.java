package essentials_new;

public class BTGridNavigator extends GridNavigator {

	private BTReceiver bt;
	
	public BTGridNavigator(Tracker tracker, ObstacleDetector detector, BTReceiver receiver) {
		super(tracker, detector);
		bt = receiver;
	}
	
}
