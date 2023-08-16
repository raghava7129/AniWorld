package com.example.raghavateam;

public class character_model {

    private int img;
    private String name;

    character_model(String na,int i){
        this.img=i;
        this.name=na;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
