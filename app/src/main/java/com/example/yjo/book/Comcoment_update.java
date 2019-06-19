package com.example.yjo.book;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Comcoment_update extends AppCompatActivity {
    Button comcoment_update_send , comcoment_back;
    EditText comcoment_post_edittext;
    private String com_com_post,com_com_num,no,id,name,post;
    private RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://13.209.17.127").build();
    private Service service = restAdapter.create(Service.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comcoment_update);
        comcoment_update_send = (Button) findViewById(R.id.comcomment_update_send);
        comcoment_back = (Button) findViewById(R.id.comcomment_back);
        comcoment_post_edittext = (EditText) findViewById(R.id.Comcoment_post);
        final Intent intent = getIntent();
        com_com_post = intent.getStringExtra("post");
        com_com_num = intent.getStringExtra("num");
        no = intent.getStringExtra("no");
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        post = intent.getStringExtra("compost");


        comcoment_post_edittext.setText(com_com_post);

        comcoment_update_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.comcoment_update(
                        comcoment_post_edittext.getText().toString(),com_com_num,
                        new retrofit.Callback<Response>() {
                            @Override
                            public void success(Response response, Response response2) {
                                BufferedReader reader = null;
                                String id1;
                                try {
                                    reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                                    id1 = reader.readLine();
                                    Toast.makeText(Comcoment_update.this, "수정되었습니다", Toast.LENGTH_SHORT).show();
                                    Intent intent1 = new Intent(Comcoment_update.this , Comcomment.class);
                                    intent1.putExtra("id",id);
                                    intent1.putExtra("name",name);
                                    intent1.putExtra("post_num",no);
                                    intent1.putExtra("post",post);
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
