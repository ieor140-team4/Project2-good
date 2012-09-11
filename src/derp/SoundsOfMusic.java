package derp;
import lejos.nxt.*;

public class SoundsOfMusic {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) {
		
		int C = 262, D = 294, E = 330, F = 349, G = 392, A = 440, B = 494, hC = 523;
		
		Sound.setVolume(100);
		Button.waitForAnyPress();
		Sound.playNote(Sound.PIANO, C, 400);
		Sound.playNote(Sound.PIANO, C, 400);
		Sound.playNote(Sound.PIANO, C, 400);
		Sound.playNote(Sound.PIANO, D, 300);
		Sound.playNote(Sound.PIANO, E, 500);
		Sound.playNote(Sound.PIANO, E, 400);
		Sound.playNote(Sound.PIANO, D, 400);
		Sound.playNote(Sound.PIANO, E, 300);
		Sound.playNote(Sound.PIANO, F, 300);
		Sound.playNote(Sound.PIANO, G, 500);
		Sound.playNote(Sound.PIANO, hC, 200);
		Sound.playNote(Sound.PIANO, hC, 200);
		Sound.playNote(Sound.PIANO, hC, 200);
		Sound.playNote(Sound.PIANO, G, 200);
		Sound.playNote(Sound.PIANO, G, 200);
		Sound.playNote(Sound.PIANO, G, 200);
		Sound.playNote(Sound.PIANO, E, 300);
		Sound.playNote(Sound.PIANO, D, 200);
		Sound.playNote(Sound.PIANO, C, 500);
		Sound.playNote(Sound.PIANO, G, 400);
		Sound.playNote(Sound.PIANO, F, 300);
		Sound.playNote(Sound.PIANO, E, 300);
		Sound.playNote(Sound.PIANO, D, 200);
		Sound.playNote(Sound.PIANO, C, 500);
		Button.waitForAnyPress();
		//new comment!
		//C, C, C, D, E, E, D, E, F, G. CCC, GGG, EEE, CCC, G, F, E, D, C.
	}

}
