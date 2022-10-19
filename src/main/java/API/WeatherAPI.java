package API;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherAPI {
    public static Weather getWeather(float lat, float lon){
        String _lat = String.valueOf(lat);
        String _lon = String.valueOf(lon);
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://weatherapi-com.p.rapidapi.com/current.json?q="+_lat+","+_lon))
                    .header("X-RapidAPI-Key", "992c85d2d8msh138057fb42bf0b1p176bffjsne0e123c03bfb")
                    .header("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObject = new JSONObject(response.body().toString());
            int t = 1;
            return new Weather(jsonObject.getJSONObject("current").getFloat("temp_f"),
                    jsonObject.getJSONObject("current").getJSONObject("condition").getString("text"),
                    jsonObject.getJSONObject("current").getFloat("feelslike_f"),
                    jsonObject.getJSONObject("current").getFloat("uv"),
                    jsonObject.getJSONObject("current").getFloat("wind_mph"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
