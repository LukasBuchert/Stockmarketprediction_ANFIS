package nodes;

public class FeedforwardFunction implements NodeVisitor{
	private double[] input;
	private boolean saveNormFSOutput;

	public FeedforwardFunction(boolean saveNormFSOutput) {
		this.saveNormFSOutput = saveNormFSOutput;
	}
	
	public void setInput(double[] input) {
		this.input = input;
	}
	
	public void setSaveNormFSOutput(boolean saveNormFSOutput) {
		this.saveNormFSOutput = saveNormFSOutput;
	}
	
	@Override
	public void visit(FiringStrengthNode fsn) {
		fsn.setOutput(fsn.computeFiringStrength());
	}

	@Override
	public void visit(MembershipFunctionNode mfn) {
		double result = 1.0D / (1.0D + Math.pow(Math.pow((input[mfn.getVarNumber() - 1] - mfn.c) / mfn.a, 2.0D), mfn.b));
		mfn.setOutput(result);
	}

	@Override
	public void visit(NormFSNode nfsn) {
		double result = nfsn.primaryPredecessorNode.getOutput() / nfsn.computeSumOfFiringStrengths();
		nfsn.setOutput(result, saveNormFSOutput);
	}

	@Override
	public void visit(PolynomialNode pn) {
		double polyResult = 0;
		int i;
		for(i = 0; i < input.length; i++) {
			polyResult += input[i] * pn.consequentParameter[i];
		}
		//i++; //TODO: check if computed correctly
		polyResult += pn.consequentParameter[i];
		pn.polyResult = polyResult;
		double result = pn.predecessorNodes.get(0).getOutput() * polyResult;
		pn.setOutput(result);
	}

	@Override
	public void visit(OutputNode on) {
		double result = 0;
		for(Node n : on.predecessorNodes) {
			result += n.getOutput();
		}
		on.setOutput(result);
	}

}
