package Solutions;

import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;

import Jama.*;

public class Solution_fifthADMM {
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
	
	public static  Matrix calculateFunctionofTheta(){
	     	double[] thetaValues = new double[3];
		    double alpha=0.001D;
			double[] gradient=new double[thetaValues.length];
			double commonGradientValue = 0.0;
			double[] previousValueOfTheta = new double[thetaValues.length];
			double convergence = 0.0;
			boolean isConvergence= false;
			
			while(!isConvergence){
				
				commonGradientValue = Math.exp(thetaValues[0] + 0.5* thetaValues[1] - 2 * thetaValues[2]);
				gradient[0]= commonGradientValue + 2 * thetaValues[0]+ thetaValues[1];
			    gradient[1]= commonGradientValue *0.5 + 2 * thetaValues[1]+ thetaValues[0] + thetaValues[2];
				gradient[2]= commonGradientValue *(-2) + 2 * thetaValues[2]+ thetaValues[1];
				
				for(int j=0;j<thetaValues.length;j++){
					previousValueOfTheta[j] = thetaValues[j];
					thetaValues[j]= thetaValues[j] - alpha*gradient[j] ;
					convergence = previousValueOfTheta[j] - thetaValues[j];
					convergence = (convergence < 0 ? -convergence : convergence);		
					if( convergence < 0.001){
						isConvergence = true;
						break;
					}
				}
				 
			}
			Matrix Theta = new Matrix(thetaValues, 3);
			return Theta;

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
			Matrix functionOfTheta = calculateFunctionofTheta();
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


	
	public static void main(String[] args) {
		Matrix Theta = admm();
		System.out.println("The estimated theta by ADMM is: ");
		System.out.println(Arrays.deepToString(Theta.getArray()));
	}
}
