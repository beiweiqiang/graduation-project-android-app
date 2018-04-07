package com.github.chagall.notificationlistenerexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import org.json.JSONObject;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;


import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

  private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
  private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";

  //  private ImageView interceptedNotificationImageView;
//  private ImageChangeBroadcastReceiver imageChangeBroadcastReceiver;
  private AlertDialog enableNotificationListenerAlertDialog;

  private static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Here we get a reference to the image we will modify when a notification is received
//    interceptedNotificationImageView
//            = (ImageView) this.findViewById(R.id.intercepted_notification_logo);

    // If the user did not turn the notification listener service on we prompt him to do so
    if (!isNotificationServiceEnabled()) {
      enableNotificationListenerAlertDialog = buildNotificationServiceAlertDialog();
      enableNotificationListenerAlertDialog.show();
    }

    // Finally we register a receiver to tell the MainActivity when a notification has been received
//    imageChangeBroadcastReceiver = new ImageChangeBroadcastReceiver();
//    IntentFilter intentFilter = new IntentFilter();
//    intentFilter.addAction("com.github.chagall.notificationlistenerexample");
//    registerReceiver(imageChangeBroadcastReceiver, intentFilter);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
//    unregisterReceiver(imageChangeBroadcastReceiver);
  }

  //  检查网络是否连接
  public boolean isConnected() {
    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected())
      return true;
    else
      return false;
  }

//  public static String POST(String url, Person person) {
//    InputStream inputStream = null;
//    String result = "";
//    try {
//
//      // 1. create HttpClient
//      HttpClient httpclient = new DefaultHttpClient();
//
//      // 2. make POST request to the given URL
//      HttpPost httpPost = new HttpPost(url);
//
//      String json = "";
//
//
//      // 3. build jsonObject
//      JSONObject jsonObject = new JSONObject();
//      jsonObject.accumulate("name", person.getName());
//      jsonObject.accumulate("country", person.getCountry());
//      jsonObject.accumulate("twitter", person.getTwitter());
//
//      // 4. convert JSONObject to JSON to String
//      json = jsonObject.toString();
//
//
//      // ** Alternative way to convert Person object to JSON string usin Jackson Lib
//      // ObjectMapper mapper = new ObjectMapper();
//      // json = mapper.writeValueAsString(person);
//
//      // 5. set json to StringEntity
//      StringEntity se = new StringEntity(json);
//
//      // 6. set httpPost Entity
//      httpPost.setEntity(se);
//
//      // 7. Set some headers to inform server about the type of the content
//      httpPost.setHeader("Accept", "application/json");
//      httpPost.setHeader("Content-type", "application/json");
//
//      // 8. Execute POST request to the given URL
//      HttpResponse httpResponse = httpclient.execute(httpPost);
//
//      // 9. receive response as inputStream
//      inputStream = httpResponse.getEntity().getContent();
//
//
//      // 10. convert inputstream to string
//      if (inputStream != null)
//        result = convertInputStreamToString(inputStream);
//      else
//        result = "Did not work!";
//
//    } catch (Exception e) {
//      Log.d("InputStream", e.getLocalizedMessage());
//    }
//
//    // 11. return result
//    return result;
//  }

  /**
   * Change Intercepted Notification Image
   * Changes the MainActivity image based on which notification was intercepted
   *
   * @param notificationCode The intercepted notification code
   */
//  private void changeInterceptedNotificationImage(int notificationCode) {
//    switch (notificationCode) {
//      case NotificationListenerExampleService.InterceptedNotificationCode.FACEBOOK_CODE:
//        interceptedNotificationImageView.setImageResource(R.drawable.facebook_logo);
//        break;
//      case NotificationListenerExampleService.InterceptedNotificationCode.INSTAGRAM_CODE:
//        interceptedNotificationImageView.setImageResource(R.drawable.instagram_logo);
//        break;
//      case NotificationListenerExampleService.InterceptedNotificationCode.WHATSAPP_CODE:
//        interceptedNotificationImageView.setImageResource(R.drawable.whatsapp_logo);
//        break;
//      case NotificationListenerExampleService.InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE:
//        interceptedNotificationImageView.setImageResource(R.drawable.other_notification_logo);
//        break;
//    }
//  }

  /**
   * Is Notification Service Enabled.
   * Verifies if the notification listener service is enabled.
   * Got it from: https://github.com/kpbird/NotificationListenerService-Example/blob/master/NLSExample/src/main/java/com/kpbird/nlsexample/NLService.java
   *
   * @return True if eanbled, false otherwise.
   */
  private boolean isNotificationServiceEnabled() {
    String pkgName = getPackageName();
    final String flat = Settings.Secure.getString(getContentResolver(),
            ENABLED_NOTIFICATION_LISTENERS);
    Log.d(TAG, "isNotificationServiceEnabled: " + flat);
    if (!TextUtils.isEmpty(flat)) {
      final String[] names = flat.split(":");
      for (int i = 0; i < names.length; i++) {
        final ComponentName cn = ComponentName.unflattenFromString(names[i]);
        if (cn != null) {
          if (TextUtils.equals(pkgName, cn.getPackageName())) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Image Change Broadcast Receiver.
   * We use this Broadcast Receiver to notify the Main Activity when
   * a new notification has arrived, so it can properly change the
   * notification image
   */
//  public class ImageChangeBroadcastReceiver extends BroadcastReceiver {
//    @Override
//    public void onReceive(Context context, Intent intent) {
//      int receivedNotificationCode = intent.getIntExtra("Notification Code", -1);
//      changeInterceptedNotificationImage(receivedNotificationCode);
//    }
//  }


  /**
   * Build Notification Listener Alert Dialog.
   * Builds the alert dialog that pops up if the user has not turned
   * the Notification Listener Service on yet.
   *
   * @return An alert dialog which leads to the notification enabling screen
   */
  private AlertDialog buildNotificationServiceAlertDialog() {
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    alertDialogBuilder.setTitle(R.string.notification_listener_service);
    alertDialogBuilder.setMessage(R.string.notification_listener_service_explanation);
    alertDialogBuilder.setPositiveButton(R.string.yes,
            new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
              }
            });
    alertDialogBuilder.setNegativeButton(R.string.no,
            new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
                // If you choose to not enable the notification listener
                // the app. will not work as expected
              }
            });
    return (alertDialogBuilder.create());
  }
}
