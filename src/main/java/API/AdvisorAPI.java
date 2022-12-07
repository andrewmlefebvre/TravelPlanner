package API;

import Entity.Event;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AdvisorAPI {

    public static List<Nearby> getNearby(float lat, float lon){
        List<Nearby> out = new LinkedList<>();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://travel-advisor.p.rapidapi.com/restaurants/list-by-latlng?latitude="+lat+"&longitude="+lon+"&limit=30&currency=USD&distance=2&open_now=false&lunit=km&lang=en_US"))
                    .header("X-RapidAPI-Key", "992c85d2d8msh138057fb42bf0b1p176bffjsne0e123c03bfb")
                    .header("X-RapidAPI-Host", "travel-advisor.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObject = new JSONObject(response.body().toString());

            jsonObject.getJSONArray("data").toList();

            for(Object h : jsonObject.getJSONArray("data").toList()){
                String name = ((HashMap) h).get("name") == null ? "" : ((HashMap) h).get("name").toString().replace(",", "").replace("'", "");
                String addr = ((HashMap) h).get("address") == null ? "" : ((HashMap) h).get("address").toString().replace(",", "").replace("'", "");
                String des = ((HashMap) h).get("description") == null ? "" : ((HashMap) h).get("description").toString().replace(",", "").replace("'", "");

                Nearby n = new Nearby(name, addr, des);
                out.add(n);
            }

        }catch(Exception e){
            e.printStackTrace();
        }


        return out;
    }
}
