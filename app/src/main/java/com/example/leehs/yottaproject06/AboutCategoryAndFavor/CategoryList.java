package com.example.leehs.yottaproject06.AboutCategoryAndFavor;

import android.app.Activity;
import java.util.ArrayList;

/**
 * Created by 402-4 on 2016-01-20.
 */
public class CategoryList extends Activity {
    //////////////////////////////////////////////////////////////////////////////////////////////카테고리
    public ArrayList<String> mGroupList = null;
    public ArrayList<ArrayList<String>> mChildList = null;
    private ArrayList<String> yChildList_Fruits = null;
    private ArrayList<String> yChildList_Processed = null;
    private ArrayList<String> yChildList_Frozen = null;
    private ArrayList<String> yChildList_Milk = null;
    private ArrayList<String> yChildList_Seafood = null;
    private ArrayList<String> yChildList_Meat = null;

    /////////////////////////////////////////////////////////////////////////////////////카테고리 구현중
    public void SetCategory() {
        mGroupList = new ArrayList<String>();
        mChildList = new ArrayList<ArrayList<String>>();
        yChildList_Fruits = new ArrayList<String>();
        yChildList_Processed = new ArrayList<String>();
        yChildList_Frozen = new ArrayList<String>();
        yChildList_Milk = new ArrayList<String>();
        yChildList_Seafood = new ArrayList<String>();
        yChildList_Meat = new ArrayList<String>();

        //////////////////////////////////상위 카테고리
        mGroupList.add("과일/채소/견과");
        mGroupList.add("가공식품");
        mGroupList.add("냉장/냉동식품");
        mGroupList.add("유제품");
        mGroupList.add("해산물");
        mGroupList.add("정육/계란");
        //mGroupList.add("유기농/웰빙");
        //mGroupList.add("건강식품");
        //mGroupList.add("조미료/장");

        /////////////////////////////////////하위 카테고리
        yChildList_Fruits.add("과일");
        yChildList_Fruits.add("쌀/잡곡");
        yChildList_Fruits.add("건과일,견과류");
        yChildList_Fruits.add("채소");

        yChildList_Processed.add("라면");
        yChildList_Processed.add("과자/껌/캔디류");
        yChildList_Processed.add("즉석식품/통조림");
        yChildList_Processed.add("김치/반찬");
        yChildList_Processed.add("조미료/소스");
        yChildList_Processed.add("빵류(잼,크림)");

        yChildList_Frozen.add("물/음료");
        yChildList_Frozen.add("주류");
        yChildList_Frozen.add("냉동식품");

        yChildList_Milk.add("우유");
        yChildList_Milk.add("요구르트");
        yChildList_Milk.add("아이스크림");
        yChildList_Milk.add("치즈");

        yChildList_Seafood.add("생선/회");
        yChildList_Seafood.add("갑각류");
        yChildList_Seafood.add("진공포장 제품");

        yChildList_Meat.add("국산");
        yChildList_Meat.add("수입산");
        yChildList_Meat.add("계란류");

        mChildList.add(yChildList_Fruits);
        mChildList.add(yChildList_Processed);
        mChildList.add(yChildList_Frozen);
        mChildList.add(yChildList_Milk);
        mChildList.add(yChildList_Seafood);
        mChildList.add(yChildList_Meat);
    }
}
