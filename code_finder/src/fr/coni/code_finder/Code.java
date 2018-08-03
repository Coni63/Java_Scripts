package fr.coni.code_finder;

import java.util.Arrays;
import java.util.Random;

public class Code {
	
	private float fitness;
	private int[] code;
	
	Code(int[] code) {
		this.code = code;
	}
	
	Code(int size, int base) {
		this.code = this.setCode(size, base);
	}
	
	public float getFitness() {
		return fitness;
	}

	public int[] getCode() {
		return code;
	}
	
	public int[] setCode(int size, int base) {
		Random random = new Random();
		int[] _code = new int[size];
		for (int i=0; i<size; i++) {
			_code[i] = random.nextInt(base);
		}
		return _code;
	}
	
	public float evaluate(int[] real) {
		float score = 0f;
		for (int i=0; i<this.code.length; i++) {
			if (real[i] == this.code[i]) {
				score += 1f;
			}
		}
		this.fitness = score/this.code.length;
		return this.fitness;
	}
	
	public String show() {
		return Arrays.toString(this.code);
	}

}
