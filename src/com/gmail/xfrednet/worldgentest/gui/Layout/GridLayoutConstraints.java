package com.gmail.xfrednet.worldgentest.gui.Layout;

import java.awt.Component;

public class GridLayoutConstraints {

	public static final int FIRST_FIT = -1;
	
	int prefferedX;
	int prefferedY;
	
	int horiCells;
	int vertCells;
	
	int gridX;
	int gridY;
	
	public GridLayoutConstraints(int x, int y) {
		this(x, y, 1, 1);
	}
	
	public GridLayoutConstraints(int x, int y, int horiCells, int vertCells) {
		this.prefferedX = x;
		this.prefferedY = y;
		this.horiCells = horiCells;
		this.vertCells = vertCells;
	}
	
	public int getX() {
		return prefferedX;
	}
	
	public int getY() {
		return prefferedY;
	}
	
	public int getHoriCells() {
		return horiCells;
	}
	
	public int getVertCells() {
		return vertCells;
	}
	
}
