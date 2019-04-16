package com.gmail.xfrednet.worldgentest;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gmail.xfrednet.worldgentest.biomegen.VoronoiDiagramBiomeGenerator;
import com.gmail.xfrednet.worldgentest.gui.IPresenter;
import com.gmail.xfrednet.worldgentest.gui.ImagePanel;
import com.gmail.xfrednet.worldgentest.gui.MainFrame;
import com.gmail.xfrednet.worldgentest.gui.ShowcasePanel;

public class Main {
	
	public static int DEFAULT_IMAGE_SCALE = 1;
	private static int DEFAULT_MAP_SIZE = 128;
	
	public static void main(String[] args) {
		ArrayList<IPresenter> list = new ArrayList<>();
		list.add(new VoronoiDiagramBiomeGenerator());
		list.add(new TrashPresenter());
		MainFrame frame = new MainFrame(list);
	}
	
	private static Image testNoiseFactory(Main m) {
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
			//m.addImage(img, "Nice to see you");
			return img;
		} while (count++ < 4);		
	}
	
}

class TrashPresenter implements IPresenter {

	@Override
	public String getName() {
		return "Trashpanda";
	}

	@Override
	public Dimension getPresentationGridSize() {
		return null;
	}

	@Override
	public int getPresentationDefaultImageSize() {
		return 0;
	}

	@Override
	public void present(ShowcasePanel panel) {
	}

	@Override
	public JPanel getPresentationsPanel() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("I'm a piece of trash"));
		
		return panel;
	}
	
}