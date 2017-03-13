package com.example.gebruiker.nathalievansterkenburg_pset5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Gebruiker on 13-3-2017.
 */

public class TodoListAdapter extends ArrayAdapter {
    private ArrayList<TodoList> theList;
    private Context context;
    private MainActivity mainActivity;

    public TodoListAdapter(Context con, ArrayList<TodoList> data) {
        super(con, 0, data);
        theList = data;
        mainActivity = (MainActivity) con;
        context = con;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.simple_layout, parent, false);
        }

        TodoList aList = theList.get(pos);
        String title = aList.getListName();

        TextView theView = (TextView) view.findViewById(R.id.listitem);
        theView.setText(title);

        return view;
    }

    @Override
    public int getCount() { return super.getCount(); }
}
