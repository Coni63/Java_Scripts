package fr.coni.genetic_algo;

import java.awt.geom.Point2D;
import java.util.Random;
import processing.core.PVector;
import processing.core.PApplet;

public class Candidate {
	
	private float speed;
	private float x;
	private float y;
	private PVector v;
	private PVector a;
	private float angle;
	private float omega;
	private float omega_dot;
	private float thrust; // thrust max for both engines
	private float weight;
	private float friction;
	private float air_friction;
	private float air_friction_force;
	private float friction_force;
	private float b;
	private float h;
	private float I;
	private float timelapse;                        
	private float density = 7800;                     			// kg/m^3
	public int score;
	public boolean is_active;
	public Point2D[] corners;
	private int current_door;
	private Map map;
	private float AngleToTarget;
	private IA ia;
	
	Candidate(float fps, Map map){
		this.speed = 0f;                              			// m/s
		this.x = 0;                                   			// m
		this.y = 400;                                 			// m
		this.v = new PVector(0, 0);                   			// m/s
		this.a = new PVector(0, 0);                   			// m/s²
		this.angle = 0;             							// rad
		this.omega = 0f;                              			// rad/s
		this.omega_dot = 0f;                          			// rad/s²
		this.thrust = 1500f; 									// N
		this.b = 3f;                                  			// m
		this.h = 0.3f;                                			// m
		this.weight = this.b * this.h * this.h * this.density; 	// kg
		this.friction = 0.3f; 						           	// coef steel/steel
		this.friction_force = this.friction * this.weight;      // N
		this.air_friction = 2.42f * 1.225f * this.b * this.h;   // Cx * rho *S
		this.I = setInertia();                        			// m^4
		timelapse = (float) (1/fps);                  			// s
		this.corners = new Point2D[4];
		this.is_active = true;
		this.map = map;
		this.current_door = 0;
		this.AngleToTarget = 0;
		this.ia = new IA();
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public void move(float[] actions) {
		
		float b1 = actions[0];
		float b2 = actions[1];
		
		this.air_friction_force = this.air_friction * (float)Math.pow(this.speed, 2);
		
		float norm = ( this.thrust * (b1 + b2) - this.air_friction_force - this.friction_force ) / this.weight;

		PVector acc_vector = PVector.fromAngle(this.angle);
		this.a = acc_vector.mult( norm );
		this.v.add(acc_vector.mult( timelapse ));
		this.speed = this.v.mag();
		
		this.x += this.v.x * timelapse;
		this.y += this.v.y * timelapse;

		this.omega_dot = this.I * (this.h / 2) * this.thrust * (b2-b1) ;
		this.omega += omega_dot * timelapse;
		this.angle = (float)((this.angle + this.omega * timelapse) % (2 * Math.PI));
		
	}
	
	public void UpdateTarget() {
		
		UpdateTargetNumber();
		this.AngleToTarget = getTargetAngle();
		
	}
	
	public void UpdateTargetNumber() {
		Door current_door = this.map.doors[this.current_door];
		if (this.x > current_door.x) {
			if ( (this.y < current_door.center - current_door.width/2) | (this.y > current_door.center + current_door.width/2) ) {
				this.is_active = false;
				System.out.println("Candidate is out");
				this.current_door++;
			} else {
				this.current_door++;
				System.out.println("Congratulation you just passed the door " + this.current_door );
			}
		}
		
	}
	
	public float[] getAction() {
		return this.ia.predict();
	}
	
	
	public void setCorner() {
		this.corners[0] = new Point2D.Double(this.x + this.b/2 * Math.sin(this.angle) + this.h/2 * Math.cos(this.angle), 
											 this.y - this.b/2 * Math.cos(this.angle) + this.h/2 * Math.sin(this.angle));
		this.corners[1] = new Point2D.Double(this.x + this.b/2 * Math.sin(this.angle) - this.h/2 * Math.cos(this.angle), 
				 							 this.y - this.b/2 * Math.cos(this.angle) - this.h/2 * Math.sin(this.angle));
		this.corners[2] = new Point2D.Double(this.x - this.b/2 * Math.sin(this.angle) - this.h/2 * Math.cos(this.angle), 
			 								 this.y + this.b/2 * Math.cos(this.angle) - this.h/2 * Math.sin(this.angle));
		this.corners[3] = new Point2D.Double(this.x - this.b/2 * Math.sin(this.angle) + this.h/2 * Math.cos(this.angle), 
				 							 this.y + this.b/2 * Math.cos(this.angle) + this.h/2 * Math.sin(this.angle));
	}
	
	public void displayHUD(PApplet parent) {
		parent.rectMode(PApplet.CORNER);
		parent.fill(255, 255, 255);
		parent.rect(0, 0, 180, 60);
		parent.textSize(10);
		parent.fill(0, 0, 0);
		parent.text("Position : (" + String.format("%.02f", this.x) + ", " + String.format("%.02f", this.y) + ")", 10, 10);
		parent.text("Speed : " + String.format("%.02f", this.speed) + " m/s (" + String.format("%.02f", this.speed * 3.6) + " km/h)" , 10, 20);
		parent.text("Acc : " + String.format("%.02f", this.a.mag()) , 10, 30);
		parent.text("Angle : " + String.format("%.02f", this.angle * 180 / Math.PI) , 10, 40);
		parent.text("Friction : " + String.format("%.02f", this.air_friction_force + this.friction_force) , 10, 50);
		//parent.text("Angle : " + String.format("%.02f", this.angle * 180 / Math.PI) , 10, 60);
		//parent.text("Angle : " + String.format("%.02f", this.angle * 180 / Math.PI) , 10, 70);
	}
	
	public void draw(PApplet parent) {		
		parent.translate(this.x, this.y);
		parent.rotate(this.angle);
		parent.rect(0, 0, this.h, this.b);
		parent.rotate(-this.angle);
		parent.translate(-this.x, -this.y);
		
		setCorner();
		
		parent.ellipse((float)this.corners[0].getX(), (float)this.corners[0].getY(), 5f, 5f);
		parent.ellipse((float)this.corners[1].getX(), (float)this.corners[1].getY(), 5f, 5f);
		parent.ellipse((float)this.corners[2].getX(), (float)this.corners[2].getY(), 5f, 5f);
		parent.ellipse((float)this.corners[3].getX(), (float)this.corners[3].getY(), 5f, 5f);
		
	}
	
	public float getTargetAngle() {
		PVector VectorToTarget;
		VectorToTarget = new PVector((float)(this.map.doors[this.current_door].x) - this.x,
									 (float)(this.map.doors[this.current_door].center) - this.y);
		
		return this.v.heading() - VectorToTarget.heading();
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
		return this.b*10;
	}

	public void setWidth(float width) {
		this.b = width;
		setInertia();
	}
	
	public float getThickness() {
		return this.h*10;
	}
	
	private float setInertia() {
		return (float)( this.b * Math.pow(this.h, 3) )/12;
	}
	
	
}
