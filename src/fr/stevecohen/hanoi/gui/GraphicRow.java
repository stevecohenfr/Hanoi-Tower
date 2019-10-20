package fr.stevecohen.hanoi.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Rectangle;

public class GraphicRow extends Rectangle {

	private static final long serialVersionUID = 3686747252888641648L;
	public Color color = Color.GRAY;
	
	private Container parent;
	private int posX;
	
	public GraphicRow(Container parent, int posX) {
		this.parent = parent;
		this.posX = posX;
	}
	
	@Override
	public double getWidth() {
		return parent.getWidth() / 50;
	}
	
	@Override
	public double getHeight() {
		return ((parent.getWidth() / 50) + GraphicConstants.PIECE_GAP) * (GraphicConstants.NUMBER_OF_PIECES + 1);
	}
	
	@Override
	public double getX() {
		return ((parent.getWidth() / GraphicConstants.POSITION_MAX) * posX) - (parent.getWidth() / GraphicConstants.POSITION_MAX / 2) - this.getWidth() / 2;
	}
	
	@Override
	public double getY() {
		return parent.getHeight() - this.getHeight();
	}
}
