package com.example.gebruiker.nathalievansterkenburg_pset5;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String CROSS = "@mipmap/cross";
    public static final String CHECK = "@mipmap/checkmark";

    private DBManager dbManager;
    private EditText newtodo;
    private ListView todolist;
    private Drawable background;;
    ArrayList<TodoList> ListofLists = new ArrayList<>();
    TodoListAdapter todoAdapter;
    int toDeleteID = 0;
    TodoList toDelete = new TodoList(null, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newtodo = (EditText) findViewById(R.id.newtodo);

        // initialize DB
        dbManager = new DBManager(this);
        dbManager.open();

        // get data from DB
        Cursor cursor = dbManager.fetchList(null);

        if(cursor.getCount() > 0) {
            TodoList theItem = new TodoList(
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SUBJECT)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper._ID)));
            ListofLists.add(theItem);
        }

        while (cursor.moveToNext()) {
            TodoList theItem = new TodoList(
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SUBJECT)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper._ID)));

            ListofLists.add(theItem);
        }
        todoAdapter = new TodoListAdapter(this, ListofLists);

        // set cursor adapter to ListView
        todolist = (ListView) findViewById(R.id.todolist);
        background = todolist.getBackground();
        todolist.setAdapter(todoAdapter);
        setListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbManager != null) {
            dbManager.close();
        }
    }

    public void AddToList(View view) {

        Log.i("ik weet niet", "waar ik moet zoeken");
        final String entry = newtodo.getText().toString();
        dbManager.insert(entry, DatabaseHelper.TABLE_NAMELIST, 0);
        Log.i("maar ik denk", "in deze functie");
        newtodo.setText("");
        Cursor cursor = dbManager.fetchList(DatabaseHelper._ID + " = (SELECT MAX(" +
                DatabaseHelper._ID + ")  FROM " + DatabaseHelper.TABLE_NAMELIST + ");");
        Log.i("kom ik", "hier dan niet?");
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper._ID));

        ListofLists.add(new TodoList(entry, id));

        Log.i("zoek zoek", "zoekerdezoek");
        fetchCursor();

        todoAdapter.notifyDataSetChanged();
    }

    public void fetchCursor() {
        Cursor cursor = dbManager.fetchList(null);
    }

    private void setListener() {
        todolist.setOnItemLongClickListener((new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("hier", "hier");
                toDelete = ListofLists.get(position);
                Log.i("of", "hier");
                toDeleteID = toDelete.getId();
                Log.i("hier", "of");
                Button removeButton = (Button) findViewById(R.id.remove);
                Log.i("eventueel", "hier");
                removeButton.setVisibility(View.VISIBLE);
                Log.i("hier", "eventueel");
                fetchCursor();
                Log.i("tenslotte", "hier");
                return true;
            }
        }));

        todolist.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                TodoList clickedList = ListofLists.get(position);
                intent.putExtra("id", clickedList.getId());

                Log.i("dit is het laatste", String.valueOf(clickedList.getId()));
                startActivity(intent);
            }
        }));
    }

    public void OnItemLongClick(AdapterView<?> parent, View view, int position, long id) {
    }

    public void OnItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    public void RemoveFromList(View view) {
        dbManager.delete(toDeleteID, DatabaseHelper.TABLE_NAMELIST, DatabaseHelper._ID);
        dbManager.delete(toDeleteID, DatabaseHelper.TABLE_NAMEITEM, DatabaseHelper.PARENT);

        ListofLists.remove(toDelete);

        Button removeButton = (Button) findViewById(R.id.remove);
        removeButton.setVisibility(View.GONE);

        todoAdapter.notifyDataSetChanged();
    }
}
