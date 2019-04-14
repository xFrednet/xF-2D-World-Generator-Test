package com.gmail.xfrednet.worldgentest.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

public class MainFrame {

	// You got a good locking main frame. Don't search for that, well actually do do that!
	
	private static final String[] TITLES = {
			"I present you the one and only %s",
			"%s the future of IPresenter",
			"Invest now in %s",
			"Hey %s, what was your name again?",
			"Current presenter %s",
			"00101010 01101011 %s 01001011 11001011",
			"%s 4 president",
			"I <3 %s"
	};
	
	JFrame frame;
	ShowcasePanel showPanel;
	
	private ArrayList<IPresenter> presenters = new ArrayList<IPresenter>();
	private IPresenter selectedPresenter = null;
	
	public MainFrame(ArrayList<IPresenter> presenters) {
		// init the components
		initFrame();
		initMenuPanel();
		
		// Save Presenters
		if (presenters == null) {
			presenters = new ArrayList<IPresenter>();
		}
		
		this.presenters = presenters;
		if (this.presenters.size() != 0) {
			selectPresenter(this.presenters.get(0));
		}
		
		
		// finish init
		this.frame.pack();
		this.frame.setVisible(true);
	}

	private void initMenuPanel() {
		// TODO Auto-generated method stub
		
	}

	private void setupShowcasePanel() {
		if (this.selectedPresenter == null) {
			return;
		}
		
		if (this.showPanel != null) {
			this.frame.remove(this.showPanel.getContentPanel());
		}
		
		Dimension gridSize = this.selectedPresenter.getPresentationGridSize();
		Dimension cellSize = ImagePanel.GetPreferredSize(this.selectedPresenter.getPresentationDefaultImageSize());
		
		showPanel = new ShowcasePanel(gridSize.width, gridSize.height, cellSize.height);
		this.frame.add(showPanel.getContentPanel(), BorderLayout.CENTER);
	}

	private void initFrame() {
		this.frame = new JFrame("No show today, sorry!");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);
		this.frame.setLocation(100, 100);
		this.frame.setLayout(new BorderLayout());
	}
	
	private void selectPresenter(IPresenter presenter) {
		this.selectedPresenter = presenter;
		updateTitle();
		setupShowcasePanel();
	}
	
	private void updateTitle() {
		int titleNr = new Random().nextInt(TITLES.length);
		this.frame.setTitle(String.format(
				TITLES[titleNr], 
				((selectedPresenter != null) ? selectedPresenter.getName() : "[HELLO MY NAME IS DUCK]")));
	}
	
	public ShowcasePanel getShowcasePanel() {
		return this.showPanel;
	}
	
	public void addPresenter(IPresenter presenter) {
		this.presenters.add(presenter);
		selectPresenter(presenter);
	}
}
