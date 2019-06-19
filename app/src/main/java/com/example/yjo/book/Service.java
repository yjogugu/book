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
            @Field("name") String id,
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

    @FormUrlEncoded
    @POST("/coment.php")
    public void coment(
            @Field("id") String id,
            @Field("text") String text,
            @Field("name") String name,
            @Field("no") int num,
            Callback<Response> responseCallback
    );


    @FormUrlEncoded
    @POST("/coment_view.php")
    public void coment_view(
            @Field("no") int no,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/comcoment.php")
    public void coment_coment(
            @Field("id") String id,
            @Field("text") String text,
            @Field("name") String name,
            @Field("no") int num,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/comcoment_view.php")
    public void coment_view3(
            @Field("no") int no,
            Callback<Response> responseCallback
    );


    @FormUrlEncoded
    @POST("/comment_nubar.php")
    public void coment_numbar(
            @Field("no") int no,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/good.php")
    public void good(
            @Field("id") String id,
            @Field("num") String num,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/gooddown.php")
    public void good_down(
            @Field("id") String id,
            @Field("num") String num,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/good_check.php")
    public void good_check(
            @Field("id") String id,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/post_good_numbar.php")
    public void post_good_numbar(
            @Field("num") String num,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/good_friend_list.php")
    public void good_friend_list(
            @Field("num") String num,
            @Field("id") String id,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/friend_list_add.php")
    public void friend_add_list(
            @Field("id") String id,
            @Field("friend_id") String friend_id,
            @Field("name") String name,
            Callback<Response> responseCallback
    );
    @FormUrlEncoded
    @POST("/friend_list_check.php")
    public void friend_list_check(
            @Field("friend_id") String friend_id,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/friend_list.php")
    public void friend_list(
            @Field("friend_id") String friend_id,
            Callback<Response> responseCallback
    );


    @FormUrlEncoded
    @POST("/Post_update.php")
    public void post_update(
            @Field("content") String content,
            @Field("number") String number,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/Post_delete.php")
    public void post_delete(
            @Field("number") String number,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/Comment_update.php")
    public void comment_update(
            @Field("content") String content,
            @Field("number") String number,
            Callback<Response> responseCallback
    );
    @FormUrlEncoded
    @POST("/Comment_delete.php")
    public void comment_delete(
            @Field("number") String number,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/Comcomment_update.php")
    public void comcoment_update(
            @Field("content") String content,
            @Field("number") String number,
            Callback<Response> responseCallback
    );
    @FormUrlEncoded
    @POST("/Comcomment_delete.php")
    public void comcom_delete(
            @Field("number") String number,
            @Field("comment_num") String num,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/imaeg_change.php")
    public void image_change(
            @Field("id") String id,
            @Field("result") String result,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/image_change_notice.php")
    public void image_change_notice(
            @Field("id") String id,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/Chat.php")
    public void Chat(
            @Field("id") String id,
            @Field("message") String message,
            Callback<Response> responseCallback
    );

}
