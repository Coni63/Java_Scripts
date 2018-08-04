package fr.coni.bubble_expansion;

import processing.core.PApplet;
import processing.core.PVector;
import java.util.Random;

public class Bubble {
	
	static int radius_init = 2;
	static int radius_ext = 13;
	static float v_max = 10;
	private PVector position;
	private PVector speed;
	private boolean extended;
	private PApplet parent;
	
	Bubble(PApplet parent){
		
		// sketch
		this.parent = parent;
		
		// create a non extended particle (with a speed and small radius) 
		this.extended = false;
		
		// set a random position
		Random random = new Random();
		int x_min = radius_init;
		int x_max = parent.width - radius_init;
		int y_min = radius_init;
		int y_max = parent.height - radius_init;
		int x = random.nextInt(x_max - x_min) + x_min;
		int y = random.nextInt(y_max - y_min) + y_min;
		
		// set a random speed
		int vx = random.nextInt(10)-5;
		int vy = random.nextInt(10)-5;
		position = new PVector(x, y);
		speed = new PVector(vx, vy).normalize().mult(v_max);
	}
	
	public void step() {
		this.position = PVector.add(this.position, this.speed);
		HandleBounces();
		HandleExpansion();
	}
	
	public void draw() {
		parent.fill(0);
		parent.ellipse(this.position.x, this.position.y, radius_init, radius_init);		
	}
	
	private void HandleBounces() {
		// above the top
		if (this.position.y < 0) {
			this.position.y *= -1;
			this.speed.y *= -1;
		}
		
		// below the bottom
		if (this.position.y > this.parent.height) {
			this.position.y = 2 * this.parent.height - this.position.y;
			this.speed.y *= -1;
		}
		
		// too much left
		if (this.position.x < 0) {
			this.position.x *= -1;
			this.speed.x *= -1;
		}
		
		// too much right
		if (this.position.x > this.parent.width) {
			this.position.x = 2 * this.parent.width - this.position.x;
			this.speed.x *= -1;
		}
	}
	
	private void HandleExpansion() {
		
	}
		
}
