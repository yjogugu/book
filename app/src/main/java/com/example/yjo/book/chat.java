package com.example.yjo.book;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public class chat extends AppCompatActivity {
    String id;
    String text;
    Socket socket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    EditText editText;
    TextView textView;
    Button button;
    String tcp_id,tcp_message,login_id;
    private ArrayList<chat_item> recycleritems = null;
    ArrayList<String> chat;
    String user_id;
    String chat_add =null;
    boolean result;
    ArrayList<String>info;
    SharedPreferences sharedPreferences;
    ArrayList<item> allarry = new ArrayList<>();
    String json_message;
    String sh;
    String userid,usermessage;
    ArrayList<String>message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        id= intent.getStringExtra("id");
        login_id = intent.getStringExtra("login");
        chat =new ArrayList<String>();
        chat = intent.getStringArrayListExtra("arry");
        user_id = intent.getStringExtra("user");
        editText = (EditText) findViewById(R.id.chat_message_edittext);
        button = (Button) findViewById(R.id.send_message);
        recyclerView = (RecyclerView) findViewById(R.id.chat_recyclerView);
        textView = (TextView) findViewById(R.id.textView9);
        recycleritems = new ArrayList<chat_item>(); // 리사이클러뷰 어레이 리스트 초기화
        info = new ArrayList<String>();
        message = new ArrayList<String>();

        //엑티비티에서 생기는 모든 데이터를 담는 어레이리스트
        sharedPreferences = getSharedPreferences(login_id, Context.MODE_PRIVATE);
        sh=sharedPreferences.getString("chat_post", ""); //key, value(defaults)w();


        if(sh!=null){
            try {
                JSONArray jsonArray = new JSONArray(sh);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject subjsonObject = jsonArray.getJSONObject(i);
                    userid = subjsonObject.getString("Userid");
                    usermessage = subjsonObject.getString("Usermessage");
                    recycleritems.add(new chat_item(userid,usermessage));
                    allarry.add(new item(userid,usermessage));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.e("11111",chat+"");
        if(chat!=null){
            for (int i = 0; i < chat.size(); i++) { //인텐트로 받아온 arry 내용을 풀음
                json_message = chat.get(i);
                recycleritems.add(new chat_item(user_id, json_message)); //리사이클러뷰 츄가
                allarry.add(new item(user_id,json_message));
            }
        }

        button.setOnClickListener(new View.OnClickListener() { //채팅 보내기
            @Override
            public void onClick(View v) {
                if(result){
                    Log.e("dwq","서버 연결 성공");
                    try {
                        dataOutputStream = new DataOutputStream(socket.getOutputStream());//Dataoutput 초기화
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    new Thread(){
                        public void run(){
                            try {
                                dataOutputStream.writeUTF(editText.getText().toString());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    editText.setText("");
                }
                else{
                    Log.e("dwq","서버 연결 실패");
                    Toast.makeText(chat.this, "실패", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Thread worker = new Thread() { //스레드 선언
            public void run() {
                try {
                    socket = new Socket("192.168.0.15", 5776); //
                    result = socket.isConnected();
                    if(result){
                        Log.e("dwq","서버 연결 성공");
                        dataOutputStream = new DataOutputStream(socket.getOutputStream());//Dataoutput 초기화
                        dataInputStream = new DataInputStream(socket.getInputStream());//Datainput 초기화
                        dataOutputStream.writeUTF(login_id);
                    }

                    else{
                        Log.e("dwq","서버 연결 실패");
                        //return;
                    }
                     }catch (IOException e) {
                    e.printStackTrace();
                }

                try {

                        while(true){
                            if(result){
                            try {
                                text = dataInputStream.readUTF();
                                textView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {

                                            JSONArray obj = new JSONArray(text);
                                            //JSONObject jsonObject = new JSONObject(text); // json 사용방법
                                            //JSONArray jsonArray = obj.getJSONArray("tcp");
                                            for (int j = 0; j < obj.length(); j++) {
                                                JSONObject subjsonObject = obj.getJSONObject(j);
                                                tcp_id = subjsonObject.getString("id");
                                                tcp_message = subjsonObject.getString("message");
                                            }
                                            recycleritems.add(new chat_item(tcp_id, tcp_message));//리사이클러뷰 추가
                                            adapter.notifyDataSetChanged();
                                            allarry.add(new item(tcp_id,tcp_message));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            System.out.print(e);
                                            Log.e("qwqw", "22");
                                        }

                                    }
                                });

                            } catch (IOException e) {
                                break;
                            }

                        }
                    }


                }catch (Exception e){
                    Log.e("qqqq","오류2222");
                }
            }

        };
        worker.start(); // 뷰가 생성 되자마자 자바 서버에 소켓 연결

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(chat.this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new chat_adapter(recycleritems,login_id);
        recyclerView.setAdapter(adapter);
        if(adapter.getItemCount()>0){
            recyclerView.smoothScrollToPosition(adapter.getItemCount()-1);
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        JSONArray jsonArray = new JSONArray();
        try {
            if(result) {
                socket.close(); //소켓을 닫는다.
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(allarry.size()>0){
            try {
                for(int i =0; i<allarry.size(); i++){
                    JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                    sObject.put("Userid", allarry.get(i).id);
                    sObject.put("Usermessage",allarry.get(i).message);
                    chat_add = allarry.get(i).message;
                    jsonArray.put(sObject);
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("chat_post", jsonArray.toString());
            editor.putString("chat_list_app", chat_add);
            editor.apply();
        }

    }

    @Override
    protected void onStop() {  //앱 종료시
        super.onStop();
        try {
            if(result) {
                socket.close(); //소켓을 닫는다.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            if(result) {
                socket.close(); //소켓을 닫는다.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class item{
    String id;
    String message;


    public item(String a , String b ){
        id = a;
        message = b;


    }
}



