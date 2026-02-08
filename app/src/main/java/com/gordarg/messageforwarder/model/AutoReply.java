package com.gordarg.messageforwarder.model;

public class AutoReply {
    private int Id;
    private String Condition;
    private String Reply;
    private boolean IsEnabled;

    public void setId(int value) {
        this.Id = value;
    }

    public int getId() {
        return this.Id;
    }

    public void setCondition(String value) {
        this.Condition = value;
    }

    public String getCondition() {
        return this.Condition;
    }

    public void setReply(String value) {
        this.Reply = value;
    }

    public String getReply() {
        return this.Reply;
    }

    public void setIsEnabled(boolean value) {
        this.IsEnabled = value;
    }

    public boolean getIsEnabled() {
        return this.IsEnabled;
    }
}
