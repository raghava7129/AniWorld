package com.example.raghavateam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Button in,up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        in=findViewById(R.id.in);
        up=findViewById(R.id.up);


        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Opening sign in page",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(MainActivity.this,sign_in.class);
                startActivity(intent);
            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Opening sign up page",Toast.LENGTH_SHORT).show();
                Intent intent1= new Intent(MainActivity.this,sign_up.class);
                startActivity(intent1);
            }
        });

    }
}