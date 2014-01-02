package anfis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nodes.MembershipFunctionNode;
import nodes.Node;
import nodes.NodeVisitor;

public class Layer {
	private List<Node> nodes;

	public Layer() {
		nodes = new ArrayList<Node>();
	}

	public void addNode(Node node) {
		nodes.add(node);
	}

	public void addNode(Node[] node) {
		for (Node n : node) {
			this.addNode(n);
		}
	}

	public List<Node> getNodes() {
		return nodes;
	}
	
	public void sendVisitorToAllNodes(NodeVisitor visitor) {
		for(Node n : nodes) {
			n.accept(visitor);
		}
	}

	public MembershipFunctionNode getMFSNode(int varNumber, int setNumber) {

		Iterator<Node> iterator = nodes.iterator();
		while (iterator.hasNext()) {
			MembershipFunctionNode mfs = (MembershipFunctionNode) iterator
					.next();
			if (mfs.getMFSNode(varNumber, setNumber) != null) {
				return mfs;
			}

		}
		return null;

	}
}
