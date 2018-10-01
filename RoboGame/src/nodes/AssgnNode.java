package nodes;

import java.io.IOException;

import main.Robot;

public class AssgnNode implements RobotProgramNode {
	String name;
	ExpressionNode expNode;

	public AssgnNode(String name, ExpressionNode expNode) {
		this.name = name;
		this.expNode = expNode;
	}

	@Override
	public void execute(Robot robot) {
		robot.variableMap.put(name, expNode);
	}

	public ExpressionNode evaluate(Robot robot) {
		try {
			if (robot.variableMap.containsKey(name)) {
				return expNode;
			}
			throw new IOException();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String toString(int indents) {
		return name + " = " + expNode.toString();
	}

}
