package fr.coni.processing;

import processing.core.PApplet;
import java.awt.geom.Point2D;

public class Square {

	private Point2D[] points = new Point2D[4];    
	private PApplet parent;
	private double square_size;
	private int R, G, B;
	
	Square(PApplet p) {
		parent = p;
		square_size = Math.min(parent.width, parent.height) * 0.9;
		points[0] = new Point2D.Float();
		points[0].setLocation(parent.width/2 - square_size/2, parent.height/2 + square_size/2);
		points[1] = new Point2D.Float();
		points[1].setLocation(parent.width/2 + square_size/2, parent.height/2 + square_size/2);
		points[2] = new Point2D.Float();
		points[2].setLocation(parent.width/2 + square_size/2, parent.height/2 - square_size/2);
		points[3] = new Point2D.Float();
		points[3].setLocation(parent.width/2 - square_size/2, parent.height/2 - square_size/2);
	}
	
	void display(int frame) {
		for (int i = 0; i < 4; i++) {
			change_color(frame, 8, 2*i);
		    parent.line((float)points[i].getX(), (float)points[i].getY(), 
		    			(float)points[(i+1)%4].getX(), (float)points[(i+1)%4].getY());
		}
	}
	
	void move() {
		double x, y;
		int factor = 10;
		
		for (int i = 0; i < 4; i++) {
			x = ((factor-1) * points[i].getX() + points[(i+1)%4].getX())/factor;
			y = ((factor-1) * points[i].getY() + points[(i+1)%4].getY())/factor;
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
