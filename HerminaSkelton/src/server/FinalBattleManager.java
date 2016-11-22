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

	private GameInstance gi;

	public FinalBattleManager(Player p1, Player p2, GameInstance gi) {
		this.miller = new Miller(0);
		this.millerCPs = miller.getCP();

		this.players = new Player[2];
		players[0] = p1;
		players[1] = p2;

		this.state = new Integer[2];
		state[0] = 1;
		state[1] = 1;

		this.CPs = new CP[4];
		CPs[0] = p1.getCP().get(client.Constants.rand.nextInt(p1.getCP().size()));
		CPs[1] = p2.getCP().get(client.Constants.rand.nextInt(p2.getCP().size()));
		CPs[2] = millerCPs.get(client.Constants.rand.nextInt(millerCPs.size()));
		CPs[3] = millerCPs.get(client.Constants.rand.nextInt(millerCPs.size()));

		this.gi = gi;

		this.pa1 = null;
		this.pa2 = null;

		sendUpdate();
	}

	public void recieveAction(PlayerAction pa) {
		if (pa1 == null) {
			pa1 = pa;
			if (pa.getPlayerNum() == 0) {
				if (state[1] == 2)
					runTurn();
			} else {
				if (state[0] == 2)
					runTurn();
			}
		} else {
			pa2 = pa;
			runTurn();
		}
	}

	public void recieveDeadSwitch(DeadSwitch ds) {
		int p = ds.getPlayerNum();
		CPs[p] = players[p].getCP().get(ds.getCP());
	}

	private void runTurn() {
		int numAttacks = 4;

		// Switch out first
		int p1 = pa1.getPlayerNum();
		if (pa1.getType() == 2) {
			numAttacks--;
			CPs[pa1.getPlayerNum()] = players[p1].getCP().get(pa1.getTarget());
		}
		int p2 = pa2.getPlayerNum();
		if (pa2.getType() == 2) {
			numAttacks--;
			CPs[pa2.getPlayerNum()] = players[p2].getCP().get(pa2.getTarget());
		}

		// determine move order
		int[] order = new int[numAttacks];
		int[] speed = new int[numAttacks];
		numAttacks = 0;
		if (pa1.getType() == 1) {
			order[numAttacks] = p1;
			speed[numAttacks] = CPs[p1].getSpeed();
			numAttacks++;
		}
		if (pa2.getType() == 1) {
			order[numAttacks] = p2;
			speed[numAttacks] = CPs[p1].getSpeed();
			numAttacks++;
		}
		order[numAttacks] = 2;
		speed[numAttacks] = CPs[p1].getSpeed();
		numAttacks++;
		order[numAttacks] = 3;
		speed[numAttacks] = CPs[p1].getSpeed();
		for (int i = 0; i < order.length; i++) {
			int maxSpeed = 0;
			int index = -1;
			for (int j = i; j < order.length; j++) {
				if (CPs[order[j]].getSpeed() > maxSpeed) {
					maxSpeed = CPs[order[j]].getSpeed();
					index = j;
				}
			}
			int temp = order[i];
			order[i] = order[index];
			order[index] = temp;
		}

		// executing moves
		for (int i = 0; i < order.length; i++) {
			int actor = order[i];
			if (CPs[actor].getHealth() <= 0)
				continue;
			if (actor == p1) {
				pa1.getMove().move(CPs[p1], CPs[pa1.getTarget()], new JTextArea());
			} else if (actor == p2) {
				pa2.getMove().move(CPs[p2], CPs[pa2.getTarget()], new JTextArea());
			} else {
				int target = client.Constants.rand.nextInt(2);
				AttackMove aMove = client.Constants.attackMoves[CPs[actor].getAttackMoves()[client.Constants.rand
						.nextInt(2)]];
				aMove.move(CPs[actor], CPs[target], new JTextArea());
			}
		}

		// checking if the game is over
		boolean p1lost = true;
		if (CPs[0].getHealth() > 0)
			p1lost = false;
		if (p1lost) {
			for (int i = 0; i < players[0].getCP().size(); i++) {
				if (players[0].getCP().get(i).getHealth() > 0) {
					p1lost = false;
					break;
				}
			}
		}
		boolean p2lost = true;
		if (CPs[1].getHealth() > 0)
			p2lost = false;
		if (p2lost) {
			for (int i = 0; i < players[1].getCP().size(); i++) {
				if (players[1].getCP().get(i).getHealth() > 0) {
					p2lost = false;
					break;
				}
			}
		}
		boolean won = true;
		if (CPs[2].getHealth() > 0)
			won = false;
		if (CPs[3].getHealth() > 0)
			won = false;
		if (won) {
			for (int i = 0; i < players[1].getCP().size(); i++) {
				if (millerCPs.get(i).getHealth() > 0) {
					won = false;
					break;
				}
			}
		}
		if (won) {
			state[0] = 4;
			state[1] = 4;
			endGame(won);
			return;
		}
		if (p1lost)
			state[0] = 2;
		if (p2lost)
			state[1] = 2;
		if (p1lost && p2lost) {
			state[0] = 3;
			state[1] = 3;
			endGame(false);
			return;
		}
		
		// replacing player 1's dead CPs
		if(state[0]==2){
			CPs[0] = new BlankCP(1);
		}
		else if(CPs[0].getHealth()==0){
			for(int i = 0; i< players[0].getCP().size(); i++){
				if(players[0].getCP().get(i).getHealth()>0)CPs[0]=players[0].getCP().get(i);
			}
		}
		
		// replacing player 2's dead CPs
		if(state[1]==2){
			CPs[1] = new BlankCP(1);
		}
		if(CPs[1].getHealth()==0){
			for(int i = 0; i< players[1].getCP().size(); i++){
				if(players[1].getCP().get(i).getHealth()>0)CPs[1]=players[1].getCP().get(i);
			}
		}
		
		// replacing miller's dead CPs
		if(CPs[2].getHealth()==0){
			for(int i = 0; i< millerCPs.size(); i++){
				if(millerCPs.get(i).getHealth()>0){
					if(!millerCPs.get(i).equals(CPs[3])) CPs[2]=millerCPs.get(i);
				}
			}
		}if(CPs[2].getHealth()==0)CPs[2] = new BlankCP(1);
		if(CPs[3].getHealth()==0){
			for(int i = 0; i< millerCPs.size(); i++){
				if(millerCPs.get(i).getHealth()>0){
					if(!millerCPs.get(i).equals(CPs[2])) CPs[3]=millerCPs.get(i);
				}
			}
		}if(CPs[3].getHealth()==0)CPs[3] = new BlankCP(1);

		sendUpdate();
		pa1 = null;
		pa2 = null;
	}

	private void sendUpdate() {
		FinalBattleState toSend = new FinalBattleState(CPs[0], CPs[1], CPs[2], CPs[3], players, state);
		gi.sendFinalBattleUpdate(toSend);
	}

	private void endGame(boolean result) {
		gi.endGame(result);
	}
}
