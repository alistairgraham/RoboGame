package nodes;

import main.Robot;

public class StmtNode implements RobotProgramNode {

	private RobotProgramNode statementNode;

	public StmtNode() {
		this.statementNode = null;
	}
	
	public StmtNode(ActionNode actNode) {
		this.statementNode = actNode;
	}

	public StmtNode(LoopNode loopNode) {
		this.statementNode = loopNode;
	}
	
	public StmtNode(IfNode ifNode) {
		this.statementNode = ifNode;
	}
	
	public StmtNode(WhileNode whileNode) {
		this.statementNode = whileNode;
	}

	@Override
	public void execute(Robot robot) {
		statementNode.execute(robot);
	}

	public String toString(int indents) {
		return statementNode.toString(indents) + "\n";
	}

}
