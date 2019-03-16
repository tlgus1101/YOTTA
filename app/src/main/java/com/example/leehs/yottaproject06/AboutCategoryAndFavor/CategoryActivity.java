package com.example.leehs.yottaproject06.AboutCategoryAndFavor;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.example.leehs.yottaproject06.ForSearch.SearchActivity;
import com.example.leehs.yottaproject06.R;
import com.example.leehs.yottaproject06.ToastMessage;

/**
 * Created by MyCom on 2016-01-05.
 */

    /*
        원래 SelectActivity였는데 기능이 바뀌면서
        CategoryActivity로 수정함. 카테고리 리스트를 보여줌.
    */
public class CategoryActivity extends Activity {//implements View.OnClickListener {
    ToastMessage toast;

    //과일/채소/견과 -> 1~10번대(FRUITS)
    public static final int CATEGORY_FRUITS_FRUIT = 1; //과일
    public static final int CATEGORY_FRUITS_RICE = 2; //쌀/잡곡
    public static final int CATEGORY_FRUITS_NUTS = 3; //견과류
    public static final int CATEGORY_FRUITS_VEGETABLE = 4; //채소

    //가공식품 -> 11~20번대(PROCESSED)
    public static final int CATEGORY_PROCESSED_RAMYUN = 11;
    public static final int CATEGORY_PROCESSED_SNACK = 12;
    public static final int CATEGORY_PROCESSED_INSTANTnCAN = 13;
    public static final int CATEGORY_PROCESSED_SIDE = 14;
    public static final int CATEGORY_PROCESSED_SEASONING =15;
    public static final int CATEGORY_PROCESSED_BREAD = 16;

    //냉장/냉동식품 -> 21~30번대(FROZEN)
    public static final int CATEGORY_FROZEN_WATERS = 21;
    public static final int CATEGORY_FROZEN_DRINKS = 22;
    public static final int CATEGORY_FROZEN_FROZENPRODUCT =23;

    //유제품 -> 31~40번대(DAIRY)
    public static final int CATEGORY_DAIRY_MILK =31;
    public static final int CATEGORY_DAIRY_YOGURT = 32;
    public static final int CATEGORY_DAIRY_ICECREAM =33;
    public static final int CATEGORY_DAIRY_CHEESE = 34;

    //해산물 -> 41~50번대(SEEFOOD)
    public static final int CATEGORY_SEEFOOD_FISHES = 41;
    public static final int CATEGORY_SEEFOOD_CRUSTACEA = 42;
    public static final int CATEGORY_SEEFOOD_VACUUMPACKING = 43;

    //정육/계란 -> 51~60번대(FRESHES)
    public static final int CATEGORY_FRESHES_DOMESTIC = 51;
    public static final int CATEGORY_FRESHES_IMPORT = 52;
    public static final int CATEGORY_FRESHES_EGGS = 53;

    //////////////////////////////////////////////////////////////////////////////////////////////카테고리
    private ExpandableListView mListView;
    ListView listViewCatalog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        toast = new ToastMessage(CategoryActivity.this);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //카테고리 밑에 제품정보 리스트
        listViewCatalog = (ListView) findViewById(R.id.ChooseListView);

        /////////////////////////////////////////////////////////////////////////////////////카테고리 구현중
        setLayout();
        CategoryList cl = new CategoryList();
        cl.SetCategory();

        mListView.setAdapter(new ExpandableAdapter(this, cl.mGroupList, cl.mChildList));

        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //Toast.makeText(getApplicationContext(), "g click = " + groupPosition, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        mListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int groupCount = mListView.getAdapter().getCount();

                // 한 그룹을 클릭하면 나머지 그룹들은 닫힌다.
                for (int i = 0; i < groupCount; i++) {
                    if (!(i == groupPosition))
                        mListView.collapseGroup(i);
                }
            }
        });

        mListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int groupCount = mListView.getAdapter().getCount();

                // 한 그룹을 클릭하면 나머지 그룹들은 닫힌다.
                for (int i = 0; i < groupCount; i++) {
                    if (!(i == groupPosition))
                        mListView.collapseGroup(i);
                }
            }
        });

        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Log.d("CategoryActivity", "그룹 : " + groupPosition);
                Log.d("CategoryActivity", "차일드 : " + childPosition);
                //toast.showToast("해당 카테고리에서 상품을 검색중 입니다", Toast.LENGTH_SHORT);

                if (groupPosition == 0) {
                    //과일/채소/견과 -> 1~10번대(FRUITS)
                    switch (childPosition) {
                        case 0:
                            SearchActivity.ySetCategory = CATEGORY_FRUITS_FRUIT; //과일
                            break;
                        case 1:
                            SearchActivity.ySetCategory = CATEGORY_FRUITS_RICE; //쌀/잡곡
                            break;
                        case 2:
                            SearchActivity.ySetCategory = CATEGORY_FRUITS_NUTS; //견과류
                            break;
                        case 3:
                            SearchActivity.ySetCategory = CATEGORY_FRUITS_VEGETABLE; //채소
                            break;
                    }
                } else if (groupPosition == 1) {
                    //가공식품 -> 11~20번대(PROCESSED)
                    switch (childPosition) {
                        case 0:
                            SearchActivity.ySetCategory = CATEGORY_PROCESSED_RAMYUN; //라면
                            break;
                        case 1:
                            SearchActivity.ySetCategory = CATEGORY_PROCESSED_SNACK; //과자/껌/캔디류
                            break;
                        case 2:
                            SearchActivity.ySetCategory = CATEGORY_PROCESSED_INSTANTnCAN; //김치 및 반찬류
                            break;
                        case 3:
                            SearchActivity.ySetCategory = CATEGORY_PROCESSED_SIDE; //김치 및 반찬류
                            break;
                        case 4:
                            SearchActivity.ySetCategory = CATEGORY_PROCESSED_SEASONING; //조미료/소스
                            break;
                        case 5:
                            SearchActivity.ySetCategory = CATEGORY_PROCESSED_BREAD; //빵류(잼,크림)
                            break;
                    }
                } else if (groupPosition == 2) {
                    //냉장/냉동식품 -> 21~30번대(FROZEN)
                    switch (childPosition) {
                        case 0:
                            SearchActivity.ySetCategory = CATEGORY_FROZEN_WATERS; //물/음료
                            break;
                        case 1:
                            SearchActivity.ySetCategory = CATEGORY_FROZEN_DRINKS; //주류
                            break;
                        case 2:
                            SearchActivity.ySetCategory = CATEGORY_FROZEN_FROZENPRODUCT; //냉동식품
                            break;
                    }
                } else if (groupPosition == 3) {
                    //유제품 -> 31~40번대(DAIRY)
                    switch (childPosition) {
                        case 0:
                            SearchActivity.ySetCategory = CATEGORY_DAIRY_MILK; //우유
                            break;
                        case 1:
                            SearchActivity.ySetCategory = CATEGORY_DAIRY_YOGURT; //요구르트
                            break;
                        case 2:
                            SearchActivity.ySetCategory = CATEGORY_DAIRY_ICECREAM; //아이스크림
                            break;
                        case 3:
                            SearchActivity.ySetCategory = CATEGORY_DAIRY_CHEESE; //치즈
                            break;
                    }
                } else if (groupPosition == 4) {
                    //해산물 -> 41~50번대(SEEFOOD)
                    switch (childPosition) {
                        case 0:
                            SearchActivity.ySetCategory = CATEGORY_SEEFOOD_FISHES; //생선/회
                            break;
                        case 1:
                            SearchActivity.ySetCategory = CATEGORY_SEEFOOD_CRUSTACEA; //갑각류
                            break;
                        case 2:
                            SearchActivity.ySetCategory = CATEGORY_SEEFOOD_VACUUMPACKING; //진공포장 제품
                            break;
                    }
                } else {
                    //정육/계란 -> 51~60번대(FRESHES)
                    switch (childPosition) {
                        case 0:
                            SearchActivity.ySetCategory = CATEGORY_FRESHES_DOMESTIC; //국산
                            break;
                        case 1:
                            SearchActivity.ySetCategory = CATEGORY_FRESHES_IMPORT; //수입산
                            break;
                        case 2:
                            SearchActivity.ySetCategory = CATEGORY_FRESHES_EGGS; //진공포장 제품
                            break;
                    }
                }
                finish();
                //여기서 값을 되돌려주면 됨.
                return false;
            }
        });
    }

    /////////////////////////////////////////////////카테고리 리스트 어댑터
    private void setLayout(){
        mListView = (ExpandableListView)findViewById(R.id.category_list);
    }
}
