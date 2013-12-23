package util;

public class InputVariable {

	// class can later on be used to define special fuzzy sets / slope and so one
	
	private String name;
	private final double min;
	private final double max;
	
	// should be the same as in MembershipNodes
	private final int varNumber;
	
	// add on
	private double avg;
	
	
	public InputVariable (int varNumber, double min, double max){
		
		this.varNumber = varNumber;
		this.min = min;
		this.max = max;
	}


	public String getName() {
		return name;
	}


	public double getMin() {
		return min;
	}


	public double getMax() {
		return max;
	}


	public int getVarNumber() {
		return varNumber;
	}


	public double getAvg() {
		return avg;
	}
	
	public void setName(String name) {
		this.name = name;
	}


	public InputVariable equals (int varNumber){
		if (this.varNumber == varNumber){
			return this;
		}else {
			return null;
		}
	}
	
}
