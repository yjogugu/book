package com.example.yjo.book;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Comment_update extends AppCompatActivity {
    EditText Coment_post;
    Button back, Coment_update_send ;
    private String comment_post , comment_num,id,name,no;
    private RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://13.209.17.127").build();
    private Service service = restAdapter.create(Service.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_update);
        Coment_post = (EditText) findViewById(R.id.Coment_post);
        back = (Button) findViewById(R.id.comment_back);
        Coment_update_send = (Button) findViewById(R.id.comment_update_send);

        final Intent intent = getIntent();

        comment_post = intent.getStringExtra("post");
        comment_num = intent.getStringExtra("post_num");
        id= intent.getStringExtra("id");
        name= intent.getStringExtra("name");
        no = intent.getStringExtra("num");
        Coment_post.setText(comment_post);

        Coment_update_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.comment_update(
                        Coment_post.getText().toString(),comment_num,
                        new retrofit.Callback<Response>() {
                            @Override
                            public void success(Response response, Response response2) {
                                BufferedReader reader = null;
                                String id1;
                                try {
                                    reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                                    id1 = reader.readLine();
                                    Toast.makeText(Comment_update.this, "수정되었습니다", Toast.LENGTH_SHORT).show();

                                    Intent intent1 = new Intent(Comment_update.this , Comment.class);
                                    intent1.putExtra("id",id);
                                    intent1.putExtra("name",name);
                                    intent1.putExtra("no",no);
                                    startActivity(intent1);
                                    finish();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        }


                );

            }
        });
    }
}
