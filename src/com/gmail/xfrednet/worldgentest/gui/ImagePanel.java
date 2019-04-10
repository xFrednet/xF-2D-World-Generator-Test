package com.gmail.xfrednet.worldgentest.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gmail.xfrednet.worldgentest.Main;

public class ImagePanel {
	
	public static final int POSITION_FIRST_FIT = -1;
	
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
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
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
	
	JPanel getContentPanel() {
		return this.contentPanel;
	}
	
	GridBagConstraints getConstrains() {
		GridBagConstraints cons = new GridBagConstraints ();
		cons.gridwidth = this.imageScale;
		cons.gridheight = this.imageScale;
		cons.gridx = (this.gridX != POSITION_FIRST_FIT) ? this.gridX : GridBagConstraints.RELATIVE;
		cons.gridy = (this.gridY != POSITION_FIRST_FIT) ? this.gridY : GridBagConstraints.RELATIVE;
		
		return cons;
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
}
