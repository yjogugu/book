package com.example.yjo.book;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Service2 {
    @FormUrlEncoded
    @POST("/imageupload/upload.php")
    Call<ImageClass> uploadImage(@Field("title") String title , @Field("image") String image);
}
