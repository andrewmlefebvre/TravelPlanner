package Entity;

public class Activity extends Event{

    public Activity(Integer ID, String name, String location, Trip trip){
        super(ID, name, location, trip);
        this.setSubtype(Event_Subtype.Activity);
    }
}
