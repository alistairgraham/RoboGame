package nodes;

import main.Robot;

public class WhileNode implements RobotProgramNode {

	ConditionNode conditionNode;
	BlockNode blockNode;
	
	public WhileNode(ConditionNode conditionNode, BlockNode blockNode) {
		this.conditionNode = conditionNode;
		this.blockNode = blockNode;
	}

	@Override
	public void execute(Robot robot) {
		boolean hasConditionPassed = conditionNode.evaluate(robot);
		while (hasConditionPassed) {
			blockNode.execute(robot);
			hasConditionPassed = conditionNode.evaluate(robot);
		}
		
	}
	
	public String toString(int indents) {
		String indentation = "";
		for (int i=0; i<indents; i++) {
			indentation += "\t";
		}
		return indentation + "while (" + conditionNode.toString() + ") {\n" + blockNode.toString(indents+1) + indentation + "}";
	}

}
