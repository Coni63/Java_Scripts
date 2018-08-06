package fr.coni.bubble_expansion;

import processing.core.PApplet;
import processing.core.PVector;
import java.util.Random;

public class Bubble {
	
	static int radius_init;
	static int radius_ext;
	static float v_max = 5;
	private PVector position;
	private PVector speed;
	private boolean extended;
	private PApplet parent;
	private boolean is_master = false;
	private int[][] color_bubble =  {{255, 0, 0, 100},
									 {0, 0, 255, 100},
									 {0, 255, 0, 100}};
	private int color_idx;
	private long timestamp;
	private int score;
	
	Bubble(PApplet parent, int r1, int r2){
		radius_init = r1;
		radius_ext = r2;
		
		// sketch
		this.parent = parent;
		
		// create a non extended particle (with a speed and small radius) 
		this.extended = false;
		this.is_master = false;
		
		// set a random position
		Random random = new Random();
		int x_min = radius_init;
		int x_max = parent.width - radius_init;
		int y_min = radius_init;
		int y_max = parent.height - radius_init;
		int x = random.nextInt(x_max - x_min) + x_min;
		int y = random.nextInt(y_max - y_min) + y_min;
		
		this.color_idx = random.nextInt(color_bubble.length);
		
		// set a random speed
		float vx = random.nextFloat()-.5f;
		float vy = random.nextFloat()-.5f;
		position = new PVector(x, y);
		speed = new PVector(vx, vy).normalize().mult(v_max);
	}
	
	public void step() {
		this.position = PVector.add(this.position, this.speed);
		HandleBounces();
	}
	
	public void draw() {
		parent.textAlign(PApplet.CENTER, PApplet.CENTER);
		if (!this.is_master) {
			parent.stroke(color_bubble[this.color_idx][0], 
						  color_bubble[this.color_idx][1], 
						  color_bubble[this.color_idx][2]);
			parent.fill(parent.color(color_bubble[this.color_idx][0], 
									 color_bubble[this.color_idx][1], 
									 color_bubble[this.color_idx][2],
									 color_bubble[this.color_idx][3])
					                );
			if (this.extended) {
				parent.ellipse(this.position.x, this.position.y, radius_ext*2, radius_ext*2);
				parent.fill(0);
				parent.text(this.score, this.position.x, this.position.y);
			} else {
				parent.ellipse(this.position.x, this.position.y, radius_init*2, radius_init*2);
			}
		} else {
			parent.stroke(0);
			parent.fill(255, 255, 255, 100);
			parent.ellipse(this.position.x, this.position.y, radius_ext*2, radius_ext*2);
			parent.fill(0);
			parent.text(this.score, this.position.x, this.position.y);
		}
	}
	
	public void set_master(int pox_x, int pos_y) {
		this.is_master = true;
		position = new PVector(pox_x, pos_y);
		this.color_idx = 0;
		setScore(1);
		setExpanded();
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
	
	public void setExpanded() {
		speed = new PVector(0, 0);
		this.extended = true;
		this.timestamp = System.currentTimeMillis();
	}
	
	public boolean isExpanded() {
		return this.extended;
	}
	
	public PVector getPosition() {
		return this.position;
	}
	
	public long getExpandedTime() {
		return this.timestamp;
	}
	
	public void setScore(int x) {
		this.score = x;
	}
	
	public int getScore() {
		return this.score;
	}
		
}
