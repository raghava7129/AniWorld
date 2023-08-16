package com.example.raghavateam;

import android.app.FragmentTransaction;
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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Friend_list_adapter extends RecyclerView.Adapter<Friend_list_adapter.myVIEWHOLDER> {

    private ArrayList<model_friend> ar;
    private Context context;
    private select_char sc;

    public Friend_list_adapter(Context context,ArrayList<model_friend> ar,select_char sc) {
        this.context = context;
        this.ar = ar;
        this.sc = sc;
    }

    @NonNull
     @Override

    public myVIEWHOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v= LayoutInflater.from(context).inflate(R.layout.activity_list_model_friend,parent,false);
        myVIEWHOLDER vh=new myVIEWHOLDER(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull myVIEWHOLDER holder, int position) {
        model_friend c=ar.get(position);

        holder.name.setText(c.getName());
//        holder.iv.setImageResource(c.getImg());
        Glide.with(context).load(c.getImg()).into(holder.iv);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sc.onCardSelected(c);

                    Intent i=new Intent(context,msg.class);

                    i.putExtra("user_n",c.getName());

                    i.putExtra("user_img",c.getImgURL());

                    i.putExtra("userid",c.getUserid());

                    context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return ar.size();
    }

    public class myVIEWHOLDER extends RecyclerView.ViewHolder{
        CircleImageView iv;
        TextView name;
        androidx.cardview.widget.CardView cv;

        public myVIEWHOLDER(View v) {
            super(v);

            cv = itemView.findViewById(R.id.card);
            iv = itemView.findViewById(R.id.Profile);
            name = itemView.findViewById(R.id.Text1);
        }

    }

}
