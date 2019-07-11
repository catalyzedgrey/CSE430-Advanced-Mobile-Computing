package com.cse437.myapplication.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cse437.myapplication.R;
import com.cse437.myapplication.model.Episode;
import com.cse437.myapplication.model.Podcast;
import com.cse437.myapplication.util.DbBitmapUtility;
import com.cse437.myapplication.util.EpisodeCustomAdapter;
import com.cse437.myapplication.util.XMLParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//import com.cse437.myapplication.util.AudioService;

public class EpisodeFragment extends Fragment{
    ListView episodeListView;
    ArrayList<Episode> episodes;
    EpisodeCustomAdapter customAdapter;
    ImageView playPauseBtn;

    ImageView imgView;
    TextView textView;

    Podcast currentPodcast;

    OnPlay mCallback;

    LinearLayout linearLayout;



    public interface OnPlay{
        public void onPlay(Episode episode);
    }

    public void setOnPlay(Activity activity){
        mCallback = (OnPlay) activity;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View detView= inflater.inflate(R.layout.detail_item, container, false);
        playPauseBtn = detView .findViewById(R.id.play_det_btn);

        View view = inflater.inflate(R.layout.fragment_episode_list, container, false);



        episodeListView = view.findViewById(R.id.episode_lv);

        episodes = new ArrayList<>();

        imgView = view.findViewById(R.id.episode_album_art);
        imgView.setImageDrawable(currentPodcast.getImg());

        linearLayout = view.findViewById(R.id.episode_list_header);

        Palette p = createPaletteSync(DbBitmapUtility.drawableToBitmap(currentPodcast.getImg()));
        int backgroundColor = ContextCompat.getColor(getContext(), R.color.colorWhite);
        linearLayout.setBackgroundColor(p.getDominantColor(backgroundColor));
        episodeListView.setBackgroundColor(p.getDominantColor(backgroundColor));

        textView = view.findViewById(R.id.episode_description);
        textView.setText(currentPodcast.getArtistName());

        episodeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Episode episode = (Episode) parent.getAdapter().getItem(position);
                mCallback.onPlay(episode);

            }
        });

        customAdapter = new EpisodeCustomAdapter(getActivity(), episodes);
        episodeListView.setAdapter(customAdapter);


        return view; //inflater.inflate(R.layout.fragment_episode_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        LoadEpisodeList(currentPodcast);
    }

    public void setCurrentPodcast(Podcast podcast){
        this.currentPodcast = podcast;
    }

    private class DownloadXmlTask extends AsyncTask<String, Void, List<Episode>> {
        @Override
        protected List<Episode> doInBackground(String... urls) {
            try {
                episodes = loadXmlFromNetwork(urls[0]);
            } catch (IOException e) {

            } catch (XmlPullParserException e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Episode> result) {
            if (getActivity()!=null){
                customAdapter = new EpisodeCustomAdapter(getActivity(), episodes);
                episodeListView.setAdapter(customAdapter);
            }


        }
    }

    public void LoadEpisodeList(Podcast podcast){
        new DownloadXmlTask().execute(podcast.getFeedUrl());
    }


    private ArrayList<Episode> loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
        InputStream stream = null;
        // Instantiate the parser
        XMLParser XMLParser = new XMLParser();
        ArrayList<Episode> entries = null;
        String title = null;
        String url = null;
        String summary = null;
//        Calendar rightNow = Calendar.getInstance();
//        DateFormat formatter = new SimpleDateFormat("MMM dd h:mmaa");



        try {
            stream = downloadUrl(urlString);
            entries = XMLParser.parse(stream);
            entries.get(0);
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        return entries;
    }

    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }

    // Generate palette synchronously and return it
    public Palette createPaletteSync(Bitmap bitmap) {
        Palette p = Palette.from(bitmap).generate();
        return p;
    }

}
