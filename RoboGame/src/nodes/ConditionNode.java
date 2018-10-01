package nodes;

import java.io.IOException;

import main.Robot;

public class ConditionNode {

	String state;
	private Relop relop;
	private String prefix;
	private ConditionNode condition1;
	private ConditionNode condition2;
	private ExpressionNode expression1;
	private ExpressionNode expression2;

	// Relop
	public ConditionNode(String relop, ExpressionNode expression1, ExpressionNode expression2) {
		this.relop = stringToRelop(relop);
		this.expression1 = expression1;
		this.expression2 = expression2;
		state = "relop";
	}

	// And Or
	public ConditionNode(String prefix, ConditionNode condition1, ConditionNode condition2) {
		this.prefix = prefix;
		this.condition1 = condition1;
		this.condition2 = condition2;
		state = "andor";
	}

	// Not
	public ConditionNode(String prefix, ConditionNode condition) {
		this.prefix = prefix;
		this.condition1 = condition;
		state = "not";
	}

	public boolean evaluate(Robot robot) {
		try {
			switch (state) {
			case "relop":
				switch (relop) {
				case LT:
					return expression1.evaluate(robot) < expression2.evaluate(robot);
				case EQ:
					return expression1.evaluate(robot) == expression2.evaluate(robot);
				case GT:
					return expression1.evaluate(robot) > expression2.evaluate(robot);
				}
			case "andor":
				switch (prefix) {
				case "and":
					return condition1.evaluate(robot) && condition2.evaluate(robot);
				case "or":
					return condition1.evaluate(robot) || condition2.evaluate(robot);
				}
			case "not":
				switch (prefix) {
				case "not":
					return !condition1.evaluate(robot);
				}
			default:
				throw new IOException();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static enum Relop {
		LT, GT, EQ
	}

	private static Relop stringToRelop(String s) {
		try {
			switch (s) {
			case "lt":
				return Relop.LT;
			case "gt":
				return Relop.GT;
			case "eq":
				return Relop.EQ;
			default:
				throw new IOException();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String relopToString(Relop relop) {
		try {
			switch (relop) {
			case EQ:
				return "eq";
			case GT:
				return "gt";
			case LT:
				return "lt";
			default:
				throw new IOException();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String toString() {
		switch (state) {
		case "relop":
			return relopToString(relop) + "(" + expression1.toString() + ", " + expression2.toString() + ")";
		case "andor":
			switch (prefix) {
			case "and":
				return "and(" + condition1.toString() + ", " + condition2.toString() + ")";
			case "or":
				return "or(" + condition1.toString() + ", " + condition2.toString() + ")";
			}
		case "not":
			switch (prefix) {
			case "not":
				return "not(" + condition1.toString();
			}
		}
		return null;
	}

}
