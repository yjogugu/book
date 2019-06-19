package com.example.yjo.book;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
    String friend_id;
    String button_a;
    String userid;

    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter;
    private  RecyclerView.LayoutManager layoutManager;
    RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://13.209.17.127").build(); //레트로핏 주소 지정
    Service service = restAdapter.create(Service.class); //서비스 인터페이스 객체 생성
    private String num,name;
    private ArrayList<String>mysqlemail;
    private ArrayList<String>mysqlname;
    private ArrayList<String>numberList;
    String a,b;
    String id;
    private String friend_name;

    ArrayList<friend_recycleritem> recycleritems = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchfriend);

        editText = (EditText) findViewById(R.id.search_friend);
        textView = (TextView) findViewById(R.id.textView4);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        Intent intent = getIntent();
        id =intent.getStringExtra("id");


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
        final String friend = editText.getText().toString();
        service.namejson(//이름 불러오기
                friend,id,
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

                                    friend_name = subjsonObject.getString("name");
                                    String id = subjsonObject.getString("id");

                                    mysqlname.add(friend_name);
                                    mysqlemail.add(id);

                                    recycleritems.add(new friend_recycleritem(friend_name,id)); //해당 갯수 데이터 갯수만큼 리사이클러뷰 아이템 적용

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

    public class recycleritem_adapter extends RecyclerView.Adapter<recycleritem_adapter.ViewHolder>{

        ArrayList <friend_recycleritem> mDataset;
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView,textView_email;
            Button friend_add_button;
            Context context;
            public ViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.itemNameTv);
                textView_email=(TextView) itemView.findViewById(R.id.item_email);
                friend_add_button =(Button) itemView.findViewById(R.id.friend_add_button);

                friend_id = textView_email.getText().toString();
                friend_add_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        service.friend_add_list(//이름 불러오기
                                id,textView_email.getText().toString(),textView.getText().toString(),
                                new retrofit.Callback<Response>() {
                                    @Override
                                    public void success(Response response, Response response2) {
                                        BufferedReader reader = null;
                                        try{
                                            reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                                            button_a = reader.readLine();
                                            if(button_a.equals("1")){
                                                friend_add_button.setVisibility(View.GONE);
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

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),profile.class);
                        Context context = v.getContext();
                        intent.putExtra("name",textView.getText().toString());
                        context.startActivity(intent);
                    }
                });

            }
        }

        public recycleritem_adapter(ArrayList<friend_recycleritem> myDataset){
            mDataset=myDataset;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler,parent,false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.textView.setText(mDataset.get(position).getName());
            holder.textView_email.setText(mDataset.get(position).getEmail());
            service.friend_list_check(//이름 불러오기
                    holder.textView_email.getText().toString(),
                    new retrofit.Callback<Response>() {
                        @Override
                        public void success(Response response, Response response2) {
                            BufferedReader reader = null;
                            try{
                                reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                                button_a = reader.readLine();
                                try{
                                    JSONObject jsonObject = new JSONObject(button_a); // json 사용방법
                                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                                    for(int i=0; i<jsonArray.length(); i++){
                                        JSONObject subjsonObject = jsonArray.getJSONObject(i);

                                        a = subjsonObject.getString("gone");
                                        b = subjsonObject.getString("friend");
                                        userid = subjsonObject.getString("id");
                                    }
                                    if(holder.textView_email.getText().toString().equals(b) && id.equals(userid) && a.equals("1") ){
                                        holder.friend_add_button.setVisibility(View.GONE);
                                    }
                                    else{
                                        holder.friend_add_button.setVisibility(View.VISIBLE);
                                    }



                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
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

        @Override
        public int getItemCount() {
            return mDataset.size();
        }


    }




}
