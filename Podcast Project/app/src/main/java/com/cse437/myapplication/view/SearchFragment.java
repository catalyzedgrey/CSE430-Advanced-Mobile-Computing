package com.cse437.myapplication.view;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import com.cse437.myapplication.R;
import com.cse437.myapplication.model.Podcast;
import com.cse437.myapplication.model.Results;
import com.cse437.myapplication.util.SearchCustomAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    ListView searchResLV;
    EditText searchET;
    SearchView searchView;

    String queryHalf1 = "https://itunes.apple.com/search?term=";
    String queryhalf2 = "&country=us&entity=podcast&limit=20";


    List<Podcast> podcasts;
    SearchCustomAdapter searchAdapter;


    OnEpisodesLoaded mCallback;

    public interface OnEpisodesLoaded{
        public void onEpisodeAdded(Podcast podcast);
    }

    public void setOnEpisodesLoaded(Activity activity){
        mCallback = (OnEpisodesLoaded) activity;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        podcasts = new ArrayList<Podcast>();

        searchResLV = view.findViewById(R.id.search_res_lv);
        searchAdapter = new SearchCustomAdapter(getActivity(), podcasts);
        searchResLV.setAdapter(searchAdapter);

        searchResLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Podcast pod = (Podcast) parent.getAdapter().getItem(position);
                mCallback.onEpisodeAdded(pod);
            }
        });

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        searchView = view.findViewById(R.id.search_view);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                try {
                    new Test().execute(new URL(queryHalf1 + s + queryhalf2));

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


    }


    class Test extends AsyncTask<URL, Void, Void> {
        String s = "";

        @Override
        protected Void doInBackground(URL... urls) {

            URL url = urls[0];

            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader isw = new InputStreamReader(in);

                int data = isw.read();
                while (data != -1) {
                    char current = (char) data;
                    data = isw.read();
                    s += current;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            Results res = parseJSON(s);
            // final List<Podcast> podcasts = new ArrayList<Podcast>();
            podcasts = res.getResults();
            searchAdapter.clear();
            searchAdapter.addAll(podcasts);
            searchAdapter.notifyDataSetChanged();

//            podcasts.add(res.getResults().get(0));

//            searchResLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                    Toast.makeText(getActivity(), "ggg", Toast.LENGTH_LONG).show();
//                    mCallback.onEpisodeAdded(pod);;
//
//                }
//            });
            //*tv.setText(s);
        }


    }

    public static Results parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        Results results = gson.fromJson(response, Results.class);
        return results;
    }

}
