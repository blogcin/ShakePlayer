package xyz.alteration.shakeplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.PowerManager;

import java.io.IOException;

/**
 * Created by blogcin on 2016-11-22.
 */

public class MusicPlayer extends Thread implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    private MediaPlayer mediaPlayer = null;
    private Context context = null;
    private int musicPostition = 0;
    private boolean prepared = false;

    public MusicPlayer(Context context) {
        this.context = context;
        initMediaPlayer();
    }

    @Override
    public void run() {
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setWakeMode(context,
                PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        try {
            mediaPlayer.setDataSource(MusicPlayList.getMusic(musicPostition));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pauseMediaPlayer() {
        try {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void startMediaPlayer() {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                mediaPlayer.start();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        musicPostition += 1;

        try {
            mediaPlayer.setDataSource(MusicPlayList.getMusic(musicPostition));
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        prepared = true;
    }
}
