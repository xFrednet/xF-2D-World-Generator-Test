package com.gmail.xfrednet.worldgentest.helper;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;

public class ImageHelper {

	public static final int A_MASK = 0xff000000;
	public static final int R_MASK = 0x00ff0000;
	public static final int G_MASK = 0x0000ff00;
	public static final int B_MASK = 0x000000ff;
	public static final int A_SHIFT = 24;
	public static final int R_SHIFT = 16;
	public static final int G_SHIFT = 8;
	
	private ImageHelper() {}

	public static class Color {
		
		public static int MixColor(
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
		
		public static int MixColor(
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
	}
	
	public static class Pixel {
		
		public static int[] GetPxBuffer(BufferedImage image) {
			DataBuffer buffer = image.getRaster().getDataBuffer();
			
			if (buffer instanceof DataBufferInt) {
				return ((DataBufferInt)buffer).getData();
			}
			else {
				System.err.println("GetPxBuffer: The given BufferedImage has a different DataBuffer type");
				return null;
			}
		}
	}
}
