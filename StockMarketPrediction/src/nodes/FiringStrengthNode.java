package nodes;

import util.Settings;

// layer 2

public class FiringStrengthNode extends Node{
	private double[] firingStrength;
	
	public FiringStrengthNode() {
		super();
		firingStrength = new double[Settings.trainingDataSize];
	}
	
	public double computeFiringStregnth() {
		incCurrIter();
		
		double result;
		result = ((MembershipFunctionNode)successor.get(0)).getMembershipValue();
		for(Node n : successor) {
			if(n instanceof MembershipFunctionNode) {
				result = firingStrengthFunction(result, ((MembershipFunctionNode)n).getMembershipValue());
			}
		}
		firingStrength[currentIteration] = result;
		return firingStrength[currentIteration];
	}
	
	private double firingStrengthFunction(double value1, double value2) {
		return Math.min(value1, value2);
	}
	
	public double getFiringStrength() {
		return firingStrength[currentIteration];
	}
}
