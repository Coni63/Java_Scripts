package fr.coni.genetic_algo;

import processing.core.PVector;

public class IA {
	
	private Candidate parent;
	float Px, Py, Ix, Iy, Dx, Dy;
	float sum_error_x, sum_error_y;
	float vx_max;
	PVector prev_error;
	
	IA(Candidate obj, float[] genome){
		this.parent = obj;
		this.sum_error_x = 0;
		this.sum_error_y = 0;
		this.prev_error = new PVector(0, 0);
		
		this.vx_max = genome[0];
		this.Px = genome[1];
		this.Py = genome[2];
		this.Ix = genome[3];
		this.Iy = genome[4];
		this.Dx = genome[5];
		this.Dy = genome[6];
	}
	
	public float[] predict() {
		float[] result = new float[2];
		
		Door target_door = this.parent.map.doors[this.parent.current_door];
		PVector target_vector = new PVector(target_door.x, target_door.center);
		
		PVector error = PVector.sub(this.parent.pos, target_vector);
		
		this.sum_error_x += error.x;
		this.sum_error_y += error.y;
		
		PVector derrordt = PVector.sub(error, this.prev_error);
		
		//float sx = Px * error.x + Ix * this.sum_error_x + Dx * derrordt.x;
		float sx = Px * (this.parent.v.x - this.vx_max) + Dx * this.parent.a.x;
		float sy = Py * error.y + Iy * this.sum_error_y + Dy * derrordt.y;
		
		float alpha = Math.abs(error.y / error.x);
		
		sx = this.sigmoide(sx);
		sy = this.sigmoide(sy);
		
		//sx = 1/(1+alpha); // (float)Math.sqrt(1/(1+Math.pow(alpha, 2)));
		//sy = alpha * sx;
		
		result[0] = sx;
		result[1] = sy;
		
		//System.out.println(result[0] + " - " + result[1]);
		
		return result;
	}
	
	private float sigmoide(float x) {
		double s = 1/(1+Math.exp(x));
		return (float)(2*s-1);
	}
	
}
