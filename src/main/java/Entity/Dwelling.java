package Entity;

public class Dwelling extends Event{

    public Dwelling(Integer ID, String name, Location location, Trip trip){
        super(ID, name, location, trip);
        this.setSubtype(Event_Subtype.Dwelling);
    }
}
