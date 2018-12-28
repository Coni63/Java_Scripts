package fr.coni.connect4;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.Iterator;

import fr.coni.connect4.Coordinate;

public class Grid implements Cloneable{
	public Boolean[][] grid;
	public int counter = 0;
	public int[] height = {5, 5, 5, 5, 5, 5, 5};
	public Boolean winner;
	public int[][] win_pos = new int[2][2];
	public Set<Coordinate> pos_true = new HashSet<Coordinate>();
	public Set<Coordinate> pos_false = new HashSet<Coordinate>();

	Grid() {
		this.grid = new Boolean[6][7];
	}
	
	public void copy(Grid other) {
		this.grid = other.grid.clone();
		System.out.println(this.grid  + " " + other.grid);
		this.counter = other.counter;
		this.height = other.height.clone();
		this.winner = other.winner;
		this.win_pos = other.win_pos.clone();
		this.pos_true = other.pos_true.stream().map(Coordinate::new).collect(Collectors.toSet());
		this.pos_false = other.pos_false.stream().map(Coordinate::new).collect(Collectors.toSet());
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
			int row = height[x];
			this.grid[row][x] = val;
			valid = true;
			this.counter++;
			this.height[x]--;
			if (val == true) {
				pos_true.add(new Coordinate(row, x));
			} else {
				pos_false.add(new Coordinate(row, x));
			}
		}
		return valid;
	}
	
	public void reset() {
		for (int i=0; i<7; i++) {
			for (int j=0; j<6; j++) {
				this.grid[j][i] = null;
			}
			this.height[i] = 5;
		}
		this.counter = 0;
		this.win_pos = new int[2][2];
		this.winner = null;
		this.pos_true.clear();
		this.pos_false.clear();
	}
	
	public Boolean is_over() {
		this.longest_sub(true);
		this.longest_sub(false);
		if (this.counter == 42 || this.winner != null) {
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
		
	public int longest_sub(boolean team) {
		Iterator<Coordinate> setIterator;
		Coordinate pawn;
		Coordinate next_pawn;
		Set<Coordinate> selected_set;
		int l = 0;
		
		if (team == true) {
			setIterator = this.pos_true.iterator();
			selected_set = this.pos_true;
		} else {
			setIterator = this.pos_false.iterator();	
			selected_set = this.pos_false;
		}

		while(setIterator.hasNext()){
			pawn = setIterator.next();
			l = Math.max(l, 1);
			// check H
			for (int i=1; i<4; i++) {
				next_pawn = new Coordinate(pawn.row, pawn.col + i);
				if (selected_set.contains(next_pawn)) {
					l = Math.max(l, i+1);
					if (l == 4) {
						this.win_pos[0][0] = pawn.row;
						this.win_pos[0][1] = pawn.col;
						this.win_pos[1][0] = next_pawn.row;
						this.win_pos[1][1] = next_pawn.col;
						this.winner = team;
						return 4;
					}
				} else {
					break;
				}
			}
			
			// check V
			for (int i=1; i<4; i++) {
				next_pawn = new Coordinate(pawn.row + i, pawn.col);
				if (selected_set.contains(next_pawn)) {
					l = Math.max(l, i+1);
					if (l == 4) {
						this.win_pos[0][0] = pawn.row;
						this.win_pos[0][1] = pawn.col;
						this.win_pos[1][0] = next_pawn.row;
						this.win_pos[1][1] = next_pawn.col;
						this.winner = team;
						return 4;
					}
				} else {
					break;
				}
			}
			
			// check diag asc
			for (int i=1; i<4; i++) {
				next_pawn = new Coordinate(pawn.row - i, pawn.col + i);
				if (selected_set.contains(next_pawn)) {
					l = Math.max(l, i+1);
					if (l == 4) {
						this.win_pos[0][0] = pawn.row;
						this.win_pos[0][1] = pawn.col;
						this.win_pos[1][0] = next_pawn.row;
						this.win_pos[1][1] = next_pawn.col;
						this.winner = team;
						return 4;
					}
				} else {
					break;
				}
			}
			
			// check diag desc
			for (int i=1; i<4; i++) {
				next_pawn = new Coordinate(pawn.row + i, pawn.col + i);
				if (selected_set.contains(next_pawn)) {
					l = Math.max(l, i+1);
					if (l == 4) {
						this.win_pos[0][0] = pawn.row;
						this.win_pos[0][1] = pawn.col;
						this.win_pos[1][0] = next_pawn.row;
						this.win_pos[1][1] = next_pawn.col;
						this.winner = team;
						return 4;
					}
				} else {
					break;
				}
			}
        }
		
		return l;
	}
	
  	public Object clone() {
    	Object o = null;
    	try {
      		o = super.clone();
    	} catch(CloneNotSupportedException cnse) {
      		cnse.printStackTrace(System.err);
	    }
	    return o;
  	}
	
}
