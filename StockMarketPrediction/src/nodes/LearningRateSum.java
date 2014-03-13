package nodes;

public class LearningRateSum implements NodeVisitor {
	private double overallSquaredErrorSum = 0;
	
	public void reset() {
		overallSquaredErrorSum = 0;
	}

	@Override
	public void visit(FiringStrengthNode fsn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MembershipFunctionNode mfn) {		
		double errorA = mfn.getErrorSumA(false);
		double errorB = mfn.getErrorSumB(false);
		double errorC = mfn.getErrorSumC(false);
		
		overallSquaredErrorSum += errorA * errorA + errorB * errorB + errorC * errorC;
		
		MembershipFunctionNode.setOverallSquaredErrorSum(overallSquaredErrorSum);
	}

	@Override
	public void visit(NormFSNode nfsn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(PolynomialNode pn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OutputNode on) {
		// TODO Auto-generated method stub
		
	}

}
