package othello;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.SQLException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.util.UUID;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class GameDB {

    private String username;
    private String password;
    private Scanner sc;
    private UUID id;
    String[] words;
    private static DefaultTableModel model;
    private static JTable table;
    private String[] resultsColumns = {"Player 1 Name", "Player 2 Name", "Player 1 Score", "Player 2 Score"};


    public GameDB () {
        try {
            sc = new Scanner(new File("othello\\authentication.txt"));
            Class.forName("org.postgresql.Driver");

            if (sc.hasNextLine()) {
                words = sc.nextLine().split("\\s");
            }
            username = words[0];
            password = words[1];


        }
        catch (Exception e) {
            System.err.println(e);
            System.exit(-1);
        }
      
   }

   public Boolean verify(String uname, String pwd){
    try {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1/othello", username, password);        
        String query = "SELECT \"Username\", \"Password\" FROM public.\"Users\" WHERE \"Username\" = '"+uname+"'";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);

        if (rs.next()) {

            if (pwd.equals(rs.getString("Password"))){
                connection.close();
                return true;
            }
            else{
                connection.close();
                return false;
            }
        }
     }
     catch (java.sql.SQLException e) {
        System.err.println(e);
        System.exit(-1);
     }
     return false;
   }

   public Boolean register(String uname, String pwd){
    String insertQuery = "INSERT INTO public.\"Users\"(\"Username\", \"Password\") VALUES (?, ?)";
    
    try (Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1/othello", username, password);
        PreparedStatement pst = connection.prepareStatement(insertQuery)) {

            pst.setString(1, uname);
            pst.setString(2, pwd);

            int queryCount = pst.executeUpdate();
            connection.close();

            return (queryCount > 0);
        
            
     }
    catch (java.sql.SQLException e) {
        System.err.println(e);
        System.exit(-1);
     }
    return false;
    }

    public Boolean submit(String p1name, String p2name, int p1score, int p2score){
        id = UUID.randomUUID();
        String insertQuery = "INSERT INTO public.\"ResultList\"(\"GameID\", \"Player1Name\", \"Player2Name\", \"Player1Score\", \"Player2Score\") VALUES (?, ?, ?, ?, ?)";
        
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1/othello", username, password);
            PreparedStatement pst = connection.prepareStatement(insertQuery)) {
    
                pst.setString(1, id.toString());
                pst.setString(2, p1name);
                pst.setString(3, p2name);
                pst.setInt(4, p1score);
                pst.setInt(5, p2score);
    
                int queryCount = pst.executeUpdate();
                connection.close();
    
                return (queryCount > 0);
            
                
         }
         catch (java.sql.SQLException e) {
            System.err.println(e);
            System.exit(-1);
         }
        return false;
    }

    public JScrollPane retrieve(String pname){
        model = new DefaultTableModel();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(resultsColumns);
        table = new JTable();
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1/othello", username, password);        
            String query1 = "SELECT * FROM public.\"ResultList\" WHERE \"Player1Name\" = '"+pname+"'";
            String query2 = "SELECT * FROM public.\"ResultList\" WHERE \"Player2Name\" = '"+pname+"'";
            Statement statement = connection.createStatement();
            ResultSet rs1 = statement.executeQuery(query1);
            
            while (rs1.next()){
                model.addRow(new Object[]{rs1.getString("Player1Name"), rs1.getString("Player2Name"), rs1.getString("Player1Score"), rs1.getString("Player2Score")});
            }

            
            ResultSet rs2 = statement.executeQuery(query2);
            while (rs2.next()){
                model.addRow(new Object[]{rs2.getString("Player1Name"), rs2.getString("Player2Name"), rs2.getString("Player1Score"), rs2.getString("Player2Score")});
            }
            connection.close();
            
            
        }
        catch (java.sql.SQLException e) {
            System.err.println(e);
            System.exit(-1);
        }
        return scroll;
    }
}
