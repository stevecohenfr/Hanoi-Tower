package fr.stevecohen.hanoi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class RightPanel extends JPanel {
	private static final long serialVersionUID = 3324179670345893896L;

	public RightPanel() {
		setLayout(new BorderLayout());
//		setBackground(Color.WHITE);
		setOpaque(false);
		setBorder(new LineBorder(Color.BLUE));
		
		TimerPanel timerPanel = new TimerPanel();
		add(timerPanel, BorderLayout.NORTH);
		
		InfoGamePanel infoGamePanel = new InfoGamePanel();
		add(infoGamePanel, BorderLayout.CENTER);
		
//		OtherPlayerPanel otherPlayerPanel = new OtherPlayerPanel();
//		add(otherPlayerPanel, BorderLayout.SOUTH);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension((int)(this.getParent().getWidth() / 7.5), this.getParent().getHeight());
	}

}
