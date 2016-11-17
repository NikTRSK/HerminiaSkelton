package client;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatPanel extends JPanel {

	private JTextArea chatText;
	private JScrollPane scrollArea;
	private JTextField writeMsgArea;
	private JButton sendButton;
	private GameClientListener mListener;
	
	public ChatPanel(GameClientListener listener){
		mListener = listener;
		initializeVariables();
		createGUI();
	}
	
	public void appendText(String text, int num){
		
	}
	
	private void initializeVariables(){
		chatText = new JTextArea();
		scrollArea = new JScrollPane();
		writeMsgArea = new JTextField();
		sendButton = new JButton("Send");
	}
	
	private void createGUI(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		chatText.setEditable(false);
		scrollArea.add(chatText);
		
		add(scrollArea);
		add(writeMsgArea);
		add(sendButton);
	}
	
	private void sendMessage(String msg){
		
	}
}
