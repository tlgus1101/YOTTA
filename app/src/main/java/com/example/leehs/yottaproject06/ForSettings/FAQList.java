package com.example.leehs.yottaproject06.ForSettings;

import android.app.Activity;

import java.util.ArrayList;

public class FAQList extends Activity {
    //////////////////////////////////////////////////////////////////////////////////////////////카테고리
    public ArrayList<String> yFAList = null;
    public ArrayList<ArrayList<String>> yFQList = null;
    private ArrayList<String> yFAQ_List1 = null; private ArrayList<String> yFAQ_List2 = null;
    private ArrayList<String> yFAQ_List3 = null; private ArrayList<String> yFAQ_List4 = null;
    private ArrayList<String> yFAQ_List5 = null; private ArrayList<String> yFAQ_List6 = null;
    private ArrayList<String> yFAQ_List7 = null; private ArrayList<String> yFAQ_List8 = null;
    private ArrayList<String> yFAQ_List9 = null;

    /////////////////////////////////////////////////////////////////////////////////////카테고리 구현중
    public void SetCategory() {
        yFAList = new ArrayList<String>();
        yFQList = new ArrayList<ArrayList<String>>();
        yFAQ_List1 = new ArrayList<String>(); yFAQ_List2 = new ArrayList<String>();
        yFAQ_List3 = new ArrayList<String>(); yFAQ_List4 = new ArrayList<String>();
        yFAQ_List5 = new ArrayList<String>(); yFAQ_List6 = new ArrayList<String>();
        yFAQ_List7 = new ArrayList<String>(); yFAQ_List8 = new ArrayList<String>();
        yFAQ_List9 = new ArrayList<String>();


        /////////////////////////////////////////질문
        yFAList.add("1. 싸싸란 무엇인가요? ");
        yFAList.add("2. 검색은 어떻게 하나요?");
        yFAList.add("3. 제품정보는 어떻게 보여주나요?");
        yFAList.add("4. 장바구니는 어떻게 이용하나요?");
        yFAList.add("5. GPS를 꼭 사용해야 하나요?");
        yFAList.add("6. 거리설정이란 무엇인가요?");
        yFAList.add("7. 관심상품 설정에 대하여");
        yFAList.add("8. 내마트 설정이란?");
        yFAList.add("9. AND THEN,");

        /////////////////////////////////////////답변
        yFAQ_List1.add("싸싸는 사용자가 직접 마트의 제품가격을 등록하여 서로 공유합니다.\n" +
                "\"오프라인 가격을, 온라인으로 소통하는 앱\"입니다");
        yFAQ_List2.add("1.제품명 검색은 직접 내가 필요한 제품의 이름을 검색할 수 있습니다.\n\n" +
                "2.카테고리별 검색은 그 카테고리에 해당되는 모든 제품을 보여줍니다.");
        yFAQ_List3.add("저희는 가격의 신뢰도를 위해 사용자가 일정 마트, 일정 제품의 가격을 등록하면, \n" +
                "3명이상 입력한 가격만을 제품정보에 제공해줍니다. ");
        yFAQ_List4.add("1.제품정보에서 장바구니 버튼을 누르면 제품이 장바구니에 담깁니다.\n\n" +
                "2.장바구니에서는 내위치 기반으로 가까운 마트 순으로 정렬해 보여줍니다.\n\n" +
                "3.장바구니에 담긴 상품의 수량을 변경하고 싶다면 장바구니에서 상품을 길게 누르면 됩니다.");
        yFAQ_List5.add("네, 저희 앱은 사용자 위치기반으로 제품정보를 제공하는 앱이기 때문에 " +
                "GPS를 켜 놓은 상태에서만 앱을 원활하게 사용가능합니다.");
        yFAQ_List6.add("1. 거리설정 버튼을 누르면 1, 3, 5km 단위로 선택할 수 있습니다.\n" +
                "선택한 거리만큼 내 주변을 탐색하여 주변 마트정보를 보여줍니다.\n\n" +
                "또한, 저장된 마트 정보가 사용자 위치 기준을 넘어도 모든 마트 정보를 보여줍니다.\n\n"+
                "2.내 마트로 등록한 마트는 설정거리에 상관없이 리스트에 보여집니다");
        yFAQ_List7.add("관심등록을 하게되면 메인페이지에서 선호제품으로 보여집니다.");
        yFAQ_List8.add("1. '내 마트설정'은 내가 원하는 마트의 검색결과만 나오도록 설정할 수 있습니다.\n" +
                "  아무것도 설정하지 않는다면 7km 이내의 마트에 대해 전체검색을 하게 됩니다.\n" +
                "2. 사용하는 방법은 거리 설정에 있는 1, 3, 5km 버튼을 누른 후 대형,중형,소형마트 중 하나의 버튼을 클릭하시면 검색결과를 보여줍니다.\n" +
                "3. 만약 대형마트에서 설정한 내용을 저장버튼을 누르지 않고 중형마트를 클릭하면 내용이 사라지게 되니 주의해주세요!");
        yFAQ_List9.add("WHAT");

        //////////////////////////////////////답변 넣는 부분
        yFQList.add(yFAQ_List1);
        yFQList.add(yFAQ_List2);
        yFQList.add(yFAQ_List3);
        yFQList.add(yFAQ_List4);
        yFQList.add(yFAQ_List5);
        yFQList.add(yFAQ_List6);
        yFQList.add(yFAQ_List7);
        yFQList.add(yFAQ_List8);
        yFQList.add(yFAQ_List9);
    }
}