package map;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import client.Player;

public class TestGUI extends JFrame {
	
	private MapScreen map;
	
	public TestGUI() {
		super("Map Test");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width =(int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		setSize(width, height);
		setLocation(0,0);
		
		setVisible(true);
		
		Player player = new Player("John");
		
		map = new MapScreen(this, screenSize, player);
		add(map, BorderLayout.CENTER);
//		map.addKeyListener(map);
		map.setFocusable(true);
		map.requestFocusInWindow();
//		map.render();
//		map.paint();
		map.renderAndPaint();
//		JOptionPane.showMessageDialog(this, "CP found!");
	}
	
	public static void main(String[] args) {
		TestGUI test = new TestGUI();
		test.setVisible(true);
//		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//		test.setUndecorated(true);
//		gd.setFullScreenWindow(test);
		com.apple.eawt.FullScreenUtilities.setWindowCanFullScreen(test,true);
		com.apple.eawt.Application.getApplication().requestToggleFullScreen(test);
	}
}
