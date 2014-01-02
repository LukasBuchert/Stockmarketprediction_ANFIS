package anfis;

import util.*;
//import nodes.*;
import data.*;

public class Main {
	public static void main(String[] args) {

		// get statistics

		DataReader input = new DataReader();
		double [][] data = input.readData();
		double[][] statistics = input.getStatistics();
		InputVariable[] inputVariables = new InputVariable[statistics[0].length-1];

		// i = 0 == expectedOutput
		
		for (int i = 0; i < inputVariables.length; i++) {
			inputVariables[i] = new InputVariable(i + 1, statistics[0][i+1],
					statistics[1][i+1]);
		}

		
		
		// set Settings
		
		Settings.numberOfInputVaribles = statistics[0].length - 1;
		// TODO do we need this ??
		Settings.trainingDataSize = 0;
		Settings.numberOfShapes = 4;
		Settings.bellSlope = 2;
		Settings.inputVariables = inputVariables;

		// Creating ANFIS network
		ANFIS anfis = new ANFIS();
		anfis.generateNetwork();
		
		System.out.println("--generation finished");
		
		double [] trainingData = new double [5];
		trainingData[0] = data [0][1];
		trainingData[1] = data [0][2];
		trainingData[2] = data [0][3];
		trainingData[3] = data [0][4];
		trainingData[4] = data [0][5];
		double expectedOutput = data[0][0];
		
		anfis.training(trainingData);
		
		System.out.println("Finished! Expected Output was: " + expectedOutput);

	}

}
