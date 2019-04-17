package com.gmail.xfrednet.worldgentest.factory;

import java.util.Random;

public class ImageNoiseFactory {

	private static final float PERL_DEFAULT_PERSISTANCE = 0.7f;
	
	private ImageNoiseFactory() {}

	public static float[] GenRandFloatNoise(int size, float min, float max, Random r) {
		float[] buffer = new float[size * size];
		float range = max - min;
		
		for (int index = 0; index < size * size; index++) {
			buffer[index] = (r.nextFloat() * range) + min;
		}
		
		return buffer;
	}
	
	static byte[] FToB(float[] fs) {
		byte[] bs = new byte[fs.length];
		int index = 0;
		int x;
		for (x = 0; x < fs.length; x++) {
			bs[index++] = (byte)(fs[x] * 255.0f);
		}
		return bs;
	}

	
	//#############################################################################################
	public static class Perlin {
		
		private static float Interpolate(float a0, float a1, float w) {
			return ((1.0f - w) * a0) + (w * a1);
		}
		private static float[] SmoothNoise(float[] baseNoise, int size, int octave) {
			float[] outNoise = new float[size * size];
			
			int samplePeriod = 1 << octave;
			float sampleFreq = 1.0f / samplePeriod;
			int x;
			for (int y = 0; y < size; y++) {
				int samY0 = (y / samplePeriod) * samplePeriod;
				int samY1 = (samY0 + samplePeriod) % size;
				float vertBlend = (y - samY0) * sampleFreq;
				
				for (x = 0; x < size; x++) {
					int samX0 = (x / samplePeriod) * samplePeriod;
					int samX1 = (samX0 + samplePeriod) % size;
					float horiBlend = (x - samX0) * sampleFreq;
					
					float top = Interpolate(baseNoise[samX0 + samY0 * size], baseNoise[samX1 + samY0 * size], horiBlend);
					float bot = Interpolate(baseNoise[samX0 + samY1 * size], baseNoise[samX1 + samY1 * size], horiBlend);
					outNoise[x + y * size] = Interpolate(top, bot, vertBlend);
				}
			}
			
			return outNoise;
		}
		public static float[] GenerateNoise(int size, int octaveCount) {
			float[] baseNoise = GenRandFloatNoise(size, 0.0f, 1.0f, new Random());
			
			return GenerateNoise(size, octaveCount, baseNoise, PERL_DEFAULT_PERSISTANCE);
		}
		public static float[] GenerateNoise(int size, int octaveCount, float[] baseNoise, float persistance) {	
			float[] perl = new float[size * size];
			
			float amp = 1.0f;
			float totalAmp = 0.0f;
			for (int oct = octaveCount - 1; oct > 0; oct--) {
				amp *= persistance;
				totalAmp += amp;
				
				float[] addNoise = SmoothNoise(baseNoise, size, oct);
				for (int index = 0; index < size * size; index++) {
					perl[index] += addNoise[index] * amp;
				}
				
			}
			
			for (int index = 0 ; index < size * size; index++) {
				perl[index] /= totalAmp;
			}
			
			return perl;
		}
	}	
}
