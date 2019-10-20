package fr.stevecohen.hanoi.gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import fr.stevecohen.eventBus.EventBus;

public class InfoGamePanel extends JPanel {

	private static final long serialVersionUID = -1732843732096464288L;
	private EventBus eventBus = EventBus.getEventBus();
	private int movementCount = 0;
	private int level = GraphicConstants.NUMBER_OF_PIECES;
	private static final String line1Txt = "Level : %d";
	private static final String line2Txt = "Steps : %d";
	
	private JLabel line1;
	private JLabel line2;
	private JLabel line3;
	
	public InfoGamePanel() {
		setLayout(new GridLayout(10, 1));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		line1 = new JLabel(line1Txt.replace("%d", String.valueOf(level)));
		line2 = new JLabel(line2Txt.replace("%d", String.valueOf(movementCount)));
		line3 = new JLabel();
		add(line1);
		add(line2);
		add(line3);
		
		eventBus.on("pieceMove", new EventBus.EventCallback() {

			@Override
			public void call(Object argument) {
				movementCount++;
				line2.setText(line2Txt.replace("%d", String.valueOf(movementCount)));
			}
		});
		eventBus.on("gameStart", new EventBus.EventCallback() {

			@Override
			public void call(Object argument) {
				movementCount = 0;
			}
		});
	}
}
