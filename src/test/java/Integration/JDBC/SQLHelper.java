package Integration.JDBC;

import Entity.Trip;
import Entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class SQLHelper extends JDBC.SQLHelper {

    @Override
    public void setDName(){
        dName = "CSC536Test";
    }


}


