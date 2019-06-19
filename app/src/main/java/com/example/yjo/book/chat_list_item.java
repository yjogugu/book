package com.example.yjo.book;

public class chat_list_item {
    String chat_list_id;
    String chat_list_post;
    String chat_list_image;


    public chat_list_item(String chat_list_id, String chat_list_post , String chat_list_image) {
        this.chat_list_id = chat_list_id;
        this.chat_list_post = chat_list_post;
        this.chat_list_image = chat_list_image;
    }

    public String getChat_list_id() {
        return chat_list_id;
    }

    public String getChat_list_post() {
        return chat_list_post;
    }

    public String getChat_list_image() {
        return chat_list_image;
    }
}
