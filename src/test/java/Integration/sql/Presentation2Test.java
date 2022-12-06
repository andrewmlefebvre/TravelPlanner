package Integration.sql;

import Entity.*;
import Integration.JDBC.SQLHelper;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.List;
import java.util.stream.Stream;

public class Presentation2Test {
    SQLHelper sql;

    public static final Location location = new Location("45 Upper College RD", "Kingston", "RI", "02881", "USA");

    public Date defaultDate = new Date(2000, 01, 01);


    @Before
    public void before(){
        sql = new SQLHelper();
        sql.wipe();
    }

    @Test
    public void generateSQL(){
        for(int i = 1; i < 11; i++) {
            sql.addUser(new User(null, "User"+i, "UserLastName"+i, new Date(2000, 5, 3), "21 Test Ave Kingston RI 02882", "User"+i, "password"));
        }

        for(int i = 1; i < 11; i++) {
            sql.addTrip(new Trip(null, "Trip"+i, new Date(2023, 01, 02), new Date(2023, 01, 05)));
        }
        System.out.println();
        List<Trip> trips = sql.getAllTrips();
        System.out.println();
        List<User> users = sql.getAllUsers();
        System.out.println();

        sql.setFriend(users.get(0), users.get(1));

        System.out.println();
        sql.getFriendsOfUser(users.get(0));
        System.out.println();

        for(int i = 0; i < trips.size(); i++) {
            sql.addTripToUser(trips.get(i), users.get(i));
        }
        System.out.println();
        sql.getTripsFromUser(users.get(0));
        System.out.println();
        sql.addTripToUser(trips.get(0), users.get(1));
        System.out.println();
        sql.getTripsFromUser(users.get(1));
        System.out.println();

        sql.addEvent(new Activity(null, "Event1", location, trips.get(0), defaultDate, defaultDate));
        sql.addEvent(new Transportation(null, "Transportation1", location, trips.get(0), defaultDate, defaultDate));
        sql.addEvent(new Activity(null, "Event2", location, trips.get(0), defaultDate, defaultDate));
        sql.addEvent(new Transportation(null, "Transportation2", location, trips.get(0), defaultDate, defaultDate));
        sql.addEvent(new Activity(null, "Event3", location, trips.get(0), defaultDate, defaultDate));

        System.out.println();
        List<Event> events = sql.getAllEvents();
        System.out.println();
        sql.getEventsFromTrip(trips.get(0));
        System.out.println();

        sql.addWeatherInformation(events.get(0));

        System.out.println();
        sql.getAPIInformationFromEvent(events.get(0));
        System.out.println();
    }
}
