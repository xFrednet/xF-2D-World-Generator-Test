package com.gmail.xfrednet.worldgentest.biomegen;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

import com.gmail.xfrednet.worldgentest.Main;
import com.gmail.xfrednet.worldgentest.MapGenerator;

public class BiomeNeighborDiff extends MapGenerator {

	private static final float SEED_START = 1.0f;
	private static final float SEED_RANGE = 0.5f;
	private static final float SEED_CHANGE = 0.2f;
	
	
	public BiomeNeighborDiff() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateMap(int size, Main main) {
		BufferedImage img = seedOnce(new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB), size);
		//TODO main.addImage(img, "Hello from the other side!");
		
	}
	
	private BufferedImage seedOnce(BufferedImage img, int size) {
		LinkedList<SeedNode> waiting = new LinkedList<>();
		LinkedList<SeedNode> knownNodes = new LinkedList<>();
		
		Random r = new Random();
		
		waiting.add(new SeedNode(r.nextInt(size), r.nextInt(size), SEED_START));
		
		SeedNode node = null;
		while (
				!waiting.isEmpty() && 
				(node = waiting.remove()) != null) {
			
			if (node.value >= SEED_RANGE &&
					!knownNodes.contains(node) &&
					node.inside(size)) {
				
				
				waiting.add(new SeedNode(
						node.x + 0,
						node.y - 1,
						node.value - SEED_CHANGE * r.nextFloat()));
				waiting.add(new SeedNode(
						node.x + 0,
						node.y + 1,
						node.value - SEED_CHANGE * r.nextFloat()));
				waiting.add(new SeedNode(
						node.x - 1,
						node.y + 0,
						node.value - SEED_CHANGE * r.nextFloat()));
				waiting.add(new SeedNode(
						node.x + 1,
						node.y + 0,
						node.value - SEED_CHANGE * r.nextFloat()));
				img.setRGB(node.x, node.y, 0xff00ff);
			}
			
			knownNodes.add(node);
		}
		
		return img;
	}
	
	class SeedNode {
		int x;
		int y;
		public float value;
		
		SeedNode(int x, int y, float value) {
			this.x = x;
			this.y = y;
			this.value = value;
		}
		
		public boolean inside(int size) {
			return (this.x >= 0 && this.x < size) &&
					(this.y >= 0 && this.y < size);
		}
		
		public boolean equals(Object o) {
			if (o instanceof SeedNode) {
				SeedNode n = (SeedNode)o;
				return this.x == n.x &&
						this.y == n.y &&
						this.value == n.value;
			}
			
			return false;
		}
	}
}
