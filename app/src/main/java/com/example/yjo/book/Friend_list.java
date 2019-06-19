package com.example.yjo.book;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Friend_list extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Friend_list_item> frd = null;
    String a="aa";
    String b ="bb";
    String friend_id , friend_name;
    private String url;
    String friend_mysql_id , friend_mysql_name , friend_mysql_image;
    String login_id;
    Socket socket;
    private RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://13.209.17.127").build(); //레트로핏 주소 지정

    private Service service = restAdapter.create(Service.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        recyclerView=(RecyclerView)findViewById(R.id.friend_recyclerView_list);

        Intent intent = getIntent();
        friend_id = intent.getStringExtra("id");
        friend_name = intent.getStringExtra("name");
        login_id = intent.getStringExtra("login");


        new Tcpsocket(socket,login_id).start();
        service.friend_list( //Service 객체에 name 이라는 클래스 호출
                friend_id,
                new retrofit.Callback<Response>() { // 레트로핏 사용

                    @Override
                    public void success(Response response, Response response2) {

                        BufferedReader reader = null; //버퍼링 판독자
                        String id;

                        try {
                            reader = new BufferedReader(new InputStreamReader(response.getBody().in())); //서버에 값을 보낸 부분

                            id = reader.readLine();
                            frd = new ArrayList<Friend_list_item>();
                            try{
                                JSONObject jsonObject = new JSONObject(id); // json 사용방법
                                JSONArray jsonArray = jsonObject.getJSONArray("result");

                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject subjsonObject = jsonArray.getJSONObject(i);

                                    friend_mysql_id = subjsonObject.getString("name");
                                    friend_mysql_name = subjsonObject.getString("friend");
                                    friend_mysql_image = subjsonObject.getString("image");
                                    url = "http://13.209.17.127/imageupload/"+friend_mysql_image;

                                    frd.add(new Friend_list_item(friend_mysql_name,friend_mysql_id,url));
                                }

                                recyclerView.setHasFixedSize(true);

                                layoutManager = new LinearLayoutManager(Friend_list.this);
                                recyclerView.setLayoutManager(layoutManager);
                                adapter = new friend_list_adapter(frd);
                                recyclerView.setAdapter(adapter);


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


    public class friend_list_adapter extends RecyclerView.Adapter<friend_list_adapter.Friend_Holder>{
        ArrayList<Friend_list_item> mdata;

        public friend_list_adapter(ArrayList<Friend_list_item> mydata){
            mdata = mydata;
        }
        @NonNull
        @Override
        public Friend_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.good_frend_list_item,viewGroup,false);
            Friend_Holder vh = new Friend_Holder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull Friend_Holder friend_holder, int i) {
            friend_holder.good_list_name.setText(mdata.get(i).getFriend_id());
            friend_holder.good_list_num.setText(mdata.get(i).getFriend_name());
            Glide.with(friend_holder.itemView.getContext())
                    .load(mdata.get(i).getFriend_image())
                    .apply(new RequestOptions().centerCrop().circleCrop())
                    .skipMemoryCache (true)
                    .error(R.drawable.baseline_account_circle_black_18dp)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .thumbnail(0.5f)
                    .into(friend_holder.good_list_image);
        }

        @Override
        public int getItemCount() {
            return mdata.size();
        }

        public class Friend_Holder extends RecyclerView.ViewHolder{
            ImageView good_list_image;
            TextView good_list_name,good_list_num,good_list_id;
            LinearLayout linearLayout;
            public Friend_Holder(@NonNull View itemView) {
                super(itemView);
                good_list_image = (ImageView) itemView.findViewById(R.id.good_friend_image);
                good_list_name = (TextView) itemView.findViewById(R.id.good_friend_name);
                good_list_num = (TextView) itemView.findViewById(R.id.good_friend_num);
                good_list_id = (TextView) itemView.findViewById(R.id.good_friend_id);
                linearLayout = (LinearLayout) itemView.findViewById(R.id.friend_layout);

                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),chat.class);
                        Context context = v.getContext();
                        intent.putExtra("login",login_id);
                        intent.putExtra("id",good_list_num.getText().toString());
                        context.startActivity(intent);
                    }
                });


            }
        }
    }
}
