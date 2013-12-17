package nodes;

import java.util.ArrayList;
import java.util.List;

import util.Settings;

public class Node {
<<<<<<< HEAD
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
=======
	
	protected List<Node> predecessor;
	protected List<Node> successor;
	protected int currentIteration;
	
	public Node() {
		predecessor = new ArrayList<Node>();
		successor = new ArrayList<Node>();
		currentIteration = -1;
>>>>>>> d8fa6ad39ed22c055ce92ff0a978efb10931c509
	}
	
	public double getOutput(int index) {
		return output[index];
	}
	
	public void addSuccessorLink(Node node) {
		if(node != this) {
<<<<<<< HEAD
			successorNodes.add(node);
			if(!node.isPredecessor(this)) {
				node.addPredecessorLink(this);
			}
=======
			this.successor.add(node);
			node.predecessor.add(this);
>>>>>>> d8fa6ad39ed22c055ce92ff0a978efb10931c509
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
