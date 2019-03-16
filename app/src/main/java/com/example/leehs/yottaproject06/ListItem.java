package com.example.leehs.yottaproject06;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee HS on 2015-12-25.
 */

/*
이 클래스는 서버로부터 전송받은 데이터를 파싱하면서 입력받기 위한 클래스입니다.
Serializable은 Intent를 통해서 Activity끼리 정보를 전송하기 위해서 필요합니다.
 */
public class ListItem implements Serializable {

    private String[] yData;

    public ListItem(String[] data) {
        yData = data;
    }

    /*
    public ListItem(String name, String price, String distance, String department,
                    String id, String count, String address,String category) {
        String yesNo = "No";
        String date = "0000-00-11";

        yData = new String[10];
        yData[0] = name;
        yData[1] = price;
        yData[2] = distance;
        yData[3] = department;
        yData[4] = id;
        yData[5] = count;
        yData[6] = address;
        yData[7] = category;
        yData[8] = yesNo;
        yData[9] = date;
    }
    */

    /*
    public ListItem(String name, String price, String distance, String department,
                    String id, String count, String address,String category, String yesNo) {
        String date = "0000-00-00";

        yData = new String[10];
        yData[0] = name;
        yData[1] = price;
        yData[2] = distance;
        yData[3] = department;
        yData[4] = id;
        yData[5] = count;
        yData[6] = address;
        yData[7] = category;
        yData[8] = yesNo;
        yData[9] = date;
    }
    */

    public ListItem(String name, String price, String distance, String department,
                    String id, String count, String category, String date) {
        String yesNo = "No";

        yData = new String[9];
        yData[0] = name;
        yData[1] = price;
        yData[2] = distance;
        yData[3] = department;
        yData[4] = id;
        yData[5] = count;
        yData[6] = category;
        yData[7] = yesNo;
        yData[8] = date;
    }

    /*
    public ListItem(String name, String price, String distance, String department,
                    String id, String count, String address, String category, String yesNo, String date) {
        yData = new String[10];
        yData[0] = name;
        yData[1] = price;
        yData[2] = distance;
        yData[3] = department;
        yData[4] = id;
        yData[5] = count;
        yData[6] = address;
        yData[7] = category;
        yData[8] = yesNo;
        yData[9] = date;
    }
    */

    ///////////////////////////////////////
    public String getName() {
        return yData[0];
    }

    public String getDistance() {
        return yData[2];
    }

    public String getDepart() {
        return yData[3];
    }

    public void setPrice(String newNum) {
        yData[1] = newNum;
    }

    public void setDistance(String newNum) {
        yData[2] = newNum;
    }


    public void setCount(String newNum) {
        yData[5] = newNum;
    }

    public void setYesNo(String newNum) {
        yData[8] = newNum;
    }


    public ListItem(ListItem data) {
        yData[0] = data.getData(0);
        yData[1] = data.getData(1);
        yData[2] = data.getData(2);
        yData[3] = data.getData(3);
        yData[4] = data.getData(4);
        yData[5] = data.getData(5);
        yData[6] = data.getData(6);
        yData[7] = data.getData(7);
        yData[8] = data.getData(8);
    }

    public void changeList(ListItem temp) {
        yData[0] = temp.yData[0];
        yData[1] = temp.yData[1];
        yData[2] = temp.yData[2];
        yData[3] = temp.yData[3];
        yData[4] = temp.yData[4];
        yData[5] = temp.yData[5];
        yData[6] = temp.yData[6];
        yData[7] = temp.yData[7];
        yData[8] = temp.yData[8];
    }

    ///////////////////////////////////////

    public String[] getData() {
        return yData;
    }

    public String getData(int index) {
        return yData[index];
    }

    public void setData(String[] data) {
        yData = data;
    }

    public void setData(ListItem data) {
        yData[0] = data.getData(0);
        yData[1] = data.getData(1);
        yData[2] = data.getData(2);
        yData[3] = data.getData(3);
        yData[4] = data.getData(4);
        yData[5] = data.getData(5);
        yData[6] = data.getData(6);
        yData[7] = data.getData(7);
        yData[8] = data.getData(8);
    }
}