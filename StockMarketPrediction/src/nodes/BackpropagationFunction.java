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
		double sumOfFiringStrengths = ((NormFSNode)fsn.successorNodes.get(0)).computeSumOfFiringStrengths(currentIteration);
		for(Node n : fsn.successorNodes) {
			if(n.equals(fsn.primarySuccessorNode)) {
				errorResult += n.error[currentIteration] * (( sumOfFiringStrengths - fsn.getOutput(currentIteration) ) / sumOfFiringStrengths / sumOfFiringStrengths);
			} else {
				errorResult += n.error[currentIteration] * fsn.getOutput(currentIteration) / sumOfFiringStrengths / sumOfFiringStrengths;
			}
		}
		fsn.error[currentIteration] = errorResult;
	}

	@Override
	public void visit(MembershipFunctionNode mfn) {
		double errorResult = 0.0D;
		for(Node n : mfn.successorNodes) {
			errorResult += n.error[currentIteration] * n.getOutput(currentIteration) / mfn.getOutput(currentIteration);
		}
		mfn.error[currentIteration] = errorResult;
	}

	@Override
	public void visit(NormFSNode nfsn) {
		double errorResult = 0.0D;
		for(Node n : nfsn.successorNodes) {
			errorResult += n.error[currentIteration] * n.getOutput(currentIteration) / nfsn.getOutput(currentIteration);
		}
		nfsn.error[currentIteration] = errorResult;
	}

	@Override
	public void visit(PolynomialNode pn) {
		double errorResult = 0.0D;
		for(Node n : pn.successorNodes) {
			errorResult += n.error[currentIteration];
		}
		pn.error[currentIteration] = errorResult;
	}

	@Override
	public void visit(OutputNode on) {
		on.error[currentIteration] = -(expectedOutput[currentIteration] - on.getOutput(currentIteration));
	}

}
