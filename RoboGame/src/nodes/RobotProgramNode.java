package nodes;
import main.Robot;

/**
 * Interface for all nodes that can be executed, including the top level program
 * node
 */

public interface RobotProgramNode {
	public void execute(Robot robot);
	public String toString(int indents);
}
