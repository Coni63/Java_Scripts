package fr.coni.connect4;

import fr.coni.connect4.Grid;

public class Agent {
	
	Agent() {}
	
	public void play_minimax(Grid board) {
		//board.show();
		minimax(board, 3, true);
	}
	
	private int minimax(Grid currentPosition, int depth, boolean maximizingTeam) {
		boolean valid = false;
		int eval = 0;
		
		if (depth == 0 || currentPosition.is_over()) {
			int l = currentPosition.longest_sub(maximizingTeam);
			return l;
		}
		
		if (maximizingTeam == true) {
			int maxEval = -1;
			for (int i = 0; i < 7; i++) {
				Grid newcurrentPosition = (Grid) currentPosition.clone();
				valid = newcurrentPosition.set_value(i, maximizingTeam);
				if (valid == true) {
					eval = minimax(newcurrentPosition, depth - 1, false);
					maxEval = Math.max(maxEval, eval);
				}
			}
			return maxEval;
		} else {
			int minEval = 5;
			for (int i = 0; i < 7; i++) {
				Grid newcurrentPosition = (Grid) currentPosition.clone();
				valid = newcurrentPosition.set_value(i, maximizingTeam);
				if (valid == true) {
					eval = minimax(newcurrentPosition, depth - 1, true);
					minEval = Math.min(minEval, eval);
				}
			}
			return minEval;
		}
	}
	
}
