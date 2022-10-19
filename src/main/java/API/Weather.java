package API;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Weather {
    Float temp;
    String des;
    Float feelsLike;
    Float UV;
    Float wind;
}
