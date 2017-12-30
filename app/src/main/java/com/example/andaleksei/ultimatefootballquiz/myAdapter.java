package com.example.andaleksei.ultimatefootballquiz;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.R.attr.data;

public class myAdapter extends BaseAdapter {

    private Context context;
    private int lastUnlockedItem;
    private int size;
    private String tableName;
    LayoutInflater inflater;

    public myAdapter(Context context, int id, int s, String table) {
        this.context = context;
        lastUnlockedItem = id;
        size = s;
        tableName = table;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        position++;

        TextView textview;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_element, null);

        }

        textview = (TextView) convertView.findViewById(R.id.textview);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                context.getResources().getDimensionPixelSize(R.dimen.character_size),
                context.getResources().getDimensionPixelSize(R.dimen.character_size));

        int margin = context.getResources().getDimensionPixelSize(R.dimen.margin_normal);

        dataBase database = new dataBase(context);

        if (position > lastUnlockedItem) {
            textview.setBackgroundResource(R.drawable.lock);
            textview.setText("");
        } else if (database.getCompletedState(position, tableName) == 0) {
            textview.setBackgroundColor(Color.TRANSPARENT);
            textview.setTextColor(Color.parseColor("#121212"));
            textview.setText("" + position);
        } else {
            textview.setBackgroundColor(Color.TRANSPARENT);
            textview.setTextColor(Color.parseColor("#F9A825"));
            textview.setText("" + position);
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}