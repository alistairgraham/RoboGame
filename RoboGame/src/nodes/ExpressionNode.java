package nodes;

import java.io.IOException;

import main.Robot;

public class ExpressionNode {

	private ExpressionType expType;
	
	private Sensor sensor;
	private int specifier;
	private boolean hasSpecifier;
	private Double num;
	private OperationNode operation;

	public ExpressionNode(Double num) {
		this.num = num;
		this.expType = ExpressionType.NUM;
		this.hasSpecifier = false;
	}

	public ExpressionNode(String sensor) {
		this.sensor = stringToSensor(sensor);
		this.expType = ExpressionType.SEN;
		this.hasSpecifier = false;
	}
	
	public ExpressionNode(String sensor, int specifier) {
		this.sensor = stringToSensor(sensor);
		this.specifier = specifier;
		this.expType = ExpressionType.SEN;
		this.hasSpecifier = true;
	}

	public ExpressionNode(OperationNode operation) {
		this.operation = operation;
		this.expType = ExpressionType.OP;
		this.hasSpecifier = false;
	}

	public Double evaluate(Robot robot) {
		switch (expType) {
		case NUM:
			return (double) num;
		case SEN:
			return getSensorValue(robot);
		case OP:
			return operation.evaluate(robot);
		}
		return null;
	}

	private static enum ExpressionType {
		NUM, SEN, OP
	}

	public static enum Sensor {
		FUELLEFT, OPPLR, OPPFB, NUMBARRELS, BARRELLR, BARRELFB, WALLDIST
	}

	private double getSensorValue(Robot robot) {
		try {
			switch (this.sensor) {
			case BARRELFB:
				// not sure about parameter
				if (hasSpecifier) {
					return robot.getBarrelFB(specifier);
				}
				return robot.getClosestBarrelFB();
			case BARRELLR:
				if (hasSpecifier) {
					return robot.getBarrelLR(specifier);
				}
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
		switch (expType) {
		case NUM:
			return num.toString();
		case SEN:
			return sensorToString(sensor);
		case OP:
			return operation.toString();
		}
		return null;
	}

}
