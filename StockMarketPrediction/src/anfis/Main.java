package anfis;

import util.*;
//import nodes.*;
import data.*;

public class Main {
	public static void main(String[] args) {

		// DataInterface xor_test = new DataInterface("input1.csv", 2,4,0,2,2);
		//DataInterface xor_test = new DataInterface("input1.csv", 2, 80, 2, 2);
		DataInterface testcase = new DataInterface("ibm_normalized.csv",3,80,2,2);

//		Settings.numberOfInputVaribles = xor_test.getNumberOfInputVariables();
//		Settings.numberOfShapes = xor_test.getNumberOfShapes();
//		Settings.bellSlope = xor_test.getBellSlope();
//		Settings.inputVariables = xor_test.getInputVariables();
		
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
		int epochNumber = 20;

		for (int i = 0; i < epochNumber; i++){
			result = anfis.test(trainingData, expectedOutput);
			anfis.trainConsequent(trainingData, expectedOutput);
			anfis.trainPremise(trainingData, expectedOutput);
			System.out.println("Fehlerrate nach" + i + "-tem Training:" + result[0][0] );
			
		}
		
//		result = anfis.test(trainingData, expectedOutput);
//
//		System.out.println("Anfaengliche Fehlerrate: " + result[0][0]);
//
//		anfis.trainConsequent(trainingData, expectedOutput);
//		result = anfis.test(trainingData, expectedOutput);
//		System.out
//				.println("Fehlerrate nach 1. Training (nur Consequent Parameter): "
//						+ result[0][0]);
//		anfis.trainPremise(trainingData, expectedOutput);
//		result = anfis.test(trainingData, expectedOutput);
//
//		System.out.println("Fehlerrate nach 1. Training: " + result[0][0]);
//
//		anfis.trainConsequent(trainingData, expectedOutput);
//		result = anfis.test(trainingData, expectedOutput);
//		System.out
//				.println("Fehlerrate nach 2. Training (nur Consequent Parameter): "
//						+ result[0][0]);
//		anfis.trainPremise(trainingData, expectedOutput);
//		result = anfis.test(trainingData, expectedOutput);
//
//		System.out.println("Fehlerrate nach 2. Training: " + result[0][0]);

		//testcase.writeData(result, "out.csv");
		
		result = anfis.test(testcase.getTestData(), testcase.getExpectedTestOutput());
		
		
		testcase.writeData(result, "out2.csv");
		System.out.println("Finished !!!");

	}
}
