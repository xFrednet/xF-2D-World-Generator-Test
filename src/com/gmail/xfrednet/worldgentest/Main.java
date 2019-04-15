package com.gmail.xfrednet.worldgentest;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import com.gmail.xfrednet.worldgentest.biomegen.VoronoiDiagramBiomeGenerator;
import com.gmail.xfrednet.worldgentest.gui.IPresenter;
import com.gmail.xfrednet.worldgentest.gui.ImagePanel;
import com.gmail.xfrednet.worldgentest.gui.MainFrame;

public class Main {
	
	public static int DEFAULT_IMAGE_SCALE = 1;
	private static int DEFAULT_MAP_SIZE = 128;
	
	public static void main(String[] args) {
		ArrayList<IPresenter> list = new ArrayList<IPresenter>();
		list.add(new VoronoiDiagramBiomeGenerator());
		MainFrame frame = new MainFrame(list);
		
		ImagePanel panel = new ImagePanel(testNoiseFactory(null), "Hello");
		frame.getShowcasePanel().add(panel);
		frame.getShowcasePanel().add(new ImagePanel(testNoiseFactory(null), "[1 0]"));
		frame.getShowcasePanel().add(new ImagePanel(testNoiseFactory(null), "[0 1]"));
		frame.getShowcasePanel().add(new ImagePanel(testNoiseFactory(null), "[1 1]"));
		
		try {
			for (int loop = 0; loop < 10; loop++) {
				Thread.sleep(1000);
				panel.updateImage(testNoiseFactory(null));
				frame.getShowcasePanel().getContentPanel().repaint();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
