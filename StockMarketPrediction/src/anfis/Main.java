package anfis;

import util.*;
//import nodes.*;
import data.*;

public class Main {

	private static String inputPath;
	private static int numberOfInputVariables;
	private static int percentage;
	private static int numberOfShapes;
	private static int bellSlope;
	private static int epochNumber;
	private static String outputPath;

	public Main(String inputPath, int numberOfInputVariables, int percentage,
			int numberOfShapes, int bellSlope, int epochNumber,
			String outputPath, int backprob_maxit, double backprob_threshold, boolean customstock, double customstock_threshold) {
		super();
		Main.inputPath = inputPath;
		Main.numberOfInputVariables = numberOfInputVariables;
		Main.percentage = percentage;
		Main.numberOfShapes = numberOfShapes;
		Main.bellSlope = bellSlope;
		Main.epochNumber = epochNumber;
		Main.outputPath = outputPath;
		Settings.backprob_maxit = backprob_maxit;
		Settings.backprob_threshold = backprob_threshold;
		Settings.customstock = customstock;
		Settings.customstock_threshold = customstock_threshold;
	}

	public static void runANFIS() {
		DataInterface testcase = new DataInterface(inputPath,
				numberOfInputVariables, percentage, numberOfShapes, bellSlope);

		Settings.numberOfInputVaribles = testcase.getNumberOfInputVariables();
		Settings.numberOfShapes = testcase.getNumberOfShapes();
		Settings.bellSlope = testcase.getBellSlope();
		Settings.inputVariables = testcase.getInputVariables();

		ANFIS anfis = new ANFIS();
		anfis.generateNetwork();

		double[][] trainingData = testcase.getTrainigData();
		double[] expectedOutput = testcase.getExpectedTrainingOutput();
		double[][] result;
		double errorRate[][] = new double[epochNumber][1];

		for (int i = 0; i < epochNumber; i++) {
			result = anfis.test(trainingData, expectedOutput);
			errorRate[i][0] = result[0][0];
			anfis.trainConsequent(trainingData, expectedOutput);
			anfis.trainPremise(trainingData, expectedOutput);
			

		}

		result = anfis.test(trainingData, expectedOutput);
		testcase.writeData(result, outputPath + "_training.csv");
		testcase.writeData(errorRate, outputPath + "_errorrate.csv");

		result = anfis.test(testcase.getTestData(),
				testcase.getExpectedTestOutput());

		testcase.writeData(result, outputPath + "_test.csv");

	}

	public static void main(String[] args) {

		inputPath = "ibm_functionforcast_short.csv";
		numberOfInputVariables = 3;
		percentage = 90;
		numberOfShapes = 3;
		bellSlope = 2;
		epochNumber = 2;
		outputPath = "maintest";
		
	}
}
