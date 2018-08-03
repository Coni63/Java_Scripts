package fr.coni.code_finder;

import processing.core.PApplet;

public class Main extends PApplet{
	
	static int batch_size = 100;
	static int code_length = 10;
	static int base = 5; // MUST BE BELOW 10 FOR RENDERING ONLY
	static Code real_code = new Code(code_length, base);
	private boolean stop = false;
	static int nb_combi = (int)Math.pow(base, code_length);
	private float FrameRate = 2f;
	GreedyAlgo greedy = new GreedyAlgo(code_length, base, batch_size);
	GeneticAlgo genetic = new GeneticAlgo(code_length, base, batch_size);
	RandomAlgo alea = new RandomAlgo(code_length, base, batch_size);
	SmartAlgo smart = new SmartAlgo(code_length, base);
	int value = 0;
	
	public static void main(String[] args) {
		PApplet.main("fr.coni.code_finder.Main");
		System.out.println("Real Code: " + real_code.show());
		System.out.println("Number of Combinations: " + nb_combi);
	}
	
	public void settings(){
	    size(800, 450);
	}


	public void setup() {
		frameRate(FrameRate);
	}

	public void keyPressed() {
		
		if (key == 43) {
			FrameRate = min(FrameRate+1, 60f);
		} else if (key == 45) {
			FrameRate = max(FrameRate-1, 15f);
		}
		
		frameRate(FrameRate);
	}
	
	public void draw() {	
		boolean result;
		if (!this.stop) {
			this.stop = true;
			if (smart.getStatus() == false) {
				this.stop = false;
				result = smart.step(real_code);
				if (result) {
					smart.show();
				}
			}
			if (greedy.getStatus() == false) {
				this.stop = false;
				result = greedy.step(real_code);
				if (result) {
					greedy.show();
				}
			}
			if (alea.getStatus() == false) {
				this.stop = false;
				result = alea.step(real_code);
				if (result) {
					alea.show();
				}
			}
			if (genetic.getStatus() == false) {
				this.stop = false;
				result = genetic.step(real_code);
				if (result) {
					genetic.show();
				}
			}
			
			draw_header();
			draw_layout();
			
			smart.draw(real_code, this);
			greedy.draw(real_code, this);
			alea.draw(real_code, this);
			genetic.draw(real_code, this);
			saveFrame("F:/java_rendering/output/img_#####.png");
		} else {
			noLoop();
		}
	}
	
	private void draw_header() {
		String txt;
		float cw;
		background(255);
		rectMode(CORNER);
		textAlign(LEFT, BOTTOM);
		fill(47, 138, 255);
		textSize(20);
		txt = "Code length :";
		cw = textWidth(txt);
		text(txt, 10, 30); 
		textSize(26);
		text(Integer.toString(code_length), 10 + cw + 10, 30+3); 
		
		textSize(20);
		txt = "Code base :";
		cw = textWidth(txt);
		text(txt, 10, 50); 
		textSize(26);
		text(Integer.toString(base), 10 + cw + 10, 50+3); 
		
		textSize(20);
		txt = "Number of Combinations :";
		cw = textWidth(txt);
		text(txt, 10, 70); 
		textSize(26);
		text(Integer.toString(nb_combi), 10 + cw + 10, 70+3); 
		
		textSize(15);
		txt = "Every frame, there is up to " + batch_size + " codes tested (in order to reflect number of tries instead of speed)";
		text(txt, 10, height-10); 
		
		textAlign(RIGHT, CENTER);
		
		txt = "FPS : " + FrameRate;
		text(txt, width-10, 30); 
		
		txt = "\"+\" : +1 FPS";
		text(txt, width-10, 50); 
		
		txt = "\"-\" :  -1 FPS";
		text(txt, width-10, 70); 
	}
	
	private void draw_layout() {
		
		// draw table
		rectMode(CORNERS);
		
		rect(20, 100, width/2, height/2); // main box
		rect(20, 100, width/2, 100+25);   // title box
		
		rect(width/2, 100, width-20, height/2); // main box
		rect(width/2, 100, width-20, 100+25);   // title box
		
		rect(20, height/2, width/2, height-100); // main box
		rect(20, height/2, width/2, height/2+25);// title box
		
		rect(width/2, height/2, width-20, height-100); // main box
		rect(width/2, height/2, width-20, height/2+25);// title box
		
		// draw title
		textAlign(CENTER, CENTER);

		fill(255, 243, 112);
		text("Smart Algo", width/4 + 10, 100+12);
		text("Genetic Algo", 3*width/4 - 10, 100+12);
		text("Random Algo", width/4 + 10, height/2+12);
		text("Greedy Algo", 3*width/4 - 10, height/2+12);
		
		// draw content
		textAlign(LEFT, CENTER);
		text("Code  : ", 30, 100+25+12);
		text("# try : ", 30, 100+50+12);
		text("Fitness : ", 30, 100+75+12);
		
		text("Code  : ", width/2+10, 100+25+12);
		text("# try : ", width/2+10, 100+50+12);
		text("Fitness : ", width/2+10, 100+75+12);
		
		text("Code  : ", 30, height/2+25+12);
		text("# try : ", 30, height/2+50+12);
		text("Fitness : ", 30, height/2+75+12);
		
		text("Code  : ", width/2+10, height/2+25+12);
		text("# try : ", width/2+10, height/2+50+12);
		text("Fitness : ", width/2+10, height/2+75+12);
	}

}