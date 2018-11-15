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
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.awt.event.*;
import java.util.Date;

public class GameGUI extends JPanel{

    JPanel panel;
    JPanel boardPanel;
    static JLabel score1;
    static JLabel score2;
    static Timer player1Timer;
    static Timer player2Timer;
    static long TWENTY_MINUTES = 1200000;
    static java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("mm : ss");
    static JLabel timer1;
    static JLabel timer2;
    static Timer timerP1;
    static Timer timerP2;
    static JButton newGame;
    static JButton [] cell;
    static Game board;
    static ArrayList<Game>  arrOthello= new ArrayList<Game>();
    
    static public int player1Score = 2; 
    static public int player2Score = 2;
    static public int currentPlayer = 1;
    static long x = TWENTY_MINUTES - 1000;
    private static Game start;
    private static int rows = 8;
    private static int cols = 8;
    private static Color col = new Color(57, 160, 51);
    
  
    public GameGUI()
    {
        super(new BorderLayout());    
        setPreferredSize(new Dimension(1000,900));
        setLocation(0, 0);
        
        board = new Game();
        start = board;
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
        score1.setText(" Player 1 : " + player1Score + "  ");
        score1.setFont(new Font("Serif", Font.BOLD, 22));
        timer1 = new JLabel(sdf.format(new Date(TWENTY_MINUTES)),JLabel.CENTER);

        timerP1 = new Timer(1000, new ActionListener(){
            long tick = TWENTY_MINUTES - 1000;
            public void actionPerformed(ActionEvent ae){
                timer1.setText(sdf.format(new Date(tick)));
                tick -= 1000;
            }
        });

        
        score2 = new JLabel();   
        score2.setText(" Player 2 : " + player2Score + "  ");
        score2.setFont(new Font("Serif", Font.BOLD, 22));
        timer2 = new JLabel(sdf.format(new Date(TWENTY_MINUTES)),JLabel.CENTER);
        
        timerP2 = new Timer(1000, new ActionListener(){
            long tick = TWENTY_MINUTES - 1000;
            public void actionPerformed(ActionEvent ae){
                timer2.setText(sdf.format(new Date(tick)));
                tick -= 1000;
            }
        });
               
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
        scorePanel.add(timer2,c);
              
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
                x = TWENTY_MINUTES - 1000;
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
                score1.setText(" Player 1 : " + player1Score + "  ");
                score2.setText(" Player 2 : " + player2Score + "  ");
            }
            else
            {
                
                for (int i = 0; i < 64; i++) 
                {
                    if(e.getSource() == cell[i])  
                    {
                        int xCor, yCor;
                        int ctBlack = -100, ctWhite = -100, point;
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

                        if (ctBlack == -1 && ctWhite == -1)
                            break;
                        
                        if  (currentPlayer == 1){
                            timerP1.stop();
                            timerP2.start();
                            ctBlack = board.playBlack(xCor, yCor);                            
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
                                    k++;
                                }
                            }

                            board.findValidMovesWhite(arrList);
                            for (int j = 0; j < arrList.size(); j += 2) 
                            {
                                try 
                                {
                                    Image img = ImageIO.read(getClass().getResource("images/legalMoveIconWhite.png"));
                                    cell[arrList.get(j)*rows + arrList.get(j + 1)].setIcon(new ImageIcon(img));
                                } catch (IOException ex) {}  
                            }
                            
                            board.calculateScore(arr);
                            player1Score = arr[0]; player2Score = arr[1]; point = arr[2];
                            score1.setText("Player 1 : " + player1Score + "  ");
                            score2.setText("Player 2 : " + player2Score + "  ");

                            currentPlayer = 2;
                            
                        }                            
                        
                        else if(currentPlayer == 2 || ctBlack == 0 || ctBlack == -1)
                        {    
                            timerP2.stop();
                            timerP1.start();
                            ctWhite = board.playWhite(xCor, yCor);
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
                            score1.setText("Player 1: " + player1Score + "  ");
                            score2.setText("Player 2 : " + player2Score + "  ");
                            
                            currentPlayer = 1;
                            
                        }
                        if (timerP1.isRunning() == false || timerP2.isRunning() == false)
                            break;
                    }

                }
                int st = board.endOfGame();
                if(st == 0)
                {
                    if(player1Score > player2Score)
                        JOptionPane.showMessageDialog(null,"No legal moves remain.\nPlayer 1 wins!","Game Over",JOptionPane.INFORMATION_MESSAGE);   
                    else
                        JOptionPane.showMessageDialog(null,"No legal moves remain.\nPlayer 2 wins!","Game Over",JOptionPane.INFORMATION_MESSAGE);
                }
                else if(st == 1 || st == 3)
                {
                    JOptionPane.showMessageDialog(null,"Player 2 Wins!","Game Over",JOptionPane.INFORMATION_MESSAGE);
                }
                else if(st == 2 || st == 4)
                {
                    JOptionPane.showMessageDialog(null,"Player 1 Wins!","Game Over",JOptionPane.INFORMATION_MESSAGE); 
                }
                else if(st == 3)
                {
                    JOptionPane.showMessageDialog(null,"Invalid Score","Game Over",JOptionPane.WARNING_MESSAGE); 
                }
            }
        }
        
    }
    
}
