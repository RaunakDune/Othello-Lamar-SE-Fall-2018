package othello;

import javax.swing.*;
import java.awt.*;


public class main extends JFrame{
    
    private static JPanel pnlLeft;
    
    public main() {
        super("Othello: Lamar University SoftEng Fall 2018");
        setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        setSize(width/2, height/2);

        // center the jframe on screen
        setLocationRelativeTo(null);
        
        pnlLeft = new GameGUI();
        add(pnlLeft, BorderLayout.CENTER);
        
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new main();
    }
    
}
