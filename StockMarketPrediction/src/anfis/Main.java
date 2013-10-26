package anfis;

public class Main {
	public static void main(String[] args) {
		double[] input = {Math.random(), Math.random(), 1.0D};
		ANFIS anfis = new ANFIS(input);
		Layer l1 = anfis.getLayer(1);
		Layer l2 = anfis.getLayer(2);
		Layer l3 = anfis.getLayer(3);
		Layer l4 = anfis.getLayer(4);
		
		for(int i = 0; i < 4; i++) {
			l1.addNode(new MembershipFunctionNode(Math.random(),Math.random(),Math.random()));
		}
		
		Node n1, n2;
		for(int i = 0; i < 2; i++) {
			for(int j = 2; j < 4; j++) {
				n1 = l1.getNodes().get(i);
				n2 = l1.getNodes().get(j);
				FiringStrengthNode f = new FiringStrengthNode();
				l2.addNode(f);
				Node.addBidirectionalLink(f, n1);
				Node.addBidirectionalLink(f, n2);
			}
		}
		
		FiringStrengthNode f;
		for(int i = 0; i < 4; i++) {
			f = (FiringStrengthNode)l2.getNodes().get(i);
			NormFSNode nfs = new NormFSNode(f);
			l3.addNode(nfs);
			Node.addBidirectionalLink(nfs, f);
		}
		
		NormFSNode nfs;
		for(int i = 0; i < 4; i++) {
			nfs = (NormFSNode)l3.getNodes().get(i);
			PolynomialNode pn = new PolynomialNode(anfis.getConsequentParameter(i));
			l4.addNode(pn);
			Node.addBidirectionalLink(pn, nfs);
		}
		
		// - - - - - - - - - - - -
		
		int i = 0;
		for(Node n : l1.getNodes()) {
			if(i < 2) {
				System.out.println("M1: " + ((MembershipFunctionNode)n).applyMembershipFunction(input[0]));
			} else {
				System.out.println("M2: " + ((MembershipFunctionNode)n).applyMembershipFunction(input[1]));
			}
			i++;
		}
		
		System.out.println();
		
		for(Node n : l2.getNodes()) {
			System.out.println("FS: " + ((FiringStrengthNode)n).computeFiringStregnth());
		}
		
		System.out.println();
		
		System.out.println("Norm: " + NormFSNode.computeSumOfFiringStrengths(l2));
		
		System.out.println();
		
		for(Node n : l3.getNodes()) {
			System.out.println("NFS: " + ((NormFSNode)n).computeNormalizedFiringStrength());
		}
		
		System.out.println();
		
		double output = 0;
		for(Node n : l4.getNodes()) {
			System.out.println("PN: " + ((PolynomialNode)n).applyPolinomial(input[0], input[1]));
			output += ((PolynomialNode)n).applyPolinomial(input[0], input[1]);
		}
		
		System.out.println("\nOutput: " + output);
		
		anfis.computeLeastSquareEstimate();
		i = 0;
		for(Node n : l4.getNodes()) {
			((PolynomialNode)n).updateConsequentParameter(anfis.getConsequentParameter(i));
			i++;
		}
		
		// new parameter test
		
		output = 0;
		for(Node n : l4.getNodes()) {
			System.out.println("PN: " + ((PolynomialNode)n).applyPolinomial(input[0], input[1]));
			output += ((PolynomialNode)n).applyPolinomial(input[0], input[1]);
		}
		
		System.out.println("\nOutput: " + output);
	}
}
