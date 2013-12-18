package util;

public class Settings {
	
	
	// number of inputVaribles
	public static int numberOfInputVaribles;
	
	// size of trainingData
	public static int trainingDataSize;
	
	// number of bell shapes per input varible
	public static int numberOfShapes;
	
	// slope of bell shapes
	public static double bellSlope;
	
	
	// statistics of trainigData input Varibles
	// [x][y]; x = number of variable -1 / y0 = min, y1 = max, y2 = avg
	public static double [][] trainingDataStatistics;
	
	public static double getMin( int varNumber){
		return trainingDataStatistics [varNumber-1][0];
	}
	
	public static double getMax( int varNumber){
		return trainingDataStatistics [varNumber-1][1];
	}
	
	public static double getAvg( int varNumber){
		return trainingDataStatistics [varNumber-1][2];
	}
	
}
