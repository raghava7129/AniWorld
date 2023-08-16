package com.example.raghavateam;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class groupFragment extends Fragment implements select_char{
    ImageButton sendB;
    RecyclerView rgrp;
    EditText et;
    FirebaseAuth auth;
    FirebaseUser fuser;
    CircleImageView pil;
    ArrayList<model_chat_> ar;
    msg_adapter msgAdapter;
    ImageButton scroll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_group, container, false);
        auth=FirebaseAuth.getInstance();
        fuser= auth.getCurrentUser();
        String sender=fuser.getUid();
        readmsg_G(sender);
        String receiver="all";

        sendB=v.findViewById(R.id.send_grp);
        rgrp=v.findViewById(R.id.rvgrp);
        et=v.findViewById(R.id.edit_bar);
        pil=v.findViewById(R.id.PIL);
        rgrp.setLayoutManager(new LinearLayoutManager(getContext()));
        scroll=v.findViewById(R.id.scrollDown);

        scroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ar != null && !ar.isEmpty()) {
                    rgrp.smoothScrollToPosition(ar.size() - 1);
                }
            }
        });

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("User_Data").child(sender);
        ref.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                model_friend user=snapshot.getValue(model_friend.class);
                assert user != null;
                readmsg_G(sender);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=et.getText().toString().trim();
                et.setText("");
                if(!message.equals("")){
                    sendMsg_G(sender,receiver,message);
                    readmsg_G(sender);
                }
            }
        });
        return v;
    }

    private void sendMsg_G(String sender,String receiver,String msg){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("msg",msg);
        reference.child("chats").push().setValue(hashMap);
    }


    public void readmsg_G(String myid){
        ar = new ArrayList<>();
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("chats");
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User_Data");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ar.clear();
                for (DataSnapshot chatSnapshot : snapshot.getChildren()) {
                    model_chat_ mc = chatSnapshot.getValue(model_chat_.class);
                    assert mc != null;
                    if (mc.getReceiver().equals("all")) {
                        // Fetch the sender's details
                        userRef.child(mc.getSender()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot senderSnapshot) {
                                model_friend senderUser = senderSnapshot.getValue(model_friend.class);
                                if (senderUser != null) {
                                    mc.setSenderurl(senderUser.getImgURL());  // Set the sender's image URL to your model_chat_ object
                                    ar.add(mc);
                                    msgAdapter = new msg_adapter(getContext(), ar, senderUser.getImgURL()); // Now, you have the sender's image URL in your model object, use it to populate your adapter
                                    rgrp.setAdapter(msgAdapter);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Handle the error
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }




    @Override
    public void onCardSelected(character_model cm) {

    }

    @Override
    public void onCardSelected(model_friend mf) {

    }
}