package com.example.leehs.yottaproject06.ForSettings;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leehs.yottaproject06.InsertDataToServer.InsertActivity;
import com.example.leehs.yottaproject06.R;

/*
제품 문의사항이나 가격 신고등을 위한 액티비티입니다.
아직 제대로 구현된 기능은 없습니다.
 */
public class NotifyActivity extends AppCompatActivity {

    ImageView yNotifyImageView;

    TextView yNotify_yotta, yNotify_name, yNotify_call, yNotify_email;
    Button yCallBtn, yReportBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        yNotifyImageView = (ImageView)findViewById(R.id.yNotifyImageView);
        yNotifyImageView.setBackgroundDrawable(new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.logo2)));

        yCallBtn = (Button) findViewById(R.id.yCallBtn);
        yReportBtn = (Button) findViewById(R.id.yReportBtn);

        yCallBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:1588-1577"));
                if (ActivityCompat.checkSelfPermission(NotifyActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
            }
        });

        yReportBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                String[] mailaddr = {"yotta@google.com"};

                intent.setType("plaine/text");
                intent.putExtra(Intent.EXTRA_EMAIL, mailaddr); // 받는사람

                startActivity(intent);
            }
        });
    }

    public void BtnInertOnClik(View v){
        Intent intent = new Intent(NotifyActivity.this,  InsertActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recycleView(findViewById(R.id.yNotifyImageView));
    }

    private void recycleView(View view) {
        if(view != null) {
            Drawable bg = view.getBackground();
            if(bg != null) {
                bg.setCallback(null);
                ((BitmapDrawable)bg).getBitmap().recycle();
                view.setBackgroundDrawable(null);
            }
        }
    }
}