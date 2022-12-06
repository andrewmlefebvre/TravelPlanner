package Entity;

import java.sql.Date;

public class Dwelling extends Event{

    public Dwelling(Integer ID, String name, Location location, Trip trip, Date startDate, Date endDate){
        super(ID, name, location, trip, startDate, endDate);
        this.setSubtype(Event_Subtype.Dwelling);
    }
}
