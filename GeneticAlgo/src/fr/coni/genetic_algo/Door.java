package fr.coni.genetic_algo;

import processing.core.PApplet;
import java.util.Random;

public class Door {
	
	private int WT  = 10;
	int width;
	int center;
	int x;
	
	Door(int screen_height, int x){
		Random random = new Random();
		this.width = random.nextInt(50) + 100;
		this.center = random.nextInt(screen_height-this.width) + this.width/2;
		this.x = x + random.nextInt(50);
		
		//System.out.println("Door of " + this.width + " px at height " + this.center + " | x = " + this.x);
	}
	
	public void draw(PApplet parent, int position_mini) {
		if ( (this.x > position_mini) & (this.x < position_mini + parent.width) ) {
			parent.rect(this.x - position_mini, this.center - (this.width / 2), this.x - position_mini + WT, 0);
			parent.rect(this.x - position_mini, this.center + (this.width / 2), this.x - position_mini + WT, parent.height);
		}
	}
	
	
}
