package com.gmail.xfrednet.worldgentest.gui;

import java.awt.Dimension;

import javax.swing.JPanel;

public interface IPresenter {
	
	public String getName();
	
	// Size
	public Dimension getPresentationGridSize();
	
	public int getPresentationDefaultImageSize();
	
	// Present
	public void present(ShowcasePanel panel);
	
	// Other GUI
	public JPanel getPresentationSettingsPanel();
}
