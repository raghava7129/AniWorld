package com.example.raghavateam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class dataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contactsDB";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";

    public dataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                +   KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    public void addContact(chat_model cm){
        SQLiteDatabase sqldb = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME,cm.getName());
        contentValues.put(KEY_PH_NO,cm.getNumber());

        sqldb.insert(TABLE_CONTACTS,null,contentValues);

        sqldb.close();
    }

    public void deleteContact(String name){
        SQLiteDatabase sqldb = getWritableDatabase();
        sqldb.delete(TABLE_CONTACTS,KEY_NAME + "=?",new String[]{name});
        sqldb.close();
    }

    public ArrayList<chat_model> getContact(){
        ArrayList<chat_model> contactsArrayList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase sqldb =getReadableDatabase();

        Cursor cursor = sqldb.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do {

                chat_model cm =new chat_model(cursor.getString(0), cursor.getString(1));

                contactsArrayList.add(cm);


            }while(cursor.moveToNext());
        }

        sqldb.close();

        return contactsArrayList;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
