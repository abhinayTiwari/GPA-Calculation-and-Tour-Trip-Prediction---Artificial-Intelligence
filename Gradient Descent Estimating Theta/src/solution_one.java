package Solution;
import java.util.Arrays;
import java.util.Random;

import org.apache.commons.lang.ArrayUtils;

import Jama.Matrix;

public class solution_one {

	public int maxIteration=10;
	public double alpha=0.000001D;
	public double[] theta=null;
	public Random random= new Random();
	
	public double f(double[] t){
		//double fun_value=0.0D;
		return t[0]*t[0]+t[1]*t[1]+t[2]*t[2]-2*t[0]-2*t[1]-2*t[2]+3;
	}
	
	public static Matrix linear_gradient_descent(){
		int r = 3;
		int c = 1;
		Matrix Theta = new Matrix(r,c); 
		double[][] feature = {{1,3.8,10}, {1,3.5,4}, {1,3.0,2}, {1,3.2,5}, {1,2.7,8}, {1,2.0,3},{1,2.5,10},{1,3.6,8},{1,1.75,2},{1,3.35,9}};
		double[] y= {98.0, 82.0, 61.0, 77.0, 85.0, 52.0, 87.0, 92.0, 33.0, 95.0};
		double alpha=0.001;
		double bracketValue = 0.0;
		double gd_maxIter = 10000;
		for(int i=0;i<gd_maxIter;i++){
			Matrix Gradient = new Matrix(r,c); 
			for(int j=0; j< 10; j++){
				Matrix featureValuesInMatrix = new Matrix(feature[j], 3);
				Matrix thetaTimesX = Theta.transpose().times(featureValuesInMatrix);
				bracketValue = thetaTimesX.get(0, 0) - y[j];
				Gradient = Gradient.plus(featureValuesInMatrix.times(2.0).times(bracketValue));
			}
			Theta = Theta.minus(Gradient.times(alpha));
			double gradValue = Gradient.normF();
			if(gradValue*gradValue<=1.e-20){
				break;
			}
		}		
		return Theta;
	}
	
	
	public static void calculateGPA(Matrix Theta){
	    	int maxExample = 10;
	    	//double[] x1= {3.8, 3.5, 3.0, 3.2, 2.7, 2.0, 2.5, 3.6, 1.75, 3.35};
			//double[] x2= {10, 4, 2, 5, 8, 3, 10, 8, 2, 9}; 
	    	double[] x1= {3.5, 3.2, 3.2, 3.8, 2.9, 2.2, 2.1, 3.1, 1.25, 2.35};
			double[] x2= {11, 6, 3, 3, 7, 6, 5, 10, 5, 9}; 
			double[] predictedGPA = new double[maxExample] ;
	 		for (int i=0; i< maxExample; i++){
	 			predictedGPA[i] = Theta.get(0,0)  + Theta.get(1,0) * x1[i] + Theta.get(2,0) * x2[i];
	 		}
	 		System.out.println("Predicted GPA using linear regression : \n"+ArrayUtils.toString(predictedGPA));
			
		}
		
	
	public solution_one(){
	    theta=new double[3];
		for (int i=0;i<theta.length;i++){
			theta[i]=(random.nextInt(10))/10.0D;
		}
		System.out.println(ArrayUtils.toString(theta));
	}
	
		public static void main(String[] args) {
			Matrix Theta = linear_gradient_descent();
			System.out.println("The estimated theta by linear regression: \n");
			System.out.println(Arrays.deepToString(Theta.getArray()));
			calculateGPA(Theta);
		}
}
