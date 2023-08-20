package commons;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TaskRequests {
    private static final String URL = "http://46.149.79.33:3000/";

    public static JSONObject postRequest(String endpoint, JSONObject jsonObject) {
        try {
            // Создаем URL-адрес для запроса
            URL url = new URL(URL + endpoint);

            // Создаем объект HttpURLConnection и настраиваем его
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);//необходимость вывода ответа

            // Создаем тело запроса
            String body = jsonObject.toString();

            // Записываем тело запроса в поток вывода
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush(); //закрыть поток
//            System.out.println(body);

            // Считываем ответ от сервера
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Выводим ответ от сервера
//            System.out.println(response);//просто, чтобы было
            JSONObject jsonObject1 = (JSONObject) new JSONParser().parse(response.toString());
            System.out.println(jsonObject1.toString());//просто, чтобы было
            return jsonObject1;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    public static JSONObject deleteRequest(String endpoint, JSONObject jsonObject) {
        try {
            // Создаем URL-адрес для запроса
            URL url = new URL(URL + endpoint);

            // Создаем объект HttpURLConnection и настраиваем его
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setDoOutput(true);//необходимость вывода ответа

            // Считываем ответ от сервера
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Выводим ответ от сервера
//            System.out.println(response);//просто, чтобы было
            JSONObject jsonObject1 = (JSONObject) new JSONParser().parse(response.toString());
            System.out.println(jsonObject1.toString());//просто, чтобы было
            return jsonObject1;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    public static JSONArray getRequest(String endpoint) throws IOException, ParseException {
        StringBuffer response = new StringBuffer();
        URL url = new URL(URL + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(false);

        int responseCode = connection.getResponseCode();
        //                response.append("Response Code : " + responseCode + "\n");
        InputStream inputStream = connection.getInputStream();// подключаюсь к потоку данных
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);//считываю данные
        BufferedReader reader = new BufferedReader(inputStreamReader);//записываю в буфер

        String inputLine;

        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();
        JSONArray jsonArray = (JSONArray) new JSONParser().parse(response.toString());
        for (
                int i = 0; i < jsonArray.size(); i++) {
            JSONObject j = (JSONObject) jsonArray.get(i);//нужно кастовать потому что возвращается объект, а не JSONObject
            System.out.println("Задача " + (i + 1) + "\n" + "Название: " + j.get("name"));
        }
        // => Response Code : 200
        // =>  <!doctype html><html><head>    <title>Example Domain</title> и т.д.
        return jsonArray;
    }
}
