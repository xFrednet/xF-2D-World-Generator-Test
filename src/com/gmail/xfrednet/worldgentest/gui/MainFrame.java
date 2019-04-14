package com.gmail.xfrednet.worldgentest.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainFrame {

	// You got a good locking main frame. Don't search for that, well actually do do that!
	
	JFrame frame;
	ShowcasePanel showPanel;
	
	public MainFrame(int gridWidth, int gridHeight, int imageSize) {
		initFrame();
		initShowPanel(gridWidth, gridHeight, imageSize);
		initMenuPanel();
		
		// finish init
		this.frame.pack();
		this.frame.setVisible(true);
	}

	private void initMenuPanel() {
		// TODO Auto-generated method stub
		
	}

	private void initShowPanel(int gridWidth, int gridHeight, int cellSize) {
		showPanel = new ShowcasePanel(gridWidth, gridHeight, cellSize);
		this.frame.add(showPanel.getContentPanel());
	}

	private void initFrame() {
		this.frame = new JFrame("xFrednet's 2D world generation test");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.frame.setResizable(false);
		this.frame.setLocation(100, 100);
		this.frame.setLayout(new BorderLayout());
	}
	
	public ShowcasePanel getShowcasePanel() {
		return this.showPanel;
	}
	
}
