package com.example.yjo.book;

import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class profile extends AppCompatActivity {

    TextView textView;
    ImageView profile;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textView = (TextView) findViewById(R.id.profile_name);
        profile = (ImageView) findViewById(R.id.profile_image);
        button = (Button) findViewById(R.id.friends_add);
        profile.setBackground(new ShapeDrawable(new OvalShape()));
        profile.setClipToOutline(true);


        Intent intent = getIntent();


        String a = intent.getExtras().getString("name");

        textView.setText(a);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
