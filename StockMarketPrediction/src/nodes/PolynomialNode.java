package nodes;

// layer 4

public class PolynomialNode extends Node {
	public double[] consequentParameter;
	public double polyResult;
	
	public PolynomialNode(double[] consequentParameter) {
		super();
		this.consequentParameter = consequentParameter;
	}
	
	public PolynomialNode(int length) {
		super();
		this.generateRandomConsequentParameter(length);
	}
	
	public void updateConsequentParameter(double[] newConsequentParameter) {
		consequentParameter = newConsequentParameter;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
	
	//temp
	private static int switcher = 1;
	
	public void generateRandomConsequentParameter (int length){
		consequentParameter = new double [length];
		for (int i = 0; i < consequentParameter.length; i++){
			
			//temporary
			int back = 0;
			if(i == consequentParameter.length - 1) {
				if(switcher > 1) {
					back = 1;
				}
				switcher = (switcher +  1) % 4;
			}
			
			//int back = getRandom();
			consequentParameter[i] = (double) back;
		}
	}
	
	public int getRandom(){
		int back = (int) (Math.random() * 10);
		back -= 5;
		
		if (back == 0){
			back = getRandom();
		}
		return back;
	}
}
