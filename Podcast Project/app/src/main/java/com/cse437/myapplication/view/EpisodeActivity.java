//package com.cse437.myapplication.view;
//
//import android.os.AsyncTask;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.widget.ListView;
//
//import com.cse437.myapplication.R;
//import com.cse437.myapplication.model.Episode;
//import com.cse437.myapplication.util.EpisodeCustomAdapter;
//import com.cse437.myapplication.util.XMLParser;
//
//import org.xmlpull.v1.XmlPullParserException;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//public class EpisodeActivity extends AppCompatActivity {
//
//    ListView episodeListView;
//    ArrayList<Episode> episodes;
//    EpisodeCustomAdapter customAdapter;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_episode);
//
//        episodeListView = findViewById(R.id.episode_lv);
//
//        String url = getIntent().getStringExtra("url");
//        new DownloadXmlTask().execute(url);
//
//
//
//    }
//    private class DownloadXmlTask extends AsyncTask<String, Void, List<Episode>> {
//        @Override
//        protected List<Episode> doInBackground(String... urls) {
//            try {
//                episodes = loadXmlFromNetwork(urls[0]);
//            } catch (IOException e) {
//
//            } catch (XmlPullParserException e) {
//
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(List<Episode> result) {
//            customAdapter = new EpisodeCustomAdapter(getApplicationContext(), episodes);
//
//            episodeListView.setAdapter(customAdapter);
//        }
//    }
//
//    private ArrayList<Episode> loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
//        InputStream stream = null;
//        // Instantiate the parser
//        XMLParser XMLParser = new XMLParser();
//        ArrayList<Episode> entries = null;
//        String title = null;
//        String url = null;
//        String summary = null;
//        Calendar rightNow = Calendar.getInstance();
//        DateFormat formatter = new SimpleDateFormat("MMM dd h:mmaa");
//
//
//
//        try {
//            stream = downloadUrl(urlString);
//            entries = XMLParser.parse(stream);
//            entries.get(0);
//            // Makes sure that the InputStream is closed after the app is
//            // finished using it.
//        } finally {
//            if (stream != null) {
//                stream.close();
//            }
//        }
//
//        return entries;
//    }
//
//    private InputStream downloadUrl(String urlString) throws IOException {
//        URL url = new URL(urlString);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setReadTimeout(10000 /* milliseconds */);
//        conn.setConnectTimeout(15000 /* milliseconds */);
//        conn.setRequestMethod("GET");
//        conn.setDoInput(true);
//        // Starts the query
//        conn.connect();
//        return conn.getInputStream();
//    }
//}
