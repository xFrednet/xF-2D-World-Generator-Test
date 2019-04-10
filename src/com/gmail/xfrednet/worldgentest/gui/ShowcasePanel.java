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
		
		this.contentlayout = new GridLayout(this.gridWidth, this.gridHeight, this.cellSize, this.cellSize);
		this.contentPanel.setLayout(this.contentlayout);
		//TODO something 
		
		this.contentPanel.add(new JLabel("Hello 1[0, 0]"), new GridLayoutConstraints(0, 0, 1, 1));
		this.contentPanel.add(new JLabel("Hello 1[1, 1]"), new GridLayoutConstraints(1, 1));
		this.contentPanel.add(new JLabel("Hello 1[2, 2]"), new GridLayoutConstraints(2, 2));
		this.contentPanel.add(new JLabel("Hello 1[3, 3]"), new GridLayoutConstraints(3, 3));
		this.contentPanel.add(new JLabel("Hello 1[1, 0]"), new GridLayoutConstraints(1, 0, 2, 1));
		this.contentPanel.add(new JLabel("Hello 1[0, 1]"), new GridLayoutConstraints(0, 1));
		
		this.contentPanel.add(new JLabel("Fit 1[F, F]"), new GridLayoutConstraints(GridLayoutConstraints.FIRST_FIT, GridLayoutConstraints.FIRST_FIT));
		this.contentPanel.add(new JLabel("Fit 2[F, F]"), new GridLayoutConstraints(GridLayoutConstraints.FIRST_FIT, GridLayoutConstraints.FIRST_FIT));
		this.contentPanel.add(new JLabel("Fit 3[F, F]"), new GridLayoutConstraints(GridLayoutConstraints.FIRST_FIT, GridLayoutConstraints.FIRST_FIT));
		this.contentPanel.add(new JLabel("Fit 4[F, F]"), new GridLayoutConstraints(GridLayoutConstraints.FIRST_FIT, GridLayoutConstraints.FIRST_FIT));
		this.contentPanel.add(new JLabel("Fit 5[F, F]"), new GridLayoutConstraints(GridLayoutConstraints.FIRST_FIT, GridLayoutConstraints.FIRST_FIT));
		this.contentPanel.add(new JLabel("Fit 6[F, F]"), new GridLayoutConstraints(GridLayoutConstraints.FIRST_FIT, GridLayoutConstraints.FIRST_FIT));
		this.contentPanel.add(new JLabel("Fit 7[F, F]"), new GridLayoutConstraints(GridLayoutConstraints.FIRST_FIT, GridLayoutConstraints.FIRST_FIT));
		this.contentPanel.add(new JLabel("Fit 8[F, F]"), new GridLayoutConstraints(GridLayoutConstraints.FIRST_FIT, GridLayoutConstraints.FIRST_FIT));
		this.contentPanel.add(new JLabel("Fit 9[F, F]"), new GridLayoutConstraints(GridLayoutConstraints.FIRST_FIT, GridLayoutConstraints.FIRST_FIT));
		this.contentPanel.add(new JLabel("Fit 10[F, F]"), new GridLayoutConstraints(GridLayoutConstraints.FIRST_FIT, GridLayoutConstraints.FIRST_FIT));
	}
	
	public void add(ImagePanel image) {
		this.contentPanel.add(image.getContentPanel(), image.getConstrains());
	}
	
	public JPanel getContentPanel() {
		return this.contentPanel;
	}
}
