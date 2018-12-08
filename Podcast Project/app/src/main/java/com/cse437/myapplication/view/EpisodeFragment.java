package com.cse437.myapplication.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.cse437.myapplication.R;
import com.cse437.myapplication.model.Episode;
import com.cse437.myapplication.util.AudioService;
import com.cse437.myapplication.util.EpisodeCustomAdapter;
import com.cse437.myapplication.util.XMLParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EpisodeFragment extends Fragment {
    ListView episodeListView;
    ArrayList<Episode> episodes;
    EpisodeCustomAdapter customAdapter;
    ImageView playPauseBtn;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.detail_item, container, false);
        return inflater.inflate(R.layout.fragment_episode_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        episodeListView = getView().findViewById(R.id.episode_lv);
        playPauseBtn = v.findViewById(R.id.play_det_btn);

        String url ="https://feeds.megaphone.fm/harmontown";
        new DownloadXmlTask().execute(url);
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
            customAdapter = new EpisodeCustomAdapter(getActivity(), episodes);
            episodeListView.setAdapter(customAdapter);

            playPauseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AudioService();
                }
            });
        }
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
}
