package fr.coni.fractal;

import processing.core.PApplet;
import processing.event.MouseEvent;


public class Main extends PApplet{
		
		private float FrameRate = 15f;
		
		static int width_ = 720;
		static int height_ = 720;
		
		// It all starts with the width, try higher or lower values
		float w = 3;
		float h = (w * height_) / width_;

		// Start at negative half the width and height
		float xmin = -w/2;
		float ymin = -h/2;
		
		float xmax = w/2;
		float ymax = h/2;
		
		// Calculate amount we increment x,y for each pixel
		float dx = w / width_;
		float dy = h / height_;
		
		// Maximum number of iterations for each point on the complex plane
		static int maxiterations = 100;
		
		
		public static void main(String[] args) {
			PApplet.main("fr.coni.fractal.Main");
		}
		
		public void settings(){
		    size(width_, height_);
		    //noLoop();
		}

		public void setup() {
			frameRate(FrameRate);
			colorMode(HSB, 255);
		}

		public void mouseWheel(MouseEvent event) {
			float e = event.getCount();
			
			// It all starts with the width, try higher or lower values
			w += (float)(e/10f);
			h = (w * height_) / width_;

			// Start at negative half the width and height
			xmin = -w/2;
			ymin = -h/2;
			
			xmax = w/2;
			ymax = h/2;
			
			// Calculate amount we increment x,y for each pixel
			dx = w / width_;
			dy = h / height_;
		}
		
		public void draw() {			
			// Make sure we can write to the pixels[] array.
			// Only need to do this once since we don't do any other drawing.
			loadPixels();

			// Start y
			float y = ymin;
			for (int j = 0; j < height; j++) {
			  // Start x
			  float x = xmin;
			  for (int i = 0; i < width; i++) {

			    // Now we test, as we iterate z = z^2 + cm does z tend towards infinity?
			    float a = x;
			    float b = y;
			    float c = map(mouseX, 0, width, -1, 1);
			    float d = map(mouseY, 0, height, -1, 1);
			    int n = 0;
			    while (n < maxiterations) {
			    	float aa = a * a;
			    	float bb = b * b;
			    	float twoab = 2.0f * a * b;
			    	a = aa - bb + c;
			    	b = twoab + d;
			    	// Infinty in our finite world is simple, let's just consider it 16
			    	if ( (a*a + b*b) > 16.0) {
			    		break;  // Bail
			    	}
			    	n++;
			    }

			    // We color each pixel based on how long it takes to get to infinity
			    // If we never got there, let's pick the color black
			    if (n == maxiterations) {
			      pixels[i+j*width] = color(0);
			    } else {
			      // Gosh, we could make fancy colors here if we wanted
			      float norm = map(n, 0, maxiterations, 0, 1);
			      pixels[i+j*width] = color(map(sqrt(norm), 0, 1, 0, 255), 255, 255);
			    }
			    x += dx;
			  }
			  y += dy;
			}
			updatePixels();
		}
}
