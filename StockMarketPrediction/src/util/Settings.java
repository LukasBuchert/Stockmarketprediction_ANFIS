package util;

public class Settings {
	public static int trainingDataSize;
	// number of bell shapes per input varible
	public static int numberOfShapes;
	// slope of bell shapes
	public static double bellSlope;
	// current iteration, number of current data set tuple
	public static int currentIteration = -1;
	
	public static void incCurrIter() {
		currentIteration++;
	}
}
