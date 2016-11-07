package Client;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import AllCPs.CP;
import AllCPs.EdgarLugo;

public class test extends JFrame{

	private static final long serialVersionUID = 1L;

	private ImageIcon sprite;
	private JLabel label;
	private CP testCP;
	
	public test(){
		super("test");
		testCP = new EdgarLugo(5);
		sprite = testCP.getSprite();
		label = new JLabel(sprite);
		add(label);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String [] args){
		new test();
	}
}
