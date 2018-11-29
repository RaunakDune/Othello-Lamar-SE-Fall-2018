package othello;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
 
public class LoginUI extends JFrame{
    private static JLabel l1, l2, l3;
    private static JTextField tf1;
    private static JButton login;
    private static JButton register;
    private static JButton results;
    private static JPasswordField p1;
    private static JPanel pnlLeft;
    private static JFrame mainUI;
    private static JFrame frame;
    private static JRadioButton singlePlayer;
    private static JRadioButton doublePlayer;
    private static Boolean single;
    private static ButtonGroup gameType;
    
    LoginUI() {
        super("Othello: Lamar University SoftEng Fall 2018");
        frame = new JFrame("Othello: Lamar University SoftEng Fall 2018");
        mainUI = new JFrame("Othello: Lamar University SoftEng Fall 2018");
        l1 = new JLabel("Welcome to a game of Othello!");
        l1.setForeground(Color.blue);
        l1.setFont(new Font("Serif", Font.BOLD, 20));
        
        l2 = new JLabel("Username");
        l3 = new JLabel("Password");
        tf1 = new JTextField();
        p1 = new JPasswordField();
        login = new JButton("Play Game?");
        register = new JButton("Register");
        results = new JButton("View Results of Matches");
        singlePlayer = new JRadioButton("Single Player");
        doublePlayer = new JRadioButton("Two Player");
        gameType = new ButtonGroup();
        single = false;
        
        l1.setBounds(100, 30, 400, 30);
        l2.setBounds(80, 70, 200, 30);
        l3.setBounds(80, 110, 200, 30);
        tf1.setBounds(300, 70, 200, 30);
        p1.setBounds(300, 110, 200, 30);
        login.setBounds(150, 160, 200, 30);
        register.setBounds(175, 200, 100, 30);
        singlePlayer.setBounds(150, 250, 100, 30);
        doublePlayer.setBounds(150, 270, 100, 30);
        results.setBounds(175, 300, 300, 30);

        gameType.add(singlePlayer);
        gameType.add(doublePlayer);
        singlePlayer.setSelected(true);
        
        frame.add(l1);
        frame.add(l2);
        frame.add(tf1);
        frame.add(l3);
        frame.add(p1);
        frame.add(login);
        frame.add(register);
        frame.add(results);
        frame.add(singlePlayer);
        frame.add(doublePlayer);

        login.addActionListener(new Action());
        register.addActionListener(new Action());
        results.addActionListener(new Action());
        
        frame.setSize(800, 800);
        frame.setLayout(null);
        frame.setVisible(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    static class Action implements ActionListener
    {  
        @Override
        public void actionPerformed(ActionEvent e) {

            if (singlePlayer.isSelected()){
                single = true;
            }

            if (doublePlayer.isSelected()){
                single = false;
            }

            if (e.getSource() == login){
                GameDB gdb = new GameDB();
                String uname = tf1.getText();
                String pass = p1.getText();
                if(gdb.verify(uname, pass)){
                    frame.setVisible(false);
                    mainUI.setLayout(new BorderLayout());
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    int height = screenSize.height;
                    int width = screenSize.width;
                    mainUI.setSize(width/2, height/2);

                    // center the jframe on screen
                    mainUI.setLocationRelativeTo(null);
                    
                    if (single){
                        String player1 = JOptionPane.showInputDialog("Player 1 Name?");
                        pnlLeft = new AIGUI(player1);
                    }
                    else{
                        String player1 = JOptionPane.showInputDialog("Player 1 Name?");
                        String player2 = JOptionPane.showInputDialog("Player 2 Name?");
                        pnlLeft = new GameGUI(player1, player2);
                    }
                    mainUI.add(pnlLeft, BorderLayout.CENTER);
                    
                    mainUI.setSize(800, 800);
                    mainUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    mainUI.setResizable(false);
                    mainUI.setVisible(true);

                    
                }
                else{
                    JOptionPane.showMessageDialog(null,"Incorrect login or password","Error",JOptionPane.ERROR_MESSAGE); 
                }
            }
            else if (e.getSource() == register){
                GameDB gdb = new GameDB();
                String uname = tf1.getText();
                String pass = p1.getText();

                if (gdb.register(uname, pass)){
                    JOptionPane.showMessageDialog(null,"Successfully Registered! You can now login.","Success",JOptionPane.INFORMATION_MESSAGE); 
                }
                else{
                    JOptionPane.showMessageDialog(null,"Unable to register. Please check you inputs.","Error",JOptionPane.ERROR_MESSAGE); 
                }
            }
            else if (e.getSource() == results){
                String playerName = JOptionPane.showInputDialog("Which Player's results do you want to see?");
                frame.setVisible(false);
                mainUI.setLayout(new BorderLayout());
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int height = screenSize.height;
                int width = screenSize.width;
                mainUI.setSize(width/2, height/2);
                mainUI.setLocationRelativeTo(null);

                mainUI.add(new PostgameUI(playerName));
                    
                mainUI.setSize(800, 800);
                mainUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainUI.setResizable(false);
                mainUI.setVisible(true);

                
            }
        }
    }

    public static void main(String[] args) {
        new LoginUI();
    }
}