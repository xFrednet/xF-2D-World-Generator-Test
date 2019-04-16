package com.gmail.xfrednet.worldgentest.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
	
	private JFrame frame = null;
	private ShowcasePanel showPanel = null;
	private JPanel presentorPanel = null;
	
	private ArrayList<IPresenter> presenters = null;
	private IPresenter selectedPresenter = null;
	
	public MainFrame(ArrayList<IPresenter> presenters) {
		// Save Presenters
		this.presenters = ((presenters != null) ? presenters : new ArrayList<IPresenter>());

		// init the components
		initFrame();
		initMenuPanel();

		if (this.presenters.size() != 0) {
			selectPresenter(this.presenters.get(0));
		}
		
		// finish init
		this.frame.pack();
		this.frame.setVisible(true);
	}

	private void initMenuPanel() {
		JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		menuPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(0xacacac)),
				BorderFactory.createEmptyBorder(4, 4, 4, 4)));
		
		// Regenerate Button
		JButton regenButton = new JButton("Regenerate");
		regenButton.addActionListener(l -> {
			if (selectedPresenter != null) {
				showPanel.getContentPanel().removeAll();
				selectedPresenter.present(showPanel);
			}
		});
		menuPanel.add(regenButton);
		
		// Presenter selector
		String[] presentorNames = new String[this.presenters.size()];
		for (int index = 0; index < presentorNames.length; index++) {
			presentorNames[index] = this.presenters.get(index).getName();
		}
		JComboBox<String> selector = new JComboBox<>(presentorNames);
		selector.addItemListener(l -> {
			if (l.getStateChange() == ItemEvent.SELECTED) {
				selectPresenter((String)l.getItem());
			}
		});
		menuPanel.add(selector);
		
		this.frame.add(menuPanel, BorderLayout.PAGE_START);
	}

	private void initFrame() {
		this.frame = new JFrame("No show today, sorry!");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);
		this.frame.setLocation(100, 100);
		this.frame.setLayout(new BorderLayout());
	}
	
	private void selectPresenter(String presenterName) {
		for (IPresenter presenter : this.presenters) {
			if (presenter.getName().equals(presenterName)) {
				selectPresenter(presenter);
				return;
			}
		}
	}
	private void selectPresenter(IPresenter presenter) {
		this.selectedPresenter = presenter;
		
		updateTitle();
		setupShowcasePanel();
		updatePresentorsPanel();
		
		this.frame.pack();
	}
	
	private void updateTitle() {
		int titleNr = new Random().nextInt(TITLES.length);
		this.frame.setTitle(String.format(
				TITLES[titleNr], 
				((selectedPresenter != null) ? selectedPresenter.getName() : "[HELLO MY NAME IS DUCK]")));
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
		
		if (gridSize != null) {
			showPanel = new ShowcasePanel(gridSize.width, gridSize.height, cellSize.height);
			this.frame.add(showPanel.getContentPanel(), BorderLayout.CENTER);
		}
	}
	private void updatePresentorsPanel() {
		if (this.presentorPanel != null) {
			this.frame.remove(this.presentorPanel);
		}
		
		if (this.selectedPresenter == null) {
			return;
		}
		
		this.presentorPanel = this.selectedPresenter.getPresentationsPanel();
		if (this.presentorPanel != null) {
			if (this.presentorPanel.getBorder() == null) {
				this.presentorPanel.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(new Color(0xacacac)),
						BorderFactory.createEmptyBorder(4, 4, 4, 4)));
			}
			
			this.frame.add(this.presentorPanel, BorderLayout.PAGE_END);
		}
	}
	
	public ShowcasePanel getShowcasePanel() {
		return this.showPanel;
	}
	
	public void addPresenter(IPresenter presenter) {
		this.presenters.add(presenter);
		selectPresenter(presenter);
	}
}
