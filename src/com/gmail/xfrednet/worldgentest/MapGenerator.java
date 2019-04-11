package com.gmail.xfrednet.worldgentest;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;

public abstract class MapGenerator {
	
	protected static final int A_MASK = 0xff000000;
	protected static final int R_MASK = 0x00ff0000;
	protected static final int G_MASK = 0x0000ff00;
	protected static final int B_MASK = 0x000000ff;
	protected static final int A_SHIFT = 24;
	protected static final int R_SHIFT = 16;
	protected static final int G_SHIFT = 8;
	
	protected MapGenerator() {
		
	}

	abstract public void generateMap(int size, Main main);
	
	protected static int MixColor(
			int c1, float percentC1,
			int c2, float percentC2) 
	{
		float a = 
				((c1 & A_MASK) >> A_SHIFT) * percentC1 +
				((c2 & A_MASK) >> A_SHIFT) * percentC2;
		float r = 
				((c1 & R_MASK) >> R_SHIFT) * percentC1 +
				((c2 & R_MASK) >> R_SHIFT) * percentC2;
		float g = 
				((c1 & G_MASK) >> G_SHIFT) * percentC1 +
				((c2 & G_MASK) >> G_SHIFT) * percentC2;
		float b = 
				(c1 & B_MASK) * percentC1 +
				(c2 & B_MASK) * percentC2;
		
		return (((int)a << A_SHIFT) |
				((int)r << R_SHIFT) |
				((int)g << G_SHIFT) |
				((int)b));
	}
	protected static int MixColor(
			int c1, float percentC1, 
			int c2, float percentC2,
			int c3, float percentC3,
			int c4, float percentC4) 
	{
		float a = 
				((c1 & A_MASK) >> A_SHIFT) * percentC1 +
				((c2 & A_MASK) >> A_SHIFT) * percentC2 +
				((c3 & A_MASK) >> A_SHIFT) * percentC3 +
				((c4 & A_MASK) >> A_SHIFT) * percentC4;
		float r = 
				((c1 & R_MASK) >> R_SHIFT) * percentC1 +
				((c2 & R_MASK) >> R_SHIFT) * percentC2 +
				((c3 & R_MASK) >> R_SHIFT) * percentC3 +
				((c4 & R_MASK) >> R_SHIFT) * percentC4;
		float g = 
				((c1 & G_MASK) >> G_SHIFT) * percentC1 +
				((c2 & G_MASK) >> G_SHIFT) * percentC2 +
				((c3 & G_MASK) >> G_SHIFT) * percentC3 +
				((c4 & G_MASK) >> G_SHIFT) * percentC4;
		float b = 
				(c1 & B_MASK) * percentC1 +
				(c2 & B_MASK) * percentC2 +
				(c3 & B_MASK) * percentC3 +
				(c4 & B_MASK) * percentC4;
		
		return (((int)a << A_SHIFT) |
				((int)r << R_SHIFT) |
				((int)g << G_SHIFT) |
				((int)b));
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
				dst.setRGB(x, y, MixColor(src1.getRGB(x, y), src1Weight, src2.getRGB(x, y), src2Weight));
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
				
				int result = MixColor(
						c00, (1.0f - xDiff) * (1.0f - yDiff),
						c01, (1.0f - xDiff) * (yDiff),
						c10, (xDiff) * (1.0f - yDiff),
						c11, (xDiff) * (yDiff));
				
				dstImg.setRGB(dstX, dstY, result);
			}
		}
		
		return dstImg;
	}
	
	protected static int[] GetPxBuffer(BufferedImage image) {
		DataBuffer buffer = image.getRaster().getDataBuffer();
		
		if (buffer instanceof DataBufferInt) {
			return ((DataBufferInt)buffer).getData();
		}
		else {
			System.err.println("GetPxBuffer: The given BufferedImage doesn't");
			return null;
		}
	}
	
}
