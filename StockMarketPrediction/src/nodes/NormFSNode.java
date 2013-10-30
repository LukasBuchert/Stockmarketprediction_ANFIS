package nodes;

import util.Settings;
import anfis.Layer;

public class NormFSNode extends Node {
	private FiringStrengthNode fsNode;
	private double[] normalizedFiringStrength;
	private static double fsSum;
	
	public NormFSNode(FiringStrengthNode fsNode) {
		super();
		this.fsNode = fsNode;
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
	
	public double computeNormalizedFiringStrength() {
		incCurrIter();
		
		normalizedFiringStrength[currentIteration] = fsNode.getFiringStrength() / fsSum;
		return normalizedFiringStrength[currentIteration];
	}
	
	public double getNormFS() {
		return normalizedFiringStrength[currentIteration];
	}
}
