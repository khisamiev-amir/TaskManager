package controllers;


import commons.FileNames;
import commons.TaskRequests;
import enums.Status;
import handlers.CreateTask;
import handlers.DeleteTask;
import handlers.FilterTask;
import handlers.UpdateTask;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


import model.Task;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;


public class TaskController implements CreateTask, DeleteTask, FilterTask, UpdateTask {
    @Override
    public Task create(JSONObject a) throws java.text.ParseException {
        String name = (String) a.get("name");
        String description = (String) a.get("description");
        // снизу стринг дэйт мы взяли дату и сохранили, как строку, далее я форматирую её
        //в дату со временем использовать именно docDate при создании объекта таск
        String date = (String) a.get("datetime");
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy HH:mm");
        Date docDate = format.parse(date);
        String owner = (String) a.get("owner");
        //создать объект класса таск и протащить туда все параметры
        //вернуть объект класса таск и вывести в мэйне
        //подать его в метод который загружает в бд
        //дернуть ручку  погуглить, как делать реквест запрос(гет пост)
        Task task1 = new Task(name, description, docDate, owner);
        return task1;
    }

    @Override
    public void addInDB(JSONObject a) {
        TaskRequests.postRequest("task/add", a);
    }

    @Override
    public JSONArray getInDBAll() throws IOException, ParseException {
        JSONArray jsonArray = TaskRequests.getRequest("task/get/all");
        return jsonArray;
    }

    @Override
    public JSONObject getInDB(JSONArray a) throws java.text.ParseException {
        System.out.println("Введите название задачи");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine().toLowerCase();
        for (int i = 0; i < a.size(); i++) {
            JSONObject j = (JSONObject) a.get(i);//нужно кастовать потому что возвращается объект, а не JSONObject
            String value = ((String) j.get("name")).toLowerCase();
            if (name.equals(value)) {
                System.out.println(j);
                return j;
            }

        }
        return null;
    }


    @Override
    public JSONObject choiceTask() throws IOException, ParseException {
        System.out.println("Введите номер задачи");
        Scanner scanner = new Scanner(System.in);
        String number = scanner.nextLine();

        Path result = FileNames.SPECIFICATIONS.resolve(number + ".json");


        String data = Files.readString(result); // сохранение содержимого файла по пути result в data

        JSONObject jsonObject = (JSONObject) new JSONParser().parse(data);//преобразование строки в объект JSONObject
        // ниже не сокращённый вариант

//        JSONParser parser = new JSONParser();
//        Object o = parser.parse(data);
//        JSONObject obj = (JSONObject) o;
        return jsonObject;

    }


    @Override
    public void updateTask() throws IOException, ParseException, java.text.ParseException {
        JSONArray tasks = getInDBAll();
        JSONObject choistTask = getInDB(tasks);
        System.out.println("Выберите, что изменить, введите цифру:\n1.Обновить статус\n2.Обновить описание");
        Scanner sc = new Scanner(System.in);
        String a = sc.nextLine();
        String enumValue = null;
        if (a.equals("1")) {
            System.out.println("Выберите статус:\n1.DONE\n2.PROCESS\n3.DECLINE");
            String b = sc.nextLine();

            switch (b) {
                case "1":
                    enumValue = String.valueOf(Status.DONE);
                    break;
                case "2":
                    enumValue = String.valueOf(Status.PROCESS);
                    break;
                case "3":
                    enumValue = String.valueOf(Status.DECLINE);
                    break;

            }

            JSONObject forUpdate = new JSONObject();
            forUpdate.put("id", choistTask.get("id"));
            forUpdate.put("status", enumValue);
            TaskRequests.postRequest("task/update/status", forUpdate);

        } else if (a.equals("2")) {
            System.out.println("Введите описание: ");
            Scanner scanner = new Scanner(System.in);
            String string = sc.nextLine();
            JSONObject forUpdate = new JSONObject();
            forUpdate.put("id", choistTask.get("id"));
            forUpdate.put("description", string);
            TaskRequests.postRequest("task/update/description", forUpdate);
        }
    }


    @Override
    public void deleteTask() throws IOException, ParseException, java.text.ParseException {

        JSONArray tasks = getInDBAll();

        JSONObject choistTask = getInDB(tasks);
        Scanner scanner = new Scanner(System.in);
        JSONObject forDelete = new JSONObject();
        forDelete.put("id", choistTask.get("id"));
        TaskRequests.deleteRequest("task/delete/" + choistTask.get("id"), forDelete);
    }
}
