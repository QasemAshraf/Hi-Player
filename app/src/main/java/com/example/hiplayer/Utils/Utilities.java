package com.example.hiplayer.Utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.hiplayer.Model.MusicFiles;
import com.example.hiplayer.R;
import java.util.ArrayList;

public class Utilities
{
    public static ArrayList<MusicFiles> albums = new ArrayList<>();

    public static ArrayList<MusicFiles> getAllAudio (Context context)
    {
        ArrayList<String> duplicate = new ArrayList<>();
        albums.clear();
        ArrayList<MusicFiles> audioList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String [] projection =
                {
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.DURATION,
                };
        Cursor cursor = context.getContentResolver().query(uri, projection,
                null,null,null);
        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
                String path = cursor.getString(0);
                String title = cursor.getString(1);
                String album = cursor.getString(2);
                String artist = cursor.getString(3);

                MusicFiles musicFiles = new MusicFiles(path, title, album, artist);
                audioList.add(musicFiles);
                if (!duplicate.contains(album))
                {
                    albums.add(musicFiles);
                    duplicate.add(album);
                }
            }
            cursor.close();
        }
        return audioList;

    }

    public static String formattedTime(int mCurrentPosition)
    {
        String totalOut;
        String totalNew;
        String seconds = String.valueOf(mCurrentPosition % 60);
        String minutes = String.valueOf(mCurrentPosition / 60);
        totalOut = minutes + ":" + seconds;
        totalNew = minutes + ":" + "0" + seconds;
        if (seconds.length() == 1) {
            return totalNew;
        }
        else
        {
            return totalOut;
        }
    }

    public static byte[] getAlbumArt(String uri)
    {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }

    public static void ImageAnimation (final Context context, final ImageView imageView, final Bitmap bitmap)
    {
        Animation animOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        final Animation animIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        animOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Glide.with(context)
                        .load(bitmap)
                        .placeholder(R.drawable.app_icon)
                        .error(R.drawable.app_icon)
                        .into(imageView);
                animIn.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                imageView.startAnimation(animIn);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(animOut);
    }

}
