package com.example.raghavateam;

import static com.example.raghavateam.sharespref.shared;
import static com.example.raghavateam.sharespref.userName;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class msg extends AppCompatActivity {
    TextView tv5;
    CircleImageView civ;
    Button bb;
    RecyclerView rv;
    RecyclerView rg;
    TextView tv;
    ImageButton sb;
    CircleImageView pil;

    msg_adapter msgAdapter;
    ArrayList<model_chat_> ar;
    FirebaseUser userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        initialize();

        userid=FirebaseAuth.getInstance().getCurrentUser();

        Intent i5 = getIntent();
        String s;
        s=i5.getStringExtra("user_n");
        tv5.setText(s);
        int img=i5.getIntExtra("user_img",0);
//        civ.setImageResource(img);
        Glide.with(getApplicationContext()).load(img).into(civ);

        final String friendUserID=i5.getStringExtra("userid");
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i=new Intent(msg.this,chat_nav.class);
//                startActivity(i);
                msg.super.onBackPressed();
            }
        });
        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m=tv.getText().toString();
                if(!m.equals("")){
                    sendMsg_u(userid.getUid(),friendUserID,m);
                    tv.setText("");
                }
            }
        });

    if(!friendUserID.equals("all")) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User_Data").child(friendUserID);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                model_friend user = snapshot.getValue(model_friend.class);

                assert user != null;
                readmsg_U(userid.getUid(), friendUserID, user.getImgURL());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error){

            }
        });


        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setStackFromEnd(true);
        rv.setLayoutManager(llm);
    }

    }

    private void sendMsg_u(String sender,String receiver,String msg){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("msg",msg);
        reference.child("chats").push().setValue(hashMap);
    }

    private void readmsg_U(String myid, String userid, String imgurl){
        ar=new ArrayList<>();
        DatabaseReference dbref =FirebaseDatabase.getInstance().getReference("chats");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ar.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    model_chat_ mc=snapshot1.getValue(model_chat_.class);
                    assert mc != null;
                    if((mc.getReceiver().equals(myid) && mc.getSender().equals(userid)) ||
                            (mc.getReceiver().equals(userid) && mc.getSender().equals(myid) )){
                        ar.add(mc);
                    }


                }
                msgAdapter=new msg_adapter(msg.this,ar,imgurl);
                rv.setAdapter(msgAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void initialize(){
        tv5=findViewById(R.id.usern);
        civ=findViewById(R.id.user_p);
        bb=findViewById(R.id.Back_icon_user);
        rv=findViewById(R.id.rvu);
        tv=findViewById(R.id.edit_bar_u);
        sb=findViewById(R.id.send_u);
        pil=findViewById(R.id.PIL);
        rg=findViewById(R.id.rvgrp);
    }


}