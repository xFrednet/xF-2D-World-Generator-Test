package com.gmail.xfrednet.worldgentest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gmail.xfrednet.worldgentest.biomegen.BiomeNeighborDiff;
import com.gmail.xfrednet.worldgentest.biomegen.VoronoiDiagramBiomeGenerator;
import com.gmail.xfrednet.worldgentest.mapgenerators.RandomNoiseMapGenerator;

public class Main {
	
	private static int MAP_SIZE = 128;
	private static int SHOWCASE_SCALE = 4;
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
			new VoronoiDiagramBiomeGenerator().generateMap(MAP_SIZE, this);
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
		
		GridLayout layout = (GridLayout)this.guiShowcasePanel.getLayout();
		
		this.guiShowcasePanel.add(conPanel);
		this.guiFrame.pack();
	}
	
	public static void main(String[] args) {
		Main me = new Main();
		//me.testNoiseFactory();
		//new RandomNoiseMapGenerator().generateMap(MAP_SIZE, me);
		//new BiomeNeighborDiff().generateMap(MAP_SIZE, me);
	}
	
	private void testNoiseFactory() {
		int count = 0;
		do {
			float[] f1 = ImageNoiseFactory.Perlin.GenerateNoise(
					128, 
					8, 
					ImageNoiseFactory.GenRandFloatNoise(128, -1, 1, new Random()),
					0.75f);
			
			// Sigmoid, why, because I can ;P
			for (int index = 0; index < f1.length; index++) {
				float exp = (float)Math.exp((double)f1[index]);
				
				f1[index] = (exp) / (exp + 1); 
			}
			
			byte[] m1 = ImageNoiseFactory.FToB(
					f1);
			
			
			long sum = 0;
			int[] c = new int[128 * 128];
			for (int index = 0; index < 128 * 128; index++) {
				
				sum += m1[index] & 0x00000000000000ff;
				
				c[index] = 0xff000000;
				c[index] |= (m1[index] & 0x000000ff);
				c[index] |= (m1[index] & 0x000000ff) << 8;
				c[index] |= (m1[index] & 0x000000ff) << 16;
			}
			System.out.println("The sum of [" + 1 + "] is " + sum + " Avg. " + (sum / (128 * 128)));
			
			BufferedImage img = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
			img.setRGB(0, 0, 128, 128, c, 0, 128);
			addImage(img, "Nice to see you");
		} while (count++ < 4);
	}
	
}
