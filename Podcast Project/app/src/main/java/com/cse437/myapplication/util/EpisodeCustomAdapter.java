package com.cse437.myapplication.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cse437.myapplication.R;
import com.cse437.myapplication.model.Episode;

import java.util.ArrayList;

public class EpisodeCustomAdapter extends ArrayAdapter<Episode> {
    private int totalLength =0;

    public EpisodeCustomAdapter(Context context, ArrayList<Episode> details) {
        super(context, 0, details);
        totalLength = details.size();
    }


    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position

        final Episode episode = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.detail_item, parent, false);

        }

        // Lookup view for data population

        TextView nameTV = (TextView) convertView.findViewById(R.id.name_tv);

        TextView epNumTV = (TextView) convertView.findViewById(R.id.episode_num_tv);

        TextView lengthTV = (TextView) convertView.findViewById(R.id.length_tv);

        final ImageView img = (ImageView) convertView.findViewById(R.id.play_det_btn);

        epNumTV.setText(totalLength-position+"");


        //TextView lengthTV = (TextView) convertView.findViewById(R.id.length_tv);

        // Populate the data into the template view using the data object

        nameTV.setText(episode.getTitle());


        lengthTV.setText(episode.getLength());

        img.setImageResource(R.drawable.play);


        return convertView;

    }


}
