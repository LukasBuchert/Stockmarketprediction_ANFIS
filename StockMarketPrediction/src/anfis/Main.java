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
			String outputPath, int backprob_maxit, double backprob_threshold, boolean customstock, int customstock_threshold) {
		super();
		this.inputPath = inputPath;
		this.numberOfInputVariables = numberOfInputVariables;
		this.percentage = percentage;
		this.numberOfShapes = numberOfShapes;
		this.bellSlope = bellSlope;
		this.epochNumber = epochNumber;
		this.outputPath = outputPath;
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
			anfis.trainConsequent(trainingData, expectedOutput);
			anfis.trainPremise(trainingData, expectedOutput);
			errorRate[i][0] = result[0][0];

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

		runANFIS();

		// DataInterface testcase = new DataInterface(
		// "ibm_functionforcast_short.csv", 3, 90, 3, 2);
		//
		// Settings.numberOfInputVaribles =
		// testcase.getNumberOfInputVariables();
		// Settings.numberOfShapes = testcase.getNumberOfShapes();
		// Settings.bellSlope = testcase.getBellSlope();
		// Settings.inputVariables = testcase.getInputVariables();
		//
		// ANFIS anfis = new ANFIS();
		// anfis.generateNetwork();
		//
		// System.out.println("--generation finished");
		//
		// double[][] trainingData = testcase.getTrainigData();
		// double[] expectedOutput = testcase.getExpectedTrainingOutput();
		//
		// // Ausfuehren von Anfis
		//
		// double[][] result;
		// // int epochNumber = 20;
		// double errorRate[][] = new double[epochNumber][1];
		//
		// // = WENN((A2*B2)> 0;1;0)
		//
		// for (int i = 0; i < epochNumber; i++) {
		// result = anfis.test(trainingData, expectedOutput);
		// anfis.trainConsequent(trainingData, expectedOutput);
		// anfis.trainPremise(trainingData, expectedOutput);
		// System.out.println("Fehlerrate nach" + i + "-tem Training:"
		// + result[0][0]);
		// errorRate[i][0] = result[0][0];
		//
		// }
		//
		// result = anfis.test(trainingData, expectedOutput);
		// testcase.writeData(result,
		// "out_ibm_ff_short_20_10000_3m_zc_training.csv");
		// testcase.writeData(errorRate,
		// "out_ibm_ff_short_20_10000_3m_zc_errorrate.csv");
		//
		// result = anfis.test(testcase.getTestData(),
		// testcase.getExpectedTestOutput());
		//
		// testcase.writeData(result,
		// "out_ibm_ff_short_100_10000_3m_zc_test.csv");
		// System.out.println("Finished !!!");
		//
	}
}
