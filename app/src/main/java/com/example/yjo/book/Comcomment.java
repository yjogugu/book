package com.example.yjo.book;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
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

public class Comcomment extends AppCompatActivity {
    String coment_coment;
    EditText coment_coment_text;
    ImageView coment_coment_send,coment_coment_profile;
    TextView coment_coment_po , coment_coment_nm;
    String coment_coment_post,coment_comemtname,coment_coment_id;
    String user_id;
    String name ;
    String user_num;
    int no;
    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter;
    private  RecyclerView.LayoutManager layoutManager;
    private ArrayList<Comment_list_item> recycleritems = null;
    private RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://13.209.17.127").build(); //레트로핏 주소 지정
    private Service service = restAdapter.create(Service.class); // Service 객체 생성
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comcomment);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        coment_coment_profile = (ImageView) findViewById(R.id.coment_coment_profile);
        coment_coment_text = (EditText) findViewById(R.id.coment_coment_text);
        coment_coment_send = (ImageView)findViewById(R.id.coment_send_button);
        coment_coment_po = (TextView) findViewById(R.id.comment_coment_post) ;
        coment_coment_nm = (TextView) findViewById(R.id.coment_coment_name);
        recyclerView = (RecyclerView) findViewById(R.id.coment_comment);



        Intent intent = getIntent();

        coment_comemtname = intent.getExtras().getString("name");
        coment_coment_id = intent.getExtras().getString("id");
        coment_coment_post = intent.getExtras().getString("post");
        user_id = intent.getExtras().getString("user_id");
        user_num = intent.getExtras().getString("post_num");

        no = Integer.parseInt(user_num);
        coment_info();


        String url = "http://13.209.17.127/imageupload/uploads/"+coment_coment_id+".jpg";


        coment_coment_nm.setText(coment_comemtname);
        coment_coment_po.setText(coment_coment_post);
        Glide.with(Comcomment.this)
                .load(url)
                .apply(new RequestOptions().centerCrop().circleCrop())
                .skipMemoryCache (true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .thumbnail(0.5f)
                .into(coment_coment_profile); // 원본 댓글

       coment_view2();

        coment_coment_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coment_coment_send2();
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        coment_view2();
                        coment_coment_text.setText(null);
                        adapter.notifyDataSetChanged();
                    }
                }, 100);


            }
        });




    }
    public void coment_coment_send2(){

        coment_coment = coment_coment_text.getText().toString();
        service.coment_coment( //Service 객체에 name 이라는 클래스 호출
                user_id,coment_coment,name,no,
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

    public void coment_info(){ //댓글에서 받아온 아이디 의 이름 보여주기
        service.name( //Service 객체에 name 이라는 클래스 호출
                user_id,
                new retrofit.Callback<Response>() { // 레트로핏 사용

                    @Override
                    public void success(Response response, Response response2) {

                        BufferedReader reader = null; //버퍼링 판독자

                        try {
                            reader = new BufferedReader(new InputStreamReader(response.getBody().in())); //서버에 값을 보낸 부분

                            String user_id1 = reader.readLine();

                            try{
                                JSONObject jsonObject = new JSONObject(user_id1); // json 사용방법
                                JSONArray jsonArray = jsonObject.getJSONArray("result");

                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject subjsonObject = jsonArray.getJSONObject(i);

                                    name = subjsonObject.getString("name");


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

    public void coment_view2(){ //대댓글 보여주기
        service.coment_view3(
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
                                    String no = subjsonObject.getString("no");
                                    String url = "http://13.209.17.127/imageupload/"+post_id;

                                    recycleritems.add(new Comment_list_item(name,url,content,id,no)); //해당 갯수 데이터 갯수만큼 리사이클러뷰 아이템 적용
                                }


                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }

                            recyclerView.setHasFixedSize(true);

                            layoutManager = new LinearLayoutManager(Comcomment.this);
                            recyclerView.setLayoutManager(layoutManager);

                            adapter = new Comcoment_adapter(recycleritems);
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

    public class Comcoment_adapter extends RecyclerView.Adapter<Comcoment_adapter.ViewHoder>{
        ArrayList<Comment_list_item> mdata;

        public Comcoment_adapter(ArrayList<Comment_list_item>mydata){
            mdata=mydata;

        }

        @NonNull
        @Override
        public ViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_comment_item,viewGroup,false);
            ViewHoder vh = new ViewHoder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHoder viewHoder, int i) {

            viewHoder.coment_coment_textView.setText(mdata.get(i).getComment());
            viewHoder.coment_coment_name.setText(mdata.get(i).getComment_name());
            Glide.with(viewHoder.itemView.getContext())
                    .load(mdata.get(i).getComment_image())
                    .apply(new RequestOptions().centerCrop().circleCrop())
                    .skipMemoryCache (true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .thumbnail(0.5f)
                    .into(viewHoder.coment_coment_profile);
            viewHoder.comcoment_id.setText(mdata.get(i).getComent_id());
            viewHoder.comcoment_num.setText(mdata.get(i).getComent_no());

            if(viewHoder.comcoment_id.getText().toString().equals(user_id)){
                viewHoder.coment_coment_update.setVisibility(View.VISIBLE);
                viewHoder.coment_coment_delete.setVisibility(View.VISIBLE);
            }
            else{
                viewHoder.coment_coment_update.setVisibility(View.GONE);
                viewHoder.coment_coment_delete.setVisibility(View.GONE);
            }

        }

        @Override
        public int getItemCount() {
            return mdata.size();
        }

        public class ViewHoder extends RecyclerView.ViewHolder {
            TextView coment_coment_textView,coment_coment_name,comcoment_num,comcoment_id;
            TextView coment_coment_answer;
            ImageView coment_coment_profile;
            TextView coment_coment_update , coment_coment_delete;
            public ViewHoder(@NonNull View itemView) {
                super(itemView);
                coment_coment_textView = (TextView)itemView.findViewById(R.id.comment_coment_post);
                coment_coment_name = (TextView) itemView.findViewById(R.id.coment_coment_name);
                coment_coment_profile = (ImageView) itemView.findViewById(R.id.coment_coment_profile2);
                coment_coment_answer = (TextView) itemView.findViewById(R.id.coment_coment_answer);
                coment_coment_update = (TextView) itemView.findViewById(R.id.comcoment_update);
                coment_coment_delete = (TextView) itemView.findViewById(R.id.comcoment_delete);
                comcoment_num = (TextView) itemView.findViewById(R.id.comcoment_num);
                comcoment_id =(TextView) itemView.findViewById(R.id.comcoment_id);


                coment_coment_update .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext() , Comcoment_update.class);
                        Context context = v.getContext();
                        intent.putExtra("post",coment_coment_textView.getText().toString());
                        intent.putExtra("num",comcoment_num.getText().toString());
                        intent.putExtra("no",user_num);
                        intent.putExtra("id",coment_coment_id);
                        intent.putExtra("name",coment_comemtname);
                        intent.putExtra("compost",coment_coment_post);
                        context.startActivity(intent);
                    }
                });

                coment_coment_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        service.comcom_delete(
                                comcoment_num.getText().toString(),user_num,
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
            }
        }
    }
}
