package com.example.leehs.yottaproject06.ForSettings;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.leehs.yottaproject06.R;

public class MenualActivity extends Activity implements View.OnTouchListener{
    ViewFlipper flipper;

    // 터치 이벤트 발생 지점의 x좌표 저장
    float xAtDown;
    float xAtUp;
    Button yPrevBtn, yNextBtn;
    int yCheck;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menual);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        flipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        flipper.setOnTouchListener(this);

        for (int i = 0; i < 6; i++) {
            ImageView img = new ImageView(this);
            img.setImageResource(R.drawable.menual1 + i);
            flipper.addView(img);
        }

        yPrevBtn = (Button) findViewById(R.id.yPrevBtn);
        yNextBtn = (Button) findViewById(R.id.yNextBtn);
        // ViewFlipper에 동적으로 child view 추가
    }

    // View.OnTouchListener의 abstract method
    // flipper 터지 이벤트 리스너
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // 터치 이벤트가 일어난 뷰가 ViewFlipper가 아니면 return
        if (v != flipper)
            return false;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            xAtDown = event.getX(); // 터치 시작지점 x좌표 저장
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            xAtUp = event.getX(); // 터치 끝난지점 x좌표 저장

            if (xAtUp < xAtDown) {
                // 왼쪽 방향 에니메이션 지정
                flipper.setInAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.push_left_in));
                flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.push_left_out));

                // 다음 view 보여줌
                flipper.showNext();
            } else if (xAtUp > xAtDown) {
                // 오른쪽 방향 에니메이션 지정
                flipper.setInAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.push_right_in));
                flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.push_right_out));
                // 전 view 보여줌
                flipper.showPrevious();
            }
        }
        return true;
    }

    public void prevBtnClick(View v) {
        // 오른쪽 방향 에니메이션 지정
        flipper.setInAnimation(AnimationUtils.loadAnimation(this,
                R.anim.push_right_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                R.anim.push_right_out));flipper.showPrevious();
    }
    public void nextBtnClick(View v) {
        // 왼쪽 방향 에니메이션 지정
        flipper.setInAnimation(AnimationUtils.loadAnimation(this,
                R.anim.push_left_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                R.anim.push_left_out));flipper.showNext();
    }
}