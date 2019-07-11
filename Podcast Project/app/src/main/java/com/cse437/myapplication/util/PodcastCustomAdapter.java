package com.cse437.myapplication.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.cse437.myapplication.R;
import com.cse437.myapplication.model.Podcast;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PodcastCustomAdapter extends ArrayAdapter {
    private LayoutInflater layoutinflater;
    private List<Podcast> listStorage;
    private Context context;

    public PodcastCustomAdapter(Context context, List<Podcast> customizedListView) {
        super(context, 0, customizedListView);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;

        final Podcast pod = (Podcast) getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_with_text_image, parent, false);
        }


        final ImageView albumArt = (ImageView) convertView.findViewById(R.id.imageView);

        //int imageResourceId = this.context.getResources().getIdentifier(listStorage.get(position).getArtworkUrl600(), "drawable", this.context.getPackageName());
        //new Test().execute(position);
        final String url = pod.getArtworkUrl600();
        final Picasso p;
//        if(pod.getImg() == null){
//            Target target = new Target() {
//                @Override
//                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                    albumArt.setImageBitmap(bitmap);
//                    Drawable image = albumArt.getDrawable();
//                    pod.setImg(image);
//                }
//
//                @Override
//                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//                }
//
//                @Override
//                public void onPrepareLoad(Drawable placeHolderDrawable) {}
//            };
//
//            Picasso.get().load("url").into(target);
//        }
//
//        else
            albumArt.setImageDrawable(pod.getImg());


        return convertView;
    }

}
