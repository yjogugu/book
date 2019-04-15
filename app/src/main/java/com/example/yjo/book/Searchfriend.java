package com.example.yjo.book;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Searchfriend extends AppCompatActivity{

    EditText editText;
    TextView textView;
    Button button;

    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter;
    private  RecyclerView.LayoutManager layoutManager;

    private String num,name;
    private ArrayList<String>mysqlemail;
    private ArrayList<String>mysqlname;
    private ArrayList<String>numberList;

    ArrayList<friend_recycleritem> recycleritems = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchfriend);

        editText = (EditText) findViewById(R.id.search_friend);
        textView = (TextView) findViewById(R.id.textView4);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        Intent intent = getIntent();



        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name();
                mysqlemail = new ArrayList<>();
                mysqlname = new ArrayList<>();
            }
        });


    }

    public void name(){
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://13.209.17.127").build(); //레트로핏 주소 지정
        final Service service = restAdapter.create(Service.class); //서비스 인터페이스 객체 생성
        final String friend = editText.getText().toString();
        service.namejson(//이름 불러오기
                friend,
                new retrofit.Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        BufferedReader reader = null;

                        try{

                            reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                            name = reader.readLine();

                            recycleritems = new ArrayList<friend_recycleritem>();

                            try{
                                JSONObject jsonObject = new JSONObject(name); // json 사용방법
                                JSONArray jsonArray = jsonObject.getJSONArray("result");

                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject subjsonObject = jsonArray.getJSONObject(i);

                                    String name = subjsonObject.getString("name");
                                    String id = subjsonObject.getString("id");

                                    mysqlname.add(name);
                                    mysqlemail.add(id);

                                    recycleritems.add(new friend_recycleritem(name,id)); //해당 갯수 데이터 갯수만큼 리사이클러뷰 아이템 적용

                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }

                            recyclerView.setHasFixedSize(true);

                            layoutManager = new LinearLayoutManager(Searchfriend.this);
                            recyclerView.setLayoutManager(layoutManager);

                            adapter = new recycleritem_adapter(recycleritems);
                            recyclerView.setAdapter(adapter);


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





}
