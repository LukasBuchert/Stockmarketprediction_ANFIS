package nodes;

public class OutputNode extends Node implements NodeClient{

	public OutputNode() {
		super();
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
}
