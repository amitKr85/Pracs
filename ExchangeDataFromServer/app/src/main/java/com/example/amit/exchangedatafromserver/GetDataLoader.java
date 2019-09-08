package com.example.amit.exchangedatafromserver;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class GetDataLoader extends AsyncTaskLoader<String> {
    private URL url;
    public static final String LOG_TAG="GetDataLoader";
    public GetDataLoader(@NonNull Context context,URL url) {
        super(context);
        this.url=url;
        Log.i(LOG_TAG,"inside getDataLoader constructor");
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.i(LOG_TAG,"inside onStartLoading");
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        Log.i(LOG_TAG,"inside loadInBackground");
        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;
        String rawData=null;
        try {
            urlConnection= (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            inputStream=urlConnection.getInputStream();
            rawData=getDataFromStream(inputStream);
        } catch (IOException e) {
            Log.e(LOG_TAG,"exception in connection",e);
        }
        String data="";
        try {
            JSONObject jsonObject=new JSONObject(rawData);
            data+="\n======Received Data From Server======\n";
            data+="name="+jsonObject.getString("name")+"\n";
            data+="age="+jsonObject.getInt("age")+"\n";
            data+="id="+jsonObject.getString("id")+"\n";
        } catch (JSONException e) {
            Log.e(LOG_TAG,"exception in parsing json",e);
        }

        return data;
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
