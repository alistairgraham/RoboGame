package nodes;

import java.util.Scanner;
import main.Robot;

/**
 * Interface for all nodes that can be executed, including the top level program
 * node
 */

public interface RobotProgramNode {
	public RobotProgramNode parse(Scanner s);
	public void execute(Robot robot);
}
