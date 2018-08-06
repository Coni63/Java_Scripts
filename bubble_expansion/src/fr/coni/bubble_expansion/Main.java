package fr.coni.bubble_expansion;

import processing.core.PApplet;
import processing.core.PVector;
import controlP5.*;
import java.util.ArrayList;

public class Main extends PApplet{

	private float FrameRate = 60f;
	private ArrayList<Bubble> bubbles = new ArrayList<Bubble>();
	private boolean launched = false;
	private int max_duration;
	private int nb_balls;
	private long score = 0;
	private int radius_init;
	private int radius_ext;
	
	ControlP5 cp5;
	Accordion accordion;
	
	public static void main(String[] args) {
		PApplet.main("fr.coni.bubble_expansion.Main");
	}
	
	public void settings(){
	    size(1920, 1080);
	}

	public void setup() {
		frameRate(FrameRate);
		makeGUI();
		Reset();
	}

	public void mousePressed() {
		if (!this.launched) {
			Bubble main_bubble = new Bubble(this, radius_init, radius_ext);
				   main_bubble.set_master(mouseX, mouseY);
			System.out.println(this.bubbles.size());
			this.bubbles.add(main_bubble);
			System.out.println(this.bubbles.size());
			this.launched = true;
			this.score += main_bubble.getScore();
		}
	}
	
	public void draw() {
		background(255);
		stroke(0);
		
		Clear_bubble();
		HandleExpansion();

		if (!this.launched) {
			Bubble main_bubble = new Bubble(this, radius_init, radius_ext);
			main_bubble.set_master(mouseX, mouseY);
			main_bubble.draw();
		}
		
		for (Bubble bubble: this.bubbles) {
			bubble.step();
			bubble.draw();
		}
		
		textAlign(LEFT, TOP);
		fill(0);
		text("Score : " + this.score, 0, 0);
	}
	
	private void HandleExpansion() {
		// for each non expanded bubble
		for (int i=0 ; i < this.bubbles.size() ; i++) {
			if ( !this.bubbles.get(i).isExpanded() ) {
				// we check distance to each expanded bubble
				for (int j=0 ; j < this.bubbles.size(); j++) {
					if ( this.bubbles.get(j).isExpanded() ) {
						float distance = distsq( this.bubbles.get(i).getPosition(), this.bubbles.get(j).getPosition() );
						float threshold = (float)Math.pow(Bubble.radius_ext + Bubble.radius_init, 2);
						if ( distance < threshold ) {
							this.bubbles.get(i).setExpanded();
							int new_score = this.bubbles.get(j).getScore() * 2;
							this.bubbles.get(i).setScore(new_score);
							this.score += new_score;
							break;
						}
					}
				}
			}
		}
	}
	
	private void Clear_bubble() {
		// remove bubble after a certain time
		for (int i=this.bubbles.size() - 1 ; i >= 0; i--) {
			if ( this.bubbles.get(i).isExpanded() == true ) {
				long duration = System.currentTimeMillis() - this.bubbles.get(i).getExpandedTime();
				if (duration > max_duration*1000) {
					this.bubbles.remove(i);
				}
			}
		}
	}
	
	private float distsq(PVector a, PVector b) {
		return (float)(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
	}
	
	public void makeGUI(){
		cp5 = new ControlP5(this);

		cp5.addFrameRate().setInterval(10).setColor(0).setPosition(width-20, 10);
		
		Group g1 = cp5.addGroup("UI")
					  .setBackgroundColor(color(0, 64))
					  .setBackgroundHeight(150);
		
		  accordion = cp5.addAccordion("acc")
	                 .setPosition(width-150,0)
	                 .setWidth(150)
	                 .addItem(g1);
				  		     
		  cp5.addSlider("Balls")
		     .setPosition(10,10)
		     .setSize(100,20)
		     .setRange(1,250)
		     .setValue(15)
		     .moveTo(g1);
		     
		  cp5.addSlider("R1")
		     .setPosition(10,40)
		     .setSize(100,20)
		     .setRange(3,30)
		     .setValue(10)
		     .setColorLabel(0)
		     .moveTo(g1);
		  
		  cp5.addSlider("R2")
		     .setPosition(10,70)
		     .setSize(100,20)
		     .setRange(5,150)
		     .setValue(25)
		     .setColorLabel(0)
		     .moveTo(g1);
		  
		  cp5.addSlider("Duration")
		     .setPosition(10,100)
		     .setSize(100,20)
		     .setRange(1,15)
		     .setValue(5)
		     .setColorLabel(0)
		     .moveTo(g1);
		  
		  cp5.addButton("Reset")
		     .setValue(0)
		     .setPosition(10,130)
		     .setSize(100,20)
		     .moveTo(g1);

	}
	
	public void Balls(int balls) {
		nb_balls = balls;
		Reset();
	}
	
	public void R1(int r1) {
		radius_init = r1;
		Reset();
	}
	
	public void R2(int r2) {
		radius_ext = r2;
		Reset();
	}
	
	public void Duration(int duration) {
		max_duration = duration;
		Reset();
	}
	
	public void Reset() {
		this.bubbles.clear();
		for (int i=0; i<nb_balls; i++) {
			bubbles.add(new Bubble(this, radius_init, radius_ext));
		}
		this.launched = false;
	}
	
}
