package nodes;

import java.util.ArrayList;
import java.util.List;

public abstract class Node implements NodeClient {
	protected List<Node> predecessorNodes;
	protected List<Node> successorNodes;
	protected double output;
	protected double error;
	
	public Node() {
		predecessorNodes = new ArrayList<Node>();
		successorNodes = new ArrayList<Node>();
	}
	
	public void setOutput(double newOutput) {
		output = newOutput;
	}
	
	public double getOutput() {
		return output;
	}
	
	public void addSuccessorLink(Node node) {
		if(node != this && !this.isSuccessor(node)) {
			successorNodes.add(node);
			if(!node.isPredecessor(this)) {
				node.addPredecessorLink(this);
			}
		}
	}
	
	public void addPredecessorLink(Node node) {
		if(node != this && !this.isPredecessor(node)) {
			predecessorNodes.add(node);
			if(!node.isSuccessor(this)) {
				node.addSuccessorLink(this);
			}
		}
	}
	
	public boolean isSuccessor(Node node) {
		return successorNodes.contains(node);
	}
	
	public boolean isPredecessor(Node node) {
		return predecessorNodes.contains(node);
	}
}
