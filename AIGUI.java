package othello;

import java.applet.Applet;
import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.border.TitledBorder;

import othello.Game;

import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.awt.event.*;
import java.util.Date;

public class AIGUI extends JPanel{

    JPanel panel;
    JPanel boardPanel;    
    private static JFrame mainUI;

    static JLabel score1;
    static JLabel score2;
    static Timer player1Timer;
    static Timer player2Timer;
    static long TIME_MIN = 1200000;
    static java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("mm : ss");
    static JLabel timer1;
    static Timer timerP1;
    static String player1;
    static JButton newGame;
    static JButton [] cell;
    static Game board;
    static ArrayList<Game>  arrOthello= new ArrayList<Game>();
    
    static public int player1Score = 2; 
    static public int player2Score = 2;
    static long x;
    private static Game start;
    private static int rows = 8;
    private static int cols = 8;
    private static Color col = new Color(57, 160, 51);
    
  
    public AIGUI(String playerName, int time)
    {
        super(new BorderLayout());    
        setPreferredSize(new Dimension(1000,900));
        setLocation(0, 0);        
        mainUI = new JFrame("Othello: Lamar University SoftEng Fall 2018");
        TIME_MIN = time;
        x = TIME_MIN - 1000;

        board = new Game();
        start = board;
        player1 = playerName;
        arrOthello.add(new Game(board));

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(800,60));
        
        newGame = new JButton();
        newGame.setPreferredSize(new Dimension(80,50));
        try 
        {
            Image img = ImageIO.read(getClass().getResource("images/start.png"));
            newGame.setIcon(new ImageIcon(img));
        } catch (IOException ex) {}
        newGame.addActionListener(new Action());

        
        

        JLabel name = new JLabel();
        name.setText("Othello Game");
        name.setLocation(750, 680);
        panel.add(newGame);
        
        add(panel, BorderLayout.SOUTH);
        
        // Board Panel
        boardPanel = new JPanel(new GridLayout(8,8));
        cell = new JButton[64];
        int k=0;
        for(int row = 0; row < rows; row++) 
        {
            for(int colum=0; colum < cols; colum++) 
            {
                cell[k] = new JButton();
                cell[k].setSize(50, 50);
                cell[k].setBackground(new Color(57, 160, 51));
                cell[k].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                if(board.gameCells[row][colum].getCh() == 'X')
                {
                    try 
                    {
                        Image img = ImageIO.read(getClass().getResource("images/dark.png"));
                        cell[k].setIcon(new ImageIcon(img));
                    } catch (IOException ex) {}
                }
                else if(board.gameCells[row][colum].getCh() == 'O')
                {
                    try 
                    {
                        Image img = ImageIO.read(getClass().getResource("images/light.png"));
                        cell[k].setIcon(new ImageIcon(img));
                    } catch (IOException ex) {}                    
                }
                else if(row == 2 && colum == 4 || row == 3 && colum == 5 || 
                        row == 4 && colum == 2 || row == 5 && colum == 3 )
                {
                    try 
                    {
                        Image img = ImageIO.read(getClass().getResource("images/legalMoveIconBlack.png"));
                        cell[k].setIcon(new ImageIcon(img));
                    } catch (IOException ex) {} 
                } 
                cell[k].addActionListener(new Action());
                boardPanel.add(cell[k]);
                k++;
            }
        }
        add(boardPanel, BorderLayout.CENTER);
        
        
        JPanel scorePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        scorePanel.setPreferredSize(new Dimension(310,800));
        
        JLabel dark = new JLabel();
        try 
        {
            Image img = ImageIO.read(getClass().getResource("images/dark.png"));
            dark.setIcon(new ImageIcon(img));
        } catch (IOException ex) {}
        JLabel light = new JLabel();
        try 
        {
            Image img = ImageIO.read(getClass().getResource("images/light.png"));
            light.setIcon(new ImageIcon(img));
        } catch (IOException ex) {}
        score1 = new JLabel();
        score1.setText(player1 +" : " + player1Score + "  ");
        score1.setFont(new Font("Serif", Font.BOLD, 22));
        timer1 = new JLabel(sdf.format(new Date(TIME_MIN)),JLabel.CENTER);

        timerP1 = new Timer(1000, new ActionListener(){
            long tick = TIME_MIN - 1000;
            public void actionPerformed(ActionEvent ae){
                timer1.setText(sdf.format(new Date(tick)));
                tick -= 1000;

                if (tick <= 0){
                    tick = 5000000;
                    GameDB gdb = new GameDB();
                    gdb.submit(player1, "Computer", player1Score, player2Score);
                    if(player1Score > player2Score){ 
                        JOptionPane.showMessageDialog(null,"You've Run out of Time!\n"+player1+" wins!","Game Over",JOptionPane.INFORMATION_MESSAGE);
                        goToPostgame(player1);  
                    } 
                    else{
                        JOptionPane.showMessageDialog(null,"You've Run out of Time!\nComputer wins!","Game Over",JOptionPane.INFORMATION_MESSAGE);
                        goToPostgame(player1);
                    }
                }
            }
        });

        
        score2 = new JLabel();   
        score2.setText(" Computer: " + player2Score + "  ");
        score2.setFont(new Font("Serif", Font.BOLD, 22));

               
        c.gridx = 0;
        c.gridy = 1;
        scorePanel.add(dark, c);  
        c.gridx = 1;
        c.gridy = 1;
        scorePanel.add(score1,c);
        
        
        c.gridx = 0;
        c.gridy = 2;
        scorePanel.add(light, c);  
        c.gridx = 1;
        c.gridy = 2;
        scorePanel.add(timer1,c);

        c.gridx = 0;
        c.gridy = 3;
        scorePanel.add(light, c);  
        c.gridx = 1;
        c.gridy = 3;
        scorePanel.add(score2,c);

        c.gridx = 0;
        c.gridy = 4;
        scorePanel.add(light, c);  
        c.gridx = 1;
        c.gridy = 4;
              
        add(scorePanel, BorderLayout.EAST);        
    }

    static class Action implements ActionListener
    {
        

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            
            if(e.getSource() == newGame)
            {
                //timerP1.restart();
                //timerP2.restart();
                x = TIME_MIN - 1000;
                board.reset();
                arrOthello.clear();
                arrOthello.add(new Game(start));
                int k = 0;
                for (int row = 0; row < rows; row++) 
                {
                    for (int colum = 0; colum < cols; colum++) 
                    {
                        cell[k].setIcon(null);
                        if(board.gameCells[row][colum].getCh() == 'X')
                        {
                            try 
                            {
                                Image img = ImageIO.read(getClass().getResource("images/dark.png"));
                                cell[k].setIcon(new ImageIcon(img));
                            } catch (IOException ex) {}
                        }
                        else if(board.gameCells[row][colum].getCh() == 'O')
                        {
                            try 
                            {
                                Image img = ImageIO.read(getClass().getResource("images/light.png"));
                                cell[k].setIcon(new ImageIcon(img));
                            } catch (IOException ex) {}                    
                        }
                        if(row == 2 && colum == 4 || row == 3 && colum == 5 || 
                        row == 4 && colum == 2 || row == 5 && colum == 3 )
                        {
                            try 
                            {
                                Image img = ImageIO.read(getClass().getResource("images/legalMoveIconBlack.png"));
                                cell[k].setIcon(new ImageIcon(img));
                            } catch (IOException ex) {} 
                        }
                        ++k;
                    }
                }
                player1Score = 2; player2Score = 2;
                score1.setText(player1 +" : " + player1Score + "  ");
                score2.setText(" Computer : " + player2Score + "  ");
            }
            else
            {
                
                
                for (int i = 0; i < 64; i++) 
                {
                    if(e.getSource() == cell[i])  
                    {
                        int xCor, yCor;
                        int ctBlack = -100, point;
                        int arr[] = new int[3];
                        if(i==0)
                        {
                            xCor=0;
                            yCor=0;
                        }
                        else
                        {
                            yCor =i%8;
                            xCor =i/8;
                        }
                        ctBlack = board.playBlack(xCor, yCor);
                        if(ctBlack == 0)
                        {
                            timerP1.stop();
                            arrOthello.add(new Game(board));
                            int k=0;
                            for(int row = 0; row < rows; row++) 
                            {
                                for(int colum=0; colum < cols; colum++) 
                                {
                                    if(board.gameCells[row][colum].getCh() == 'X')
                                    {
                                        try 
                                        {
                                            Image img = ImageIO.read(getClass().getResource("images/dark.png"));
                                            cell[k].setIcon(new ImageIcon(img));
                                        } catch (IOException ex) {}
                                    }
                                    else if(board.gameCells[row][colum].getCh() == 'O')
                                    {
                                        try 
                                        {
                                            Image img = ImageIO.read(getClass().getResource("images/light.png"));
                                            cell[k].setIcon(new ImageIcon(img));
                                        } catch (IOException ex) {}                    
                                    }
                                    k++;
                                }
                            }
                            board.calculateScore(arr);
                            player1Score = arr[0]; player2Score = arr[1]; point = arr[2];
                            score1.setText(player1 +" : " + player1Score + "  ");
                            score2.setText("Computer : " + player2Score + "  "); 
                        }
                        if(ctBlack == 0 || ctBlack == -1)
                        {    
                            timerP1.start();
                            board.playComputer();
                            arrOthello.add(new Game(board));
                            ArrayList <Integer> arrList = new ArrayList <Integer>();
                            int k=0;
                            for(int row = 0; row < rows; row++) 
                            {
                                for(int colum=0; colum < cols; colum++) 
                                {
                                    if(board.gameCells[row][colum].getCh() == 'X')
                                    {
                                        try 
                                        {
                                            Image img = ImageIO.read(getClass().getResource("images/dark.png"));
                                            cell[k].setIcon(new ImageIcon(img));
                                        } catch (IOException ex) {}
                                    }
                                    else if(board.gameCells[row][colum].getCh() == 'O')
                                    {
                                        try 
                                        {
                                            Image img = ImageIO.read(getClass().getResource("images/light.png"));
                                            cell[k].setIcon(new ImageIcon(img));
                                        } catch (IOException ex) {}                    
                                    }
                                    else if(board.gameCells[row][colum].getCh() == '.')
                                    {
                                        cell[k].setIcon(null);
                                    }
                                    k++;
                                }
                            }
                            board.findValidMoves(arrList);
                            for (int j = 0; j < arrList.size(); j += 2) 
                            {
                                try 
                                {
                                    Image img = ImageIO.read(getClass().getResource("images/legalMoveIconBlack.png"));
                                    cell[arrList.get(j)*rows + arrList.get(j + 1)].setIcon(new ImageIcon(img));
                                } catch (IOException ex) {}  
                            }
                            board.calculateScore(arr);
                            player1Score = arr[0]; player2Score = arr[1]; point = arr[2];
                            score1.setText(player1 +" : " + player1Score + "  ");
                            score2.setText("Computer : " + player2Score + "  ");  
                        }

                        
                        
                    }

                }
                int st = board.endOfGame();
                GameDB gdb = new GameDB();
                
                if(st == 0)
                {
                    gdb.submit(player1, "Computer", player1Score, player2Score);
                    if(player1Score > player2Score){ 
                        JOptionPane.showMessageDialog(null,"No legal moves remain.\n"+player1+" wins!","Game Over",JOptionPane.INFORMATION_MESSAGE);
                        goToPostgame(player1);  
                    } 
                    else{
                        JOptionPane.showMessageDialog(null,"No legal moves remain.\nComputer wins!","Game Over",JOptionPane.INFORMATION_MESSAGE);
                        goToPostgame(player1);
                    }
                }
                else if(st == 1 || st == 3){
                    gdb.submit(player1, "Computer", player1Score, player2Score);
                    JOptionPane.showMessageDialog(null,"Computer Wins!","Game Over",JOptionPane.INFORMATION_MESSAGE);
                    goToPostgame(player1);
                }
                else if(st == 2 || st == 4){
                    gdb.submit(player1, "Computer", player1Score, player2Score);
                    JOptionPane.showMessageDialog(null,player1+" Wins!","Game Over",JOptionPane.INFORMATION_MESSAGE);
                    goToPostgame(player1); 
                }
                else if(st == 3){
                    gdb.submit(player1, "Computer", player1Score, player2Score);
                    JOptionPane.showMessageDialog(null,"Invalid Score","Game Over",JOptionPane.WARNING_MESSAGE);
                    goToPostgame(player1); 
                }

            }
        }
        
    }

    static void goToPostgame(String p1){
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to View your List of Results, "+p1+" ?","Warning",dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION){
            mainUI.setLayout(new BorderLayout());
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int height = screenSize.height;
            int width = screenSize.width;
            mainUI.setSize(width/2, height/2);
            mainUI.setLocationRelativeTo(null);

            mainUI.add(new PostgameUI(p1));
                
            mainUI.setSize(800, 800);
            mainUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainUI.setResizable(false);
            mainUI.setVisible(true);
        }
        else{
            return;
        }
    }
    
}
