package com.example.raghavateam;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class userFragment extends Fragment implements select_char{
    RecyclerView rv5;
    FirebaseAuth auth;
    FirebaseUser user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);

        rv5=view.findViewById(R.id.list5);
        auth=FirebaseAuth.getInstance();

        ArrayList<model_friend> model_list5 = new ArrayList<>();

        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv5.setLayoutManager(lm);

//        GridLayoutManager glm =new GridLayoutManager(ChatListView.this,1,LinearLayoutManager.VERTICAL,false);
//        rv.setLayoutManager(glm);

        Friend_list_adapter obj5 = new Friend_list_adapter(getContext(), model_list5, this);
        rv5.setAdapter(obj5);

        user= auth.getCurrentUser();
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("User_Data");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                model_list5.clear();

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    Friend_info info = snapshot1.getValue(Friend_info.class);
                    assert info != null;

                    FirebaseStorage storage=FirebaseStorage.getInstance();
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child(info.getId());

                    String n = info.getName();

                    model_list5.add(new model_friend(n,info.getImgURL(),info.getId(),info.getImgURL()));
                }
                obj5.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    @Override
    public void onCardSelected(character_model cm) {

    }

    @Override
    public void onCardSelected(model_friend mf) {


    }
}