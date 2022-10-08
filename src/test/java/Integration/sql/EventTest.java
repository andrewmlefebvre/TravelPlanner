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

        Event event = new Activity(null, "Event1", "A place", trip);
        Event event2 = new Activity(null, "Event2", "A second place", trip2);
        Event event3 = new Activity(null, "Event3", "A third place", trip2);

        sql.addEvent(event);
        sql.addEvent(event2);
        sql.addEvent(event3);

        assertTrue(sql.getEventsFromTrip(trip).size() == 1);
        assertTrue(sql.getEventsFromTrip(trip2).size() == 2);
    }



}
