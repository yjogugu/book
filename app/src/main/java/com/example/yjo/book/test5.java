package com.example.yjo.book;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class test5 extends AppCompatActivity {
    RecyclerView recyclerView;
    ScrollView scrollView;
    LinearLayout linearLayout ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test5);
        //final EditText editText = (EditText) findViewById(R.id.editText4);
       // recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        linearLayout = (LinearLayout)findViewById(R.id.li); //<-- 내부 레이아웃이라고 치고
        scrollView = (ScrollView) findViewById(R.id.sv_root);

    }

}


