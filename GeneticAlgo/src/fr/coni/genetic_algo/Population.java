package fr.coni.genetic_algo;

import processing.core.PApplet;

public class Population {
	
	private int population_size;
	private float mutation_rate;
	private float crossover_rate;
	private float selection_rate;
	private Candidate[] population;
	private PApplet parent;
	
	Population(PApplet p, int population_size, float selection_rate, float crossover_rate, float mutation_rate, Map map ) {
		parent = p;
		this.population_size = population_size;
		this.mutation_rate = mutation_rate;
		this.crossover_rate = crossover_rate;
		this.selection_rate = selection_rate;
		this.population = new Candidate[this.population_size];
		for (int i=0 ; i<this.population_size ;i++) {
			this.population[i] = new Candidate(p.frameRate, map);
		}
	}
	
	public float[] getRange() {
		float min_x = 0;
		float max_x = 0;
		for (Candidate c : this.population) {
			min_x = Math.min(min_x, c.getX()); 
			max_x = Math.max(max_x, c.getX()); 
		}
		float[] A =  {min_x, max_x};
		return A;
	}
	
	public void step() {
		float[] actions = new float[2]; 
		for (Candidate c : this.population) {
			
			c.UpdateTarget();
			actions = c.getAction();
			c.move(actions);
			c.UpdateTarget();
		}
	}
	
	public void draw() {
		parent.rectMode(PApplet.CENTER);
		parent.fill(128, 50);
		for (Candidate c : this.population) {
			c.draw(parent);
		}
		this.population[0].displayHUD(parent);
	};


}
