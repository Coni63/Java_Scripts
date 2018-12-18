package fr.coni.connect4;

import java.util.Random;
import fr.coni.connect4.Grid;

public class Agent {
	
	private Grid grid;
	Random rand = new Random();
	
	Agent() {
		
	}
	
	public void play(Grid board) {
		int num = 0;
		Boolean valid = false;
		while (!valid) {
			num = rand.nextInt(7);
			valid = board.set_value(num, true);
		}
	}
	
}
