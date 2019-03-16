package com.example.leehs.yottaproject06.InsertDataToServer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.leehs.yottaproject06.AboutMaps.GPSInfo;
import com.example.leehs.yottaproject06.AboutMaps.MapApiConst;
import com.example.leehs.yottaproject06.FindLocation.Item;
import com.example.leehs.yottaproject06.FindLocation.OnFinishSearchListener;
import com.example.leehs.yottaproject06.FindLocation.Searcher;
import com.example.leehs.yottaproject06.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-02-02.
 */
public class InsertLocationDialog extends Activity{
    final static int TAG_SETTING_DISTANCE = 300;

    LinearLayout yInsertLocationLayout;

    RadioGroup rGrpMart;
    Button nearLocationOk;
    Button nearLocationNo;

    String searchName = "";
    String productName = "";
    InsertLocationAdapter mInsertAdapter;
    ArrayList<Item> mLocationList;
    boolean checkRBtn;
    boolean checkGps;
    int checkNumber = -1;

    // 애플리케이션 전체에서 사용할 값
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor sharedEditor;

    private GPSInfo gps;
    double latitude = 0;
    double longitude = 0;
    int radius;
    int page = 1;

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_location_dialog);

        yInsertLocationLayout = (LinearLayout)findViewById(R.id.yInsertLocationLayout);
        yInsertLocationLayout.setBackgroundDrawable(new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.insert_location)));

        //다이얼로그를 띄우고 밖을 클릭했을 시 꺼지는 것을 방지함.
        this.setFinishOnTouchOutside(false);

        //액티비티간에 정보를 저장,공유하기 위한 변수
        sharedPref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        sharedEditor = sharedPref.edit();

        checkGps = false;
        checkRBtn = true;
        mLocationList = new ArrayList<Item>();
        setRadius(TAG_SETTING_DISTANCE);

        searchName = "마트";

        rGrpMart = (RadioGroup)findViewById(R.id.rGrpMart);
        nearLocationOk = (Button)findViewById(R.id.nearLocationOk);
        nearLocationNo= (Button)findViewById(R.id.nearLocationNo);

        Intent intent = getIntent();
        productName = intent.getStringExtra("name");

        rGrpMart.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkGps = settingLocation();
                if (checkGps) {
                    if (checkRBtn) {
                        checkRBtn = false;
                        switch (rGrpMart.getCheckedRadioButtonId()) {
                            case R.id.nearMart:
                                searchName = "마트";
                                searchLocation(searchName);
                                break;
                            case R.id.nearSuper:
                                searchName = "슈퍼";
                                searchLocation(searchName);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        });

        nearLocationOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLocationList.size() != 0 && checkNumber != -1){
                    if(mLocationList.get(checkNumber).distance <= TAG_SETTING_DISTANCE){
                        Intent intent = new Intent(InsertLocationDialog.this, InsertActivity.class);
                        intent.putExtra("product", productName.toString());
                        intent.putExtra("mart",mLocationList.get(checkNumber).title);
                        startActivity(intent);
                        finish();
                    }else{
                        showToast("사용자가 마트에 있다고 판단되지않습니다.");
                    }
                }
            }
        });

        nearLocationNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        checkGps = settingLocation();
        if(checkGps){
            searchLocation(searchName);
        }
    }

    public boolean settingLocation () {
        Log.d ( "###InsertLocationDialog", "settinglocation 시작" );

        gps = new GPSInfo ( InsertLocationDialog.this );
        Log.d ( "*** GPSInfo ***", "lat : 일로 안오냐일로 안와?"+gps.isGPSEnabled () );
        if (gps.isGPSEnabled ()==true)
        {
            Log.d ( "GPS latitude", "" + gps.getLatitude () );
            Log.d ( "GPS longitude", "" + gps.getLongitude () );

            if (gps.getLatitude () == 0 && gps.getLongitude () == 0) {
                showToast ( "사용자 위치 파악중입니다." );
                Log.d ( "*** GPSInfo ***", "lat : 일로 안오냐일로 안와?if" );
                return false;
            } else{
                Log.d ( "*** GPSInfo ***", "lat : 일로 안오냐일로 안와?else" );
                setLatitude ( gps.getLatitude () );
                setLongitude ( gps.getLongitude () );

                sharedEditor.remove ( "Latitude_" );
                sharedEditor.remove ( "Longitude_" );

                Log.d ( "*** GPSInfo ***", "lat : " + getLatitude () );
                Log.d ( "*** GPSInfo ***", "lon : " + getLongitude () );

                sharedEditor.putString ( "Latitude_", "" + getLatitude () );
                sharedEditor.commit ();
                sharedEditor.putString ( "Longitude_", "" + getLongitude () );
                sharedEditor.commit ();
                if(gps.isNetworkEnabled ()==true) {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else  if(gps.isGPSEnabled ()==false) {
            if (sharedPref.getString("Latitude_", "").equals(null) == false) {
                String lat = sharedPref.getString("Latitude_", "");
                String lot = sharedPref.getString("Longitude_", "");

                if (lat == "" || lot == "") {
                    return false;
                } else {
                    setLatitude(Double.parseDouble(lat));
                    setLongitude(Double.parseDouble(lot));

                    Log.d("*** GPSInfo ***", "lat : " + lat);
                    Log.d("*** GPSInfo ***", "lon : " + lot);
                    Log.d("*** GPSInfo ***", "getLatitude : " + getLatitude());
                    Log.d("*** GPSInfo ***", "getLongitude : " + getLongitude());

                    if (gps.isNetworkEnabled() == true) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

//    public boolean settingLocation() {
//        yGps = new GPSInfo(InsertLocationDialog.this);
//        if (yGps.isGetLocation()) {
//
//            if (yGps.getLatitude() == 0 && yGps.getLongitude() == 0) {
//                showToast("사용자 위치 파악중입니다.");
//
//                return false;
//            } else {
//                setLatitude(yGps.getLatitude());
//                setLongitude(yGps.getLongitude());
//                return true;
//            }
//        }
//        return false;
//    }

    public void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(InsertLocationDialog.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void SortLocation(ArrayList<Item> arr) {
        int last = arr.size() - 1;
        Item temp;

        for (int i = last; i > 0; --i) {
            for (int j = 0; j < i; ++j) {
                if ((arr.get(j).distance) > (arr.get(j + 1).distance)) {
                    temp = arr.get(j + 1);
                    arr.set(j + 1, arr.get(j));
                    arr.set(j, temp);
                }
            }
        }
        mLocationList = arr;

        viewLocationList(arr);
    }

    public void SaveLocation(List<Item> arr) {
        mLocationList.clear();
        for (int i = 0; i < arr.size() ; ++i) {
            Item item = arr.get(i);
            mLocationList.add(i, item);
            Log.d("InsertLocation",mLocationList.get(i).title);
            //Log.d("InsertActivity", "마트#############" + mMartLocationList.get(i).title);
        }
        SortLocation(mLocationList);
    }

    public void searchLocation(final String name) {
        final Searcher searcher = new Searcher();

        Log.d("settingbasketlocation", name);
        searcher.searchKeyword(getApplicationContext(), name, getLatitude(), getLongitude(), getRadius(), page,
                MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        Log.d("settingbasketlocation", "Search 성공");
                        for (int i = 0; i < itemList.size(); ++i) {
                            Log.d("setting location name", itemList.get(i).title);
                        }
                        SaveLocation(itemList);
                    }

                    @Override
                    public void onSuccessSingle(Item item) {

                    }

                    @Override
                    public void onFail() {
                        Toast.makeText(InsertLocationDialog.this, "API_KEY의 제한 트래픽이 초과되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void viewLocationList(final ArrayList<Item> arr) {
        checkRBtn = true;

        final ListView nearLocationList = (ListView)findViewById(R.id.nearLocationList);
        nearLocationList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        mInsertAdapter = new InsertLocationAdapter(arr, getLayoutInflater());

        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 해당 작업을 처리함
                        if (arr.size() == 0)
                            Toast.makeText(InsertLocationDialog.this, "마트를 찾을 수 없습니다.\n다시 시도해주세요", Toast.LENGTH_SHORT).show();

                        nearLocationList.setAdapter(mInsertAdapter);
                        nearLocationList.setOnItemClickListener(mItemClickListener);
                    }
                });
            }
        }).start();
    }

    public AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mInsertAdapter.setAllchecked(false);
            mInsertAdapter.setChecked(position);
            mInsertAdapter.notifyDataSetChanged();

            checkNumber = position;
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        recycleView(findViewById(R.id.yInsertLocationLayout));
    }

    private void recycleView(View view) {
        if(view != null) {
            Drawable bg = view.getBackground();
            if(bg != null) {
                bg.setCallback(null);
                ((BitmapDrawable)bg).getBitmap().recycle();
                view.setBackgroundDrawable(null);
            }
        }
    }
}
