package nodes;

public interface NodeVisitor {
	public void visit(FiringStrengthNode fsn);
	public void visit(MembershipFunctionNode mfn);
	public void visit(NormFSNode nfsn);
	public void visit(PolynomialNode pn);
}
