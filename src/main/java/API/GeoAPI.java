package API;




import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://forward-reverse-geocoding.p.rapidapi.com/v1/forward?street="+_street+"&city="+_city+"&state="+_state+"&postalcode="+_postal+"&country="+_country+"&accept-language=en&polygon_threshold=0.0"))
                    .header("X-RapidAPI-Key", "992c85d2d8msh138057fb42bf0b1p176bffjsne0e123c03bfb")
                    .header("X-RapidAPI-Host", "forward-reverse-geocoding.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println(response.body());
            JSONObject jsonObject = new JSONObject(response.body().toString().substring(1, response.body().length()-1));

            return new LocationCoords(jsonObject.getFloat("lat"), jsonObject.getFloat("lon"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
