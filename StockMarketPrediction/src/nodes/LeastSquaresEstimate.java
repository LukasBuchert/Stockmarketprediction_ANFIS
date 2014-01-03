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

		System.out.println("This takes very long on large data atm! I am working on it :) Maybe reduce the data or network size if it doesn't continue");
		
		Matrix lse = helperMatrix.times(helperMatrix.transpose()).inverse().times(helperMatrix).times(expectedOutputMatrix.transpose());
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
