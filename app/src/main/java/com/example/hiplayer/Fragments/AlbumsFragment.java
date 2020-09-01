package com.example.hiplayer.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.hiplayer.Album.AlbumAdapter.AlbumAdapter;
import com.example.hiplayer.R;
import static com.example.hiplayer.Utils.Utilities.albums;

public class AlbumsFragment extends Fragment {


    public static AlbumAdapter albumAdapter;

    public AlbumsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        iniView(view);

        return view;

    }

    private void iniView(View view)
    {

        RecyclerView recyclerView = view.findViewById(R.id.albums_recycler_view);
        recyclerView.setHasFixedSize(true);
        if (!(albums.size() < 1))
        {
            albumAdapter = new AlbumAdapter(getContext(), albums);
            recyclerView.setAdapter(albumAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        }

    }


}
