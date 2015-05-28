package cdut.heruoxin.oscilloscope;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by heruoxin on 15/5/28.
 */
public class AccelerometerSensor extends BaseSensor {

    private final SensorManager sensorManager;
    private final Sensor magneticSensor;
    private final SensorEventListener sensorEventListener;
    private final Handler mHandler;

    private volatile double mValue = 0;

    public AccelerometerSensor(Context context, long waitTime, final Callback callback) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (isPaused) return;
                callback.onValueChanged(mValue);
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendMessage(new Message());
            }
        }, 1, waitTime);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (isPaused) return;
                mValue = Math.sqrt(Math.pow(event.values[0], 2)
                        +Math.pow(event.values[1], 2)
                        +Math.pow(event.values[2], 2));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    @Override
    public void resume() {
        super.resume();
        sensorManager.registerListener(
                sensorEventListener,
                magneticSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void pause() {
        super.pause();
        sensorManager.unregisterListener(sensorEventListener);
    }

}
