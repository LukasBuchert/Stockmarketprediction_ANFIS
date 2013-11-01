package nodes;

import util.Settings;

// layer 1

public class MembershipFunctionNode extends Node{
	private double a;
	private double b;
	private double c;
	
	private double membershipValue[];
	
	public MembershipFunctionNode(double a, double b, double c) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
		membershipValue = new double[Settings.trainingDataSize];
	}
	
	public double applyMembershipFunction(double x) {
		incCurrIter();
		
		membershipValue[currentIteration] =  1.0D / (1.0D + Math.pow(Math.pow((x - c) / a, 2.0D), b));
		return membershipValue[currentIteration];
	}
	
	public double getMembershipValue() {
		return membershipValue[currentIteration];
	}	
	
}
