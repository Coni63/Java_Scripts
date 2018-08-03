package fr.coni.code_finder;

import java.util.Arrays;

import processing.core.PApplet;

public class SmartAlgo implements Algo {

	private static int code_length_, base_;
	private int[] guess;
	private int position;
	private boolean done;
	private Code result;
	private int loop;
	
	SmartAlgo(int code_length, int base){
		code_length_ = code_length;
		base_ = base;
		this.guess = new int[code_length_];
		this.position = 0;
		this.done = false;
		this.loop= 0;
		Arrays.fill(this.guess, 0);
		this.result = new Code(code_length, base); // temporary
	}
	
	public boolean step(Code real) {
		if (position < code_length_ & this.done == false) {
			int[] current = new int[code_length_];
			float current_fitness = 0;
			float best_fitness = 0;
			int digit = -1;
			Code temp;
			Arrays.fill(current, 0);
			
			for (int i=0; i<base_; i++) {
				current[this.position] = i;
				temp = new Code(current);
				current_fitness = temp.evaluate(real.getCode());
				
				if (current_fitness > best_fitness) {
					best_fitness = current_fitness;
					digit = i;
				}
			}
			
			this.loop += base_;
			
			this.guess[this.position] = digit;
			
			this.result = new Code(this.guess);
			
			if (this.result.evaluate(real.getCode()) == 1f) {
				this.done = true;
			} else {
				this.position++;
			}
		}
		return this.done;
	}
	
	public boolean getStatus() {
		return this.done;
	}
	
	public void show() {
		System.out.println("Smart Algo found the solution in " + this.loop + "steps");
	}
	
	public void draw(Code real, PApplet parent) {
		int[] c_a = this.result.getCode();
		int[] c_b = real.getCode();
		parent.textAlign(PApplet.LEFT, PApplet.CENTER);
		for (int i = 0 ; i< code_length_ ; i++) {
			if (c_a[i] == c_b[i]) {
				parent.fill(38, 232, 0);
				parent.text(c_a[i], parent.width/4 + 10 + (i-code_length_/2)*10, 100+25+12);
			} else {
				parent.fill(255, 22, 0);
				parent.text(c_a[i], parent.width/4 + 10 + (i-code_length_/2)*10, 100+25+12);
			}
		}
		parent.fill(255, 243, 112);
		parent.textAlign(PApplet.CENTER, PApplet.CENTER);
		parent.text(this.loop, parent.width/4 + 10, 100+50+12);
		parent.text(this.result.evaluate(real.getCode()), parent.width/4 + 10, 100+75+12);
	}
}
