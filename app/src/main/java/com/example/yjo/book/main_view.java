package com.example.yjo.book;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.nhn.android.naverlogin.OAuthLogin;

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

public class main_view extends AppCompatActivity {
    Button btnLogout;
    EditText search;
    Button post_new , button,myprofile;
    static Context context;
    static final String CLIENT_ID = "U5StJhoEdYKC8ypzTZl7";
    static final String CLIENT_SECRET = "bLW9HPVZ3R";
    static final String CLIENT_NAME = "yjo0909";
    private static OAuthLogin naverLoginInstance;
    private RecyclerView recyclerView_post;
    private String id;
    private String no;
    private SwipeRefreshLayout mainBinding;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<String>mysqlcontent;
    private ArrayList<String>mysqlname;
    private String name2;
    private String name3;



    ArrayList<post_list_item> post_list_items = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        recyclerView_post = (RecyclerView) findViewById(R.id.posts_main);
        myprofile = (Button) findViewById(R.id.myprofile);
        mainBinding = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLo);
        post_new = (Button) findViewById(R.id.post_new);
        button = (Button) findViewById(R.id.button2);



        final Intent intent = getIntent();
        final String name = intent.getStringExtra("navername");


        content();
        mainBinding.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                content();
                mainBinding.setRefreshing(false);
            }

        });

        search = (EditText) findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(main_view.this,Searchfriend.class);
                startActivity(intent1);
            }
        });
        name2 = intent.getStringExtra("name2");
         RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://13.209.17.127").build(); //레트로핏 주소 지정

            Service service = restAdapter.create(Service.class); // Service 객체 생성
            service.name( //Service 객체에 name 이라는 클래스 호출
                    name2,
                    new retrofit.Callback<Response>() { // 레트로핏 사용

                        @Override
                        public void success(Response response, Response response2) {

                            BufferedReader reader = null; //버퍼링 판독자

                            try {
                                reader = new BufferedReader(new InputStreamReader(response.getBody().in())); //서버에 값을 보낸 부분

                                id = reader.readLine();

                                try{
                                    JSONObject jsonObject = new JSONObject(id); // json 사용방법
                                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                                    for(int i=0; i<jsonArray.length(); i++){
                                        JSONObject subjsonObject = jsonArray.getJSONObject(i);

                                        name3 = subjsonObject.getString("name");
                                        id = subjsonObject.getString("id");
                                        no = subjsonObject.getString("no");
                                        mysqlname.add(name3);
                                        //textView2.setText(name);
                                        //textView.setText(name+ " 님 환영합니다"); //어플 이름
                                    }


                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(main_view.this, id, Toast.LENGTH_SHORT).show();

                                //Toast.makeText(main_view.this, id, Toast.LENGTH_SHORT).show();
                                /*if(name==null){
                                    textView.setText(id+ " 님 환영합니다"); //어플 이름

                                    return;
                                }
                                else{
                                    textView.setText(name+ " 님 환영합니다");//네이버 이름
                                }*/



                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    }
            );



        btnLogout = (Button)findViewById(R.id.btnlogout);


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//로그아웃
                naverLoginInstance.logout(context);

                Toast.makeText(context, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(main_view.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        init();

        post_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(main_view.this,com.example.yjo.book.post_new.class);
                intent1.putExtra("email",name3);
                intent1.putExtra("name",id);
                startActivity(intent1);
            }
        });

        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(main_view.this,Myprofile.class);
                intent1.putExtra("name",id);
                intent1.putExtra("no",no);
                startActivity(intent1);
            }
        });



    }
    private void init(){
        context = this;
        naverLoginInstance = OAuthLogin.getInstance();
        naverLoginInstance.init(this,CLIENT_ID,CLIENT_SECRET,CLIENT_NAME);

    }
    public void content(){
        mysqlcontent = new ArrayList<>();
        mysqlname = new ArrayList<>();
        //textView2 = (TextView) findViewById(R.id.textView2);
        recyclerView_post = (RecyclerView) findViewById(R.id.posts_main);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://13.209.17.127").build();
        Service service = restAdapter.create(Service.class);
        service.post_view(
                id,
                new retrofit.Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        BufferedReader reader = null;
                        String id1;

                        try {
                            reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                            id1 = reader.readLine();
                            post_list_items = new ArrayList<post_list_item>();
                            try{
                                JSONObject jsonObject = new JSONObject(id1); // json 사용방법
                                JSONArray jsonArray = jsonObject.getJSONArray("result");

                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject subjsonObject = jsonArray.getJSONObject(i);

                                    String name = subjsonObject.getString("name");
                                    String content = subjsonObject.getString("content");
                                    String post_id = subjsonObject.getString("image");
                                    String id = subjsonObject.getString("id");
                                    String url = "http://13.209.17.127/imageupload/"+post_id;
                                    Log.e("태그1",post_id);
                                    mysqlname.add(name);
                                    mysqlcontent.add(content);
                                    post_list_items.add(new post_list_item(name,content,url,id)); //해당 갯수 데이터 갯수만큼 리사이클러뷰 아이템 적용

                                }

                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                            recyclerView_post.setHasFixedSize(true);

                            layoutManager = new LinearLayoutManager(main_view.this);
                            recyclerView_post.setLayoutManager(layoutManager);

                            adapter = new post_adapter(post_list_items);
                            recyclerView_post.setAdapter(adapter);
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
