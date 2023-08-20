package handlers;

import model.Task;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface CreateTask {
    public Task create(JSONObject a) throws java.text.ParseException;

    public void addInDB(JSONObject a);

    public JSONObject choiceTask() throws IOException, ParseException;
}
