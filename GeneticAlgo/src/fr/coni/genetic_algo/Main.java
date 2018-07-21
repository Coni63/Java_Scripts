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
	int temp = 0;
		
	public static void main(String[] args) {
		PApplet.main("fr.coni.genetic_algo.Main");
	}

	public void settings(){
	    size(800, 800);
	}


	public void setup() {
		frameRate(15);
		rectMode(CORNERS);
		map = new Map(this, 42);
		population = new Population(this, 10, 0.5f, 0.1f, 0.05f);
		
		map.generate();
	}

	public void draw() {
		background(255);
		
		map.draw(temp);
		
		population.step();
		population.draw();
		
		temp += 10;
		
		if (frameCount > 100) {
			noLoop();
		}
	}
	
}
