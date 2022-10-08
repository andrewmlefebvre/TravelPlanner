package JDBC;

import Entity.*;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class SQLHelper {

    public String dName;


    public static final String getEVENT_SUBTYPES = "SELECT * FROM EVENT_SUBTYPES";
    public static final String getEVENTS = "SELECT * FROM EVENT";
    public static final String getTRIPS = "SELECT * FROM TRIP";
    public static final String getUSERS = "SELECT * FROM USER";
    public static final String getNEARBY = "SELECT * FROM NEARBY";
    public static final String getFRIENDS = "SELECT * FROM FRIENDS";
    public static final String getAPIINFORMATION = "SELECT * FROM APIINFORMATION";

    public SQLHelper(){
        setDName();
    }

    public void wipe(){
        String q = "CALL WIPE();";
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            p.execute(q);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setDName(){
        dName = "CSC536";
    }

    public List<User> getAllUsers(){
        List out = new LinkedList<>();
        String q = "SELECT * FROM USER";
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                out.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5)));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return out;
    }

    public List<User> getUsersWithName(String first, String last){
        List out = new LinkedList<>();
        String q = "SELECT * FROM USER WHERE USER.firstName = '"+first+"' and USER.lastName = '"+last+"';";
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                out.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5)));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return out;
    }
    public List<Trip> getTripsFromUser(User user){
        List out = new LinkedList<>();
        String q = "SELECT TRIP.* FROM USER LEFT JOIN USERTRIP on USERTRIP.userID = USER.ID LEFT JOIN TRIP on TRIP.ID = USERTRIP.tripID WHERE USER.ID = "+user.getID()+";";
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            ResultSet rs = p.executeQuery();
            while(rs.next() && rs.getInt(1) != 0){
                out.add(new Trip(rs.getInt(1), rs.getString(2),rs.getDate(3), rs.getDate(4)));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return out;
    }
    public void addUser(User user){
        if(user.getID() != null) return;
        String q = "INSERT INTO USER VALUES (null, '"+user.getFirstName()+"', '"+user.getLastName()+"', '"+user.getDob()+"', '"+user.getAddress()+"')";
        Integer ID = null;
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            p.executeUpdate(q, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = p.getGeneratedKeys();
            while(rs.next()) ID = rs.getInt(1);
        }catch(Exception e){
            e.printStackTrace();
        }
        user.setID(ID);
    }

    public List<String> getEventSubtypes(){
        List out = new LinkedList<>();
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(getEVENT_SUBTYPES);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                out.add(rs.getString("subtype"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return out;
    }

    public List<User> getFriendsOfUser(User user){
        List<User> out = new LinkedList<>();
        String q = "SELECT FRIENDS.friendID FROM FRIENDS JOIN USER on USER.ID = FRIENDS.friendID WHERE FRIENDS.userID = "+user.getID();
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                out.add(getUserWithID(rs.getInt("friendID")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return out;
    }

    public User getUserWithID(int ID){
        User out = null;
        String q = "SELECT * FROM USER WHERE USER.ID ="+ID;
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                out = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return out;
    }

    public void setFriend(User user, User friend){
        String q = "INSERT INTO FRIENDS VALUES (null,"+user.getID()+","+friend.getID()+");";
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            p.execute(q);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addTrip(Trip trip){
        if(trip.getID() != null) return;
        String q = "INSERT INTO TRIP VALUES (null,'"+trip.getName()+"','"+trip.getStartDate()+"','"+trip.getEndDate()+"');";
        Integer ID = null;
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            p.executeUpdate(q, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = p.getGeneratedKeys();
            while(rs.next()) ID = rs.getInt(1);
        }catch(Exception e){
            e.printStackTrace();
        }
        trip.setID(ID);
    }

    public List<Trip> getAllTrips(){
        List out = new LinkedList<>();
        String q = "SELECT * FROM TRIP";
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                out.add(new Trip(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getDate(4)));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return out;
    }

    public void addTripToUser(Trip trip, User user){
        String q = "INSERT INTO USERTRIP VALUES (null,"+user.getID()+","+trip.getID()+");";
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            p.execute(q);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addEvent(Event event){
        String q = "INSERT INTO EVENT VALUES (null,'"+event.getSubtype()+"','"+event.getName()+"','"+event.getLocation()+"',"+event.getTrip().getID()+");";
        Integer ID = null;
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            p.executeUpdate(q, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = p.getGeneratedKeys();
            while(rs.next()) ID = rs.getInt(1);
        }catch(Exception e){
            e.printStackTrace();
        }
        event.setID(ID);
    }

    public List<Event> getEventsFromTrip(Trip trip){
        List out = new LinkedList<>();
        String q = "SELECT * FROM Event WHERE EVENT.tripID = "+trip.getID()+";";
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            ResultSet rs = p.executeQuery();
            Event_Subtype type;
            while(rs.next()){
                if(rs.getString(2).equalsIgnoreCase("Activity")) {
                    out.add(new Activity(rs.getInt(1), rs.getString(3), rs.getString(4), trip));
                }else if (rs.getString(2).equalsIgnoreCase("Transportation")){
                    out.add(new Transportation(rs.getInt(1), rs.getString(3), rs.getString(4), trip));
                } else{
                    throw new Exception("UNDEFINED EVENT TYPE");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return out;
    }


}


