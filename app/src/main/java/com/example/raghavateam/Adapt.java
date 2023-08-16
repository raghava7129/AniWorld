package com.example.raghavateam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapt extends BaseAdapter {

    private Context context;
    private ArrayList<chat_model> ar;
    LayoutInflater inflater;

    public Adapt(Context context, ArrayList<chat_model> ar) {
        this.context = context;
        this.ar = ar;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return ar.size();
    }

    @Override
    public Object getItem(int position) {
        return ar.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.chat_child,null);

        TextView tx=view.findViewById(R.id.n);

        chat_model cm= ar.get(position);
        tx.setText(cm.getName());



        return view;
    }

    public void updateContacts(ArrayList<chat_model> list){
        this.ar=list;
        notifyDataSetChanged();
    }
}
