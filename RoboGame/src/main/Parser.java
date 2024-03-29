package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.*;
import javax.swing.JFileChooser;

import nodes.*;

/**
 * The parser and interpreter. The top level parse function, a main method for
 * testing, and several utility methods are provided. You need to implement
 * parseProgram and all the rest of the parser.
 */
public class Parser {

	/**
	 * Top level parse method, called by the World
	 */
	static RobotProgramNode parseFile(File code) {
		Scanner scan = null;
		try {
			scan = new Scanner(code);

			// the only time tokens can be next to each other is
			// when one of them is one of (){},;
			scan.useDelimiter("\\s+|(?=[{}(),;])|(?<=[{}(),;])");

			RobotProgramNode n = parseProgram(scan); // You need to implement this!!!

			scan.close();
			return n;
		} catch (FileNotFoundException e) {
			System.out.println("Robot program source file not found");
		} catch (ParserFailureException e) {
			System.out.println("Parser error:");
			System.out.println(e.getMessage());
			scan.close();
		}
		return null;
	}

	/** For testing the parser without requiring the world */

	public static void main(String[] args) {
		if (args.length > 0) {
			for (String arg : args) {
				File f = new File(arg);
				if (f.exists()) {
					System.out.println("Parsing '" + f + "'");
					RobotProgramNode prog = parseFile(f);
					System.out.println("Parsing completed ");
					if (prog != null) {
						System.out.println("================\nProgram:");
						System.out.println(prog);
					}
					System.out.println("=================");
				} else {
					System.out.println("Can't find file '" + f + "'");
				}
			}
		} else {
			while (true) {
				JFileChooser chooser = new JFileChooser("./src/data/");// System.getProperty("user.dir"));
				int res = chooser.showOpenDialog(null);
				if (res != JFileChooser.APPROVE_OPTION) {
					break;
				}
				RobotProgramNode prog = parseFile(chooser.getSelectedFile());
				System.out.println("Parsing completed");
				if (prog != null) {
					System.out.println("Program: \n" + prog);
				}
				System.out.println("=================");
			}
		}
		System.out.println("Done");
	}

	// Useful Patterns

	public static Pattern NUMPAT = Pattern.compile("-?\\d+"); // ("-?(0|[1-9][0-9]*)");
	public static Pattern OPENPAREN = Pattern.compile("\\(");
	public static Pattern CLOSEPAREN = Pattern.compile("\\)");
	public static Pattern OPENBRACE = Pattern.compile("\\{");
	public static Pattern CLOSEBRACE = Pattern.compile("\\}");
	public static Pattern SEMICOLON = Pattern.compile("\\;");
	public static Pattern COMMA = Pattern.compile(",");
	public static Pattern STMT = Pattern.compile(
			"move|turnL|turnR|takeFuel|turnAround|shieldOn|shieldOff|wait|loop|if|while|[?][a-zA-Z_]+[0-9a-zA-Z_]*");
	public static Pattern ACTION = Pattern.compile("move|turnL|turnR|takeFuel|turnAround|shieldOn|shieldOff|wait");
	public static Pattern CONDITION = Pattern.compile("and|or|not|lt|gt|eq");
	public static Pattern ANDOR = Pattern.compile("and|or");
	public static Pattern NOT = Pattern.compile("not");
	public static Pattern RELOP = Pattern.compile("lt|gt|eq");
	public static Pattern SENSOR = Pattern.compile("fuelLeft|oppLR|oppFB|numBarrels|barrelLR|barrelFB|wallDist");
	public static Pattern NUM = Pattern.compile("-?[0-9]+");
	public static Pattern EXPRESSION = Pattern
			.compile("-?[0-9]+|fuelLeft|oppLR|oppFB|numBarrels|barrelLR|barrelFB|wallDist|add|sub|mul|div");
	public static Pattern OPERATION = Pattern.compile("add|sub|mul|div");
	public static Pattern LOOP = Pattern.compile("loop");
	public static Pattern IF = Pattern.compile("if");
	public static Pattern ELSE = Pattern.compile("else");
	public static Pattern WHILE = Pattern.compile("while");
	public static Pattern MOVE = Pattern.compile("move");
	public static Pattern TURNL = Pattern.compile("turnL");
	public static Pattern TURNR = Pattern.compile("turnR");
	public static Pattern TAKEFUEL = Pattern.compile("takeFuel");
	public static Pattern WAIT = Pattern.compile("wait");
	public static Pattern ASSGN = Pattern.compile("[?][a-zA-Z_]+[0-9a-zA-Z_]*");
	public static Pattern EQUALS = Pattern.compile("=");

	/**
	 * PROG ::= STMT+
	 */
	static RobotProgramNode parseProgram(Scanner s) {
		if (!s.hasNext()) {
			Parser.fail("Empty program", s);
		}

		ProgNode program = new ProgNode();

		while (s.hasNext()) {
			program.addStatement(parseStmt(s));
		}

		return program;
	}

	static StmtNode parseStmt(Scanner s) {
		StmtNode stmt = null;

		if (s.hasNext(ACTION)) {
			stmt = new StmtNode(parseActionNode(s));
		} else if (s.hasNext(LOOP)) {
			stmt = new StmtNode(parseLoopNode(s));
		} else if (s.hasNext(IF)) {
			stmt = new StmtNode(parseIfNode(s));
		} else if (s.hasNext(WHILE)) {
			stmt = new StmtNode(parseWhileNode(s));
		} else if (s.hasNext(ASSGN)) {
			stmt = new StmtNode(parseAssgnNode(s));
		} else {
			fail("Not a statement", s);
		}

		return stmt;
	}
	
	static AssgnNode parseAssgnNode(Scanner s) {
		String name = require(ASSGN, "Requires a valid variable name", s);
		require(EQUALS, "Requires an equal sign", s);
		ExpressionNode expNode = parseExpressionNode(s);
		require(SEMICOLON, "Requires semicolon", s);
		return new AssgnNode(name, expNode);
	}

	static ActionNode parseActionNode(Scanner s) {
		ActionNode actionNode = null;

		String actionType = require(ACTION, "Not an action", s);

		switch (actionType) {
		case "move":
			if (s.hasNext(OPENPAREN)) {
				require(OPENPAREN, "Requires open parentheses", s);
				ExpressionNode exp = parseExpressionNode(s);
				actionNode = new ActionNode("move", exp);
				require(CLOSEPAREN, "Requires close parentheses", s);
			} else {
				actionNode = new ActionNode("move");
			}
			break;
		case "turnL":
			actionNode = new ActionNode("turnL");
			break;
		case "turnR":
			actionNode = new ActionNode("turnR");
			break;
		case "takeFuel":
			actionNode = new ActionNode("takeFuel");
			break;
		case "wait":
			if (s.hasNext(OPENPAREN)) {
				require(OPENPAREN, "Requires open parentheses", s);
				ExpressionNode exp = parseExpressionNode(s);
				actionNode = new ActionNode("wait", exp);
				require(CLOSEPAREN, "Requires close parentheses", s);
			} else {
				actionNode = new ActionNode("wait");
			}
			break;
		case "turnAround":
			actionNode = new ActionNode("turnAround");
			break;
		case "shieldOn":
			actionNode = new ActionNode("shieldOn");
			break;
		case "shieldOff":
			actionNode = new ActionNode("shieldOff");
			break;
		default:
			fail("Not an action", s);
		}

		require(SEMICOLON, "Not a semicolon", s);

		return actionNode;
	}

	static LoopNode parseLoopNode(Scanner s) {
		LoopNode loopNode = null;

		require(LOOP, "Requires a loop", s);
		loopNode = new LoopNode(parseBlockNode(s));

		return loopNode;
	}

	static IfNode parseIfNode(Scanner s) {
		IfNode ifNode = null;

		require(IF, "Requires an if statement", s);
		require(OPENPAREN, "Requires open parantheses", s);
		ConditionNode cond = null;
		cond = parseConditionNode(s);
		require(CLOSEPAREN, "Requires close parantheses", s);

		BlockNode ifBlock = parseBlockNode(s);

		if (s.hasNext("else")) {
			require(ELSE, "Requires an else", s);
			BlockNode elseBlock = parseBlockNode(s);
			ifNode = new IfNode(cond, ifBlock, elseBlock);
		} else {
			ifNode = new IfNode(cond, ifBlock);
		}
		return ifNode;
	}

	static WhileNode parseWhileNode(Scanner s) {
		WhileNode whileNode = null;
		require(WHILE, "Requires a while statement", s);
		require(OPENPAREN, "Requires open parantheses", s);

		ConditionNode cond = null;
		cond = parseConditionNode(s);

		require(CLOSEPAREN, "Requires close parantheses", s);

		whileNode = new WhileNode(cond, parseBlockNode(s));
		return whileNode;
	}

	static ConditionNode parseConditionNode(Scanner s) {
		ConditionNode condNode = null;

		if (s.hasNext(ANDOR)) {
			String conjunctor = require(ANDOR, "Requires a condition", s);

			require(OPENPAREN, "Requires open parantheses", s);
			ConditionNode firstCond = parseConditionNode(s);
			require(COMMA, "Requires comma", s);
			ConditionNode secondCond = parseConditionNode(s);
			require(CLOSEPAREN, "Requires close parantheses", s);

			condNode = new ConditionNode(conjunctor, firstCond, secondCond);
		} else if (s.hasNext(NOT)) {
			String conjunctor = require(NOT, "Requires a condition", s);

			require(OPENPAREN, "Requires open parantheses", s);
			ConditionNode condition = parseConditionNode(s);
			require(CLOSEPAREN, "Requires close parantheses", s);

			condNode = new ConditionNode(conjunctor, condition);
		} else if (s.hasNext(RELOP)) {

			String relop = require(RELOP, "Requires relop", s);
			require(OPENPAREN, "Requires open parantheses", s);
			ExpressionNode exp1 = parseExpressionNode(s);
			require(COMMA, "Requires comma", s);
			ExpressionNode exp2 = parseExpressionNode(s);
			require(CLOSEPAREN, "Requires close parantheses", s);

			condNode = new ConditionNode(relop, exp1, exp2);
		} else {
			fail("Not a condition", s);
		}

		return condNode;
	}

	static ExpressionNode parseExpressionNode(Scanner s) {
		if (s.hasNext(NUM)) {
			return new ExpressionNode(Double.valueOf(require(NUM, "Requires a num", s)));
		} else if (s.hasNext(SENSOR)) {
			return new ExpressionNode(require(SENSOR, "Requires a sensor", s));
		} else if (s.hasNext(OPERATION)) {
			return new ExpressionNode(parseOperationNode(s));
		} else {
			fail("Not an expression", s);
			return null;
		}
	}

	static OperationNode parseOperationNode(Scanner s) {
		String operation = require(OPERATION, "Requires an operation", s);

		require(OPENPAREN, "Requires open parantheses", s);
		ExpressionNode expNode1 = parseExpressionNode(s);
		require(COMMA, "Requires comma", s);
		ExpressionNode expNode2 = parseExpressionNode(s);
		require(CLOSEPAREN, "Requires close parantheses", s);

		return new OperationNode(operation, expNode1, expNode2);
	}

	static BlockNode parseBlockNode(Scanner s) {
		BlockNode blockNode = new BlockNode();

		require(OPENBRACE, "Requires an open brace for loop", s);
		if (!s.hasNext(STMT)) {
			fail("Not a statement", s);
		}
		while (!s.hasNext(CLOSEBRACE)) {
			blockNode.addStatement(parseStmt(s));
		}
		require(CLOSEBRACE, "Requires an close brace for loop", s);

		return blockNode;
	}

	// utility methods for the parser

	/**
	 * Report a failure in the parser.
	 */
	public static void fail(String message, Scanner s) {
		String msg = message + "\n   @ ...";
		for (int i = 0; i < 5 && s.hasNext(); i++) {
			msg += " " + s.next();
		}
		throw new ParserFailureException(msg + "...");
	}

	/**
	 * Requires that the next token matches a pattern if it matches, it consumes and
	 * returns the token, if not, it throws an exception with an error message
	 */
	static String require(String p, String message, Scanner s) {
		if (s.hasNext(p)) {
			return s.next();
		}
		fail(message, s);
		return null;
	}

	static String require(Pattern p, String message, Scanner s) {
		if (s.hasNext(p)) {
			return s.next();
		}
		fail(message, s);
		return null;
	}

	/**
	 * Requires that the next token matches a pattern (which should only match a
	 * number) if it matches, it consumes and returns the token as an integer if
	 * not, it throws an exception with an error message
	 */
	static int requireInt(String p, String message, Scanner s) {
		if (s.hasNext(p) && s.hasNextInt()) {
			return s.nextInt();
		}
		fail(message, s);
		return -1;
	}

	static int requireInt(Pattern p, String message, Scanner s) {
		if (s.hasNext(p) && s.hasNextInt()) {
			return s.nextInt();
		}
		fail(message, s);
		return -1;
	}

	/**
	 * Checks whether the next token in the scanner matches the specified pattern,
	 * if so, consumes the token and return true. Otherwise returns false without
	 * consuming anything.
	 */
	static boolean checkFor(String p, Scanner s) {
		if (s.hasNext(p)) {
			s.next();
			return true;
		} else {
			return false;
		}
	}

	public static boolean checkFor(Pattern p, Scanner s) {
		if (s.hasNext(p)) {
			s.next();
			return true;
		} else {
			return false;
		}
	}

}

// You could add the node classes here, as long as they are not declared public
// (or private)
