package JDBC;

import API.APIUtil;
import API.LocationCoords;
import API.Nearby;
import API.Weather;
import Entity.*;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SQLHelper {

    public String dName;

    public SQLHelper(){
        setDName();
    }

    public void wipe(){
        String q = "CALL WIPE();";
        log(q);
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            p.execute(q);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getDobString(Date date){
        return String.valueOf(date.getYear())+"-"+String.valueOf(date.getMonth())+"-"+String.valueOf(date.getDate());
    }

    public static void log(String s){
        System.out.println(s);
    }

    public void setDName(){
        dName = "CSC536";
    }

    public List<User> getAllUsers(){
        List<User> out = new LinkedList<>();
        String q = "SELECT * FROM USER;";
        log(q);
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                out.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return out;
    }

    public List<User> getUsersWithName(String first, String last){
        List<User> out = new LinkedList<>();
        String q = "SELECT * FROM USER WHERE USER.firstName = '"+first+"' and USER.lastName = '"+last+"';";
        log(q);
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                out.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return out;
    }
    public List<Trip> getTripsFromUser(User user){
        List<Trip> out = new LinkedList<>();
        String q = "SELECT TRIP.* FROM USER JOIN USERTRIP on USERTRIP.userID = USER.ID JOIN TRIP on TRIP.ID = USERTRIP.tripID WHERE USER.ID = "+user.getID()+";";
        log(q);
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
    public List<Trip> getTripWithID(Integer ID){
        List<Trip> out = new LinkedList<>();
        String q = "SELECT TRIP.* FROM TRIP WHERE TRIP.ID = "+ID+";";
        log(q);
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

    public APIInformation getAPIFromEvent(Event e){
        String q = "SELECT APIINFORMATION.* FROM APIINFORMATION WHERE APIINFORMATION.eventID = "+e.getID()+";";
        log(q);
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            ResultSet rs = p.executeQuery();
            rs.next();
            return new APIInformation(rs.getInt(1), rs.getInt(2), rs.getFloat(3), rs.getFloat(4), rs.getFloat(5), rs.getString(6), rs.getFloat(7), rs.getFloat(8), rs.getFloat(9));
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    public void addUser(User user){
        if(user.getID() != null) return;
        String q = "INSERT INTO USER VALUES (null, '"+user.getFirstName()+"', '"+user.getLastName()+"', '"+getDobString(user.getDob())+"', '"+user.getAddress()+"','"+user.getUserName()+"','"+user.getPassword()+"');";
        log(q);
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
        String q = "SELECT * FROM EVENT_SUBTYPES;";
        log(q);
        List<String> out = new LinkedList<>();
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
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
        String q = "SELECT FRIENDS.friendID FROM FRIENDS JOIN USER on USER.ID = FRIENDS.friendID WHERE FRIENDS.userID = "+user.getID()+";";
        log(q);
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
        String q = "SELECT * FROM USER WHERE USER.ID ="+ID+";";
        log(q);
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                out = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5), rs.getString(6), rs.getString(7));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return out;
    }

    public void setFriend(User user, User friend){
        String q = "INSERT INTO FRIENDS VALUES (null,"+user.getID()+","+friend.getID()+");";
        log(q);
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
        String q = "INSERT INTO TRIP VALUES (null,'"+trip.getName()+"','"+String.valueOf(trip.getStartDate())+"','"+String.valueOf(trip.getEndDate())+"');";
        log(q);
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
        List<Trip> out = new LinkedList<>();
        String q = "SELECT * FROM TRIP;";
        log(q);
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
        log(q);
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            p.execute(q);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addEvent(Event event){
        String q = "INSERT INTO EVENT VALUES (null,'"+event.getSubtype()+"','"+event.getName()+"','"+event.getLocation().getStreet()+"','"+event.getLocation().getCity()+"','"+event.getLocation().getState()+"','"+event.getLocation().getPostal()+"','"+event.getLocation().getCountry()+"',"+event.getTrip().getID()+",'"+event.getStartDate()+"','"+event.getEndDate()+"');";
        log(q);
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
        addAPIInformation(event);
    }

    public List<Event> getEventsFromTrip(Trip trip){
        List<Event> out = new LinkedList<>();
        String q = "SELECT * FROM Event WHERE EVENT.tripID = "+trip.getID()+";";
        log(q);
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                if(rs.getString(2).equalsIgnoreCase("Activity")) {
                    out.add(new Activity(rs.getInt(1), rs.getString(3), new Location(rs.getString("street"), rs.getString("city"), rs.getString("state"), rs.getString("postal"), rs.getString("country")), trip, rs.getDate("startDate"), rs.getDate("endDate")));
                }else if (rs.getString(2).equalsIgnoreCase("Transportation")){
                    out.add(new Transportation(rs.getInt(1), rs.getString(3), new Location(rs.getString("street"), rs.getString("city"), rs.getString("state"), rs.getString("postal"), rs.getString("country")), trip, rs.getDate("startDate"), rs.getDate("endDate")));
                }else if (rs.getString(2).equalsIgnoreCase("Dwelling")){
                    out.add(new Dwelling(rs.getInt(1), rs.getString(3), new Location(rs.getString("street"), rs.getString("city"), rs.getString("state"), rs.getString("postal"), rs.getString("country")), trip, rs.getDate("startDate"), rs.getDate("endDate")));
                }else{
                    throw new Exception("UNDEFINED EVENT TYPE");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        out.sort((e1, e2) -> e1.getStartDate().compareTo(e2.getStartDate()));
        return out;
    }

    public void addAPIInformation(Event event){
        APIInformation i = new APIInformation(event);
        String q = "INSERT INTO APIINFORMATION VALUES (null, "+event.getID()+", null, null, null, null, null, null, null);";
        log(q);
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
        i.setID(ID);
    }

    public List<Event> getAllEvents(){
        List<Event> out = new LinkedList<>();
        String q = "SELECT * FROM Event;";
        log(q);
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                int tripID = rs.getInt("tripID");
                Trip trip = getAllTrips().stream().filter(t -> t.getID() == tripID).findAny().get();
                if(rs.getString(2).equalsIgnoreCase("Activity")) {
                    out.add(new Activity(rs.getInt(1), rs.getString(3), new Location(rs.getString("street"), rs.getString("city"), rs.getString("state"), rs.getString("postal"), rs.getString("country")), trip, rs.getDate("startDate"), rs.getDate("endDate")));
                }else if (rs.getString(2).equalsIgnoreCase("Transportation")){
                    out.add(new Transportation(rs.getInt(1), rs.getString(3), new Location(rs.getString("street"), rs.getString("city"), rs.getString("state"), rs.getString("postal"), rs.getString("country")), trip, rs.getDate("startDate"), rs.getDate("endDate")));
                }else if (rs.getString(2).equalsIgnoreCase("Dwelling")){
                    out.add(new Dwelling(rs.getInt(1), rs.getString(3), new Location(rs.getString("street"), rs.getString("city"), rs.getString("state"), rs.getString("postal"), rs.getString("country")), trip, rs.getDate("startDate"), rs.getDate("endDate")));
                }else{
                    throw new Exception("UNDEFINED EVENT TYPE");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return out;
    }

    public APIInformation getAPIInformationFromEvent(Event event){
        APIInformation out = null;
        String q = "SELECT * FROM APIInformation WHERE APIInformation.eventID = "+event.getID()+";";
        log(q);
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                out = new APIInformation(rs.getInt("ID"), event, rs.getFloat("lat"), rs.getFloat("lon"), rs.getFloat("temp"), rs.getString("des"), rs.getFloat("feelsLike"), rs.getFloat("UV"), rs.getFloat("wind"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return out;
    }

    public void addCoordInformation(Event event){
        LocationCoords coords = APIUtil.getCoordsForEvent(event);
        String q = "UPDATE APIINFORMATION SET lat = "+coords.getLat()+", lon = "+coords.getLon()+ " WHERE eventID ="+event.getID()+";";
        log(q);
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            p.executeUpdate(q);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addWeatherInformation(Event event){
        Weather w = APIUtil.getWeatherForEvent(event);
        String q = "UPDATE APIINFORMATION SET temp = "+w.getTemp()+", des = '"+w.getDes()+"', feelsLike = "+w.getFeelsLike()+", UV = "+w.getUV()+", wind = "+w.getWind()+" WHERE eventID ="+event.getID()+";";
        log(q);
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            p.executeUpdate(q);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public User getUserWithUserName(String userName){
        User user = null;
        String q = "SELECT User.* FROM USER WHERE USER.userName = '"+userName+"';";
        log(q);
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5), rs.getString(6), rs.getString(7));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }

    public User login(String userName, String password){
        User user = null;
        String q = "SELECT user.* FROM user WHERE user.userName = '"+userName+"' and user.password = '"+password+"';";
        log(q);
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5), rs.getString(6), rs.getString(7));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }

    public void updateUser(User user){
        String q = "UPDATE USER SET firstName = '"+user.getFirstName()+"', lastName = '"+user.getLastName()+"', userName = '"+user.getUserName()+"' WHERE USER.ID = "+user.getID()+";";
        log(q);
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            p.executeUpdate(q);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void removeFriend(User user1, User user2){
        String q = "DELETE FROM FRIENDS WHERE userID = "+user1.getID()+" and friendID = "+user2.getID()+";";
        log(q);
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            p.executeUpdate(q);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void addAdvisorInfo(List<Nearby> info, Event event){
        for(Nearby n : info){
            String q = "INSERT INTO NEARBY VALUES(null, '"+n.getName()+"','"+n.getAddress()+"','"+n.getDes()+"',"+event.getID()+");";
            log(q);
            try{
                Connection con = JConnection.getConnection(dName);
                PreparedStatement p = con.prepareStatement(q);
                p.executeUpdate(q, Statement.RETURN_GENERATED_KEYS);
                ResultSet rs = p.getGeneratedKeys();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public List<Nearby> getNearby(Event event){
        List<Nearby> out = new LinkedList<>();
        String q = "SELECT * FROM NEARBY WHERE eventID = "+event.getID()+";";
        log(q);
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                out.add(new Nearby(rs.getString("name"), rs.getString("address"), rs.getString("des")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return out;
    }

    public Event getEventByID(Integer ID){
        String q = "SELECT * FROM EVENT WHERE ID = "+ID+";";
        log(q);
        Event event = null;
        try{
            Connection con = JConnection.getConnection(dName);
            PreparedStatement p = con.prepareStatement(q);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                if(rs.getString(2).equalsIgnoreCase("Activity")) {
                    event = (new Activity(rs.getInt(1), rs.getString(3), new Location(rs.getString("street"), rs.getString("city"), rs.getString("state"), rs.getString("postal"), rs.getString("country")), null, rs.getDate("startDate"), rs.getDate("endDate")));
                }else if (rs.getString(2).equalsIgnoreCase("Transportation")){
                    event = (new Transportation(rs.getInt(1), rs.getString(3), new Location(rs.getString("street"), rs.getString("city"), rs.getString("state"), rs.getString("postal"), rs.getString("country")), null, rs.getDate("startDate"), rs.getDate("endDate")));
                }else if (rs.getString(2).equalsIgnoreCase("Dwelling")){
                    event = (new Dwelling(rs.getInt(1), rs.getString(3), new Location(rs.getString("street"), rs.getString("city"), rs.getString("state"), rs.getString("postal"), rs.getString("country")), null, rs.getDate("startDate"), rs.getDate("endDate")));
                }else{
                    throw new Exception("UNDEFINED EVENT TYPE");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return event;
    }


}


