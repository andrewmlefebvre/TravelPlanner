package API;

import Entity.Location;

public class APIUtil {

    public static Weather getWeatherForLocation(Location location){
        LocationCoords c = GeoAPI.fowardGeoCode(location.getStreet(), location.getCity(), location.getState(), location.getPostal(), location.getCountry());
        return WeatherAPI.getWeather(c.getLat(), c.getLon());
    }


}
