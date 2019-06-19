package com.example.yjo.book;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
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

public class Comment extends AppCompatActivity {
    ImageView send,coment_coment_image;
    String id,name,num,numbar;
    int no,no2;
    int a;
    String coment;
    String coment_numbar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mainBinding;
    private  RecyclerView.Adapter adapter;
    private  RecyclerView.LayoutManager layoutManager;
    private ArrayList<Comment_list_item> recycleritems = null;
    private RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://13.209.17.127").build(); //레트로핏 주소 지정
    private Service service = restAdapter.create(Service.class); // Service 객체 생성
    EditText coment_text;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        send = (ImageView) findViewById(R.id.send_button);
        mainBinding = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLo3);
        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        name = intent.getExtras().getString("name");
        num = intent.getExtras().getString("no");
        no = Integer.parseInt(num);
        adapter = new Comment_adapter(recycleritems);

        coment_view();
        mainBinding.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                coment_view();
                mainBinding.setRefreshing(false);
            }

        });
        recyclerView  = (RecyclerView)findViewById(R.id.comment_list);
        coment_text = (EditText) findViewById(R.id.coment_edittext);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coment_send();
                coment_text.setText(null);
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        coment_view();
                        adapter.notifyDataSetChanged();
                    }
                }, 100);

            }
        });


    }

    public void coment_send(){
        coment = coment_text.getText().toString();
        service.coment( //Service 객체에 name 이라는 클래스 호출
                id,coment,name,no,
                new retrofit.Callback<Response>() { // 레트로핏 사용

                    @Override
                    public void success(Response response, Response response2) {

                        BufferedReader reader = null; //버퍼링 판독자
                        String a;
                        try {
                            reader = new BufferedReader(new InputStreamReader(response.getBody().in())); //서버에 값을 보낸 부분

                            a = reader.readLine();


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

    public void coment_view(){

        service.coment_view(
                no,
                new retrofit.Callback<Response>() { // 레트로핏 사용

                    @Override
                    public void success(Response response, Response response2) {
                        BufferedReader reader = null; //버퍼링 판독자
                        String a;
                        try {
                            reader = new BufferedReader(new InputStreamReader(response.getBody().in())); //서버에 값을 보낸 부분

                            a = reader.readLine();
                            recycleritems = new ArrayList<Comment_list_item>();

                            try{
                                JSONObject jsonObject = new JSONObject(a); // json 사용방법
                                JSONArray jsonArray = jsonObject.getJSONArray("result");

                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject subjsonObject = jsonArray.getJSONObject(i);
                                    String name = subjsonObject.getString("name");
                                    String post_id = subjsonObject.getString("image");
                                    String content = subjsonObject.getString("content");
                                    String id = subjsonObject.getString("id");
                                    numbar = subjsonObject.getString("no");
                                    String url = "http://13.209.17.127/imageupload/"+post_id;
                                    recycleritems.add(new Comment_list_item(name,url,content,id,numbar)); //해당 갯수 데이터 갯수만큼 리사이클러뷰 아이템 적용
                                    adapter.notifyDataSetChanged();
                                }


                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }

                            recyclerView.setHasFixedSize(true);

                            layoutManager = new LinearLayoutManager(Comment.this);
                            recyclerView.setLayoutManager(layoutManager);
                            adapter = new Comment_adapter(recycleritems);


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



    public class Comment_adapter extends RecyclerView.Adapter<Comment_adapter.ViewHoder> {
        ArrayList<Comment_list_item> mdata;

        public Comment_adapter (ArrayList<Comment_list_item> mydata){
            mdata = mydata;
        }

        @NonNull
        @Override
        public ViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_item,viewGroup,false);
            ViewHoder vh = new ViewHoder(view);
            return vh;

        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHoder viewHoder, int i) {
            viewHoder.textView.setText(mdata.get(i).getComment());
            viewHoder.coment_name.setText(mdata.get(i).getComment_name());
            Glide.with(viewHoder.itemView.getContext())
                    .load(mdata.get(i).getComment_image())
                    .apply(new RequestOptions().centerCrop().circleCrop())
                    .skipMemoryCache (true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .thumbnail(0.5f)
                    .into(viewHoder.coment_profile);
            viewHoder.coment_id.setText(mdata.get(i).getComent_id());
            viewHoder.coment_no.setText(mdata.get(i).getComent_no());
            if(viewHoder.coment_id.getText().toString().equals(id)){
                viewHoder.comment_update.setVisibility(View.VISIBLE);
                viewHoder.comment_delete.setVisibility(View.VISIBLE);
            }
            else{
                viewHoder.comment_update.setVisibility(View.GONE);
                viewHoder.comment_delete.setVisibility(View.GONE);
            }
            getItemCount();
            service.coment_numbar(
                    no,
                    new retrofit.Callback<Response>() { // 레트로핏 사용

                        @Override
                        public void success(Response response, Response response2) {
                            BufferedReader reader = null; //버퍼링 판독자
                            try {
                                reader = new BufferedReader(new InputStreamReader(response.getBody().in())); //서버에 값을 보낸 부분

                                coment_numbar = reader.readLine();
                                try{
                                    JSONObject jsonObject = new JSONObject(coment_numbar); // json 사용방법
                                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                                    for(int i=0; i<jsonArray.length(); i++){
                                        JSONObject subjsonObject = jsonArray.getJSONObject(i);
                                        coment_numbar = subjsonObject.getString("no");

                                        if(coment_numbar.equals(viewHoder.coment_no.getText())){

                                            viewHoder.coment_coment_post.setVisibility(View.VISIBLE);
                                            viewHoder.coment_coment_post.setText("답글이 있습니다");
                                            return;
                                        }
                                        else{
                                            viewHoder.coment_coment_post.setVisibility(View.GONE);
                                        }


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
            return mdata.size();
        }

        public  class  ViewHoder extends RecyclerView.ViewHolder{
            TextView textView,coment_name,coment_no;
            TextView coment_answer,coment_id , comment_update , comment_delete;
            ImageView coment_profile;
            TextView coment_coment_user,coment_coment_post;
            public ViewHoder(@NonNull View itemView) {
                super(itemView);
                coment_no = (TextView) itemView.findViewById(R.id.comment_num);
                textView = (TextView)itemView.findViewById(R.id.comment_post);
                coment_name = (TextView) itemView.findViewById(R.id.coment_name);
                coment_profile = (ImageView) itemView.findViewById(R.id.coment_profile);
                coment_answer = (TextView) itemView.findViewById(R.id.coment_answer);
                coment_id = (TextView) itemView.findViewById(R.id.coment_id);

                comment_update = (TextView) itemView.findViewById(R.id.coment_update);
                comment_delete = (TextView) itemView.findViewById(R.id.coment_delete);

                coment_coment_user = (TextView)itemView.findViewById(R.id.coment_coment_user_name);
                coment_coment_post = (TextView)itemView.findViewById(R.id.coment_coment_user_post);
                coment_coment_image = (ImageView)findViewById(R.id.coment_coment_user_image);

                comment_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        service.comment_delete(
                                coment_no.getText().toString(),
                                new retrofit.Callback<Response>() {
                                    @Override
                                    public void success(Response response, Response response2) {
                                        BufferedReader reader = null;
                                        String id1;
                                        try {
                                            reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                                            id1 = reader.readLine();

                                            new Handler().postDelayed(new Runnable()
                                            {
                                                @Override
                                                public void run()
                                                {
                                                    mdata.remove(getAdapterPosition());
                                                    notifyItemRemoved(getAdapterPosition());
                                                    notifyItemRangeChanged(getAdapterPosition(), mdata.size());

                                                }
                                            }, 100);
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


                comment_update .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext() , Comment_update.class);
                        Context context = v.getContext();
                        intent.putExtra("num",num);
                        intent.putExtra("name",name);
                        intent.putExtra("id",id);
                        intent.putExtra("post",textView.getText().toString());
                        intent.putExtra("post_num",coment_no.getText().toString());
                        context.startActivity(intent);
                        finish();

                    }
                });


                coment_coment_post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),Comcomment.class);
                        Context context = v.getContext();
                        intent.putExtra("id",coment_id.getText().toString());
                        intent.putExtra("post",textView.getText().toString());
                        intent.putExtra("name",coment_name.getText().toString());
                        intent.putExtra("user_id",id);
                        intent.putExtra("post_num",coment_no.getText().toString());
                        context.startActivity(intent);
                    }
                });
                coment_answer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),Comcomment.class);
                        Context context = v.getContext();
                        intent.putExtra("id",coment_id.getText().toString());
                        intent.putExtra("post",textView.getText().toString());
                        intent.putExtra("name",coment_name.getText().toString());
                        intent.putExtra("user_id",id);
                        intent.putExtra("post_num",coment_no.getText().toString());
                        context.startActivity(intent);
                    }
                });
            }
        }




    }


}
