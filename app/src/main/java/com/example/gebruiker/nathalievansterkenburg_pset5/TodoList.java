package com.example.gebruiker.nathalievansterkenburg_pset5;

import java.util.ArrayList;

/**
 * Created by Gebruiker on 13-3-2017.
 */

public class TodoList {
    private String listName;
    private ArrayList<TodoItem> theList;

    public TodoList(String name) {
        listName = name;
    }

    public void add(TodoItem newItem) {
        theList.add(newItem);
    }

    public String getListName() { return listName; }

    public ArrayList<TodoItem> getTheList() { return theList; }

    public void setListName(String newName) { listName = newName; }

    public void setTheList(ArrayList<TodoItem> newList) { theList = newList; }
}
