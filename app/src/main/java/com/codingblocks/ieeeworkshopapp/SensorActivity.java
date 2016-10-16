package com.codingblocks.ieeeworkshopapp;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

public class SensorActivity extends Activity {

    public static final String TAG = "Sensor";

    RelativeLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        SensorManager sensMan = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor accelSensor = sensMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mainLayout = (RelativeLayout) findViewById(R.id.activity_sensor);


        sensMan.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                Log.d(TAG, "onSensorChanged: x = " + event.values[0]);
                Log.d(TAG, "onSensorChanged: y = " + event.values[1]);
                Log.d(TAG, "onSensorChanged: z = " + event.values[2]);

                mainLayout.setBackgroundColor(accelToColor(
                        event.values[0],
                        event.values[1],
                        event.values[2]
                ));


            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, accelSensor, (10 * 1000));

    }

    int accelToColor(float ax, float ay, float az) {

        int red = (int) (((ax + 11) / 22) * 255);
        int green = (int) (((ay + 11) / 22) * 255);
        int blue = (int) (((az + 11) / 22) * 255);

        return Color.argb(255, red, green, blue);
    }
}
