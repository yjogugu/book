package com.example.yjo.book;

public class chat_item {
    String text;
    String id;

    public chat_item(String text,String id) {
        this.text = text;
        this.id = id;

    }

    public String getText() {
        return text;
    }


    public String getId() {
        return id;
    }
}
