package API;

import Entity.Event;
import Entity.Location;

public class APIUtil {

    public static Weather getWeatherForEvent(Event event){
        Location location = event.getLocation();
        LocationCoords c = GeoAPI.fowardGeoCode(location.getStreet(), location.getCity(), location.getState(), location.getPostal(), location.getCountry());
        Weather w = WeatherAPI.getWeather(c.getLat(), c.getLon());
        return w;
    }
    public static LocationCoords getCoordsForEvent(Event event){
        Location location = event.getLocation();
        LocationCoords c = GeoAPI.fowardGeoCode(location.getStreet(), location.getCity(), location.getState(), location.getPostal(), location.getCountry());
        return c;
    }


}
