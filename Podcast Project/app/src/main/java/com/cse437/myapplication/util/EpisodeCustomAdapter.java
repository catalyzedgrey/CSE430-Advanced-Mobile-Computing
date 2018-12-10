package com.cse437.myapplication.util;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cse437.myapplication.R;
import com.cse437.myapplication.model.Episode;

import java.util.ArrayList;

public class EpisodeCustomAdapter extends ArrayAdapter<Episode> {

    public EpisodeCustomAdapter(Context context, ArrayList<Episode> details) {
        super(context, 0, details);
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

        final ImageView img = (ImageView)  convertView.findViewById(R.id.play_det_btn);

        //TextView lengthTV = (TextView) convertView.findViewById(R.id.length_tv);

        // Populate the data into the template view using the data object

        nameTV.setText(episode.getTitle());

        //epNumTV.setText(1+"");

        lengthTV.setText((Integer.parseInt(episode.getLength())/60) + " min");

        img.setImageResource(R.drawable.play);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer mpAudio = AudioService.getMpAudio();
                Toast.makeText(getContext(), "playing", Toast.LENGTH_LONG).show();
                if(mpAudio != null){
                    if(mpAudio.isPlaying()){
                        mpAudio.pause();
                        img.setImageResource(R.drawable.play);
                    }else{
                        mpAudio.start();
                        img.setImageResource(R.drawable.pause);
                    }
                }else{
                    getContext().startService(new Intent(getContext(), AudioService.class));
                    img.setImageResource(R.drawable.pause);
                }
                //Intent i = new Intent(getContext(), AudioService.class);
                //i.putExtra("sourceUrl", episode.getUrl());
                //getContext().startService(i);

            }
        });

        //lengthTV.setText(episode.getLength());


        // Return the completed view to render on screen

        return convertView;

    }


}
