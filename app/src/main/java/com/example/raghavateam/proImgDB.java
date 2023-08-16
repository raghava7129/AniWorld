package com.example.raghavateam;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class proImgDB extends SQLiteOpenHelper {

    Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME="img_db";
    private static final String TABLE_NAME="profile_images";
    private static final String KEY_USERNAME="USER";
    private byte[] imageInByte;
    private ByteArrayOutputStream byteArrayOutputStream;


    public proImgDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_IMG_DB = "CREATE TABLE " + TABLE_NAME + "(" + KEY_USERNAME +"TEXT," + "image BLOB)";
        db.execSQL(CREATE_IMG_DB);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void save_img(Image_db obj){
        SQLiteDatabase sqldb = this.getWritableDatabase();
        Bitmap imageToStoreBitmap =obj.getImgBit();
        byteArrayOutputStream=new ByteArrayOutputStream();

        imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        imageInByte=byteArrayOutputStream.toByteArray();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME,obj.getUserName());
        values.put("image",imageInByte);
        sqldb.insert(TABLE_NAME,null,values);
        sqldb.close();

    }
}
