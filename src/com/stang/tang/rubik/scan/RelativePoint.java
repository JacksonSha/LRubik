package com.stang.tang.rubik.scan;

/**
 * 相对点，即其他点相对原点（左上方第一个角块的面的左上角为（0,0）点）的坐标
 * 以横向向右为X轴正方向，竖向向下为Y轴正方向，同swing中屏幕坐标方向。
 * 
 * @author Jackson Sha
 * <br/>
 * 2013-04-18
 * 
 */
public class RelativePoint {
	private float x;
	private float y;

	public RelativePoint() {
		super();
	}

	public RelativePoint(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

}
