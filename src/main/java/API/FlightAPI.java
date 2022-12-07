package API;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class FlightAPI {
    public static String getFlightInfo(String name){
        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://flightera-flight-data.p.rapidapi.com/flight/info?flnr="+name+""))
                    .header("X-RapidAPI-Key", "992c85d2d8msh138057fb42bf0b1p176bffjsne0e123c03bfb")
                    .header("X-RapidAPI-Host", "flightera-flight-data.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());


            JSONObject jsonObject = new JSONObject(response.body().toString().substring(1, response.body().toString().length()-1));

            return jsonObject.getString("status");


            //return "on time";
        }catch(Exception e){
            e.printStackTrace();
        }
        return "Service Unavailable";
    }
}
