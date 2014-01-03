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
		combinedOutputs = new double[numberOfNormFSNodes * (input[0].length + 1)][];
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
		for(int i = 0; i < input[0].length + 1; i++) {
			combinedOutputs[index] = nfsn.getSavedOutputs(true);
			index++;
		}
	}
	
	private void calculateLeastSquareEstimate() {
		double[][] helperArray = new double[combinedOutputs.length][combinedOutputs[0].length];
		
		for(int i = 0; i < combinedOutputs.length; i++) {
			for(int j = 0; j < combinedOutputs[i].length; j++) {
				if(i%(input[0].length + 1) == input[0].length) {
					helperArray[i][j] = combinedOutputs[i][j];
				} else {
					helperArray[i][j] = combinedOutputs[i][j] * input[j][i%(input[0].length + 1)];
				}
			}
		}
		
		Matrix helperMatrix = new Matrix(helperArray);
		Matrix expectedOutputMatrix = new Matrix(expectedOutput, 1);

		System.out.println("If matrix is singular, the LSE can't be calculated with matrices");
		System.out.println("There is another way of doing it though... not yet implemented");
		
		Matrix lse = helperMatrix.transpose().times(helperMatrix).inverse().times(helperMatrix.transpose()).transpose().times(expectedOutputMatrix.transpose());	
		
		consequentParameters = lse.transpose().getArray()[0];
		
		isCalculated = true;
		index = 0;
	}

	@Override
	public void visit(PolynomialNode pn) {
		if(!isCalculated) {
			calculateLeastSquareEstimate();
		}
		
		for(int i = index; i < index + input[0].length + 1; i++) {
			pn.consequentParameter[i % (input[0].length + 1)] = consequentParameters[i];
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
