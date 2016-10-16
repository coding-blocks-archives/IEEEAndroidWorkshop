package com.codingblocks.ieeeworkshopapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends Activity {

    public static final String TAG = "Main";

    EditText etKm, etMin;

    Button btnCalc, btnSecAct, btnSensorAct, btnInternet;

    TextView tvFare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        etKm = (EditText) findViewById(R.id.etKm);
        etMin = (EditText) findViewById(R.id.etMin);
        btnCalc = (Button) findViewById(R.id.btnCalc);
        tvFare = (TextView) findViewById(R.id.tvFare);

        //Retrieve saved data and set it here

        SharedPreferences savedPrefs = getPreferences(MODE_PRIVATE);
        float savedKm = savedPrefs.getFloat("km", 0.0f);
        int savedMin = savedPrefs.getInt("min", 0);

        etKm.setText(String.valueOf(savedKm));
        etMin.setText(String.valueOf(savedMin));

        btnSecAct = (Button) findViewById(R.id.btnSecAct);
        btnSensorAct = (Button) findViewById(R.id.btnSensor);
        btnInternet = (Button) findViewById(R.id.btnInternet);


        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float km = Float.valueOf(etKm.getText().toString());
                int min = Integer.valueOf(etMin.getText().toString());

                float fare = calculateFare(km, min);

                //Saving the data to SharedPreferences

                SharedPreferences sPref = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = sPref.edit();
                prefEditor.putFloat("km", km);
                prefEditor.putInt("min", min);
                prefEditor.putFloat("fare", fare);
                prefEditor.apply();

                String dataToSave = "Km = " + km
                        + "\n"
                        + "Min = " + min
                        + "\n"
                        + "Fare = Rs. " + fare;


                try {
                    saveToFile("myfile.txt", dataToSave);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Could not save file", Toast.LENGTH_SHORT).show();
                }


                tvFare.setText("Rs. " + String.valueOf(fare));




                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Fare")
                        .setMessage("Fare is Rs. " + String.valueOf(fare))
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this,
                                        "Paid",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this,
                                        "Did not pay",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();

                new Student().increaseAge().increaseAge().increaseAge();
            }
        });

        btnSecAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(i);
            }
        });

        btnSensorAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(MainActivity.this, SensorActivity.class);
                startActivity(i2);
            }
        });

        btnInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(MainActivity.this, InternetActivity.class);
                startActivity(i3);
            }
        });
    }



    float calculateFare(float km, int min) {
        float fare = 25;

        if (km > 2) {
            fare += (km * 9);
        }

        if (min > 15) {
            fare += (min - 15);
        }

        return fare;
    }

    void saveToFile(String fileName, String data) throws IOException {
        Log.d(TAG, "saveToFile: " + Environment.getExternalStorageDirectory().getAbsolutePath());

        File sdcardDir = Environment.getExternalStorageDirectory();
        File myFile = new File(sdcardDir, fileName);

        if (sdcardDir.isDirectory()) {
            if (myFile.exists()) {
                myFile.delete();
            }
            myFile.createNewFile();
        }

        FileOutputStream fileOutputStream = new FileOutputStream(myFile);
        fileOutputStream.write(data.getBytes());
        fileOutputStream.close();
    }

    String readFromFile(String fileName) throws IOException {
        File sdcardDir = Environment.getExternalStorageDirectory();
        File myFile = new File(sdcardDir, fileName);

        FileInputStream fiStream = new FileInputStream(myFile);
        byte[] buffer = new byte[8];
        while(buffer != null) {
            fiStream.read(buffer);
        }
        return null;
    }



    public class Student {
        String name;
        int year;
        int age = 10;

        Student increaseAge() {
            age++;
            return this;
        }
        Student decreaseAge() {
            age--;
            return this;
        }
    }

    public interface OnAttendanceListener {
        void onAttendance(boolean present);
    }


    public class CollegeStudent implements OnAttendanceListener {

        @Override
        public void onAttendance(boolean present) {

        }
    }










}
