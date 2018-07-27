package fr.coni.genetic_algo;

public class Map {

	public int nb_doors = 6;
	public Door[] doors;
	public int h_max;
	public float x_max;
	
	Map(int hmax){
		doors = new Door[nb_doors];
		h_max = hmax;
		x_max = h_max * nb_doors / 4;
	}
	
	void generate(){
		for (int i=0 ; i<nb_doors ; i++) {
			doors[i] = new Door(h_max, h_max * (i+1) / 4);
		}
	}
	
}
