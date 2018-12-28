package fr.coni.connect4;

class MiniMax {
	
	//static int indexOfBestMove;
	static int depth;
	
    MiniMax() {}

    static int run (Boolean player, Grid board, int maxPly) {
    	depth = maxPly;
        return miniMax(player, board, maxPly-1);
    }

    private static int miniMax (Boolean player, Grid board, int currentPly) {
        if (currentPly == 0 || board.is_over()) {
            return board.longest_sub(player);
        }

        if (player == true) {  // the bot has to maximize the score
            return getMax(player, board, currentPly);
        } else {
            return getMin(player, board, currentPly);
        }
        
    }

    private static int getMax (Boolean player, Grid board, int currentPly) {
        int bestScore = 0;

        for (int i = 0; i < 7 ; i++) {
        	
        	//System.out.println(board);
            Grid modifiedBoard = (Grid) board.clone();
        	//Grid modifiedBoard = new Grid();
        	//modifiedBoard.copy(board);
            Boolean valid = modifiedBoard.set_value(i, player);
            modifiedBoard.show();
            
			if (valid == true) {
				int score = miniMax(!player, modifiedBoard, currentPly - 1);
				if (score >= bestScore) {
	                bestScore = score;
	            }
			}
        }
        return bestScore;
    }

    private static int getMin (Boolean player, Grid board, int currentPly) {
        int bestScore = 5;

        for (int i = 0; i < 7 ; i++) {

        	//System.out.println(board);
            Grid modifiedBoard = (Grid) board.clone();
        	//Grid modifiedBoard = new Grid();
        	//modifiedBoard.copy(board);
        	
            Boolean valid = modifiedBoard.set_value(i, player);
            modifiedBoard.show();
            
            if (valid == true) {
				int score = miniMax(!player, modifiedBoard, currentPly - 1);
	            if (score <= bestScore) {
	                bestScore = score;
	            }
            }

        }
        return bestScore;
    }

}
