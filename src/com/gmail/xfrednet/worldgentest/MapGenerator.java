package com.gmail.xfrednet.worldgentest;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;

public abstract class MapGenerator {
	
	protected MapGenerator() {
		
	}

	abstract public void generateMap(int size, Main main);
	
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
