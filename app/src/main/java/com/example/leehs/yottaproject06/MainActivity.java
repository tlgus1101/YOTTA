package com.example.leehs.yottaproject06;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.leehs.yottaproject06.AboutCategoryAndFavor.FavorActivity;
import com.example.leehs.yottaproject06.ForSearch.SearchActivity;
import com.example.leehs.yottaproject06.ForSettings.MyPageActivity;
import com.example.leehs.yottaproject06.ShoppingCart.BasketActivity;

/*
기본적으로 MainActivity에서는 여러 액티비티 기능을 사용하기위해
onCreate시 여러 개의 Activity를 탭호스트에 추가시켜줍니다.
 */
public class MainActivity extends ActivityGroup {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TabHost yTabHost = (TabHost)findViewById(R.id.tab_host);
        yTabHost.setup(this.getLocalActivityManager());

        Intent yIntent;

        // Tab1 Setting
        yIntent = new Intent().setClass(this, FavorActivity.class);
        TabHost.TabSpec yTabSpec1 = yTabHost.newTabSpec("Tab1");
        yTabSpec1.setIndicator("선호제품"); // Tab Subject
        yTabSpec1.setContent(yIntent);
        //tabSpec1.setContent(R.id.tab_view1); // Tab Content
        yTabHost.addTab(yTabSpec1);


        // Tab2 Setting
        yIntent = new Intent().setClass(this, SearchActivity.class);
        TabHost.TabSpec yTabSpec2 = yTabHost.newTabSpec("Tab2");
        yTabSpec2.setIndicator("제품검색"); // Tab Subject
        yTabSpec2.setContent(yIntent);
        //tabSpec1.setContent(R.id.tab_view1); // Tab Content
        yTabHost.addTab(yTabSpec2);

        // Tab3 Setting
        final Intent yIntentForBasket = new Intent().setClass(MainActivity.this, BasketActivity.class);
        final TabHost.TabSpec yTabSpec3 = yTabHost.newTabSpec("Tab3");
        yTabSpec3.setIndicator("장바구니"); // Tab Subject
        yTabSpec3.setContent(yIntentForBasket);
        yTabHost.addTab(yTabSpec3);
        yTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId.equals("Tab3")){
                    ((BasketActivity)getCurrentActivity()).DoShoppingBasket();
                }
            }
        });

        // Tab4 Setting
        yIntent = new Intent().setClass(this, MyPageActivity.class);
        TabHost.TabSpec yTabSpec4 = yTabHost.newTabSpec("Tab4");
        yTabSpec4.setIndicator("마이페이지"); // Tab Subject
        yTabSpec4.setContent(yIntent);
        yTabHost.addTab(yTabSpec4);

        // show First Tab Content
        if(SearchActivity.musthotname != null)
        {
            yTabHost.setCurrentTab(1);
        }
        else {
            yTabHost.setCurrentTab(0);
        }
    }

    int count=0;
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Log.d("", "count " + count);
            if(count>0)
            {
                finish();
            }
            else
            {
                Toast.makeText(this, "한번 더 누르면 앱이 종료 됩니다.", Toast.LENGTH_SHORT).show();
                Log.d("", "count " + count);
                count ++;
            }
        }
        return false;
    }

}
