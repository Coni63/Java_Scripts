package fr.coni.genetic_algo;

import java.util.Random;

public class Candidate {
	
	private float speed;
	private int x;
	private int y;
	private float thrust; // thrust max for both engines
	private float angle;
	private float weight;
	private float friction;
	private float b;
	private float h;
	private float I;
	
	Candidate(){
		this.speed = 0f;
		this.x = 0;
		this.y = 400;
		this.thrust = 100f;
		this.angle = 0f;
		this.weight = 100f;
		this.friction = 100f;
		this.b = 30f;
		this.h = 3f;
		this.I = setInertia();
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void move() {
		Random random = new Random();
		this.x += random.nextInt(10);
		this.y += random.nextInt(10)-5;
		this.angle = (float) (( random.nextFloat() - 0.5 ) * Math.PI);
	}

	public float getThrust() {
		return this.thrust;
	}

	public void setThrust(float thrust) {
		this.thrust = thrust;
	}

	public float getAngle() {
		return this.angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getWeight() {
		return this.weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getFriction() {
		return this.friction;
	}

	public void setFriction(float friction) {
		this.friction = friction;
	}

	public float getWidth() {
		return this.b;
	}

	public void setWidth(float width) {
		this.b = width;
		setInertia();
	}
	
	public float getThickness() {
		return this.h;
	}
	
	private float setInertia() {
		return (float)( this.b * Math.pow(this.h, 3) )/12;
	}
	
	
}
