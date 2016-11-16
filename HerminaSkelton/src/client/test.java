package client;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import AllCPs.CP;
import AllCPs.EdgarLugo;
import AllCPs.PriyankaShah;

public class test extends JFrame{

	private static final long serialVersionUID = 1L;

	private ImageIcon sprite;
	private JLabel label;
	private CP testCP;
	private Player testPlayer;
	
	public test(){
		super("test");
		setSize(1000, 1000);
		testPlayer = new Player("matt", System.currentTimeMillis(), 0);
		for(int i = 0; i<3; i++){
			testPlayer.addCP(Constants.generateCP(Constants.rand.nextInt(3)));
		}
		add(new BattleScreen(testPlayer));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		/*super("test");
		testCP = new EdgarLugo(5);
		sprite = testCP.getSprite();
		label = new JLabel(sprite);
		add(label);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);*/
	}
	
	public static void main(String [] args){
		new test();
	}
}
