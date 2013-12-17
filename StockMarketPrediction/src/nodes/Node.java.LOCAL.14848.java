package nodes;

import java.util.ArrayList;
import java.util.List;

import util.Settings;

public class Node {
	protected List<Node> predecessorNodes;
	protected List<Node> successorNodes;
	protected double[] output;
	
	public Node() {
		predecessorNodes = new ArrayList<Node>();
		successorNodes = new ArrayList<Node>();
		output = new double[Settings.trainingDataSize];
	}
	
	protected void setOutput(double newOutput) {
		output[Settings.currentIteration] = newOutput;
	}
	
	public double getOutput() {
		return output[Settings.currentIteration];
	}
	
	public double getOutput(int index) {
		return output[index];
	}
	
	public void addSuccessorLink(Node node) {
		if(node != this) {
			successorNodes.add(node);
			if(!node.isPredecessor(this)) {
				node.addPredecessorLink(this);
			}
		}
	}
	
	public void addPredecessorLink(Node node) {
		if(node != this) {
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