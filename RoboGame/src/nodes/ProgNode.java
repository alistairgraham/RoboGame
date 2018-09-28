package nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Parser;
import main.Robot;

public class ProgNode implements RobotProgramNode {
	
	List<StmtNode> statements;

	public ProgNode(List<StmtNode> statements) {
		this.statements = statements;
	}
	
	public ProgNode() {
		this.statements = new ArrayList<StmtNode>();
	}
	
	@Override
	public void execute(Robot robot) {
		for (int i = 0; i < statements.size(); i++) {
			statements.get(i).execute(robot);
		}
	}
	
	public void addStatement(StmtNode stmt) {
		this.statements.add(stmt);
	}
	
	@Override
	public String toString() {
		String stmts = "";
		for (int i = 0; i < statements.size(); i++) {
			stmts += statements.get(i).toString(0);
		}
		return stmts;
	}
	
	// Program always has indent 0 so unnecessary
	public String toString(int indents) {
		return null;
	}
	

}
