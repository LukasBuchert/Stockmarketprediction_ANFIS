package nodes;

// layer 2

public class FiringStrengthNode extends Node{
	public NormFSNode primarySuccessorNode;

	public FiringStrengthNode() {
		super();
	}
	
	public void setPrimarySuccessorNode(NormFSNode primarySuccessorNode) {
		this.primarySuccessorNode = primarySuccessorNode;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
}
