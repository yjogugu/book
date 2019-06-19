package com.example.yjo.book;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class chat_list extends AppCompatActivity {
    String login_id,sh_id;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<chat_list_item> recycleritems = null;
    private Messenger mServiceMessenger = null;
    private boolean mIsBound;
    String a = "1";
    String message,user_id;
    ArrayList<String> chat;
    ArrayList<chat_list_arry> ee = new ArrayList<>();
    SharedPreferences sharedPreferences;
    String sh,sh1;
    String meassge;
    String image_url="http://13.209.17.127/imageupload/uploads/vwvw22@naver.com.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        Intent intent = getIntent();
        login_id= intent.getStringExtra("id");
        setStartService();
        chat = new ArrayList<String>();
        /* 리사이클러뷰 부분 */
        recycleritems = new ArrayList<chat_list_item>();
        recyclerView = (RecyclerView) findViewById(R.id.chat_list_item_recyclerView);
        sharedPreferences = getSharedPreferences(login_id,Context.MODE_PRIVATE);
        sh1 = sharedPreferences.getString("chat_list_app","");
        sh=sharedPreferences.getString("chat_list", ""); //key, value(defaults)w();

        try {
            JSONArray jsonArray = new JSONArray(sh); //sh 담겨져 있는 json 형태 풀기
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject subjsonObject = jsonArray.getJSONObject(i);
                sh_id = subjsonObject.getString("Userid");
                meassge = subjsonObject.getString("Usermessage");
            }

            recycleritems.add(new chat_list_item(sh_id,sh1,image_url)); //리사이클러뷰 풀음
            ee.add(new chat_list_arry(sh_id,sh1)); // cd에 다음 내용들을 ee 어레이리스트에 담음

        } catch (JSONException e) {
            Log.e("1111111","222222222");
        }

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(chat_list.this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new Chat_list_adapter(recycleritems,login_id,chat);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                sh1 = sharedPreferences.getString("chat_list_app","");
                //Toast.makeText(chat_list.this, sh1+"", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        }, 100);
    }

    @Override
    protected void onPause() {
        super.onPause();
        setStopService();
        JSONArray jsonArray = new JSONArray();
        if(ee.size()>0){ //ee 어레이리스트에 데이터가 1개이상 존재하면 진행
            try {
                for(int i =0; i<ee.size(); i++){
                    JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                    sObject.put("Userid", ee.get(i).id);
                    sObject.put("Usermessage",ee.get(i).list_meassge);
                    jsonArray.put(sObject);
                }

            }catch (JSONException e) {
                e.printStackTrace();
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("chat_list",jsonArray.toString()).apply();
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

    @Override
    protected void onStop() {
        super.onStop();
        setStopService();
    }

    /** 서비스 정지 */
    private void setStopService() {
        if (mIsBound) {
            unbindService(mConnection);
            mIsBound = false;
        }
        stopService(new Intent(chat_list.this, Sevrice_back.class));
    }

    public void in(){ //아이디 값 보내는 부분
        Intent intent = new Intent(this, Sevrice_back.class);
        intent.putExtra("login", login_id);
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
    private final Messenger mMessenger = new Messenger(new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {

                case Sevrice_back.MSG_SEND_TO_ACTIVITY:
                    message = msg.getData().getString("test");
                    user_id = msg.getData().getString("user_id");

                    if(message!=null){
                        recycleritems = new ArrayList<chat_list_item>();
                        recycleritems.add(new chat_list_item(user_id,message,image_url+user_id));
                        chat.add(message);
                        ee.add(new chat_list_arry(user_id,message));
                        recyclerView.setHasFixedSize(true);

                        layoutManager = new LinearLayoutManager(chat_list.this);
                        recyclerView.setLayoutManager(layoutManager);

                        adapter = new Chat_list_adapter(recycleritems,login_id,chat);
                        recyclerView.setAdapter(adapter);
                    }
                    break;
            }
            return false;
        }

    }));


}
class chat_list_arry{
    String id;
    String list_meassge;

    public chat_list_arry(String a , String b){
        id = a;
        list_meassge = b;
    }
}


