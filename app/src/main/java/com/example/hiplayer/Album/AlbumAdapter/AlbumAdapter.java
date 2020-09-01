package com.example.hiplayer.Album.AlbumAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hiplayer.Album.AlbumDetails;
import com.example.hiplayer.Model.MusicFiles;
import com.example.hiplayer.R;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<MyAlbumHolder> {

    private Context mContext;
    private ArrayList<MusicFiles> albumFiles;

    public AlbumAdapter(Context mContext, ArrayList<MusicFiles> albumFiles) {
        this.mContext = mContext;
        this.albumFiles = albumFiles;
    }

    @NonNull
    @Override
    public MyAlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.album_item, parent, false);
        return new MyAlbumHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAlbumHolder holder, final int position) {

        holder.bindView(albumFiles.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AlbumDetails.class);
                intent.putExtra("albumName", albumFiles.get(position).getAlbum());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return albumFiles.size();
    }

    public void updateList (ArrayList<MusicFiles> musicFilesArrayList)
    {
        albumFiles = new ArrayList<>();
        albumFiles.addAll(musicFilesArrayList);
        notifyDataSetChanged();
    }
}
