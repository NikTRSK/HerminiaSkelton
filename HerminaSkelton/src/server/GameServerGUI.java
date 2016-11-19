package server;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import utilities.GameInstance;

public class GameServerGUI extends JFrame {
	private static final long serialVersionUID = 4186159208256922717L;
	private JTabbedPane tabbedPanel;
	private JTable gamesTable, usersTable;
	private JPanel gamesPanel, usersPanel;
	
	public GameServerGUI() {
		super("Game waiting room");
		initializeVariables();
		createGUI();
		addEvents();
		setVisible(true);
	}
	
	private void setBackground(){
		try {
		    final Image backgroundImage = javax.imageio.ImageIO.read(new File(utilities.Constants.resourceFolderbg + utilities.Constants.serverbkgimg));
		    setContentPane(new JPanel(new BorderLayout()) {
				private static final long serialVersionUID = -1315795587311609680L;

				@Override public void paintComponent(Graphics g) {
		            g.drawImage(backgroundImage, 0, 0, null);
		        }
		    });
		} catch (IOException ioe) { utilities.Util.printExceptionToCommand(ioe); }
	}
	
	private void initializeVariables() {
		UIManager.put("Table.font", client.Constants.GAMEFONT);
		UIManager.put("Table.font", client.Constants.GAMEFONT);
		
		String [] gamesCols = { "Game", "Connected Users" };
		DefaultTableModel gamesTableModel = new DefaultTableModel(0, gamesCols.length);
		gamesTableModel.setColumnIdentifiers(gamesCols);
		gamesTable = new JTable(gamesTableModel);
		gamesTable.setFont(client.Constants.GAMEFONT);
		gamesTable.setOpaque(false);
		
		String [] usersCols = { "User", "Game Instance" };
		DefaultTableModel usersTableModel = new DefaultTableModel(0, usersCols.length);
		usersTableModel.setColumnIdentifiers(usersCols);
		usersTable = new JTable(usersTableModel);
		usersTable.setFont(client.Constants.GAMEFONT);
		usersTable.setOpaque(false);
	}
	
	private void createGUI() {
		setSize(600, 600);
		
		setBackground();
		// Create tabbed pane and add panels for two tabs
		UIManager.put("TabbedPane.contentOpaque", false);
//		UIManager.put("TabbedPane.tabsOpaque", false);
		
		tabbedPanel = new JTabbedPane();
		tabbedPanel.setOpaque(false);
		tabbedPanel.setFont(client.Constants.GAMEFONT);
		gamesPanel = new JPanel();
		gamesPanel.setOpaque(false);
		gamesPanel.add(new JScrollPane(gamesTable));
		usersPanel = new JPanel();
		usersPanel.setOpaque(false);
		usersPanel.add(new JScrollPane(usersTable));
		
		tabbedPanel.add("gamesPanel", gamesPanel);
		tabbedPanel.add("usersPanel", usersPanel);
		add(tabbedPanel);
		

	}
	
	private void addEvents() {
		
	}
	
	
	// TABLE UPDATES
	protected void addGameInstance(GameInstance gi) {
		DefaultTableModel gamesModel = (DefaultTableModel) gamesTable.getModel();
		ArrayList<String> players = gi.getPlayerUsernames();
		gamesModel.addRow(new Object [] {"Game " + gi.getInstanceID(), players.get(0) + " | " + players.get(1)});
		
		updateUsersTable(gi.getInstanceID(), players);
//		gi.getPlayerUsernames()
	}
	
	protected void updateGamesTable() {
		DefaultTableModel gamesModel = (DefaultTableModel) gamesTable.getModel();
		gamesModel.addRow(new Object [] {"Game 5", "User 3"});
	}
	
	protected void addUserToUsersTable(String user) {
		DefaultTableModel usersModel = (DefaultTableModel) usersTable.getModel();
		usersModel.addRow(new Object [] {user, "n/a"});
	}
	
	protected void updateUsersTable(Integer gameID, ArrayList<String> players) {
		DefaultTableModel usersModel = (DefaultTableModel) usersTable.getModel();
		for (int i = 0; i < usersModel.getRowCount(); i++) {
			if (usersModel.getValueAt(i, 0).equals(players.get(0)))
				usersModel.setValueAt("Game " + gameID, i, 1);
			if (players.size() > 1 && usersModel.getValueAt(i, 0).equals(players.get(1)))
				usersModel.setValueAt("Game " + gameID, i, 1);
		}
	}
	
	protected void removeUserToUsersTable(String user) {
		DefaultTableModel usersModel = (DefaultTableModel) usersTable.getModel();
		for (int i = 0; i < usersModel.getRowCount(); i++) {
			if (usersModel.getValueAt(i, 1).equals(user))
				usersModel.removeRow(i);
		}
	}
}
