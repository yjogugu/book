package com.example.yjo.book;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class Comment extends AppCompatActivity {
    ImageView send;
    String id;
    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter;
    private  RecyclerView.LayoutManager layoutManager;
    private ArrayList<Comment_list_item> recycleritems = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        send = (ImageView) findViewById(R.id.send_button);
        Intent intent = getIntent();
        recyclerView  = (RecyclerView)findViewById(R.id.comment_list);
        id = intent.getExtras().getString("name");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Comment.this, id, Toast.LENGTH_SHORT).show();
            }
        });


        recycleritems = new ArrayList<Comment_list_item>();

        recycleritems.add(new Comment_list_item(id));
        recycleritems.add(new Comment_list_item(id));
        recycleritems.add(new Comment_list_item(id));
        recycleritems.add(new Comment_list_item(id));

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(Comment.this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Comment_adapter(recycleritems);


        recyclerView.setAdapter(adapter);
    }
}
