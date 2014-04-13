package anfis;

import util.*;
//import nodes.*;
import data.*;

public class Main {
	public static void main(String[] args) {

		// DataInterface xor_test = new DataInterface("input1.csv", 2,4,0,2,2);
		DataInterface xor_test = new DataInterface("input1.csv", 2, 80, 2, 2);
		DataInterface ibm2 = new DataInterface("ibm2.csv",3,80,2,2);

//		Settings.numberOfInputVaribles = xor_test.getNumberOfInputVariables();
//		Settings.numberOfShapes = xor_test.getNumberOfShapes();
//		Settings.bellSlope = xor_test.getBellSlope();
//		Settings.inputVariables = xor_test.getInputVariables();
		
		Settings.numberOfInputVaribles = ibm2.getNumberOfInputVariables();
		Settings.numberOfShapes = ibm2.getNumberOfShapes();
		Settings.bellSlope = ibm2.getBellSlope();
		Settings.inputVariables = ibm2.getInputVariables();

		ANFIS anfis = new ANFIS();
		anfis.generateNetwork();

		System.out.println("--generation finished");

		double[][] trainingData = ibm2.getTrainigData();
		double[] expectedOutput = ibm2.getExpectedTrainingOutput();

		// Ausfuehren von Anfis

		double[][] result;

		result = anfis.test(trainingData, expectedOutput);

		System.out.println("Anfaengliche Fehlerrate: " + result[0][0]);

		anfis.trainConsequent(trainingData, expectedOutput);
		result = anfis.test(trainingData, expectedOutput);
		System.out
				.println("Fehlerrate nach 1. Training (nur Consequent Parameter): "
						+ result[0][0]);
		anfis.trainPremise(trainingData, expectedOutput);
		result = anfis.test(trainingData, expectedOutput);

		System.out.println("Fehlerrate nach 1. Training: " + result[0][0]);

		anfis.trainConsequent(trainingData, expectedOutput);
		result = anfis.test(trainingData, expectedOutput);
		System.out
				.println("Fehlerrate nach 2. Training (nur Consequent Parameter): "
						+ result[0][0]);
		anfis.trainPremise(trainingData, expectedOutput);
		result = anfis.test(trainingData, expectedOutput);

		System.out.println("Fehlerrate nach 2. Training: " + result[0][0]);

		xor_test.writeData(result, "out.csv");

	}
}
