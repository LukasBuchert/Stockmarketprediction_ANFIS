package nodes;

import weka.core.matrix.Matrix;

public class LeastSquaresEstimate implements NodeVisitor{
	private double[][] input; //needs to contain the 1 at the end
	private double[] expectedOutput;
	private double[][] combinedOutputs;
	private double[] consequentParameters;
	private boolean isCalculated;
	private int index;
	
	public LeastSquaresEstimate(double[][] input, double[] expectedOutput, int numberOfNormFSNodes) {
		this.input = input;
		this.expectedOutput = expectedOutput;
		combinedOutputs = new double[numberOfNormFSNodes * input.length][];
		isCalculated = false;
		index = 0;
	}

	@Override
	public void visit(FiringStrengthNode fsn) {
		// do nothing
	}

	@Override
	public void visit(MembershipFunctionNode mfn) {
		// do nothing
	}

	@Override
	public void visit(NormFSNode nfsn) {
		for(int i = 0; i < input.length; i++) {
			combinedOutputs[index] = nfsn.getSavedOutputs(true);
			index++;
		}
	}
	
	private void calculateLeastSquareEstimate() {
		double[][] helperArray = new double[combinedOutputs.length][];
		
		for(int i = 0; i < combinedOutputs.length; i++) {
			for(int j = 0; j < combinedOutputs[i].length; j++) {
				helperArray[i][j] = combinedOutputs[i][j] * input[j][i%input.length];
			}
		}
		
		Matrix helperMatrix = new Matrix(helperArray);
		Matrix expectedOutputMatrix = new Matrix(expectedOutput, 1);
		Matrix lse = helperMatrix.transpose().times(helperMatrix).inverse().times(helperMatrix.transpose()).times(expectedOutputMatrix);
		consequentParameters = lse.getArray()[0];
		
		isCalculated = true;
		index = 0;
	}

	@Override
	public void visit(PolynomialNode pn) {
		if(!isCalculated) {
			calculateLeastSquareEstimate();
		}
		
		for(int i = index; i < index + input[0].length; i++) {
			pn.consequentParameter[i] = consequentParameters[i];
		}
		
		index += input[0].length;
		
		if(index > consequentParameters.length) {
			isCalculated = false;
		}
	}

	@Override
	public void visit(OutputNode on) {
		// do nothing
	}

}
