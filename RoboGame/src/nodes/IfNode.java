package nodes;

import main.Robot;

public class IfNode implements RobotProgramNode {

	ConditionNode conditionNode;
	BlockNode ifBlock;
	BlockNode elseBlock;

	public IfNode(ConditionNode conditionNode, BlockNode ifBlock) {
		this.conditionNode = conditionNode;
		this.ifBlock = ifBlock;
		this.elseBlock = null;
	}

	public IfNode(ConditionNode conditionNode, BlockNode ifBlock, BlockNode elseBlock) {
		this.conditionNode = conditionNode;
		this.ifBlock = ifBlock;
		this.elseBlock = elseBlock;
	}

	@Override
	public void execute(Robot robot) {
		boolean hasConditionPassed = conditionNode.evaluate(robot);

		if (hasConditionPassed) {
			ifBlock.execute(robot);
		} else {
			elseBlock.execute(robot);
		}
	}

	public String toString(int indents) {
		String indentation = "";
		for (int i = 0; i < indents; i++) {
			indentation += "\t";
		}
		if (elseBlock == null) {
			return indentation + "if (" + conditionNode.toString() + ") {\n" + ifBlock.toString(indents + 1) + indentation + "}";
		}
		else {
		return indentation + "if (" + conditionNode.toString() + ") {\n" + ifBlock.toString(indents + 1) + indentation + "} "
				+ "else " + "{\n" + elseBlock.toString(indents + 1) + indentation + "}";
		}
	}

}
