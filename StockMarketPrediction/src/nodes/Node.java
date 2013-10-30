package nodes;

import java.util.ArrayList;
import java.util.List;

public class Node {
	protected List<Node> linkedNodes;
	protected int currentIteration;
	
	public Node() {
		linkedNodes = new ArrayList<Node>();
		currentIteration = -1;
	}
	
	protected void incCurrIter() {
		currentIteration++;
	}
	
	public void addLink(Node node) {
		if(node != this) {
			linkedNodes.add(node);
		}
	}
	
	public static void addBidirectionalLink(Node n1, Node n2) {
		n1.addLink(n2);
		n2.addLink(n1);
	}
	
	
}
