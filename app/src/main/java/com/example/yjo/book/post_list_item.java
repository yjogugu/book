package com.example.yjo.book;

public class post_list_item {

    private String posts;
    private String name;
    private String images;
    private String id;
    private String no;

    public post_list_item(String content, String name , String images , String id , String no) {
        this.name = name;
        this.posts = content;
        this.images = images;
        this.id = id;
        this.no=no;
    }


    public String getPosts() {
        return posts;
    }


    public String getName() {
        return name;
    }


    public String getImages() { return images; }

    public String getId() {
        return id;
    }

    public String getNo() {
        return no;
    }
}
