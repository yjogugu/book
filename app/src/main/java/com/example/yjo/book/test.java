package com.example.yjo.book;


import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class test extends AppCompatActivity {
    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter;
    private  RecyclerView.LayoutManager layoutManager;
    private  SwipeRefreshLayout mainBinding;
    ArrayList<friend_recycleritem> recycleritems = null;
    ArrayList<post_list_item> post_list_items = null;

    String al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mainBinding = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLo1);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_1);
        //test();


        mainBinding.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                content();
                mainBinding.setRefreshing(false);
            }

        });


    }

    public void content(){
        String content = "야";
        String name = "호";
        post_list_items = new ArrayList<post_list_item>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_1);

        //post_list_items.add(new post_list_item(content,name));

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(test.this);
        recyclerView.setLayoutManager(layoutManager);

        //adapter = new post_adapter(post_list_items);
        recyclerView.setAdapter(adapter);


    }






}
