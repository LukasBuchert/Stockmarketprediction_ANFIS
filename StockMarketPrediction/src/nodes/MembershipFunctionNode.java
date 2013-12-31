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
	private double errorSumA;
	private double errorSumB;
	private double errorSumC;

	public MembershipFunctionNode(double a, double b, double c, int varNumber,
			int setNumber) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
		this.varNumber = varNumber;
		this.setNumber = setNumber;
		errorSumA = 0.0D;
		errorSumB = 0.0D;
		errorSumC = 0.0D;
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
	
	public MembershipFunctionNode getMFSNode(int varNumber, int setNumber){
		if (this.varNumber == varNumber && this.setNumber == setNumber){
			return this;
		}else {
			return null;
		}
	}

	public double getErrorSumA(boolean clearErrorSum) {
		double ret = errorSumA;
		if(clearErrorSum) {
			errorSumA = 0.0D;
		}
		return ret;
	}

	public void updateErrorSumA(double errorA) {
		this.errorSumA += errorA;
	}

	public double getErrorSumB(boolean clearErrorSum) {
		double ret = errorSumB;
		if(clearErrorSum) {
			errorSumB = 0.0D;
		}
		return ret;
	}

	public void updateErrorSumB(double errorB) {
		this.errorSumB += errorB;
	}

	public double getErrorSumC(boolean clearErrorSum) {
		double ret = errorSumC;
		if(clearErrorSum) {
			errorSumC = 0.0D;
		}
		return ret;
	}

	public void updateErrorSumC(double errorC) {
		this.errorSumC += errorC;
	}

}
