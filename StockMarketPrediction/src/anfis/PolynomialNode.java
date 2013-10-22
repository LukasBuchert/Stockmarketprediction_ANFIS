package anfis;

public class PolynomialNode extends Node {
	private double p;
	private double q;
	private double r;
	
	private double output;
	
	public PolynomialNode(double p, double q, double r) {
		super();
		this.p = p;
		this.q = q;
		this.r = r;
	}
	
	public double applyPolinomial(double x, double y) {
		output = ((NormFSNode)linkedNodes.get(0)).getNormFS() * (p * x + q * y + r);
		return output;
	}
	
	public double getOutput() {
		return output;
	}
}
