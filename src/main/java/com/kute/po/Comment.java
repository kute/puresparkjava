package com.kute.po;

import java.io.Serializable;

public class Comment implements Serializable {

    private String message;

    public Comment() {
    }

    public Comment(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
