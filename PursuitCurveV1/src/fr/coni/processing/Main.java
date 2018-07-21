package fr.coni.processing;

import processing.core.PApplet;

public class Main extends PApplet{
	
	Square square; 
	float sval = 1;
	
	public static void main(String[] args){
		PApplet.main("fr.coni.processing.Main");
    }
	
	public void settings(){
	    size(800, 800);
	}


	public void setup() {
		square = new Square(this);
	    frameRate(4);
	}

	public void draw() {
		//background(255, 10);
		//zoom_middle
		square.display(frameCount);
		square.move();
		if (frameCount > 100) {
			noLoop();
		}
	}
	
	public void zoom_middle() {
		sval += 0.05;
		translate(width/2,height/2); // use translate around scale
		scale(sval);
		translate(-width/2,-height/2); // to scale from the center
	}
	
}
