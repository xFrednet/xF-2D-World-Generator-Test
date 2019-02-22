package com.gmail.xfrednet.worldgentest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mapgenerators.RandomNoiseMapGenerator;

public class Main {
	
	private static int MAP_SIZE = 128;
	private static int SHOWCASE_SCALE = 2;
	private static int GRID_WIDTH = 1;
	private static int GRID_HEIGHT = 1;
	
	JFrame guiFrame;
	JPanel guiShowcasePanel;
	
	public Main() {
		initFrame();
	}
	private void initFrame() {
		this.guiFrame = new JFrame("xFrednet's 2D world generation test");
		this.guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.guiFrame.setResizable(false);
		this.guiFrame.setLocation(100, 100);
		
		//TODO add layout
		this.guiFrame.setLayout(new BorderLayout());
		this.guiFrame.add(initShowcasePanel(), BorderLayout.CENTER);
		this.guiFrame.add(createMenuPanel(), BorderLayout.PAGE_END);
		
		this.guiFrame.pack();
		this.guiFrame.setVisible(true);
	}
	private JPanel createMenuPanel() {
		JPanel menuPanel = new JPanel();
		
		JButton regenButton = new JButton("Regenerate");
		regenButton.addActionListener(l -> {
			System.out.println("Button[Regenerate]: pressed");
			this.guiShowcasePanel.removeAll();
			new RandomNoiseMapGenerator().generateMap(MAP_SIZE, Main.this);
		});
		menuPanel.add(regenButton);
		
		JButton oButton = new JButton("O");
		oButton.addActionListener(l -> {
			System.out.println("Button[O         ]: You found an easteregg well an eastero");
		});
		menuPanel.add(oButton);
		
		return menuPanel;
	}
	private JPanel initShowcasePanel() {
		this.guiShowcasePanel = new JPanel();
		this.guiShowcasePanel.setLayout(new GridLayout(GRID_WIDTH, GRID_HEIGHT, 5, 5));
		
		return this.guiShowcasePanel;
	}
	
	public void addImage(Image img, String title) {
		JPanel conPanel = new JPanel();
		conPanel.setLayout(new BoxLayout(conPanel, BoxLayout.Y_AXIS));
		conPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(0xacacac)), 
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		JLabel image = new JLabel(new ImageIcon(img.getScaledInstance(
				img.getWidth(null) * SHOWCASE_SCALE, 
				img.getHeight(null) * SHOWCASE_SCALE,
				Image.SCALE_DEFAULT)));
		image.setAlignmentX(Component.CENTER_ALIGNMENT);
		conPanel.add(image);
		
		JLabel titleLabel = new JLabel(title);
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		conPanel.add(titleLabel);
		
		this.guiShowcasePanel.add(conPanel);
		this.guiFrame.pack();
	}
	
	public static void main(String[] args) {
		Main me = new Main();
		
		new RandomNoiseMapGenerator().generateMap(MAP_SIZE, me);
	}

}
