package anfis;

import java.util.Arrays;

public class PolynomialNode extends Node {
	private double[] consequentParameter;
	
	private double output;
	
	public PolynomialNode(double[] consequentParameter) {
		super();
		this.consequentParameter = consequentParameter;
	}
	
	public void updateConsequentParameter(double[] newConsequentParameter) {
		consequentParameter = newConsequentParameter;
	}
	
	public double applyPolinomial(double x, double y) {
		output = ((NormFSNode)linkedNodes.get(0)).getNormFS() * (consequentParameter[0] * x + consequentParameter[1] * y + consequentParameter[2]);
		return output;
	}
	
	public double getOutput() {
		return output;
	}
}
