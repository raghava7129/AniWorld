package com.example.raghavateam;

public class model_chat_ {
    private String msg;
    private String receiver;
    private String sender;
    private String senderurl;




    public model_chat_(String msg,String receiver,String sender,String senderurl){
        this.sender=sender;
        this.msg=msg;
        this.receiver=receiver;
        this.senderurl=senderurl;
    }
    public model_chat_(){}


    public String getSenderurl() {
        return senderurl;
    }

    public void setSenderurl(String senderurl) {
        this.senderurl = senderurl;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
