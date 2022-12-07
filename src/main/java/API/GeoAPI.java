

//45 Upper College Rd Kingston RI 02881 URI
//2000 Post Rd Warwick RI 02886 TF Green
//100 Terminal Dr Fort Lauderdale FL 33315 FLL Airport
//301 SW 1st Ave Fort Lauderdale FL 33301 Sonder Hotel
//3601 N Ocean Dr Hollywood, FL 33019 Hollywood Beach
//2201 S Ocean Dr Hollywood FL 33019 Restaurant



package API;




import org.json.JSONObject;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class GeoAPI {
    public static LocationCoords fowardGeoCode(String street, String city, String state, String postal, String country){
        String _street = street.replaceAll(" ", "%20");
        String _state = state.replaceAll(" ", "%20");
        String _city = city.replaceAll(" ", "%20");
        String _postal = postal.replaceAll(" ", "%20");
        String _country = country.replaceAll(" ", "%20");
        try{
            Thread.sleep(400);
        }catch(Exception e){
            e.printStackTrace();
        }
        try {
            /*
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://forward-reverse-geocoding.p.rapidapi.com/v1/forward?street="+_street+"&city="+_city+"&state="+_state+"&postalcode="+_postal+"&country="+_country+"&accept-language=en&polygon_threshold=0.0"))
                    .header("X-RapidAPI-Key", "992c85d2d8msh138057fb42bf0b1p176bffjsne0e123c03bfb")
                    .header("X-RapidAPI-Host", "forward-reverse-geocoding.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println(response.body());

             */

            System.out.println(" https://eec19846-geocoder-us-census-bureau-v1.p.rapidapi.com/locations/onelineaddress?benchmark=Public_AR_Current&address="+_street+"%20"+_state+_city+_postal+_country+"&format=json");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://eec19846-geocoder-us-census-bureau-v1.p.rapidapi.com/locations/onelineaddress?benchmark=Public_AR_Current&address="+_street+"%20"+_city+"%20"+_state+"%20"+_postal+"%20"+_country+"&format=json"))
                    .header("X-RapidAPI-Key", "992c85d2d8msh138057fb42bf0b1p176bffjsne0e123c03bfb")
                    .header("X-RapidAPI-Host", "eec19846-geocoder-us-census-bureau-v1.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());


            JSONObject jsonObject = new JSONObject(response.body().toString());

            Object coords = ((HashMap) jsonObject.getJSONObject("result").getJSONArray("addressMatches").toList().get(0)).get("coordinates");
            BigDecimal lon = (BigDecimal) ((HashMap) coords).get("x");
            BigDecimal lat = (BigDecimal) ((HashMap) coords).get("y");


            return new LocationCoords(lat.floatValue(), lon.floatValue());
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
