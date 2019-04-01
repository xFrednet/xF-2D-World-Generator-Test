package com.gmail.xfrednet.worldgentest.biomegen;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import com.gmail.xfrednet.worldgentest.Main;
import com.gmail.xfrednet.worldgentest.MapGenerator;
import com.gmail.xfrednet.worldgentest.math.Vec2;

public class VoronoiDiagramBiomeGenerator extends MapGenerator {

	private static final int NODE_COUNT = 128;
	
	private ArrayList<VoronoiNode> nodes = new ArrayList<>();
	private int size;
	
	
	@Override
	public void generateMap(int size, Main main) {
		this.size = size;
		
		createNodes();
		main.addImage(createBasicNodeImage(), "Node Positions");
		main.addImage(createNodeImage(), "heyo");
	}
	
	private void createNodes() {
		nodes.clear();
		
		Random r = new Random();
		for (int nodeNo = 0; nodeNo < NODE_COUNT; nodeNo++) {
			VoronoiNode node = new VoronoiNode();
			node.Pos = new Vec2(r.nextInt(size), r.nextInt(size));
			node.Color = 0xff000000 | r.nextInt(0x00ffffff);
			
			this.nodes.add(node);
		}
	}
	
	private BufferedImage createBasicNodeImage() {
		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		
		int[] px = GetPxBuffer(image);
		for (int index = 0; index < size * size; index++) {
			px[index] = 0xffffffff;
		}
		
		for (VoronoiNode node : this.nodes) {
			px[node.Pos.X + node.Pos.Y * size] = 0xff000000;
		}
		
		return image;
	}
	
	private BufferedImage createNodeImage() {
		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		
		int[] px = GetPxBuffer(image);
		int x = 0;
		for (int y = 0; y < size; y++) {
			for (x = 0; x < size; x++) {
				Vec2 pxPos = new Vec2(x, y);
				VoronoiNode parent = null;
				int dst = size * size;
				for (VoronoiNode node : this.nodes) {
					if (node.Pos.distanceSqr(pxPos) < dst) {
						parent = node;
						dst = parent.Pos.distanceSqr(pxPos);
					}
				}
				
				px[x + y * size] = parent.Color;
			}
		}
		for (int index = 0; index < size * size; index++) {
		}
		
		for (VoronoiNode node : this.nodes) {
			px[node.Pos.X + node.Pos.Y * size] = 0xff000000;
		}
		
		return image;
	}
}

class VoronoiNode {
	
	public Vec2 Pos;
	int Color;
	
}