package com.example.yjo.book;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Post_update extends AppCompatActivity {
    EditText post_update;
    Button post_update_send;
    private String content,number , id;
    private RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://13.209.17.127").build();
    private Service service = restAdapter.create(Service.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_update);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Intent intent = getIntent();

        post_update = (EditText) findViewById(R.id.update_post);
        post_update_send = (Button) findViewById(R.id.update_post_send);

         content = intent.getStringExtra("content");
         number = intent.getStringExtra("number");
         id = intent.getStringExtra("id");


        post_update.setText(content);

        post_update_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.post_update(
                        post_update.getText().toString(),number,
                        new retrofit.Callback<Response>() {
                            @Override
                            public void success(Response response, Response response2) {
                                BufferedReader reader = null;
                                String id1;
                                try {
                                    reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                                    id1 = reader.readLine();
                                    Toast.makeText(Post_update.this, "수정되었습니다", Toast.LENGTH_SHORT).show();
                                    Intent intent1 = new Intent(Post_update.this,main_view.class);
                                    intent1.putExtra("name2",id);
                                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
