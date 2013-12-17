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
<<<<<<< HEAD

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
=======
	
	public double applyPolinomial(double x, double y) {
		incCurrIter();
		
		output[currentIteration] = ((NormFSNode)successor.get(0)).getNormFS() * (consequentParameter[0] * x + consequentParameter[1] * y + consequentParameter[2]);
		return output[currentIteration];
	}
	
	public double getOutput() {
		return output[currentIteration];
>>>>>>> d8fa6ad39ed22c055ce92ff0a978efb10931c509
	}
}
