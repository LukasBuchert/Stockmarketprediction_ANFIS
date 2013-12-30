package nodes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// layer 3

public class NormFSNode extends Node implements NodeClient{
	public FiringStrengthNode primaryPredecessorNode;
	private List<Double> savedOutputs;
	
	public NormFSNode(FiringStrengthNode primaryPredecessorNode) {
		super();
		this.primaryPredecessorNode = primaryPredecessorNode;
		primaryPredecessorNode.setPrimarySuccessorNode(this);
		savedOutputs = new ArrayList<Double>();
	}
	
	public double computeSumOfFiringStrengths() {
		double result = 0;
		for(Node n : predecessorNodes) {
			result += n.getOutput();
		}
		return result;
	}
	
	public double[] getSavedOutputs(boolean clearSavedOutputs) {
		double[] outputsArray = new double[savedOutputs.size()];
	    Iterator<Double> iterator = savedOutputs.iterator();
	    for (int i = 0; i < outputsArray.length; i++) {
	    	outputsArray[i] = iterator.next().doubleValue();
	    }
	    
	    if(clearSavedOutputs) {
	    	clearSavedOutputs();
	    }
	    
	    return outputsArray;
	}
	
	public void clearSavedOutputs() {
		savedOutputs.clear();
	}
	
	public void setOutput(double newOutput, boolean saveOutput) {
		output = newOutput;
		if(saveOutput) {
			savedOutputs.add(newOutput);
		}
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
}
