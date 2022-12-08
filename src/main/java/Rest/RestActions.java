package Rest;

import API.*;
import Entity.*;
import JDBC.SQLHelper;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class RestActions {

    @GetMapping("/HelloWorld")
    @ResponseBody
    public String getTest(){
        return "Hello World";
    }

    //-----------------------CREATE------------------------------
    @GetMapping("/api/create/user/{first}/{last}/{dob}/{address}/{userName}/{password}")
    @ResponseStatus(HttpStatus.CREATED)
    public static @ResponseBody List<User> createUser(@PathVariable("first") String first,
                                  @PathVariable("last") String last,
                                  @PathVariable("dob") @DateTimeFormat(pattern = "yyyy-MM-dd")  java.util.Date dob,
                                  @PathVariable("address") String address,
                                  @PathVariable("userName") String userName,
                                  @PathVariable("password") String password){
        System.out.println(dob);
        try{
            SQLHelper sql = new SQLHelper();
            User user = new User(null, first, last, new Date(dob.getTime()), address, userName, password);
            sql.addUser(user);
            List<User> out = new LinkedList<>();
            out.add(user);
            return out;
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

    @GetMapping("/api/create/userfriendwname/{user1}/{user2}")
    @ResponseStatus(HttpStatus.CREATED)
    public static @ResponseBody boolean createUserFriend(@PathVariable("user1") Integer user1ID, @PathVariable("user2") String user2Name){
        try{
            SQLHelper sql = new SQLHelper();
            User user1 = sql.getUserWithID(user1ID);
            User user2 = sql.getUserWithUserName(user2Name);
            if(user1 != null && user2 != null){
                sql.setFriend(user1, user2);
                return true;
            }else{
                 throw new Exception("User not found");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @GetMapping("/api/remove/userfriendwname/{user1}/{user2}")
    @ResponseStatus(HttpStatus.CREATED)
    public static @ResponseBody boolean removeUserFriend(@PathVariable("user1") Integer user1ID, @PathVariable("user2") String user2Name){
        try{
            SQLHelper sql = new SQLHelper();
            User user1 = sql.getUserWithID(user1ID);
            User user2 = sql.getUserWithUserName(user2Name);
            if(user1 != null && user2 != null){
                sql.removeFriend(user1, user2);
                return true;
            }else{
                throw new Exception("User not found");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @PostMapping("/api/create/trip/{name}/{arrival}/{departal}/{userID}")
    @ResponseStatus(HttpStatus.CREATED)
    public static @ResponseBody List<Trip> createTrip(@PathVariable("name") String name,
                                                @PathVariable("arrival") Date arrival,
                                                @PathVariable("departal") Date departal,
                                                @PathVariable("userID") Integer userID){
        try {
            SQLHelper sql = new SQLHelper();
            Trip trip = new Trip(null, name, arrival, departal);
            sql.addTrip(trip);
            User user = sql.getUserWithID(userID);
            sql.addTripToUser(trip, user);
            List<Trip> out = new LinkedList<>();
            out.add(trip);
            return out;
        }catch(Exception e){
            throw e;
        }
    }

    @GetMapping("/api/create/event2/{subtype}/{name}/{street}/{city}/{state}/{postal}/{country}/{tripID}/{startDate}/{endDate}")
    @ResponseStatus(HttpStatus.CREATED)
    public static @ResponseBody List<DisplayableEvent> createEventAndReturnAllNoFlight(@PathVariable("subtype") String subtype,
                                                                               @PathVariable("name") String name,
                                                                               @PathVariable("street") String street,
                                                                               @PathVariable("city") String city,
                                                                               @PathVariable("state") String state,
                                                                               @PathVariable("postal") String postal,
                                                                               @PathVariable("country") String country,
                                                                               @PathVariable("tripID") Integer tripID,
                                                                               @PathVariable("startDate") Date startDate,
                                                                               @PathVariable("endDate") Date endDate){

        return createEventAndReturnAll(subtype, name, street, city, state, postal, country, tripID, startDate, endDate, "");

    }

    @GetMapping("/api/create/event/{subtype}/{name}/{street}/{city}/{state}/{postal}/{country}/{tripID}/{startDate}/{endDate}/{flight}")
    @ResponseStatus(HttpStatus.CREATED)
    public static @ResponseBody List<DisplayableEvent> createEventAndReturnAll(@PathVariable("subtype") String subtype,
                                                  @PathVariable("name") String name,
                                                  @PathVariable("street") String street,
                                                  @PathVariable("city") String city,
                                                  @PathVariable("state") String state,
                                                  @PathVariable("postal") String postal,
                                                  @PathVariable("country") String country,
                                                  @PathVariable("tripID") Integer tripID,
                                                  @PathVariable("startDate") Date startDate,
                                                  @PathVariable("endDate") Date endDate,
                                                  @PathVariable("flight") String flight){
        try {
            SQLHelper sql = new SQLHelper();
            Trip trip = sql.getAllTrips().stream().filter(t -> t.getID() == tripID).findFirst().get();
            Event event = null;
            if(subtype.equalsIgnoreCase("activity")) {
                event = new Activity(null, name,  new Location(street, city, state, postal, country), trip, startDate, endDate);
            }else if(subtype.equalsIgnoreCase("transportation")){
                event = new Transportation(null, name, new Location(street, city, state, postal, country), trip, startDate, endDate, flight);
            }else if(subtype.equalsIgnoreCase("dwelling")){
                event = new Dwelling(null, name, new Location(street, city, state, postal, country), trip, startDate, endDate);
            }

            if(event != null){
                sql.addEvent(event);
                if (flight != null && flight != "") {
                    sql.addFlightNumber(flight, event);
                }
                List<Event> out = sql.getEventsFromTrip(sql.getTripWithID(tripID).get(0));
                    out.stream().forEach(e -> {
                        sql.addWeatherInformation(e);
                        sql.addCoordInformation(e);
                    });
                return out.stream().map(e -> new DisplayableEvent(e.getID(), e.getSubtype(), e.getName(), e.getLocation(), e.getTrip(), e.getStartDate(), e.getEndDate(), sql.getAPIFromEvent(e), e.getFlightNumber())).toList();
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
    public static @ResponseBody List<Trip> getTripsByUser(@PathVariable("userID") Integer userID){
        SQLHelper sql = new SQLHelper();
        User user = sql.getUserWithID(userID);
        List<Trip> out = sql.getTripsFromUser(user);
        for(User f : sql.getFriendsOfUser(sql.getUserWithID(userID))){
            out.addAll(sql.getTripsFromUser(f));
        }
        return out;
    }

    @GetMapping("/api/get/friendsbyuser/{userID}")
    @ResponseStatus(HttpStatus.OK)
    public static @ResponseBody List<User> getFriendsByUser(@PathVariable("userID") Integer userID){
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

    @GetMapping("/api/get/userswithusername/{userName}")
    @ResponseStatus(HttpStatus.OK)
    public static @ResponseBody List<User> getUsersWithUserName(@PathVariable("userName") String userName){
        SQLHelper sql = new SQLHelper();
        List<User> out = new LinkedList<>();
        out.add(sql.getUserWithUserName(userName));
        return out;
    }

    @GetMapping("/api/get/eventsfromtrip/{tripID}")
    @ResponseStatus(HttpStatus.OK)
    public static @ResponseBody List<DisplayableEvent> getEventsFromTrip(@PathVariable("tripID") Integer tripID){
        SQLHelper sql = new SQLHelper();
        Trip trip = sql.getAllTrips().stream().filter(t -> t.getID() == tripID).findAny().orElse(null);
        return sql.getEventsFromTrip(trip).stream().map(e -> new DisplayableEvent(e.getID(), e.getSubtype(), e.getName(), e.getLocation(), e.getTrip(), e.getStartDate(), e.getEndDate(), sql.getAPIFromEvent(e), e.getFlightNumber())).toList();
    }

    @GetMapping("/api/get/login/{userName}/{password}")
    @ResponseStatus(HttpStatus.OK)
    public static @ResponseBody List<User> getUsersWithUserName(@PathVariable("userName") String userName, @PathVariable("password") String password){
        SQLHelper sql = new SQLHelper();
        List<User> out = new LinkedList<>();
        out.add(sql.login(userName, password));
        return out;
    }

    @GetMapping("/api/get/nearby/{eventID}")
    @ResponseStatus(HttpStatus.OK)
    public static @ResponseBody List<Nearby> getNearby(@PathVariable("eventID") Integer ID){
        SQLHelper sql = new SQLHelper();
        Event event = sql.getEventByID(ID);
        List<Nearby> preNearby = sql.getNearby(event);
        if( preNearby.size() > 0){
            return preNearby;
        }else{
            LocationCoords c = APIUtil.getCoordsForEvent(event);
            sql.addAdvisorInfo(AdvisorAPI.getNearby(c.getLat(), c.getLon()), event);
        }

        return sql.getNearby(event);
    }

    @GetMapping("/api/get/flight/{name}")
    @ResponseStatus(HttpStatus.OK)
    public static @ResponseBody String getFlightStatus(@PathVariable("name") String name){
        return FlightAPI.getFlightInfo(name);
    }

    //-----------------------GET---------------------------------
    //-----------------------UPDATE---------------------------------
    @PutMapping("/api/update/user/{id}/{userName}/{first}/{last}/{dob}")
    @ResponseStatus(HttpStatus.OK)
    public static void updateUser(@PathVariable("userName") String userName, @PathVariable("first") String first, @PathVariable("last") String last, @PathVariable("id") Integer id, @PathVariable("dob") String dob){
        SQLHelper sql = new SQLHelper();
        User user = sql.getUserWithID(id);
        user.setUserName(userName);
        user.setFirstName(first);
        user.setLastName(last);
        sql.updateUser(user);
    }
    //-----------------------UPDATE---------------------------------
}
