package com.stang.tang.rubik.model;

import java.awt.Color;

/**
 * 一个方块(Cube)的一面
 * 
 * @author Jackson Sha <br/>
 *         2012-12-19
 * 
 */
public enum Faceplate {
	YELLOW("黄", "Yellow", Color.YELLOW), RED("红", "Red", Color.RED), GREEN("绿",
			"Green", Color.GREEN), ORANGE("橙", "Orange", Color.ORANGE), BLUE(
			"蓝", "Blue", Color.BLUE), WHITE("白", "White", Color.WHITE), NONE(
			"无", "None", null);

	private String name;
	private String desc;
	private Color color;

	private static Faceplate[] all;

	private Faceplate() {

	}

	private Faceplate(String name, String desc, Color color) {
		this.name = name;
		this.desc = desc;
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public static Faceplate[] getAll() {
		if (all == null) {
			all = new Faceplate[] { YELLOW, RED, GREEN, ORANGE, BLUE, WHITE,
					NONE };
		}
		return all;
	}

}
