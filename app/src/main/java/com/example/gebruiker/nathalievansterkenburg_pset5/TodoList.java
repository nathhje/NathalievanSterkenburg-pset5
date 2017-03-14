package com.example.gebruiker.nathalievansterkenburg_pset5;

import java.util.ArrayList;

/**
 * Created by Gebruiker on 13-3-2017.
 */

public class TodoList {
    private String listName;
    private ArrayList<TodoItem> theList;
    private int id;

    public TodoList(String name, int ID) {
        listName = name;
        id = ID;
    }

    public void add(TodoItem newItem) {
        theList.add(newItem);
    }

    public String getListName() { return listName; }

    public ArrayList<TodoItem> getTheList() { return theList; }

    public int getId() { return id; }

    public void setListName(String newName) { listName = newName; }

    public void setTheList(ArrayList<TodoItem> newList) { theList = newList; }

    public void setId(int newId) { id = newId; }
}
