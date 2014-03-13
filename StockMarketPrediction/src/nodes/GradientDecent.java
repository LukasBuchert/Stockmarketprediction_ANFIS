package nodes;

public class GradientDecent implements NodeVisitor {
	private double learningRate;
	
	public void setLearningRate(double k) {
		this.learningRate = k/Math.sqrt(MembershipFunctionNode.getOverallSquaredErrorSum());
	}
	
	@Override
	public void visit(FiringStrengthNode fsn) {
		//do nothing
	}

	@Override
	public void visit(MembershipFunctionNode mfn) {
		//TODO: problem with mfn output = 1
//		System.out.println("A: " + mfn.getErrorSumA(false));
//		System.out.println("B: " + mfn.getErrorSumB(false));
//		System.out.println("C: " + mfn.getErrorSumC(false));
		mfn.a -= learningRate * mfn.getErrorSumA(true);
		mfn.b -= learningRate * mfn.getErrorSumB(true);
		mfn.c -= learningRate * mfn.getErrorSumC(true);
	}

	@Override
	public void visit(NormFSNode nfsn) {
		//do nothing
	}

	@Override
	public void visit(PolynomialNode pn) {
		//do nothing
	}

	@Override
	public void visit(OutputNode on) {
		//do nothing
	}

}
