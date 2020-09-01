package com.example.hiplayer.Album.AlbumDetailsAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hiplayer.Model.MusicFiles;
import com.example.hiplayer.PlayerActivity.PlayerActivity;
import com.example.hiplayer.R;

import java.util.ArrayList;

public class AlbumDetailsAdapter extends RecyclerView.Adapter<MyAlbumDetailsHolder> {

    private Context mContext;
    public static ArrayList<MusicFiles> albumFiles;

    public AlbumDetailsAdapter(Context mContext, ArrayList<MusicFiles> albumFiles) {
        this.mContext = mContext;
        AlbumDetailsAdapter.albumFiles = albumFiles;
    }

    @NonNull
    @Override
    public MyAlbumDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.music_items, parent, false);
        return new MyAlbumDetailsHolder( view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAlbumDetailsHolder holder, final int position) {

        holder.bindView(albumFiles.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra("sender", "albumDetails");
                intent.putExtra("position", position);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return albumFiles.size();
    }
}
