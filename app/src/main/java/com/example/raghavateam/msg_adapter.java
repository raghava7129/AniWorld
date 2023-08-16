package com.example.raghavateam;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class msg_adapter extends RecyclerView.Adapter<msg_adapter.myVIEWHOLDER>{
    public static final int msg_type_left=0;
    public static final int msg_type_right=1;

    private ArrayList<model_chat_> ar;
    private Context context;
    private String imgurl;


    public msg_adapter(Context context,ArrayList<model_chat_> ar,String imgurl){
        this.context = context;
        this.ar = ar;
        this.imgurl=imgurl;
    }

    @NonNull
    @Override

    public myVIEWHOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v;
        if(viewType==msg_type_right){
            v= LayoutInflater.from(context).inflate(R.layout.chat_item_right,parent,false);
            return new myVIEWHOLDER(v);
        }
        else{
            v= LayoutInflater.from(context).inflate(R.layout.chat_item_left,parent,false);
            return new myVIEWHOLDER(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull myVIEWHOLDER holder, int position) {
        model_chat_ mc ;
        mc=ar.get(position);
        holder.show_msg.setText(mc.getMsg());

        if(imgurl.equals("default")){
            holder.iv.setImageResource(R.drawable.ic_launcher_background);
        }
        else if(mc.getReceiver().equals("all")){
            Glide.with(context).load(mc.getSenderurl()).into(holder.iv);
        }
        else{
            Glide.with(context).load(imgurl).into(holder.iv);
        }
    }

    @Override
    public int getItemCount() {
        return ar.size();
    }

    public class myVIEWHOLDER extends RecyclerView.ViewHolder{

        CircleImageView iv;
        TextView show_msg;

        public myVIEWHOLDER(View v) {
            super(v);

            iv = itemView.findViewById(R.id.PIL);
            show_msg = itemView.findViewById(R.id.cl);
        }

    }

    public int getItemViewType(int position) {
        return getImageViewType(position);
    }

    public int getImageViewType(int position){

        FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();

        assert User != null;
        if(ar.get(position).getSender().equals(User.getUid())){
            return msg_type_right;
        }
        else return msg_type_left;
    }

}
