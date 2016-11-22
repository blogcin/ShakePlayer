package xyz.alteration.shakeplayer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by blogcin on 2016-11-22.
 */

public class ShakeDetector implements SensorEventListener {
    private Context context = null;
    private Sensor accelerometer = null;
    private long lastTime;
    private float lastX;
    private float lastY;
    private float lastZ;
    private Lock lock = new ReentrantLock();

    private static final int SHAKE_THRESHOLD = 800;

    private final String TAG = getClass().getSimpleName();

    private boolean shaked = false;

    public ShakeDetector(Context context) {
        this.context = context;

        SensorManager sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    public boolean getShaked() {
        lock.lock();
        boolean s = shaked;
        lock.unlock();

        return shaked;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();

            long gabOfTime = (currentTime - lastTime);
            float x, y, z;
            float speed;

            if (gabOfTime > 100) {
                lastTime = currentTime;
                x = sensorEvent.values[0];
                y = sensorEvent.values[1];
                z = sensorEvent.values[2];

                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    shaked = true;
                } else {
                    shaked = false;
                }

                lastX = sensorEvent.values[0];
                lastY = sensorEvent.values[1];
                lastZ = sensorEvent.values[2];
            }
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
