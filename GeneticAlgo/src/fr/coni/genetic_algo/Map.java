package fr.coni.genetic_algo;

import flanagan.interpolation.CubicSplineFast;

public class Map {

	public int nb_doors = 6;
	public Door[] doors;
	public int h_max;
	public float x_max;
	private int nb_pt_spline = 1000;
	public float[] x = new float[nb_pt_spline];
	public float[] y = new float[nb_pt_spline];
	public CubicSplineFast model; 
	
	Map(int hmax){
		doors = new Door[nb_doors];
		h_max = hmax;
		x_max = h_max * nb_doors / 4;
	}
	
	void generate(){
		for (int i=0 ; i<nb_doors ; i++) {
			doors[i] = new Door(h_max, h_max * (i+1) / 4);
		}
		getSpline();
	}
	
	private void getSpline() {
		double[] x_d = new double[nb_doors+1];
		double[] y_d = new double[nb_doors+1];
		
		x_d[0] = 0;
		y_d[0] = h_max/2;
		
		// compute spline
		for (int i=0; i<nb_doors; i++) {
			x_d[i+1] = doors[i].x;
			y_d[i+1] = doors[i].center;
		}
		
		//long startTime = System.nanoTime();
		model = new CubicSplineFast(x_d, y_d);
		//long endTime = System.nanoTime();
		//long duration = (endTime - startTime); 
		//System.out.println(duration/1000);
		
		for (int i=1; i<nb_pt_spline; i++) {
			double temp_x = i*(x_max+10)/nb_pt_spline;
			double temp_y = model.interpolate(temp_x);
			x[i] = (float)temp_x;
			y[i] = (float)temp_y;
		}
	}
	
}
