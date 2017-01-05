package Solution;

import java.util.Random;

import org.apache.commons.lang.ArrayUtils;

public class solution_fifth {
	public int maxIteration=100000;
	public double alpha=0.001D;
	public double[] theta=null;
	public Random random= new Random();
	
	public double costFunction(double[] t){
		return Math.exp(t[0]+0.5* t[1] - 2* t[2])+ t[0] * t[0] + t[1] * t[1] + t[2] * t[2] + t[0] * t [1] + t[1] * t [2];  
	}
	
	//NOTE: theta[0] is theta 1 , theta[1] is theta 2 and theta[2] is theta 3
	public void gradientDescent(){
		double[] gradient=new double[this.theta.length];
		double commonGradientValue = 0.0;
		double[] previousValueOfTheta = new double[this.theta.length];
		double convergence = 0.0;
		boolean isConvergence= false;
		
		while(!isConvergence){
			
			commonGradientValue = Math.exp(this.theta[0] + 0.5* this.theta[1] - 2 * this.theta[2]);
			gradient[0]= commonGradientValue + 2 * this.theta[0]+ this.theta[1];
		    gradient[1]= commonGradientValue *0.5 + 2 * this.theta[1]+ this.theta[0] + this.theta[2];
			gradient[2]= commonGradientValue *(-2) + 2 * this.theta[2]+ this.theta[1];
			
			for(int j=0;j<this.theta.length;j++){
				previousValueOfTheta[j] = this.theta[j];
				this.theta[j]=this.theta[j] - this.alpha*gradient[j] ;
				convergence = previousValueOfTheta[j] - this.theta[j];
				convergence = (convergence < 0 ? -convergence : convergence);		
				if( convergence < 0.001){
					isConvergence = true;
					break;
				}
			}
			 
		}
		System.out.println("Gradient :"+gradient[0]+" "+gradient[1]+" "+gradient[2]);

	}
	
	public solution_fifth(){
		theta=new double[3];
		for (int i=0;i<theta.length;i++){
			theta[i]=(random.nextInt(10))/10.0D;
		}
		System.out.println("Initialization of theta values: "+ArrayUtils.toString(theta));
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		solution_fifth gd=new solution_fifth();
		gd.gradientDescent();
		System.out.println("Fnction Value:" + gd.costFunction(gd.theta)+" Theta values:"+ArrayUtils.toString(gd.theta));
		
	}
}
