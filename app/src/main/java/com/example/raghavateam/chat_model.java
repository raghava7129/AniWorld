package com.example.raghavateam;

public class chat_model {
    public chat_model(String naam,String no) {

        name=naam;
        number=no;

    }

    private String name;
    private String number;

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }


}
