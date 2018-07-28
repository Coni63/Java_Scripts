package fr.coni.genetic_algo;

//import java.awt.geom.Point2D;
// import java.util.Random;
import processing.core.PVector;

public class Candidate {
	
	public PVector pos;
	public PVector v;
	public PVector a;
	public PVector balance;
	public int ID;
	public float thrust; // thrust max for both engines
	public float weight;
	public float friction;
	public float air_friction;
	public float air_friction_force;
	public float friction_force;
	public float w, h, thk;
	private float timelapse;                        
	private float density = 7800;                     			// kg/m^3
	public float fitness;
	public boolean is_active;
	public int current_door;
	public Map map;
	public float AngleToTarget;
	public float v_max, px, py, ix, iy, dx, dy;
	private IA ia;
	private float[] genome = new float[7];
	public int remaining_life;
	public boolean best;
	int steps;
	
	Candidate(int ID, float fps, Map map){
		this.ID = ID;
		this.pos =new PVector(0, (float)(map.h_max / 2));       // m
		this.v = new PVector(0, 0);                   			// m/s
		this.a = new PVector(0, 0);                   			// m/s²
		this.balance = new  PVector(1f, 1f);
		this.thrust = 15000f; 									// N
		this.w = 3f;                                  			// m
		this.h = 0.3f;                            				// m
		this.thk = .1f;											// m
		this.weight = this.w * this.h * this.thk * this.density; // kg
		this.friction = 0.3f; 						           	// coef steel/steel
		this.friction_force = this.friction * this.weight;      // N
		this.air_friction = 2.42f * 1.225f * this.w * this.h;   // Cx * rho *S                      			
		timelapse = (float) (1/fps);                  			// s
		this.is_active = true;
		this.map = map;
		this.current_door = 0;
		this.AngleToTarget = 0;
		this.fitness = 0;
		this.remaining_life = 100;
		this.best = false;
		
		this.v_max = (float)Math.random()*10;
		this.px = (float)Math.random()*10;
		this.py = (float)Math.random()*10;
		this.ix = (float)Math.random()*2;
		this.iy = (float)Math.random()*2;
		this.dx = (float)Math.random()*10;
		this.dy = (float)Math.random()*10;
		
		this.genome[0] = this.v_max;
		this.genome[1] = this.px; 
		this.genome[2] = this.py; 
		this.genome[3] = this.ix; 
		this.genome[4] = this.iy;
		this.genome[5] = this.dx;;
		this.genome[6] = this.dy;
		
		this.ia = new IA(this, this.genome);
	}
	
	public float getFitness() {
		return this.fitness;
	}
	
	public float getX() {
		return this.pos.x;
	}
	
	public float getY() {
		return this.pos.y;
	}
	
	public void move(float[] actions) {
		
		this.balance = new  PVector(actions[0], actions[1]);
		
		this.air_friction_force = this.air_friction * this.v.magSq();
		
		PVector current_thrust = this.balance.copy();
				current_thrust.mult(this.thrust);
				
		PVector current_friction = this.v.copy();
				current_friction.normalize();
				current_friction.mult(this.air_friction_force + this.friction_force);
				
		this.a = PVector.sub(current_thrust, current_friction).div(this.weight);
		this.v = PVector.add( this.v, PVector.mult(this.a, timelapse) );
		this.pos = PVector.add( this.pos, PVector.mult(this.v, timelapse) );
		
		if (this.pos.x < 0) {
			this.a.x = 0;
			this.v.x = 0;
			this.pos.x = 0;
		}
		
		if (this.pos.y - this.w/2 < 0) {
			this.a.y = 0;
			this.v.y = 0;
			this.pos.y = this.w/2;
		} else if (this.pos.y + this.w/2 > this.map.h_max) {
			this.a.y = 0;
			this.v.y = 0;
			this.pos.y = this.map.h_max - this.w/2;
		}
		
		this.remaining_life--;
		
	}
	
	public void UpdateTarget() {
		UpdateTargetNumber();
		//this.AngleToTarget = getTargetAngle();
		
	}
	
	public void UpdateTargetNumber() {
		Door current_door = this.map.doors[this.current_door];
		if (this.pos.x + this.h/2 > current_door.x) {
			if ( (this.pos.y-this.w/2 < current_door.center - current_door.width/2) | (this.pos.y + this.w/2 > current_door.center + current_door.width/2) ) {
				this.is_active = false;
				this.fitness = this.remaining_life/(this.map.nb_doors+1); //(float)(this.pos.x / this.map.x_max);
				//System.out.println("Candidate is out");
			} else {
				this.current_door++;
				this.remaining_life += 100;
				//System.out.println("Congratulation you just passed the door " + this.current_door );
				if (this.current_door >= this.map.nb_doors) {
					this.current_door--;
					this.fitness = this.remaining_life/(this.map.nb_doors+1); //1f;
					this.is_active = false;
				}
			}
		}
		
		if (this.remaining_life < 1) {
			this.is_active = false;
			this.fitness = this.remaining_life/(this.map.nb_doors+1); //(float)(this.pos.x / this.map.x_max);
			//System.out.println("Candidate is out");
		}
	}
	
	public float[] getAction() {
		return this.ia.predict();
	}
			
	public float getTargetAngle() {
		PVector VectorToTarget;
		VectorToTarget = new PVector((float)(this.map.doors[this.current_door].x) - this.pos.x,
									 (float)(this.map.doors[this.current_door].center) - this.pos.y);
		//System.out.println(VectorToTarget.heading());
		return VectorToTarget.heading();
	}

	public float getThrust() {
		return this.thrust;
	}

	public void setThrust(float thrust) {
		this.thrust = thrust;
	}

	public float getWeight() {
		return this.weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getFriction() {
		return this.friction;
	}

	public void setFriction(float friction) {
		this.friction = friction;
	}

	public float getWidth() {
		return this.w;
	}

	public void setWidth(float width) {
		this.w = width;
	}
	
	public float getThickness() {
		return this.thk;
	}
	
	public void setGenome(float[] genome) {
		this.genome = genome;
	}
	
	public float[] getGenome() {
		return this.genome;
	}
	
	public void reset() {
		this.pos =new PVector(0, (float)(map.h_max / 2));       // m
		this.v = new PVector(0, 0);                   			// m/s
		this.a = new PVector(0, 0);
		this.current_door = 0;
		this.fitness = 0;
		this.best = false;
		this.is_active = true;
		//this.ia = new IA(this, this.genome);
		this.ia.sum_error_x = 0;
		this.ia.sum_error_y = 0;
		this.ia.prev_error_y = 0;
	}
	
}
