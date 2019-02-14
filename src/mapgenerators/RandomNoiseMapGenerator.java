package mapgenerators;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.gmail.xfrednet.worldgentest.Main;
import com.gmail.xfrednet.worldgentest.MapGenerator;

public class RandomNoiseMapGenerator extends MapGenerator {

	private static int RAND_MAP_SCALE = 8;
	
	public RandomNoiseMapGenerator() {
		
	}
	
	public void generateMap(int size, Main main) {
		BufferedImage randMap = createRandMap(size);
		main.addImage(randMap, "Random Map");
		
		Image scaledMap = smoothScaleMap(randMap, size);
		main.addImage(scaledMap, "Scaled Random Map");
	}
	
	private BufferedImage createRandMap(int size) {
		BufferedImage image = new BufferedImage(
				size / RAND_MAP_SCALE, 
				size / RAND_MAP_SCALE, 
				BufferedImage.TYPE_BYTE_GRAY);
		byte[] px = GetPxBuffer(image);
		
		Random r = new Random();
		for (int index = 0; index < px.length; index++) {
			px[index] = (byte)r.nextInt(255);
		}
		
		return image;
	}
	
	private Image smoothScaleMap(BufferedImage img, int outSize) {
		BufferedImage outImg = new BufferedImage(outSize, outSize, BufferedImage.TYPE_BYTE_GRAY);
		int srcSize = (outSize / RAND_MAP_SCALE);
		
		byte[] srcBuf = GetPxBuffer(img);
		byte[] outBuf = GetPxBuffer(outImg);
		
		float inc = 1.0f / (float)RAND_MAP_SCALE;
		float srcX = 0.0f;
		float srcY = -inc;
		for (int index = 0; index < outBuf.length; index++) {
			if (index % outSize != 0) {
				srcX += inc;
			} else {
				srcX = 0.0f;
				srcY += inc;
			}
			
			float proX = (srcX - (int)srcX);
			float proY = (srcY - (int)srcY);
			int x0 = (int)srcX;
			int y0 = (int)srcY * srcSize;
			int x1 = (x0 + 1 < srcSize) ? x0 + 1 : x0;
			int y1 = ((int)srcY + 1 < srcSize) ? y0 + srcSize : y0;
			
			byte pixel = 0;
			pixel += (proX 			* proY			) * srcBuf[x0 + y0];
			pixel += ((1.0f - proX) * proY			) * srcBuf[x1 + y0];
			pixel += (proX 			* (1.0f - proY)	) * srcBuf[x0 + y1];
			pixel += ((1.0f - proX) * (1.0f - proY)	) * srcBuf[x1 + y1];
			outBuf[index] = pixel; 
		}
		
		return outImg;
	}
}
