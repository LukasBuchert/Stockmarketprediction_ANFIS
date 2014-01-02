package nodes;

public class OutputNode extends Node {
	private double sumOfError;

	public OutputNode() {
		super();
		sumOfError = 0.0D;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

	public double getSumOfError(boolean clearSumOfError) {
		double ret = sumOfError;
		if(clearSumOfError) {
			sumOfError = 0.0D;
		}
		return ret;
	}

	public void updateSumOfError(double sumOfError) {
		this.sumOfError += sumOfError;
	}
}
