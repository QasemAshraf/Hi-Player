package com.example.hiplayer.Music;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.hiplayer.Model.MusicFiles;
import com.example.hiplayer.R;
import static com.example.hiplayer.Utils.Utilities.getAlbumArt;

public class MyMusicHolder extends RecyclerView.ViewHolder {

    private ImageView musicImg;
    private TextView musicName;

    MyMusicHolder(@NonNull View itemView)
    {
        super(itemView);
        musicImg = itemView.findViewById(R.id.music_img);
        musicName = itemView.findViewById(R.id.music_file_name);
    }

    void bindView (final MusicFiles musicFiles)
    {
        musicName.setText(musicFiles.getTitle());
        byte[] image = getAlbumArt(musicFiles.getPath());
        if (image != null)
        {
            Glide.with(itemView)
                    .asBitmap()
                    .load(image)
                    .placeholder(R.drawable.app_icon)
                    .error(R.drawable.app_icon)
                    .into(musicImg);
        }
        else
        {
            Glide.with(itemView)
                    .load(R.drawable.app_icon)
                    .placeholder(R.drawable.app_icon)
                    .error(R.drawable.app_icon)
                    .into(musicImg);
        }

    }

}