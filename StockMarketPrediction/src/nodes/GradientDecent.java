package nodes;

public class GradientDecent implements NodeVisitor {
	private double learningRate;
	
	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}
	
	@Override
	public void visit(FiringStrengthNode fsn) {
		//do nothing
	}

	@Override
	public void visit(MembershipFunctionNode mfn) {
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
