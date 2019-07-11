package com.cse437.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<ListDetails> {

    public CustomAdapter(Context context, ArrayList<ListDetails> details) {

        super(context, 0, details);

    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position

        ListDetails details= getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.detail_item, parent, false);

        }

        // Lookup view for data population

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);

        TextView tvScore = (TextView) convertView.findViewById(R.id.tvScore);

        TextView tvTimeStamp = (TextView) convertView.findViewById(R.id.tvTimeStamp);

        // Populate the data into the template view using the data object

        tvName.setText(details.getName());

        tvScore.setText(details.getScore() + " Stars");

        tvTimeStamp.setText(details.getTimestamp() + " Seconds");


        // Return the completed view to render on screen

        return convertView;

    }


}
