package com.example.yjo.book;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import static android.support.constraint.Constraints.TAG;

public class Sever_Service extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        // Service 객체와 화면 Activity 사이에서 데이터를 주고받을 때
        // 데이터를 전달할 필요가 없으면 return null;
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "서비스 시작");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "서비스 종료");
        super.onDestroy();
    }


}
