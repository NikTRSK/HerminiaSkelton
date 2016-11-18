package map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public abstract class MapNode {
	
	protected Image mImage = null;
	
	protected MapNode(int inX, int inY, int inWidth, int inHeight) {
		width = inWidth;
		height = inHeight;
		trueX = inX * width;
		trueY = inY * height;
	}
	
	//The "true" location and size of the object use this for updating
	protected int trueX = 0, trueY = 0;
	protected int width = 0, height = 0;
	
	private double mXScale;
	private double mYScale;
	
	//The location and size used for rendering
	protected Rectangle renderBounds = new Rectangle(0,0,0,0);
	private int mapBorderSize = 0;
	
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.drawImage(mImage, trueX, trueY, width, height, null);
	}
	
	//Scale the object for rendering
//	public void resize(double xScale, double yScale) {
//		mXScale = xScale;
//		mYScale = yScale;
//		renderBounds.x = (int)(x * xScale);
//		renderBounds.y = (int)(y * yScale);
//		renderBounds.width = (int)(width * xScale); renderBounds.height = (int)(height* yScale);
//	}
}
