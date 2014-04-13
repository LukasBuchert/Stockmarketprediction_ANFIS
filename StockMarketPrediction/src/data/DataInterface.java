package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import util.InputVariable;

/**
 * 
 * This class should be used in the future to get input and write output
 * 
 */
public class DataInterface {

	private String inputFilePath;
	// length of input from 1 to inputLength / field inputLength+1 has to
	// contain expected output
	private int inputLength;
	private String outputFilePath;
	private double errorSum;
	private int numberOfData;

	private double[][] data;
	private final int trainingDataLength;
	private final int testDataLength;
	private InputVariable[] inputVariables;
	private int numberOfShapes;
	private int bellSlope;

	/**
	 * 
	 * @param inputFilePath
	 *            - Path of Input
	 * @param inputLength
	 *            - Number of Input Variables
	 * @param trainigDataLength
	 *            - Length of Data for train the ANFIS
	 * @param testDataLength
	 *            - Length of Data to test ANFIS with
	 * @param numberOfShapes
	 *            - number of Fuzzy Sets for each Input Variable
	 * @param bellSlope
	 *            - Bell Slope in Membership Function
	 */
	public DataInterface(String inputFilePath, int inputLength,
			int trainigDataLength, int testDataLength, int numberOfShapes,
			int bellSlope) {
		this.inputFilePath = inputFilePath;
		this.inputLength = inputLength;
		this.trainingDataLength = trainigDataLength;
		this.testDataLength = testDataLength;
		this.numberOfShapes = numberOfShapes;
		this.bellSlope = bellSlope;
		inputVariables = new InputVariable[inputLength];
		numberOfData = countData();
		readData();
		protokoll(data);
		protokoll(this.getTrainigData());
		protokoll(this.getTestData());

	}

	public DataInterface(String inputFilePath, int inputLength,
			int trainingPercentage, int numberOfShapes, int bellSlope) {
		this.inputFilePath = inputFilePath;
		this.inputLength = inputLength;
		numberOfData = countData();
		this.trainingDataLength = trainingPercentage * numberOfData / 100;
		this.testDataLength = numberOfData - trainingDataLength;
		this.numberOfShapes = numberOfShapes;
		this.bellSlope = bellSlope;
		inputVariables = new InputVariable[inputLength];
		readData();
		protokoll(data);
		protokoll(this.getTrainigData());
		protokoll(this.getTestData());

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

	public int getNumberOfInputVariables() {
		return inputVariables.length;
	}

	public double getErrorSum() {
		return this.errorSum;
	}

	public double[][] getTrainigData() {

		double trainigData[][] = new double[trainingDataLength][inputVariables.length];
		for (int i = 0; i < trainingDataLength; i++) {
			for (int m = 0; m < inputVariables.length; m++) {
				trainigData[i][m] = data[i][m];
			}
		}
		return trainigData;
	}

	public double[] getExpectedTrainingOutput() {
		double expectedOutput[] = new double[trainingDataLength];
		for (int i = 0; i < trainingDataLength; i++) {
			expectedOutput[i] = data[i][inputVariables.length];
		}
		return expectedOutput;
	}

	public double[][] getTestData() {

		double testData[][] = new double[testDataLength][inputVariables.length];
		for (int i = trainingDataLength, j = 0; i < (trainingDataLength + testDataLength); i++, j++) {
			for (int m = 0; m < inputVariables.length; m++) {
				testData[j][m] = data[i][m];
			}

		}
		return testData;

	}

	public double[] getExpectedTestOutput() {
		double expectedOutput[] = new double[testDataLength];
		for (int i = 0; i < testDataLength; i++) {
			expectedOutput[i] = data[i + trainingDataLength][inputVariables.length];
		}
		return expectedOutput;
	}

	private void readData() {
		data = new double[trainingDataLength + testDataLength][inputLength + 1];

		try {

			final BufferedReader reader = new BufferedReader(new FileReader(
					inputFilePath));
			String current;
			current = reader.readLine();

			for (int i = 0; i < data.length && current != null; i++) {

				processCsvLine(current, i);
				current = reader.readLine();

			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		generateStatistic();

	}

	/**
	 * Verarbeite eine einzelne Zeile.
	 */
	private void processCsvLine(final String line, int row) {
		final StringTokenizer st = new StringTokenizer(line, ";");

		for (int j = 0; j <= inputLength && st.hasMoreTokens(); j++) {

			data[row][j] = Double.valueOf(st.nextToken());
		}

	}

	private void generateStatistic() {

		for (int i = 0; i < inputLength; i++) {
			double min = data[0][i];
			double max = 0;
			double avg = 0;

			for (int j = 0; j < trainingDataLength; j++) {

				if (data[j][i] < min) {
					min = data[j][i];
				}

				if (data[j][i] > max) {
					max = data[j][i];
				}

				avg += data[j][i];
			}

			avg = avg / data.length;

			inputVariables[i] = new InputVariable(i + 1, min, max, avg);
		}

	}

	private int countData() {
		int back = 1;
		try {
			BufferedReader in = new BufferedReader(
					new FileReader(inputFilePath));
			String zeile = null;
			while ((zeile = in.readLine()) != null) {
				back++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return back;
	}

	public void writeData(double[][] output, String outputFilePath) {

		this.outputFilePath = outputFilePath;
		errorSum = output[0][0];

		try {

			FileWriter writer = new FileWriter(outputFilePath);

			for (int i = 1; i < output.length; i++) {
				for (int j = 0; j < output[0].length; j++) {
					writer.append(Double.toString(output[i][j]));
					writer.append(';');
				}
				writer.append('\n');
			}

			writer.flush();
			writer.close();
		} catch (Exception e) {

		}
	}

	public static void protokoll(double[][] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				System.out.print(data[i][j] + " ");
			}
			System.out.println();
		}
	}

}
