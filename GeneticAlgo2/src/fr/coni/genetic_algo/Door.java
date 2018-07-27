package fr.coni.genetic_algo;

import java.util.Random;

public class Door {
	
	int width;
	int center;
	int x;
	
	Door(int y_max, int x){
		Random random = new Random();
		this.width = random.nextInt(10) + 20;
		this.center = random.nextInt(y_max-this.width) + this.width/2;
		this.x = x + random.nextInt(20);
		
		//System.out.println("Door of " + this.width + " px at height " + this.center + " | x = " + this.x);
	}	
	
}
