package com.example.raghavateam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatListView extends AppCompatActivity implements select_char {
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list_view);

        initialize();

        ArrayList<model_friend> model_list = new ArrayList<>();

        LinearLayoutManager lm = new LinearLayoutManager(ChatListView.this);
        rv.setLayoutManager(lm);

//        GridLayoutManager glm =new GridLayoutManager(ChatListView.this,1,LinearLayoutManager.VERTICAL,false);
//        rv.setLayoutManager(glm);

        Friend_list_adapter obj = new Friend_list_adapter(ChatListView.this, model_list, this);
        rv.setAdapter(obj);

        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Users");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                model_list.clear();

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    Friend_info info = snapshot1.getValue(Friend_info.class);
                    String n = info.getName();

//                    model_list.add(new model_friend(n, R.drawable.pr));
                }
                obj.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void initialize() {

        rv = findViewById(R.id.list);
    }

    @Override
    public void onCardSelected(character_model cm) {

    }

    @Override
    public void onCardSelected(model_friend mf) {

    }
}