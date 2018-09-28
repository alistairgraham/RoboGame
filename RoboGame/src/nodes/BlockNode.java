package nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Robot;

public class BlockNode implements RobotProgramNode {

	List<StmtNode> statements;

	public BlockNode(List<StmtNode> statements) {
		this.statements = statements;
	}

	public BlockNode() {
		this.statements = new ArrayList<StmtNode>();
	}
	
	public void addStatement(StmtNode stmt) {
		this.statements.add(stmt);
	}

	@Override
	public void execute(Robot robot) {
		for (int i = 0; i < statements.size(); i++) {
			statements.get(i).execute(robot);
		}
	}

	@Override
	public String toString(int indents) {
		String stmts = "";
		for (int i = 0; i < statements.size(); i++) {
//			for (int j=0; j<indents; j++) {
//				stmts += "\t";
//			}
			stmts += statements.get(i).toString(indents);
		}
		return stmts;
	}

	
}
