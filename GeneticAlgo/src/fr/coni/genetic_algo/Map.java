package fr.coni.genetic_algo;

import processing.core.PApplet;

public class Map {
	
	private int WT  = 10;
	private PApplet parent;
	private Door[] doors;
	
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
			if ( (d.x > position_mini) & (d.x < position_mini + parent.width) ) {
				parent.rect(d.x - position_mini, d.center - (d.width / 2), d.x - position_mini + WT, 0);
				parent.rect(d.x - position_mini, d.center + (d.width / 2), d.x - position_mini + WT, parent.height);
			}
		}
	}
	
}
