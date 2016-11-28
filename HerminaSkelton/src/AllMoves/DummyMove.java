package AllMoves;

import javax.swing.JTextArea;

import AllCPs.CP;

public class DummyMove extends AttackMove{
	private static final long serialVersionUID = 3736847069616022797L;

	public DummyMove(){
		name = "";
		type = 0;
	}
	
		@Override
		public void move(CP attacker, CP defender, JTextArea text){
			//Do Nothing
		}
}
