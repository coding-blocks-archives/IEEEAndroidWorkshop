package com.codingblocks.ieeeworkshopapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class InternetActivity extends Activity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);

        textView = (TextView) findViewById(R.id.textView);

        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String data = "";
                try {
                    data = getDataFromUrl("http://open-event-dev.herokuapp.com/api/v2/events/192/speakers");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return data;
            }

            @Override
            protected void onPostExecute(String data) {

                textView.setText(data);

                super.onPostExecute(data);
            }
        };

        task.execute();






    }


    String getDataFromUrl (String url) throws IOException {
        URL myUrl = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();

        connection.setConnectTimeout(10000);
        connection.setReadTimeout(15000);

        connection.connect();


        InputStream is = connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        StringBuilder sb = new StringBuilder();
        String buffer = "";
        while (buffer != null) {
            buffer = br.readLine();
            sb.append(buffer);
        }

        return sb.toString();

    }
}
