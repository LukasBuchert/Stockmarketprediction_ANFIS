package nodes;

// layer 3

public class NormFSNode extends Node implements NodeClient{
	public FiringStrengthNode primaryNode;
	
	public NormFSNode(FiringStrengthNode primaryNode) {
		super();
		this.primaryNode = primaryNode;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
}
