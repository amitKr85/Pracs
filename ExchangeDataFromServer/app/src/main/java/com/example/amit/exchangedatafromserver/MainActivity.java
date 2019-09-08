package com.example.amit.exchangedatafromserver;

import android.nfc.TagLostException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
        ,LoaderManager.LoaderCallbacks<String> {

    public final static String GET_URL="http://dummix.cf/php/getData.php";
    public final static String SEND_URL="http://dummix.cf/php/sendData.php";
    public final static int GET_DATA=10;
    public final static int SEND_DATA=20;
    public final static String LOG_TAG="Main Activity";
    private URL getUrl=null;
    private URL sendUrl=null;
    private TextView consoleTextView=null;
    private Button getButton=null;
    private Button sendButton=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getButton=findViewById(R.id.get_data_button);
        sendButton=findViewById(R.id.send_data_button);
        getButton.setOnClickListener(this);
        sendButton.setOnClickListener(this);
        consoleTextView=findViewById(R.id.console_text_view);

        try{
            getUrl=new URL(GET_URL);
            sendUrl=new URL(SEND_URL);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG,"exception in creating url",e);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.get_data_button:
                Log.i(LOG_TAG,"get Data btn clicked");
                getSupportLoaderManager().initLoader(GET_DATA,null,this);
                break;
            case R.id.send_data_button:
                Log.i(LOG_TAG,"send data btn clicked");
                consoleTextView.append("\n======Sending Data To Server======\n");
                getSupportLoaderManager().initLoader(SEND_DATA,null, this);
        }
    }

    private void enableButtons(){
        getButton.setEnabled(true);
        sendButton.setEnabled(true);
    }
    private void disableButtons(){
        getButton.setEnabled(false);
        sendButton.setEnabled(false);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        Log.i(LOG_TAG,"inside onCreateLoader id="+id+" thread="+Thread.currentThread());
        disableButtons();
        if(id==GET_DATA)
            return new GetDataLoader(this,getUrl);
        else
            return new SendDataLoader(this,sendUrl);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Log.i(LOG_TAG,"inside onLoadFinished");
        consoleTextView.append(data);
        enableButtons();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        Log.i(LOG_TAG,"inside onLoaderReset");
    }
}
