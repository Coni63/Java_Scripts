package fr.coni.genetic_algo;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.linalg.Algebra;
import cern.colt.function.DoubleDoubleFunction;
import cern.colt.function.DoubleFunction;

public class IA {
	
	private Candidate parent;
	private DoubleMatrix2D A1, A2;
	private DoubleMatrix1D B1, B2;
	
	IA(Candidate obj){
		this.parent = obj;
		this.A1 = DoubleFactory2D.dense.random(7, 4);
		this.B1 = DoubleFactory1D.dense.random(4);
		this.A2 = DoubleFactory2D.dense.random(4, 2);
		this.B2 = DoubleFactory1D.dense.random(2);
	}
	
	public float[] predict() {
		
		float[] result = new float[2];
		
		//System.out.println( this.parent.AngleToTarget );
		
		if (this.parent.AngleToTarget > 0) {
			result[0] = 1f;
			result[1] = 0.8f;
		} else {
			result[0] = 0.8f;
			result[1] = 1f;
		}
		
		//System.out.println(result);
		/*
		float[] result = new float[2];
		
		double[] float_input = {0f, 1f, 1f, 0.8f};
		
		DoubleMatrix1D input = DoubleFactory1D.dense.make(float_input);
		DoubleMatrix1D output;
		
		output = model(input);
		
		System.out.println(output);
		
		result[0] = (float)output.get(0);
		result[0] = (float)output.get(1);
		*/
		return result;
	}
	
	public DoubleMatrix1D model(DoubleMatrix1D x) {
		Algebra a = new Algebra();
		DoubleMatrix1D temp = a.mult(this.A1, x);
		temp.assign(this.B1, plus);
		temp.assign(activate);
		temp = a.mult(this.A2, temp);
		temp.assign(this.B2, plus);
		temp.assign(activate);
		return temp;
	}
		
	DoubleDoubleFunction plus = new DoubleDoubleFunction() {
        @Override
        public double apply(double v, double v1) {
            return v + v1;
        }
    };
    
	DoubleFunction activate = new DoubleFunction() {
        public double apply(double v) {
            return Math.max(0, v);  //relu
        }
    };
	
}
