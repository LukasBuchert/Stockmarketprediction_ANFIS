package nodes;

// layer 3

public class NormFSNode extends Node implements NodeClient{
	public FiringStrengthNode primaryNode;
	
	public NormFSNode(FiringStrengthNode primaryNode) {
		super();
		this.primaryNode = primaryNode;

	public NormFSNode(Node node) {
		super();
		this.fsNode = (FiringStrengthNode) node;
		normalizedFiringStrength = new double[Settings.trainingDataSize];
	}
	
	public static double computeSumOfFiringStrengths(Layer layer) {
		double result = 0;
		for(Node n : layer.getNodes()) {
			if(n instanceof FiringStrengthNode) {
				result += ((FiringStrengthNode)n).getFiringStrength();
			}
		}
		fsSum = result;
		return fsSum;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
}
