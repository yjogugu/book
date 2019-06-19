package com.example.yjo.book;

import android.os.Handler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Tcpsocket extends Thread{
    DataOutputStream dataOutputStream;
    static Socket soc;
    String sid;


    public Tcpsocket(Socket socket,String id) {
        this.soc = socket;
        this.sid = id;
        new Thread(){
            public void run(){
                    try {
                        //소켓을 생성하고 입출력 스트립을 소켓에 연결한다.
                        soc = new Socket("192.168.0.29",5776); // ip랑 소켓 번호 설정
                        dataOutputStream = new DataOutputStream(soc.getOutputStream());//Dataoutput 초기화
                        dataOutputStream.writeUTF(sid);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }

        }.start();

    }

}
