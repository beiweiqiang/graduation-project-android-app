package com.github.chagall.notificationlistenerexample;

import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import model.Message;
import okhttp3.Call;
import okhttp3.MediaType;

public class NotificationListenerExampleService extends NotificationListenerService {
  private static final String TAG = "NotificationListenerExa";
  private static final String url = "https://www.beiweiqiang.com/graduation/chattingHistory/save";
  private String userId;

  @Override
  public IBinder onBind(Intent intent) {
    Log.d(TAG, "onBind:  ");
//    userId = intent.getStringExtra("userId");
//    Log.d(TAG, "34 -> " + "onBind: " + userId);
    return super.onBind(intent);
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    this.userId = intent.getStringExtra("userId");
    Log.d(TAG, "41 -> " + "onStartCommand: " + userId);

    super.onStartCommand(intent, flags, startId);
    return START_STICKY;
  }

  @Override
  public void onNotificationPosted(StatusBarNotification sbn) {
    Log.d(TAG, "55 -> " + "onNotificationPosted: " + sbn.toString());
    if (sbn.getPackageName() != null) {
      Log.d(TAG, "onNotificationPosted: " + sbn.getPackageName());
      String packageName = sbn.getPackageName();
      String title = sbn.getNotification().extras.getString("android.title");
      String text = sbn.getNotification().extras.getString("android.text");
//      如果是微信的消息就拦截并发送到server
      if (packageName.equals("com.tencent.mm")) {
//        发送消息到后台
        Log.d(TAG, "45 -> " + "onNotificationPosted: " + title);
        Log.d(TAG, "46 -> " + "onNotificationPosted: " + text);
//        发送消息到server
        OkHttpUtils
            .postString()
            .url(url)
            .content(new Gson().toJson(new Message(this.userId, title, text)))
            .mediaType(MediaType.parse("application/json; charset=utf-8"))
            .build()
            .execute(new MyStringCallback());
      }
    }
  }

  public class MyStringCallback extends StringCallback {
    @Override
    public void onError(Call call, Exception e, int id) {

    }

    @Override
    public void onResponse(String response, int id) {
      Log.d(TAG, "67 -> " + "onResponse: " + response);
    }
  }

  @Override
  public void onNotificationRemoved(StatusBarNotification sbn) {
  }
}
