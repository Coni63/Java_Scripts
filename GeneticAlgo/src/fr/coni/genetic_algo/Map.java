package fr.coni.genetic_algo;

import java.awt.geom.Point2D;

import processing.core.PApplet;

public class Map {

	private PApplet parent;
	public Door[] doors;
	
	Map(PApplet p, int seed){
		parent = p;	
		doors = new Door[100];
	}
	
	void generate(){
		for (int i=0 ; i<doors.length ; i++) {
			doors[i] = new Door(parent.height, 200 * i);
		}
	}
	
	void draw(int position_mini) {
		parent.rectMode(PApplet.CORNERS);
		parent.fill(0);
		for (Door d : doors) {
			d.draw(parent, position_mini);
		}
	}
}
