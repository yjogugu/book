package com.example.yjo.book;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.nhn.android.naverlogin.OAuthLogin;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class main_view extends AppCompatActivity {
    TextView user_id;
    ImageView post_new ,btnLogout,myprofile , friend_list_button ,image_mychat;
    EditText search;
    Button button;
    String userid,ch;
    String image_change_id,image_change_result,image_change_image;
    static Context context;
    static final String CLIENT_ID = "U5StJhoEdYKC8ypzTZl7";
    static final String CLIENT_SECRET = "bLW9HPVZ3R";
    static final String CLIENT_NAME = "yjo0909";
    private static OAuthLogin naverLoginInstance;
    private RecyclerView recyclerView_post;
    private String id;
    private String no,num;
    boolean abc;
    private SwipeRefreshLayout mainBinding;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<String>mysqlcontent;
    private ArrayList<String>mysqlname;
    private String name2;
    private String name3;
    public static Context mcontext;
    private String good_num,good_id,good_no,good_numbar;
    Socket socket;
    DataOutputStream dataOutputStream;
    LinearLayout linearLayout;
    private Messenger mServiceMessenger = null;
    private boolean mIsBound;

    ArrayList<post_list_item> post_list_items = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);


        mcontext=this;
        abc = false;
        recyclerView_post = (RecyclerView) findViewById(R.id.posts_main);
        myprofile = (ImageView) findViewById(R.id.myprofile);
        mainBinding = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLo);
        post_new = (ImageView) findViewById(R.id.post_new);
        //button = (Button) findViewById(R.id.button2);
        user_id = (TextView) findViewById(R.id.user_id);
        friend_list_button = (ImageView) findViewById(R.id.friend_list);
        image_mychat = (ImageView) findViewById(R.id.mychat);
        linearLayout = (LinearLayout) findViewById(R.id.main_linear);

        final Intent intent = getIntent();
        final String name = intent.getStringExtra("navername");


        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();

        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        content();
        mainBinding.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {

                        content();
                        mainBinding.setRefreshing(false);

                    }
                }, 500);
            }

        });


        search = (EditText) findViewById(R.id.search);
        name2 = intent.getStringExtra("name2");
        ch = intent.getStringExtra("true");

        //new Tcpsocket(socket,name2).start();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(main_view.this,Searchfriend.class);
                intent1.putExtra("id",name2);
                startActivity(intent1);
            }
        });
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


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                }
        );

        btnLogout = (ImageView) findViewById(R.id.btnlogout);

        user_id.setText(name2);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        friend_list_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(main_view.this,Friend_list.class);
                intent1.putExtra("login",name2);
                intent1.putExtra("name",name3);
                intent1.putExtra("id",id);
                startActivity(intent1);
            }
        });


        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(main_view.this,Myprofile.class);
                intent1.putExtra("name",id);
                intent1.putExtra("no",no);
                intent1.putExtra("myname",name3);
                startActivity(intent1);
            }
        });

        image_mychat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_view.this,chat_list.class);
                intent.putExtra("id",name2);
                intent.putExtra("name",name3);
                startActivity(intent);
            }
        });


    }


    private void init(){
        context = this;
        naverLoginInstance = OAuthLogin.getInstance();
        naverLoginInstance.init(this,CLIENT_ID,CLIENT_SECRET,CLIENT_NAME);

    }
    public void content(){
        Intent intent = getIntent();
        userid = intent.getStringExtra("name2");
        mysqlcontent = new ArrayList<>();
        mysqlname = new ArrayList<>();
        //textView2 = (TextView) findViewById(R.id.textView2);
        recyclerView_post = (RecyclerView) findViewById(R.id.posts_main);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://13.209.17.127").build();
        Service service = restAdapter.create(Service.class);
        service.post_view(
                userid,
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

                                    num = subjsonObject.getString("no");
                                    String name = subjsonObject.getString("name");
                                    String content = subjsonObject.getString("content");
                                    String post_id = subjsonObject.getString("image");
                                    String id = subjsonObject.getString("id");
                                    String url = "http://13.209.17.127/imageupload/"+post_id;
                                    mysqlname.add(name);
                                    mysqlcontent.add(content);
                                    post_list_items.add(new post_list_item(name,content,url,id,num)); //해당 갯수 데이터 갯수만큼 리사이클러뷰 아이템 적용

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
    public class post_adapter extends RecyclerView.Adapter<post_adapter.ViewHolder>{ //어댑터
        ArrayList<post_list_item> mDataset;
        Context context;
        String id;
        boolean ab;
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://13.209.17.127").build();
        Service service = restAdapter.create(Service.class);
        private boolean a;
        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView textView,textView_email,textView_id,text_num,good_number;
            ImageView imageView,imageView2,imageView3,comment_button , image_change_notice ;
            String resut="false";
            private ImageView memu_button;
            LinearLayout linearLayout_good;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                image_change_notice = (ImageView) itemView.findViewById(R.id.image_change_notice);
                text_num = (TextView) itemView.findViewById(R.id.text_no);
                comment_button = (ImageView) itemView.findViewById(R.id.comment_button);
                textView = (TextView) itemView.findViewById(R.id.item_name_content);
                textView_email=(TextView) itemView.findViewById(R.id.item_content);
                imageView = (ImageView) itemView.findViewById(R.id.good);
                imageView2 = (ImageView) itemView.findViewById(R.id.good2);
                imageView3 = (ImageView) itemView.findViewById(R.id.profile_my);
                textView_id = (TextView) itemView.findViewById(R.id.text_id_item);
                good_number = (TextView) itemView.findViewById(R.id.good_number);
                linearLayout_good = (LinearLayout) itemView.findViewById(R.id.good_linear);
                memu_button = (ImageView) itemView.findViewById(R.id.memu_item_button);

                imageView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext() , profile.class);
                        Context context = v.getContext();
                        intent.putExtra("no",text_num.getText().toString());
                        intent.putExtra("id",name2);
                        intent.putExtra("prfile_id",textView_id.getText().toString());
                        intent.putExtra("name",textView.getText().toString());
                       /* service.image_change(
                                textView_id.getText().toString(),resut,
                                new retrofit.Callback<retrofit.client.Response>() {
                                    @Override
                                    public void success(retrofit.client.Response response, retrofit.client.Response response2) {
                                        BufferedReader reader = null;
                                        String a;
                                        try {
                                            reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                                            a = reader.readLine();

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    @Override
                                    public void failure(RetrofitError error) {

                                    }
                                }
                        );*/
                        context.startActivity(intent);
                    }
                });



                memu_button.setOnClickListener(new View.OnClickListener() { // 메뉴바
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(getApplicationContext(),v);
                        getMenuInflater().inflate(R.menu.memu,popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()){
                                    case R.id.update :
                                        Intent intent = new Intent(main_view.context , Post_update.class );
                                        intent.putExtra("content",textView_email.getText().toString());
                                        intent.putExtra("number",text_num.getText().toString());
                                        intent.putExtra("id",name2);
                                        startActivity(intent);
                                        break;
                                    case R.id.delete:
                                        //Toast.makeText(main_view.this, text_num.getText().toString(), Toast.LENGTH_SHORT).show();
                                        service.post_delete(
                                                text_num.getText().toString(),
                                                new retrofit.Callback<Response>() {
                                                    @Override
                                                    public void success(Response response, Response response2) {
                                                        BufferedReader reader = null;
                                                        String id1;
                                                        try {
                                                            reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                                                            id1 = reader.readLine();
                                                            //Toast.makeText(main_view.this, id1+"삭제 되었습니다", Toast.LENGTH_SHORT).show();


                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }

                                                    }

                                                    @Override
                                                    public void failure(RetrofitError error) {

                                                    }
                                                }

                                        );

                                        new Handler().postDelayed(new Runnable()
                                        {
                                            @Override
                                            public void run()
                                            {
                                                mDataset.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());
                                                notifyItemRangeChanged(getAdapterPosition(), mDataset.size());

                                            }
                                        }, 100);

                                        break;
                                    default:
                                        break;

                                }
                                return false;
                            }
                        });
                        popupMenu.show();
                    }
                });


                final String use_id = user_id.getText().toString();

                comment_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),Comment.class);
                        Context context = v.getContext();
                        intent.putExtra("no",text_num.getText().toString());
                        intent.putExtra("id",name2);
                        intent.putExtra("name",name3);
                        context.startActivity(intent);

                    }
                });

                linearLayout_good.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),good_friend_list.class);
                        Context context = v.getContext();
                        intent.putExtra("no",text_num.getText().toString());
                        intent.putExtra("id",name2);
                        context.startActivity(intent);
                    }
                });

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        a=true;
                        if(a){
                            service.good( //Service 객체에 name 이라는 클래스 호출
                                    use_id,text_num.getText().toString(),
                                    new retrofit.Callback<Response>() { // 레트로핏 사용

                                        @Override
                                        public void success(Response response, Response response2) {

                                            BufferedReader reader = null; //버퍼링 판독자
                                            String a;
                                            try {
                                                reader = new BufferedReader(new InputStreamReader(response.getBody().in())); //서버에 값을 보낸 부분

                                                a = reader.readLine();
                                                imageView2.setVisibility(View.VISIBLE);
                                                imageView.setVisibility(View.GONE);

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

                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                adapter.notifyDataSetChanged();
                            }
                        }, 100);
                    }
                });
                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        a=false;
                        if(!a){
                            service.good_down( //Service 객체에 name 이라는 클래스 호출
                                    use_id,text_num.getText().toString(),
                                    new retrofit.Callback<Response>() { // 레트로핏 사용

                                        @Override
                                        public void success(Response response, Response response2) {

                                            BufferedReader reader = null; //버퍼링 판독자
                                            String a;
                                            try {
                                                reader = new BufferedReader(new InputStreamReader(response.getBody().in())); //서버에 값을 보낸 부분

                                                a = reader.readLine();
                                                imageView2.setVisibility(View.GONE);
                                                imageView.setVisibility(View.VISIBLE);

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
                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                adapter.notifyDataSetChanged();
                            }
                        }, 100);

                    }

                });

            }
        }
        public post_adapter(ArrayList<post_list_item> myDataset){
            mDataset=myDataset;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.posts_item,viewGroup,false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

            viewHolder.textView.setText(mDataset.get(i).getPosts());
            viewHolder.textView_email.setText(mDataset.get(i).getName());
            viewHolder.textView_id.setText(mDataset.get(i).getId());
            viewHolder.text_num.setText(mDataset.get(i).getNo());
            service.image_change_notice(
                    name2,
                    new retrofit.Callback<retrofit.client.Response>() {
                        @Override
                        public void success(retrofit.client.Response response, retrofit.client.Response response2) {
                            BufferedReader reader = null;
                            String a;
                            try {
                                reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                                a = reader.readLine();
                                try{
                                    JSONObject jsonObject = new JSONObject(a); // json 사용방법
                                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                                    for(int i=0; i<jsonArray.length(); i++){
                                        JSONObject subjsonObject = jsonArray.getJSONObject(i);
                                        image_change_id = subjsonObject.getString("id");
                                        image_change_image = subjsonObject.getString("image");
                                        image_change_result = subjsonObject.getString("result_notice");

                                    }
                                    if(viewHolder.textView_id.getText().toString().equals(image_change_id)&&image_change_result.equals("true")){
                                        viewHolder.image_change_notice.setVisibility(View.VISIBLE);

                                    }

                                    if(abc){
                                        new Handler().postDelayed(new Runnable()
                                        {
                                            @Override
                                            public void run()
                                            {

                                                Glide.with(viewHolder.itemView.getContext())
                                                        .load(mDataset.get(i).getImages())
                                                        .clone()
                                                        .apply(new RequestOptions().centerCrop().circleCrop())
                                                        //.override(50,40)
                                                        .skipMemoryCache (true)
                                                        .error(R.drawable.baseline_account_circle_black_18dp)
                                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                                        //.thumbnail(0.5f)
                                                        .into(viewHolder.imageView3);
                                                abc = false;
                                            }
                                        }, 300);

                                    }


                                    else {
                                        Glide.with(viewHolder.itemView.getContext())
                                                .load(mDataset.get(i).getImages())
                                                .clone()
                                                .apply(new RequestOptions().centerCrop().circleCrop())
                                                //.override(50,40)
                                                //.skipMemoryCache (false)
                                                .error(R.drawable.baseline_account_circle_black_18dp)
                                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                                //.thumbnail(0.5f)
                                                .into(viewHolder.imageView3);

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

            if(name2.equals(viewHolder.textView_id.getText().toString())){
                viewHolder.memu_button.setVisibility(View.VISIBLE);
            }
            else{
                viewHolder.memu_button.setVisibility(View.GONE);
            }

            int itemCount = adapter.getItemCount();
            int tsum=0;
            int price = Integer.parseInt(mDataset.get(i).getNo());
            for (int j = 0; j < itemCount; j++){
                tsum = 0;
                tsum = tsum + price;

            }
            Log.d("total pay : ", String.valueOf(tsum));
            service.good_check( //Service 객체에 name 이라는 클래스 호출
                    user_id.getText().toString(),
                    new retrofit.Callback<Response>() { // 레트로핏 사용

                        @Override
                        public void success(Response response, Response response2) {

                            BufferedReader reader = null; //버퍼링 판독자
                            String a;
                            try {
                                reader = new BufferedReader(new InputStreamReader(response.getBody().in())); //서버에 값을 보낸 부분
                                a = reader.readLine();

                                try{
                                    JSONObject jsonObject = new JSONObject(a); // json 사용방법
                                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                                    for(int i=0; i<jsonArray.length(); i++){
                                        JSONObject subjsonObject = jsonArray.getJSONObject(i);

                                        good_num = subjsonObject.getString("num");
                                        good_id = subjsonObject.getString("id");
                                        good_no = subjsonObject.getString("goodnum");

                                        getItemCount();
                                        if(viewHolder.text_num.getText().toString().equals(good_num)&&good_no.equals("1")){
                                            viewHolder.imageView2.setVisibility(View.VISIBLE);
                                            viewHolder.imageView.setVisibility(View.GONE);
                                            return;
                                        }
                                        else{
                                            viewHolder.imageView2.setVisibility(View.GONE);
                                            viewHolder.imageView.setVisibility(View.VISIBLE);
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

            service.post_good_numbar( //Service 객체에 name 이라는 클래스 호출
                    String.valueOf(tsum),
                    new retrofit.Callback<Response>() { // 레트로핏 사용

                        @Override
                        public void success(Response response, Response response2) {

                            BufferedReader reader = null; //버퍼링 판독자
                            String a;
                            try {
                                reader = new BufferedReader(new InputStreamReader(response.getBody().in())); //서버에 값을 보낸 부분
                                a = reader.readLine();

                                try{
                                    JSONObject jsonObject = new JSONObject(a); // json 사용방법
                                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                                    for(int i=0; i<jsonArray.length(); i++){
                                        JSONObject subjsonObject = jsonArray.getJSONObject(i);

                                        good_numbar = subjsonObject.getString("good_num");

                                    }
                                    viewHolder.good_number.setText(good_numbar);

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


    /** 서비스 정지 */
   /* private void setStopService() {
        if (mIsBound) {
            unbindService(mConnection);
            mIsBound = false;
        }
        stopService(new Intent(main_view.this, Sevrice_back.class));
    }

    public void in(){ //아이디 값 보내는 부분
        Intent intent = new Intent(this, Sevrice_back.class);
        intent.putExtra("login", name2);
        startService(intent);

    }
    private void setStartService() { //서비스 시작하는부분
        bindService(new Intent(this, Sevrice_back.class), mConnection, Context.BIND_AUTO_CREATE);
        in();
        mIsBound = true;
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //서비스 연결 부분
            mServiceMessenger = new Messenger(iBinder);
            try {
                Message msg = Message.obtain(null, Sevrice_back.MSG_REGISTER_CLIENT);
                msg.replyTo = mMessenger;
                mServiceMessenger.send(msg);
            }
            catch (RemoteException e) {
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };
    /** Service 로 부터 message를 받음 */
    /*private final Messenger mMessenger = new Messenger(new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case Sevrice_back.MSG_SEND_TO_ACTIVITY:
                    //message = msg.getData().getString("test");
                    //user_id = msg.getData().getString("user_id");

                    break;
            }
            return false;
        }
    }));*/






   /* @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                conte

                nt();
                abc = true;
            }
        }, 300);

    }*/


}


