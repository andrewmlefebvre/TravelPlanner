package Entity;

import java.sql.Date;

public class Activity extends Event{

    public Activity(Integer ID, String name, Location location, Trip trip, Date startDate, Date endDate){
        super(ID, name, location, trip, startDate, endDate);
        this.setSubtype(Event_Subtype.Activity);
    }
}
