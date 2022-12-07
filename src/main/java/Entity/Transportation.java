package Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@Getter
public class Transportation extends Event{

    public Transportation(Integer ID, String name, Location location, Trip trip, Date startDate, Date endDate){
        super(ID, name, location, trip, startDate, endDate);
        this.setSubtype(Event_Subtype.Transportation);
    }

    public Transportation(Integer ID, String name, Location location, Trip trip, Date startDate, Date endDate, String flight){
        super(ID, name, location, trip, startDate, endDate);
        this.setSubtype(Event_Subtype.Transportation);
        this.flightNumber = flight;
    }
}
