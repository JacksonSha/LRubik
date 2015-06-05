package com.stang.tang.rubik.model;

public class RubikGenerator {
	private static final int G = 0;
	private static final int O = 1;
	private static final int B = 2;
	private static final int R = 3;
	private static final int Y = 4;
	private static final int W = 5;

	public static Faceplate getFaceplate(int idx) {
		Faceplate face = null;
		switch (idx) {
		case G:
			face = Faceplate.GREEN;
			break;
		case O:
			face = Faceplate.ORANGE;
			break;
		case B:
			face = Faceplate.BLUE;
			break;
		case R:
			face = Faceplate.RED;
			break;
		case Y:
			face = Faceplate.YELLOW;
			break;
		case W:
			face = Faceplate.WHITE;
			break;
		}
		return face;
	}

	/**
	 * cubes=6*n*n
	 * 6
	 *      4(U)
	 * 0(L) 1(F) 2(R) 3(B)
	 *      5(D)
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
		RubikCube rubik = new RubikCube(n, true);
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
				left[i][j].setLeft(getFaceplate(cubes[0][i][j]));
			}
		}
		return rubik;
	}

}
