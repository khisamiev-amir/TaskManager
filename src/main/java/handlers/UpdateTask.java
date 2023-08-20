package handlers;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface UpdateTask {
    public void updateTask() throws IOException, ParseException, java.text.ParseException;

}
