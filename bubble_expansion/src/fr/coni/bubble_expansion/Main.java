package fr.coni.bubble_expansion;

import processing.core.PApplet;
import java.util.ArrayList;

public class Main extends PApplet{

	private float FrameRate = 15f;
	private ArrayList<Bubble> bubbles = new ArrayList<Bubble>();
	
	public static void main(String[] args) {
		PApplet.main("fr.coni.bubble_expansion.Main");
	}
	
	public void settings(){
	    size(800, 450);
	}


	public void setup() {
		frameRate(FrameRate);
		for (int i=0; i<10; i++) {
			bubbles.add(new Bubble(this));
		}
	}

	public void mousePressed() {
		
	}
	
	public void draw() {
		background(255);
		for (Bubble bubble: bubbles) {
			bubble.step();
			bubble.draw();
		}
	}

}
