package application.model;

import java.awt.Rectangle;

public class Block {

	private int x;
	private int y;
	private int size;
	private Rectangle box;
	
	public Block(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.size = 5;
		box = new Rectangle (this.x, this.y, this.size, this.size);
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	public Rectangle getBox() {
		return box;
	}
	
	public Rectangle getTopBounds() {
		return new Rectangle( x, y, size, 6);
	}
	public Rectangle getBottomBounds() {
		return new Rectangle( x, y + (size - 6), size, 6);
	}
	public Rectangle getLeftBounds() {
		return new Rectangle( x, y, 6, size);
	}
	public Rectangle getRightBounds() {
		return new Rectangle( x + (size - 6), y, 6, size);
	}
	
}
