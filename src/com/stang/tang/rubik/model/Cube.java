package com.stang.tang.rubik.model;

/**
 * 魔方的一个小块
 * 
 * @author Jackson Sha
 * <br/>
 * 2012-12-19
 * 
 */
public class Cube {
	public static final int UP = 0;
	public static final int FONT = 1;
	public static final int LEFT = 2;
	public static final int BACK = 3;
	public static final int RIGHT = 4;
	public static final int DOWN = 5;
	private static final int[] F = new int[] { UP, LEFT, DOWN, RIGHT };
	private static final int[] R = new int[] { UP, FONT, DOWN, BACK };
	private static final int[] U = new int[] { FONT, RIGHT, BACK, LEFT };
	private static final int[] A = new int[] { UP, FONT, LEFT, BACK, RIGHT, DOWN };
	private Faceplate[] All = new Faceplate[A.length];

	/**
	 * 六个面都是{@code Faceplate.NONE}
	 */
	public Cube() {
		for (int i = 0; i < A.length; i++) {
			All[A[i]] = Faceplate.NONE;
		}
	}

	/**
	 * {@code face}面的值置为{@code plate}，其余都是{@code Faceplate.NONE}
	 * 
	 * @param plate
	 * @param face
	 */
	public Cube(Faceplate plate, int face) {
		if (face >= UP && face <= DOWN) {
			for (int i = 0; i < A.length; i++) {
				All[A[i]] = Faceplate.NONE;
			}
			All[face] = plate;
		}
	}

	/**
	 * {@code face[]}与{@code plate[]}相互对应，未赋值的面都为{@code Faceplate.NONE}
	 * 
	 * @param plate
	 * @param face
	 */
	public Cube(Faceplate[] plate, int[] face) {
		if (plate == null || face == null || plate.length != face.length
				|| plate.length > A.length) {
			return;
		}
		for (int i = 0; i < A.length; i++) {
			All[A[i]] = Faceplate.NONE;
		}
		for (int i = 0; i < face.length; i++) {
			All[face[i]] = plate[i];
		}
	}

	/**
	 * 顺序是 上，下，左，右，前，后
	 * 
	 * @param up
	 * @param down
	 * @param left
	 * @param right
	 * @param font
	 * @param back
	 */
	public Cube(Faceplate up, Faceplate down, Faceplate left, Faceplate right,
			Faceplate font, Faceplate back) {
		All[UP] = up;
		All[FONT] = font;
		All[LEFT] = left;
		All[BACK] = back;
		All[RIGHT] = right;
		All[DOWN] = down;
	}

	public Faceplate getUp() {
		return All[UP];
	}

	public Faceplate getDown() {
		return All[DOWN];
	}

	public Faceplate getLeft() {
		return All[LEFT];
	}

	public Faceplate getRight() {
		return All[RIGHT];
	}

	public Faceplate getFont() {
		return All[FONT];
	}

	public Faceplate getBack() {
		return All[BACK];
	}

	/**
	 * 如果这一面已被赋值(不为{@code null}或者{@code Faceplate.NONE})，则不能重复set，返回{@code false}
	 * 
	 * @param up
	 * @return
	 */
	public boolean setUp(Faceplate up) {
		boolean res = false;
		if (All[UP] == null || All[UP] == Faceplate.NONE) {
			All[UP] = up;
			res = true;
		}
		return res;
	}

	/**
	 * 如果这一面已被赋值(不为{@code null}或者{@code Faceplate.NONE})，则不能重复set，返回{@code false}
	 * 
	 * @param up
	 * @return
	 */
	public boolean setDown(Faceplate down) {
		boolean res = false;
		if (All[DOWN] == null || All[DOWN] == Faceplate.NONE) {
			All[DOWN] = down;
			res = true;
		}
		return res;
	}

	/**
	 * 如果这一面已被赋值(不为{@code null}或者{@code Faceplate.NONE})，则不能重复set，返回{@code false}
	 * 
	 * @param up
	 * @return
	 */
	public boolean setLeft(Faceplate left) {
		boolean res = false;
		if (All[LEFT] == null || All[LEFT] == Faceplate.NONE) {
			All[LEFT] = left;
			res = true;
		}
		return res;
	}

	/**
	 * 如果这一面已被赋值(不为{@code null}或者{@code Faceplate.NONE})，则不能重复set，返回{@code false}
	 * 
	 * @param up
	 * @return
	 */
	public boolean setRight(Faceplate right) {
		boolean res = false;
		if (All[RIGHT] == null || All[RIGHT] == Faceplate.NONE) {
			All[RIGHT] = right;
			res = true;
		}
		return res;
	}

	/**
	 * 如果这一面已被赋值(不为{@code null}或者{@code Faceplate.NONE})，则不能重复set，返回{@code false}
	 * 
	 * @param up
	 * @return
	 */
	public boolean setFont(Faceplate font) {
		boolean res = false;
		if (All[FONT] == null || All[FONT] == Faceplate.NONE) {
			All[FONT] = font;
			res = true;
		}
		return res;
	}

	/**
	 * 如果这一面已被赋值(不为{@code null}或者{@code Faceplate.NONE})，则不能重复set，返回{@code false}
	 * 
	 * @param up
	 * @return
	 */
	public boolean setBack(Faceplate back) {
		boolean res = false;
		if (All[BACK] == null || All[BACK] == Faceplate.NONE) {
			All[BACK] = back;
			res = true;
		}
		return res;
	}

	/**
	 * 以前后面中心线为轴，向下旋转{@code turns*90°}
	 * 
	 * @param turns
	 */
	public void turnFClockwise(int turns) {
		int length = F.length;
		turns = ((turns % length) + length) % length;
		for (int t = 0; t < turns; t++) {
			Faceplate tmp = All[F[0]];
			for (int i = 0; i < length - 1; i++) {
				All[F[i]] = All[F[i + 1]];
			}
			All[F[length - 1]] = tmp;
		}
	}

	/**
	 * 以左右面中心线为轴，向后旋转{@code turns*90°}
	 * 
	 * @param turns
	 */
	public void turnRClockwise(int turns) {
		int length = R.length;
		turns = ((turns % length) + length) % length;
		for (int t = 0; t < turns; t++) {
			Faceplate tmp = All[R[0]];
			for (int i = 0; i < length - 1; i++) {
				All[R[i]] = All[R[i + 1]];
			}
			All[R[length - 1]] = tmp;
		}
	}

	/**
	 * 以上下面中心线为轴，向左旋转{@code turns*90°}
	 * 
	 * @param turns
	 */
	public void turnUClockwise(int turns) {
		int length = U.length;
		turns = ((turns % length) + length) % length;
		for (int t = 0; t < turns; t++) {
			Faceplate tmp = All[U[0]];
			for (int i = 0; i < length - 1; i++) {
				All[U[i]] = All[U[i + 1]];
			}
			All[U[length - 1]] = tmp;
		}
	}

	public String show() {
		return "UP:" + this.getUp().getName()
				+ "\nFONT:" + this.getFont().getName()
				+ "\nLEFT:" + this.getLeft().getName()
				+ "\nBACK:" + this.getBack().getName()
				+ "\nRIGHT:" + this.getRight().getName()
				+ "\nDOWN:" + this.getDown().getName();
	}
}
