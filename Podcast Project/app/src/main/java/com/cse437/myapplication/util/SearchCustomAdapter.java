package com.cse437.myapplication.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cse437.myapplication.R;
import com.cse437.myapplication.model.Episode;
import com.cse437.myapplication.model.Podcasts;
import com.cse437.myapplication.view.ContentFragment;
import com.cse437.myapplication.view.MainActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SearchCustomAdapter extends ArrayAdapter<Podcasts> {
    private LayoutInflater layoutinflater;
    private List<Podcasts> listStorage;
    private Context context;
    //SearchCustomAdapter.ViewHolder listViewHolder;


    public SearchCustomAdapter(Context context, List<Podcasts> customizedListView) {
        super(context, 0, customizedListView);
    }
//    public SearchCustomAdapter(Context context, List<Podcasts> customizedListView) {
//
//        this.context = context;
//        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        listStorage = customizedListView;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Podcasts pod = getItem(position);

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

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentFragment.podcasts.add(pod);
            }
        });

        return convertView;
    }
//
//    @Override
//    public int getCount() {
//        return listStorage.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return position;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        final int pos = position;
//
//        if(convertView == null){
//            listViewHolder = new SearchCustomAdapter.ViewHolder();
//            convertView = layoutinflater.inflate(R.layout.fragment_search, parent, false);
//
//            listViewHolder.albumArtImgView = (ImageView)convertView.findViewById(R.id.search_art_view);
//            listViewHolder.nameTxtView = (TextView)convertView.findViewById(R.id.search_name_view);
//            listViewHolder.addImgView = (ImageView)convertView.findViewById(R.id.add_btn);
//
//
//            convertView.setTag(listViewHolder);
//        }else{
//            listViewHolder = (SearchCustomAdapter.ViewHolder)convertView.getTag();
//        }
//
//
//        //int imageResourceId = this.context.getResources().getIdentifier(listStorage.get(position).getArtworkUrl600(), "drawable", this.context.getPackageName());
//        //new Test().execute(position);
//        String url = listStorage.get(position).getArtworkUrl600();
//
//        listViewHolder.nameTxtView.setText(listStorage.get(position).getArtistName());
//        Picasso.get().load(url).into(listViewHolder.albumArtImgView);
//
//
////        convertView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Toast.makeText(context, "ggg", Toast.LENGTH_LONG).show();
////                listStorage.get(pos);
////                listStorage.get(pos);
////            }
////        });
//
//        return convertView;
//    }
//
//    static class ViewHolder{
//        ImageView albumArtImgView;
//        TextView nameTxtView;
//        ImageView addImgView;
//    }

//    class Test extends AsyncTask<Integer, Void, Void> {
//        String s = "";
//        Bitmap bitmap;
//        @Override
//        protected Void doInBackground(Integer... pos) {
//            try {
//                bitmap = BitmapFactory.decodeStream((InputStream)new URL(listStorage.get(pos[0]).getArtworkUrl600()).getContent());
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//
//        }
//
//
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            listViewHolder.addImgView.setImageBitmap(bitmap);
//        }
//
//
//    }
}