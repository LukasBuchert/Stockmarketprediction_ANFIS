package nodes;

// layer 2

public class FiringStrengthNode extends Node implements NodeClient{
	
	public FiringStrengthNode() {
		super();
<<<<<<< HEAD
=======
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
>>>>>>> d8fa6ad39ed22c055ce92ff0a978efb10931c509
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
}
