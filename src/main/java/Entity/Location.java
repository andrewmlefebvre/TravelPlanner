package Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Location {
    String street;
    String city;
    String state;
    String postal;
    String country;
}
