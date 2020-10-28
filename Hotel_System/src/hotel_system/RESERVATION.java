
package hotel_system;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Imtiaz Ahmed
 */
public class RESERVATION {
    
    //in reservation we need two FOREIGN keys
    // 1 for the client 
    // -> ALTER TABLE reservations ADD CONSTRAINT fk_client_id FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE
    // 2 for the room
    // -> ALTER TABLE reservations ADD CONSTRAINT fk_room_number FOREIGN KEY (room_number) REFERENCES rooms(r_number) ON DELETE CASCADE
    //and add another foriegn key between Table types and rooms
    // -> ALTER TABLE rooms ADD CONSTRAINT fk_type_id FOREIGN KEY (type) REFERENCES type(id) ON DELETE CASCADE
    
    
    //some fixes we need to do
    // 1- when we addd a new reservation the room  associated with it should be set to reserved = YES
    // and when we deleting the reservation it should be set to reserved = NO
    // 2 - when we addd a new reservation we need to chek if the room is already reserved
    // 3 - chek if the date in > the current date
    // 4 - chek if the date in > the date in
    
    
    
    MY_CONNECTION my_connection = new MY_CONNECTION();
    ROOMS room = new ROOMS();
    
    //create a function to add a new room
     public boolean addReservation(int client_id, int room_number, String dateIn , String dateOut)
    {
        PreparedStatement st;
        String addQuery = "INSERT INTO `reservations`(`client_id`, `room_number`, `date_in`, `date_out`) VALUES (?,?,?,?)";
        
        try {
            
            st = my_connection.createConnection().prepareStatement(addQuery);
            st.setInt(1, client_id);
            st.setInt(2, room_number);
            st.setString(3, dateIn);
            st.setString(4, dateOut);
            
            
            if(room.isRoomReserved(room_number).equals("No"))
            {
                if(st.executeUpdate()>0)
                    {
                        room.setRoomToReserved(room_number, "YES");
                        return true;
                    }
                    else{

                        return false;
                    }
            }
            else{
                JOptionPane.showMessageDialog(null,"This Room Is Already Reservation", "Room Reserved",JOptionPane.WARNING_MESSAGE);
                     
                return false;
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(CLIENT.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
    
     
    //create a funtion to edit the selected room
    public boolean editReservation(int reservation_id,int client_id , int room_number, String dateIn, String dateOut)
    {
        PreparedStatement st;
        String updateQuery = "UPDATE `reservations` SET `client_id`=?,`room_number`=?,`date_in`=?,`date_out`=? WHERE `id`=?";
        
        try {
            
            st = my_connection.createConnection().prepareStatement(updateQuery);
            
            st.setInt(1, client_id);
            st.setInt(2, room_number);
            st.setString(3, dateIn);
            st.setString(4, dateOut);
            st.setInt(5, reservation_id);
            
            return (st.executeUpdate()>0);
           
            
            } 
            catch (SQLException ex) {
            Logger.getLogger(CLIENT.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
    
    //create a funtion to remove the selected Reservation
    public boolean removeReservation(int reservation_id)
    {
        PreparedStatement st;
        String deleteQuery = "DELETE FROM `reservations` WHERE `id` =?";
        
        try {
            
            st = my_connection.createConnection().prepareStatement(deleteQuery);
            
            st.setInt(1, reservation_id);
            
            //we need to get room number before deleting the reservation
            int room_number =  getRoomNumberFromReservation(reservation_id);
            
            
            if(st.executeUpdate()>0)
            {
                room.setRoomToReserved(room_number, "NO");
                return true;
            }
            else{
                
                return false;
            }
           
            
            } 
            catch (SQLException ex) {
            Logger.getLogger(CLIENT.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
    
     //create a function to display all Reservation in jTable
    public void fillReservationsJTable(JTable table)
    {
        PreparedStatement ps;
        ResultSet rs;
        
        String selectQuery = "SELECT * FROM `reservations`";
        
        try {
            ps = my_connection.createConnection().prepareStatement(selectQuery);
            
            rs = ps.executeQuery();
            
            DefaultTableModel tableModel=(DefaultTableModel)table.getModel();
            Object[] row;
            
            while(rs.next())
            {
                row = new Object[5];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getInt(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                
                tableModel.addRow(row);
                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(CLIENT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //create a function to get the room number from a reservation
    public int getRoomNumberFromReservation(int reservationID)
    {
        PreparedStatement ps;
        ResultSet rs;
        
        String selectQuery = "SELECT `room_number` FROM `reservations` WHERE `id`=?";
        
        try {
            ps = my_connection.createConnection().prepareStatement(selectQuery);
            ps.setInt(1, reservationID);
            
            rs = ps.executeQuery();
            
           if(rs.next())
           {
               return rs.getInt(1);
           }
           else{
               return 0;
           }
        } catch (SQLException ex) {
            Logger.getLogger(CLIENT.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    
    }
    
     
}
