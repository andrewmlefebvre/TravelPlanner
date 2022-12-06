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
    public APIInformation(Integer ID, Integer eventID, float lat, float lon, float temp, String des, float feelsLike, float UV, float wind){
        this.ID = ID;
        this.event = null;
        this.lat = lat;
        this.lon = lon;
        this.temp = temp;
        this.des = des;
        this.feelsLike = feelsLike;
        this.UV = UV;
        this.wind = wind;
    }
}
