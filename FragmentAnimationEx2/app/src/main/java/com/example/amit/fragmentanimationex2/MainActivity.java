package com.example.amit.fragmentanimationex2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
int state=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentById(R.id.frag)).commit();
        findViewById(R.id.anim_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state==0) {
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
                            .show(getSupportFragmentManager().findFragmentById(R.id.frag)).commit();
                    state = 1;
                }
                else{
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
                            .hide(getSupportFragmentManager().findFragmentById(R.id.frag)).commit();
                    state = 0;
                }
            }
        });

    }
}
