package com.cse437.myapplication.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cse437.myapplication.R;
import com.cse437.myapplication.model.Podcasts;
import com.cse437.myapplication.view.EpisodeFragment;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PodcastCustomAdapter extends BaseAdapter {
    private LayoutInflater layoutinflater;
    private List<Podcasts> listStorage;
    private Context context;
    ViewHolder listViewHolder;

    public PodcastCustomAdapter(Context context, List<Podcasts> customizedListView) {
        this.context = context;
        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
    }
    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;

        if(convertView == null){
            listViewHolder = new ViewHolder();
            convertView = layoutinflater.inflate(R.layout.listview_with_text_image, parent, false);
            //listViewHolder.textInListView = (TextView)convertView.findViewById(R.id.textView);
            listViewHolder.imageInListView = (ImageView)convertView.findViewById(R.id.imageView);
            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }


        //int imageResourceId = this.context.getResources().getIdentifier(listStorage.get(position).getArtworkUrl600(), "drawable", this.context.getPackageName());
        //new Test().execute(position);
        String url = listStorage.get(position).getArtworkUrl600();

        Picasso.get().load(url).into(listViewHolder.imageInListView);


//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, "ggg", Toast.LENGTH_LONG).show();
//                listStorage.get(pos);
//                listStorage.get(pos);
//            }
//        });

        return convertView;
    }

    static class ViewHolder{
        //TextView textInListView;
        ImageView imageInListView;
    }

    class Test extends AsyncTask<Integer, Void, Void> {
        String s = "";
        Bitmap bitmap;
        @Override
        protected Void doInBackground(Integer... pos) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(listStorage.get(pos[0]).getArtworkUrl600()).getContent());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }



        @Override
        protected void onPostExecute(Void aVoid) {
            listViewHolder.imageInListView.setImageBitmap(bitmap);
        }


    }
}
