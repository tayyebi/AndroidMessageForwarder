package com.gordarg.messageforwarder.model;

public class AutoReply {
    private int id;
    private String condition;
    private String reply;
    private boolean isEnabled;

    public void setId(int value) {
        this.id = value;
    }

    public int getId() {
        return this.id;
    }

    public void setCondition(String value) {
        this.condition = value;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setReply(String value) {
        this.reply = value;
    }

    public String getReply() {
        return this.reply;
    }

    public void setIsEnabled(boolean value) {
        this.isEnabled = value;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }
}
