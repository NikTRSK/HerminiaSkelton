package server;

import java.util.Vector;

import AllCPs.CP;
import client.FinalBattleScreen;
import client.Miller;
import client.Player;

public class FinalBattleManager {
	private FinalBattleScreen player1;
	private FinalBattleScreen player2;
	
	private Miller miller;
	private Vector<CP> millerCPs;
	private CP p1CP;
	private CP p2CP;
	private CP m1CP;
	private CP m2CP;
	
	private int state;
	
	private ServerClientCommunicator scc;
	
	public FinalBattleManager(Player p1, Player p2, int difficulty, ServerClientCommunicator scc){
		this.miller = new Miller(difficulty);
		this.millerCPs = miller.getCP();
		
		this.scc = scc;
		
		//scc.setFinalBattleManager(this);
		//scc.sendMiller(miller);
	}
	
	public void setp1CP(CP cp){
		//p1CP = new 
	}
	
}
