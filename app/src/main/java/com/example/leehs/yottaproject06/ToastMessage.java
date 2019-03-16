package com.example.leehs.yottaproject06;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 402-4 on 2016-01-16.
 */
public class ToastMessage extends Toast {
    Context yContext;
    public ToastMessage(Context context){
        super(context);
        yContext = context;
    }

    @SuppressLint("InflateParams")
    @SuppressWarnings("unused")
    public void showToast(String body, int duration){
        LayoutInflater inflater;
        View v;
        if(false) {
            Activity act = (Activity) yContext;
            inflater = act.getLayoutInflater();
            v = inflater.inflate(R.layout.toast_layout, null);
        }
        else{
            inflater= (LayoutInflater)yContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.toast_layout, null);
        }
        TextView text = (TextView) v.findViewById(R.id.text);
        text.setText(body);

        show(this, v, duration);
    }

    private void show(Toast toast, View v, int duration){
        toast.setGravity(Gravity.CENTER, 0 , 150);
        toast.setDuration((duration));
        toast.setView(v);
        toast.show();
    }
}
