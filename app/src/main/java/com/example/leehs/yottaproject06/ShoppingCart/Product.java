package com.example.leehs.yottaproject06.ShoppingCart;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by MyCom on 2016-01-06.
 */

/*
장바구니 및 제품선택에 쓰이는 데이터를 담기위한 클래스입니다.
 */
public class Product {
    private String yPName;
    private Bitmap yPImage;
    private String yDepartment;
    private int yPrice;
    private boolean ySelected;
    private boolean yYesNo;
    private boolean yDate;
    private int yPicker = 1;
    private int yCount;

    public Product(String title, Bitmap productImage, String description,
                   int price) {
        this.yPName = title;
        this.yPImage = productImage;
        this.yDepartment = description;
        this.yPrice = price;
    }

    public Product(String title, Bitmap productImage, String description,
                   int price, int count, int picker, boolean date, boolean yesNo) {
        this.yPName = title;
        this.yPImage = productImage;
        this.yDepartment = description;
        this.yPrice = price;
        this.yCount = count;
        this.yPicker = picker;
        this.yDate = date;
        this.yYesNo = yesNo;
    }

    public Product(String title, Bitmap productImage, String description,
                   int price, int count, int picker, boolean yesNo) {
        this.yPName = title;
        this.yPImage = productImage;
        this.yDepartment = description;
        this.yPrice = price;
        this.yCount = count;
        this.yPicker = picker;
        this.yDate = false;
        this.yYesNo = yesNo;
    }

    public Bitmap GetBitmap() {
        return this.yPImage;
    }

    public void setPicker(int getNum) {
        if (getNum != 0) {
            yPicker = getNum;
        }
    }

    public int GetPicker() { return yPicker; }

    public void setyYesNo(boolean yesNo) {
        yYesNo = yesNo;
    }

    public void SetTotalPrice() {
        yPrice *= yPicker;
    }

    public String GetName() {
        return yPName;
    }

    public String GetDepartment() {
        return yDepartment;
    }

    public int GetPrice() {
        return yPrice;
    }

    public int GetCount() {
        return yCount;
    }

    public boolean GetSelect() {
        return ySelected;
    }

    public boolean GetYesNo() {
        return yYesNo;
    }

    public void Setdate(boolean getDate) {
        yDate = getDate;
    }

    public boolean GetDate() {
        return yDate;
    }

    public void SetYesNo(boolean getYesNo) {
        yYesNo = getYesNo;
    }

    public void SetSeelect(boolean getSeletced) {
        ySelected = getSeletced;
    }
}