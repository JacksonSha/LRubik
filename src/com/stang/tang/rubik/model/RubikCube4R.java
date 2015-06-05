package com.stang.tang.rubik.model;

import java.util.regex.Pattern;

/**
 * 四阶魔方
 * 
 * @author Jackson Sha
 * <br/>
 * 2012-12-19
 * 
 */
public class RubikCube4R extends RubikCube {
	public static final int N = 4;
	public static final Pattern STEP_ONE_PATTERN = Pattern
			.compile("[R|U|F|L|D|B|r|u|f|l|d|b]['|2]?");
	public static final Pattern STEP_ALL_PATTERN = Pattern.compile("^("
			+ STEP_ONE_PATTERN.toString() + ")+$");

	public RubikCube4R() {
		super(4);
	}

	public RubikCube4R(boolean isNone) {
		super(N, isNone);
	}

	protected void execStep(String step) {
		if (Pattern.matches(STEP_ONE_PATTERN.pattern(), step)) {
			int turns = 1;
			if (step.length() == 2) {
				switch (step.charAt(1)) {
				case '\'':
					turns = -1;
					break;
				case '2':
					turns = 2;
					break;
				}
			}
			switch (step.charAt(0)) {
			case 'R':
				this.turnRight(turns);
				break;
			case 'U':
				this.turnUp(turns);
				break;
			case 'F':
				this.turnFront(turns);
				break;
			case 'L':
				this.turnLeft(turns);
				break;
			case 'D':
				this.turnDown(turns);
				break;
			case 'B':
				this.turnBack(turns);
				break;
			}
		}
	}

}
