package nodes;

// layer 2

public class FiringStrengthNode extends Node implements NodeClient{
	
	public FiringStrengthNode() {
		super();
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
}
