package fr.coni.code_finder;

import java.util.Arrays;

import processing.core.PApplet;

public class GreedyAlgo  implements Algo {

	private int loop;
	private static int code_length_, base_, batch_size_;
	private boolean done;
	private Code result;
	private float previous_best;
	
	GreedyAlgo(int code_length, int base, int batch_size){
		code_length_ = code_length;
		base_ = base;
		batch_size_ = batch_size;
		this.loop = 0;
		this.done = false;
		this.result = new Code(code_length, base); // temporary
	}
	
	public boolean step(Code real) {
		previous_best = 0;
		float current_score = 0;
		if (this.done == false) {
			for (int i=0; i<batch_size_; i++) {
				Code temp = new Code(convertFromBaseToBase(this.loop, base_));
				current_score = temp.evaluate(real.getCode());
				if (current_score < 1f) {
					this.loop++;
				} else {
					this.result = temp;
					this.done = true;
				}
				if (current_score > previous_best) {
					previous_best = current_score;
					this.result = temp;
				}
				
			}
		}
		return this.done;
	}
	
	public static int[] convertFromBaseToBase(int step, int toBase) {
		int[] genome = new int[code_length_];
		Arrays.fill(genome, 0);
		int remaining = step;
		for (int i=0; i<code_length_; i++) {
			int a = remaining % toBase;
			genome[code_length_-i-1] = a;
			remaining -= a;
			remaining /= toBase;
		}
		return genome;
	}
	
	public boolean getStatus() {
		return this.done;
	}
	
	public void show() {
		System.out.println("Greedy Algo found the solution in " + this.loop + "steps");
	}
	
	public void draw(Code real, PApplet parent) {
		int[] c_a = this.result.getCode();
		int[] c_b = real.getCode();
		parent.textAlign(PApplet.LEFT, PApplet.CENTER);
		for (int i = 0 ; i< code_length_ ; i++) {
			if (c_a[i] == c_b[i]) {
				parent.fill(38, 232, 0);
				parent.text(c_a[i], 3*parent.width/4 -10 + (i-code_length_/2)*10, parent.height/2+25+12);
			} else {
				parent.fill(255, 22, 0);
				parent.text(c_a[i], 3*parent.width/4 -10 + (i-code_length_/2)*10, parent.height/2+25+12);
			}
		}
		parent.fill(255, 243, 112);
		parent.textAlign(PApplet.CENTER, PApplet.CENTER);
		parent.text(this.loop, 3*parent.width/4 - 10, parent.height/2+50+12);
		parent.text(this.result.evaluate(real.getCode()), 3*parent.width/4 - 10, parent.height/2+75+12);
	}
	
}