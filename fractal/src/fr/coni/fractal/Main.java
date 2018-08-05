package fr.coni.fractal;

import processing.core.PApplet;
import processing.event.MouseEvent;


public class Main extends PApplet{
		
		private float FrameRate = 15f;
		
		static int width_ = 720;
		static int height_ = 720;
		
		// trigger if we change the cm or move the figure
		int mode = 1; // 0 = move cm - 1 = move figure
		
		// manage zoom factor to be converted as exp
		int zoom = 0;
		
		// drag factor
		float drag_factor = 100f;
		
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
		
		float c = 0;
		float d = 0;
		
		int previous_x = 0;
		int previous_y = 0;
		int value = 0;
		
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
			
			zoom += e;
			zoom = constrain(zoom, -10, 100);
			
			// It all starts with the width, try higher or lower values
			w = (float)(4*Math.exp(-zoom/10f));
			h = (w * height_) / width_;
			
			// get the middle of the screen to zoom on the correct area
			float mid_x = (xmax+xmin)/2f;
			float mid_y = (ymax+ymin)/2f;
			
			// Start at negative half the width and height
			xmin = -w/2+mid_x;
			ymin = -h/2+mid_y;
			
			xmax = w/2+mid_x;
			ymax = h/2+mid_y;
			
			// Calculate amount we increment x,y for each pixel
			dx = w / width_;
			dy = h / height_;
		}
		
		public void mousePressed() {
			if (mouseButton == LEFT) {
				mode = 1-mode;
			}
		}
		
		public void keyPressed() {
			System.out.println(keyCode);
			if (keyCode == 82) {
				System.out.println("eee");
				// trigger if we change the cm or move the figure
				mode = 1; // 0 = move cm - 1 = move figure
				
				// manage zoom factor to be converted as exp
				zoom = 0;
				
				// drag factor
				drag_factor = 100f;
				
				// It all starts with the width, try higher or lower values
				w = 3;
				h = (w * height_) / width_;

				// Start at negative half the width and height
				xmin = -w/2;
				ymin = -h/2;
				
				xmax = w/2;
				ymax = h/2;
				
				// Calculate amount we increment x,y for each pixel
				dx = w / width_;
				dy = h / height_;
				
				c = 0;
				d = 0;
				
				previous_x = 0;
				previous_y = 0;
			}
		}
		
		public void mouseDragged() {
			if ((mouseButton == RIGHT) && (mode == 1)) {
				int delta_x = mouseX - previous_x;
				int delta_y = mouseY - previous_y;
				previous_x = mouseX;
				previous_y = mouseY;
				
				// delta can't be reset on mousePressed as it is triggered after
				if (abs(delta_x)>1) {
					delta_x = 0;
					delta_y = 0;
				}
				
				drag_factor = (float)(100f*Math.exp(zoom/10f));
								
				// Start at negative half the width and height
				xmin -= (float)delta_x/drag_factor;
				ymin -= (float)delta_y/drag_factor;
				
				xmax -= (float)delta_x/drag_factor;
				ymax -= (float)delta_y/drag_factor;
			}
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
			    if (mode == 0) {
				    c = map(mouseX, 0, width, -1, 1);
				    d = map(mouseY, 0, height, -1, 1);
			    }
			    int n = 0;
			    while (n < maxiterations) {
			    	float aa = a * a;
			    	float bb = b * b;
			    	float twoab = 2.0f * a * b;
			    	a = aa - bb + c;
			    	b = twoab + d;
			    	// Infinty in our finite world is simple, let's just consider it 16
			    	
			    	float depth = 16f;
			    	if (zoom > 0) {
			    		depth = 16f / (float)Math.pow(1.01, zoom);
			    	}
			    	
			    	if ( (a*a + b*b) > depth) {
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
