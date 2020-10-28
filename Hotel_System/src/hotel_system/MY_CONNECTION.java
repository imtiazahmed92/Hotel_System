
package hotel_system;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Imtiaz Ahmed
 */
public class MY_CONNECTION {
    
    public Connection createConnection(){
        Connection connection = null;
        
        MysqlDataSource mds = new MysqlDataSource();
        mds.setServerName("localhost");
        mds.setPortNumber(3306);
        mds.setUser("root");
        mds.setPassword("");
        mds.setDatabaseName("hotel_db");
    
        try{
        connection = (Connection) mds.getConnection();
    }catch(SQLException ex){
        Logger.getLogger(MY_CONNECTION.class.getName()).log(Level.SEVERE,null,ex);
    }
        return connection;
    
}
    
}
