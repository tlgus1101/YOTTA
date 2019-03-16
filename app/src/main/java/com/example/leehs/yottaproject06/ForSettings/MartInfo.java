package com.example.leehs.yottaproject06.ForSettings;

/**
 * Created by MyCom on 2016-01-13.
 */
import android.graphics.drawable.Drawable;

/**
 * Created by IT on 2016-01-05.
 */
public class MartInfo {
    private String yMartName;
    private String yMartDistance;
    private Drawable yMartImage;
    private boolean ySelected = true;
    private boolean ySaved = false;

    public MartInfo(String _yMartName, String _yMartDistance, Drawable _yMartImage, boolean _ySaved) {
        this.yMartName = _yMartName;
        this.yMartDistance = _yMartDistance;
        this.yMartImage = _yMartImage;
        this.ySaved = _ySaved;
    }

    public String GetMartName(){
        return yMartName;
    }
    public Drawable GetMartImage() { return yMartImage; }
    public boolean GetSelect() { return ySelected; }
    public void SetSelect(boolean setSelect) { ySelected = setSelect; }
    public String GetMartDistance() { return yMartDistance; }

    public boolean SavedCheck() { return ySaved; }

}
