package Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@Getter
public class Transportation extends Event{
    private String transportationType;
    private String flightNumber;
    private Date flightDepartal;

    public Transportation(Integer ID, String name, String location, Trip trip){
        super(ID, name, location, trip);
        this.setSubtype(Event_Subtype.Transportation);
    }
}
