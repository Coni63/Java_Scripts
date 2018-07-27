package fr.coni.genetic_algo;

import processing.core.PApplet;
import processing.core.PVector;

public class Main extends PApplet{
	
	private Map map;
	private Population population;
	public float FrameRate = 15f;
	private float scale = 10f;
	public int hmax;
	private float x_min, x_max;
	private int WT = 1;
	private boolean status = false;
	
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
		
		population = new Population(10, 0.5f, 0.1f, 0.05f, map, FrameRate);
	}

	public void draw() {
		
		
		long startTime = System.nanoTime();
		status = population.step();
		long endTime = System.nanoTime();
		long duration = (endTime - startTime); 
		//System.out.println(duration/1000);
		
		
		if (status == true) {
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
				if (c.best == true) {
					fill(255, 0, 0);
				} else {
					fill(128, 50);
				}
				
				translate(to_pixel_x(c.pos.x), to_pixel_y(c.pos.y));
				rect(0, 0, c.h*scale, c.w*scale);
				translate(to_pixel_x(-c.pos.x), to_pixel_y(-c.pos.y));
				line(to_pixel_x(c.pos.x), to_pixel_y(c.pos.y), to_pixel_x(map.doors[c.current_door].x), to_pixel_y(map.doors[c.current_door].center));
				PVector F = c.a.copy().normalize();
				drawArrow(
							to_pixel_x(c.pos.x), 
							to_pixel_y(c.pos.y),
							to_pixel_x(c.pos.x + F.x), 
							to_pixel_y(c.pos.y + F.y)
						);
			
			}
			
			// draw HUD
			Candidate c = population.best_one;
			rectMode(PApplet.CORNER);
			fill(255, 255, 255);
			rect(0, 0, 180, 60);
			textSize(10);
			fill(0, 0, 0);
			text("Position : (" + String.format("%.02f", c.pos.x) + ", " + String.format("%.02f", c.pos.y) + ")", 10, 10);
			text("Speed : " + String.format("%.02f", c.v.mag()) + " m/s (" + String.format("%.02f", c.v.mag() * 3.6) + " km/h)" , 10, 20);
			text("Acc : " + String.format("%.02f", c.a.mag()) , 10, 30);
			//text("Angle : " + String.format("%.02f", c.angle * 180 / Math.PI) , 10, 40);
			text("Friction : " + String.format("%.02f", c.air_friction_force + c.friction_force) , 10, 40);
			text("Computation time : " + String.format("%.02f", (float)duration/1000) + "ms" , 10, 50);
			
			textSize(30);
			rectMode(PApplet.CENTER);
			fill(255, 255, 255, 128);
			rect(width/2, 30, 280, 40);
			fill(0, 0, 0);
			text("Generation : " + population.generation , width/2-130, 40);
			
		} else {
			population.prepare();
			population.select();
			population.cross();
			population.mutate();
			population.complete();
			map.generate();
			population.reset();
			//noLoop();
		}
		
		saveFrame("F:/java_rendering/output/img_#####.png");
	}
		
	void drawArrow(float x1, float y1, float x2, float y2) {
		  float a = dist(x1, y1, x2, y2) / 50;
		  pushMatrix();
		  translate(x2, y2);
		  rotate(atan2(y2 - y1, x2 - x1));
		  triangle(- a * 2 , - a, 0, 0, - a * 2, a);
		  popMatrix();
		  line(x1, y1, x2, y2);  
	}
	
	private float to_pixel_x(float x){
		return (x - x_min) * scale;
	}
	
	private float to_pixel_y(float y){
		return y * scale;
	}
	
	
}
