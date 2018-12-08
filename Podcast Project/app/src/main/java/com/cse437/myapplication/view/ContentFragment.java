package com.cse437.myapplication.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import com.cse437.myapplication.R;
import com.cse437.myapplication.model.Episode;
import com.cse437.myapplication.model.Podcasts;
import com.cse437.myapplication.model.Results;
import com.cse437.myapplication.util.PodcastCustomAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ContentFragment extends Fragment {
    Button button;
    GridView gridView;
    FragmentManager fragmentManager;
    SearchView searchView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        button = getView().findViewById(R.id.submit);
        gridView = getView().findViewById(R.id.gridview);

//        searchView = getView().findViewById(R.id.search_view);
//        searchView.

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new Test().execute(new URL("https://itunes.apple.com/search?term=harmontown&country=us&entity=podcast"));
                } catch (Exception e) {
                }
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
            final List<Podcasts> podcasts = new ArrayList<Podcasts>();
            podcasts.add(res.getResults().get(0));

            PodcastCustomAdapter podcastCustomAdapter = new PodcastCustomAdapter(getActivity(), podcasts);
            gridView.setAdapter(podcastCustomAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Toast.makeText(getActivity(), "ggg", Toast.LENGTH_LONG).show();
//                fragmentManager = getFragmentManager();
//
                    EpisodeFragment f = new EpisodeFragment();
////                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////                fragmentTransaction.add(findViewById(R.id.main_frag), f, "f");
//
//                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//                transaction.add(R.id.main_frag, f).commit();
//                transaction.commit();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frag, f).commit();
                    //fragmentTransaction.commit();

                }
            });
            //*tv.setText(s);
        }


    }

    public static Results parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        Results results = gson.fromJson(response, Results.class);
        return results;
    }

}
