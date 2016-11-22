package xyz.alteration.shakeplayer;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by blogcin on 2016-11-22.
 */

public class MusicPlayList {
    private static ArrayList<String> playList = new ArrayList<>();
    private static Lock lock  = new ReentrantLock();

    public static void insertMusic(String musicName) {
        lock.lock();
        playList.add(musicName);
        lock.unlock();
    }

    public static void removeMusic(String musicName) {
        lock.lock();
        playList.remove(musicName);
        lock.unlock();
    }

    public static void removeMusic(int index) {
        lock.lock();
        playList.remove(index);
        lock.unlock();
    }

    public static ArrayList<String> getMusics() {
        ArrayList<String> tempPlayList = null;

        lock.lock();
        tempPlayList = playList;
        lock.unlock();

        return tempPlayList;
    }

    public static String getMusic(int index) {
        String music = null;

        lock.lock();
        music = playList.get(index);
        lock.unlock();

        return music;
    }

}
