package com.example.hiplayer.Album;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.hiplayer.Album.AlbumDetailsAdapter.AlbumDetailsAdapter;
import com.example.hiplayer.Model.MusicFiles;
import com.example.hiplayer.R;

import java.util.ArrayList;
import static com.example.hiplayer.Main.MainActivity.musicFiles;
import static com.example.hiplayer.Utils.Utilities.getAlbumArt;

public class AlbumDetails extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<MusicFiles> albumSongs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);

        iniView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!(albumSongs.size() < 1))
        {
            AlbumDetailsAdapter albumDetailsAdapter = new AlbumDetailsAdapter(this, albumSongs);
            recyclerView.setAdapter(albumDetailsAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,
                    RecyclerView.VERTICAL, false));
        }
    }

    private void iniView() {

        recyclerView = findViewById(R.id.details_recycler_view);
        ImageView albumPhoto = findViewById(R.id.albumPhoto);
        String albumName = getIntent().getStringExtra("albumName");
        int j = 0;
        for (int i = 0; i < musicFiles.size(); i ++)
        {
            assert albumName != null;
            if (albumName.equals(musicFiles.get(i).getAlbum()))
            {
                albumSongs.add(j, musicFiles.get(i));
                j ++;
            }
        }
        byte[] image = getAlbumArt(albumSongs.get(0).getPath());
        if (image != null)
        {

            Glide.with(this)
                    .load(image)
                    .placeholder(R.drawable.app_icon)
                    .error(R.drawable.app_icon)
                    .into(albumPhoto);
        }
        else
        {
            Glide.with(this)
                    .load(R.drawable.app_icon)
                    .placeholder(R.drawable.app_icon)
                    .error(R.drawable.app_icon)
                    .into(albumPhoto);
        }
    }

}
