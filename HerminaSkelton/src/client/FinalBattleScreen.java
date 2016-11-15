package client;

import javax.swing.JLabel;
import javax.swing.JPanel;

import AllCPs.CP;
import utilities.DataPacket;

public class FinalBattleScreen extends JPanel{

	private static final long serialVersionUID = -9154200804429634453L;
	//private ClientListener cl;
	
	private Player player1;
	private Player player2;
	private Miller miller;
	
	private CP playerCP1;
	private CP playerCP2;
	private CP millerCP1;
	private CP millerCP2;
	
	private JPanel battlePanel;
	private JPanel actionPanel;
	private JPanel attackPanel;
	private JPanel switchPanel;
	
	private JLabel img1;
	private JLabel img2;
	private JLabel img3;
	private JLabel img4;

	public FinalBattleScreen(/*ClientListener cl*/Miller profMiller){
		//this.cl = cl;
		this.miller = profMiller;
		
	}
	
	public void setPlayers(Player p1, Player p2){
		this.player1 = p1;
		playerCP1 = player1.getCP().get(Constants.rand.nextInt(player1.getCP().size()));
		
		this.player2 = p2;
		playerCP2 = player2.getCP().get(Constants.rand.nextInt(player2.getCP().size()));
	}
	
	public void recieveMessage(DataPacket dp){
		String command = dp.getCommand();
		if(command.equals("Miller Move 1")){
			
		}else if(command.equals("Miller Move 2")){
			
		}else if(command.equals("Miller Switch 1")){
			
		}else if(command.equals("Miller Switch 2")){
			
		}else if(command.equals("")){
			
		}
	}
	
	
}
