package Integration.sql;

import Entity.*;
import Integration.JDBC.SQLHelper;

import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;


public class WorkFlowTest {

    SQLHelper sql;

    public static final Location location = new Location("45 Upper College RD", "Kingston", "RI", "02881", "USA");


    @Before
    public void before(){
        sql = new SQLHelper();
        sql.wipe();
    }

    @Test
    public void testBareCreationWorkFlow(){
        //Primary user
        User primaryUser = new User(null, "Andrew", "Lefebvre", new Date(1999, 05, 03), "21 Test Ave Kingston RI 02882", "User1", "password");
        sql.addUser(primaryUser);

        //Friends of user
        User friend1 = new User(null, "Friend1", "Test", new Date(1999, 05, 03), "21 Test Ave Kingston RI 02882", "User2", "password");
        User friend2 = new User(null, "Friend2", "Test", new Date(1999, 05, 03), "21 Test Ave Kingston RI 02882", "User3", "password");
        sql.addUser(friend1);
        sql.addUser(friend2);

        //Setting friends
        sql.setFriend(primaryUser, friend1);
        sql.setFriend(primaryUser, friend2);

        //Adding trip
        Trip trip = new Trip(null, "Trip1", new Date(2000, 01, 02), new Date(2000, 01, 05));
        sql.addTrip(trip);
        sql.addTripToUser(trip, primaryUser);
        sql.addTripToUser(trip, friend1);

        //Add events to trip
        Event event1 = new Activity(null, "Event1", location, trip);
        Event event2 = new Transportation(null, "Event2", location, trip);
        Event event3 = new Activity(null, "Event3", location, trip);
        sql.addEvent(event1);
        sql.addEvent(event2);
        sql.addEvent(event3);

        //Friends check
        assertTrue(sql.getAllUsers().size() == 3);
        assertTrue(sql.getFriendsOfUser(primaryUser).size() == 2);
        assertTrue(sql.getFriendsOfUser(primaryUser).stream().filter(f -> f.getFirstName().equalsIgnoreCase("Friend2")).count() == 1);


        //Trip Check
        assertTrue(sql.getAllTrips().size() == 1);
        assertTrue(sql.getTripsFromUser(primaryUser).size() == 1);
        assertTrue(sql.getTripsFromUser(friend2).size() == 0);

        //Event Check
        assertTrue(sql.getEventSubtypes().size() == 3);
        assertTrue(sql.getEventsFromTrip(trip).size() == 3);
        assertTrue(sql.getEventsFromTrip(trip).stream().filter(e -> e.getSubtype() == Event_Subtype.Activity).count() == 2);
        assertTrue(sql.getEventsFromTrip(trip).stream().filter(e -> e.getSubtype() == Event_Subtype.Transportation).count() == 1);
        assertTrue(sql.getEventsFromTrip(trip).stream().filter(e -> e.getSubtype() == Event_Subtype.Dwelling).count() == 0);
        assertTrue(sql.getAllEvents().size() == 3);
    }
}
