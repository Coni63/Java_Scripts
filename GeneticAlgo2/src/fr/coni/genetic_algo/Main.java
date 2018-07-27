package fr.coni.genetic_algo;

import processing.core.PApplet;


public class Main extends PApplet{
	
	private Map map;
	private Population population;
	public float FrameRate = 15f;
	private float scale = 10f;
	public int hmax;
	private float x_min, x_max;
	private int WT = 1;
		
	
	public static void main(String[] args) {
		PApplet.main("fr.coni.genetic_algo.Main");
	}

	public void settings(){
	    size(1500, 800);
	    hmax = (int)(height / scale);
	    x_min = 0;
	    x_max = width / scale + x_min;
	}


	public void setup() {
		frameRate(FrameRate);
		rectMode(CORNERS);
		map = new Map(hmax);
		map.generate();
		
		population = new Population(1, 0.5f, 0.1f, 0.05f, map, FrameRate);
	}

	public void draw() {

		//long startTime = System.nanoTime();
		population.step();
		//long endTime = System.nanoTime();
		//long duration = (endTime - startTime); 
		//System.out.println(duration/1000);
		
		background(255);
		
		// draw walls
		rectMode(PApplet.CORNERS);
		fill(0);
				
		for (Door d : map.doors) {
			rect(to_pixel_x(d.x), to_pixel_y(d.center - (d.width / 2)), to_pixel_x(d.x + WT), to_pixel_y(0));
			rect(to_pixel_x(d.x), to_pixel_y(d.center + (d.width / 2)), to_pixel_x(d.x + WT), to_pixel_y(hmax));
		}
		
		// draw candidates
		rectMode(PApplet.CENTER);
		fill(128, 50);
		for (Candidate c : population.population) {
			translate(to_pixel_x(c.x), to_pixel_y(c.y));
			rotate(c.angle);
			rect(0, 0, c.h*scale, c.b*scale);
			rotate(-c.angle);
			translate(to_pixel_x(-c.x), to_pixel_y(-c.y));
			
			ellipse(to_pixel_x((float)c.corners[0].getX()), to_pixel_y((float)c.corners[0].getY()), 5f, 5f);
			ellipse(to_pixel_x((float)c.corners[1].getX()), to_pixel_y((float)c.corners[1].getY()), 5f, 5f);
			ellipse(to_pixel_x((float)c.corners[2].getX()), to_pixel_y((float)c.corners[2].getY()), 5f, 5f);
			ellipse(to_pixel_x((float)c.corners[3].getX()), to_pixel_y((float)c.corners[3].getY()), 5f, 5f);
		}
		
		// draw HUD
		Candidate c = population.population[0];
		rectMode(PApplet.CORNER);
		fill(255, 255, 255);
		rect(0, 0, 180, 60);
		textSize(10);
		fill(0, 0, 0);
		text("Position : (" + String.format("%.02f", c.x) + ", " + String.format("%.02f", c.y) + ")", 10, 10);
		text("Speed : " + String.format("%.02f", c.speed) + " m/s (" + String.format("%.02f", c.speed * 3.6) + " km/h)" , 10, 20);
		text("Acc : " + String.format("%.02f", c.a.mag()) , 10, 30);
		text("Angle : " + String.format("%.02f", c.angle * 180 / Math.PI) , 10, 40);
		text("Friction : " + String.format("%.02f", c.air_friction_force + c.friction_force) , 10, 50);
		
		if (frameCount > 1000) {
			noLoop();
		}
		
		saveFrame("F:/java_rendering/output/img_#####.png");
	}
	
	private float to_pixel_x(float x){
		return (x - x_min) * scale;
	}
	
	private float to_pixel_y(float y){
		return y * scale;
	}
	
	
}
