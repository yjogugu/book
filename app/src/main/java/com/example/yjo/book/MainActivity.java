package com.example.yjo.book;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
    EditText id_edit_text,pw_edit_text;
    Button sinup_button,login_button,test,test2;
    private int login=0;
    //private OAuthLoginButton naverLogInButton;
    //private static OAuthLogin naverLoginInstance;

    ImageView imageView;

    static final String CLIENT_ID = "U5StJhoEdYKC8ypzTZl7";
    static final String CLIENT_SECRET = "bLW9HPVZ3R";
    static final String CLIENT_NAME = "yjo0909";

    static Context context;
    private String name;
    private Intent intent;
    Button test_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test_button = (Button) findViewById(R.id.button3);
        imageView = (ImageView) findViewById(R.id.imageView4);
        ConstraintLayout constraintLayout = findViewById(R.id.main_view_id);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        test_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,test5.class);
                startActivity(intent);
            }
        });
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();

        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        /*getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );*/


        id_edit_text= (EditText) findViewById(R.id.editText);
        pw_edit_text=(EditText) findViewById(R.id.editText2);


        Glide.with(this)
                .load(R.drawable.b_main)
                .apply(new RequestOptions().centerCrop().circleCrop())
                .into(imageView);

        sinup_button = findViewById(R.id.sinup_button);

        login_button = findViewById(R.id.login);

        sinup_button.setOnClickListener(new View.OnClickListener() { //회원가입
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, SignUp.class);
                    startActivity(intent);

                }
            });

        login_button.setOnClickListener(new View.OnClickListener() {//기본 로그인
            @Override
            public void onClick(View v) {
                String id = id_edit_text.getText().toString();
                String pw = pw_edit_text.getText().toString();

                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://13.209.17.127").build();
                Service service = restAdapter.create(Service.class);
                service.login(
                        id,pw,login,
                        new retrofit.Callback<Response>(){

                            @Override
                            public void success(Response response, Response response2) {
                                BufferedReader reader = null;
                                String id;
                                try {

                                    reader = new BufferedReader(new InputStreamReader(response.getBody().in()));

                                    id = reader.readLine();

                                    if(!id_edit_text.getText().toString().equals(id)){
                                        Toast.makeText(MainActivity.this, "정보확인", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    else{
                                        intent = new Intent(MainActivity.this, main_view.class);
                                        intent.putExtra("name2",id);
                                        startActivity(intent);
                                        finish();
                                    }


                                }catch (IOException e) {
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

        //init();
        //init_View();

        test=(Button)findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,com.example.yjo.book.test.class);
                startActivity(intent);
            }
        });

        test2=(Button)findViewById(R.id.button2_test2);
        test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,test2.class);
                startActivity(intent);
            }
        });

    }

    /*//초기화
    private void init(){
        context = this;
        naverLoginInstance = OAuthLogin.getInstance();
        naverLoginInstance.init(this,CLIENT_ID,CLIENT_SECRET,CLIENT_NAME);

    }

    //변수 붙이기
    private void init_View(){
        naverLogInButton = (OAuthLoginButton)findViewById(R.id.buttonNaverLogin);

        //로그인 핸들러
        OAuthLoginHandler naverLoginHandler  = new OAuthLoginHandler() {
            @Override
            public void run(boolean success) {
                if (success) {//로그인 성공
                    Toast.makeText(context,"로그인 성공",Toast.LENGTH_SHORT).show();
                    new RequestApiTask().execute();
                    intent = new Intent(MainActivity.this,main_view.class);
                } else {//로그인 실패
                    String errorCode = naverLoginInstance.getLastErrorCode(context).getCode();
                    String errorDesc = naverLoginInstance.getLastErrorDesc(context);
                    Toast.makeText(context, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
                }
            }

        };

        naverLogInButton.setOAuthLoginHandler(naverLoginHandler);

    }

    private class RequestApiTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {//작업이 실행되기 전에 먼저 실행.
            //tv_mail.setText((String) "");//메일 란 비우기
        }

        @Override
        protected String doInBackground(Void... params) {//네트워크에 연결하는 과정이 있으므로 다른 스레드에서 실행되어야 한다.
            String url = "https://openapi.naver.com/v1/nid/me";
            String at = naverLoginInstance.getAccessToken(context);
            return naverLoginInstance.requestApi(context, at, url);//url, 토큰을 넘겨서 값을 받아온다.json 타입으로 받아진다.

        }

        protected void onPostExecute(String content) {//doInBackground 에서 리턴된 값이 여기로 들어온다.
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONObject response = jsonObject.getJSONObject("response");
                name = response.getString("name");
                intent.putExtra("navername",name);
                startActivity(intent);

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }*/



}


