package fr.stevecohen.hanoi.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import fr.stevecohen.eventBus.EventBus;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -3913685059982470374L;
	private EventBus eventBus = EventBus.getEventBus();
	private long startTime;
	private int movementCount = 0;
	private boolean tickLoop = true;

	public MainFrame(String name) {
		super(name);
		this.setMinimumSize(new Dimension(550, 250));
	}

	public void init()  {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGui();
			}
		});
	}

	public void createAndShowGui() {
		MainPanel gamePanel = new MainPanel();
		JPanel parentPanel = new JPanel();
		parentPanel.setLayout(new BorderLayout(10, 0));
		parentPanel.add(gamePanel, BorderLayout.CENTER);
		
		RightPanel rightPanel = new RightPanel();
		parentPanel.add(rightPanel, BorderLayout.EAST);
		setContentPane(parentPanel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		eventBus.on("gameStart", new EventBus.EventCallback() {

			@Override
			public void call(Object argument) {
				startTime = System.currentTimeMillis();
				launchTimer();
				movementCount = 0;
			}
		});


		eventBus.on("gameEnd", new EventBus.EventCallback() {

			@Override
			public void call(Object argument) {
				String timeFormatted = getTimeWithMillis(System.currentTimeMillis() - startTime, true);
				JOptionPane.showMessageDialog(MainFrame.this, "Congratulation you have resolved the Hanoi tower !\n"
						+ "Level: " + GraphicConstants.NUMBER_OF_PIECES + "\n"
								+ "Time: " + timeFormatted + "\n"
										+ "Steps: " + movementCount);
				System.out.println("CONGRATULATION YOU HAVE RESOLVED THE HANOI TOWER !");
				System.out.println("LEVEL : " + GraphicConstants.NUMBER_OF_PIECES);
				System.out.println("TIME : " + timeFormatted);
				System.out.println("STEPS : " + movementCount);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.exit(0);
			}
		});

		eventBus.on("pieceMove", new EventBus.EventCallback() {

			@Override
			public void call(Object argument) {
				movementCount++;
			}
		});
		setVisible(true);
	}

	protected void launchTimer() {
		eventBus.on("gameEnd", new EventBus.EventCallback() {

			@Override
			public void call(Object argument) {
				tickLoop = false;
			}
		});
		new Thread() {
			@Override
			public void run() {
				while (tickLoop) {
					eventBus.dispatch("tick", getTimeWithMillis(System.currentTimeMillis() - startTime, false));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	public String getTimeWithMillis(long timeMillis, boolean advanced) {
		long time = timeMillis / 1000;  
		String millis = Integer.toString((int)(timeMillis % 1000));
		String seconds = Integer.toString((int)(time % 60));  
		String minutes = Integer.toString((int)((time % 3600) / 60));  
		String hours = Integer.toString((int)(time / 3600));  
		for (int i = 0; i < 2; i++) {  
			if (seconds.length() < 2) {  
				seconds = "0" + seconds;
			}  
			if (minutes.length() < 2) {  
				minutes = "0" + minutes;  
			}  
			if (hours.length() < 2) {  
				hours = "0" + hours;  
			}  
		}
		for (int i = 0; i < 3; i++) {
			if (millis.length() < 3)
				millis = "0" + millis;  
		}
		return hours + ":" + minutes + ":" + seconds + (advanced ? (":" + millis) : "");
	}
}
