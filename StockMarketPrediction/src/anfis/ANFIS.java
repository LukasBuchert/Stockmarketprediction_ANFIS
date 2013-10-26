package anfis;

import java.util.Arrays;

import weka.core.matrix.Matrix;

public class ANFIS {
	private Layer[] layer;
	private double[] input;
	private double expectedOutput;
	private double[] premiseParameter;
	private double[] consequentParameter;
	
	public ANFIS(double[] input) {
		this.input = input;
		expectedOutput = 2.0D;
		layer = new Layer[4];
		for(int i = 0; i < 4; i++) {
			layer[i] = new Layer();
		}
		premiseParameter = new double[3 * 4]; //TODO size of array equal to 3 times number of membership functions
		consequentParameter = new double[3 * 4]; //TODO size of array equal to number of polynomial nodes times (number of input parameter + 1)
		for(int i  = 0; i < consequentParameter.length; i++) {
			consequentParameter[i] = Math.random();
		}
	}
	
	public double[] getConsequentParameter(int id) {
		return Arrays.copyOfRange(consequentParameter, id * input.length, id * input.length + input.length);
	}
	
	public void computeLeastSquareEstimate() {
		Layer l3 = this.getLayer(3);
		double[] normFS = new double[l3.getNodes().size()];
		int i = 0;
		for(Node n : l3.getNodes()) {
			normFS[i] = ((NormFSNode)n).getNormFS();
			i++;
		}
		
		double[] lseHelperArray = new double[input.length * normFS.length];
		i = 0;
		for(double nfs : normFS) {
			for(double in : input) {
				lseHelperArray[i] = nfs * in;
				i++;
			}
		}
		
		Matrix lseHelperMatrix = new Matrix(lseHelperArray, lseHelperArray.length);
		Matrix lse = lseHelperMatrix.transpose().times(lseHelperMatrix).inverse().times(lseHelperMatrix.transpose()).times(expectedOutput);
		
		consequentParameter = lse.getArray()[0];
	}
	
	public Layer getLayer(int id) {
		return layer[id-1];
	}
}
