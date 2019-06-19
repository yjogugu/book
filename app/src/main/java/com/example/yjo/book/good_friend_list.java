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
import android.widget.ImageView;
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
import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class good_friend_list extends AppCompatActivity {
    private RecyclerView recyclerView ;
    private ArrayList<good_friend_list_item> lstAnime = null;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://13.209.17.127").build(); //레트로핏 주소 지정
    private Service service = restAdapter.create(Service.class); // Service 객체 생성
    String good_no,good_num,good_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_friend_list);
        recyclerView = (RecyclerView) findViewById(R.id.good_friend_list);
        Intent intent = getIntent();

        good_no = intent.getStringExtra("no");
        good_id = intent.getStringExtra("id");
        //Toast.makeText(this, good_no, Toast.LENGTH_SHORT).show();

        service.good_friend_list( //Service 객체에 name 이라는 클래스 호출
                good_no,good_id,
                new retrofit.Callback<Response>() { // 레트로핏 사용

                    @Override
                    public void success(Response response, Response response2) {

                        BufferedReader reader = null; //버퍼링 판독자
                        String a;
                        try {
                            reader = new BufferedReader(new InputStreamReader(response.getBody().in())); //서버에 값을 보낸 부분

                            a = reader.readLine();
                            lstAnime = new ArrayList<good_friend_list_item>();
                            try{
                                JSONObject jsonObject = new JSONObject(a); // json 사용방법
                                JSONArray jsonArray = jsonObject.getJSONArray("result");

                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject subjsonObject = jsonArray.getJSONObject(i);
                                    String good_id= subjsonObject.getString("id");
                                    good_num = subjsonObject.getString("num");
                                    String url = "http://13.209.17.127/imageupload/uploads/"+good_id+".jpg";
                                    lstAnime.add(new good_friend_list_item(good_id,url,good_num)); //해당 갯수 데이터 갯수만큼 리사이클러뷰 아이템 적용

                                }

                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }

                            recyclerView.setHasFixedSize(true);

                            layoutManager = new LinearLayoutManager(good_friend_list.this);
                            recyclerView.setLayoutManager(layoutManager);
                            adapter = new good_friend_list_adapter(lstAnime);
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


    public class good_friend_list_adapter extends RecyclerView.Adapter<good_friend_list_adapter.ViewHolder>{

        ArrayList<good_friend_list_item> mdata;

        public good_friend_list_adapter(ArrayList<good_friend_list_item> mydata){
            mdata = mydata;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.good_frend_list_item,viewGroup,false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.good_list_name.setText(mdata.get(i).getGood_friend_name());
            Glide.with(viewHolder.itemView.getContext())
                    .load(mdata.get(i).getGood_friend_image())
                    .apply(new RequestOptions().centerCrop().circleCrop())
                    .skipMemoryCache (true)
                    .error(R.drawable.baseline_account_circle_black_18dp)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .thumbnail(0.5f)
                    .into(viewHolder.good_list_image);

            viewHolder.good_list_num.setText(mdata.get(i).getGood_friend_num());

        }

        @Override
        public int getItemCount() {
            return mdata.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView good_list_image;
            TextView good_list_name,good_list_num;

            public ViewHolder(View itemView) {
                super(itemView);
                good_list_image = (ImageView) itemView.findViewById(R.id.good_friend_image);
                good_list_name = (TextView) itemView.findViewById(R.id.good_friend_name);
                good_list_num = (TextView) itemView.findViewById(R.id.good_friend_num);
            }
        }



    }
}
