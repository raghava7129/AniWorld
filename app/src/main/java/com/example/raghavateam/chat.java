package com.example.raghavateam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class chat extends AppCompatActivity {

    private static final int READ_CONTACT=100;

    ListView v;
    EditText newName,newNo;
    Button Add,Remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initializeViews();

        ActionBar action= getSupportActionBar();
        action.setTitle("Call Logs !!");


        dataBase db =new dataBase(chat.this);

//        chat_model c1=new chat_model("Raghava","7995463268");
//        chat_model c2=new chat_model("Deep","7436005772");
//        chat_model c3=new chat_model("Ravi","8409559019");
//        chat_model c4=new chat_model("Dinesh","9550754993");
//        chat_model c5=new chat_model("Home","6301558043");


        //db.addContact(c1);db.addContact(c2);db.addContact(c3);db.addContact(c4);db.addContact(c5);



        Adapt obj = new Adapt(chat.this,db.getContact());
        ArrayAdapter<chat_model> adapter=new ArrayAdapter<chat_model>(chat.this,
                R.layout.chat_child,db.getContact());
        v.setAdapter(obj);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String na,No;
                na=newName.getText().toString();
                No=newNo.getText().toString();



                chat_model c=new chat_model(na,No);
                db.addContact(c);
                obj.updateContacts(db.getContact());
                //obj.addItem(c);


                clearInputs();
            }
        });

        Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String na=newName.getText().toString();
                String No=newNo.getText().toString();
                chat_model c=new chat_model(na,No);
//                obj.removeItem(c);

                dataBase db =new dataBase(chat.this);
                db.deleteContact(na);
                obj.updateContacts(db.getContact());
                newName.setText("");
                newNo.setText("");

            }
        });

        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chat_model sel = adapter.getItem(position);
                //perform Call open action
                Toast.makeText( chat.this, sel.getNumber(), Toast.LENGTH_SHORT).show();
                make_call(sel);
            }
        });
//        ArrayList<String> cou=new ArrayList<>();
//        cou.add("India");
//        cou.add("USA");
//        cou.add("Japan");
//        cou.add("Russia");
//
//        ArrayAdapter<String> adapter=new ArrayAdapter<>(chat.this,
//                android.R.layout.simple_list_item_1,cou);
//
//        v.setAdapter(adapter);




    }
    private void clearInputs(){
        newName.setText("");
        newNo.setText("");
    }
    private void initializeViews(){
        v=findViewById(R.id.vi);
        Add=findViewById(R.id.add);
        Remove=findViewById(R.id.del);
        newName=findViewById(R.id.nu);
        newNo=findViewById(R.id.no);
    }
    private void make_call(chat_model c){
        String number=c.getNumber();
        if(number.trim().length()>0){
            if(ContextCompat.checkSelfPermission(chat.this, Manifest.permission.CALL_PHONE )!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(chat.this,new String[] {Manifest.permission.CALL_PHONE},READ_CONTACT);
            }
            else{
                String dial="tel:"+ number ;
                Intent i=new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse(dial));
                startActivity(i);
            } 
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults,chat_model c) {
        if(READ_CONTACT == requestCode){
            if( grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                make_call(c);
            }
            else{
                Toast.makeText(chat.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}