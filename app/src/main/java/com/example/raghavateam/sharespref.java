package com.example.raghavateam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class sharespref {

    public static String shared = "RaghavaTeam";


    public static final String userName ="username";
    public static final String eMail ="eMail";
    public static final String phoneNo ="phoneno";
    public static final String Gender ="gender";
    public static final String Country ="country";
    public static final String PassWord ="password";
    public static final String is_loggedIn="isuserloggedin";
    public static final String Img="img";



    public static void saveString(Context context,String key,String value){
        SharedPreferences sp = context.getSharedPreferences(shared,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,value);

        editor.apply();
    }

    public static void saveimg(Context context, String key, String urlInString){
        SharedPreferences sp = context.getSharedPreferences(shared,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,urlInString);

        editor.apply();
    }

    public static String getString(Context context,String key){

        SharedPreferences sp = context.getSharedPreferences(shared,Context.MODE_PRIVATE);

        return sp.getString(key,"");

    }

    public static String getImgInString(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences(shared,Context.MODE_PRIVATE);
        return sp.getString(key,"");

    }

    public static boolean is_loggedIn(Context context){
        SharedPreferences sp= context.getSharedPreferences(shared,Context.MODE_PRIVATE);
            return sp.getBoolean(is_loggedIn,false);
    }

    public static void set_is_loggedIn(Context context,boolean val){
        SharedPreferences sp= context.getSharedPreferences(shared,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        editor.putBoolean(is_loggedIn,val);
        editor.apply();

    }

    public static void saveSignUp(Context context, String use, String em, String phone, String gen, String cou, String p){

        saveString(context,userName,use);
        saveString(context,eMail,em);
        saveString(context,phoneNo,phone);
        saveString(context,Gender,gen);
        saveString(context,Country,cou);
        saveString(context,PassWord,p);
        SharedPreferences sp =context.getSharedPreferences(shared,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(is_loggedIn,true);
        editor.apply();

    }

    public static void getLoginData(Context context, TextView mnd, TextView emd, TextView fuNd) {
        mnd.setText(getString(context,phoneNo));
        emd.setText(getString(context,eMail));
        fuNd.setText(getString(context,userName));

    }

    public static void logOut(Context context){
        SharedPreferences sp =context.getSharedPreferences(shared,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
//        editor.clear();
        editor.putBoolean(is_loggedIn,false);
        editor.apply();

    }
}
