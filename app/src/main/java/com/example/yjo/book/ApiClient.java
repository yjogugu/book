package com.example.yjo.book;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BaseUrl = "http://13.209.17.127/imageupload/imageupload/uploads";
    private static Retrofit retrofit;

    public static  Retrofit getRetrofit(){

        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl("http://13.209.17.127")
                    .addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;
    }
}
