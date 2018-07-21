package fr.coni.pursuit2;

import processing.core.PApplet;

public class Main extends PApplet{
	
	Square square; 
	private float sval = 1;
	private int depth = 100;
	
	public static void main(String[] args){
		PApplet.main("fr.coni.pursuit2.Main");
    }
	
	public void settings(){
	    size(800, 800);
	}


	public void setup() {
		square = new Square(this, depth);
	    frameRate(15);
	}

	public void draw() {
		background(255, 10);
		square.move();
		square.display(frameCount);
		if (frameCount > 200) {
			noLoop();
		}
	}
	
}
