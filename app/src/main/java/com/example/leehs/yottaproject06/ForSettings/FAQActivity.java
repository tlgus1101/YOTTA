package com.example.leehs.yottaproject06.ForSettings;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;

import com.example.leehs.yottaproject06.R;

/**
 * Created by 402-4 on 2016-01-23.
 */
public class FAQActivity extends Activity {
    private ExpandableListView yFaqListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /////////////////////////////////////////////////////////////////////////////////////카테고리 구현중
        yFaqListView = (ExpandableListView)findViewById(R.id.faqlist);
        FAQList fl = new FAQList();
        fl.SetCategory();

        yFaqListView.setAdapter(new FAQListAdapter(this, fl.yFAList, fl.yFQList));
        yFaqListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int groupCount = yFaqListView.getAdapter().getCount();

                // 한 그룹을 클릭하면 나머지 그룹들은 닫힌다.
                for (int i = 0; i < groupCount; i++) {
                    if (!(i == groupPosition))
                        yFaqListView.collapseGroup(i);
                }
            }
        });
        yFaqListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return true;
            }
        });
    }
}

