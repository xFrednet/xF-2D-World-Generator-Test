package mapgenerators;

import java.awt.image.BufferedImage;
import java.util.Random;

import com.gmail.xfrednet.worldgentest.Main;
import com.gmail.xfrednet.worldgentest.MapGenerator;

public class RandomNoiseMapGenerator extends MapGenerator {

	private static int RAND_MAP_SCALE = 8;
	
	public RandomNoiseMapGenerator() {
		
	}
	
	public void generateMap(int size, Main main) {
		BufferedImage randMap1 = createRandMap(size);
		main.addImage(randMap1, "Random Map 1");
		BufferedImage randMap2 = createRandMap(size);
		main.addImage(randMap2, "Random Map 2");
		BufferedImage randMapCom = CombineImages(randMap1, 0.5f, randMap2, 0.5f, BufferedImage.TYPE_INT_ARGB);
		main.addImage(randMapCom, "Random combined map");
		main.addImage(smoothScaleMap(randMapCom, size), "combined & scaled");
		
		BufferedImage scaledMap1 = smoothScaleMap(randMap1, size);
		main.addImage(scaledMap1, "Scaled Random Map 1");
		BufferedImage scaledMap2 = smoothScaleMap(randMap2, size);
		main.addImage(scaledMap2, "Scaled Random Map 2");
		main.addImage(CombineImages(scaledMap1, 0.5f, scaledMap2, 0.5f, BufferedImage.TYPE_INT_ARGB), "Scaled combined");
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
