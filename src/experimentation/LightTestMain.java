package experimentation;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.util.Delay;
import lejos.util.Datalogger;

public class LightTestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		
		LineDataCollect ldc = new LineDataCollect();
		
		System.out.println("Begin Calibration.");
		ldc.myCalibrate();
		System.out.println("Calibration complete. Begin polling values.");
		
		ldc.go();
		
		/*
		//Take readings
		
		LightSensor rightEye = new LightSensor(SensorPort.S1);
		LightSensor leftEye = new LightSensor(SensorPort.S2);
		Datalogger logger = new Datalogger();
		
		for (int i = 0; i < 9; i++) {
			System.out.println("Reading " + i);
			Button.waitForAnyPress();
			
            Delay.msDelay(500);
            logger.writeLog(leftEye.readNormalizedValue());
            logger.writeLog(rightEye.readNormalizedValue());
            Sound.beep();
			
		}
		
		System.out.println("Waiting for upload...");
		Button.waitForAnyPress();
		*/

	}

}
