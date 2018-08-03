package fr.coni.code_finder;

import processing.core.PApplet;

public class RandomAlgo implements Algo {

	private static int code_length_, base_, batch_size_;
	private boolean done;
	private int loop;
	private Code result;
	
	RandomAlgo(int code_length, int base, int batch_size){
		code_length_ = code_length;
		base_ = base;
		batch_size_ = batch_size;
		this.done = false;
		this.loop = 0;
		this.result = new Code(code_length_, base_);
	}
	
	public boolean step(Code real) {
		for (int i=0; i<batch_size_; i++) {
			Code temp = new Code(code_length_, base_);
			this.result = temp;
			if (temp.evaluate(real.getCode()) < 1f) {
				this.loop++;
			} else {
				this.done = true;
			}
		}
		
		return this.done;
	}
	
	public boolean getStatus() {
		return this.done;
	}
	
	public void show() {
		System.out.println("Random Algo found the solution in " + this.loop + "steps");
	}
	
	public void draw(Code real, PApplet parent) {
		int[] c_a = this.result.getCode();
		int[] c_b = real.getCode();
		parent.textAlign(PApplet.LEFT, PApplet.CENTER);
		for (int i = 0 ; i< code_length_ ; i++) {
			if (c_a[i] == c_b[i]) {
				parent.fill(38, 232, 0);
				parent.text(c_a[i], parent.width/4 + 10 + (i-code_length_/2)*10, parent.height/2+25+12);
			} else {
				parent.fill(255, 22, 0);
				parent.text(c_a[i], parent.width/4 + 10 + (i-code_length_/2)*10, parent.height/2+25+12);
			}
		}
		parent.fill(255, 243, 112);
		parent.textAlign(PApplet.CENTER, PApplet.CENTER);
		parent.text(this.loop, parent.width/4 + 10, parent.height/2+50+12);
		parent.text(this.result.evaluate(real.getCode()), parent.width/4 + 10, parent.height/2+75+12);
	}
	
}