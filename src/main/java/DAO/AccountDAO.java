package DAO;

//adding Service class and connection to database to AccountDAO class
//DAO class will need the information from service and util
import Model.Account;
import Util.ConnectionUtil;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountDAO {

    public Account InsertnewUser(Account account){
       // 1. Get a connection  
        Connection conn = ConnectionUtil.getConnection();
        try {
            //Create a Statement
            String sql = "INSERT INTO Account (username,  password) VALUES (? , ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, account.getUsername());
            ps.setString(2,account.getPassword());

            //excute query
            ps.executeUpdate();

            //process result
            ResultSet rs = ps.getGeneratedKeys();

            //close connection
            while(rs.next()){
                account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return account;

    }
   


    
    
}
