package nodes;

import java.util.List;
import java.util.Scanner;

import main.Robot;

public class ProgNode implements RobotProgramNode {
	
	List<StmtNode> statements;

	public ProgNode(List<StmtNode> statements) {
		this.statements = statements;
	}
	
	public ProgNode() {
		this.statements = null;
	}
	
	@Override
	public ProgNode parse(Scanner s) {
		return null;		
	}
	
	@Override
	public void execute(Robot robot) {
		for (int i = 0; i < statements.size(); i++) {
			statements.get(i).execute(robot);
		}
	}
	
	public void setStatementList(List<StmtNode> stmts) {
		this.statements = stmts;
	}
	
	@Override
	public String toString() {
		String stmts = "";
		for (int i = 0; i < statements.size(); i++) {
			stmts += statements.get(i).toString();
		}
		return "PROG" + stmts;
	}

}
