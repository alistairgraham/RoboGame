package nodes;

import java.util.List;

import main.Robot;

public class ProgNode implements RobotProgramNode {
	
	List<StmtNode> statements;

	public ProgNode(List<StmtNode> statements) {
		this.statements = statements;
	}
	
	public ProgNode() {
		this.statements = null;
	}
	
	public void setStatementList(List<StmtNode> stmts) {
		this.statements = stmts;
	}
	
	@Override
	public void execute(Robot robot) {
		for (int i=0; i<statements.size(); i++) {
			statements.get(i).execute(robot);
		}
	}

}
