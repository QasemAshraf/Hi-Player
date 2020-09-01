package com.example.hiplayer.PlayerActivity;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.hiplayer.Model.MusicFiles;
import com.example.hiplayer.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Random;
import static com.example.hiplayer.Album.AlbumDetailsAdapter.AlbumDetailsAdapter.albumFiles;
import static com.example.hiplayer.Main.MainActivity.repeatBoolean;
import static com.example.hiplayer.Main.MainActivity.shuffleBoolean;
import static com.example.hiplayer.Music.MusicAdapter.mFiles;
import static com.example.hiplayer.Utils.Utilities.ImageAnimation;
import static com.example.hiplayer.Utils.Utilities.formattedTime;
import static com.example.hiplayer.Utils.Utilities.getAlbumArt;

public class PlayerActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener,
        View.OnClickListener {

    private TextView song_name, artist_name, duration_played, duration_total;
    private ImageView cover_art;
    private ImageView nextBtn;
    private ImageView prevBtn;
    private ImageView shuffleBtn;
    private ImageView repeatBtn;
    private FloatingActionButton playPauseBtn;
    private SeekBar seekBar;
    private int position = -1;
    static ArrayList<MusicFiles> listSong = new ArrayList<>();
    static Uri uri;
    public static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        iniView();
        getIntentMethod();
        setPhoneStateListener();


    }
    PhoneStateListener phoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            if (state == TelephonyManager.CALL_STATE_RINGING) {
                //Incoming call: Pause music
                playPauseBtn.setBackgroundResource(R.drawable.ic_pause);
                mediaPlayer.pause();
            } else if(state == TelephonyManager.CALL_STATE_IDLE) {
                //Not in call: Play music
                playPauseBtn.setBackgroundResource(R.drawable.ic_play);
                mediaPlayer.start();
            } else if(state == TelephonyManager.CALL_STATE_OFFHOOK) {
                //A call is dialing, active or on hold
                playPauseBtn.setBackgroundResource(R.drawable.ic_pause);
                mediaPlayer.pause();
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    };

    private void setPhoneStateListener()
    {
        TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if(mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }


    private void runOnUiThread() {
        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                    duration_played.setText(formattedTime(mCurrentPosition));

                    int mCurrentPositionEnd = mediaPlayer.getDuration() / 1000;
                    seekBar.setMax(mCurrentPositionEnd);
                    duration_total.setText(formattedTime(mCurrentPositionEnd));

                }
                handler.postDelayed(this, 1000);
            }
        });

    }

    private void iniView() {

        song_name = findViewById(R.id.song_name);
        artist_name = findViewById(R.id.song_artist);
        duration_played = findViewById(R.id.durationPlayed);
        duration_total = findViewById(R.id.durationTotal);
        cover_art = findViewById(R.id.cover_art);
        nextBtn = findViewById(R.id.next);
        prevBtn = findViewById(R.id.prev);
        shuffleBtn = findViewById(R.id.shuffle);
        repeatBtn = findViewById(R.id.repeat);
        playPauseBtn = findViewById(R.id.play_pause);
        seekBar = findViewById(R.id.seekBar);

        shuffleBtn.setOnClickListener(this);
        repeatBtn.setOnClickListener(this);




    }

    private void iniUiPlayer() {
        mediaPlayer.setOnCompletionListener(this);

        song_name.setText(listSong.get(position).getTitle());
        artist_name.setText(listSong.get(position).getArtist());
        byte[] image = getAlbumArt(listSong.get(position).getPath());
        Bitmap bitmap;
        if (image != null) {
            bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            ImageAnimation(this, cover_art, bitmap);
        } else {
            Glide.with(this)
                    .asBitmap()
                    .load(R.drawable.app_icon)
                    .placeholder(R.drawable.app_icon)
                    .error(R.drawable.app_icon)
                    .into(cover_art);
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        runOnUiThread();

    }

    private void getIntentMethod()
    {
        position = getIntent().getIntExtra("position", -1);
        String sender = getIntent().getStringExtra("sender");


        if (sender != null && sender.equals("albumDetails")) {
            listSong = albumFiles;
        } else {
            listSong = mFiles;
        }

        if (listSong != null) {
            playPauseBtn.setImageResource(R.drawable.ic_pause);
            uri = Uri.parse(listSong.get(position).getPath());
        }
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        } else {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
        iniUiPlayer();

    }

    private int getRandom(int i) {
        Random random = new Random();
        return random.nextInt(i + 1);
    }

    @Override
    protected void onResume() {
//        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        playThreadBtn();
        prevThreadBtn();
        nextThreadBtn();
        super.onResume();
    }

    private void playPauseBtnClicked() {

        if (mediaPlayer.isPlaying()) {
            playPauseBtn.setImageResource(R.drawable.ic_play);
            mediaPlayer.pause();
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            runOnUiThread();
        } else {
            playPauseBtn.setImageResource(R.drawable.ic_pause);
            mediaPlayer.start();
            seekBar.setProgress(mediaPlayer.getDuration() / 1000);
            runOnUiThread();
        }
    }

    private void nextThreadBtn() {

        Thread nextThread = new Thread() {
            @Override
            public void run() {
                super.run();
                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nextBtnClicked();

                    }
                });
            }
        };
        nextThread.start();

    }

    private void nextBtnClicked() {

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            if (shuffleBoolean && !repeatBoolean) {
                position = getRandom(listSong.size() - 1);
            } else if (!shuffleBoolean && !repeatBoolean) {
                position = ((position + 1) % listSong.size());
            }
            uri = Uri.parse(listSong.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
//            metaData(uri);
            iniUiPlayer();
            mediaPlayer.setOnCompletionListener(this);
            playPauseBtn.setBackgroundResource(R.drawable.ic_pause);
            mediaPlayer.start();
        } else {
            mediaPlayer.stop();
            mediaPlayer.release();
            if (shuffleBoolean && !repeatBoolean) {
                position = getRandom(listSong.size() - 1);
            } else if (!shuffleBoolean && !repeatBoolean) {
                position = ((position + 1) % listSong.size());
            }
            uri = Uri.parse(listSong.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
//            metaData(uri);
            iniUiPlayer();
            mediaPlayer.setOnCompletionListener(this);
            playPauseBtn.setBackgroundResource(R.drawable.ic_play);
        }
    }

    private void prevThreadBtn() {

        Thread prevThread = new Thread() {
            @Override
            public void run() {
                super.run();
                prevBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prevBtnClicked();
                    }
                });
            }
        };
        prevThread.start();

    }

    private void prevBtnClicked() {

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            if (shuffleBoolean && !repeatBoolean)
            {
                position = getRandom(listSong.size() - 1);
            }
            else if (!shuffleBoolean && !repeatBoolean)
            {
                position = ((position - 1) < 0 ? (listSong.size() - 1) : (position - 1));
            }
            uri = Uri.parse(listSong.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
//            metaData(uri);
            iniUiPlayer();
            playPauseBtn.setBackgroundResource(R.drawable.ic_pause);
            mediaPlayer.start();
        } else {
            mediaPlayer.stop();
            mediaPlayer.release();
            if (shuffleBoolean && !repeatBoolean) {
                position = getRandom(listSong.size() - 1);
            } else if (!shuffleBoolean && !repeatBoolean) {
                position = ((position - 1) < 0 ? (listSong.size() - 1) : (position - 1));
            }
            uri = Uri.parse(listSong.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
//            metaData(uri);
            iniUiPlayer();
            playPauseBtn.setBackgroundResource(R.drawable.ic_pause);
        }

    }

    private void playThreadBtn() {

        Thread playThread = new Thread() {
            @Override
            public void run() {
                super.run();

                playPauseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playPauseBtnClicked();
                    }
                });
            }
        };

        playThread.start();
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        nextBtnClicked();
        if (mediaPlayer != null) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shuffle:
                if (shuffleBoolean) {
                    shuffleBoolean = false;
                    shuffleBtn.setImageResource(R.drawable.ic_shuffle_of);

                } else {
                    shuffleBoolean = true;
                    shuffleBtn.setImageResource(R.drawable.ic_shuffle_on);
                }
                break;

            case R.id.repeat:

                if (repeatBoolean) {
                    repeatBoolean = false;
                    repeatBtn.setImageResource(R.drawable.ic_repeat_one_of);
                } else {
                    repeatBoolean = true;
                    repeatBtn.setImageResource(R.drawable.ic_repeat_one_on);
                }
                break;
        }
    }





}
