package com.example.raghavateam;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class owner1 extends AppCompatActivity {
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_owner1);
        ActionBar ab=getSupportActionBar();
        assert ab != null;
        ab.setTitle("Creator`s Profile");

        b=findViewById(R.id.Back_icon);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5=new Intent(owner1.this,Home_page.class);
                startActivity(intent5);
            }
        });
    }
}