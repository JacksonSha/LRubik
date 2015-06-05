package com.stang.tang.rubik.scan;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import com.stang.tang.rubik.model.RubikException;

/**
 * 扫描魔方的一个面
 * 以魔方的一面作为一个扫面对象
 * 坐标同魔方坐标，X轴为从左向右，Y轴为从后到前
 * 
 * @author Jackson Sha
 * <br/>
 * 2013-04-18
 * 
 */
public class ScanFace {
	/**
	 * 魔方的阶数
	 */
	private int N;

	/**
	 * 魔方的边长
	 */
	private float edgeLength;

	/**
	 * 相邻两个Cube的中心距，其值为edgeLength/N
	 */
	private float centerDistance;

	/**
	 * 魔方一个面的原点在屏幕中的绝对坐标
	 */
	private RelativePoint originPoint;

	/**
	 * 魔方一个面所要扫描的点的坐标
	 */
	private RelativePoint[][] RPoints;

	/**
	 * 与RPoints坐标对应的点的RGB值
	 */
	private int[][] RGB;

	private static Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

	public ScanFace(int n, float edgeLength, RelativePoint originPoint) {
		if (n < 2 || edgeLength < 0) {
			throw new RubikException();
		}
		N = n;
		this.edgeLength = edgeLength;
		this.centerDistance = edgeLength / n;
		this.originPoint = originPoint;
		this.init();
	}

	/**
	 * 以行扫描（X轴）的方式初始化RPoints（RPoints[Y][X]）
	 */
	private void init() {
		RPoints = new RelativePoint[N][N];
		RGB = new int[N][N];
		for (int i = 0; i < N; i++) {
			float begin = centerDistance / 2;
			RPoints[i][0] = new RelativePoint(begin, begin + centerDistance * i);
			for (int j = 1; j < N; j++) {
				RPoints[i][j] = new RelativePoint(begin + centerDistance * j, RPoints[i][0].getY());
			}
		}
	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

	public float getEdgeLength() {
		return edgeLength;
	}

	public void setEdgeLength(float edgeLength) {
		this.edgeLength = edgeLength;
	}

	public RelativePoint getOriginPoint() {
		return originPoint;
	}

	public void setOriginPoint(RelativePoint originPoint) {
		this.originPoint = originPoint;
	}

	/**
	 * 执行扫描，并返回这个面的color值：RGB的int数组
	 * <br/>注意坐标对应
	 * @return int[][]
	 */
	public int[][] scanning() {
		try {
			// bScreenShot为屏幕截图
			BufferedImage bScreenShot = new Robot()
					.createScreenCapture(new Rectangle(0, 0, (int) dimension
							.getWidth(), (int) dimension.getHeight()));
			float x0 = originPoint.getX();
			float y0 = originPoint.getY();
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					RGB[i][j] = bScreenShot.getRGB(
							(int) (x0 + RPoints[i][j].getX()),
							(int) (y0 + RPoints[i][j].getY()));
				}
			}
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return RGB;
	}

}
