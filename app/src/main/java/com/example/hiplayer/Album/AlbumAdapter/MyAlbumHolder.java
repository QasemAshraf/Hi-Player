package com.example.hiplayer.Album.AlbumAdapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.hiplayer.Model.MusicFiles;
import com.example.hiplayer.R;

import static com.example.hiplayer.Utils.Utilities.getAlbumArt;


public class MyAlbumHolder extends RecyclerView.ViewHolder {

    private ImageView album_image;
    private TextView album_name;

    public MyAlbumHolder(@NonNull View itemView)
    {
        super(itemView);

        album_image = itemView.findViewById(R.id.album_image);
        album_name = itemView.findViewById(R.id.album_name);
    }

    void bindView (MusicFiles musicFiles)
    {
        album_name.setText(musicFiles.getAlbum());

        byte[] image = getAlbumArt(musicFiles.getPath());
        if (image != null)
        {
            Glide.with(itemView)
                    .asBitmap()
                    .load(image)
                    .placeholder(R.drawable.app_icon)
                    .error(R.drawable.app_icon)
                    .into(album_image);
        }
        else
        {
            Glide.with(itemView)
                    .load(R.drawable.app_icon)
                    .placeholder(R.drawable.app_icon)
                    .error(R.drawable.app_icon)
                    .into(album_image);
        }

    }
}
