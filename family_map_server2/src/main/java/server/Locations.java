package server;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by jhenstrom on 10/27/17.
 */

public class Locations {

    ArrayList<Location> locations = new ArrayList<>();

    public Locations()
    {
        locations = GenerateLocations();
    }

    private ArrayList<Location> GenerateLocations()
    {
        String filePath = "ServerResources/json/locations.json";
        Gson gson = new Gson();
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(new File(filePath));
            String jsonLocation = "";
            while(scanner.hasNext())
            {
                jsonLocation += scanner.next();
            }
            JSONObject jObject = new JSONObject(jsonLocation);
            JSONArray jsonArray = jObject.getJSONArray("data");
            ArrayList<Location> curr = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++)
            {
                Location l = new Location();
                l.setCountry(jsonArray.getJSONObject(i).getString("country"));
                l.setCity(jsonArray.getJSONObject(i).getString("city"));
                l.setLatitude(Double.toString(jsonArray.getJSONObject(i).getDouble("longitude")));
                l.setLongitude(Double.toString(jsonArray.getJSONObject(i).getDouble("longitude")));
                curr.add(l);
            }
            return curr;

        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    ArrayList<Location> getLocations(){ return locations; }
}
