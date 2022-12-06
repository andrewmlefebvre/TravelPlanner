package Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@Getter
public class Transportation extends Event{
    private String transportationType;
    private String flightNumber;
    private Date flightDepartal;

    public Transportation(Integer ID, String name, Location location, Trip trip, Date startDate, Date endDate){
        super(ID, name, location, trip, startDate, endDate);
        this.setSubtype(Event_Subtype.Transportation);
    }
}
