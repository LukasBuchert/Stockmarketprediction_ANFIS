package anfis;

import util.*;
//import nodes.*;
import data.*;

public class Main {
	public static void main(String[] args) {


		DataInterface testcase = new DataInterface("ibm_functionforcast_short.csv",3,90,3,2);

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
		testcase.writeData(result, "out_ibm_ff_short_20_10000_3m_zc_training.csv");
		testcase.writeData(errorRate, "out_ibm_ff_short_20_10000_3m_zc_errorrate.csv");
		
		result = anfis.test(testcase.getTestData(), testcase.getExpectedTestOutput());
		
		
		testcase.writeData(result, "out_ibm_ff_short_100_10000_3m_zc_test.csv");
		System.out.println("Finished !!!");

	}
}
