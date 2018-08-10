package fr.coni.BtController;

import java.util.Arrays;

import processing.core.PApplet;

public class Main extends PApplet {
	
	private float FrameRate = 15f;
	private float[] sensors = new float[6];
	
	public static void main(String[] args) {
		PApplet.main("fr.coni.BtController.Main");
	}
	
	public void settings(){
	    size(1920, 1080);
	    StartWaitingThread();
	}

	public void setup() {
		frameRate(FrameRate);
	}
	
	public void draw() {
		
	}
	
	public void StartWaitingThread() {
		Thread waitThread = new Thread(new WaitThread(this));
        waitThread.start();
	}

	public float[] getSensors() {
		return sensors;
	}

	public void setSensors(float[] sensors) {
		this.sensors = sensors;
		System.out.println(Arrays.toString(this.sensors));
	}

}
