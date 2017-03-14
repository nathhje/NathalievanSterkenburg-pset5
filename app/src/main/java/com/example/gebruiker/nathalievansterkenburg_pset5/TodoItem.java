package com.example.gebruiker.nathalievansterkenburg_pset5;

import java.io.Serializable;

/**
 * Created by Gebruiker on 12-3-2017.
 */

public class TodoItem implements Serializable{
    private String task;
    private String checked;
    private int parent;
    private int id;

    public TodoItem(String newTask, int theParent, int ID) {
        task = newTask;
        checked = MainActivity.CROSS;
        parent = theParent;
        id = ID;
    }

    public String getTask() {
        return task;
    }

    public String getChecked() {
        return checked;
    }

    public int getParent() { return parent; }

    public int getId() { return id; }

    public void setTask(String newTask) {
        task = newTask;
    }

    public void setChecked(String newChecked) { checked = newChecked; }

    public void setParent(int newParent) { parent = newParent; }

    public void setId(int newID) { id = newID; }
}
