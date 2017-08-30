package com.example.andaleksei.ultimatefootballquiz;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class myAdapter extends BaseAdapter {

    private Context context;
    private int footballerId;
    LayoutInflater inflater;

    public myAdapter(Context context, int id) {
        this.context = context;
        footballerId = id;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        position++;
        TextView textview;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_element, null);

        }
        textview = (TextView) convertView.findViewById(R.id.textview);
        textview.setText("" + position);

        if (position < footballerId) {
            textview.setBackgroundColor(Color.YELLOW);
        } else if (position == footballerId) {
            textview.setBackgroundColor(Color.BLUE);
        } else textview.setBackgroundColor(Color.GRAY);

        return convertView;
    }

    @Override
    public int getCount() {
        return 450;
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