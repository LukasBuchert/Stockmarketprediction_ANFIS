package anfis;

public class FiringStrengthNode extends Node{
	private double firingStrength;
	
	public FiringStrengthNode() {
		super();
	}
	
	public double computeFiringStregnth() {
		double result;
		result = ((MembershipFunctionNode)linkedNodes.get(0)).getMembershipValue();
		for(Node n : linkedNodes) {
			if(n instanceof MembershipFunctionNode) {
				result = firingStrengthFunction(result, ((MembershipFunctionNode)n).getMembershipValue());
			}
		}
		firingStrength = result;
		return firingStrength;
	}
	
	private double firingStrengthFunction(double value1, double value2) {
		return Math.min(value1, value2);
	}
	
	public double getFiringStrength() {
		return firingStrength;
	}
}
