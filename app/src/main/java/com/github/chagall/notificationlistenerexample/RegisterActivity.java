package com.github.chagall.notificationlistenerexample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import model.User;
import okhttp3.Call;
import okhttp3.MediaType;

public class RegisterActivity extends AppCompatActivity {
  private static final String TAG = "RegisterActivity";
  private static final String url = "https://www.beiweiqiang.com/graduation/userRegister";
  int duration = Toast.LENGTH_SHORT;

  private void intentToLogin() {
    Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
    RegisterActivity.this.startActivity(loginIntent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    final Context context = getApplicationContext();

    final EditText etUsername = (EditText) findViewById(R.id.etUsername);
    final EditText etPassword = (EditText) findViewById(R.id.etPassword);
    final EditText etConfirm = (EditText) findViewById(R.id.confirmPassword);
    final Button bRegister = (Button) findViewById(R.id.bRegister);
    final TextView loginLink = (TextView) findViewById(R.id.loginLink);

    //    跳转到登录页
    loginLink.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        intentToLogin();
      }
    });

    bRegister.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//        拿到用户的输入值
        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();
        final String confirmPassword = etConfirm.getText().toString();

        if (!password.equals(confirmPassword)) {
          Toast.makeText(context, "密码不一致", duration).show();
        } else {
          OkHttpUtils
              .postString()
              .url(url)
              .content(new Gson().toJson(new User(username, password)))
              .mediaType(MediaType.parse("application/json; charset=utf-8"))
              .build()
              .execute(new MyStringCallback());
        }

      }
    });
  }

  public class MyStringCallback extends StringCallback {
    final Context context = getApplicationContext();
    @Override
    public void onError(Call call, Exception e, int id) {

    }

    @Override
    public void onResponse(String response, int id) {
      Log.d(TAG, "onResponse: " + response);
      Map<String, Object> m = new Gson().fromJson(
          response, new TypeToken<HashMap<String, Object>>() {}.getType()
      );
      Double d = (Double) m.get("code");
      int code = d.intValue();
      Log.d(TAG, "onResponse: " + code);
      if (code == 200) {
        Toast.makeText(context, "注册成功", duration).show();
        intentToLogin();
      } else {
        Toast.makeText(context, "账号已被注册", duration).show();
      }
    }
  }
}
