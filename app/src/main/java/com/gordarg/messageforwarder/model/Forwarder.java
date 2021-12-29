package com.gordarg.messageforwarder.model;

public class Forwarder {
    private int Id;
    private String From;
    private String To;


    public void setFrom(String value){
        this.From = value;
    }
    public String getFrom() {
        return this.From;
    }

    public void setTo(String value){
        this.To = value;
    }
    public String getTo(){
        return this.To;
    }

    public void setId(int value){
        this.Id = value;
    }
    public int getId(){
        return this.Id;
    }
}
