package Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class APIInformation {
    private Integer ID;
    private Event event;
    private float lat;
    private float lon;

    private float temp;
    private String des;
    private float feelsLike;
    private float UV;
    private float wind;

    public APIInformation(Event event){
        this.event = event;
    }
}
