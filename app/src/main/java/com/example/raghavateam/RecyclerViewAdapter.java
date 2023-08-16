package com.example.raghavateam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.myViewHolder>{

    private ArrayList<character_model> ar;
    private Context context;
    private select_char sc;

    public RecyclerViewAdapter(Context context,ArrayList<character_model> a,select_char sc){
        this.ar=a;
        this.context=context;
        this.sc=sc;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.activity_character_child,parent,false);
        myViewHolder vh = new myViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        character_model c=ar.get(position);

        holder.name.setText(c.getName());
        holder.iv.setImageResource(c.getImg());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sc.onCardSelected(c);

            }
        });
    }


    @Override
    public int getItemCount() {
        return ar.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        ImageView iv;
        TextView name;
        androidx.cardview.widget.CardView cv;

        public myViewHolder(View v) {
            super(v);

            cv = itemView.findViewById(R.id.cardv);
            iv = itemView.findViewById(R.id.char_img);
            name = itemView.findViewById(R.id.char_name);
        }
    }
}
