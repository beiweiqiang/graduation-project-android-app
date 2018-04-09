package com.github.chagall.notificationlistenerexample;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

  private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
  private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";

  private AlertDialog enableNotificationListenerAlertDialog;
  private static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    Intent intent = getIntent();
    String userId = intent.getStringExtra("userId");
    Log.d(TAG, "26 -> " + "onCreate: " + userId);

    Intent mIntent = new Intent(getApplicationContext(), NotificationListenerExampleService.class);
    mIntent.putExtra("userId", userId);
    startService(mIntent);

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // If the user did not turn the notification listener service on we prompt him to do so
    if (!isNotificationServiceEnabled()) {
      enableNotificationListenerAlertDialog = buildNotificationServiceAlertDialog();
      enableNotificationListenerAlertDialog.show();
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  private boolean isNotificationServiceEnabled() {
    String pkgName = getPackageName();
    final String flat = Settings.Secure.getString(getContentResolver(),
            ENABLED_NOTIFICATION_LISTENERS);
    Log.d(TAG, "144 -> " + "isNotificationServiceEnabled: " + flat);
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
