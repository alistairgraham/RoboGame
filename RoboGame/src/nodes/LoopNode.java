package nodes;

import java.util.Scanner;

import main.Robot;

public class LoopNode implements RobotProgramNode {

	BlockNode blockNode;

	public LoopNode(BlockNode blockNode) {
		this.blockNode = blockNode;
	}

	public LoopNode() {
		blockNode = null;
	}

	@Override
	public void execute(Robot robot) {
		while (true) {
			blockNode.execute(robot);
		}
	}
	
	public void setBlockNode(BlockNode bN) {
		this.blockNode = bN;
	}

	public String toString(int indents) {
		String indentation = "";
		for (int i=0; i<indents; i++) {
			indentation += "\t";
		}
		return indentation + "loop {\n" + blockNode.toString(indents+1) + "}\n";
	}

}
