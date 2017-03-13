package com.example.gebruiker.nathalievansterkenburg_pset5;

import java.io.Serializable;

/**
 * Created by Gebruiker on 12-3-2017.
 */

public class TodoItem implements Serializable{
    private String task;
    private String checked;
    private String parent;

    public TodoItem(String newTask, String theParent) {
        task = newTask;
        checked = MainActivity.CROSS;
        parent = theParent;
    }

    public String getTask() {
        return task;
    }

    public String getChecked() {
        return checked;
    }

    public String getParent() { return parent; }

    public void setTask(String newTask) {
        task = newTask;
    }

    public void setChecked(String newChecked) { checked = newChecked; }

    public void setParent(String newParent) { parent = newParent; }
}
