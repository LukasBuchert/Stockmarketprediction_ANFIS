package nodes;

import java.util.Iterator;

public class FeedforwardFunction implements NodeVisitor{
	private double[] input;
	private int currentIteration;
	private int index;

	public void setCurrentIteration(int currentIteration) {
		this.currentIteration = currentIteration;
	}

	// firing strength function for FiringStrengthNode
	private double firingStrengthFunction(double value1, double value2) {
		return Math.min(value1, value2);
		//return value1 * value2
	}
	
	@Override
	public void visit(FiringStrengthNode fsn) {
		Iterator<Node> predNodes = fsn.predecessorNodes.iterator();
		double result = predNodes.next().getOutput(currentIteration);
		while(predNodes.hasNext()) {
			result = firingStrengthFunction(result, predNodes.next().getOutput(currentIteration));
		}
		fsn.setOutput(result, currentIteration);
	}

	@Override
	public void visit(MembershipFunctionNode mfn) {
		double result = 1.0D / (1.0D + Math.pow(Math.pow((input[index] - mfn.c) / mfn.a, 2.0D), mfn.b));
		mfn.setOutput(result, currentIteration);
	}
	
	public double computeSumOfFiringStrengths(Node nfsn) {
		double result = 0;
		for(Node n : nfsn.predecessorNodes) {
			result += n.getOutput(currentIteration);
		}
		return result;
	}

	@Override
	public void visit(NormFSNode nfsn) {
		double result = nfsn.primaryNode.getOutput(currentIteration) / computeSumOfFiringStrengths(nfsn);
		nfsn.setOutput(result, currentIteration);
	}

	@Override
	public void visit(PolynomialNode pn) {
		double polyResult = 0;
		int i;
		for(i = 0; i < input.length; i++) {
			polyResult += input[i] * pn.consequentParameter[i];
		}
		i++; //TODO: check if computed correctly
		polyResult += pn.consequentParameter[i];
		double result = pn.predecessorNodes.get(0).getOutput(currentIteration) * polyResult;
		pn.setOutput(result, currentIteration);
	}

	@Override
	public void visit(OutputNode on) {
		double result = 0;
		for(Node n : on.predecessorNodes) {
			result += n.getOutput(currentIteration);
		}
		on.setOutput(result, currentIteration);
	}

}
