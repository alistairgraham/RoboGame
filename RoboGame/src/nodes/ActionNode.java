package nodes;

import java.io.IOException;
import java.util.Scanner;

import main.Robot;

public class ActionNode implements RobotProgramNode {

	Terminal action;

	public ActionNode(String s) {
		action = stringToEnum(s);
	}

	@Override
	public ActionNode parse(Scanner s) {
		return null;
	}

	@Override
	public void execute(Robot robot) {
		switch (action) {
		case MOVE:
			robot.move();
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
			robot.idleWait();
			break;
		default:
			break;
		}
	}

	private static enum Terminal {
		MOVE, TURNL, TURNR, TAKEFUEL, WAIT
	}

	private Terminal stringToEnum(String s) {
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

	private String enumToString(Terminal t) {
		switch (t) {
		case MOVE:
			return "move";
		case TAKEFUEL:
			return "takeFuel";
		case TURNL:
			return "turnL";
		case TURNR:
			return "turnR";
		case WAIT:
			return "wait";
		default:
			return null;
		}
	}

	@Override
	public String toString() {
		return enumToString(action);
	}

}
