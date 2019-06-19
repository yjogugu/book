package com.example.yjo.book;

public class Comment_list_item {
    String comment_name;
    String comment_image;
    String comment;
    String coment_id;
    String coment_no;
    String user_name;
    String user_post;

    public Comment_list_item(String comment_name , String comment_image , String comment ,String coment_id , String coment_no ) {
        this.comment_name = comment_name;
        this.comment_image = comment_image;
        this.comment = comment;
        this.coment_id = coment_id;
        this.coment_no = coment_no;
    }

    public Comment_list_item(String user_name, String user_post) {
        this.user_name = user_name;
        this.user_post = user_post;
    }

    public String getComment_name() {
        return comment_name;
    }

    public String getComment_image() {
        return comment_image;
    }

    public String getComment() {
        return comment;
    }

    public String getComent_id() {
        return coment_id;
    }

    public String getComent_no() {
        return coment_no;
    }


    public String getUser_name() {
        return user_name;
    }

    public String getUser_post() {
        return user_post;
    }
}
