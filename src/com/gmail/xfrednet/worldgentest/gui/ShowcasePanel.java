package com.gmail.xfrednet.worldgentest.gui;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gmail.xfrednet.worldgentest.gui.Layout.GridLayout;
import com.gmail.xfrednet.worldgentest.gui.Layout.GridLayoutConstraints;

public class ShowcasePanel {

	int gridWidth;
	int gridHeight;
	int cellSize;
	
	JPanel contentPanel;
	GridLayout contentlayout;
	
	public ShowcasePanel(int gridWidth, int gridHeight, int cellSize) {
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		this.cellSize = cellSize;
		
		initContent();
	}
	
	private void initContent() {
		this.contentPanel = new JPanel();
		
		this.contentlayout = new GridLayout(this.gridWidth, this.gridHeight, this.cellSize);
		this.contentPanel.setLayout(this.contentlayout);
		//TODO something 
		
		this.contentPanel.add(new JLabel("Hello 1"), new GridLayoutConstraints(0, 0));
	}
	
	public void add(ImagePanel image) {
		this.contentPanel.add(image.getContentPanel(), image.getConstrains());
	}
	
	public JPanel getContentPanel() {
		return this.contentPanel;
	}
}
