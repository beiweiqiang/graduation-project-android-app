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

  @Override
  public IBinder onBind(Intent intent) {
    Log.d(TAG, "onBind:  ");
    return super.onBind(intent);
  }

  @Override
  public void onNotificationPosted(StatusBarNotification sbn) {
    if (sbn.getPackageName() != null) {
      Log.d(TAG, "onNotificationPosted: " + sbn.getPackageName());
      String packageName = sbn.getPackageName();
      String title = sbn.getNotification().extras.getString("android.title");
      String text = sbn.getNotification().extras.getString("android.text");
//      如果是微信的消息就拦截并发送到server
      if (packageName.equals("com.tencent.mm")) {
//        发送消息到后台
        Log.d(TAG, "onNotificationPosted: " + "title -> " + title);
        Log.d(TAG, "onNotificationPosted: " + "text -> " + text);
//        发送消息到server
        OkHttpUtils
            .postString()
            .url(url)
            .content(new Gson().toJson(new Message(1, title, text)))
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
      Log.d(TAG, "onResponse: " + response);
    }
  }

  @Override
  public void onNotificationRemoved(StatusBarNotification sbn) {
  }
}
