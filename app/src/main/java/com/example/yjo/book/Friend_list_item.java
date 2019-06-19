package com.example.yjo.book;

public class Friend_list_item {

    String friend_name;
    String friend_id;
    String friend_image;

    public Friend_list_item(String friend_name, String friend_id , String friend_image) {
        this.friend_name = friend_name;
        this.friend_id = friend_id;
        this.friend_image = friend_image;
    }

    public String getFriend_name() {
        return friend_name;
    }

    public String getFriend_id() {
        return friend_id;
    }

    public String getFriend_image() {
        return friend_image;
    }
}
