package com.example.yjo.book;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface Service {
    @FormUrlEncoded
    @POST("/test.php")
    public void sing(
            @Field("Id") String Id,
            @Field("jo") int jo,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/test3.php")
    public void sing2(
            @Field("Id") String Id,
            @Field("Pw") String Pw,
            @Field("name") String name,
            @Field("jo") int jo,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/login.php")
    public void login(
            @Field("id") String id,
            @Field("pw") String pw,
            @Field("login") int login,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/name.php")
    public void name(
            @Field("id") String name,
            Callback<Response> responseCallback
    );



    @FormUrlEncoded
    @POST("/namejson.php")
    public void namejson(
            @Field("id") String name,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/post.php")
    public void post(
            @Field("text") String text,
            @Field("id") String id,
            @Field("name") String name,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/post_view.php")
    public void post_view(
            @Field("id") String text,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/profile_image.php")
    public void profile_my_image(
            @Field("image") String image,
            Callback<Response> responseCallback
    );
}
