package map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import client.Constants;
import client.GameGUI;
import client.Player;

public class MapScreen extends JPanel implements KeyListener {
	
	private GameGUI mGUI;
	
	private MapZone currZone;
	private int mWidth = 0;
	private int mHeight = 0;
	
	private Graphics mGraphics;
	private Image mImage = null;
	
	private MapZone[][] zones;
	private int zoneX, zoneY;
	
	private Player mPlayer;
	
	public void renderAndPaint() {
		render();
		paint();
	}
	
	public MapScreen(GameGUI gui, Dimension fullScreenDimensions, Player player) {
		super();
		mGUI = gui;
		
		mWidth = (int)(2 * fullScreenDimensions.getWidth() / 3); // map is 2/3 or fullscreen
		mHeight = (int)(fullScreenDimensions.getHeight());       // map is full height
		
		zoneX = MapConstants.STARTZONE_X;
		zoneY = MapConstants.STARTZONE_Y;
//		currZone = new MapZone(MapConstants.A1, mWidth, mHeight); // choose where player starts
		
		// TODO initialize map zones
		zones = new MapZone[4][3];
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 3; j++) {
				zones[i][j] = new MapZone(MapConstants.ZONES[j][i], mWidth, mHeight);
			}
		}
		
		currZone = zones[zoneX][zoneY];
		
		// figure out player stuff
		mPlayer = player;
		player.setSize(mWidth/10, mHeight/10);
		
		addKeyListener(this);
//		setFocusable(true);
//		requestFocusInWindow();
	}
	
	public void render() {
		if(mImage == null) {
			mImage = createImage(mWidth, mHeight);
			System.out.println("" + createImage(mWidth, mHeight));
		}
		if(mImage != null) {
			System.out.println("mImage not null");
			mGraphics = mImage.getGraphics();
		} else return;
		if(mGraphics == null) {
			System.out.println("graphics null");
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
		
		//draw player
		mPlayer.draw(mGraphics);
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
	
	
	public void setToCPCenter() {
		currZone = zones[1][2]; //TODO set to constants
		mPlayer.setX(5);
		mPlayer.setY(5);
		
		renderAndPaint();
	}
	
	//movement stuff
	private void movePlayer(KeyEvent e) {
		int currX = mPlayer.getX();
		int currY = mPlayer.getY();
			 if(e.getKeyCode() == KeyEvent.VK_UP)    currY--;
		else if(e.getKeyCode() == KeyEvent.VK_DOWN)  currY++;
		else if(e.getKeyCode() == KeyEvent.VK_LEFT)  currX--;
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT) currX++;
		
		if(validMove(currX, currY)) {
			if(e.getKeyCode() == KeyEvent.VK_UP)    mPlayer.moveUp();
			else if(e.getKeyCode() == KeyEvent.VK_DOWN)  mPlayer.moveDown();
			else if(e.getKeyCode() == KeyEvent.VK_LEFT)  mPlayer.moveLeft();
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT) mPlayer.moveRight();
//			mPlayer.incrementSteps();
		}
		     
		renderAndPaint();
		
		if(currZone.getNodes()[mPlayer.getX()][mPlayer.getY()] instanceof GrassNode) {
			if(encounteredCP()) {
				JOptionPane.showMessageDialog(mGUI, "CP found!");
				mGUI.switchToBattle();
			}
		}
	}
	
	private boolean encounteredCP() {
		int random = client.Constants.rand.nextInt(8);
		if(random < 1) return true;
		return false;
	}
	
	private boolean validMove(int x, int y) {
//		System.out.println("x: " + x + "y: " + y);
		if(x > 9) {
			moveZoneRight();
			return false;
		}
		if(x < 0) {
			moveZoneLeft();
			return false;
		}
		if(y > 9) {
			moveZoneDown();
			return false;
		}
		if(y < 0) {
			moveZoneUp();
			return false;
		}
		
		if(currZone.getNodes()[x][y] instanceof WallNode) {
			return false;
		}
		return true;
	}
	
	private void moveZoneRight() {
		zoneX++;
		currZone = zones[zoneX][zoneY];
		mPlayer.setX(0);
	}
	
	private void moveZoneLeft() {
		zoneX--;
		currZone = zones[zoneX][zoneY];
		mPlayer.setX(9);
	}
	
	private void moveZoneDown() {
		zoneY++;
		currZone = zones[zoneX][zoneY];
		mPlayer.setY(0);
	}
	
	private void moveZoneUp() {
		zoneY--;
		currZone = zones[zoneX][zoneY];
		mPlayer.setY(9);
	}
	
	public void keyPressed(KeyEvent e) {
		movePlayer(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}
