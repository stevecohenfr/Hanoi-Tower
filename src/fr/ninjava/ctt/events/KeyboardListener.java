package fr.ninjava.ctt.events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import fr.stevecohen.eventBus.EventBus;

public class KeyboardListener implements KeyListener {
	
	private EventBus eventBus = EventBus.getEventBus();

	public KeyboardListener() {
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			eventBus.dispatch("keyPressed", "right");
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			eventBus.dispatch("keyPressed", "left");
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
			eventBus.dispatch("keyPressed", "enter");
		}
	} 
}
