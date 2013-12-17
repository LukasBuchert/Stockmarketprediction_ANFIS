package nodes;

// layer 4

public class PolynomialNode extends Node implements NodeClient{
	public double[] consequentParameter;
	
	public PolynomialNode(double[] consequentParameter) {
		super();
		this.consequentParameter = consequentParameter;
	}
	
	public void updateConsequentParameter(double[] newConsequentParameter) {
		consequentParameter = newConsequentParameter;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
}
