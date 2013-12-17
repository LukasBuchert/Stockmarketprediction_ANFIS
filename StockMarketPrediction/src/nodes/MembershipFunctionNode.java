package nodes;

// layer 1

public class MembershipFunctionNode extends Node implements NodeClient{
	public double a;
	public double b;
	public double c;
	
	public MembershipFunctionNode(double a, double b, double c) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}	
	
}
