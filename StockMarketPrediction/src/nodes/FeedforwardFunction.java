package nodes;

import java.util.Iterator;

public class FeedforwardFunction implements NodeVisitor{
	private double[] input;
	private int index;

	// firing strength function for FiringStrengthNode
	private double firingStrengthFunction(double value1, double value2) {
		return Math.min(value1, value2);
	}
	
	@Override
	public void visit(FiringStrengthNode fsn) {
		Iterator<Node> predNodes = fsn.predecessorNodes.iterator();
		double result = predNodes.next().getOutput();
		while(predNodes.hasNext()) {
			result = firingStrengthFunction(result, predNodes.next().getOutput());
		}
		fsn.setOutput(result);
	}

	@Override
	public void visit(MembershipFunctionNode mfn) {
		double result = 1.0D / (1.0D + Math.pow(Math.pow((input[index] - mfn.c) / mfn.a, 2.0D), mfn.b));
		mfn.setOutput(result);
	}
	
	public double computeSumOfFiringStrengths(Node nfsn) {
		double result = 0;
		for(Node n : nfsn.predecessorNodes) {
			result += n.getOutput();
		}
		return result;
	}

	@Override
	public void visit(NormFSNode nfsn) {
		double result = nfsn.primaryNode.getOutput() / computeSumOfFiringStrengths(nfsn);
		nfsn.setOutput(result);
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
		double result = pn.predecessorNodes.get(0).getOutput() * polyResult;
		pn.setOutput(result);
	}

}
