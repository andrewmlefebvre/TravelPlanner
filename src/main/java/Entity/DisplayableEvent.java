package Entity;

import API.Weather;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@Getter
@Setter
public class DisplayableEvent {
    Integer ID;
    Event_Subtype subtype;
    String name;
    Location location;
    Trip trip;
    Date startDate;
    Date endDate;
    APIInformation api;
}
