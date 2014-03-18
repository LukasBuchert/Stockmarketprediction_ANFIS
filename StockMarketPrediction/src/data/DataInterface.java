package data;

import util.InputVariable;

/**
 * 
 * This class should be used in the future to get input and write output
 *	
 */
public class DataInterface {
	
	private String inputFilePath;
	// length of input from 1 to x / field x+1 contains expected output
	private int inputLength;
	private String outputFilePath;
	
	private double [][] data;
	private final int trainigDataLength;
	private final int testDataLength;
	private InputVariable [] inputVariables;
	private int numberOfShapes;
	private int bellSlope;
	
	
	
	public DataInterface(String inputFilePath, int inputLength,
			int trainigDataLength, int testDataLength, int numberOfShapes,
			int bellSlope) {
		this.inputFilePath = inputFilePath;
		this.inputLength = inputLength;
		this.trainigDataLength = trainigDataLength;
		this.testDataLength = testDataLength;
		this.numberOfShapes = numberOfShapes;
		this.bellSlope = bellSlope;
		readData();
	}
	
	public InputVariable[] getInputVariables() {
		return inputVariables;
	}
	public int getNumberOfShapes() {
		return numberOfShapes;
	}
	public int getBellSlope() {
		return bellSlope;
	}
	
	public int getNumberOfInputVariables(){
		return inputVariables.length;
	}
	
	public double [][] getTrainigData (){
		
		double trainigData[][] = new double [trainigDataLength][inputVariables.length];
		for (int i = 0; i < trainigDataLength; i++){
			trainigData[i] = data[i];
			// TODO check if this functions data is bigger than traingData, this might lead to exception
		}
		return trainigData;
	}
	
	public double [][] getTestData (int testDataLength){
		
		double testData[][] = new double [testDataLength][inputVariables.length];
		for (int i = trainigDataLength; i < (trainigDataLength + testDataLength); i++){
			testData[i] = data[i];
			// TODO check if this functions data is bigger than traingData, this might lead to exception
		}
		return testData;
		
	}
	
	private void readData(){
		// TODO read Data form Input File, Check if number of Datasets >= training + testData length
	}
	
	private void assignInputVariables (){
		// TODO assign for each column of input data (without the last one) an InputVariable
		// for that, min, max, avg have to calculated
	}
	
	private void writeData (double [][] output){
		// TODO write Output, output should contain estimated output and real ANFIS output
	}
	
	
}
