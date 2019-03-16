package com.example.leehs.yottaproject06.AboutCategoryAndFavor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.leehs.yottaproject06.R;

/**
 * Created by Lee HS on 2016-02-03.
 */
public class LargeImageActivity extends Activity{
    public static final int EXPLAIN_IMAGE1 = 1;
    public static final int EXPLAIN_IMAGE2 = 2;
    public static final int EXPLAIN_IMAGE3 = 3;
    public static final int EXPLAIN_IMAGE4 = 4;

    ImageView yLargeImage;
    int yCheckNum = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favor_image);

        yLargeImage = (ImageView)findViewById(R.id.yLargeImage);
        Intent intent = getIntent();
        yCheckNum = intent.getIntExtra("IMAGE",0);

        switch (yCheckNum){
            case EXPLAIN_IMAGE1:
                yLargeImage.setImageDrawable(getResources().getDrawable(R.drawable.view1));
                break;
            case EXPLAIN_IMAGE2:
                yLargeImage.setImageDrawable(getResources().getDrawable(R.drawable.view2));
                break;
            case EXPLAIN_IMAGE3:
                yLargeImage.setImageDrawable(getResources().getDrawable(R.drawable.view3));
                break;
            case EXPLAIN_IMAGE4:
                yLargeImage.setImageDrawable(getResources().getDrawable(R.drawable.view4));
                break;
        }
    }
}