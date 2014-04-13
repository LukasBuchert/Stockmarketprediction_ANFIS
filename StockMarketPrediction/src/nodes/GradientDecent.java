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
		double dataSize = (double)mfn.getVisits(true);
		mfn.a -= learningRate * mfn.getErrorSumA(true) / dataSize;
		mfn.b -= learningRate * mfn.getErrorSumB(true) / dataSize;
		mfn.c -= learningRate * mfn.getErrorSumC(true) / dataSize;
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
