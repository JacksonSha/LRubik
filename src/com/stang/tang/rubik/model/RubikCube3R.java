package com.stang.tang.rubik.model;

import java.util.regex.Pattern;

/**
 * 三阶魔方
 * 
 * @author Jackson Sha
 * <br/>
 * 2012-12-19
 * 
 */
public class RubikCube3R extends RubikCube implements Recovery {
	public static final int N = 3;
	public static final Pattern STEP_ONE_PATTERN = Pattern
			.compile("[R|U|F|L|D|B|r|u|f|l|d|b|X|Y|Z]['|2]?");
	public static final Pattern STEP_ALL_PATTERN = Pattern.compile("^("
			+ STEP_ONE_PATTERN.toString() + ")+$");

	public RubikCube3R() {
		super(N);
	}

	public RubikCube3R(boolean isNone) {
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
			case 'X':
				this.turnX(turns);
				break;
			case 'Y':
				this.turnY(turns);
				break;
			case 'Z':
				this.turnZ(turns);
				break;
			}
		}
	}

	@Override
	public String[] recoverRubik() throws RubikException {
		return null;
	}

	public boolean checkCenter() {
		return false;
	}

	public String recoverCross() {
		return null;
	}

	public String recoverF2L() {
		return null;
	}

	public String recoverOLL() {
		return null;
	}

	public String recoverPLL() {
		return null;
	}

}
