package DAO;

//adding Service class and connection to database to AccountDAO class
//DAO class will need the information from service and util
import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;


public class AccountDAO {
    /* 
    //this is private becuase we dont want other people getting through our connection
    private Connection conn;

    //making a constructor for the connection to the database
    //
    public AccountDAO(){
        this.conn =  ConnectionUtil.getConnection();
    }
    */

    public Account InsertnewUser(Account account){
        //1. Get a connection  
        Connection conn = ConnectionUtil.getConnection();

         //Create a Statement
        String sql = "INSERT INTO Account (username, password) VALUES (? , ?);";

        //preparing the sql query into the database
        try(PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setString(1, account.getUsername());
            ps.setString(2,account.getPassword());

            //excute query
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0){
                try(ResultSet generatedKeys = ps.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        int account_id = generatedKeys.getInt(1);
                        account.setAccount_id(account_id);
                        return account;
                    }
                }
            }
            //excute query
            //ps.executeUpdate();

            //process result
            //ResultSet rs = ps.getGeneratedKeys();

            //close connection
            //if(rs.next()){
                //return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                //int generated_account_id = (int) rs.getLong(1);
                //return new Account(generated_account_id, account.getUsername(), account.getPassword());
            //}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
// This will relate to the method 'accountExist' in the ServiceAccount java file (checking if account already exist)
    public boolean accountExist(String userName){
        Connection conn = ConnectionUtil.getConnection();

        //use to look through the table 
        String sql = "SELECT * FROM account WHERE username = ?";

        try( PreparedStatement ps = conn.prepareStatement(sql)) {
            //PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1,userName);
            
            //ResultSet to excute the query
            try(ResultSet resultSet = ps.executeQuery()){
                if(resultSet.next()){
                    // account id, which starts at 1, returning if there is at least 1 account in table
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


//Back in our ServiceAccount, we used two parameters for the method; verifyAccount. we will do the same for this since we are calling it in our method
    public Account verifyAccount(String username, String password){ //grab username and password from the front-end
        //make a connection
        Connection conn = ConnectionUtil.getConnection();

        //create statement 
        String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
        
            ps.setString(1,username);
            ps.setString(2,password);

        //execute query
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return new Account(rs.getInt("account_id"),rs.getString("username"), rs.getString("password"));
        }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    public Account getAccountById(int accountid){
        Connection conn = ConnectionUtil.getConnection();

        String sql = "SELECT * FROM account WHERE account_id = ? ";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
        
            ps.setInt(1,accountid);
            ResultSet rs = ps.executeQuery();
    
            if(rs.next()){
                String username = rs.getString("username");
                String password = rs.getString("password");
                return new Account(accountid,username, password);

                
            }
    
            
        } catch (SQLException e) {
            e.printStackTrace();
            
        }

        return null;

       

    }

















    

}
