package nodes;

import java.util.Scanner;

import main.Robot;

public class StmtNode implements RobotProgramNode {

	ActionNode actNode;
	LoopNode loopNode;
	boolean actNotLoop;

	public StmtNode(LoopNode loopNode) {
		this.loopNode = loopNode;
		this.actNode = null;
		actNotLoop = false;
	}

	public StmtNode(ActionNode actNode) {
		this.actNode = actNode;
		this.loopNode = null;
		actNotLoop = true;
	}
	
	@Override
	public StmtNode parse(Scanner s) {
		return null;		
	}

	@Override
	public void execute(Robot robot) {
		if (actNotLoop) {
			actNode.execute(robot);
		} else {
			loopNode.execute(robot);
		}
	}
	
	@Override
	public String toString() {
		String stmt = "";
		if (actNotLoop) {
			stmt += actNode.toString();
		} else {
			stmt += loopNode.toString();
		}
		return "STMT" + stmt;
	}

}
