package com.stang.tang.rubik.model;

import java.util.HashMap;
import java.util.Map;

public class RubikGenerator {
	private static final int G = 0;
	private static final int O = 1;
	private static final int B = 2;
	private static final int R = 3;
	private static final int Y = 4;
	private static final int W = 5;
	private static final Map<Integer, Faceplate> colors = new HashMap<Integer, Faceplate>();

	static {
		colors.put(G, Faceplate.GREEN);
		colors.put(O, Faceplate.ORANGE);
		colors.put(B, Faceplate.BLUE);
		colors.put(R, Faceplate.RED);
		colors.put(Y, Faceplate.YELLOW);
		colors.put(W, Faceplate.WHITE);
	}

	public static Faceplate getFaceplate(int idx) {
		return colors.get(idx);
	}

	/**
	 * cubes=6*n*n 
	 * <pre>
	 *      4(U) 
	 * 0(L) 1(F) 2(R) 3(B) 
	 *      5(D)
	 * 
	 *       4 4 4
	 *       4 4 4
	 *       4 4 4
	 * 0 0 0 1 1 1 2 2 2 3 3 3
	 * 0 0 0 1 1 1 2 2 2 3 3 3
	 * 0 0 0 1 1 1 2 2 2 3 3 3
	 *       5 5 5
	 *       5 5 5
	 *       5 5 5
	 * </pre>
	 * @param n
	 * @param cubes
	 * @return
	 */
	public static RubikCube generate(int n, int[][][] cubes) {
		if (cubes == null) {
			throw new RubikException("Cubes cannot null");
		}
		if (cubes.length != 6) {
			throw new RubikException("Only support 6 faces");
		}
		if (cubes[0].length != n || cubes[0][0].length != n) {
			throw new RubikException("Only support n*n steps");
		}
		RubikCube rubik = null;
		switch (n) {
		case 2:
			rubik = new RubikCube2R(true);
			break;
		case 3:
			rubik = new RubikCube3R(true);
			break;
		case 4:
			rubik = new RubikCube4R(true);
			break;
		default:
			rubik = new RubikCube(n, true);
			break;
		}
		for (int i = 0; i < cubes.length; i++) {
			for (int j = 0; j < cubes[i].length; j++) {
				for (int k = 0; k < cubes[i][j].length; k++) {
					if (cubes[i][j][k] < G || cubes[i][j][k] > W) {
						throw new RubikException("Colors not exist");
					}
				}
			}
		}
		Cube[][] left = rubik.getLeft();
		for (int i = 0; i < cubes[0].length; i++) {
			for (int j = 0; j < cubes[0][i].length; j++) {
				left[n - 1 - i][j].setLeft(getFaceplate(cubes[0][i][j]));
			}
		}
		Cube[][] front = rubik.getFront();
		for (int i = 0; i < cubes[0].length; i++) {
			for (int j = 0; j < cubes[0][i].length; j++) {
				front[n - 1 - i][j].setFront(getFaceplate(cubes[1][i][j]));
			}
		}
		Cube[][] right = rubik.getRight();
		for (int i = 0; i < cubes[0].length; i++) {
			for (int j = 0; j < cubes[0][i].length; j++) {
				right[n - 1 - i][n - 1 - j].setRight(getFaceplate(cubes[2][i][j]));
			}
		}
		Cube[][] back = rubik.getBack();
		for (int i = 0; i < cubes[0].length; i++) {
			for (int j = 0; j < cubes[0][i].length; j++) {
				back[n - 1 - i][n - 1 - j].setBack(getFaceplate(cubes[3][i][j]));
			}
		}
		Cube[][] up = rubik.getUp();
		for (int i = 0; i < cubes[0].length; i++) {
			for (int j = 0; j < cubes[0][i].length; j++) {
				up[i][j].setUp(getFaceplate(cubes[4][i][j]));
			}
		}
		Cube[][] down = rubik.getDown();
		for (int i = 0; i < cubes[0].length; i++) {
			for (int j = 0; j < cubes[0][i].length; j++) {
				down[n - 1 - i][j].setDown(getFaceplate(cubes[5][i][j]));
			}
		}
		return rubik;
	}

}
