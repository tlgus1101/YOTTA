package com.example.leehs.yottaproject06.ForSearch;

import android.graphics.Bitmap;

/**
 * Created by MyCom on 2016-01-15.
 */
public class SearchProduct {
    private String yPName;
    private Bitmap yPImage;
    private String yDepartment;
    private int yPrice;
    private String yDistance;
    private boolean ySelected;
    private int yCount = 0;
    private String yDate;

    public SearchProduct(Bitmap productImage, String title, int price,
                         String department, String distance,String date,
                         int count) {
        this.yPName = title;
        this.yPImage = productImage;
        this.yDepartment = department;
        this.yPrice = price;
        this.yDistance = distance;
        this.yDate = date;
        this.yCount = count;
    }

    public Bitmap GetPImage(){
        return this.yPImage;
    }
    /*
    public void setCount(int getNum){
        if(getNum != 0){
            yCount = getNum;
        }
    }
    */

    public int GetCount(){
        return yCount;
    }
    public String GetName(){
        return yPName;
    }
    public String GetDepartment() { return yDepartment; }
    public String GetDistance() {return yDistance; }
    public int GetPrice() { return yPrice; }
    public boolean GetSelect() { return ySelected; }
    public void SetSelect(boolean check){
        ySelected = check;
    }
    public String GetDate(){ return yDate; }

}