package xyz.alteration.shakeplayer;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

/**
 * Created by blogcin on 2016-11-21.
 */

public class ShakeService extends Service {
    private final String TAG = getClass().getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void do_now(MusicPlayer musicPlayer, ShakeDetector shakeDetector) {
        while(true) {
            Log.d(TAG, shakeDetector.getShaked() == true ? "true" : "false");
            if(shakeDetector.getShaked()) {
                musicPlayer.startMediaPlayer();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                musicPlayer.pauseMediaPlayer();
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ShakeDetector shakeDetector = new ShakeDetector(ShakeService.this);
                MusicPlayer musicPlayer = new MusicPlayer(ShakeService.this);

                musicPlayer.start();
                try {
                    musicPlayer.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                do_now(musicPlayer, shakeDetector);
            }
        });

        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }


}
