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
 
public class PostgameUI extends JPanel{
    
    PostgameUI(String player1) {
        super(new BorderLayout());    
        setPreferredSize(new Dimension(500,400));
        setLocation(0, 0);
        
        GameDB gdb = new GameDB();

        JScrollPane scroll = gdb.retrieve(player1);
        add(scroll, BorderLayout.CENTER);
    }    
}