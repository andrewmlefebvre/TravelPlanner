package JDBC;

import lombok.Getter;

import java.sql.*;

@Getter
public class JConnection {

    public static Connection getConnection(String dName){
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/"+dName,
                    "root", "password1999");
        }catch(Exception e){
            e.printStackTrace();
        }
        return con;
    }

}
