package nodes;

import java.io.IOException;

import main.Robot;

public class ConditionNode {

	private Relop relop;
	private Sensor sensor;
	private int num;

	public ConditionNode(String relop, String sensor, int num) {
		this.relop = stringToRelop(relop);
		this.sensor = stringToSensor(sensor);
		this.num = num;
	}

	public boolean evaluate(Robot robot) {
		try {
			switch (relop) {
			case EQ:
				return getSensorValue(robot) == num;
			case GT:
				return getSensorValue(robot) > num;
			case LT:
				return getSensorValue(robot) < num;
			default:
				throw new IOException();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public double getSensorValue(Robot robot) {
		try {
			switch (this.sensor) {
			case BARRELFB:
				// not sure about parameter
				return robot.getClosestBarrelFB();
			case BARRELLR:
				return robot.getClosestBarrelLR();
			case FUELLEFT:
				return robot.getFuel();
			case NUMBARRELS:
				return robot.numBarrels();
			case OPPFB:
				return robot.getOpponentFB();
			case OPPLR:
				return robot.getOpponentLR();
			case WALLDIST:
				return robot.getDistanceToWall();
			default:
				throw new IOException();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Integer.MAX_VALUE;
	}

	public static enum Relop {
		LT, GT, EQ
	}

	public static enum Sensor {
		FUELLEFT, OPPLR, OPPFB, NUMBARRELS, BARRELLR, BARRELFB, WALLDIST
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

	private static Sensor stringToSensor(String s) {
		try {
			switch (s) {
			case "fuelLeft":
				return Sensor.FUELLEFT;
			case "oppLR":
				return Sensor.OPPLR;
			case "oppFB":
				return Sensor.OPPFB;
			case "numBarrels":
				return Sensor.NUMBARRELS;
			case "barrelLR":
				return Sensor.BARRELLR;
			case "barrelFB":
				return Sensor.BARRELFB;
			case "wallDist":
				return Sensor.WALLDIST;
			default:
				throw new IOException();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String sensorToString(Sensor sensor) {
		try {
			switch (sensor) {
			case BARRELFB:
				return "barrelFB";
			case BARRELLR:
				return "barrelLR";
			case FUELLEFT:
				return "fuelLeft";
			case NUMBARRELS:
				return "numBarrels";
			case OPPFB:
				return "oppFB";
			case OPPLR:
				return "oppLR";
			case WALLDIST:
				return "wallDist";
			default:
				throw new IOException();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String toString() {
		return relopToString(relop) + "(" + sensorToString(sensor) + ", " + String.valueOf(num) + ")";
	}

}
