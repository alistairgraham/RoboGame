package nodes;

import java.io.IOException;
import java.util.Scanner;
import java.util.stream.IntStream;

import main.Robot;

public class ActionNode implements RobotProgramNode {

	Terminal action;
	ExpressionNode repeat;

	public ActionNode(String s) {
		action = stringToEnum(s);
		repeat = new ExpressionNode(1D);
	}

	public ActionNode(String s, ExpressionNode repeat) {
		action = stringToEnum(s);
		this.repeat = repeat;
	}

	@Override
	public void execute(Robot robot) {
		try {
			switch (action) {
			case MOVE:
				IntStream.range(0, repeat.evaluate(robot).intValue()).forEach(i -> {
					robot.move();
				});
				break;
			case TAKEFUEL:
				robot.takeFuel();
				break;
			case TURNL:
				robot.turnLeft();
				break;
			case TURNR:
				robot.turnRight();
				break;
			case WAIT:
				IntStream.range(0, repeat.evaluate(robot).intValue()).forEach(i -> {
					robot.idleWait();
				});
				break;
			case SHIELDOFF:
				robot.setShield(false);
				break;
			case SHIELDON:
				robot.setShield(true);
				break;
			case TURNAROUND:
				robot.turnAround();
				break;
			default:
				throw new IOException();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static enum Terminal {
		MOVE, TURNL, TURNR, TURNAROUND, SHIELDON, SHIELDOFF, TAKEFUEL, WAIT
	}

	public static Terminal stringToEnum(String s) {
		try {
			switch (s) {
			case "move":
				return Terminal.MOVE;
			case "turnL":
				return Terminal.TURNL;
			case "turnR":
				return Terminal.TURNR;
			case "takeFuel":
				return Terminal.TAKEFUEL;
			case "turnAround":
				return Terminal.TURNAROUND;
			case "shieldOn":
				return Terminal.SHIELDON;
			case "shieldOff":
				return Terminal.SHIELDOFF;
			case "wait":
				return Terminal.WAIT;
			default:
				throw new IOException();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String enumToString(Terminal t) {
		try {
			switch (t) {
			case MOVE:
				return "move";
			case TAKEFUEL:
				return "takeFuel";
			case TURNL:
				return "turnLeft";
			case TURNR:
				return "turnRight";
			case TURNAROUND:
				return "turnAround";
			case SHIELDON:
				return "shieldOn";
			case SHIELDOFF:
				return "shieldOff";
			case WAIT:
				return "wait";
			default:
				throw new IOException();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String toString(int indents) {
		String indentation = "";
		for (int i = 0; i < indents; i++) {
			indentation += "\t";
		}
		return indentation + enumToString(action) + ";";
	}

}
