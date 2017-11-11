package server;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by jhenstrom on 10/27/17.
 */

class FemaleNames {

    private ArrayList<String> femaleNames = new ArrayList<>();

    FemaleNames()
    {
        femaleNames = GenerateNames();
    }

    private ArrayList<String> GenerateNames()
    {
        String filePath = "ServerResources/json/fnames.json";
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
            ArrayList<String> curr = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++)
            {
                String s = jsonArray.get(i).toString();
                curr.add(s);
            }
            return curr;

        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    ArrayList<String> getFemaleNames() { return femaleNames; }
}
