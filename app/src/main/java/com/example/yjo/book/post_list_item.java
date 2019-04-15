package com.example.yjo.book;

public class post_list_item {

    private String posts;
    private String name;
    private String images;
    private String id;

    public post_list_item(String content, String name , String images , String id) {
        this.name = name;
        this.posts = content;
        this.images = images;
        this.id = id;
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
}
