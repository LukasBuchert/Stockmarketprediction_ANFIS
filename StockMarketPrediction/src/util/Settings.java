package util;

public class Settings {
	
	
	// number of inputVaribles
	public static int numberOfInputVaribles;
	
	// number of bell shapes per input varible
	public static int numberOfShapes;
	
	// slope of bell shapes
	public static double bellSlope;
	
	// maximal Iterations for backpropagation in epoch
	public static int backprob_maxit;
	
	// backpropagation threshold
	public static double backprob_threshold;
	
	// enable custom stock trend error
	public static boolean customstock;
	
	// trend error function thresold
	public static int customstock_threshold;
	
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
