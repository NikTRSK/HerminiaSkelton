package client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import AllCPs.CP;
import AllCPs.JimmyChen;
import utilities.Constants;
import utilities.GameScore;

public class EndGameGUI extends JFrame{
    private static final long serialVersionUID = 1L;
    private String temp;
    private JButton favCP;
    private boolean win;
    private boolean pause = false;
    private int gamemode;
    private CP bestfriend;
    private JLabel name1, name2, score1_, score2_;
    private JTextArea professorwords;
    private GameClientListener mlistener;
    private ArrayList<GameScore> topFiveScores;
    private GridBagConstraints mGridBagConst;
    private BackgroundMusic music;
    private JButton mute;
    
    public EndGameGUI(int gamemode,CP bestfriend, ArrayList<GameScore> gs, boolean win, GameClientListener mlistener){
        //gamemode 0 as guest, gamemmode 1 as singleplayer, gameode 2 as multiplayer
        this.gamemode = gamemode;
        this.mlistener = mlistener;
        this.bestfriend = bestfriend;
        this.win = win;
        topFiveScores = gs;
        addNames();
        setBackground();
        initializeComponents();
        setIcon();
        createGUI();
        addEvents();
        music.endstart();
    }
    
    private void addNames(){
        temp = Constants.generateWord(win, gamemode);
    }
    
    private void setIcon(){
        try{
            Image img3 = ImageIO.read(new File(Constants.resourceFolderbg + "unmute.png"));
            Image img4 = bestfriend.getSprite().getImage();
            Image img5 = img4.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
            ImageIcon img6 = new ImageIcon(img5);
            favCP.setIcon(img6);
            mute.setIcon(new ImageIcon(img3));
            
        }
        catch (IOException e) {
            JPanel error = new JPanel();
            JOptionPane.showMessageDialog(error,
                                          "Asset Corruption",
                                          "File Error",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void initializeComponents(){
        music = new BackgroundMusic();
        name1 = new JLabel("");
        name1.setFont(new Font("Courier", Font.BOLD, 20));
        name2 = new JLabel("");
        name2.setFont(new Font("Courier", Font.BOLD, 20));
        name1.setText("Top Five Scores:");
        if(gamemode ==2){
            name2.setText("");
        }
        score1_ = new JLabel("");
        score2_ = new JLabel("");
        score1_.setText("");
        if(gamemode ==2){
            score2_.setText("");
        }
        professorwords = new JTextArea(temp);
        professorwords.setEditable(false);
        professorwords.setPreferredSize(new Dimension(1,1));
        professorwords.setOpaque(false);
        professorwords.setBackground(Color.LIGHT_GRAY);
        professorwords.setForeground(Color.DARK_GRAY);
        professorwords.setColumns(1);
        professorwords.setLineWrap(true);
        professorwords.setRows(1);
        professorwords.setWrapStyleWord(true);
        professorwords.setFont(new Font("Serif", Font.BOLD, 20));
        favCP = new JButton();
        favCP.setOpaque(false);
        favCP.setContentAreaFilled(false);
        favCP.setBorderPainted(false);
        music = new BackgroundMusic();
        mute = new JButton();
        mute.setOpaque(false);
        mute.setContentAreaFilled(false);
        mute.setBorderPainted(false);
        mute.setToolTipText("mute?");
    }
    
    private void setBackground(){
        try {
            final Image backgroundImage = javax.imageio.ImageIO.read(new File(Constants.resourceFolderbg + "EndGameGUI.jpg"));
            setContentPane(new JPanel(new BorderLayout()) {
                private static final long serialVersionUID = -1315795587311609680L;
                
                @Override public void paintComponent(Graphics g) {
                    g.drawImage(backgroundImage, 0, 0, null);
                }
            });
        } catch (IOException e) {
            JPanel error = new JPanel();
            JOptionPane.showMessageDialog(error,
                                          "Asset Corruption",
                                          "File Error",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void createGUI(){
        setSize(1280,750);
        setTitle("Herminia Skelton");
        setLocation(500,25);
        setLayout(new GridLayout(3,5));
        
        JPanel holder = new JPanel();
        holder.setOpaque(false);
        holder.setLayout(new BoxLayout(holder, BoxLayout.Y_AXIS));
        holder.add(Box.createGlue());
        holder.add(Box.createGlue());
        holder.add(Box.createGlue());
        
        for(int i = 0 ; i < topFiveScores.size(); i++){
            JLabel numLabel = new JLabel(topFiveScores.get(i).getUsername());
            System.out.println(""+topFiveScores.get(i).getUsername());
            numLabel.setForeground(Color.DARK_GRAY);
            numLabel.setFont(new Font("Serif", Font.BOLD, 20));
            JLabel namLabel = new JLabel(""+topFiveScores.get(i).getScore());
            System.out.println(""+topFiveScores.get(i).getScore());
            namLabel.setForeground(Color.DARK_GRAY);
            namLabel.setFont(new Font("Serif", Font.BOLD, 20));
            
            JPanel subHolder = new JPanel();
            subHolder.setOpaque(false);
            subHolder.setLayout(new BoxLayout(subHolder, BoxLayout.X_AXIS));
            subHolder.add(numLabel);
            subHolder.add(Box.createGlue());
            subHolder.add(namLabel);
            
            holder.add(subHolder);
        }
        
        add(holder);
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        add(professorwords);
        add(new JLabel(""));
        
        add(new JLabel(""));
        add(mute);
        add(new JLabel(""));
        add(favCP);
        add(new JLabel(""));
    }
    
    private void addEvents(){
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we)
            {
                String ObjButtons[] = {"Yes","No","Restart"};
                int PromptResult = JOptionPane.showOptionDialog(null,"Logout or Restart?","Hermina Skelton"
                                                                ,JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[2]);
                if(PromptResult==JOptionPane.YES_OPTION)
                {
                    mlistener.logout();
                    System.exit(0);
                }
                else if(PromptResult == JOptionPane.CANCEL_OPTION){
                    closePanel();
                    new waitGUI(mlistener,"old host", "old port").setVisible(true);
                }
            }
        });
        mute.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!pause){
                    music.pauseMusic();
                    pause = true;
                    try{
                        Image img = ImageIO.read(new File(Constants.resourceFolderbg + "mute.png"));
                        mute.setIcon(new ImageIcon(img));
                    }
                    catch (IOException e1) {
                        JPanel error = new JPanel();
                        JOptionPane.showMessageDialog(error,
                                                      "Asset Corruption",
                                                      "File Error",
                                                      JOptionPane.ERROR_MESSAGE);
                    }
                }
                else{
                    music.unpauseMusic();
                    pause = false;
                    try{
                        Image img = ImageIO.read(new File(Constants.resourceFolderbg + "unmute.png"));
                        mute.setIcon(new ImageIcon(img));
                    }
                    catch (IOException e1) {
                        JPanel error = new JPanel();
                        JOptionPane.showMessageDialog(error,
                                                      "Asset Corruption",
                                                      "File Error",
                                                      JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            
        });
    }
    protected void closePanel(){
        music.endMusic();
        this.dispose();
    }
    protected void close(){
        System.exit(0);
    }
    
    public static void main(String[] args){
    	ArrayList<GameScore> topFiveScores = new ArrayList<GameScore>();
    	topFiveScores.add(new GameScore("matt", 5));
    	topFiveScores.add(new GameScore("matt", 8));
    	topFiveScores.add(new GameScore("matt", 10));
    	new EndGameGUI(2,new JimmyChen(2),topFiveScores,true,null).setVisible(true);
    }
}
