package Solutions;

import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;

import Jama.*;

public class Solution_two {
	public static int getArrayIndex(double[] array, double value){
		int k=-2;
		for(int i=0; i<array.length; i++){
			if(array[i] == value){
				k = i;
				break;
			}
		}
		return k;
	}
	
	
	public static Matrix Projection(Matrix Theta){
		int r = 3;
		double[] valsForProj = Theta.getRowPackedCopy();
		double[] valsForProjAbs = Arrays.copyOf(valsForProj, valsForProj.length);		
		for (int i=0;i<valsForProj.length;i++){		
			valsForProjAbs[i] = Math.abs(valsForProjAbs[i]);
		}
		double[] valsForProjAbsOrigin = Arrays.copyOf(valsForProjAbs, valsForProjAbs.length);
		Arrays.sort(valsForProjAbs);
		double biggest = valsForProjAbs[valsForProjAbs.length-1];
		double biggestSecond = valsForProjAbs[valsForProjAbs.length-2];
		int idx = getArrayIndex(valsForProjAbsOrigin, biggest);
		int idxSecond = getArrayIndex(valsForProjAbsOrigin, biggestSecond);
		double[] valsProjected = new double[valsForProj.length];
		Arrays.fill(valsProjected, 0.0);
		valsProjected[idx] = valsForProj[idx];
		valsProjected[idxSecond] = valsForProj[idxSecond]; 
		Matrix thetaProjected = new Matrix(valsProjected, r);
		return thetaProjected;
	}
	
	public static Matrix minimizeGradientDescent(Matrix Gamma, Matrix Beta, double rho){
		int r = 3;
		int c = 1;
		double[][] feature = {{1,3.8,10}, {1,3.5,4}, {1,3.0,2}, {1,3.2,5}, {1,2.7,8}, {1,2.0,3},{1,2.5,10},{1,3.6,8},{1,1.75,2},{1,3.35,9}};
		double[] y= {98.0, 82.0, 61.0, 77.0, 85.0, 52.0, 87.0, 92.0, 33.0, 95.0};
		double bracketValue = 0.0;
		Matrix Theta = new Matrix(r,c); 
		double alpha=0.001;
		double gd_maxIter = 10000;
		for(int i=0;i<gd_maxIter;i++){
			Matrix gradientOfFirstPart = new Matrix(r,c); 
			for(int j=0; j< 10; j++){
				Matrix featureValuesInMatrix = new Matrix(feature[j], 3);
				Matrix thetaTimesX = Theta.transpose().times(featureValuesInMatrix);
				bracketValue = thetaTimesX.get(0, 0) - y[j];
				gradientOfFirstPart = gradientOfFirstPart.plus(featureValuesInMatrix.times(2.0).times(bracketValue));					
			}
			Matrix functionOfTheta = gradientOfFirstPart;
			Matrix g_Of_theta = Theta.minus(Beta).plus(Gamma.times(1.0/rho)).times(rho);
			Matrix Gradient = functionOfTheta.plus(g_Of_theta);
			Theta = Theta.minus(Gradient.times(alpha));
			double gradValue = Gradient.normF();
			if(gradValue*gradValue<=1.e-20){
				break;
			}
		}		
		return Theta;
	}
	
	
	public static Matrix admm(){
		int r = 3;
		int c = 1;
		double Epsilon = 0.001;
		Matrix gamma = new Matrix(r,c);
		Matrix theta = new Matrix(r,c); 
		Matrix beta = Matrix.random(r,c);
		double rho = 0.001;
		int maxIter = 1000;
		double deltaR;
		double deltaS;
		for(int i=0; i < maxIter ; i++){
			theta = minimizeGradientDescent(gamma, beta, rho);
			beta = Projection(theta.plus(gamma.times(1/rho)));	
			gamma = gamma.plus(theta.minus(beta).times(rho));
			deltaR = theta.minus(beta).normF() * theta.minus(beta).normF(); 
			deltaS = rho * beta.minus(beta).normF() * theta.minus(beta).normF();
			if(deltaR < Epsilon || deltaS < Epsilon){
				break;
			}
		}
		return theta;
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
	 		System.out.println("Predicted GPA: \n"+ArrayUtils.toString(predictedGPA));
			
		}
	
	public static void main(String[] args) {
		Matrix Theta = admm();
		System.out.println("The estimated theta by ADMM is: ");
		System.out.println(Arrays.deepToString(Theta.getArray()));
		calculateGPA(Theta);
	}
}
