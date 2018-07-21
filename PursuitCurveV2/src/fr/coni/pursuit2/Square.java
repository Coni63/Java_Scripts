package fr.coni.pursuit2;

import processing.core.PApplet;
import java.awt.geom.Point2D;

public class Square {

	private Point2D[] points; 
	private PApplet parent;
	private double square_size;
	private int R, G, B;
	private int depth;
	
	Square(PApplet p, int _depth) {
		parent = p;
		depth = _depth;
		square_size = Math.min(parent.width, parent.height) * 0.9;
		points = new Point2D[4 * depth];   
		double x, y;
		int factor = 10;
		
		points[0] = new Point2D.Float();
		points[0].setLocation(parent.width/2 - square_size/2, parent.height/2 + square_size/2);
		points[1] = new Point2D.Float();
		points[1].setLocation(parent.width/2 + square_size/2, parent.height/2 + square_size/2);
		points[2] = new Point2D.Float();
		points[2].setLocation(parent.width/2 + square_size/2, parent.height/2 - square_size/2);
		points[3] = new Point2D.Float();
		points[3].setLocation(parent.width/2 - square_size/2, parent.height/2 - square_size/2);
		for (int i = 4; i < 4*depth; i++) {
			x = ((factor-1) * points[i-4].getX() + points[i-3].getX())/factor;
			y = ((factor-1) * points[i-4].getY() + points[i-3].getY())/factor;
			points[i] = new Point2D.Float();
			points[i].setLocation((float)x, (float)y);
		}
	}
	
	void display(int frame) {
		for (int i = 0; i < 4 * depth -1; i++) {
			change_color(frame, 8, 2*i);
		    parent.line((float)points[i].getX(), (float)points[i].getY(), 
		    			(float)points[i+1].getX(), (float)points[i+1].getY());
		}
	}
	
	void move() {
		double x, y;
		float zoom_factor = 1.01f;
		for (int i = 0; i < 4*depth; i++) {
			x = (points[i].getX() - parent.width/2) * zoom_factor + parent.width/2;
			y = (points[i].getY() - parent.height/2) * zoom_factor + parent.height/2;
			points[i].setLocation((float)x, (float)y);
		}
	}
	
	private void change_color(int value, int steps, int offset) {
		R = PApplet.constrain(255-value*steps-offset, 0, 255);
		G = PApplet.constrain(0, 0, 0);
		B = PApplet.constrain(value*steps+offset, 0, 255);
		parent.stroke(R, G, B);
	}
	
}
