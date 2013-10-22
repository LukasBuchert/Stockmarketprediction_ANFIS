package anfis;

import java.util.ArrayList;
import java.util.List;

public class Layer {
	private List<Node> nodes;
	
	public Layer() {
		nodes = new ArrayList<Node>();
	}
	
	public void addNode(Node node) {
		nodes.add(node);
	}
	
	public List<Node> getNodes() {
		return nodes;
	}
}
