package com.example.raghavateam;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.acl.Owner;

public class Home_page extends AppCompatActivity {

    Button b1,b2,b3;
    ImageButton ib;
    ActionBar ab;
    FirebaseAuth auth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        b1=findViewById(R.id.button1);
        b2=findViewById(R.id.button2);
        b3=findViewById(R.id.button3);
        ib=findViewById(R.id.chat_G);

        ab=getSupportActionBar();
        assert ab!=null;
        ab.setTitle("Home Page");


        auth=FirebaseAuth.getInstance();
        FirebaseUser User= auth.getCurrentUser();
        String ph= sharespref.getString(Home_page.this,sharespref.phoneNo);
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("User_Data").child(User.getUid());
        dbref.child("Phone_no").setValue(ph);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://all-worlds-alliance.fandom.com/wiki/Kiyotaka_Ayanokouji"));
                startActivity(i);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent();
                i2.setAction(Intent.ACTION_VIEW);
                i2.setData(Uri.parse("https://www.youtube.com/watch?v=j6SRlNsrvCI&ab_channel=MuseIndia"));
                startActivity(i2);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3=new Intent();
                i3.setAction(Intent.ACTION_VIEW);
                i3.setData(Uri.parse("https://m.imdb.com/title/tt7263328/"));
                startActivity(i3);
            }
        });
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home_page.this,chat_nav.class);
                startActivity(i);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_1,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home_page.this);
        builder.setTitle("Alert !!");
        builder.setCancelable(false);
        builder.setMessage("Do you want to exit ? ");
        builder.setPositiveButton("Yes",(DialogInterface.OnClickListener) (dialog,which)->{
            finishAffinity (); // To close all Activities !!
        });
        builder.setNegativeButton("No",(DialogInterface.OnClickListener) (dialog,which)->{
            dialog.cancel();
        });
        AlertDialog alertdialog = builder.create();

        alertdialog.show();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.head_profile:
                Intent intent3 =new Intent(Home_page.this, owner1.class);
                startActivity(intent3);
                break;
            case R.id.myProfile:
                Intent intent2 =new Intent(Home_page.this, profile.class);
                startActivity(intent2);
                break;
            case R.id.Char:
                Intent intent10 =new Intent(Home_page.this, character_page.class);
                startActivity(intent10);
                break;

            case R.id.Friend_list:
                Intent i11=new Intent(Home_page.this,chat_nav.class);
                startActivity(i11);
                break;

            case R.id.Call:
                Intent intent9 =new Intent(Home_page.this, chat.class);
                startActivity(intent9);
                break;

            case R.id.log_out:
                sharespref.logOut(Home_page.this);
                sharespref.set_is_loggedIn(Home_page.this,false);   
                    Intent intent1 =new Intent(Home_page.this, MainActivity.class);
                    startActivity(intent1);
                    finish();
                break;


        }
        return super.onOptionsItemSelected(item);
    }


}