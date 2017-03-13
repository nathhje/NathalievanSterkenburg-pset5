package com.example.gebruiker.nathalievansterkenburg_pset5;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gebruiker on 12-3-2017.
 */

public class TodoItemAdapter extends ArrayAdapter {
    private ArrayList<TodoItem> theList;
    private Context context;
    private ListActivity listActivity;

    public TodoItemAdapter(Context con, ArrayList<TodoItem> data) {
        super(con, 0, data);
        theList = data;
        listActivity = (ListActivity) con;
        context = con;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.simple_layout, parent, false);
        }

        TodoItem anItem = theList.get(pos);
        String title = anItem.getTask();

        String checkImage = anItem.getChecked();
        int theImage = context.getResources().getIdentifier(checkImage, "drawable", listActivity.getBaseContext().getPackageName());
        Drawable drawable = context.getResources().getDrawable(theImage);

        TextView theView = (TextView) view.findViewById(R.id.listitem);
        ImageView image = (ImageView) view.findViewById(R.id.checked);

        theView.setText(title);
        image.setBackground(drawable);

        return view;
    }

    @Override
    public int getCount() { return super.getCount(); }
}
