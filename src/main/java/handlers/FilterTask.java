package handlers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.MalformedURLException;

public interface FilterTask {

    public JSONArray getInDBAll() throws IOException, ParseException;
    public JSONObject getInDB(JSONArray a) throws java.text.ParseException;

}
