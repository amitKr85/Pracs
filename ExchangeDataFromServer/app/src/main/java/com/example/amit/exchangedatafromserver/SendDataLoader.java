package com.example.amit.exchangedatafromserver;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.util.Pair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class SendDataLoader extends AsyncTaskLoader<String> {
    private URL url;
    public static final String LOG_TAG = "SendDataLoader";

    public SendDataLoader(@NonNull Context context, URL url) {
        super(context);
        this.url = url;
        Log.i(LOG_TAG, "inside constructor");
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.i(LOG_TAG, "inside onStartLoading");
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        Log.i(LOG_TAG, "inside loadInBackground");

        ArrayList<Pair> list = new ArrayList<>();
        list.add(new Pair<>("name", "manish"));
        list.add(new Pair<>("age", 20));
        list.add(new Pair<>("id", "2016UIT4014"));

        HttpURLConnection urlConnection = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        String feedBack="";
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);

            outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, Charset.forName("UTF-8")));
            writer.write(getStringData(list));
            writer.flush();
            writer.close();
            outputStream.close();

            urlConnection.connect();
            inputStream=urlConnection.getInputStream();
            feedBack=getDataFromStream(inputStream);
            inputStream.close();

        } catch (IOException e) {
            Log.e(LOG_TAG, "exception in creating connection", e);
        }
        return feedBack;
    }

    private String getStringData(ArrayList<Pair> list) {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Pair item : list) {
            if(first)
                first=false;
            else
                builder.append("&");
            try {
                builder.append(URLEncoder.encode(item.first.toString(), "UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(item.second.toString(),"UTF-8"));
            }catch(Exception e){
                Log.e(LOG_TAG,"exception in encoding data",e);
            }
        }
        return builder.toString();
    }
    private String getDataFromStream(InputStream inputStream){
        StringBuilder builder=new StringBuilder();
        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        String line="";
        try {
            line=reader.readLine();
            while(line!=null){
                builder.append(line);
                line=reader.readLine();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG,"exception in getting data from stream",e);
        }
        return builder.toString();
    }
}