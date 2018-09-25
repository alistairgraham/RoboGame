package nodes;

import java.io.IOException;

import main.Robot;

public class ActionNode implements RobotProgramNode {

	public ActionNode(String s) {
	}

	@Override
	public void execute(Robot robot) {
		// TODO Auto-generated method stub

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

}
