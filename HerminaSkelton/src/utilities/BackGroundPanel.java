package utilities;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackGroundPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Image image = null;
	private int iWidth2;
	private int iHeight2;

	public BackGroundPanel(String path)
	{
		super();
		ImageIcon imgIcon = new ImageIcon(path);
	    this.image = imgIcon.getImage();
	    //this.image = this.image.getScaledInstance(this.getWidth(), this.getHeight(), java.awt.Image.SCALE_SMOOTH);
	    this.iWidth2 = image.getWidth(this)/2;
	    this.iHeight2 = image.getHeight(this)/2;
	}


	public void paintComponent(Graphics g)
	{
	    super.paintComponent(g);
	    if (image != null)
	    {
	        int x = this.getParent().getWidth()/2 - iWidth2;
	        int y = this.getParent().getHeight()/2 - iHeight2;
	        g.drawImage(image,x,y,this);
	    }
	}
	
	public void setImage(String path){
		ImageIcon imgIcon = new ImageIcon(path);
	    this.image = imgIcon.getImage();
	    //this.image = this.image.getScaledInstance(this.getWidth(), this.getHeight(), java.awt.Image.SCALE_SMOOTH);
	    this.iWidth2 = image.getWidth(this)/2;
	    this.iHeight2 = image.getHeight(this)/2;
	    this.repaint();
	}
	
	/*private static final long serialVersionUID = 289210130126374451L;
	private String filename;

	public BackGroundPanel(String path) {
		super();
		background = Toolkit.getDefaultToolkit().createImage(path);
		filename = path;
		repaint();
	}

	// got help for this line from StackOverflow
	// (http://stackoverflow.com/questions/19125707/simplest-way-to-set-image-as-jpanel-background)
	private Image background;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, this);
	}

	public void setImage(String filename) {
		background = Toolkit.getDefaultToolkit().createImage(filename);
		this.filename = filename;
		repaint();
	}

	public String getImage() {
		return filename;
	}*/
}
