package anfis;

import java.util.Arrays;

import data.DataReader;

import nodes.FiringStrengthNode;
import nodes.MembershipFunctionNode;
import nodes.Node;
import nodes.NormFSNode;

import util.Settings;
import weka.core.matrix.Matrix;

public class ANFIS {
	private Layer[] layer;
	private double[][] data;
	private int inputSize;
	private double[] premiseParameter;
	private double[] consequentParameter;

	/**
	 * 
	 * @param data 	[i][0] - expected ouput
	 * 				[i][j>0] - input
	 * @param relativeTrainingSize relative size of training data to test data, interval [0;1]
	 */
	public ANFIS(double[][] data, double relativeTrainingSize) {
		
		// input
		this.data = data;
		inputSize = data[0].length - 1;
		Settings.trainingDataSize = (int)(data.length * relativeTrainingSize);
		Settings.numberOfShapes = 3;
		Settings.bellSlope = 2;
		
		// definition of layers
		layer = new Layer[4];
		for (int i = 0; i < 4; i++) {
			layer[i] = new Layer();
		}
		
		// save all msfNodes for generating the FiringStrengthNodes
		MembershipFunctionNode[][] msfNodes = new MembershipFunctionNode[Settings.numberOfShapes][inputSize];
		
		// adds new nodes to layer 1 -- for each input numberOfShapes-times MembershipfunctionNodes
		for (int i = 0; i < inputSize; i++){
			double[] statistics = DataReader.getStatistics(getDataColumn(i+1));
			MembershipFunctionNode[] inputNodes = getDefaultMemberships(statistics[0], statistics[1], Settings.numberOfShapes, Settings.bellSlope);
			for (int j = 0; j < inputNodes.length; j++){
				layer[0].addNode(inputNodes[j]);
				msfNodes[j][i] = inputNodes[j];
			}
		}
		
		// generating new nodes for layer 2
		
		int [] countingArray = new int [msfNodes[0].length];
		for (int x : countingArray){
			x = 0;
		}
		generateFiringStrengthNodes(layer[1], msfNodes, countingArray, msfNodes.length, 0);
		
		
		
		// whatever
		premiseParameter = new double[3 * 4]; // TODO size of array equal to 3
												// times number of membership
												// functions
		consequentParameter = new double[3 * 4]; // TODO size of array equal to
													// number of polynomial
													// nodes times (number of
													// input parameter + 1)
		for (int i = 0; i < consequentParameter.length; i++) {
			consequentParameter[i] = Math.random();
		}
	}

	public double[] getConsequentParameter(int id) {
		return Arrays.copyOfRange(consequentParameter, id * inputSize, id
				* inputSize + inputSize);
	}
	/**
	 * 
	 * @param column - number
	 * @return column of data
	 */
	private double[] getDataColumn (int column){
		double [] back = new double [data.length];
		
		for (int i = 0; i < back.length; i++){
			back [i] = data[i][column];
		}
		
		return back;
	}
	
	/**
	 * Generating layer 2 nodes by using all nodes from layer 1 and connecting them using backtracking algorithm
	 * @param layer -- layer 2 
	 * @param msfNodes all nodes of layer one 	[a1][a2][a3] example structure
	 * 											[b1][b2][b3]
	 * 											[c1][c2][c3]
	 * @param countingArray -- for backtrack [0][1][1] --> leads to [a1][b2][c2] - start [0][0][0] * msfNodes[0].length
	 * @param big -- for backtrack - start msfNodes.lenght
	 * @param pos -- for backtrack - start is 0
	 */
	private void generateFiringStrengthNodes(Layer layer, MembershipFunctionNode [][] msfNodes, int [] countingArray, int big, int pos){
		
		if (pos < countingArray.length){
			for (int p = 0; p < big; p++){
				countingArray[pos] = p;
				generateFiringStrengthNodes(layer,msfNodes,countingArray,big,pos+1);
			}
		} else{
			FiringStrengthNode f = new FiringStrengthNode();
			layer.addNode(f);
			for (int i = 0; i < msfNodes[0].length; i++){
				f.addLink(msfNodes[countingArray[i]][i]);
			}
		}
		
	}

/*	public void computeLeastSquareEstimate() {
		Layer l3 = this.getLayer(3);
		double[][] normFS = new double[l3.getNodes().size()][];
		int i = 0;
		for (Node n : l3.getNodes()) {
			normFS[i][] = ((NormFSNode) n).getNormFS();
			i++;
		}

		double[] lseHelperArray = new double[inputSize * normFS.length];
		i = 0;
		for (double nfs : normFS) {
			for (double in : input) {
				lseHelperArray[i] = nfs * in;
				i++;
			}
		}

		Matrix lseHelperMatrix = new Matrix(lseHelperArray,lseHelperArray.length);
		Matrix lse = lseHelperMatrix.transpose().times(lseHelperMatrix).inverse().times(lseHelperMatrix.transpose()).times(expectedOutput);
		consequentParameter = lse.getArray()[0];
	} */

	/**
	 * returns a set of function nodes from min to max divided by number of shapes
	 * @param min - min value
	 * @param max - max value
	 * @param shapes - number of shapes
	 * @param slope - slope of the bell function (default could be 2)
	 * @return returns a array of MembershipFunctionNode with length = shapes
	 */
	public static MembershipFunctionNode[] getDefaultMemberships(double min,
			double max, int shapes, double slope) {

		MembershipFunctionNode[] back = new MembershipFunctionNode[shapes];

		if (shapes == 1) {

			double a = (max - min) / 2;
			double b = slope * a;
			double c = min + a;
			
			back[0] = new MembershipFunctionNode(a,b,c);
			
		} else {
			
			double a = (max - min) / (2 * shapes - 2);
	        double b = 2 * a;
	        double c = min - 2 * a;

	        for (int i = 0; i < shapes; i++) {
	            c = c + 2*a;
	            back [i] = new MembershipFunctionNode(a,b,c);

	        }

		}

		return back;
	}

	public Layer getLayer(int id) {
		return layer[id - 1];
	}
}
