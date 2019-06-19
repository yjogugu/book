package com.example.yjo.book;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import retrofit.RestAdapter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Pattern;
import retrofit.RetrofitError;
import retrofit.RestAdapter;
import retrofit.client.Response;

public class SignUp extends AppCompatActivity {
    private EditText editTextId;
    private EditText editTextPw;
    private EditText editTextname;
    private EditText editTextPw_ok;
    private Button ok_button,submit;
    private ImageView setImage;
    private int a=0,jo=0;
    private boolean check;
    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editTextId = (EditText) findViewById(R.id.new_id);
        editTextPw = (EditText) findViewById(R.id.new_pw);
        editTextname = (EditText)findViewById(R.id.new_nmae);
        editTextPw_ok = (EditText)findViewById(R.id.new_pw_ok);
        ok_button = (Button)findViewById(R.id.button_ok);
        submit = (Button)findViewById(R.id.btn_submit);
        setImage = (ImageView)findViewById(R.id.setImage);
        constraintLayout = findViewById(R.id.sing_up_view);

        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();

        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //중복확인 버튼
                String id = editTextId.getText().toString();

                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://13.209.17.127").build(); //레트로핏 주소 지정

                Service service = restAdapter.create(Service.class); // Service 객체 생성

                service.sing( //Service 객체에 sing 이라는 클래스 호출
                        id,jo, // edittext id 값과 int 형 jo 를 php 에 보냄

                        new retrofit.Callback<Response>() { // 레트로핏 사용

                            @Override
                            public void success(Response response, Response response2) {

                                BufferedReader reader = null; //버퍼링 판독자

                                String id;


                                try{
                                    if (!checkEmail(editTextId.getText().toString())) { //이메일 형식 이 아니면
                                        Toast.makeText(SignUp.this, "이메일 형식을 맞춰주세요", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    else{
                                        a=1;
                                        reader = new BufferedReader(new InputStreamReader(response.getBody().in())); //서버에 값을 보낸 부분
                                        id = reader.readLine();

                                        jo = Integer.parseInt(id); //string 형을 int 로 변환 하는 작업

                                        if(jo==0){
                                            Toast.makeText(SignUp.this, "중복된아이디", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(SignUp.this, "사용가능한 아이디", Toast.LENGTH_SHORT).show();
                                        }

                                    }


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
        editTextPw_ok.addTextChangedListener(new TextWatcher() { //비밀번호 체크 부분
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(editTextPw.getText().toString().equals(editTextPw_ok.getText().toString())) {
                    setImage.setImageResource(R.drawable.baseline_panorama_fish_eye_black_18dp);
                    check=true;
                }
                else if(editTextPw_ok.getText().toString().length()==0){
                    setImage.setImageBitmap(null);
                    check=false;
                }
                else {
                    check=false;
                    setImage.setImageResource(R.drawable.baseline_close_black_18dp);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() { //회원가입 버튼
            @Override
            public void onClick(View v) {
                String id = editTextId.getText().toString();
                String pw = editTextPw.getText().toString();
                String name = editTextname.getText().toString();

                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://13.209.17.127").build();

                Service service = restAdapter.create(Service.class);

                service.sing2(
                        id,pw,name,jo,
                        new retrofit.Callback<Response>() {
                            @Override
                            public void success(Response response, Response response2) {
                                BufferedReader reader = null;

                                String id;
                                try{
                                    if(editTextname.getText().toString().length()==0||editTextId.getText().toString().length()==0||editTextPw.getText().toString().length()==0){
                                        Toast.makeText(SignUp.this, "빈칸이 있는곳을 확인해주세요", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    else if (!checkEmail(editTextId.getText().toString())) {
                                        Toast.makeText(SignUp.this, "이메일 형식을 맞춰주세요", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    else if(a==0){
                                        Toast.makeText(SignUp.this, "중복확인 해주세요", Toast.LENGTH_SHORT).show();

                                        return;
                                    }

                                    else{
                                        if(check==false){
                                            Toast.makeText(SignUp.this, "비밀번호를 확인하세요", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        else if(jo==0){
                                            Toast.makeText(SignUp.this, "중복확인 해주세요", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        else {
                                            reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                                            id = reader.readLine();
                                            Toast.makeText(SignUp.this, id, Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(SignUp.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }



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



    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
}
