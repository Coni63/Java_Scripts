package fr.coni.BtController;

import java.util.Arrays;

import processing.core.PApplet;
import processing.core.PVector;

public class Main extends PApplet {
	
	private float FrameRate = 30f;
	private float[] sensors = new float[6];
	
	int width = 800;
	int height = 450;
	
	PVector position = new PVector(width/2, height/2, -250);
	PVector speed = new PVector(0, 0, 0);
	PVector acc = new PVector(0, 0, 0);
	
	PVector angle = new PVector(0, 0, 0);
	PVector angledot = new PVector(0, 0, 0);
	PVector angledotdot = new PVector(0, 0, 0);
	
	
	public static void main(String[] args) {
		PApplet.main("fr.coni.BtController.Main");
	}
	
	public void settings(){
		size(width, height, P3D);
	    StartWaitingThread();
	}

	public void setup() {
		frameRate(FrameRate);
	}
	
	public void draw() {
		background(0);
		
		// based on sensors value, compute the new position/speed ...
		step();
		
		translate(this.position.x, this.position.y, this.position.z);
		rotateX(this.angle.x);
		rotateY(this.angle.y);
		rotateZ(this.angle.z);
		rectMode(CENTER);
		box(70, 20, 50);

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
	
	private void SensorsToPVector() {
		this.acc.x = this.sensors[1];
		this.acc.y = this.sensors[2];
		this.acc.z = this.sensors[0];
		this.angledotdot.x = this.sensors[4];
		this.angledotdot.y = this.sensors[5];
		this.angledotdot.z = this.sensors[3];
	}
	
	private void step() {
		
		SensorsToPVector();
		
		//System.out.println(this.acc);
		denoiseAll();		
		System.out.println(this.acc);
		
		this.speed = PVector.add(this.speed, PVector.div(this.acc, FrameRate));
		this.position = PVector.add(this.position, PVector.div(this.speed, FrameRate));
		this.angledot = PVector.add(this.angledot, PVector.div(this.angledotdot, FrameRate));
		this.angle = PVector.add(this.angle, PVector.div(this.angledot, FrameRate));
		
		//System.out.println(this.position);
		addConstrain();
		System.out.println(this.position);
		System.out.println("");
	}
	
	private void denoiseAll() {
		this.acc.x = denoise(this.acc.x);
		this.acc.y = denoise(this.acc.y);
		this.acc.z = denoise(this.acc.z);
		this.angledotdot.x = denoise(this.angledotdot.x);
		this.angledotdot.y = denoise(this.angledotdot.y);
		this.angledotdot.z = denoise(this.angledotdot.z);
	}
	
	private float denoise(float value) {
		if (Math.abs(value) < 0.005f ) {
			return 0;
		} else if (Math.abs(value) > 3f ) {
			return constrain(value, -3f, 3f);
		} else {
			return value;
		}
	}

	private void addConstrain() {
		this.position.x = constrain(this.position.x, 0, width);
		this.position.y = constrain(this.position.y, 0, height);
		this.position.z = constrain(this.position.z, -500, -100);
		
		this.angle.x = this.angle.x % (float)(2*Math.PI);
		this.angle.y = this.angle.y % (float)(2*Math.PI);
		this.angle.z = this.angle.z % (float)(2*Math.PI);
	}

}
