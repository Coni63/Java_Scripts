package fr.coni.cubic_interpolation;

import processing.core.PApplet;
import java.util.Arrays;

import flanagan.interpolation.CubicSplineFast;

public class Main extends PApplet{

	int nb_pts = 10;
	int[][] pts = new int[nb_pts][2];
	double out_x[] = new double[100];
	double out_y[] = new double[100];
	int idx = 0;
	boolean generate = false;
	
	public static void main(String[] args) {
		PApplet.main("fr.coni.cubic_interpolation.Main");        
	}

	public void settings(){
	    size(1500, 800);
	}

	public void setup() {
		frameRate(15);
	}

	public void draw() {
		background(255);
		stroke(0, 0, 0);
		for (int i=0; i<nb_pts;i++) {
			if ( (pts[i][0] > 0) & (pts[i][1] > 0) ) {
				ellipse(pts[i][0], pts[i][1], 10, 10);
			}
		}
		
		stroke(255, 0, 0);
		if (generate == true) {
			for (int i=0; i<100;i++) {
				ellipse((float)out_x[i], (float)out_y[i], 5, 5);
			}
		}
	}

	public void mouseClicked() {
		idx = (idx + 1)%nb_pts;
		pts[idx][0] = mouseX;
		pts[idx][1] = mouseY;
		
		if (idx == 0) {
			generate = true;
		}
		
		if (generate == true) {
			getSpline();
		}
	}
	
	public void getSpline() {
		
		// copy of the matrix and sort by X ascending
		int[][] xy = new int[nb_pts][2];

		for(int i=0; i<nb_pts; i++) {
			for(int j=0; j<2; j++) {
			    xy[i][j]=pts[i][j];
			}
		}
		
		Arrays.sort(xy, (a, b) -> Double.compare(a[0], b[0]));
		
		double[] x = new double[nb_pts];
		double[] y = new double[nb_pts];
		
		// compute spline
		for (int i=0; i<nb_pts; i++) {
			x[i] = xy[i][0];
			y[i] = xy[i][1];
		}
		
		long startTime = System.nanoTime();
		CubicSplineFast cs = new CubicSplineFast(x, y);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime); 
		System.out.println(duration/1000);
		
		float min = (float)x[0];
		float max = (float)x[nb_pts-1];
		
		for (int i=0; i<100; i++) {
			double temp_x = map((float)i, 0, 100, min-10, max+10);
			double temp_y = cs.interpolate(temp_x);
			out_x[i] = temp_x;
			out_y[i] = temp_y;
		}
		
	}

}
