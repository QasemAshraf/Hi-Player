package com.example.hiplayer.Album.AlbumDetailsAdapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.hiplayer.Model.MusicFiles;
import com.example.hiplayer.R;

import static com.example.hiplayer.Utils.Utilities.getAlbumArt;


public class MyAlbumDetailsHolder extends RecyclerView.ViewHolder {

    private ImageView music_img;
    private TextView album_name;

    public MyAlbumDetailsHolder(@NonNull View itemView) {
        super(itemView);

        music_img = itemView.findViewById(R.id.music_img);
        album_name = itemView.findViewById(R.id.music_file_name);


    }

    void bindView(MusicFiles musicFiles)
    {
        album_name.setText(musicFiles.getTitle());
        byte[] image = getAlbumArt(musicFiles.getPath());

        if (image != null)
        {
            Glide.with(itemView)
                    .asBitmap()
                    .load(image)
                    .placeholder(R.drawable.app_icon)
                    .error(R.drawable.app_icon)
                    .into(music_img);
        }
        else
        {
            Glide.with(itemView)
                    .load(R.drawable.app_icon)
                    .placeholder(R.drawable.app_icon)
                    .error(R.drawable.app_icon)
                    .into(music_img);
        }
    }
}
