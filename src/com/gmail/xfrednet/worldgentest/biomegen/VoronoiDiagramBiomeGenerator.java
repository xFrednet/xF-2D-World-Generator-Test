package com.gmail.xfrednet.worldgentest.biomegen;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import com.gmail.xfrednet.worldgentest.Main;
import com.gmail.xfrednet.worldgentest.MapGenerator;
import com.gmail.xfrednet.worldgentest.gui.IPresenter;
import com.gmail.xfrednet.worldgentest.gui.ShowcasePanel;
import com.gmail.xfrednet.worldgentest.math.Vec2;

public class VoronoiDiagramBiomeGenerator extends MapGenerator implements IPresenter {

	private static final int NODE_CHUNK_SIZE = 16;
	
	private VoronoiNode[][] nodes;
	private int size;
	
	
	@Override
	public void generateMap(int size, Main main) {
		this.size = size;
		
		createNodes(size);
		//TODO main.addImage(createNodes(size), "Node Positions");
		//TODO main.addImage(createNodeImage(), "heyo");
	}
	
	private BufferedImage createNodes(int size) {
		// Create Image for the return
		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		int[] pixel = super.GetPxBuffer(image);
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				if (x % NODE_CHUNK_SIZE == 0 || y % NODE_CHUNK_SIZE == 0)
					pixel[x + y * size] = 0xfffafafa;
				else 
					pixel[x + y * size] = 0xffffffff;
			}
		}
		
		// Cleanup
		this.nodes = new VoronoiNode[size][size];
		
		// Create nodes
		Random r = new Random();
		int chunkCount = size / NODE_CHUNK_SIZE;
		int chunkX;
		for (int chunkY = 0; chunkY < chunkCount; chunkY++) {
			for (chunkX = 0; chunkX < chunkCount; chunkX++) {
				int nodeX = chunkX * NODE_CHUNK_SIZE + r.nextInt(NODE_CHUNK_SIZE);
				int nodeY = chunkY * NODE_CHUNK_SIZE + r.nextInt(NODE_CHUNK_SIZE);
				
				VoronoiNode node = new VoronoiNode();
				node.Pos = new Vec2(nodeX, nodeY);
				node.Color = 0xff000000 | r.nextInt(0x00ffffff);
				
				this.nodes[chunkX][chunkY] = node;
				pixel[nodeX + nodeY * size] = 0xff000000;
			}
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
				for (VoronoiNode node : getRelevantNodes(x, y)) {
					if (node.Pos.distanceSqr(pxPos) < dst) {
						parent = node;
						dst = parent.Pos.distanceSqr(pxPos);
					}
				}
				
				if (dst == 0)
					px[x + y * size] = 0xff000000;
				else
					px[x + y * size] = parent.Color;
			}
		}
		
		return image;
	}

	private ArrayList<VoronoiNode> getRelevantNodes(int mapX, int mapY) {
		ArrayList<VoronoiNode> nodes = new ArrayList<VoronoiNode>();
		int baseChunkX = mapX / NODE_CHUNK_SIZE;
		int baseChunkY = mapY / NODE_CHUNK_SIZE;
		for (int yOffset = -1; yOffset <= 1; yOffset++) {
			int chunkY = baseChunkY + yOffset;
			if (chunkY < 0 || chunkY >= size / NODE_CHUNK_SIZE)
				continue;
			
			for (int xOffset = -1; xOffset <= 1; xOffset++) {
				int chunkX = baseChunkX + xOffset;
				if (chunkX < 0 || chunkX >= size / NODE_CHUNK_SIZE)
					continue;
				
				nodes.add(this.nodes[chunkX][chunkY]);
			}
		}
		return nodes;
	}

	@Override
	public String getName() {
		return "Vorono Diagram";
	}

	@Override
	public Dimension getPresentationGridSize() {
		return new Dimension(8, 2);
	}

	@Override
	public int getPresentationDefaultImageSize() {
		return 128;
	}

	@Override
	public void present(ShowcasePanel panel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JPanel getPresentationSettingsPanel() {
		return null;
	}
}

class VoronoiNode {
	
	public Vec2 Pos;
	int Color;
	
}