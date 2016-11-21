package server;

import java.util.Vector;

import javax.swing.JTextArea;

import AllCPs.BlankCP;
import AllCPs.CP;
import AllMoves.AttackMove;
import client.Miller;
import client.Player;
import utilities.DeadSwitch;
import utilities.FinalBattleState;
import utilities.GameInstance;
import utilities.PlayerAction;

public class FinalBattleManager {
	
	private Miller miller;
	private Vector<CP> millerCPs;
	private CP[] CPs;
	
	private PlayerAction pa1;
	private PlayerAction pa2;
	
	private Integer[] state;
	
	private Player[] players;
	
	private ServerListener sl;
	
	private GameInstance gi;
	
	public FinalBattleManager(Player p1, Player p2, GameInstance gi){
		this.miller = new Miller(0); // TODO Miller Difficulty
		this.millerCPs = miller.getCP();
		
		this.players = new Player[2];
		players[0] = p1;
		players[1] = p2;
		
		this.state = new Integer[2];
		state[0] = 1;
		state[1] = 1;
		
		this.CPs= new CP[4];
		
		this.gi = gi;
		
		this.pa1 = null;
		this.pa2 = null;
		
		sendUpdate();
	}
	
	public void recieveAction(PlayerAction pa){
		if(pa1==null){
			pa1 = pa;
			if(pa.getPlayerNum()==0){
				if(state[1]==2)runTurn();
			}else{
				if(state[0]==2)runTurn();
			}
		}else {
			pa2 = pa;
			runTurn();
		}
	}
	
	public void recieveDeadSwitch(DeadSwitch ds){
		int p = ds.getPlayerNum();
		CPs[p] = players[p].getCP().get(ds.getCP());
		
		if(CPs[0].getHealth()>0 && CPs[1].getHealth()>0)sendUpdate();
	}
	
	private void runTurn(){
		int numAttacks = 4;
		
		// Switch out first
		int p1 = pa1.getPlayerNum();
		if(pa1.getType()==2){
			numAttacks--;
			CPs[pa1.getPlayerNum()] = players[p1].getCP().get(pa1.getTarget());
		}
		int p2 = pa2.getPlayerNum();
		if(pa2.getType()==2){
			numAttacks--;
			CPs[pa2.getPlayerNum()] = players[p2].getCP().get(pa2.getTarget());
		}
		
		// determine move order
		int[] order = new int[numAttacks];
		int[] speed = new int[numAttacks];
		numAttacks = 0;
		if(pa1.getType()==1){
			order[numAttacks]=p1;
			speed[numAttacks]=CPs[p1].getSpeed();
			numAttacks++;
		}
		if(pa2.getType()==1){
			order[numAttacks]=p2;
			speed[numAttacks]=CPs[p1].getSpeed();
			numAttacks++;
		}
		order[numAttacks]=2;
		speed[numAttacks]=CPs[p1].getSpeed();
		numAttacks++;
		order[numAttacks]=3;
		speed[numAttacks]=CPs[p1].getSpeed();
		for(int i = 0; i < order.length; i++){
			int maxSpeed=0;
			int index = -1;
			for(int j = i; j < order.length; j++){
				if(CPs[order[j]].getSpeed()>maxSpeed){
					maxSpeed = CPs[order[j]].getSpeed();
					index = j;
				}
			}
			int temp = order[i];
			order[i] = order[index];
			order[index] = temp;
		}
		
		// executing moves
		for(int i = 0; i < order.length; i++){
			int actor = order[i];
			if(CPs[actor].getHealth()<=0)continue;
			if(actor==p1){
				pa1.getMove().move(CPs[p1], CPs[pa1.getTarget()], new JTextArea());
			}else if(actor==p2){
				pa2.getMove().move(CPs[p2], CPs[pa2.getTarget()], new JTextArea());
			}
			else {
				int target = client.Constants.rand.nextInt(2);
				AttackMove aMove = client.Constants.attackMoves[CPs[actor].getAttackMoves()[client.Constants.rand.nextInt(2)]];
				aMove.move(CPs[actor], CPs[target], new JTextArea());
			}
		}
		
		// checking if the game is over
		boolean p1lost = true;
		if(CPs[0].getHealth()>0)p1lost=false;
		if(p1lost){
			for(int i = 0; i < players[0].getCP().size(); i++){
				if(players[0].getCP().get(i).getHealth()>0){
					p1lost=false;
					break;
				}
			}
		}
		boolean p2lost = true;
		if(CPs[1].getHealth()>0)p2lost=false;
		if(p2lost){
			for(int i = 0; i < players[1].getCP().size(); i++){
				if(players[1].getCP().get(i).getHealth()>0){
					p2lost=false;
					break;
				}
			}
		}
		boolean won = true;
		if(CPs[2].getHealth()>0)won=false;
		if(CPs[3].getHealth()>0)won=false;
		if(won){
			for(int i = 0; i < players[1].getCP().size(); i++){
				if(millerCPs.get(i).getHealth()>0){
					won=false;
					break;
				}
			}
		}
		if(won){
			state[0] = 4;
			state[1] = 4;
			endGame(1);
			return;
		}
		if(p1lost)state[0] = 2;
		if(p2lost)state[1] = 2;
		if(p1lost&&p2lost){
			state[0] = 3;
			state[1] = 3;
			endGame(0);
			return;
		}
		
		// replacing dead CPs
		boolean updateYet = true;
		for(int i = 0; i < 4; i++){
			if(CPs[i].getHealth()<=0){
				if(i<2){
					if(state[i]!=2){
						replaceCP(i);
						updateYet = false;
					}
				}else{
					Vector<Integer> liveCPs = new Vector<Integer>();
					for(int j = 0; j<millerCPs.size(); j++){
						CP temp = millerCPs.get(j);
						if(temp.getHealth()>0 && !(temp==CPs[2]) && !(temp==CPs[3]))liveCPs.add(j);
					}if(liveCPs.size()>0){
						CPs[i] = millerCPs.get(client.Constants.rand.nextInt(liveCPs.size()));
					}else{
						CPs[i] = new BlankCP(1);
					}
				}
			}
		}
		if(updateYet)sendUpdate();
	}
	
	private void sendUpdate(){
		gi.sendFinalBattleUpdate(new FinalBattleState(CPs[0], CPs[1], CPs[2], CPs[3], players, state));
	}

	private void replaceCP(int player){
		//gi.sendCPRequest(player);
	}
	
	private void endGame(int result){
		//TODO
		//sl.endGame(result, gi);
	}
}
