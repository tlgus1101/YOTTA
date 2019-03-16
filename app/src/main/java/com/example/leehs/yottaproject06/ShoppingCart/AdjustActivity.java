package com.example.leehs.yottaproject06.ShoppingCart;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leehs.yottaproject06.R;

/**
 * Created by MyCom on 2016-01-14.
 */

/*
     이 액티비티는 기존 장바구니에 담겨있는 물건의 수량을 수정하는 액티비티입니다.
 */
public class AdjustActivity extends Activity{
    int yGetPicker;
    LinearLayout yAdjustLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adjust_main);

        //다이얼로그를 띄우고 밖을 클릭했을 시 꺼지는 것을 방지함.
        this.setFinishOnTouchOutside(false);

        yAdjustLayout = (LinearLayout)findViewById(R.id.yAdjustLayout);
        yAdjustLayout.setBackgroundDrawable(new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.changeprice)));

        Intent intent = getIntent();

        TextView yTextName = (TextView)findViewById(R.id.yTextName);
        TextView yTextPrice = (TextView)findViewById(R.id.yTextPrice);

        Button yBtnCanel = (Button)findViewById(R.id.btnCanel);

        yTextName.setText(intent.getStringExtra("NAME"));
        yTextPrice.setText(intent.getStringExtra("PRICE") + "원");

        ImageView yImageView = (ImageView)findViewById(R.id.ImageViewInDia);
        yImageView.setImageBitmap((Bitmap) intent.getParcelableExtra("IMAGE"));

        int ySetPickerCount = Integer.parseInt(intent.getStringExtra("PICKER"));

        setupUI(ySetPickerCount);

        yBtnCanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void BtnSendOnClick(View v){
        Intent yResultIntent = new Intent();
        yResultIntent.putExtra("RESULT",""+yGetPicker);
        setResult(0, yResultIntent);
        finish();
    }

    public void setupUI(int ySetCount) {
        NumberPicker np = (NumberPicker) findViewById(R.id.yNumPickerInDia);
        np.setMaxValue(100); //최대 선택할 수 있는 값이 100
        np.setMinValue(1); //최소 선택할 수 있는 값이 1
        np.setWrapSelectorWheel(false); //최대값에서 더이상 올라가지 않게하기.
        np.setOnLongPressUpdateInterval(100); //길게 누르고 있으면 더 빨리 값이 바뀌게 하기.
        np.setValue(ySetCount);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                yGetPicker = newVal;
                Log.d("ProductDetailsActivity", "피커 값 : " + newVal);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recycleView(findViewById(R.id.yAdjustLayout));
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
