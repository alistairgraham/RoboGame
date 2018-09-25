package nodes;

import main.Robot;

public class LoopNode implements RobotProgramNode {
	
	BlockNode blockNode;
	

	public LoopNode(BlockNode blockNode) {
		this.blockNode = blockNode;
	}
	
	public LoopNode() {
		blockNode = null;
	}
	
	public void setBlockNode(BlockNode bN) {
		this.blockNode = bN;
	}

	@Override
	public void execute(Robot robot) {
		while (true) {
			blockNode.execute(robot);
		}
	}

}
