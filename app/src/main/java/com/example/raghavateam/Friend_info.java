package com.example.raghavateam;

 class Friend_info {
     public String Phone_no;
     public String fav_char;
     public String username;
     public String id;
     public String imgURL;


     public Friend_info(){
    }

     public Friend_info(String ph, String fav, String n,String ID,String imgURL){
         this.fav_char = fav;
         this.Phone_no = ph;
         this.username = n;
         this.id=ID;
         this.imgURL=imgURL;
     }

     public String getImgURL() {
         return imgURL;
     }

     public String getId() {
         return id;
     }

     public void setId(String id) {
         this.id = id;
     }

     public String getName() {
        return username;
    }

    public String getNumb() {
        return Phone_no;
    }

    public String getFav_char() {
        return fav_char;
    }
}
