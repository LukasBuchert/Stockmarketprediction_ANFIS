package nodes;

import java.util.Arrays;

import util.Settings;


public class PolynomialNode extends Node {
	private double[] consequentParameter;
	
	private double[] output;
	
	public PolynomialNode(double[] consequentParameter) {
		super();
		this.consequentParameter = consequentParameter;
		output = new double[Settings.trainingDataSize];
	}
	
	public void updateConsequentParameter(double[] newConsequentParameter) {
		consequentParameter = newConsequentParameter;
	}
	
	public double applyPolinomial(double x, double y) {
		incCurrIter();
		
		output[currentIteration] = ((NormFSNode)linkedNodes.get(0)).getNormFS() * (consequentParameter[0] * x + consequentParameter[1] * y + consequentParameter[2]);
		return output[currentIteration];
	}
	
	public double getOutput() {
		return output[currentIteration];
	}
}
