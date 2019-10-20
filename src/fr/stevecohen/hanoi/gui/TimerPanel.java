package fr.stevecohen.hanoi.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import fr.stevecohen.eventBus.EventBus;

public class TimerPanel extends JPanel {
	private static final long serialVersionUID = 286647007199562448L;
	private EventBus eventBus = EventBus.getEventBus();
	private char[] timer = "00:00:00".toCharArray();
	
	public TimerPanel() {
		eventBus.on("tick", new EventBus.EventCallback() {

			@Override
			public void call(Object argument) {
				timer = argument.toString().toCharArray();
				repaint();
			}
		});
		setBackground(Color.BLACK);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(this.getParent().getWidth(), 30);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.drawChars(timer, 0, timer.length, this.getWidth() / 2 - 50 / 2, 30 / 2 + 10 / 2);
	}

}
