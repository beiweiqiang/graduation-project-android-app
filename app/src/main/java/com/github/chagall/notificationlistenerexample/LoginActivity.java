package com.github.chagall.notificationlistenerexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import model.User;
import okhttp3.Call;
import okhttp3.MediaType;

public class LoginActivity extends AppCompatActivity {
  private static final String TAG = "LoginActivity";
  private static final String url = "https://www.beiweiqiang.com/graduation/userLogin";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    final EditText etUsername = (EditText) findViewById(R.id.etUsername);
    final EditText etPassword = (EditText) findViewById(R.id.etPassword);
    final TextView tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);
    final Button bLogin = (Button) findViewById(R.id.bSignIn);

//    跳转到注册页
    tvRegisterLink.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        LoginActivity.this.startActivity(registerIntent);
      }
    });

    bLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();

        OkHttpUtils
            .postString()
            .url(url)
            .content(new Gson().toJson(new User(username, password)))
            .mediaType(MediaType.parse("application/json; charset=utf-8"))
            .build()
            .execute(new MyStringCallback());

      }
    });
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
}
