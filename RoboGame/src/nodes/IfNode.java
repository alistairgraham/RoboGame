package nodes;

import main.Robot;

public class IfNode implements RobotProgramNode {

	ConditionNode conditionNode;
	BlockNode blockNode;
	
	public IfNode(ConditionNode conditionNode, BlockNode blockNode) {
		this.conditionNode = conditionNode;
		this.blockNode = blockNode;
	}

	@Override
	public void execute(Robot robot) {
		boolean hasConditionPassed = conditionNode.evaluate(robot);
		
		if (hasConditionPassed) {
			blockNode.execute(robot);
		}
	}
	
	public String toString(int indents) {
		String indentation = "";
		for (int i=0; i<indents; i++) {
			indentation += "\t";
		}
		return indentation + "if (" + conditionNode.toString() + ") {\n" + blockNode.toString(indents+1) + indentation + "}";
	}

}
