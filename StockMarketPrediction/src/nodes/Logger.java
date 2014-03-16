package nodes;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Logger implements NodeVisitor {
	private StringBuilder log = new StringBuilder();
	
	public Logger() {
		//initialize
	}
	
	public String getLog() {
		return log.toString();
	}
	
	public void write() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("log.txt", "UTF-8");
		writer.println(log.toString());
		writer.close();
	}
	
	public void append(String str) {
		log.append(str);
	}
	
	@Override
	public void visit(FiringStrengthNode fsn) {
		//do nothing
	}

	@Override
	public void visit(MembershipFunctionNode mfn) {
		log.append("Error: " + mfn.getErrorSumA(false) + " " + mfn.getErrorSumB(false) + " " + mfn.getErrorSumC(false) + "\n");
		log.append("Params: " + mfn.a + " " + mfn.b + " " + mfn.c + "\n\n");
	}

	@Override
	public void visit(NormFSNode nfsn) {
		//do nothing
	}

	@Override
	public void visit(PolynomialNode pn) {
		//do nothing
	}

	@Override
	public void visit(OutputNode on) {
		//do nothing
	}
}
