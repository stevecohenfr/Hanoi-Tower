package fr.stevecohen.hanoi.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import fr.ninjava.ctt.events.KeyboardListener;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = -2495149716744051893L;

	public MainPanel() {
		setLayout(new BorderLayout(0, 0));
		
		GamePanel CenterPanel = new GamePanel();
		add(CenterPanel, BorderLayout.CENTER);
		setFocusable(true);
		requestFocus();
		addKeyListener(new KeyboardListener());
	}
	
	@Override
    public Dimension getPreferredSize() {
        return new Dimension(GraphicConstants.WINDOW_DEFAULT_WIDTH, GraphicConstants.WINDOW_DEFAULT_HEIGHT);
    }
	
}
