package server;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GameServerGUI extends JFrame {
	private static final long serialVersionUID = 4186159208256922717L;
	private JTabbedPane tabbedPanel;
	private JTable gamesTable, usersTable;
	private JPanel gamesPanel, usersPanel;
	
	public GameServerGUI() {
		initializeVariables();
		createGUI();
		addEvents();
		// test
		updateGamesTable();
		
		setVisible(true);
	}
	
	private void initializeVariables() {
		// Initialize Games Table
//		ArrayList<String> gamesColumns = new ArrayList<String>("Game", "Connected Users");
//		ArrayList<String []> gamesData = new ArrayList<String []>();
		// test
//		String [] addData = {"test game", "test user"};
//		gamesData.add(addData);
		DefaultTableModel gamesTableModel = new DefaultTableModel(new Object[][] {
      { "Game 1", "User 1" }, { "Game 2", "User 2" } },
      new Object[] { "Game", "Connected Users" });
		gamesTable = new JTable(gamesTableModel);
//		gamesTable = new JTable(gamesData, gamesColumns);
		
		// Initialize Users Colums
//		String[] usersColums = {"User", "Game Instance"};
		DefaultTableModel usersTableModel = new DefaultTableModel(new Object[][] {
      { "User 1", "Game 1" }, { "Game 2", "User 2" } },
      new Object[] { "User", "Game Instance" });
		usersTable = new JTable(usersTableModel);
	}
	
	private void createGUI() {
		setSize(600, 800);
		
		// Create tabbed pane and add panels for two tabs
		tabbedPanel = new JTabbedPane();
		gamesPanel = new JPanel();
		gamesPanel.add(new JScrollPane(gamesTable));
		usersPanel = new JPanel();
		usersPanel.add(new JScrollPane(usersTable));
		
		
		
		tabbedPanel.add("gamesPanel", gamesPanel);
		tabbedPanel.add("usersPanel", usersPanel);
		add(tabbedPanel);
		

	}
	
	private void addEvents() {
		
	}
	
	protected void updateGamesTable() {
		DefaultTableModel gamesModel = (DefaultTableModel) gamesTable.getModel();
		gamesModel.addRow(new Object [] {"Game 5", "User 3"});
	}
	
	protected void updateUsersTable() {
		
	}
}
