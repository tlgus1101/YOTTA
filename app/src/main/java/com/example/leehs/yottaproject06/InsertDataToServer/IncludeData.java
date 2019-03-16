package com.example.leehs.yottaproject06.InsertDataToServer;

import android.graphics.Bitmap;

/**
 * Created by MyCom on 2016-01-11.
 */
public class IncludeData {
    Bitmap bitImage;
    String yInputName;
    String yInputPrice;
    String yInputDepart;
    String yInputCategory;
    String yInputPhone;

    public IncludeData(Bitmap _resized,String _inputName, String _inputPrice,  String _inputDepart, String _inputCategory,String _inputPhone){
        bitImage = _resized;
        yInputName = _inputName;
        yInputPrice = _inputPrice;
        yInputDepart = _inputDepart;
        yInputCategory = _inputCategory;
        yInputPhone=_inputPhone;
    }

    public Bitmap GetImage(){
        return bitImage;
    }
    public String GetName(){
        return yInputName;
    }
    public String GetPrice(){
        return yInputPrice;
    }
    public String GetDepart(){
        return yInputDepart;
    }
    public String GetCategory() { return yInputCategory; }
    public String GetPhone() { return yInputPhone; }
}