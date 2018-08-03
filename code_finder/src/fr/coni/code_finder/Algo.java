package fr.coni.code_finder;

import processing.core.PApplet;

public interface Algo {
	public boolean step(Code real);
	public boolean getStatus();
	public void show();
	public void draw(Code real, PApplet parent);
}
