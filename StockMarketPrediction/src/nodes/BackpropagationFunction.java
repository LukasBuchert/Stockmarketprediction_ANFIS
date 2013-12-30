package nodes;

public class BackpropagationFunction implements NodeVisitor{
	private double[] expectedOutput;
	private int currentIteration;

	public void setExpectedOutput(double[] expectedOutput) {
		this.expectedOutput = expectedOutput;
	}

	public void setCurrentIteration(int currentIteration) {
		this.currentIteration = currentIteration;
	}

	@Override
	public void visit(FiringStrengthNode fsn) {
		double errorResult = 0.0D;
		double sumOfFiringStrengths = ((NormFSNode)fsn.successorNodes.get(0)).computeSumOfFiringStrengths();
		for(Node n : fsn.successorNodes) {
			if(n.equals(fsn.primarySuccessorNode)) {
				errorResult += n.error * (( sumOfFiringStrengths - fsn.getOutput() ) / sumOfFiringStrengths / sumOfFiringStrengths);
			} else {
				errorResult += n.error * fsn.getOutput() / sumOfFiringStrengths / sumOfFiringStrengths;
			}
		}
		fsn.error = errorResult;
	}

	@Override
	public void visit(MembershipFunctionNode mfn) {
		double errorResult = 0.0D;
		for(Node n : mfn.successorNodes) {
			errorResult += n.error * n.getOutput() / mfn.getOutput();
		}
		mfn.error = errorResult;
	}

	@Override
	public void visit(NormFSNode nfsn) {
		double errorResult = 0.0D;
		for(Node n : nfsn.successorNodes) {
			errorResult += n.error * n.getOutput() / nfsn.getOutput();
		}
		nfsn.error = errorResult;
	}

	@Override
	public void visit(PolynomialNode pn) {
		double errorResult = 0.0D;
		for(Node n : pn.successorNodes) {
			errorResult += n.error;
		}
		pn.error = errorResult;
	}

	@Override
	public void visit(OutputNode on) {
		on.error = -(expectedOutput[currentIteration] - on.getOutput());
	}

}
