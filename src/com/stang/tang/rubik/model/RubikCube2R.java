package com.stang.tang.rubik.model;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import jxl.Workbook;

/**
 * 二阶魔方
 * 
 * @author Jackson Sha
 * <br/>
 * 2012-12-19
 * 
 */
public class RubikCube2R extends RubikCube implements Recovery {
	public static final int N = 2;
	public static final Pattern STEP_ONE_PATTERN = Pattern
			.compile("[R|U|F|L|D|B|X|Y|Z]['|2]?");
	public static final Pattern STEP_ALL_PATTERN = Pattern.compile("^("
			+ STEP_ONE_PATTERN.toString() + ")+$");

	private static Map<String, String> GraphicMode = new HashMap<String, String>();

	// 一 一 一  |
	// |         |
	// |         |
	// |  一 一 一
	static {
		// 恢复底层（底层四个角块）  Make First Layer
		// 共8个角块，位置如下
		//    [5]       [6]
		// [8]-------[7]
		// |          |
		// |  [1]     | [2]
		// [4]-------[3]
		// 除了第一个角块复原在位置[4]上，其他角块的都复原在位置[3]上
		// [0,1,2]为底层顶面颜色的朝向，[0]底面颜色朝顶面，[1]底面颜色朝侧面，[2]底面颜色朝前面
		
		// 恢复第1个角块，位置[4]为标准块
		GraphicMode.put("MFL|1|40", "");
		GraphicMode.put("MFL|1|41", "Z'Y");
		GraphicMode.put("MFL|1|42", "ZX'");
		
		GraphicMode.put("MFL|1|30", "Y");
		GraphicMode.put("MFL|1|31", "X'Y2");
		GraphicMode.put("MFL|1|32", "Z");
		
		GraphicMode.put("MFL|1|20", "Y2");
		GraphicMode.put("MFL|1|21", "ZY'");
		GraphicMode.put("MFL|1|22", "XY");
		
		GraphicMode.put("MFL|1|10", "Y'");
		GraphicMode.put("MFL|1|11", "X");
		GraphicMode.put("MFL|1|12", "Z'Y2");
		
		GraphicMode.put("MFL|1|80", "YX2");
		GraphicMode.put("MFL|1|81", "Z'");
		GraphicMode.put("MFL|1|82", "X'");
		
		GraphicMode.put("MFL|1|70", "Y2X2");
		GraphicMode.put("MFL|1|71", "X'Y");
		GraphicMode.put("MFL|1|72", "ZY");
		
		GraphicMode.put("MFL|1|60", "Y'X2");
		GraphicMode.put("MFL|1|61", "ZY2");
		GraphicMode.put("MFL|1|62", "XY2");
		
		GraphicMode.put("MFL|1|50", "X2");
		GraphicMode.put("MFL|1|51", "XY'");
		GraphicMode.put("MFL|1|52", "Z'Y'");
		
		// 恢复第2,3个角块，复原位置在[3]上
		GraphicMode.put("MFL|30", "");
		GraphicMode.put("MFL|31", "RU'R2");
		GraphicMode.put("MFL|32", "R2UR'");
		
		GraphicMode.put("MFL|20", "R2U'R2");
		GraphicMode.put("MFL|21", "R'UR'");
		GraphicMode.put("MFL|22", "R");
		
		GraphicMode.put("MFL|10", "B2R2");
		GraphicMode.put("MFL|11", "BR");
		GraphicMode.put("MFL|12", "B2UR'");
		
		GraphicMode.put("MFL|80", "U2R2");
		GraphicMode.put("MFL|81", "U'R'");
		GraphicMode.put("MFL|82", "U'RUR'");
		
		GraphicMode.put("MFL|70", "U'R2");
		GraphicMode.put("MFL|71", "R'");
		GraphicMode.put("MFL|72", "RUR'");
		
		GraphicMode.put("MFL|60", "R2");
		GraphicMode.put("MFL|61", "UR'");
		GraphicMode.put("MFL|62", "F'UF");
		
		GraphicMode.put("MFL|50", "UR2");
		GraphicMode.put("MFL|51", "U2R'");
		GraphicMode.put("MFL|52", "UF'UF");
		
		// 恢复第4个角块，复原位置在[3]上
		GraphicMode.put("MFL|4|30", "");
		GraphicMode.put("MFL|4|31", "RU'R'URU'R'");
		GraphicMode.put("MFL|4|32", "RUR'UF'U2F");
		
		GraphicMode.put("MFL|4|80", "U'RU'R'UF'UF");
		GraphicMode.put("MFL|4|81", "RU'R'");
		GraphicMode.put("MFL|4|82", "U2F'UF");
		
		GraphicMode.put("MFL|4|70", "RU'R'UF'UF");
		GraphicMode.put("MFL|4|71", "URU'R'");
		GraphicMode.put("MFL|4|72", "U'F'UF");
		
		GraphicMode.put("MFL|4|60", "U'RUR'URU'R'");
		GraphicMode.put("MFL|4|61", "U2RU'R'");
		GraphicMode.put("MFL|4|62", "F'UF");
		
		GraphicMode.put("MFL|4|50", "U2RU'R'UF'UF");
		GraphicMode.put("MFL|4|51", "U'RU'R'");
		GraphicMode.put("MFL|4|52", "UF'UF");
		
		
		// 恢复顶层顶面  Orientation of Last Layer
		// [0,1,2]为顶层顶面颜色的朝向，[0]顶面颜色朝顶面，[1]顶面颜色朝侧面，[2]顶面颜色朝前面
		// 顺序为顶层最外围的一圈，即(0,0),(0,1),(1,1),(1,0)
		// 1       2       ...   N-1   N
		// 4*N-4                       N+1
		// ...                         ...
		// 3*N-1                       2*N-2
		// 3*N-2   3*N-3   ...   2*N   2*N-1
		// 
		// 020:UUUU
		GraphicMode.put("OLL|0000", "");
		// 021:BBFF
		GraphicMode.put("OLL|1212", "R2U2RU2R2");
		// 022:LBLF
		GraphicMode.put("OLL|2211", "FRUR'U'RUR'U'F'");
		// 023:LULU
		GraphicMode.put("OLL|2001", "FRUR'U'F'");
		// 024:BUBU
		GraphicMode.put("OLL|1002", "RUR'U'R'FRF'");
		// 025:URFU
		GraphicMode.put("OLL|0102", "FR'F'RURU'R'");
		// 026:LUFR
		GraphicMode.put("OLL|2022", "RU2R'U'RU'R'");
		// 027:BRUF
		GraphicMode.put("OLL|1110", "RUR'URU2R'");
		
		// 恢复顶层（顶层侧面）  Permutation of Last Layer
		// [0,1,2,3]为顶层每个边块顺时针转到各自的正确位置所需的次数，一次转90°
		// 顺序为顶层最外围的一圈，即(0,0),(0,1),(1,1),(1,0)
		// 1       2       ...   N-1   N
		// 4*N-4                       N+1
		// ...                         ...
		// 3*N-1                       2*N-2
		// 3*N-2   3*N-3   ...   2*N   2*N-1
		// 
		GraphicMode.put("PLL|0000", "");
		// PLL1
		GraphicMode.put("PLL|2020", "RU'R'U'F2U'RUR'DR2");
		// PLL2
		GraphicMode.put("PLL|3302", "LF'LB2L'FLB2L2");
	}

	public RubikCube2R() {
		super(N);
	}

	public RubikCube2R(boolean isNone) {
		super(N, isNone);
	}

	public RubikCube2R(Workbook wbook) {
		super(N, wbook);
	}

	public RubikCube2R(Workbook wbook, int sht, int beginRow, int beginColumn) {
		super(N, wbook, sht, beginRow, beginColumn);
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
				this.turnFont(turns);
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
	public String[] recoverRubik() {
		String[] steps1 = recoverMFL();
		for(String s:steps1){
			System.out.println(s);
		}
		this.showMe();
		System.out.println();
		String steps2 = recoverOLL();
		System.out.println(steps2);
		this.showMe();
		System.out.println();
		String steps3 = recoverPLL();
		System.out.println(steps3);
		this.showMe();
		System.out.println();
		String[] res = new String[steps1.length + 2];
		for (int i = 0; i < steps1.length; i++) {
			res[i] = steps1[i];
		}
		res[steps1.length] = steps2;
		res[steps1.length + 1] = steps3;
		return res;
	}

	private Cube[] getCorners() {
		Cube[] corners = new Cube[8];
		corners[0] = rubik[0][0][0];
		corners[1] = rubik[0][0][N - 1];
		corners[2] = rubik[0][N - 1][N - 1];
		corners[3] = rubik[0][N - 1][0];
		corners[4] = rubik[N - 1][0][0];
		corners[5] = rubik[N - 1][0][N - 1];
		corners[6] = rubik[N - 1][N - 1][N - 1];
		corners[7] = rubik[N - 1][N - 1][0];
		return corners;
	}

	private Faceplate[][] getCornerFaces() {
		Cube[] corners = getCorners();
		Faceplate[][] faces = new Faceplate[corners.length][3];
		
		faces[0][0] = corners[0].getDown();
		faces[0][1] = corners[0].getBack();
		faces[0][2] = corners[0].getLeft();
		
		faces[1][0] = corners[1].getDown();
		faces[1][1] = corners[1].getRight();
		faces[1][2] = corners[1].getBack();
		
		faces[2][0] = corners[2].getDown();
		faces[2][1] = corners[2].getFont();
		faces[2][2] = corners[2].getRight();
		
		faces[3][0] = corners[3].getDown();
		faces[3][1] = corners[3].getLeft();
		faces[3][2] = corners[3].getFont();
		
		faces[4][0] = corners[4].getUp();
		faces[4][1] = corners[4].getBack();
		faces[4][2] = corners[4].getLeft();
		
		faces[5][0] = corners[5].getUp();
		faces[5][1] = corners[5].getRight();
		faces[5][2] = corners[5].getBack();
		
		faces[6][0] = corners[6].getUp();
		faces[6][1] = corners[6].getFont();
		faces[6][2] = corners[6].getRight();
		
		faces[7][0] = corners[7].getUp();
		faces[7][1] = corners[7].getLeft();
		faces[7][2] = corners[7].getFont();
		
		return faces;
	}

	private String[] recoverMFL() {
		String mfl1Base = "MFL|1|";
		String mfl23Base = "MFL|";
		String mfl4Base = "MFL|4|";
		String mflMode = null;
		String[] mflSteps = new String[4];
		Faceplate[][] faces = getCornerFaces();
		LOOP: for (int i = 0; i < faces.length; i++) {
			for (int j = 0; j < faces[i].length; j++) {
				if (DownFace == faces[i][j]) {
					mflMode = (i + 1) + "" + j;
					break LOOP;
				}
			}
		}
		mflSteps[0] = GraphicMode.get(mfl1Base + mflMode);
		if (mflSteps[0] == null) {
			throw new RubikException("Rubik face wrong Exception!!!");
		}
		this.turnSteps(mflSteps[0]);

		Faceplate baseFace = null;
		for (int t = 1; t <= 2; t++) {
			faces = getCornerFaces();
			baseFace = faces[3][2];
			LOOP: for (int i = 0; i < faces.length; i++) {
				if (i == 3) {
					continue;
				}
				int num = 0;
				for (int j = 0; j < faces[i].length; j++) {
					if (DownFace == faces[i][j]) {
						num++;
						mflMode = (i + 1) + "" + j;
					} else if (baseFace == faces[i][j]) {
						num++;
					}
					if (num == 2) {
						break LOOP;
					}
				}
			}
			mflSteps[t] = GraphicMode.get(mfl23Base + mflMode);
			if (mflSteps[t] == null) {
				throw new RubikException("Rubik face wrong Exception!!!");
			}
			mflSteps[t] += "Y";
			this.turnSteps(mflSteps[t]);
		}

		faces = getCornerFaces();
		baseFace = faces[3][2];
		LOOP: for (int i = faces.length - 1; i >= 0; i--) {
			if (i == 3) {
				continue;
			}
			int num = 0;
			for (int j = 0; j < faces[i].length; j++) {
				if (DownFace == faces[i][j]) {
					num++;
					mflMode = (i + 1) + "" + j;
				} else if (baseFace == faces[i][j]) {
					num++;
				}
				if (num == 2) {
					break LOOP;
				}
			}
		}
		mflSteps[3] = GraphicMode.get(mfl4Base + mflMode);
		if (mflSteps[3] == null) {
			throw new RubikException("Rubik face wrong Exception!!!");
		}
		this.turnSteps(mflSteps[3]);
		return mflSteps;
	}

	/**
	 * 恢复顶层（顶层侧面） [Permutation of Last Layer]
	 * <br/>
	 * [0,1,2]为顶层顶面颜色的朝向
	 * <br/>
	 * [0]顶面颜色朝顶面，[1]顶面颜色朝侧面，[2]顶面颜色朝前面
	 * <pre>
	 *   1       2       ...   N-1   N
	 *   4*N-4                       N+1
	 *   ...                         ...
	 *   3*N-1                       2*N-2
	 *   3*N-2   3*N-3   ...   2*N   2*N-1
	 * </pre>
	 * @return
	 * @throws RubikException
	 */
	private String recoverOLL() {
		String ollBase = "OLL|";
		String ollMode = ollBase;
		
		Cube[][] upLayer = this.getUp();
		int[] oll = new int[4 * (N - 1)];
		for (int i = 0; i < N - 1; i++) {
			if (UpFace == upLayer[0][i].getUp()) {
				oll[i] = 0;
			} else if (UpFace == upLayer[0][i].getBack()) {
				oll[i] = 1;
			} else if ((i % (N - 1) == 0)
					&& UpFace == upLayer[0][i].getLeft()) {
				oll[i] = 2;
			} else {
				throw new RubikException("Rubik cube wrong!!!");
			}
			
			if (UpFace == upLayer[i][N - 1].getUp()) {
				oll[N - 1 + i] = 0;
			} else if (UpFace == upLayer[i][N - 1].getRight()) {
				oll[N - 1 + i] = 1;
			} else if ((i % (N - 1) == 0)
					&& UpFace == upLayer[i][N - 1].getBack()) {
				oll[N - 1 + i] = 2;
			} else {
				throw new RubikException("Rubik cube wrong!!!");
			}
			
			if (UpFace == upLayer[N - 1][N - 1 - i].getUp()) {
				oll[2 * (N - 1) + i] = 0;
			} else if (UpFace == upLayer[N - 1][N - 1 - i].getFont()) {
				oll[2 * (N - 1) + i] = 1;
			} else if ((i % (N - 1) == 0)
					&& UpFace == upLayer[N - 1][N - 1 - i].getRight()) {
				oll[2 * (N - 1) + i] = 2;
			} else {
				throw new RubikException("Rubik cube wrong!!!");
			}
			
			if (UpFace == upLayer[N - 1 - i][0].getUp()) {
				oll[3 * (N - 1) + i] = 0;
			} else if (UpFace == upLayer[N - 1 - i][0].getLeft()) {
				oll[3 * (N - 1) + i] = 1;
			} else if ((i % (N - 1) == 0)
					&& UpFace == upLayer[N - 1 - i][0].getFont()) {
				oll[3 * (N - 1) + i] = 2;
			} else {
				throw new RubikException("Rubik cube wrong!!!");
			}
		}
		
		int turns = 0;
		ollMode += joinArray(oll);
		String ollSteps = GraphicMode.get(ollMode);
		while (ollSteps == null) {
			if (turns++ >= 3) {
				throw new RubikException("Rubik face wrong Exception!!!");
			}
			for (int i = 0; i < N - 1; i++) {
				int tmp = oll[i];
				oll[i] = oll[3 * (N - 1) + i];
				oll[3 * (N - 1) + i] = oll[2 * (N - 1) + i];
				oll[2 * (N - 1) + i] = oll[N - 1 + i];
				oll[N - 1 + i] = tmp;
			}
			ollMode = ollBase + joinArray(oll);
			ollSteps = GraphicMode.get(ollMode);
		}
		
		switch (turns) {
		case 1:
			ollSteps = "U" + ollSteps;
			break;
		case 2:
			ollSteps = "U2" + ollSteps;
			break;
		case 3:
			ollSteps = "U'" + ollSteps;
			break;
		}
		
		this.turnSteps(ollSteps);
		return ollSteps;
	}

	/**
	 * 恢复顶层（顶层侧面） [Permutation of Last Layer]
	 * <br/>
	 * [0,1,2,3]为顶层每个边块顺时针转到各自的正确位置所需的次数，一次转90°
	 * <pre>
	 *   1       2       ...   N-1   N
	 *   4*N-4                       N+1
	 *   ...                         ...
	 *   3*N-1                       2*N-2
	 *   3*N-2   3*N-3   ...   2*N   2*N-1
	 * </pre>
	 * @return
	 * @throws RubikException 
	 */
	private String recoverPLL() {
		String pllBase = "PLL|";
		String pllMode = pllBase;
		
		Cube[][] upLayer = this.getUp();
		Cube[][] downLayer = this.getDown();
		Faceplate[] upSideFace = new Faceplate[4 * (N - 1)];
		Faceplate[] downSideFace = new Faceplate[4 * (N - 1)];
		int[] pll = new int[4 * (N - 1)];
		int[] pllCopy = null;
		
		for (int i = 0; i < N - 1; i++) {
			upSideFace[i] = upLayer[0][i].getBack();
			upSideFace[N - 1 + i] = upLayer[i][N - 1].getRight();
			upSideFace[2 * (N - 1) + i] = upLayer[N - 1][N - 1 - i].getFont();
			upSideFace[3 * (N - 1) + i] = upLayer[N - 1 - i][0].getLeft();
			
			downSideFace[i] = downLayer[0][i].getBack();
			downSideFace[N - 1 + i] = downLayer[i][N - 1].getRight();
			downSideFace[2 * (N - 1) + i] = downLayer[N - 1][N - 1 - i].getFont();
			downSideFace[3 * (N - 1) + i] = downLayer[N - 1 - i][0].getLeft();
		}
		
		for (int i = 0; i < pll.length; i++) {
			if (upSideFace[i] == downSideFace[i % pll.length]) {
				pll[i] = 0;
			} else if (upSideFace[i] == downSideFace[(N - 1 + i) % pll.length]) {
				pll[i] = 1;
			} else if (upSideFace[i] == downSideFace[(2 * (N - 1) + i) % pll.length]) {
				pll[i] = 2;
			} else if (upSideFace[i] == downSideFace[(3 * (N - 1) + i) % pll.length]) {
				pll[i] = 3;
			} else {
				throw new RubikException("Can not find match location.");
			}
			pllMode += pll[i];
		}
		
		String pllSteps = GraphicMode.get(pllMode);

		int turnsY = 0;
		int turnsU = 0;
		while (pllSteps == null && turnsY < 4) {
			turnsU = 0;
			pllCopy = pll.clone();
			while (pllSteps == null && turnsU < 3) {
				turnsU++;
				for (int i = 0; i < N - 1; i++) {
					int tmp = pll[i];
					pll[i] = (pll[3 * (N - 1) + i] - 1 + 4) % 4;
					pll[3 * (N - 1) + i] = (pll[2 * (N - 1) + i] - 1 + 4) % 4;
					pll[2 * (N - 1) + i] = (pll[N - 1 + i] - 1 + 4) % 4;
					pll[N - 1 + i] = (tmp - 1 + 4) % 4;
				}
				pllMode = pllBase + joinArray(pll);
				pllSteps = GraphicMode.get(pllMode);
			}
			
			if (pllSteps == null) {
				turnsY++;
				pll = pllCopy.clone();
				for (int i = 0; i < N - 1; i++) {
					int tmp = pll[i];
					pll[i] = pll[3 * (N - 1) + i];
					pll[3 * (N - 1) + i] = pll[2 * (N - 1) + i];
					pll[2 * (N - 1) + i] = pll[N - 1 + i];
					pll[N - 1 + i] = tmp;
				}
				pllMode = pllBase + joinArray(pll);
				pllSteps = GraphicMode.get(pllMode);
			}
		}
		
		if (pllSteps == null) {
			throw new RubikException("Rubik face wrong Exception!!!");
		}
		
		switch (turnsU) {
		case 1:
			pllSteps = "U" + pllSteps;
			break;
		case 2:
			pllSteps = "U2" + pllSteps;
			break;
		case 3:
			pllSteps = "U'" + pllSteps;
			break;
		}
		
		switch (turnsY) {
		case 1:
			pllSteps = "Y" + pllSteps;
			break;
		case 2:
			pllSteps = "Y2" + pllSteps;
			break;
		case 3:
			pllSteps = "Y'" + pllSteps;
			break;
		}
		
		this.turnSteps(pllSteps);
		return pllSteps;
	}

	private String joinArray(int[] array) {
		if (array == null) {
			return null;
		}
		String res = "";
		for (int i : array) {
			res += i;
		}
		return res;
	}

}
