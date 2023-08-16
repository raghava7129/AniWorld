package com.example.raghavateam;

public class model_friend {
    private String img;
    private String name;
    private String userid;
    private String imgURL;
    private String senderImgURL;

    model_friend(String na, String i, String uid,String imgURL){
        this.img=i;
        this.name=na;
        this.userid=uid;
        this.imgURL=imgURL;
    }

    model_friend(){}

    public String getImgURL() {
        return imgURL;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
