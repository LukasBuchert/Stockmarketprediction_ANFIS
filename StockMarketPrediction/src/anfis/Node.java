package anfis;

import java.util.ArrayList;
import java.util.List;

public class Node {
	protected List<Node> linkedNodes;
	
	public Node() {
		linkedNodes = new ArrayList<Node>();
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
