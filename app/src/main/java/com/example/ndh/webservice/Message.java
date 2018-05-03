package com.example.ndh.webservice;

/**
 * Created by NDH on 4/23/2018.
 */

public class Message {

    private  String content;
    private String idSend;

    public Message() {
    }

    public Message(String content, String idSend) {
        this.content = content;
        this.idSend = idSend;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIdSend() {
        return idSend;
    }

    public void setIdSend(String idSend) {
        this.idSend = idSend;
    }
}
