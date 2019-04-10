package com.gmail.xfrednet.worldgentest.gui.Layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.util.ArrayList;

public class GridLayout implements LayoutManager2 {

	int gridWidth;
	int gridHeight;
	int cellSize;
	
	ArrayList<ComponentPair> components;
	boolean gridUsage[][];
	
	public GridLayout(int gridWidth, int gridHeight, int cellSize) {
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		this.cellSize = cellSize;
		
		this.components = new ArrayList<ComponentPair>();
		this.gridUsage = new boolean[gridWidth][gridHeight];
		
		int x;
		for (int y = 0; y < this.gridWidth; y++) {
			for (x = 0; x < this.gridHeight; x++) {
				this.gridUsage[x][y] = false;
			}
		}
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		// TODO Auto-generated method stub
		System.out.println("I hope you have insurrance");
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		ComponentPair toRemove = null;
		for (ComponentPair pair : this.components) {
			if (pair.component.equals(comp)) {
				toRemove = pair;
				break;
			}
		}
		
		if (toRemove != null) {
			this.components.remove(toRemove);
		}
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return new Dimension(
				this.gridWidth * this.cellSize, 
				this.gridHeight * this.cellSize);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return preferredLayoutSize(parent);
	}

	@Override
	public void layoutContainer(Container parent) {
		// TODO Auto-generated method stub
		System.out.println("When is this called?");
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (!(constraints instanceof GridLayoutConstraints)) {
			return;
		}
		
		ComponentPair pair = new ComponentPair();
		pair.component = comp;
		pair.constraints = findFit((GridLayoutConstraints)constraints);
		
		claimCells(pair.constraints);
		applyConstraints(pair);
		this.components.add(pair);
	}
	private GridLayoutConstraints findFit(GridLayoutConstraints cons) {
		// x: ! 
		// y: !
		if (	cons.prefferedX != GridLayoutConstraints.FIRST_FIT ||
				cons.prefferedY != GridLayoutConstraints.FIRST_FIT) {
			cons.gridX = cons.prefferedX;
			cons.gridY = cons.prefferedY;
			return cons;
		}
		
		// x: ~
		// y: !
		if (	cons.prefferedX == GridLayoutConstraints.FIRST_FIT ||
				cons.prefferedY != GridLayoutConstraints.FIRST_FIT) {
			cons.gridX = 0;
			cons.gridY = cons.prefferedY;
			
			for (int testX = 0; testX < this.gridWidth; testX++) {
				if (fits(testX, cons.gridY, cons.horiCells, cons.vertCells)) {
					cons.gridX = testX;
					break;
				}
			}
			
			return cons;
		}
		
		// x: !
		// y: ~
		if (	cons.prefferedX != GridLayoutConstraints.FIRST_FIT ||
				cons.prefferedY == GridLayoutConstraints.FIRST_FIT) {
			cons.gridX = cons.prefferedX;
			cons.gridY = 0;
			
			for (int testY = 0; testY < this.gridHeight; testY++) {
				if (fits(cons.gridX, testY, cons.horiCells, cons.vertCells)) {
					cons.gridY = testY;
					break;
				}
			}
			
			return cons;
		}
		
		// x: ~
		// y: ~
		if (	cons.prefferedX == GridLayoutConstraints.FIRST_FIT ||
				cons.prefferedY == GridLayoutConstraints.FIRST_FIT) {
			cons.gridX = 0;
			cons.gridY = 0;
			
			boolean foundFit = false;
			for (int testY = 0; testY < this.gridHeight; testY++) {
				for (int testX = 0; testX < this.gridWidth; testX++) {
					if (fits(testX, testY, cons.horiCells, cons.vertCells)) {
						cons.gridX = testX;
						cons.gridY = testY;
						foundFit = true;
						break;
					}
				}
				
				if (foundFit) {
					break;
				}
			}
			
			return cons;
		}
		
		return cons;
	}
	private boolean fits(int x, int y, int width, int height) {
		if (x < 0 || x + width >= this.gridWidth ||
				y < 0 || y + height >= this.gridHeight) {
			return false;
		}
		
		for (int testY = y; testY < (height + y); testY++) {
			for (int testX = x; testX < (width + x); testX++) {
				if (this.gridUsage[testX][testY])
					return false;
			}
		}
		
		return true;
	}
	private void claimCells(GridLayoutConstraints cons) {
		int x;
		for (int y = cons.gridY; y < cons.gridY * cons.vertCells; y++) {
			for (x = cons.gridX; x < cons.gridX * cons.vertCells; x++) {
				this.gridUsage[x][y] = true;
			}
		}
	}
	private void applyConstraints(ComponentPair pair) {
		Component com = pair.component;
		GridLayoutConstraints straints = pair.constraints;
		
		Rectangle rec = new Rectangle();
		rec.x = straints.gridX * this.cellSize;
		rec.y = straints.gridY * this.cellSize;
		rec.width = straints.horiCells * this.cellSize;
		rec.height = straints.vertCells * this.cellSize;
		
		com.setBounds(rec);
	}
	
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return preferredLayoutSize(target);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		int x;
		for (int y = 0; y < this.gridWidth; y++) {
			for (x = 0; x < this.gridHeight; x++) {
				this.gridUsage[x][y] = false;
			}
		}
		
		for (ComponentPair pair : this.components) {
			pair.constraints = findFit((GridLayoutConstraints)pair.constraints);
			
			claimCells(pair.constraints);
			applyConstraints(pair);
		}
	}
	
	private class ComponentPair {
		public Component component;
		public GridLayoutConstraints constraints;
	}
}
