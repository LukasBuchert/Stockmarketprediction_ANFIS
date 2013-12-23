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
	
	// consequent parameters
	public static double [] consequentParameter;
	
	
	// statistics of trainigData input Varibles
	// [x][y]; x = number of variable -1 / y0 = min, y1 = max, y2 = avg
	//public static double [][] trainingDataStatistics;
	
	public static InputVariable [] inputVariables;
	
	public static double getMin( int varNumber){
		double back = 0.0;
		for (InputVariable i : inputVariables){
			if (i.equals(varNumber) != null){
				back = i.getMin();
				break;
			}
		}
		return back;
	}
	
	public static double getMax( int varNumber){
		double back = 0.0;
		for (InputVariable i : inputVariables){
			if (i.equals(varNumber) != null){
				back = i.getMax();
				break;
			}
		}
		return back;
	}
	
	public static double getAvg( int varNumber){
		double back = 0.0;
		for (InputVariable i : inputVariables){
			if (i.equals(varNumber) != null){
				back = i.getAvg();
				break;
			}
		}
		return back;
	}
	
}
