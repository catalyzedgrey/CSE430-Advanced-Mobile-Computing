package com.cse437.myapplication.view;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.cse437.myapplication.R;
import com.cse437.myapplication.model.Podcast;
import com.cse437.myapplication.util.DBAdapter;
import com.cse437.myapplication.util.PodcastCustomAdapter;

public class PodcastFragment extends Fragment {

    GridView gridView;

    PodcastCustomAdapter podcastCustomAdapter;

    PodcastFragment.EpisodeSwitcher mCallback;

    MainActivity mainActivity;


    public interface EpisodeSwitcher{
        public void onEpisodeSwitch(Podcast podcast);
    }

    public void setOnEpisodeSwitch(Activity activity){
        mCallback = (PodcastFragment.EpisodeSwitcher) activity;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        gridView = view.findViewById(R.id.gridview);

        mainActivity = (MainActivity) getActivity();

        podcastCustomAdapter = new PodcastCustomAdapter(getActivity(), mainActivity.podcasts);
        //PopulateList(myDb.getAllRows());
        gridView.setAdapter(podcastCustomAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Podcast pod = (Podcast) parent.getAdapter().getItem(position);

                mCallback.onEpisodeSwitch(pod);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }



    private void PopulateList(Cursor cursor) {
        String message = "";
        podcastCustomAdapter.clear();
        // populate the message from the cursor

        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                String name = cursor.getString(DBAdapter.COL_NAME);
                String feed = cursor.getString(DBAdapter.COL_URL);
                String art = cursor.getString(DBAdapter.ART_URL);

                mainActivity.podcasts.add(new Podcast(name, feed, art));

            } while(cursor.moveToNext());
        }


        cursor.close();
        podcastCustomAdapter.notifyDataSetChanged();

        // [TO_DO_B7]
        // Update the list view

        // [TO_DO_B8]
        // Display a Toast message
    }


}
