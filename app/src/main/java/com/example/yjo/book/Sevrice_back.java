package com.example.yjo.book;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Sevrice_back extends Service {
    public static final int MSG_REGISTER_CLIENT = 1; //등록된 클라이언트
    Socket socket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    private static final String TAG = "MyService";
    String result=null;
    public static final int MSG_SEND_TO_SERVICE = 3; // 서비스로 보내는 변수
    public static final int MSG_SEND_TO_ACTIVITY = 4; //액티비티로 보내는 변수
    String a = "23";
    String b, tcp_po,tcp_id;
    private Messenger mClient = null;   // Activity 에서 가져온 Messenger
    @Override
    public void onCreate() { //생성
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // 여기서 처리 한다. 시스템 특성상 아래 특징 확인
        if(intent == null){
            return Service.START_STICKY;
            // 끈적끈적하게 계쏙 서비스가 종료되더라도 자동으로 실행되도록한다.
        }else{          // Null이 아닐경우
            processCommand(intent); // 메소드를 분리하는게 보기가 좋다. 여기서 처리한다.
        }

        return super.onStartCommand(intent, flags, startId);

    }


    public void tcp(final String srt){ //소켓 연결부분
        Thread thread = new Thread() { //스레드 선언
            public void run() {
                try {
                    socket = new Socket("192.168.0.15", 5776); //
                    //소켓을 생성하고 입출력 스트립을 소켓에 연결한다.
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());//Dataoutput 초기화
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    dataOutputStream.writeUTF(srt);

                } catch (IOException e) {
                    Log.e("dqwdwq", "jjjjjjj");
                }
                try {
                    while (true) {
                        try {
                            b = dataInputStream.readUTF();
                            try {
                                JSONArray obj = new JSONArray(b);
                                for (int j = 0; j < obj.length(); j++) {
                                    JSONObject subjsonObject = obj.getJSONObject(j);
                                    tcp_id = subjsonObject.getString("id");
                                    tcp_po = subjsonObject.getString("message");
                                }

                                sendMsgToActivity(tcp_po,tcp_id);

                            } catch (JSONException e) {
                                //e.printStackTrace();
                                System.out.print(e);
                            }
                        } catch (IOException e) {
                            //break;
                            //Log.e("dqwdwq", "dqwdwq");
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }


    private void processCommand(Intent intent){
        // 전달 받은 데이터 찍기 위함.
        result = intent.getStringExtra("login"); // command 는 구분 하기 위한것
        tcp(result);

    }

    @Override
    public IBinder onBind(Intent intent) {
        //return null;
        return mMessenger.getBinder();
    }



    /** activity로부터 binding 된 Messenger */
    private final Messenger mMessenger = new Messenger(new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.w("test","ControlService - message what : "+msg.what +" , msg.obj12321323 "+ msg.obj);
            switch (msg.what) {
                case MSG_REGISTER_CLIENT:
                    mClient = msg.replyTo;  // activity로부터 가져온
                    break;
            }
            return false;
        }
    }));

    private void sendMsgToActivity(String message ,String user) { //메세지 보내는 부분
        try {
            Bundle bundle = new Bundle(); //번들 선언 초기화
            bundle.putString("test",message); //인텐트 형식으로 key value 선언
            bundle.putString("user_id",user); //인텐트 형식으로 key value 선언
            Message msg = Message.obtain(null, MSG_SEND_TO_ACTIVITY);//메신저 클라이언트 측으로 보내기
            msg.setData(bundle);
            mClient.send(msg);      // msg 보내기
        } catch (RemoteException e) {
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
