package com.cse437.myapplication.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.cse437.myapplication.R;

public class PlayerFragment extends Fragment {
    ImageView rewindBtn, fastForwardBtn, playPauseBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rewindBtn =  getView().findViewById(R.id.rewind_btn);
        fastForwardBtn =  getView().findViewById(R.id.play_pause_btn);
        playPauseBtn =  getView().findViewById(R.id.fast_forward_btn);
    }
}
