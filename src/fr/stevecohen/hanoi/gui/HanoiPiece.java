package fr.stevecohen.hanoi.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Rectangle;

public class HanoiPiece extends Rectangle {

	private static final long serialVersionUID = -8900008971182926463L;
	private Color color = Color.BLACK;
	
	private Container parent;
	private int id;
	private boolean selected;
	private boolean moving;
	
	public HanoiPiece(Container parent, int id) {
		this.id = id;
		this.parent = parent;
	}
	
	public int getId() {
		return id;
	}
	
	public Color getColor() {
		return selected ? Color.BLUE : color;
	}
	
	@Override
	public double getWidth() {
		return ((parent.getWidth() / 30) + ((parent.getWidth() / 30) * id));
	}
	
	@Override
	public double getHeight() {
		return parent.getWidth() / 50;
	}
	
	public double getX(int posX) {
		return ((parent.getWidth() / GraphicConstants.POSITION_MAX) * posX) - (parent.getWidth() / GraphicConstants.POSITION_MAX / 2) - this.getWidth() / 2;
	}
	
	public double getY(int posY) {
		return parent.getHeight() - this.getHeight() - (posY * (this.getHeight() + GraphicConstants.PIECE_GAP));
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	@Override
	public boolean equals(Object other) {
		return this.getId() == ((HanoiPiece)other).getId();
	}
}
