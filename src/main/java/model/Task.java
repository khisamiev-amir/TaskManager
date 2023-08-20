package model;

import enums.Status;

import java.util.Date;

public class Task {
    private Status status;
    private String name;
    private String description;
    private Date date;
    private String owner;

    public Task(Status status, String name, String description, Date date, String owner) {
        this.status = status;
        this.name = name;
        this.description = description;
        this.date = date;
        this.owner = owner;
    }

    public Task(Status status, String name, Date date, String owner) {
        this.status = status;
        this.name = name;
        this.date = date;
        this.owner = owner;
    }

    public Task(String name, String description, Date date, String owner) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", owner='" + owner + '\'' +
                '}';
    }


}
