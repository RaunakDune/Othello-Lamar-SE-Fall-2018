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

class GameDB {

    private String username;
    private String password;
    private Scanner sc;
    String[] words;
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
}
