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

public class post_new extends AppCompatActivity {
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_new);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        editText = (EditText) findViewById(R.id.editText_post);
        button = (Button) findViewById(R.id.post_write);

        final Intent intent = getIntent();
        final String emali = intent.getStringExtra("email");
        final String name = intent.getStringExtra("name");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://13.209.17.127").build();
                Service service = restAdapter.create(Service.class);
                service.post(
                        text,emali,name,
                        new retrofit.Callback<Response>() {
                            @Override
                            public void success(Response response, Response response2) {
                                BufferedReader reader = null;
                                String id;
                                try {
                                    reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                                    id = reader.readLine();
                                    Intent intent1 = new Intent(post_new.this,main_view.class);
                                    intent1.putExtra("name2",name);
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
