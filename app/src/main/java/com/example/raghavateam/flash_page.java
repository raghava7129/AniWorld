package com.example.raghavateam;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class flash_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_page);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Timer timer =new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {


                Intent i1;
                Intent i2;

                if(sharespref.is_loggedIn(flash_page.this)){
                    i1=new Intent(flash_page.this,Home_page.class);
                    startActivity(i1);
                    finish();
                }
                else{ 
                    i2=new Intent(flash_page.this,MainActivity.class);
                    startActivity(i2);
                    finish();
                }
            }
        },5000);
    }
}