package com.gmail.xfrednet.worldgentest.mapgenerators;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JPanel;

import com.gmail.xfrednet.worldgentest.Main;
import com.gmail.xfrednet.worldgentest.MapGenerator;
import com.gmail.xfrednet.worldgentest.gui.IPresenter;
import com.gmail.xfrednet.worldgentest.gui.ImagePanel;
import com.gmail.xfrednet.worldgentest.gui.ShowcasePanel;

public class RandomNoiseMapGenerator extends MapGenerator implements IPresenter {

	private static int RAND_MAP_SCALE = 8;
	
	public RandomNoiseMapGenerator() {
		
	}

	@Override
	public String getName() {
		return "Random Noise Generator";
	}

	@Override
	public Dimension getPresentationGridSize() {
		return new Dimension(6, 4);
	}

	@Override
	public int getPresentationDefaultImageSize() {
		return 128;
	}

	@Override
	public void present(ShowcasePanel panel) {
		int size = getPresentationDefaultImageSize();
		
		BufferedImage randMap1 = createRandMap(size);
		panel.add(new ImagePanel(randMap1, "Map 1", 2));
		BufferedImage randMap2 = createRandMap(size);
		panel.add(new ImagePanel(randMap2, "Map 2", 2));
		BufferedImage randMapCom = CombineImages(randMap1, 0.5f, randMap2, 0.5f, BufferedImage.TYPE_INT_ARGB);
		panel.add(new ImagePanel(randMapCom, "Map 1 + Map 2", 2));
		
		BufferedImage scaledMap1 = smoothScaleMap(randMap1, size);
		panel.add(new ImagePanel(scaledMap1, "scaled Map 1", 2));
		BufferedImage scaledMap2 = smoothScaleMap(randMap2, size);
		panel.add(new ImagePanel(scaledMap2, "scaled Map 2", 2));
		BufferedImage scaledMapCom = CombineImages(scaledMap1, 0.5f, scaledMap2, 0.5f, BufferedImage.TYPE_INT_ARGB);
		panel.add(new ImagePanel(scaledMapCom, "scaled Map 1 + scaled Map 2", 2));
	}

	@Override
	public JPanel getPresentationsPanel() {
		return null;
	}
	
	public void generateMap(int size, Main main) {
		BufferedImage randMap1 = createRandMap(size);
		//main.addImage(randMap1, "Random Map 1");
		BufferedImage randMap2 = createRandMap(size);
		//main.addImage(randMap2, "Random Map 2");
		BufferedImage randMapCom = CombineImages(randMap1, 0.5f, randMap2, 0.5f, BufferedImage.TYPE_INT_ARGB);
		//main.addImage(randMapCom, "Random combined map");
		//main.addImage(smoothScaleMap(randMapCom, size), "combined & scaled");
		
		BufferedImage scaledMap1 = smoothScaleMap(randMap1, size);
		//main.addImage(scaledMap1, "Scaled Random Map 1");
		BufferedImage scaledMap2 = smoothScaleMap(randMap2, size);
		//main.addImage(scaledMap2, "Scaled Random Map 2");
		//main.addImage(CombineImages(scaledMap1, 0.5f, scaledMap2, 0.5f, BufferedImage.TYPE_INT_ARGB), "Scaled combined");
	}
	
	private BufferedImage createRandMap(int size) {
		BufferedImage image = new BufferedImage(
				size / RAND_MAP_SCALE, 
				size / RAND_MAP_SCALE, 
				BufferedImage.TYPE_INT_ARGB);
		int[] px = GetPxBuffer(image);
		
		Random r = new Random();
		for (int index = 0; index < px.length; index++) {
			px[index] = 0xff000000 | r.nextInt();
		}
		
		return image;
	}
	
	private BufferedImage smoothScaleMap(BufferedImage img, int outSize) {
		return ScaleImageUpLinear(img, outSize, outSize, BufferedImage.TYPE_INT_ARGB);
	}
}
