package com.gmail.xfrednet.worldgentest.helper;

import java.awt.image.BufferedImage;

public class ImageScaleHelper {

	private ImageScaleHelper() {}

	public static BufferedImage ScaleLinearUp(BufferedImage srcImg, int dstW, int dstH, int imgType) {
		int srcW = srcImg.getWidth();
		int srcH = srcImg.getHeight();
		BufferedImage dstImg = new BufferedImage(dstW, dstH, imgType);
		
		float xRatio = ((float)(srcW - 1)) / (float)dstW;
		float yRatio = ((float)(srcH - 1)) / (float)dstH;
		int c00, c10, c01, c11;
		int dstX;
		for (int dstY = 0; dstY < dstH; dstY++) {
			int srcY  = (int)(yRatio * dstY);
			float yDiff = (yRatio * dstY) - (float)srcY;
			
			for (dstX = 0; dstX < dstW; dstX++) {
				int srcX = (int)(xRatio * dstX);
				float xDiff = (xRatio * dstX) - (float)srcX;
				
				c00 = srcImg.getRGB(srcX    , srcY    );
				c10 = srcImg.getRGB(srcX + 1, srcY    );
				c01 = srcImg.getRGB(srcX    , srcY + 1);
				c11 = srcImg.getRGB(srcX + 1, srcY + 1);
				
				int result = ImageHelper.Color.MixColor(
						c00, (1.0f - xDiff) * (1.0f - yDiff),
						c01, (1.0f - xDiff) * (yDiff),
						c10, (xDiff) * (1.0f - yDiff),
						c11, (xDiff) * (yDiff));
				
				dstImg.setRGB(dstX, dstY, result);
			}
		}
		
		return dstImg;
	}
	
}
