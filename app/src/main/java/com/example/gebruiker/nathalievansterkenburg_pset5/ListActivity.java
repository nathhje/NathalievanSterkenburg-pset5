package com.example.gebruiker.nathalievansterkenburg_pset5;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    public static final String CROSS = "@mipmap/cross";
    public static final String CHECK = "@mipmap/checkmark";

    private DBManager dbManager;
    private EditText newtodo;
    private ListView todolist;
    private Drawable background;
    String parent;
    ArrayList<TodoItem> itemList = new ArrayList<>();
    TodoItemAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        newtodo = (EditText) findViewById(R.id.newtodo);

        // initialize DB
        dbManager = new DBManager(this);
        dbManager.open();

        // get data from DB
        Cursor cursor = dbManager.fetchItem(null);
        parent = getIntent().getStringExtra("title");
        while (cursor.moveToNext()) {
            TodoItem theItem = new TodoItem(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SUBJECT)), parent);

            itemList.add(theItem);
        }
        todoAdapter = new TodoItemAdapter(this, itemList);

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
        dbManager.insert(entry, DatabaseHelper.TABLE_NAMEITEM, parent);
        newtodo.setText("");

        itemList.add(new TodoItem(entry, parent));

        fetchCursor();

        todoAdapter.notifyDataSetChanged();
    }

    public void fetchCursor() {
        Cursor cursor = dbManager.fetchItem(null);
    }

    private void setListener() {
        todolist.setOnItemLongClickListener((new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String toDelete = (todolist.getItemAtPosition(position)).toString();
                dbManager.delete(id);

                fetchCursor();
                return true;
            }
        }));

        todolist.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                todolist.clearChoices();
//                todolist.setItemChecked(position, true);

                Cursor cursor = dbManager.fetchItem(DatabaseHelper._ID + " = " + id);
                String image = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.DONE));

//                image = DatabaseHelper.CROSS;
                if (image.equals(DatabaseHelper.CHECK)) {
                    image = DatabaseHelper.CROSS;
                }
                else {
                    image = DatabaseHelper.CHECK;
                }

                dbManager.update(id, image);
                fetchCursor();
            }
        }));
    }

    public void OnItemLongClick(AdapterView<?> parent, View view, int position, long id) {
    }

    public void OnItemClick(AdapterView<?> parent, View view, int position, long id) {
    }
}
