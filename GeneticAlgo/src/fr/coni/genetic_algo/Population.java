package fr.coni.genetic_algo;

import processing.core.PApplet;

public class Population {
	
	private int population_size;
	private float mutation_rate;
	private float crossover_rate;
	private float selection_rate;
	private Candidate[] population;
	private PApplet parent;
	
	Population(PApplet p, int population_size, float selection_rate, float crossover_rate, float mutation_rate ) {
		parent = p;
		this.population_size = population_size;
		this.mutation_rate = mutation_rate;
		this.crossover_rate = crossover_rate;
		this.selection_rate = selection_rate;
		this.population = new Candidate[this.population_size];
		for (int i=0 ; i<this.population_size ;i++) {
			this.population[i] = new Candidate();
		}
	}
	
	public int[] getRange() {
		int min_x = 0;
		int max_x = 0;
		for (Candidate c : this.population) {
			min_x = Math.min(min_x, c.getX()); 
			max_x = Math.max(max_x, c.getX()); 
		}
		int[] A =  {min_x, max_x};
		return A;
	}
	
	public void step() {
		for (Candidate c : this.population) {
			c.move();
		}
	}
	
	public void draw() {
		parent.rectMode(PApplet.CENTER);
		parent.fill(128, 50);
		for (Candidate c : this.population) {	
			parent.translate(c.getX(), c.getY());
			parent.rotate(c.getAngle());
			parent.rect(0, 0, c.getWidth(), c.getThickness());
			parent.rotate(-c.getAngle());
			parent.translate(-c.getX(), -c.getY());
		}
	}


}
