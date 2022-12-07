package API;


import Entity.Location;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.List;
import java.lang.Math;

import static org.junit.Assert.*;

public class APItest {

    public static final Location location = new Location("45 Upper College RD", "Kingston", "RI", "02881", "USA");


    @Test
    public void fowardGeoCodeTest(){
        LocationCoords c =  GeoAPI.fowardGeoCode(location.getStreet(), location.getCity(), location.getState(), location.getPostal(), location.getCountry());
        int i = 0;
        assertTrue(Math.abs(c.getLat() - 41.48691f) < 0.0001f);
        assertTrue(Math.abs(c.getLon() - -71.52561f) < 0.0001f);
    }

    @Test
    public void weatherTest(){
        LocationCoords c =  GeoAPI.fowardGeoCode(location.getStreet(), location.getCity(), location.getState(), location.getPostal(), location.getCountry());
        Weather w = WeatherAPI.getWeather(c.getLat(), c.getLon());

        assertTrue(w.getDes() != null);
        assertTrue(w.getUV() != null);
        assertTrue(w.getWind() != null);
        assertTrue(w.getTemp() != null);
        assertTrue(w.getFeelsLike() != null);
    }

    @Test
    public void restTest(){
        LocationCoords c =  GeoAPI.fowardGeoCode(location.getStreet(), location.getCity(), location.getState(), location.getPostal(), location.getCountry());
        List<Nearby> n = AdvisorAPI.getNearby(c.getLat(), c.getLon());
    }
}
