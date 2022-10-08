package Rest;

import Entity.Trip;
import Entity.User;
import JDBC.SQLHelper;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@Controller
public class RestActions {

    @GetMapping("/HelloWorld")
    @ResponseBody
    public String getTest(){
        return "Hello World";
    }

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

    @GetMapping("/api/create/userfriend")
    @ResponseStatus(HttpStatus.CREATED)
    public static void createUserFriend(Integer user1ID, Integer user2ID){
        try{
            SQLHelper sql = new SQLHelper();
            User user1 = sql.getUserWithID(user1ID);
            User user2 = sql.getUserWithID(user2ID);
            sql.setFriend(user1, user2);
        }catch(Exception e){
            throw e;
        }
    }

    @GetMapping("/api/create/trip")
    @ResponseStatus(HttpStatus.CREATED)
    public static @ResponseBody Trip createTrip(String name, Date arrival, Date departal){
        try {
            SQLHelper sql = new SQLHelper();
            Trip trip = new Trip(null, name, arrival, departal);
            sql.addTrip(trip);
            return trip;
        }catch(Exception e){
            throw e;
        }
    }
}
