package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class MessageDAO {





    public List<Message> retrieveAllMessages(){
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
    
}
