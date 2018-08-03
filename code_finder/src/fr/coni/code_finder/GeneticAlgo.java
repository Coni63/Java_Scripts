package fr.coni.code_finder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import processing.core.PApplet;

public class GeneticAlgo  implements Algo {

	private static int code_length_, base_, batch_size_;
	private boolean done;
	private int loop;
	private ArrayList<Code> population;
	private int top_size = 5;
	private float selection = 0.9f;
	private float crossover = 0.1f;
	private float mutate = 0.01f;
	private Code result;
	
	
	GeneticAlgo(int code_length, int base, int batch_size){
		code_length_ = code_length;
		base_ = base;
		batch_size_ = batch_size;
		this.done = false;
		this.loop = 0;
		this.population = new ArrayList<Code>();
		this.result = new Code(code_length, base); // temporary
		this.complete();
	}
	
	public boolean step(Code real) {
		if (!this.done) {
			this.evaluate_population(real.getCode()); // we can increase speed by evaluating them when we generate them but for now we can keep it as is
			if (this.population.get(this.population.size()-1).getFitness() == 1f) {
				this.done = true;
				this.result = this.population.get(this.population.size()-1);
			} else {
				this.make_selection();
				this.make_crossover();
				this.make_mutation();
				this.complete();
			}
		}
		
		return this.done;
	}
	
	private void complete() {
		while (this.population.size() < batch_size_) {
			this.population.add(new Code(code_length_, base_));
			this.loop ++;
		}
	}
	
	private void evaluate_population(int[] real) {
		for (Code c: this.population) {
			c.evaluate(real);
		}
		this.population.sort(Comparator.comparing(Code::getFitness));
		this.result = this.population.get(this.population.size()-1); // store best one
	}
	
	private void make_selection() {
		for (int i= (this.population.size() - this.top_size - 1) ; i >=0 ; i-- ) {
			if (Math.random() < this.selection) {
				if (Math.random() > this.population.get(i).getFitness()) {
					this.population.remove(i);
				}
			}
		}
	}
	
	private void make_crossover() {
		Random random = new Random();
		int a, b, idx;
		int[] gen_a, gen_b;
		int[] gen_c = new int[code_length_];
		int[] gen_d = new int[code_length_];
		for (int i=0 ; i < this.population.size() ; i++ ) {
			if (Math.random() < this.crossover) {
				a = random.nextInt(this.population.size());
				b = random.nextInt(this.population.size());
				while (b==a) {
					b = random.nextInt(this.population.size());
				}
				idx = random.nextInt(code_length_-2)+1;
				gen_a = this.population.get(a).getCode();
				gen_b = this.population.get(b).getCode();
				for (int j=0; j<code_length_ ;j++) {
					if (j<=idx) {
						gen_c[j] = gen_a[j];
						gen_d[j] = gen_b[j];
					} else {
						gen_c[j] = gen_b[j];
						gen_d[j] = gen_a[j];
					}
				}
				this.population.add(new Code(gen_c));
				this.population.add(new Code(gen_d));
				this.loop +=2;
			}
		}
		
	}
	
	private void make_mutation() {
		Random random = new Random();
		int idx, val;
		int[] gen_a;
		for (int i=0 ; i < this.population.size() ; i++ ) {
			if (Math.random() < this.mutate) {
				idx = random.nextInt(code_length_);
				val = random.nextInt(base_);
				gen_a = this.population.get(i).getCode();
				gen_a[idx] = val;
				this.population.add(new Code(gen_a));
				this.loop ++;
			}
		}
	}
	
	public boolean getStatus() {
		return this.done;
	}
	
	public void show() {
		System.out.println("Genetic Algo found the solution in " + this.loop + "steps");
	}
	
	public void draw(Code real, PApplet parent) {
		int[] c_a = this.result.getCode();
		int[] c_b = real.getCode();
		parent.textAlign(PApplet.LEFT, PApplet.CENTER);
		for (int i = 0 ; i< code_length_ ; i++) {
			if (c_a[i] == c_b[i]) {
				parent.fill(38, 232, 0);
				parent.text(c_a[i], 3*parent.width/4 -10 + (i-code_length_/2)*10, 100+25+12);
			} else {
				parent.fill(255, 22, 0);
				parent.text(c_a[i], 3*parent.width/4 -10 + (i-code_length_/2)*10, 100+25+12);
			}
		}
		parent.fill(255, 243, 112);
		parent.textAlign(PApplet.CENTER, PApplet.CENTER);
		parent.text(this.loop, 3*parent.width/4 - 10, 100+50+12);
		parent.text(this.result.evaluate(real.getCode()), 3*parent.width/4 - 10, 100+75+12);
	}
}