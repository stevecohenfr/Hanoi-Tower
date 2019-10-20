package fr.stevecohen.hanoi.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import fr.stevecohen.eventBus.EventBus;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int panelSize = 300;

	private EventBus eventBus = EventBus.getEventBus();
	private List<HanoiPiece> row1 = new ArrayList<HanoiPiece>();
	private List<HanoiPiece> row2 = new ArrayList<HanoiPiece>();
	private List<HanoiPiece> row3 = new ArrayList<HanoiPiece>();
	private HanoiPiece selectedPiece;
	private List<Rectangle> background = new ArrayList<Rectangle>();
	private Integer lastRowPos = null;
	private boolean gameStarted = false;
	
	public GamePanel() {
		initShapes();
		eventBus.on("keyPressed", new EventBus.EventCallback() {

			@Override
			public void call(Object argument) {
				if (gameStarted == false) {
					eventBus.dispatch("gameStart", null);
					gameStarted = true;
				}
				if (argument.toString().equals("right")) {
					if (!selectedPiece.isMoving())
						moveSelectorOnRight();
					else
						movePieceOnRight();
				}else if (argument.toString().equals("left")) {
					if (!selectedPiece.isMoving())
						moveSelectorOnLeft();
					else
						movePieceOnLeft();
				}else if (argument.toString().equals("enter")) {
					if (!selectedPiece.isMoving()) {
						lastRowPos = getParentRowIndex(selectedPiece);
						setMovingSelectedPiece();
					}else {
						tryToValidate();
					}
				}
			}
		});
	}

	public void tryToValidate() {
		List<HanoiPiece> row = getParentRow(selectedPiece);
		if ((row.size()-2 >= 0 && row.get(row.size()-2).getId() > selectedPiece.getId()) || row.size() == 1) { //VALIDATE
			selectedPiece.setMoving(false);
			eventBus.dispatch("pieceMove", String.valueOf(selectedPiece.getId()));
			for (int i = GraphicConstants.POSITION_MIN; i <= GraphicConstants.POSITION_MAX; i++) {
				if (i == GraphicConstants.START_ROW)
					continue;
				if (getRowByIndex(i).size() == GraphicConstants.NUMBER_OF_PIECES) {
					eventBus.dispatch("gameEnd", null);
				}
			}
		}else { //UNVALIDATE
			row.remove(selectedPiece);
			getRowByIndex(lastRowPos).add(selectedPiece);
			System.out.println("Mouvement invalide !");
		}
		this.repaint();
	}

	public void setMovingSelectedPiece() {
		selectedPiece.setMoving(true);
		this.repaint();
	}

	public void movePieceOnRight() {
		List<HanoiPiece> parentRow = getParentRow(selectedPiece);
		getRowOnRight(parentRow, true).add(selectedPiece);
		parentRow.remove(selectedPiece);
		this.repaint();
	}

	public void movePieceOnLeft() {
		List<HanoiPiece> parentRow = getParentRow(selectedPiece);
		getRowOnLeft(parentRow, true).add(selectedPiece);
		parentRow.remove(selectedPiece);
		this.repaint();
	}

	public void moveSelectorOnRight() {
		List<HanoiPiece> rowOnRight = getRowOnRight(getParentRow(selectedPiece), false);
		if (rowOnRight != null) {
			changeSelectedPiece(rowOnRight.get(rowOnRight.size()-1));
			this.repaint();
		}
	}

	public void moveSelectorOnLeft() {
		List<HanoiPiece> rowOnLeft = getRowOnLeft(getParentRow(selectedPiece), false);
		if (rowOnLeft != null) {
			changeSelectedPiece(rowOnLeft.get(rowOnLeft.size()-1));
			this.repaint();
		}
	}

	public void changeSelectedPiece(HanoiPiece newPiece) {
		if (selectedPiece != null)
			selectedPiece.setSelected(false);
		newPiece.setSelected(true);
		selectedPiece = newPiece;
	}

	public void initShapes() {
		for (int i = 0; i < GraphicConstants.POSITION_MAX; i++) {
			background.add(new GraphicRow(this, i+1));
		}
		List<HanoiPiece> row = getRowByIndex(GraphicConstants.START_ROW);
		for (int i = GraphicConstants.NUMBER_OF_PIECES; i > 0; i--) {
			row.add(new HanoiPiece(this, i));
		}
		List<HanoiPiece> startRow = getRowByIndex(GraphicConstants.START_ROW);
		changeSelectedPiece(startRow.get(startRow.size()-1));
	}

	public List<HanoiPiece> getParentRow(HanoiPiece piece) {
		if (row1.contains(piece)) {
			return row1;
		}else if (row2.contains(piece)) {
			return row2;
		}else if (row3.contains(piece)) {
			return row3;
		}
		return null;
	}

	public int getParentRowIndex(HanoiPiece piece) {
		if (row1.contains(piece)) {
			return 1;
		}else if (row2.contains(piece)) {
			return 2;
		}else if (row3.contains(piece)) {
			return 3;
		}
		return -1;
	}

	public List<HanoiPiece> getRowByIndex(int index) {
		if (index == 1) {
			return row1;
		}else if (index == 2) {
			return row2;
		}else if (index == 3) {
			return row3;
		}
		return null;
	}

	public List<HanoiPiece> getRowOnRight(List<HanoiPiece> row, boolean canBeEmpty) {
		if (row1.contains(selectedPiece)) {
			if (canBeEmpty)
				return row2;
			else { 
				if (!row2.isEmpty()) {
					return row2;
				} else if (!row3.isEmpty()) {
					return row3;
				}
			}
		}else if (row2.contains(selectedPiece)) {
			if (canBeEmpty)
				return row3;
			else { 
				if (!row3.isEmpty()) {
					return row3;
				} else if (!row1.isEmpty()) {
					return row1;
				}
			}
		}else if (row3.contains(selectedPiece)) {
			if (canBeEmpty)
				return row1;
			else { 
				if (!row1.isEmpty()) {
					return row1;
				} else if (!row2.isEmpty()) {
					return row2;
				}
			}
		}
		return null;
	}

	public List<HanoiPiece> getRowOnLeft(List<HanoiPiece> row, boolean canBeEmpty) {
		if (row3.contains(selectedPiece)) {
			if (canBeEmpty)
				return row2;
			else { 
				if (!row2.isEmpty()) {
					return row2;
				} else if (!row1.isEmpty()) {
					return row1;
				}
			}
		}else if (row2.contains(selectedPiece)) {
			if (canBeEmpty)
				return row1;
			else { 
				if (!row1.isEmpty()) {
					return row1;
				} else if (!row3.isEmpty()) {
					return row3;
				}
			}
		}else if (row1.contains(selectedPiece)) {
			if (canBeEmpty)
				return row3;
			else { 
				if (!row3.isEmpty()) {
					return row3;
				} else if (!row2.isEmpty()) {
					return row2;
				}
			}
		}
		return null;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(panelSize, panelSize);
	}

	private void paintBackground(Graphics g) {
		for (Rectangle rec : background) {
			g.setColor(((GraphicRow)rec).color);
			g.fillRect((int)rec.getX(), (int)rec.getY(), (int)rec.getWidth(), (int)rec.getHeight());
		}
	}

	private void paintRow(Graphics g, int index, List<HanoiPiece> row) {
		for (int i = 0; i < row.size(); i++) {
			HanoiPiece piece = row.get(i);
			g.setColor(piece.getColor());
			g.fillRect((int)piece.getX(index), (int)piece.getY(i), (int)piece.getWidth(), (int)piece.getHeight());
			if (piece.isMoving()) {
				g.setColor(Color.RED);
				g.drawRect((int)piece.getX(index), (int)piece.getY(i), (int)piece.getWidth(), (int)piece.getHeight());
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintBackground(g);
		paintRow(g, 1, row1);
		paintRow(g, 2, row2);
		paintRow(g, 3, row3);
	}
}