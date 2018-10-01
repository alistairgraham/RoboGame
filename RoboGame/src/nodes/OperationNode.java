package nodes;

import java.io.IOException;

import main.Robot;

public class OperationNode {

	String operation;
	private ExpressionNode expression1;
	private ExpressionNode expression2;

	public OperationNode(String operation, ExpressionNode expression1, ExpressionNode expression2) {
		this.operation = operation;
		this.expression1 = expression1;
		this.expression2 = expression2;
	}

	public Double evaluate(Robot robot) {
		try {
			switch (operation) {
			case "add":
				return expression1.evaluate(robot) + expression2.evaluate(robot);
			case "sub":
				return expression1.evaluate(robot) - expression2.evaluate(robot);
			case "mul":
				return expression1.evaluate(robot) * expression2.evaluate(robot);
			case "div":
				return expression1.evaluate(robot) / expression2.evaluate(robot);
			default:
				throw new IOException();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String toString() {
		return operation + "(" + expression1.toString() + ", " + expression2.toString() + ")";
	}

}
