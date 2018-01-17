package com.warped.andaleksei.ultimatefootballquiz;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        FrameLayout gridLayout = (FrameLayout)convertView.findViewById(R.id.lin_layout);
        TextView completedLevelTextView = (TextView)convertView.findViewById(R.id.show_completed_level_text_view);
        ImageView comletedLevelTickImageView = (ImageView)convertView.findViewById(R.id.tick_image_view);

        if (position > lastUnlockedItem) {
            completedLevelTextView.setVisibility(View.INVISIBLE);
            comletedLevelTickImageView.setVisibility(View.INVISIBLE);
            textview.setVisibility(View.VISIBLE);
            textview.setBackgroundResource(R.drawable.lock);
            gridLayout.setBackgroundResource(R.drawable.grid_frame_completed);
            textview.setText("");
        } else if (database.getCompletedState(position, tableName) == 0) {
            completedLevelTextView.setVisibility(View.INVISIBLE);
            comletedLevelTickImageView.setVisibility(View.INVISIBLE);
            textview.setVisibility(View.VISIBLE);
            textview.setBackgroundColor(Color.TRANSPARENT);
            gridLayout.setBackgroundResource(R.drawable.grid_frame);
            textview.setText("" + position);
        } else {
            completedLevelTextView.setVisibility(View.VISIBLE);
            comletedLevelTickImageView.setVisibility(View.VISIBLE);
            textview.setVisibility(View.INVISIBLE);

            completedLevelTextView.setText(String.valueOf(position));

            gridLayout.setBackgroundResource(R.drawable.grid_frame_completed);
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
