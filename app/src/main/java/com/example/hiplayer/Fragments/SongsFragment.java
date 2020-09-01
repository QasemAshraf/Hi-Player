package com.example.hiplayer.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.hiplayer.Music.MusicAdapter;
import com.example.hiplayer.R;
import static com.example.hiplayer.Main.MainActivity.musicFiles;


public class SongsFragment extends Fragment {

    public static MusicAdapter musicAdapter;


    public SongsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_songs, container, false);
        iniView(view);
        return view;

    }

    private void iniView(View view)
    {
        RecyclerView musicRecyclerView = view.findViewById(R.id.songs_recycler_view);
        musicRecyclerView.setHasFixedSize(true);
        if (!(musicFiles.size() < 1))
        {
            musicAdapter = new MusicAdapter(getContext(), musicFiles);
            musicRecyclerView.setAdapter(musicAdapter);
            musicRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,
                    false));
        }

    }

}
