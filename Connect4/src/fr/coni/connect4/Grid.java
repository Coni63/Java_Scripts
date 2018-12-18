package fr.coni.connect4;

import java.util.Arrays;

public class Grid {
	public Boolean[][] grid;
	private int counter = 0;
	public int[] height = {5, 5, 5, 5, 5, 5, 5};
	public Boolean winner;
	public int[][] win_pos = new int[2][2];

	Grid() {
		this.grid = new Boolean[6][7];
	}
	
	public void set_grid(Grid other) {
		this.grid = other.grid;
	}
	
	public void set_grid(Boolean val) {
		for (int i=0; i<7; i++) {
			for (int j=0; j<6; j++) {
				this.grid[j][i] = val;
			}
		}
	}
	
	public Boolean set_value(int x, Boolean val) {
		Boolean valid = false;
		if (height[x] >= 0) {
			this.grid[height[x]][x] = val;
			valid = true;
			counter++;
			height[x]--;
		}
		return valid;
	}
	
	public void reset() {
		for (int i=0; i<7; i++) {
			for (int j=0; j<6; j++) {
				this.grid[j][i] = null;
			}
		}
		for (int i=0; i<7; i++) {
			this.height[i] = 5;
		}
		counter = 0;
	}
	
	public Boolean is_over() {
		if (counter == 42 || this.check_winner()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void show() {
		for (int j=0; j<6; j++) {
			System.out.println(Arrays.toString(this.grid[j]));
		}
	}
	
	private Boolean check_winner() {
		return check_H() || check_V() || check_D_asc() || check_D_desc();
	}
	
	private Boolean check_H() {
		for (int j=0; j<6; j++) {
			for (int i=0; i<4; i++) {
				if (this.grid[j][i] == this.grid[j][i+1] && 
					this.grid[j][i] == this.grid[j][i+2] && 
					this.grid[j][i] == this.grid[j][i+3] &&
					this.grid[j][i] != null) {
						this.win_pos[0][0] = j;
						this.win_pos[0][1] = i;
						this.win_pos[1][0] = j;
						this.win_pos[1][1] = i+3;
						this.winner = this.grid[j][i];
						return true;
				}
			}
		}
		return false;
	}
	
	private Boolean check_V() {
		for (int j=0; j<3; j++) {
			for (int i=0; i<7; i++) {
				if (this.grid[j][i] == this.grid[j+1][i] && 
					this.grid[j][i] == this.grid[j+2][i] && 
					this.grid[j][i] == this.grid[j+3][i] &&
					this.grid[j][i] != null) {
						this.win_pos[0][0] = j;
						this.win_pos[0][1] = i;
						this.win_pos[1][0] = j+3;
						this.win_pos[1][1] = i;
						this.winner = this.grid[j][i];
						return true;
				}
			}
		}
		return false;
	}
	
	private Boolean check_D_asc() {
		for (int j=5; j>2; j--) {
			for (int i=0; i<4; i++) {
				if (this.grid[j][i] == this.grid[j-1][i+1] && 
					this.grid[j][i] == this.grid[j-2][i+2] && 
					this.grid[j][i] == this.grid[j-3][i+3] &&
					this.grid[j][i] != null) {
						this.win_pos[0][0] = j;
						this.win_pos[0][1] = i;
						this.win_pos[1][0] = j-3;
						this.win_pos[1][1] = i+3;
						this.winner = this.grid[j][i];
						return true;
				}
			}
		}
		return false;
	}
	
	private Boolean check_D_desc() {
		for (int j=5; j>2; j--) {
			for (int i=3; i<7; i++) {
				if (this.grid[j][i] == this.grid[j-1][i-1] && 
					this.grid[j][i] == this.grid[j-2][i-2] && 
					this.grid[j][i] == this.grid[j-3][i-3] &&
					this.grid[j][i] != null) {
						this.win_pos[0][0] = j;
						this.win_pos[0][1] = i;
						this.win_pos[1][0] = j-3;
						this.win_pos[1][1] = i-3;
						this.winner = this.grid[j][i];
						return true;
				}
			}
		}
		return false;
	}
	
}
