package com.stang.tang.rubik.model;

import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 所有N阶(N*N*N)魔方的父类<br/>
 * 魔方块的信息存储为一个N*N*N的数组，以底层左后方的一个角块为坐标原点(0,0,0)，X轴从左向右，Y轴从后向前，Z轴从下向上
 * 
 * @author Jackson Sha
 * <br/>
 * 2012-12-19
 * 
 */
public class RubikCube {

	/**
	 * 魔方的阶数
	 */
	protected int N;

	/**
	 * 魔方的所有块，以底层左后方的第一个角块为坐标原点(0,0,0)，X轴从左向右，Y轴从后向前，Z轴从下向上
	 */
	protected Cube[][][] rubik;

	protected static final Pattern STEP_ONE_PATTERN = Pattern
			.compile("[R|U|F|L|D|B|X|Y|Z]['|2]?");
	protected static final Pattern STEP_ALL_PATTERN = Pattern.compile("^("
			+ STEP_ONE_PATTERN.toString() + ")+$");

	protected static final Faceplate UpFace = Faceplate.YELLOW;
	protected static final Faceplate DownFace = Faceplate.WHITE;

	/**
	 * 默认 上:黄，下:白，左:蓝，右:绿，前:红，后:橙
	 * 
	 * @param n
	 * @throws Exception 
	 */
	public RubikCube(int n) {
		this(n, false);
	}

	/**
	 * 若{@code isNone为true}，则返回的所有的{@code Cube.Faceplate}都是{@code NONE}
	 * @param n
	 * @param isNone
	 */
	public RubikCube(int n, boolean isNone) {
		if (n < 2) {
			return;
		}
		N = n;
		rubik = new Cube[N][N][N];
		if (isNone) {
			for (int i = 0; i < rubik.length; i++) {
				for (int j = 0; j < rubik[i].length; j++) {
					for (int k = 0; k < rubik[i][j].length; k++) {
						rubik[i][j][k] = new Cube();
					}
				}
			}
		} else {
			for (int i = 0; i < rubik.length; i++) {
				for (int j = 0; j < rubik[i].length; j++) {
					for (int k = 0; k < rubik[i][j].length; k++) {
						rubik[i][j][k] = new Cube(Faceplate.YELLOW,
								Faceplate.WHITE, Faceplate.BLUE,
								Faceplate.GREEN, Faceplate.RED,
								Faceplate.ORANGE);
					}
				}
			}
		}
	}

	/**
	 * rubik[Z][Y][X]对应坐标(X,Y,Z)<br/>
	 * <br/>
	 * 
	 * <pre>
	 * 底层左后方坐标为(0,0,0)
	 * 底层右后方坐标为(0,0,N-1)
	 * 底层左前方坐标为(0,N-1,0)
	 * 底层右前方坐标为(0,N-1,N-1)
	 * 
	 * 顶层左后方坐标为(N-1,0,0)
	 * 顶层右后方坐标为(N-1,0,N-1)
	 * 顶层左前方坐标为(N-1,N-1,0)
	 * 顶层右前方坐标为(N-1,N-1,N-1)
	 * </pre>
	 * 
	 * @param rubik
	 * @throws RubikException
	 */
	public RubikCube(Cube[][][] rubik) {
		if (rubik != null && rubik.length == rubik[0].length
				&& rubik.length == rubik[0][0].length && rubik.length < 2) {
			N = rubik.length;
			this.rubik = rubik;
		} else {
			throw new RubikException("It must be N*N*N cube.");
		}
	}

	/**
	 * 返回魔方的阶数N
	 * 
	 * @return
	 */
	public int getRank() {
		return N;
	}

	/**
	 * Return (X,Y,N-1)
	 * 
	 * @return
	 */
	public Cube[][] getUp() {
		return getLayer(Axis.ZAxis, N - 1);
	}

	/**
	 * Return (X,Y,0)
	 * 
	 * @return
	 */
	public Cube[][] getDown() {
		return getLayer(Axis.ZAxis, 0);
	}

	/**
	 * Return (0,Y,Z)
	 * 
	 * @return
	 */
	public Cube[][] getLeft() {
		return getLayer(Axis.XAxis, 0);
	}

	/**
	 * Return (N-1,Y,Z)
	 * 
	 * @return
	 */
	public Cube[][] getRight() {
		return getLayer(Axis.XAxis, N - 1);
	}

	/**
	 * Return (X,N-1,Z)
	 * 
	 * @return
	 */
	public Cube[][] getFront() {
		return getLayer(Axis.YAxis, N - 1);
	}

	/**
	 * Return (X,0,Z)
	 * 
	 * @return
	 */
	public Cube[][] getBack() {
		return getLayer(Axis.YAxis, 0);
	}

	/**
	 * If Axis is X that means the X coordinate is Fixed and be set as layer.
	 * 
	 * @param axis
	 * @param layer
	 * @return
	 */
	protected Cube[][] getLayer(Axis axis, int layer) {
		if (layer < 0 || layer >= N || axis == null) {
			return null;
		}
		Cube[][] res = new Cube[N][N];
		switch (axis) {
		case XAxis: {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					res[i][j] = rubik[i][j][layer];
				}
			}
			break;
		}
		case YAxis: {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					res[i][j] = rubik[i][layer][j];
				}
			}
			break;
		}
		case ZAxis: {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					res[i][j] = rubik[layer][i][j];
				}
			}
			break;
		}
		default: {
			break;
		}
		}
		return res;
	}

	/**
	 * 从正上方看过去，顺时针转顶层{@code turns*90°}
	 * 
	 * @param turns
	 */
	public void turnUp(int turns) {
		turnZAxisClockwise(N - 1, turns);
	}

	/**
	 * 从正上方看过去，顺时针转顶层{@code turns*90°}
	 * 
	 * @param turns
	 */
	public void turnDown(int turns) {
		turnZAxisClockwise(0, 4 - turns);
	}

	/**
	 * 从正上方看过去，顺时针转最左面{@code turns*90°}
	 * 
	 * @param turns
	 */
	public void turnLeft(int turns) {
		turnXAxisClockwise(0, 4 - turns);
	}

	/**
	 * 从正上方看过去，顺时针转最右面{@code turns*90°}
	 * 
	 * @param turns
	 */
	public void turnRight(int turns) {
		turnXAxisClockwise(N - 1, turns);
	}

	/**
	 * 从正上方看过去，顺时针转最前面{@code turns*90°}
	 * 
	 * @param turns
	 */
	public void turnFront(int turns) {
		turnYAxisClockwise(N - 1, turns);
	}

	/**
	 * 从正上方看过去，顺时针转最后面{@code turns*90°}
	 * 
	 * @param turns
	 */
	public void turnBack(int turns) {
		turnYAxisClockwise(0, 4 - turns);
	}

	/**
	 * 整体以R方向（以X为轴）旋转{@code turns*90°}
	 * 
	 * @param turns
	 */
	public void turnX(int turns) {
		for (int i = 0; i < N; i++) {
			turnXAxisClockwise(i, turns);
		}
	}

	/**
	 * 整体以U方向（以Z为轴）旋转{@code turns*90°}
	 * 
	 * @param turns
	 */
	public void turnY(int turns) {
		for (int i = 0; i < N; i++) {
			turnZAxisClockwise(i, turns);
		}
	}

	/**
	 * 整体以F方向（以Y为轴）旋转{@code turns*90°}
	 * 
	 * @param turns
	 */
	public void turnZ(int turns) {
		for (int i = 0; i < N; i++) {
			turnYAxisClockwise(i, turns);
		}
	}

	/**
	 * 从X轴反方向看过去，顺时针转第{@code layer}层{@code (turns*90°）}
	 * 
	 * @param layer
	 * @param turns
	 */
	public void turnXAxisClockwise(int layer, int turns) {
		this.turnClockwise(Axis.XAxis, layer, turns);
	}

	/**
	 * 从Y轴反方向看过去，顺时针转第{@code layer}层{@code (turns*90°）}
	 * 
	 * @param layer
	 * @param turns
	 */
	public void turnYAxisClockwise(int layer, int turns) {
		this.turnClockwise(Axis.YAxis, layer, 4 - turns);
	}

	/**
	 * 从Z轴反方向看过去，顺时针转第{@code layer}层{@code (turns*90°）}
	 * 
	 * @param layer
	 * @param turns
	 */
	public void turnZAxisClockwise(int layer, int turns) {
		this.turnClockwise(Axis.ZAxis, layer, turns);
	}

	/**
	 * Rubik[Z][Y][X]
	 * 
	 * <pre>
	 * If turns=1, then
	 * 
	 * Turn
	 * (0,0)   (0,1)   ...  (0,N-1)
	 * (1,0)   (1,1)   ...  (1,N-1)
	 * ...     ...     ...  ...
	 * (N-1,0) (N-1,1) ...  (N-1,N-1)
	 * 
	 * To
	 * (N-1,0)   ...  (1,0)   (0,0)
	 * (N-1,1)   ...  (1,1)   (0,1)
	 * ...       ...  ...     ...
	 * (N-1,N-1) ...  (1,N-1) (0,N-1)
	 * </pre>
	 * 
	 * <pre>        (i,j)
	 * 
	 *                                (j,N-i-1)
	 * 
	 * (N-j-1,i)
	 * 
	 *                   (N-i-1,N-j-1)</pre>
	 * 
	 * This clockwise means U,D',F',B,R,L'.
	 * 
	 * @param axis
	 * @param layer
	 * @param turns
	 */
	private void turnClockwise(Axis axis, int layer, int turns) {
		turns = ((turns % 4) + 4) % 4;
		if (layer < 0 || layer >= N || turns == 0 || axis == null) {
			return;
		}
		switch (axis) {

		case XAxis: {
			// rubik[Z][Y][layer]
			Cube temp = null;
			switch (turns) {
			case 1: {
				// (i,j)-->(j,N-i-1)-->(N-i-1,N-j-1)-->(N-j-1,i)-->(i,j)
				for (int i = 0; i < (N / 2); i++) {
					for (int j = i; j <= (N - i - 2); j++) {
						temp = rubik[i][j][layer];
						rubik[i][j][layer] = rubik[N - j - 1][i][layer];
						rubik[N - j - 1][i][layer] = rubik[N - i - 1][N - j - 1][layer];
						rubik[N - i - 1][N - j - 1][layer] = rubik[j][N - i - 1][layer];
						rubik[j][N - i - 1][layer] = temp;
						rubik[i][j][layer].turnRClockwise(turns);
						rubik[N - j - 1][i][layer].turnRClockwise(turns);
						rubik[j][N - i - 1][layer].turnRClockwise(turns);
						rubik[N - i - 1][N - j - 1][layer].turnRClockwise(turns);
					}
				}
				break;
			}
			case 2: {
				// (i,j)<-->(N-i-1,N-j-1), (j,N-i-1)<-->(N-j-1,i)
				for (int i = 0; i < (N / 2); i++) {
					for (int j = i; j <= (N - i - 2); j++) {
						temp = rubik[i][j][layer];
						rubik[i][j][layer] = rubik[N - i - 1][N - j - 1][layer];
						rubik[N - i - 1][N - j - 1][layer] = temp;
						temp = rubik[N - j - 1][i][layer];
						rubik[N - j - 1][i][layer] = rubik[j][N - i - 1][layer];
						rubik[j][N - i - 1][layer] = temp;
						rubik[i][j][layer].turnRClockwise(turns);
						rubik[N - j - 1][i][layer].turnRClockwise(turns);
						rubik[j][N - i - 1][layer].turnRClockwise(turns);
						rubik[N - i - 1][N - j - 1][layer].turnRClockwise(turns);
					}
				}
				break;
			}
			case 3: {
				// (i,j)-->(N-j-1,i)-->(N-i-1,N-j-1)-->(j,N-i-1)-->(i,j)
				for (int i = 0; i < (N / 2); i++) {
					for (int j = i; j <= (N - i - 2); j++) {
						temp = rubik[i][j][layer];
						rubik[i][j][layer] = rubik[j][N - i - 1][layer];
						rubik[j][N - i - 1][layer] = rubik[N - i - 1][N - j - 1][layer];
						rubik[N - i - 1][N - j - 1][layer] = rubik[N - j - 1][i][layer];
						rubik[N - j - 1][i][layer] = temp;
						rubik[i][j][layer].turnRClockwise(turns);
						rubik[N - j - 1][i][layer].turnRClockwise(turns);
						rubik[j][N - i - 1][layer].turnRClockwise(turns);
						rubik[N - i - 1][N - j - 1][layer].turnRClockwise(turns);
					}
				}
				break;
			}
			}
			break;
		}

		case YAxis: {
			// rubik[Z][layer][X]
			Cube temp = null;
			switch (turns) {
			case 1: {
				// (i,j)-->(j,N-i-1)-->(N-i-1,N-j-1)-->(N-j-1,i)-->(i,j)
				for (int i = 0; i < (N / 2); i++) {
					for (int j = i; j <= (N - i - 2); j++) {
						temp = rubik[i][layer][j];
						rubik[i][layer][j] = rubik[N - j - 1][layer][i];
						rubik[N - j - 1][layer][i] = rubik[N - i - 1][layer][N - j - 1];
						rubik[N - i - 1][layer][N - j - 1] = rubik[j][layer][N - i - 1];
						rubik[j][layer][N - i - 1] = temp;
						rubik[i][layer][j].turnFClockwise(4 - turns);
						rubik[N - j - 1][layer][i].turnFClockwise(4 - turns);
						rubik[j][layer][N - i - 1].turnFClockwise(4 - turns);
						rubik[N - i - 1][layer][N - j - 1].turnFClockwise(4 - turns);
					}
				}
				break;
			}
			case 2: {
				// (i,j)<-->(N-i-1,N-j-1), (j,N-i-1)<-->(N-j-1,i)
				for (int i = 0; i < (N / 2); i++) {
					for (int j = i; j <= (N - i - 2); j++) {
						temp = rubik[i][layer][j];
						rubik[i][layer][j] = rubik[N - i - 1][layer][N - j - 1];
						rubik[N - i - 1][layer][N - j - 1] = temp;
						temp = rubik[N - j - 1][layer][i];
						rubik[N - j - 1][layer][i] = rubik[j][layer][N - i - 1];
						rubik[j][layer][N - i - 1] = temp;
						rubik[i][layer][j].turnFClockwise(4 - turns);
						rubik[N - j - 1][layer][i].turnFClockwise(4 - turns);
						rubik[j][layer][N - i - 1].turnFClockwise(4 - turns);
						rubik[N - i - 1][layer][N - j - 1].turnFClockwise(4 - turns);
					}
				}
				break;
			}
			case 3: {
				// (i,j)-->(N-j-1,i)-->(N-i-1,N-j-1)-->(j,N-i-1)-->(i,j)
				for (int i = 0; i < (N / 2); i++) {
					for (int j = i; j <= (N - i - 2); j++) {
						temp = rubik[i][layer][j];
						rubik[i][layer][j] = rubik[j][layer][N - i - 1];
						rubik[j][layer][N - i - 1] = rubik[N - i - 1][layer][N - j - 1];
						rubik[N - i - 1][layer][N - j - 1] = rubik[N - j - 1][layer][i];
						rubik[N - j - 1][layer][i] = temp;
						rubik[i][layer][j].turnFClockwise(4 - turns);
						rubik[N - j - 1][layer][i].turnFClockwise(4 - turns);
						rubik[j][layer][N - i - 1].turnFClockwise(4 - turns);
						rubik[N - i - 1][layer][N - j - 1].turnFClockwise(4 - turns);
					}
				}
				break;
			}
			}
			break;
		}

		case ZAxis: {
			// rubik[layer][Y][X]
			Cube temp = null;
			switch (turns) {
			case 1: {
				// (i,j)-->(j,N-i-1)-->(N-i-1,N-j-1)-->(N-j-1,i)-->(i,j)
				for (int i = 0; i < (N / 2); i++) {
					for (int j = i; j <= (N - i - 2); j++) {
						temp = rubik[layer][i][j];
						rubik[layer][i][j] = rubik[layer][N - j - 1][i];
						rubik[layer][N - j - 1][i] = rubik[layer][N - i - 1][N - j - 1];
						rubik[layer][N - i - 1][N - j - 1] = rubik[layer][j][N - i - 1];
						rubik[layer][j][N - i - 1] = temp;
						rubik[layer][i][j].turnUClockwise(turns);
						rubik[layer][N - j - 1][i].turnUClockwise(turns);
						rubik[layer][j][N - i - 1].turnUClockwise(turns);
						rubik[layer][N - i - 1][N - j - 1].turnUClockwise(turns);
					}
				}
				break;
			}
			case 2: {
				// (i,j)<-->(N-i-1,N-j-1), (j,N-i-1)<-->(N-j-1,i)
				for (int i = 0; i < (N / 2); i++) {
					for (int j = i; j <= (N - i - 2); j++) {
						temp = rubik[layer][i][j];
						rubik[layer][i][j] = rubik[layer][N - i - 1][N - j - 1];
						rubik[layer][N - i - 1][N - j - 1] = temp;
						temp = rubik[layer][N - j - 1][i];
						rubik[layer][N - j - 1][i] = rubik[layer][j][N - i - 1];
						rubik[layer][j][N - i - 1] = temp;
						rubik[layer][i][j].turnUClockwise(turns);
						rubik[layer][N - j - 1][i].turnUClockwise(turns);
						rubik[layer][j][N - i - 1].turnUClockwise(turns);
						rubik[layer][N - i - 1][N - j - 1].turnUClockwise(turns);
					}
				}
				break;
			}
			case 3: {
				// (i,j)-->(N-j-1,i)-->(N-i-1,N-j-1)-->(j,N-i-1)-->(i,j)
				for (int i = 0; i < (N / 2); i++) {
					for (int j = i; j <= (N - i - 2); j++) {
						temp = rubik[layer][i][j];
						rubik[layer][i][j] = rubik[layer][j][N - i - 1];
						rubik[layer][j][N - i - 1] = rubik[layer][N - i - 1][N - j - 1];
						rubik[layer][N - i - 1][N - j - 1] = rubik[layer][N - j - 1][i];
						rubik[layer][N - j - 1][i] = temp;
						rubik[layer][i][j].turnUClockwise(turns);
						rubik[layer][N - j - 1][i].turnUClockwise(turns);
						rubik[layer][j][N - i - 1].turnUClockwise(turns);
						rubik[layer][N - i - 1][N - j - 1].turnUClockwise(turns);
					}
				}
				break;
			}
			}
			break;
		}

		default: {
			break;
		}

		}
	}

	/**
	 * 按steps所描述的步骤旋转魔方，steps的值必须匹配{@code STEP_ALL_PATTERN}正则表达式
	 * 
	 * @param steps
	 * @return
	 */
	public boolean turnSteps(String steps) {
		boolean res = false;
		if (steps != null) {
			Matcher match = STEP_ALL_PATTERN.matcher(steps);
			res = match.find();
			if (res) {
				Matcher mat1 = STEP_ONE_PATTERN.matcher(steps);
				while (mat1.find()) {
					this.execStep(mat1.group());
					steps = steps.substring(mat1.end());
					mat1 = STEP_ONE_PATTERN.matcher(steps);
				}
			}
		}
		return res;
	}

	/**
	 * 执行一步旋转，steps的值必须匹配{@code STEP_ONE_PATTERN}正则表达式
	 * 
	 * @param step
	 */
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

	/**
	 * 查看第{@code layer}层是否是有序的（按颜色）
	 * 
	 * @param layer
	 * @return
	 */
	public boolean isLayerOrderly(int layer) {
		Cube[][] lCube = getLayer(Axis.ZAxis, layer);
		if (lCube == null) {
			return false;
		}
		for (int i = 1; i < N; i++) {
			if (lCube[0][0].getLeft() != lCube[i][0].getLeft()
					|| lCube[0][0].getBack() != lCube[0][i].getBack()
					|| lCube[N - 1][N - 1].getRight() != lCube[i - 1][N - 1].getRight()
					|| lCube[N - 1][N - 1].getFront() != lCube[N - 1][i - 1].getFront()) {
				return false;
			}
		}
		if (layer == 0) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (lCube[0][0].getUp() != lCube[i][j].getUp()) {
						return false;
					}
				}
			}
		} else if (layer == N - 1) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (lCube[0][0].getDown() != lCube[i][j].getDown()) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * 把该魔方的六个面的颜色分别打印出来
	 */
	public void showMe() {
		this.showMe(System.out);
	}

	/**
	 * 把该魔方的六个面的颜色分别打印出来
	 * <pre>
	 * 结果如下：
	 *       上 上
	 *       上 上
	 * 左 左 前 前 右 右 后 后
	 * 左 左 前 前 右 右 后 后
	 *       下 下
	 *       下 下
	 * </pre>
	 * 
	 * @param ps
	 */
	public void showMe(PrintStream ps) {
		String format = "%3s";
		String split = "====";
		String blank = "    ";
		for (int i = 0; i < 4 * N; i++) {
			ps.print(split);
		}
		ps.println();

		Cube[][] faceUp = getUp();
		Cube[][] faceLeft = getLeft();
		Cube[][] faceFont = getFront();
		Cube[][] faceRight = getRight();
		Cube[][] faceBack = getBack();
		Cube[][] faceDown = getDown();

		for (int i = 0; i < N; i++) {
			printBlank(ps, blank);
			for (int j = 0; j < N; j++) {
				// up
				ps.printf(format, faceUp[i][j].getUp().getName());
			}
			printBlank(ps, blank);
			printBlank(ps, blank);
			ps.println();
		}

		for (int i = 0; i < N; i++) {
			// left
			for (int j = 0; j < N; j++) {
				ps.printf(format, faceLeft[(N - i - 1)][j].getLeft().getName());
			}
			// font
			for (int j = 0; j < N; j++) {
				ps.printf(format, faceFont[(N - i - 1)][j].getFront().getName());
			}
			// right
			for (int j = 0; j < N; j++) {
				ps.printf(format, faceRight[(N - i - 1)][N - j - 1].getRight().getName());
			}
			// back
			for (int j = 0; j < N; j++) {
				ps.printf(format, faceBack[(N - i - 1)][N - j - 1].getBack().getName());
			}
			ps.println();
		}

		for (int i = 0; i < N; i++) {
			printBlank(ps, blank);
			for (int j = 0; j < N; j++) {
				// down
				ps.printf(format, faceDown[N - i - 1][j].getDown().getName());
			}
			printBlank(ps, blank);
			printBlank(ps, blank);
			ps.println();
		}
	}

	private void printBlank(PrintStream ps, String blank) {
		for (int i = 0; i < N; i++) {
			ps.print(blank);
		}
	}

}
