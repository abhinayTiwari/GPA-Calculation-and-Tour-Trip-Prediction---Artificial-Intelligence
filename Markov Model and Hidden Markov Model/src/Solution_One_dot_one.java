package Solution;

import java.awt.GradientPaint;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.lang.ArrayUtils;

import Jama.Matrix;


public class Solution_One_dot_one {
	
	
	
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
		
		
		for(int i=0; i < length; i++){
			for(int j=0; j < length; j++){
				for(int k=0; k < length; k++){
					for(int l=0; l < length; l++){
						currentSequence.add(i);
						currentSequence.add(j);
						currentSequence.add(k);
						currentSequence.add(l);
						currentProbability = mappingOfCities[i] *  transitionMatrix.get(i, j) * transitionMatrix.get(j, k) * transitionMatrix.get(k, l);
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
	
	
	
	
	public static void main(String[] args) {
		ArrayList largestSequence= new ArrayList();
		largestSequence = findLargestProbability();
		ArrayList citiesHavingLargestProbability= new ArrayList();
		citiesHavingLargestProbability = findCitiesHavingLargestProbability(largestSequence);
		
		System.out.println("Largest Sequence "+ArrayUtils.toString(largestSequence));
		System.out.println("Sequence of four Cities that has largest probability based on MM is  "+ArrayUtils.toString(citiesHavingLargestProbability));

	}

}
