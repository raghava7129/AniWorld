package com.example.raghavateam;

import android.graphics.Bitmap;

public class Image_db {

    private String userName;
    private Bitmap imgBit;

    public Image_db(String u,Bitmap i){
        this.userName=u;
        this.imgBit=i;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Bitmap getImgBit() {
        return imgBit;
    }

    public void setImgBit(Bitmap imgBit) {
        this.imgBit = imgBit;
    }

}
