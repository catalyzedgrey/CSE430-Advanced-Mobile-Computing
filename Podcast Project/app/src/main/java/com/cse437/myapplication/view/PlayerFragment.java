package com.cse437.myapplication.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;

import com.cse437.myapplication.R;

public class PlayerFragment extends Fragment { // implements EpisodeCustomAdapter.OnPlay {

    ImageView rewindBtn, fastForwardBtn, playPauseBtn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_player, container, false);;
        MediaController mc = new MediaController(getContext());
        mc.setAnchorView(v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rewindBtn =  getView().findViewById(R.id.rewind_btn);
        fastForwardBtn =  getView().findViewById(R.id.fast_forward_btn);
        playPauseBtn =  getView().findViewById(R.id.play_pause_btn);
    }

//    @Override
//    public void OnPlay(Episode episode) {
//        if(playPauseBtn != null){
//            Toast.makeText(getContext(), "I'm player", Toast.LENGTH_SHORT).show();
//            playPauseBtn.setImageResource(R.drawable.pause);
//        }
//    }
}
