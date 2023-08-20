package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class MessageDAO {

    public Message inserMessage(Message message){
        //obtains a database connection
        Connection conn = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES(?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);                     //The PreparedStatement is set up to return the generated keys (typically auto-generated IDs) after the insert is executed.

            // Set the values for the placeholders in the SQL statement
            ps.setInt(1,message.getPosted_by());
            ps.setString(2,message.getMessage_text());
            ps.setLong(3,message.getTime_posted_epoch());

            // Execute the SQL statement and get the number of rows affected
            int rowAffected = ps.executeUpdate();

            // If one row was affected (insert was successful
            if(rowAffected == 1){                               //the insertion was successful (one row affected), it retrieves the generated keys using getGeneratedKeys()
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()){                                  // contain a next row, return message id
                    int messageid = rs.getInt(1);     // Get the generated message ID from the result set
                    message.setMessage_id(messageid);             // Set the generated message ID in the message object
                    return message;                                // Return the message object with the updated ID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
          
        }
        return null;
    }


    public List<Message> retrieveAllMessages(){
        //based on the labs week 5 and week 6
        //creatint the connection to the database 
        Connection conn = ConnectionUtil.getConnection();
        
        //making a list
        List<Message> message = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Message";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();

            while(rs.next()){
                Message mess = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"), 
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));
                message.add(mess);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        return message;
    }

    public Message retrieveAllMessagesByid(int id){
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"), 
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));
                return message;
            }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return null;
    }

    public boolean deleteMessageByID(int messageID){
        //establishes a database connection
        Connection conn = ConnectionUtil.getConnection();

        try {
            String sql = "DELETE FROM Message WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);                      // Prepared statements are used to execute SQL queries with parameters.
            ps.setInt(1, messageID);                                 //setting the value of the first parameter

            int rowDeleted = ps.executeUpdate();

            if(rowDeleted == 0){                                                    //If no rows were deleted (meaning there was no message with the given ID in the database)
                return false;
            }else{  
                return true;                                                        //If one or more rows were deleted, indicating a successful deletion, the method returns true.
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
            
        }

    }



    public void updateMessage(int id, Message message){
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,message.getMessage_text());
            ps.setInt(2,id);
            ps.executeUpdate();
           
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    
    }

        /* 
        Connection conn = ConnectionUtil.getConnection();
        Message updateMess = retrieveAllMessagesByid(id);
        if(updateMess == null) return null;
       // Message updateMe = null;

        try {
            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
           
            ps.setString(1,message);
            ps.setInt(2,id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Message messa = new Message(rs.getInt("message_id"),rs.getInt("posted_by"), rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                return messa;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

        */

    


    public List<Message> getUserID(int id){
        Connection conn = ConnectionUtil.getConnection();                               // Get a database connection
        try {
            List<Message> listMessages = new ArrayList<>();                             // Create a list to store the fetched messages  
            String sql = "SELECT * FROM message WHERE posted_by = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();                                           // Execute the SQL query and get the result set


            while(rs.next()){                                                           //// Loop through the result set to process each retrieved message, 'rs' contains the retrieved rows from the database.
                int messageId = rs.getInt("message_id");
                int postBy = rs.getInt("posted_by");
                String messageText = rs.getString("message_text");
                Long timePostedEpoch = rs.getLong("time_posted_epoch");

                // Create a new Message object with retrieved data and add it to the list
                Message message = new Message(messageId, postBy, messageText, timePostedEpoch);
                listMessages.add(message);
                
            }
            return listMessages;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
       
    }









    
}
