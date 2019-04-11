package com.gmail.xfrednet.worldgentest.gui;

import java.awt.Dimension;

import javax.swing.JPanel;

import com.gmail.xfrednet.worldgentest.gui.Layout.GridLayout;

public class ShowcasePanel {

	int gridWidth;
	int gridHeight;
	
	JPanel contentPanel;
	GridLayout contentlayout;
	
	public ShowcasePanel(int gridWidth, int gridHeight, int imageSize) {
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		
		initContent(imageSize);
	}
	
	private void initContent(int imageSize) {
		this.contentPanel = new JPanel();
		
		Dimension cellSize = ImagePanel.GetPreferredSize(imageSize);
		this.contentlayout = new GridLayout(this.gridWidth, this.gridHeight, cellSize.width, cellSize.height);
		this.contentPanel.setLayout(this.contentlayout);
	}
	
	public void add(ImagePanel image) {
		this.contentPanel.add(image.getContentPanel(), image.getConstrains());
	}
	
	public JPanel getContentPanel() {
		return this.contentPanel;
	}
}
