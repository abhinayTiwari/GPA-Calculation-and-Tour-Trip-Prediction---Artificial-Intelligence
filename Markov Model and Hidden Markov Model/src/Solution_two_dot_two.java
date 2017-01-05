package Solution;

import java.awt.GradientPaint;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistribution;
import org.apache.commons.math.distribution.NormalDistributionImpl;

import Jama.Matrix;


public class Solution_two_dot_two {
	
	
	private static NormalDistribution d;
	
	public static ArrayList findCitiesHavingLargestProbability(ArrayList largestSequence){
		ArrayList cities= new ArrayList();
		cities.add("A");
		cities.add("B");
		cities.add("W");
		cities.add("P");
		cities.add("N");
		
		ArrayList finalSequenceOfCities= new ArrayList();
		
		for(int i=0; i < largestSequence.size() ; i++){
			finalSequenceOfCities.add(cities.get((int) largestSequence.get(i)));
		}
		
		return finalSequenceOfCities;
		
	}
	
	
	
	
	public static  ArrayList findLargestProbability(){
		double[][] transitionValues = {{0,0,0.5,0.25,0.25}, {0.71, 0, 0, 0.14, 0.14}, {0.16, 0.16, 0, 0.33, 0.33}, {0.25, 0.5, 0.125,0 ,0.125}, {0.33, 0, 0.16,0.5 ,0}};
		Matrix transitionMatrix = new Matrix(transitionValues);
		int length = 5;
		double[] mappingOfCities = {0.5, 0.3, 0, 0.1, 0.1};
		double largestProbability = 0;
		ArrayList largestSequence= new ArrayList();
		largestSequence.add(0);
		largestSequence.add(0);
		largestSequence.add(0);
		largestSequence.add(0);
		ArrayList currentSequence= new ArrayList();
		double currentProbability = 0;
		int count =0;
		int indexOfWashingtonCity=2;
		ArrayList meanOfEveryCity = findMeanOfEveryCity();
		ArrayList standardDeviationOfEveryCity = findStandardDeviation();
		
		for(int i=0; i < length; i++){
			for(int j=0; j < length; j++){
				for(int k=0; k < length; k++){
					for(int l=0; l < length; l++){
						currentSequence.add(i);
						currentSequence.add(j);
						currentSequence.add(k);
						currentSequence.add(l);
						currentProbability = transitionMatrix.get(indexOfWashingtonCity,i) * normalDistribution((double)meanOfEveryCity.get(i), (double)standardDeviationOfEveryCity.get(i) , 355) *  transitionMatrix.get(i, j) * normalDistribution((double)meanOfEveryCity.get(j), (double)standardDeviationOfEveryCity.get(j) , 339) * transitionMatrix.get(j, k) *normalDistribution((double)meanOfEveryCity.get(k), (double)standardDeviationOfEveryCity.get(k) , 148)* transitionMatrix.get(k, l)*normalDistribution((double)meanOfEveryCity.get(l), (double)standardDeviationOfEveryCity.get(l) , 50);
						if(currentProbability > largestProbability){
							largestSequence.clear();
							largestProbability = currentProbability;
							largestSequence.add(currentSequence.get(0));
							largestSequence.add(currentSequence.get(1));
							largestSequence.add(currentSequence.get(2));
							largestSequence.add(currentSequence.get(3));
						}
						currentSequence.clear();
						count++;
					}   
				}  
			}
		}
		
		System.out.println("Largest Probability : "+largestProbability);
		return largestSequence;
		
		
	}
	
	
	public static double findMean (double[] data){
		double total = 0.0;
		for(int i=0; i< data.length; i++){
			total += (double)data[i];
		}
		
		return total/data.length;
	}
	
	public static double findStandardDeviation(double[] data){
		
		double mean = findMean(data);
		double total = 0.0;
		double difference = 0.0;
		for(int i=0; i< data.length; i++){
			difference = data[i] - mean; 
			total += Math.pow(difference, 2);
		}
		
		return Math.sqrt(total/data.length);
	} 
	
  public static ArrayList findMeanOfEveryCity(){
	  double[] albanyCityTrainingData = {60.44, 18.82, -200.04, 142.08, -195.59, 185.67, 200.59, 30.4, -336.63, 395.82, 202.59, 182.25, 159.14, 188.31, -270.73};
	  double[] bostonCityTrainingData = {346.4, 79.09, 620.13, 62.12, 496.2, 95.53, 292.77, -297.47, 369.64};
	  double[] washingtonCityTrainingData = {159.67, 537.1, 284.93, 152.77, 472.77, 346.42, -101.1, 61.39};
	  double[] philadelphiaCityTrainingData = {231.84, 177.49, 180.69, 213.88, 93.81, 214.96, 97.44, 154.91, 46.59, 75.75};
	  double[] newYorkCityTrainingData = {109.82, 100.25, 36.17, 3.76, 119.03, -22.4, 41.39};
	  ArrayList collectionOfExpensesAccordingToTheSequence= new ArrayList();
	  collectionOfExpensesAccordingToTheSequence.add(albanyCityTrainingData);
	  collectionOfExpensesAccordingToTheSequence.add(bostonCityTrainingData);
	  collectionOfExpensesAccordingToTheSequence.add(washingtonCityTrainingData);
	  collectionOfExpensesAccordingToTheSequence.add(philadelphiaCityTrainingData);
	  collectionOfExpensesAccordingToTheSequence.add(newYorkCityTrainingData);
	  int numberOfCities =5;
	  ArrayList meanOfEveryCity= new ArrayList();
	  ArrayList standardDeviationOfEveryCity= new ArrayList();
	  for(int i=0; i< numberOfCities; i++){
		  meanOfEveryCity.add(findMean((double[]) collectionOfExpensesAccordingToTheSequence.get(i)));
	  }

	  
	  return meanOfEveryCity ;
	
	} 
  
  public static ArrayList findStandardDeviation(){
	  double[] albanyCityTrainingData = {60.44, 18.82, -200.04, 142.08, -195.59, 185.67, 200.59, 30.4, -336.63, 395.82, 202.59, 182.25, 159.14, 188.31, -270.73};
	  double[] bostonCityTrainingData = {346.4, 79.09, 620.13, 62.12, 496.2, 95.53, 292.77, -297.47, 369.64};
	  double[] washingtonCityTrainingData = {159.67, 537.1, 284.93, 152.77, 472.77, 346.42, -101.1, 61.39};
	  double[] philadelphiaCityTrainingData = {231.84, 177.49, 180.69, 213.88, 93.81, 214.96, 97.44, 154.91, 46.59, 75.75};
	  double[] newYorkCityTrainingData = {109.82, 100.25, 36.17, 3.76, 119.03, -22.4, 41.39};
	  ArrayList collectionOfExpensesAccordingToTheSequence= new ArrayList();
	  collectionOfExpensesAccordingToTheSequence.add(albanyCityTrainingData);
	  collectionOfExpensesAccordingToTheSequence.add(bostonCityTrainingData);
	  collectionOfExpensesAccordingToTheSequence.add(washingtonCityTrainingData);
	  collectionOfExpensesAccordingToTheSequence.add(philadelphiaCityTrainingData);
	  collectionOfExpensesAccordingToTheSequence.add(newYorkCityTrainingData);
	  int numberOfCities =5;
	  ArrayList meanOfEveryCity= new ArrayList();
	  ArrayList standardDeviationOfEveryCity= new ArrayList();
	  for(int i=0; i< numberOfCities; i++){
		  standardDeviationOfEveryCity.add(findStandardDeviation((double[]) collectionOfExpensesAccordingToTheSequence.get(i)));
	  }
	  
	 // return normalDistributionValues;
	  
	  return standardDeviationOfEveryCity ;
	
	} 
  
	
	
  public static double normalDistribution(double mean, double standardDeviation,int data){
	  
	   d = new NormalDistributionImpl(mean,standardDeviation);
	   try {
		return d.cumulativeProbability(data);
	} catch (MathException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return data;
	 
  }
	
	public static void main(String[] args) {
		ArrayList largestSequence= new ArrayList();
		largestSequence = findLargestProbability();
		ArrayList citiesHavingLargestProbability= new ArrayList();
		citiesHavingLargestProbability = findCitiesHavingLargestProbability(largestSequence);
		
		System.out.println("Largest Sequence "+ArrayUtils.toString(largestSequence));
		System.out.println("Starting from Washington, the Sequence of next four Cities that has largest probability based on HMM is  "+ArrayUtils.toString(citiesHavingLargestProbability));

	}

}
