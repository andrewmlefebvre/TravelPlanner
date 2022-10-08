package Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Event {
    private Integer ID;
    private Event_Subtype subtype;
    String name;
    String location;
    Trip trip;

    public Event(Integer ID, String name, String location, Trip trip){
        this.ID = ID;
        this.name = name;
        this.location = location;
        this.trip = trip;
    }
}
