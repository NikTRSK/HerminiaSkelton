package map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class MapScreen extends JPanel {
	
	private MapZone currZone;
	private int mWidth = 0;
	private int mHeight = 0;
	
	private Graphics mGraphics;
	private Image mImage = null;
	
	private MapZone[][] zones;
	
	public MapScreen(Dimension fullScreenDimensions) {
		super();
		mWidth = (int)(2 * fullScreenDimensions.getWidth() / 3); // map is 2/3 or fullscreen
		mHeight = (int)(fullScreenDimensions.getHeight());       // map is full height
//		System.out.println(mWidth + " " + mHeight);
//		mImage = createImage(mWidth, mHeight);
//		if(mImage == null) System.out.println("NULL");
		System.out.println(mHeight);
		currZone = new MapZone(MapConstants.A1, mWidth, mHeight); // choose where player starts
		
		// TODO initialize map zones
//		zones = new MapZone[4][3];
//		for(int i = 0; i < )
	}
	
	public void render() {
		if(mImage == null) {
			mImage = createImage(mWidth, mHeight);
		}
		if(mImage != null) {
//			System.out.println("mImage not null");
			mGraphics = mImage.getGraphics();
		} else return;
		if(mGraphics == null) {
//			System.out.println("graphics null");
			return;
		}
		
		// TODO change zone if player leaves
		
		//clear the entire panel
		mGraphics.setColor(Color.WHITE);
		mGraphics.fillRect(0, 0, mWidth, mHeight);
		
		//draw nodes
		for(MapNode[] nodes : currZone.getNodes()) {
			for(MapNode node : nodes) {
				node.draw(mGraphics);
			}
		}
	}
	
	public void paint() {
		Graphics g;
		try{
			g = this.getGraphics();
			if((g != null) && (mImage != null)) {
				g.drawImage(mImage, 0, 0, null);
			}
			//Apparently this is needed for some systems
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		} catch (Exception e) {System.out.println("Graphics context error:" + e);}
	} // from Factory code
	
	@Override
	protected void paintComponent(Graphics g) {
		//This isn't part of the main loop, but will be called when re-scaling
		super.paintComponent(g);
		if(mImage != null) {
			g.drawImage(mImage, 0, 0, null);
		}
	} // from Factory code
}
