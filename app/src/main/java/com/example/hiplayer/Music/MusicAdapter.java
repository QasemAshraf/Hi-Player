package com.example.hiplayer.Music;

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

public class MusicAdapter extends RecyclerView.Adapter<MyMusicHolder> {

    private Context mContext;
    public static ArrayList<MusicFiles> mFiles;


    public MusicAdapter(Context mContext, ArrayList<MusicFiles> mFiles) {
        this.mContext = mContext;
        MusicAdapter.mFiles = mFiles;

    }

    @NonNull
    @Override
    public MyMusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.music_items, parent, false);
        return new MyMusicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyMusicHolder holder, final int position) {

        holder.bindView(mFiles.get(position));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra("position", position);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    public void updateList (ArrayList<MusicFiles> musicFilesArrayList)
    {
        mFiles = new ArrayList<>();
        mFiles.addAll(musicFilesArrayList);
        notifyDataSetChanged();
    }

}

