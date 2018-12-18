package fr.coni.connect4;

import fr.coni.connect4.Grid;
import fr.coni.connect4.Agent;
import processing.core.PApplet;

public class Main extends PApplet{

	private static final float FrameRate = 20f;
	static Grid board = new Grid();
	Boolean turn = false;
	Agent agent = new Agent();
	Boolean game_over = false;

	public static void main(String[] args) {
		PApplet.main("fr.coni.connect4.Main");
	}
	
	public void settings(){
	    size(700, 600);
	}

	public void setup() {
		frameRate(FrameRate);
	}
	
	public void draw() {
		/*
		if (turn == true && !game_over) {
			agent.play(board);
			turn = !turn;
			this.game_over = board.is_over()
			if () {
				board.reset();
			}
		}
		*/
		
		draw_layout();
		if (game_over) {
			draw_result();
		} else {
			show_mouse();
		}
		
	}
	
	public void keyPressed() {
		char ch = 'r';
		if (key == ch) {
			board.reset();
		}
	}
	
	public void mouseClicked() {
		if (!game_over) {
			Boolean valid = false;
			int col = mouseX/100;
			valid = board.set_value(col, turn);
			if (valid) {
				turn = !turn;
				this.game_over = board.is_over();
			}		
		}
	}
	
	private void draw_layout() {
		for (int i=0; i<7; i++) {
			for (int j=0; j<6; j++) {
				if (board.grid[j][i] == null) {
					fill(0,0,0);
				} else if (board.grid[j][i] == false) {
					fill(255,0,0);
				} else if (board.grid[j][i] == true) {
					fill(0,0,255);
				}
				ellipse(i*100+50, j*100+50, 80, 80);
			}
		}
	}
	
	private void draw_result() {
		int a = board.win_pos[0][0] * 100 + 50;
		int b = board.win_pos[0][1] * 100 + 50;
		int c = board.win_pos[1][0] * 100 + 50;
		int d = board.win_pos[1][1] * 100 + 50;
		
		strokeWeight(10);
		if (board.winner) {  // bot win
			stroke(255, 0, 0);
		} else {
			stroke(0, 255, 0);
		}
		line(b, a, d, c);
		strokeWeight(1);
		stroke(0, 0, 0);
	}
	
	private void show_mouse() {
		int col = mouseX/100;
		int row = board.height[col];
		if (turn == false) {
			fill(255, 0, 0, 100);
		} else if (turn == true) {
			fill(0, 0, 255, 100);
		}
		ellipse(col*100+50, row*100+50, 80, 80);
	}
	
}
