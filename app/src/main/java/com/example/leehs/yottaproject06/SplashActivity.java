package com.example.leehs.yottaproject06;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Lee HS on 2015-12-26.
 */

/*
어플리케이션이 처음 켜졌을 때
스플래시 이미지를 2초간 보여줍니다.
 */
public class SplashActivity extends Activity{


    // 애플리케이션 전체에서 사용할 값
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor sharedEditor;
    String join;

    ToastMessage yToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = getSharedPreferences("pref", Activity.MODE_PRIVATE);//폰 메모리에 저장
        sharedEditor = sharedPref.edit();

        join=sharedPref.getString("join", "");

        Log.d("join","join"+join);
        try
        {
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        if(join.equals("")) {
            startActivity(new Intent(this, JoinActivity.class));
        }
        else{
            yToast = new ToastMessage(SplashActivity.this);
            yToast.showToast("로그인에 성공했습니다!", Toast.LENGTH_SHORT);
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }
}
