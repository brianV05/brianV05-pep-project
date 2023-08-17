package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class MessageDAO {

    public Message inserMessage(Message message){
        Connection conn = ConnectionUtil.getConnection();

        String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch VALUES(?,?,?))";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1,message.getPosted_by());
            ps.setString(2,message.getMessage_text());
            ps.setLong(3,message.getTime_posted_epoch());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            while(rs.next()){
                int generated_message_id = (int)rs.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            e.printStackTrace();
          
        }
        return null;

    }





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


    public Message updateMessage(int id, String message){
        Connection conn = ConnectionUtil.getConnection();
        Message updateMess = retrieveAllMessagesByid(id);
        if(updateMess == null) return null;
        Message updateMe = null;

        try {
            String sql = "UPDATE Message SET posted_by = ?, message_text = ?, time_posted_epoch = ? WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
           
            ps.setString(2,message);
            ps.setInt(4,id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Message messa = new Message(rs.getInt("message_id"),rs.getInt("posted_by"), rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                return messa;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateMe;

    }









    
}
