package nodes;

// layer 3

public class NormFSNode extends Node implements NodeClient{
	public FiringStrengthNode primaryPredecessorNode;
	
	public NormFSNode(FiringStrengthNode primaryPredecessorNode) {
		super();
		this.primaryPredecessorNode = primaryPredecessorNode;
		primaryPredecessorNode.setPrimarySuccessorNode(this);
	}
	
	public double computeSumOfFiringStrengths(int currentIteration) {
		double result = 0;
		for(Node n : predecessorNodes) {
			result += n.getOutput(currentIteration);
		}
		return result;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
}
