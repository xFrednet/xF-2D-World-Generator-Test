package com.gmail.xfrednet.worldgentest.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gmail.xfrednet.worldgentest.Main;
import com.gmail.xfrednet.worldgentest.gui.Layout.GridLayoutConstraints;

public class ImagePanel {
	
	public static final int POSITION_FIRST_FIT = -1;
	public static final int DEFAULT_PADDING = 5;
	
	int imageScale;
	int gridX;
	int gridY;
	JPanel contentPanel;
	JLabel imageLabel;
	JLabel titleLabel;
	
	public ImagePanel(Image img, String title) {
		this(img, title, Main.DEFAULT_IMAGE_SCALE);
	}
	public ImagePanel(Image img, String title, int imageScale) {
		this(img, title, Main.DEFAULT_IMAGE_SCALE, POSITION_FIRST_FIT, POSITION_FIRST_FIT);
	}
	public ImagePanel(Image img, String title, int imageScale, int gridX, int gridY) {
		this.imageScale = imageScale;
		this.gridX = gridX;
		this.gridY = gridY;
		
		initContent(img, title);
	}
	
	private void initContent(Image image, String title) {
		// create Panel
		this.contentPanel = new JPanel();
		this.contentPanel.setLayout(new BoxLayout(this.contentPanel, BoxLayout.Y_AXIS));
		this.contentPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(0xacacac)), 
				BorderFactory.createEmptyBorder(DEFAULT_PADDING - 1, DEFAULT_PADDING - 1, DEFAULT_PADDING - 1, DEFAULT_PADDING - 1)));
		
		// Add Image
		this.imageLabel = new JLabel(new ImageIcon(image.getScaledInstance(
				image.getWidth(null) * this.imageScale, 
				image.getHeight(null) * this.imageScale,
				Image.SCALE_DEFAULT)));
		this.imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.contentPanel.add(this.imageLabel);
		
		// Add Title
		this.titleLabel = new JLabel(title);
		this.titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.contentPanel.add(this.titleLabel);
	}
	
	public JPanel getContentPanel() {
		return this.contentPanel;
	}
	
	GridLayoutConstraints getConstrains() {
		return new GridLayoutConstraints(this.gridX, this.gridY, this.imageScale, this.imageScale);
	}
	
	public void updateImage(Image image) {
		this.imageLabel.setIcon(new ImageIcon(image.getScaledInstance(
				image.getWidth(null) * this.imageScale, 
				image.getHeight(null) * this.imageScale,
				Image.SCALE_DEFAULT)));
	}
	
	public void updateTitle(String text) {
		this.titleLabel.setText(text);
	}
	
	public static Dimension GetPreferredSize(int imageSize) {
		Dimension size = new Dimension();
		
		// width
		size.width = DEFAULT_PADDING * 2 + imageSize;
		
		// height
		size.height = DEFAULT_PADDING * 2 + imageSize;
		// TODO xFrednet 10.04.2019: change this to use a FontMatrix 
		size.height += new JLabel("ABCDEFGHIJKLMNOPQRSTUVWXYZ").getPreferredSize().height;
		
		return size;
	}
}
