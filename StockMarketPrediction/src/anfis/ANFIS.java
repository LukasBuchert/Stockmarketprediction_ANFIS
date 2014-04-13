package anfis;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import nodes.*;
import util.Settings;

public class ANFIS {

	// all layers of an anfis network;
	private Layer layer1, layer2, layer3, layer4, layer5;
	
	//parameter for learning rate
	private double k = 0.1D;
	
	//backpropagation stops when change is less than threshold
	private double threshold = Double.valueOf("1E-10");
	private double minIterations = 100;
	private double maxIterations = 1500;

	public ANFIS() {

	}
	
	// TODO Nice if Stephan would do this :)
	// TODO There should be a method for test purposes that has input parameters testData, expectedOutput and returns a double [][] array with
	// TODO 		calculated output and expected Output for each set of the training data --> this can be written in .csv and then evaluated.

	// generating new network -- overwrite old if there was one
	public void generateNetwork() {
		initializeLayers();
		generateLayer1();
		generateLayer2();
		generateLayer3();
		generateLayer4();
		generateLayer5();
	}
	
	public double[][] test(double[][] dataSet, double[] expectedOutput) {
		FeedforwardFunction fff = new FeedforwardFunction(false);
		BackpropagationFunction bpf = new BackpropagationFunction(true);
		
		double[][] result = new double[expectedOutput.length + 1][2]; // 1st line: error (twice); 2nd - last line: anfis output, expected output
		
		for(int i = 0; i < dataSet.length; i++) {
			fff.setInput(dataSet[i]);
			bpf.setInput(dataSet[i]);
			bpf.setExpectedOutput(expectedOutput[i]);
			
			layer1.sendVisitorToAllNodes(fff);
			layer2.sendVisitorToAllNodes(fff);
			layer3.sendVisitorToAllNodes(fff);
			layer4.sendVisitorToAllNodes(fff);
			layer5.sendVisitorToAllNodes(fff);
			
			System.out.println("Input: "+ dataSet[i][0] + " " + dataSet[i][1] + " Output: " + layer5.getNodes().get(0).getOutput());
			result[i+1][0] = layer5.getNodes().get(0).getOutput();
			result[i+1][1] = expectedOutput[i];
			
			layer5.sendVisitorToAllNodes(bpf);
		}
		
		result[0][0] = ((OutputNode) layer5.getNodes().get(0)).getSumOfError(true);
		result[0][1] = ((OutputNode) layer5.getNodes().get(0)).getSumOfError(true);
		
		return result;
	}
	
	public double trainConsequent (double[][] trainingSet, double[] expectedOutput){
		
		// TODO perhaps using layer instead of using nodes 
		
		FeedforwardFunction fff = new FeedforwardFunction(true);
		LeastSquaresEstimate lse = new LeastSquaresEstimate(trainingSet, expectedOutput, layer4.getNodes().size());
		
		//Train consequent parameters (Polynomial Node)
		for(double[] input : trainingSet) {
			fff.setInput(input);
			layer1.sendVisitorToAllNodes(fff);
			layer2.sendVisitorToAllNodes(fff);
			layer3.sendVisitorToAllNodes(fff);
			layer4.sendVisitorToAllNodes(fff);
		}
		
		layer3.sendVisitorToAllNodes(lse);
		layer4.sendVisitorToAllNodes(lse);
		
		// Consequent parameters trained!
		
		//TODO: always 0
		return ((OutputNode) layer5.getNodes().get(0)).getSumOfError(true);
	}
	
	public double trainPremise (double[][] trainingSet, double[] expectedOutput){
		Logger logger = new Logger();
		FeedforwardFunction fff = new FeedforwardFunction(false);
		BackpropagationFunction bpf = new BackpropagationFunction(true);
		LearningRateSum lrs = new LearningRateSum();
		GradientDecent gd = new GradientDecent();
		
		//variables for adjusting k (learning rate)
		double lastError = 0.0D, newError = 0.0D, sign, lastSign = 0.0D;
		int consecutiveSignCount = 0, alternatingSignCount = 0;
		
		int j = 0;
		// Train premise parameters (Membership Function Node)
		while(true) {
			j++;
			for(int i = 0; i < trainingSet.length; i++) {
				fff.setInput(trainingSet[i]);
				bpf.setInput(trainingSet[i]);
				bpf.setExpectedOutput(expectedOutput[i]);
				
				layer1.sendVisitorToAllNodes(fff);
				layer2.sendVisitorToAllNodes(fff);
				layer3.sendVisitorToAllNodes(fff);
				layer4.sendVisitorToAllNodes(fff);
				layer5.sendVisitorToAllNodes(fff);
				
				layer5.sendVisitorToAllNodes(bpf);
				layer4.sendVisitorToAllNodes(bpf);
				layer3.sendVisitorToAllNodes(bpf);
				layer2.sendVisitorToAllNodes(bpf);
				layer1.sendVisitorToAllNodes(bpf);
			}
			
			logger.append("Iteration " + j + "\n\n");
			layer1.sendVisitorToAllNodes(logger);
			
			
			newError = ((OutputNode) layer5.getNodes().get(0)).getSumOfError(false);
			
			if (j%1000 == 1){
			System.out.println("Iteration:" + j + "k: "+ k + "Change Rate: "  + Math.abs(newError - lastError));}
			
			//if change in error is less than threshold -> stop backpropagation learning
			if(j > minIterations && Math.abs(newError - lastError) < threshold || j > maxIterations) {
				break;
			}
						
			//adjusting k for learning rate
			sign = Math.signum(newError - lastError);
			if(lastSign == sign) {
				consecutiveSignCount++;
				alternatingSignCount = 0;
			} else if(lastSign != sign) {
				alternatingSignCount++;
				consecutiveSignCount = 0;
			}
			if(consecutiveSignCount == 3) { //increase k by 10% if 4 consecutive moves in same direction
				k = k * 1.1D;
				consecutiveSignCount = 0;
			} else if (alternatingSignCount == 3) { //decrease k by 10% if last 4 moves have alternating directions
				k = k * 0.9D;
				alternatingSignCount = 0;
			}
			lastSign = sign;
			lastError = newError;
			
			lrs.reset();
			layer1.sendVisitorToAllNodes(lrs);
			gd.setLearningRate(k);
			layer1.sendVisitorToAllNodes(gd);
			
			layer1.sendVisitorToAllNodes(logger);
			logger.append("Overall Error: " + newError + "\n\n");
			logger.append("\n");
			((OutputNode) layer5.getNodes().get(0)).getSumOfError(true);
		}
		//Premise parameters trained!
		
		System.out.println("Iterations: " + j);
		
		try {
			logger.write();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//TODO: this output has not been calculated with updated parameters
		return ((OutputNode) layer5.getNodes().get(0)).getSumOfError(true);
	}

	private void initializeLayers() {
		layer1 = new Layer();
		layer2 = new Layer();
		layer3 = new Layer();
		layer4 = new Layer();
		layer5 = new Layer();
	}

	private void generateLayer1() {

		for (int i = 1; i <= Settings.numberOfInputVaribles; i++) {

			layer1.addNode(getDefaultMemberships(i, Settings.getMin(i),
					Settings.getMax(i), Settings.numberOfShapes,
					Settings.bellSlope));
		}

	}

	private void generateLayer2() {

		int numberOfNodes = 
		(int) Math.pow(Settings.numberOfShapes, Settings.numberOfInputVaribles);
		int[] counter = new int[Settings.numberOfInputVaribles];
		initializeArray(counter);

		for (int i = 0; i < numberOfNodes; i++) {
			FiringStrengthNode fsn = new FiringStrengthNode();
			layer2.addNode(fsn);

			for (int j = 0; j < counter.length; j++) {
				fsn.addPredecessorLink(this.searchMembersphipFunctionNode(
						j + 1, counter[j] + 1));
			}

			countUpArray(counter, Settings.numberOfShapes);
		}
		
		

	}

	private void countUpArray(int[] input, int limit) {
		for (int i = input.length - 1; i >= 0; i--) {

			input[i]++;
			if (input[i] == limit) {
				input[i] = 0;
			} else {
				break;
			}
		}
	}

	private void initializeArray(int[] input) {
		for (int i = 0; i < input.length; i++) {
			input[i] = 0;
		}
	}

	private MembershipFunctionNode searchMembersphipFunctionNode(int varNumber,
			int setNumber) {
		return layer1.getMFSNode(varNumber, setNumber);
	}

	private void generateLayer3() {

		for (int i = 0; i < layer2.getNodes().size(); i++) {
			NormFSNode n = new NormFSNode((FiringStrengthNode) layer2
					.getNodes().get(i));
			layer3.addNode(n);

			for (int j = 0; j < layer2.getNodes().size(); j++) {
				layer2.getNodes().get(j).addSuccessorLink(n);
			}
		}
	}

	private void generateLayer4() {

		for (int i = 0; i < layer3.getNodes().size(); i++) {
			PolynomialNode pn = new PolynomialNode(Settings.numberOfInputVaribles + 1);
			layer4.addNode(pn);
			layer3.getNodes().get(i).addSuccessorLink(pn);
		}
	}

	private void generateLayer5() {

		Node lastNode = new OutputNode();
		layer5.addNode(lastNode);

		for (int i = 0; i < layer4.getNodes().size(); i++) {
			layer4.getNodes().get(i).addSuccessorLink(lastNode);
		}
	}

	/**
	 * returns a set of function nodes from min to max divided by number of
	 * shapes
	 * 
	 * @param min
	 *            - min value
	 * @param max
	 *            - max value
	 * @param shapes
	 *            - number of shapes
	 * @param slope
	 *            - slope of the bell function (default could be 2)
	 * @return returns a array of MembershipFunctionNode with length = shapes
	 */
	public static MembershipFunctionNode[] getDefaultMemberships(int varNumber,
			double min, double max, int shapes, double slope) {

		MembershipFunctionNode[] back = new MembershipFunctionNode[shapes];

		if (shapes == 1) {

			double a = (max - min) / 2;
			double b = slope * a;
			double c = min + a;

			back[0] = new MembershipFunctionNode(a, b, c, varNumber, 1);

		} else {

			double a = (max - min) / (2 * shapes - 2);
			double b = 2 * a;
			double c = min - 2 * a;

			for (int i = 0; i < shapes; i++) {
				c = c + 2 * a;
				back[i] = new MembershipFunctionNode(a, b, c, varNumber, i + 1);

			}

		}

		return back;
	}
	
//	public static MembershipFunctionNode[] getDefaultMemberships(int varNumber,
//			double min, double max, int shapes, double slope) {
//		MembershipFunctionNode[] back = new MembershipFunctionNode[shapes];
//		
//		double a = 0.0000001D;
//		double b = 200D;
//		back[0] = new MembershipFunctionNode(a,b,0D,varNumber,1);
//		back[1] = new MembershipFunctionNode(a,b,1D,varNumber,2);
//		
//		return back;
//	}

}