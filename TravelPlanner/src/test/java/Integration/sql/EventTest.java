package Integration.sql;

import Entity.*;
import Integration.JDBC.SQLHelper;

import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;

public class EventTest {

    SQLHelper sql;

    public static final Location location = new Location("45 Upper College RD", "Kingston", "RI", "02881", "USA");

    @Before
    public void before(){
        sql = new SQLHelper();
        sql.wipe();
    }

    @Test
    public void testGetEventSubtypes(){
        assertTrue(sql.getEventSubtypes().size() == 3);
    }

    @Test
    public void testAddEvent(){
        Trip trip = new Trip(null, "Trip1", new Date(2000, 01, 02), new Date(2000, 01, 05));
        Trip trip2 = new Trip(null, "Trip2", new Date(2000, 01, 02), new Date(2000, 01, 05));
        sql.addTrip(trip);
        sql.addTrip(trip2);

        Event event = new Activity(null, "Event1", location, trip);
        Event event2 = new Activity(null, "Event2", location, trip2);
        Event event3 = new Activity(null, "Event3", location, trip2);

        sql.addEvent(event);
        sql.addEvent(event2);
        sql.addEvent(event3);

        assertTrue(sql.getEventsFromTrip(trip).size() == 1);
        assertTrue(sql.getEventsFromTrip(trip2).size() == 2);
        assertTrue(sql.getAPIInformationFromEvent(event).getEvent().getID() == event.getID());
    }

    @Test
    public void testAddEventAPIInfo(){
        Trip trip = new Trip(null, "Trip1", new Date(2000, 01, 02), new Date(2000, 01, 05));
        sql.addTrip(trip);

        Event event = new Activity(null, "Event1", location, trip);
        Event event2 = new Activity(null, "Event2", location, trip);
        sql.addEvent(event);
        sql.addEvent(event2);

        sql.addCoordInformation(event);

        assertTrue(Math.abs(sql.getAPIInformationFromEvent(event).getLat() - 41.48691f) < 0.0001f);
        assertTrue(Math.abs(sql.getAPIInformationFromEvent(event).getLon() - -71.52561f) < 0.0001f);

        sql.addWeatherInformation(event);

        assertTrue(sql.getAPIInformationFromEvent(event).getFeelsLike() < 150f && sql.getAPIInformationFromEvent(event).getFeelsLike() > 0f);


    }



}
