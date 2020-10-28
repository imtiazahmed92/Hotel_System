package dbsconnection;
import java.sql.*;

/**
 *
 * @author Imtiaz Ahmed
 */
public class DbsConnection {
    Connection conn = null;
    ResultSet rs= null;
    Statement st = null;
    
   
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection("jdbc")
            
            
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
