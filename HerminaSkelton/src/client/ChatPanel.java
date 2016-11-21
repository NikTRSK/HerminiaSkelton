package client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import utilities.ChatMessage;
import utilities.DataPacket;
import utilities.User;

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
		chatText = new JTextArea(50,50);
		scrollArea = new JScrollPane(chatText);
		writeMsgArea = new JTextField();
		sendButton = new JButton("Send");
	}
	
	private void createGUI(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		chatText.setEditable(false);
		chatText.setText("Start chatting");
		chatText.setLineWrap(true);
		chatText.setWrapStyleWord(true);
		//chatText.setPreferredSize(new Dimension(300,100));
		//scrollArea.setPreferredSize(new Dimension(300,100));
		scrollArea.getViewport().setView(chatText);
		scrollArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		
		sendButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String string = writeMsgArea.getText();
				ChatMessage msg = new ChatMessage(string, mListener.getUser());
				chatText.append("\n"+string);
				writeMsgArea.setText("");
				mListener.sendData(new DataPacket<ChatMessage>(utilities.Commands.CHAT_MESSAGE, msg));			
			}
			
		});
		
		
		add(scrollArea);
		add(writeMsgArea);
		add(sendButton);
	}
}
