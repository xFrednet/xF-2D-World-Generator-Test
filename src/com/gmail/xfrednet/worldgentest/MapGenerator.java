package com.gmail.xfrednet.worldgentest;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;

public abstract class MapGenerator {
	
	protected MapGenerator() {
		
	}

	abstract public void generateMap(int size, Main main);
	
	protected static Color MixColor(Color c1, float percentC1, Color c2, float percentC2) 
	{
		float r = c1.getRed()   * percentC1 + 
				c2.getRed()   * percentC2;
		float g = c1.getGreen() * percentC1 + 
				c2.getGreen() * percentC2;
		float b = c1.getBlue()  * percentC1 + 
				c2.getBlue()  * percentC2;
		float a = c1.getAlpha() * percentC1 + 
				c2.getAlpha() * percentC2;
		
		return new Color((int)r, (int)g, (int)b, (int)a);
	}
	protected static Color MixColor(
			Color c1, float percentC1, 
			Color c2, float percentC2,
			Color c3, float percentC3,
			Color c4, float percentC4) 
	{
		
		
		float r = c1.getRed()   * percentC1 + 
					c2.getRed()   * percentC2 + 
					c3.getRed()   * percentC3 + 
					c4.getRed()   * percentC4;
		float g = c1.getGreen() * percentC1 + 
					c2.getGreen() * percentC2 +
					c3.getGreen() * percentC3 +
					c4.getGreen() * percentC4;
		float b = c1.getBlue()  * percentC1 + 
					c2.getBlue()  * percentC2 +
					c3.getBlue()  * percentC3 +
					c4.getBlue()  * percentC4;
		float a = c1.getAlpha() * percentC1 + 
					c2.getAlpha() * percentC2 + 
					c3.getAlpha() * percentC3 +
					c4.getAlpha() * percentC4;
		
		return new Color((int)r, (int)g, (int)b, (int)a);
		// Please please let there be no bugs it's easy code but not clean NOT CLEAN
	}
	protected static BufferedImage CombineImages(
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
				Color c1 = new Color(src1.getRGB(x, y), true);
				Color c2 = new Color(src2.getRGB(x, y), true);
				dst.setRGB(x, y, MixColor(c1, src1Weight, c2, src2Weight).getRGB());
			}
		}
		
		
		return dst;
	}
	
	protected static BufferedImage ScaleImageUpLinear(BufferedImage srcImg, int dstW, int dstH, int imgType) {
		int srcW = srcImg.getWidth();
		int srcH = srcImg.getHeight();
		BufferedImage dstImg = new BufferedImage(dstW, dstH, imgType);
		
		float xRatio = ((float)(srcW - 1)) / (float)dstW;
		float yRatio = ((float)(srcH - 1)) / (float)dstH;
		Color c00, c10, c01, c11;
		int dstX;
		for (int dstY = 0; dstY < dstH; dstY++) {
			int srcY  = (int)(yRatio * dstY);
			float yDiff = (yRatio * dstY) - (float)srcY;
			
			for (dstX = 0; dstX < dstW; dstX++) {
				int srcX = (int)(xRatio * dstX);
				float xDiff = (xRatio * dstX) - (float)srcX;
				
				c00 = new Color(srcImg.getRGB(srcX    , srcY    ), true);
				c10 = new Color(srcImg.getRGB(srcX + 1, srcY    ), true);
				c01 = new Color(srcImg.getRGB(srcX    , srcY + 1), true);
				c11 = new Color(srcImg.getRGB(srcX + 1, srcY + 1), true);
				
				Color result = MixColor(
						c00, (1.0f - xDiff) * (1.0f - yDiff),
						c01, (1.0f - xDiff) * (yDiff),
						c10, (xDiff) * (1.0f - yDiff),
						c11, (xDiff) * (yDiff));
				
				dstImg.setRGB(dstX, dstY, result.getRGB());
			}
		}
		
		return dstImg;
	}
	
	protected static byte[] GetPxBuffer(BufferedImage image) {
		DataBuffer buffer = image.getRaster().getDataBuffer();
		
		if (buffer instanceof DataBufferByte) {
			return ((DataBufferByte)buffer).getData();
		}
		else {
			System.err.println("GetPxBuffer: The given BufferedImage doesn't");
			return null;
		}
	}
	
}
