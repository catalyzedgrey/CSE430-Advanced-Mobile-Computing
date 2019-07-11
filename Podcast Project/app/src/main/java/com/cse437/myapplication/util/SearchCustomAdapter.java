package com.cse437.myapplication.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cse437.myapplication.R;
import com.cse437.myapplication.model.Podcast;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchCustomAdapter extends ArrayAdapter<Podcast> {
    private LayoutInflater layoutinflater;
    private List<Podcast> listStorage;
    private Context context;
    //SearchCustomAdapter.ViewHolder listViewHolder;

    public SearchCustomAdapter(Context context, List<Podcast> customizedListView) {
        super(context, 0, customizedListView);
    }

//    public SearchCustomAdapter(Context context, List<Podcast> customizedListView) {
//
//        this.context = context;
//        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        listStorage = customizedListView;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Podcast pod = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_result_item, parent, false);

        }

        ImageView albumArt = (ImageView) convertView.findViewById(R.id.search_art_view);
        TextView nameTV = (TextView) convertView.findViewById(R.id.search_name_view);
        ImageView addImg = (ImageView) convertView.findViewById(R.id.add_btn);

        nameTV.setText(pod.getCollectionName());
        addImg.setImageResource(R.drawable.plus);
        Picasso.get().load(pod.getArtworkUrl600()).into(albumArt);

        pod.setImg(albumArt.getDrawable());


        return convertView;
    }


}