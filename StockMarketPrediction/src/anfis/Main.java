package anfis;

import util.*;
//import nodes.*;
import data.*;

public class Main {
	public static void main(String[] args) {


		DataInterface testcase = new DataInterface("ibm_functionforcast_normalized.csv",3,90,2,2);

		Settings.numberOfInputVaribles = testcase.getNumberOfInputVariables();
		Settings.numberOfShapes = testcase.getNumberOfShapes();
		Settings.bellSlope = testcase.getBellSlope();
		Settings.inputVariables = testcase.getInputVariables();

		ANFIS anfis = new ANFIS();
		anfis.generateNetwork();

		System.out.println("--generation finished");

		double[][] trainingData = testcase.getTrainigData();
		double[] expectedOutput = testcase.getExpectedTrainingOutput();

		// Ausfuehren von Anfis

		double[][] result;
		int epochNumber = 100;
		double errorRate [][] = new double [epochNumber][1];
		
		
		// = WENN((A2*B2)> 0;1;0)
		
		for (int i = 0; i < epochNumber; i++){
			result = anfis.test(trainingData, expectedOutput);
			anfis.trainConsequent(trainingData, expectedOutput);
			anfis.trainPremise(trainingData, expectedOutput);
			System.out.println("Fehlerrate nach" + i + "-tem Training:" + result[0][0] );
			errorRate [i][0] = result[0][0];
			
		}	
	
		result = anfis.test(trainingData, expectedOutput);
		testcase.writeData(result, "out_ibm_ffn_200_15000_training.csv");
		testcase.writeData(errorRate, "out_ibm_ffn_200_15000_errorrate.csv");
		
		result = anfis.test(testcase.getTestData(), testcase.getExpectedTestOutput());
		
		
		testcase.writeData(result, "out_ibm_ffn_200_15000_test.csv");
		System.out.println("Finished !!!");

	}
}
