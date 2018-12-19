package fr.coni.connect4;

public class Coordinate {
	public int row;
	public int col;
	
	Coordinate(int row, int col){
		this.row = row;
		this.col = col;
	}
	
	@Override
    public boolean equals(Object arg0) {

		Coordinate other = (Coordinate)arg0;

        if(other.row == this.row && other.col == this.col){
        	return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.row * 10 + this.col;
    }
}
