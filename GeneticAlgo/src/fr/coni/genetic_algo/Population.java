package fr.coni.genetic_algo;

import java.util.ArrayList;
//import processing.core.PApplet;
import java.util.Random;
import java.util.Comparator;

public class Population {
	
	public int generation;
	private int population_size;
	private float mutation_rate;
	private float crossover_rate;
	private float selection_rate;
	private int current_ID;
	private Map map;
	private float frameRate;
	public ArrayList<Candidate> population;
	public Candidate best_one;
	
	Population(int population_size, float selection_rate, float crossover_rate, float mutation_rate, Map map, float frameRate) {
		this.population_size = population_size;
		this.generation = 1;
		this.mutation_rate = mutation_rate;
		this.crossover_rate = crossover_rate;
		this.selection_rate = selection_rate;
		this.current_ID = 0;
		this.map = map;
		this.frameRate = frameRate;
		//this.population = new Candidate[this.population_size];
		this.population = new ArrayList<Candidate>();
		this.complete();
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
	
	public boolean step() {
		//System.out.println("");
		best_one = this.population.get(0);
		float[] actions = new float[2]; 
		boolean still_active = false;
		for (Candidate c : this.population) {
			c.best = false; // to check if best every turn
			if (c.is_active == true) {
				still_active = true;
				//System.out.println(c.ID + " - " + c.remaining_life);
				c.UpdateTarget();
				actions = c.getAction();
				c.move(actions);
				c.UpdateTarget();
				if (c.pos.x > best_one.pos.x) {
					best_one = c;
				}
			}
		}
		
		best_one.best = true;
		/*
		if (still_active == false) {
			for (Candidate c : this.population) {
				System.out.println(c.ID + " - " + c.fitness);
			}
		}
		*/
		return still_active;
	}
	
	public void prepare() {
		this.population.sort(Comparator.comparing(Candidate::getFitness));
		for (Candidate c: this.population) {
			System.out.println(c.ID + " -> " + c.fitness);
		}
		System.out.println("");
	}
	
	public void select() {
		Random random = new Random();
		for (int i = this.population.size()-4 ; i>=0 ; i--) {
			if ( random.nextFloat() > this.selection_rate) { //this.population.get(i).fitness ) {
				this.population.remove(i);
			}			
		}
	}
	
	public void cross() {
		Random random = new Random();
		if ( this.population.size() > 1) {
			for(int i = 0; i < this.population.size()-1 ; i+=2) {
				if (random.nextFloat() < this.crossover_rate) {
					float[] gen_a = this.population.get(i).getGenome();
					float[] gen_b = this.population.get(i+1).getGenome();
					for (int j = 0; j<gen_a.length ; j++) {
						if(random.nextFloat() > 0.5) {
							float temp = gen_a[j];
							gen_a[j] = gen_b[j];
							gen_b[j] = temp;
						}
					}
					Candidate temp1 = new Candidate(this.current_ID, this.frameRate, this.map);
					temp1.setGenome(gen_a);
					Candidate temp2 = new Candidate(this.current_ID, this.frameRate, this.map);
					temp2.setGenome(gen_b);
					this.population.add( temp1 );
					this.population.add( temp2 );
					this.current_ID +=2 ;
				}
			}
		}
	}

	public void mutate() {
		Random random = new Random();
		
		for (Candidate c : this.population) {
			if (random.nextFloat() < this.mutation_rate) {
				float[] gen_a = c.getGenome();
				int idx = random.nextInt(gen_a.length);
				gen_a[idx] *= (float)(1 + (Math.random()-0.5)/10);
				//Candidate temp1 = new Candidate(this.current_ID, this.frameRate, this.map);
				//		  temp1.setGenome(gen_a); 
				//this.population.add( temp1 );
				//this.current_ID++;
			}
		}
		
		
	}
	
	public void complete() {
		while (this.population.size() < this.population_size) {
			this.population.add( new Candidate(this.current_ID, this.frameRate, this.map) );
			this.current_ID ++;
		}
	}
	
	public void reset() {
		for (Candidate c : this.population) {
			c.reset();
		}
		this.generation++;
	}
}


