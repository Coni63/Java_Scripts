package fr.coni.genetic_algo;

import processing.core.PApplet;

/*
import org.jbox2d.collision.Distance;
import org.jbox2d.collision.TimeOfImpact;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;
*/

public class Main extends PApplet{
	
	private Map map;
	private Population population;
	public int FrameRate = 15;
		
	public static void main(String[] args) {
		PApplet.main("fr.coni.genetic_algo.Main");
	}

	public void settings(){
	    size(1500, 800);
	}


	public void setup() {
		frameRate(FrameRate);
		rectMode(CORNERS);
		map = new Map(this, 42);
		population = new Population(this, 1, 0.5f, 0.1f, 0.05f, map);
		
		map.generate();
	}

	public void draw() {

		//long startTime = System.nanoTime();
		population.step();
		//long endTime = System.nanoTime();
		//long duration = (endTime - startTime); 
		//System.out.println(duration/1000);
		
		background(255);
		map.draw(0);
		population.draw();
		
		if (frameCount > 1000) {
			noLoop();
		}
		
		//saveFrame("F:/java_rendering/output/img_#####.png");
	}
	
}
