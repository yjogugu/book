package com.example.yjo.book;

public class good_friend_list_item {

    private String good_friend_name;
    private String good_friend_image;
    private String good_friend_num;

    public good_friend_list_item(String name, String image, String num) {
        this.good_friend_name = name;
        this.good_friend_image = image;
        this.good_friend_num = num;
    }

    public String getGood_friend_name() {
        return good_friend_name;
    }

    public String getGood_friend_image() {
        return good_friend_image;
    }

    public String getGood_friend_num() {
        return good_friend_num;
    }
}
