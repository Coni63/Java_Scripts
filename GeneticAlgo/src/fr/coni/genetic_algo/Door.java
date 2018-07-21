package fr.coni.genetic_algo;

import java.util.Random;

public class Door {

	int width;
	int center;
	int x;
	
	Door(int screen_height, int x){
		Random random = new Random();
		this.width = random.nextInt(50) + 100;
		this.center = random.nextInt(screen_height-this.width) + this.width/2;
		this.x = x + random.nextInt(50);
		System.out.println("Door of " + this.width + " px at height " + this.center + " | x = " + this.x);
	}
	
}
