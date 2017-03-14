package com.example.gebruiker.nathalievansterkenburg_pset5;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    public static final String CROSS = "@mipmap/cross";
    public static final String CHECK = "@mipmap/checkmark";

    private DBManager dbManager;
    private EditText newtodo;
    private ListView todolist;
    private Drawable background;
    int parent;
    ArrayList<TodoItem> itemList = new ArrayList<>();
    TodoItemAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Log.i("Hij komt hier", "helemaal niet");
        newtodo = (EditText) findViewById(R.id.newtodo);

        Log.i("heh", "wat?");
        // initialize DB
        dbManager = new DBManager(this);
        dbManager.open();

        Log.i("is hier al gestruggeld", "");
        parent = getIntent().getIntExtra("id", 0);
        Log.i("of komt het hier", String.valueOf(parent));

        // get data from DB
        Cursor cursor = dbManager.fetchItem(DatabaseHelper.PARENT + " = " + parent);
        Cursor forTitle = dbManager.fetchList(DatabaseHelper._ID + " = " + parent);

        TextView title = (TextView) findViewById(R.id.whatlist);
        title.setText(forTitle.getString(forTitle.getColumnIndexOrThrow(DatabaseHelper.SUBJECT)));

        Cursor justCheckin = dbManager.fetchItem(null);
        //Log.i("wtf", justCheckin.getString(justCheckin.getColumnIndexOrThrow(DatabaseHelper.PARENT)));
        Log.i("hier denk ik", "op zn laatst");
        Log.i("i", Integer.toString(cursor.getColumnIndexOrThrow(DatabaseHelper.SUBJECT)));

        if(cursor.getCount() > 0) {
            TodoItem theItem = new TodoItem(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SUBJECT)),
                    parent, cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper._ID)));
            theItem.setChecked(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.DONE)));
            itemList.add(theItem);
        }

        while (cursor.moveToNext()) {
            TodoItem theItem = new TodoItem(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SUBJECT)),
                    parent, cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper._ID)));
            Log.i("o", theItem.toString());
            theItem.setChecked(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.DONE)));
            itemList.add(theItem);
        }
        Log.i("hoi", itemList.toString());
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

        Cursor cursor = dbManager.fetchList(DatabaseHelper._ID + " = (SELECT MAX(" +
                DatabaseHelper._ID + ")  FROM " + DatabaseHelper.TABLE_NAMELIST + ");");
        Log.i("kom ik", "hier dan niet?");
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper._ID));

        itemList.add(new TodoItem(entry, parent, id));

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

                TodoItem toDelete = itemList.get(position);
                Log.i("i dont know", String.valueOf(toDelete.getId()));
                dbManager.delete(toDelete.getId(), DatabaseHelper.TABLE_NAMEITEM, DatabaseHelper._ID);

                itemList.remove(toDelete);
                fetchCursor();
                todoAdapter.notifyDataSetChanged();
                return true;
            }
        }));

        todolist.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                todolist.clearChoices();
//                todolist.setItemChecked(position, true);

                TodoItem toChange = itemList.get(position);
                String image = toChange.getChecked();
                Log.i("why does it do this", image);
//                image = DatabaseHelper.CROSS;
                if (image.equals(DatabaseHelper.CHECK)) {
                    image = DatabaseHelper.CROSS;
                }
                else {
                    image = DatabaseHelper.CHECK;
                }
                Log.i("nog een keer", image);

                dbManager.update(toChange.getId(), image);
                toChange.setChecked(image);
                
                Cursor cursor = dbManager.fetchItem(DatabaseHelper._ID + " = " + toChange.getId());
                Log.i("ik ben benieuwd", cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.DONE)));
                fetchCursor();

                todoAdapter.notifyDataSetChanged();
            }
        }));
    }

    public void OnItemLongClick(AdapterView<?> parent, View view, int position, long id) {
    }

    public void OnItemClick(AdapterView<?> parent, View view, int position, long id) {
    }
}
