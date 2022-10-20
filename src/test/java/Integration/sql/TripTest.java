package Integration.sql;

import Entity.Trip;
import Entity.User;
import Integration.JDBC.SQLHelper;

import org.junit.Before;
import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.*;

public class TripTest {

    SQLHelper sql;

    @Before
    public void before(){
        sql = new SQLHelper();
        sql.wipe();
    }

    @Test
    public void testAddTrip(){
        Trip trip = new Trip(null, "Trip1", new Date(2000, 01, 02), new Date(2000, 01, 05));
        Trip trip2 = new Trip(null, "Trip2", new Date(2000, 01, 02), new Date(2000, 01, 05));

        sql.addTrip(trip);
        sql.addTrip(trip2);

        assertTrue(trip.getID() == 1 && trip2.getID() == 2);
        assertTrue(sql.getAllTrips().size() == 2);
    }

    @Test
    public void testAddTripToUser(){
        Trip trip = new Trip(null, "Trip1", new Date(2000, 01, 02), new Date(2000, 01, 05));
        Trip trip2 = new Trip(null, "Trip2", new Date(2000, 01, 02), new Date(2000, 01, 05));

        sql.addTrip(trip);
        sql.addTrip(trip2);

        User user1 = new User(null, "Test1", "TEsTest", new Date(1999, 05, 03), "21 Test Ave Kingston RI 02882");
        User user2 = new User(null, "Test2", "Lefebvre", new Date(1999, 05, 03), "21 Test Ave Kingston RI 02882");

        sql.addUser(user1);
        sql.addUser(user2);

        sql.addTripToUser(trip, user1);
        sql.addTripToUser(trip2, user1);
        sql.addTripToUser(trip2, user2);

        assertTrue(sql.getTripsFromUser(user1).size() == 2);
        assertTrue(sql.getTripsFromUser(user2).size() == 1);
        assertTrue(sql.getTripsFromUser(user2).get(0).getName().equalsIgnoreCase("Trip2"));
    }
}
