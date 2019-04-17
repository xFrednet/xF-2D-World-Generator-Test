package com.gmail.xfrednet.worldgentest.helper;

import java.awt.image.BufferedImage;

public class ImageCombineHelper {

	private ImageCombineHelper() {}

	public static BufferedImage MixImageColors(
			BufferedImage src1, float src1Weight, 
			BufferedImage src2, float src2Weight,
			int imgType) 
	{
		if (src1.getWidth() != src2.getWidth() ||
				src1.getWidth() != src2.getHeight())
			assert false;
		int srcW = src1.getWidth();
		int srcH = src1.getHeight();
		
		BufferedImage dst = new BufferedImage(srcW, srcH, imgType);
		
		int x;
		for (int y = 0; y < srcH; y++) {
			for (x = 0; x < srcW; x++) {
				dst.setRGB(
						x, 
						y, 
						ImageHelper.Color.MixColor(
								src1.getRGB(x, y), 
								src1Weight, 
								src2.getRGB(x, y), 
								src2Weight));
			}
		}
		
		
		return dst;
	}
	
}
