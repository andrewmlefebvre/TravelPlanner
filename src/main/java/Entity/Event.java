package Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public abstract class Event {
    Integer ID;
    Event_Subtype subtype;
    String name;
    Location location;
    Trip trip;
    Date startDate;
    Date endDate;

    public Event(Integer ID, String name, Location location, Trip trip, Date startDate, Date endDate){
        this.ID = ID;
        this.name = name;
        this.location = location;
        this.trip = trip;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
