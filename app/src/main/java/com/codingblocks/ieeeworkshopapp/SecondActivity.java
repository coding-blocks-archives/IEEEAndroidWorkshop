package com.codingblocks.ieeeworkshopapp;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SecondActivity extends Activity {

    TextView tvLat, tvLong, tvDist, tvTime;
    Location loc;
    float dist = 0.0f;
    long time = 0, lastTime = 0;

    public static final String TAG = "Location";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tvLat = (TextView) findViewById(R.id.tvLat);
        tvLong = (TextView) findViewById(R.id.tvLong);
        tvDist = (TextView) findViewById(R.id.tvDistance);
        tvTime = (TextView) findViewById(R.id.tvTime);

        LocationManager locMan = (LocationManager) getSystemService(LOCATION_SERVICE);

        locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000, 10, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                        tvLat.setText(String.valueOf(location.getLatitude()));
                        tvLong.setText(String.valueOf(location.getLongitude()));

                        Log.d(TAG, "Longitude = " + String.valueOf(location.getLongitude()));
                        Log.d(TAG, "Latitude = " + String.valueOf(location.getLatitude()));

                        if (loc == null) {
                            loc = location;
                        } else {
                            dist = dist + location.distanceTo(loc);
                            loc = location;
                        }

                        if (lastTime == 0) {
                            lastTime = System.currentTimeMillis();
                        } else {
                            long currTime = System.currentTimeMillis();
                            time = time + (currTime - lastTime);
                            lastTime = currTime;
                        }

                        tvDist.setText(String.valueOf(dist));
                        tvTime.setText(String.valueOf(time));




                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });

    }
}
