package anfis;

import util.*;
//import nodes.*;
import data.*;

public class Main {
	public static void main(String[] args) {

		// get statistics

		DataReader input = new DataReader();
		input.readData();
		double[][] statistics = input.getStatistics();
		InputVariable[] inputVariables = new InputVariable[statistics[0].length];

		for (int i = 0; i < statistics[0].length; i++) {
			inputVariables[i] = new InputVariable(i + 1, statistics[0][i],
					statistics[1][i]);
		}

		Settings.numberOfInputVaribles = statistics[0].length;
		// TODO do we need this ??
		Settings.trainingDataSize = 0;
		Settings.numberOfShapes = 4;
		Settings.bellSlope = 2;
		Settings.inputVariables = inputVariables;

		// Creating ANFIS network
		ANFIS anfis = new ANFIS();
		anfis.generateNetwork();
		
		System.out.println("--generation finished");

	}

}
