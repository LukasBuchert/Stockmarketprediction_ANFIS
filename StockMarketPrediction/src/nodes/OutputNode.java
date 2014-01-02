package nodes;

public class OutputNode extends Node {

	public OutputNode() {
		super();
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
}
