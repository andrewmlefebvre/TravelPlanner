package Rest;

import Entity.*;
import JDBC.SQLHelper;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

import static Entity.Event_Subtype.Dwelling;

@Controller
public class RestActions {

    @GetMapping("/HelloWorld")
    @ResponseBody
    public String getTest(){
        return "Hello World";
    }

    //-----------------------CREATE------------------------------
    @GetMapping("/api/create/user/{first}/{last}/{dob}/{address}")
    @ResponseStatus(HttpStatus.CREATED)
    public static @ResponseBody User createUser(@PathVariable("first") String first,
                                  @PathVariable("last") String last,
                                  @PathVariable("dob") @DateTimeFormat(pattern = "yyyy-MM-dd")  java.util.Date dob,
                                  @PathVariable("address") String address){
        System.out.println(dob);
        try{
            SQLHelper sql = new SQLHelper();
            User user = new User(null, first, last, new Date(dob.getTime()), address);
            sql.addUser(user);
            return user;
        }catch(Exception e){
            throw e;
        }
    }

    @GetMapping("/api/create/userfriend/{user1}/{user2}")
    @ResponseStatus(HttpStatus.CREATED)
    public static void createUserFriend(@PathVariable("user1") Integer user1ID, @PathVariable("user2") Integer user2ID){
        try{
            SQLHelper sql = new SQLHelper();
            User user1 = sql.getUserWithID(user1ID);
            User user2 = sql.getUserWithID(user2ID);
            sql.setFriend(user1, user2);
        }catch(Exception e){
            throw e;
        }
    }

    @GetMapping("/api/create/trip/{name}/{arrival}/{departal}/{userID}")
    @ResponseStatus(HttpStatus.CREATED)
    public static @ResponseBody Trip createTrip(@PathVariable("name") String name,
                                                @PathVariable("arrival") Date arrival,
                                                @PathVariable("departal") Date departal,
                                                @PathVariable("userID") Integer userID){
        try {
            SQLHelper sql = new SQLHelper();
            Trip trip = new Trip(null, name, arrival, departal);
            sql.addTrip(trip);
            User user = sql.getUserWithID(userID);
            sql.addTripToUser(trip, user);
            return trip;
        }catch(Exception e){
            throw e;
        }
    }

    @GetMapping("/api/create/event/{subtype}/{name}/{street}/{city}/{state}/{postal}/{country}/{tripID}")
    @ResponseStatus(HttpStatus.CREATED)
    public static @ResponseBody Event createEvent(@PathVariable("subtype") String subtype,
                                                  @PathVariable("name") String name,
                                                  @PathVariable("street") String street,
                                                  @PathVariable("city") String city,
                                                  @PathVariable("state") String state,
                                                  @PathVariable("postal") String postal,
                                                  @PathVariable("country") String coountry,
                                                  @PathVariable("tripID") Integer tripID){
        try {
            SQLHelper sql = new SQLHelper();
            Trip trip = sql.getAllTrips().stream().filter(t -> t.getID() == tripID).findFirst().get();
            Event event = null;
            if(subtype.equalsIgnoreCase("activity")) {
                event = new Activity(null, name, new Location(street, city, state, postal, coountry), trip);
            }else if(subtype.equalsIgnoreCase("transportation")){
                event = new Transportation(null, name, new Location(street, city, state, postal, coountry), trip);
            }

            if(event != null){
                sql.addEvent(event);
                return event;
            }
            return null;
        }catch(Exception e){
            throw e;
        }
    }
    //-----------------------CREATE------------------------------
    //-----------------------GET---------------------------------
    @GetMapping("/api/get/allusers")
    @ResponseStatus(HttpStatus.OK)
    public static @ResponseBody List<User> getAllUsers(){
        SQLHelper sql = new SQLHelper();
        return sql.getAllUsers();
    }

    @GetMapping("/api/get/alltrips")
    @ResponseStatus(HttpStatus.OK)
    public static @ResponseBody List<Trip> getAllTrips(){
        SQLHelper sql = new SQLHelper();
        return sql.getAllTrips();
    }

    @GetMapping("/api/get/tripsbyuser/{userID}")
    @ResponseStatus(HttpStatus.OK)
    public static @ResponseBody List<Trip> getAllTrips(@PathVariable("userID") Integer userID){
        SQLHelper sql = new SQLHelper();
        User user = sql.getUserWithID(userID);
        return sql.getTripsFromUser(user);
    }

    @GetMapping("/api/get/friendsbyuser/{userID}")
    @ResponseStatus(HttpStatus.OK)
    public static @ResponseBody List<User> getAllFriends(@PathVariable("userID") Integer userID){
        SQLHelper sql = new SQLHelper();
        User user = sql.getUserWithID(userID);
        return sql.getFriendsOfUser(user);
    }

    @GetMapping("/api/get/userswithname/{first}/{last}")
    @ResponseStatus(HttpStatus.OK)
    public static @ResponseBody List<User> getUsersWithName(@PathVariable("first") String first,
                                                            @PathVariable("last") String last){
        SQLHelper sql = new SQLHelper();
        return sql.getUsersWithName(first, last);
    }

    @GetMapping("/api/get/eventsfromtrip/{tripID}")
    @ResponseStatus(HttpStatus.OK)
    public static @ResponseBody List<Event> getEventsFromTrip(@PathVariable("tripID") Integer tripID){
        SQLHelper sql = new SQLHelper();
        Trip trip = sql.getAllTrips().stream().filter(t -> t.getID() == tripID).findAny().orElse(null);
        return sql.getEventsFromTrip(trip);
    }
    //-----------------------GET---------------------------------
    //-----------------------UPDATE---------------------------------
    //-----------------------UPDATE---------------------------------
}
