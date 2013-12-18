package nodes;

// layer 1

public class MembershipFunctionNode extends Node implements NodeClient {
	public double a;
	public double b;
	public double c;
	// number of variable (in model something like A, B, C ...)
	private final int varNumber;

	// number of fuzzy set (in model something like A1, A2, A3, ...)
	private final int setNumber;

	public MembershipFunctionNode(double a, double b, double c, int varNumber,
			int setNumber) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
		this.varNumber = varNumber;
		this.setNumber = setNumber;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * 
	 * @return Attention Numbers from 1 to n
	 */
	public int getVarNumber() {
		return varNumber;
	}

	/**
	 * 
	 * @return Attention Numbers from 1 to n
	 */

	public int getSetNumber() {
		return setNumber;
	}

}
