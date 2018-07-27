package fr.coni.genetic_algo;

public class Map {

	public Door[] doors;
	public int h_max;
	
	Map(int hmax){
		doors = new Door[100];
		h_max = hmax;
	}
	
	void generate(){
		for (int i=0 ; i<doors.length ; i++) {
			doors[i] = new Door(h_max, h_max * (i+1) / 4);
		}
	}
	
}
