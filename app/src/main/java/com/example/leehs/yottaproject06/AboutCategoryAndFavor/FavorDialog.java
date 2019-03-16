package com.example.leehs.yottaproject06.AboutCategoryAndFavor;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.leehs.yottaproject06.InfoActivity;
import com.example.leehs.yottaproject06.R;

/**
 * Created by IT on 2016-01-26.
 */
public class FavorDialog extends Activity {

    private Button btnFavor1, btnFavor2, btnFavor3;
    private LinearLayout yFavorDialogLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favordialog);

        //다이얼로그를 띄우고 밖을 클릭했을 시 꺼지는 것을 방지함.
        this.setFinishOnTouchOutside(false);

        yFavorDialogLayout = (LinearLayout)findViewById(R.id.yFavorDialogLayout);
        yFavorDialogLayout.setBackgroundDrawable(new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.favortitle)));


        btnFavor1 =(Button)findViewById(R.id.fdialog1);
        btnFavor2 =(Button)findViewById(R.id.fdialog2);
        btnFavor3 =(Button)findViewById(R.id.fdialog3);


        if(InfoActivity.Dial[0]==1) {
            btnFavor1.setBackgroundResource(R.drawable.favor_select);
        }
        if(InfoActivity.Dial[1]==2) {
            btnFavor2.setBackgroundResource(R.drawable.favor_select);
        }
        if(InfoActivity.Dial[2]==3) {
            btnFavor3.setBackgroundResource(R.drawable.favor_select);
        }
    }

    /*
    종료 버튼입니다.
     */
    public void btnFinishOnClick(View v) {
        finish();
    }

    public void btnNum1(View v)
    {

        InfoActivity.favorDialognum=1;
        btnFavor1.setBackgroundResource(R.drawable.favor_select);
        finish();

    }
    public void btnNum2(View v)
    {
        InfoActivity.favorDialognum=2;
        btnFavor2.setBackgroundResource(R.drawable.favor_select);
        finish();
    }
    public void btnNum3(View v)
    {
        InfoActivity.favorDialognum=3;
        btnFavor3.setBackgroundResource(R.drawable.favor_select);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recycleView(findViewById(R.id.yFavorDialogLayout));
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