package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import utilities.ChatMessage;

public class ChatPanel extends JPanel {
	private static final long serialVersionUID = -7030744577806492731L;
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
	
	public void appendText(String user, String text){
		chatText.append("\n" + user + ": " + text);
	}
	
	private void initializeVariables(){
		chatText = new JTextArea(50,50);
		scrollArea = new JScrollPane(chatText);
		writeMsgArea = new JTextField();
		sendButton = new JButton("Send");
	}
	
	private void createGUI(){
		setLayout(new BorderLayout());
		setBackground(Constants.BACKGROUND_COLOR);
		
		chatText.setEditable(false);
		chatText.setText("Start chatting");
		chatText.setLineWrap(true);
		chatText.setWrapStyleWord(true);
		chatText.setBackground(Constants.BACKGROUND_COLOR2);
		chatText.setForeground(Constants.BACKGROUND_COLOR);
		chatText.setFont(Constants.GAMEFONT);
		
		writeMsgArea.setBackground(Constants.BACKGROUND_COLOR);
		writeMsgArea.setForeground(Constants.BACKGROUND_COLOR2);
		writeMsgArea.setFont(Constants.GAMEFONT);
		
		scrollArea.getViewport().setView(chatText);
		scrollArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		sendButton.setBackground(Constants.BACKGROUND_COLOR2);
		sendButton.setForeground(Constants.FONT_COLOR);
		sendButton.setFont(Constants.GAMEFONT);
		sendButton.setPreferredSize(new Dimension(80, 0));
		
		sendButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String string = writeMsgArea.getText();
				ChatMessage msg = new ChatMessage(string, mListener.getUser());
//				chatText.append("\n"+string);
				writeMsgArea.setText("");
				mListener.sendChat(msg);		
			}
			
		});
		
		JPanel sendHolder = new JPanel();
		sendHolder.setLayout(new BorderLayout());
		sendHolder.add(writeMsgArea, BorderLayout.CENTER);
		sendHolder.add(sendButton, BorderLayout.EAST);
		sendHolder.setPreferredSize(new Dimension(0, 50));
		
		add(scrollArea, BorderLayout.CENTER);
		add(sendHolder, BorderLayout.SOUTH);
	}
}
