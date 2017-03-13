package com.example.gebruiker.nathalievansterkenburg_pset5;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
        while (cursor.moveToNext()) {
            TodoList theItem = new TodoList(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SUBJECT)));

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
        final String entry = newtodo.getText().toString();
        dbManager.insert(entry, DatabaseHelper.TABLE_NAMELIST, null);
        newtodo.setText("");

        ListofLists.add(new TodoList(entry));

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

                String toDelete = (todolist.getItemAtPosition(position)).toString();
                dbManager.delete(id);

                fetchCursor();
                todoAdapter.notifyDataSetChanged();

                return true;
            }
        }));

        todolist.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                TodoList clickedList = ListofLists.get(position);
                intent.putExtra("title", clickedList.getListName());

                startActivity(intent);
            }
        }));
    }

    public void OnItemLongClick(AdapterView<?> parent, View view, int position, long id) {
    }

    public void OnItemClick(AdapterView<?> parent, View view, int position, long id) {
    }
}
