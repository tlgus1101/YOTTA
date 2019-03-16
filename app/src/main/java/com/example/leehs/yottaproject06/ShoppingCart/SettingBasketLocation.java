package com.example.leehs.yottaproject06.ShoppingCart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
 * Created by MyCom on 2016-01-06.
 */

/*
SelectActivity에서 리스트 아이템을 클릭하면 이 액티비티가 실행되며
여기서 수량과 가격정보(추후에 추가할 것)를 입력하면
해당 정보가 입력되서 장바구니에 추가됩니다.
 */
public class SettingBasketLocation extends Activity {
    final static int TAG_1KM = 1000;
    final static int TAG_3KM = 3000;
    final static int TAG_5KM = 5000;

    String searchName = "";

    int checkNumber = -1;
    ;

    boolean checkButton;
    boolean checkGps;

    ArrayList<Item> mLocationList;
    private SettingBasketAdapter mSettingAdapter;

    RadioGroup rGrpDistance;
    RadioButton rBtn1km;
    RadioButton rBtn3km;
    RadioButton rBtn5km;

    // 애플리케이션 전체에서 사용할 값
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor sharedEditor;

    GPSInfo gps;
    double latitude = 0;
    double longitude = 0;
    int radius = 0;
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

    EditText searchBasketEdit;
    ListView searchBasketList;
    Button searchBasketBtn;
    Button searchBasketBtnYes;
    Button searchBasketBtnNo;
    LinearLayout ySettingLocationLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basket_dialog);

        //다이얼로그를 띄우고 밖을 클릭했을 시 꺼지는 것을 방지함.
        this.setFinishOnTouchOutside(false);

        ySettingLocationLayout = (LinearLayout)findViewById(R.id.ySettingLocationLayout);
        ySettingLocationLayout.setBackgroundDrawable(new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.setmart)));

        //액티비티간에 정보를 저장,공유하기 위한 변수
        sharedPref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        sharedEditor = sharedPref.edit();

        checkButton = true;
        checkGps = false;
        setRadius(TAG_1KM);

        searchBasketEdit = (EditText) findViewById(R.id.searchBasketEdit);
        searchBasketList = (ListView) findViewById(R.id.searchBasketList);
        searchBasketBtn = (Button) findViewById(R.id.searchBasketBtn);
        searchBasketBtnYes = (Button) findViewById(R.id.searchBasketYes);
        searchBasketBtnNo = (Button) findViewById(R.id.searchBasketNo);

        rGrpDistance = (RadioGroup)findViewById(R.id.rGrpDistance);
        rBtn1km = (RadioButton) findViewById(R.id.rBtn1km);
        rBtn3km = (RadioButton) findViewById(R.id.rBtn3km);
        rBtn5km = (RadioButton) findViewById(R.id.rBtn5km);

        rGrpDistance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (rGrpDistance.getCheckedRadioButtonId()) {
                    case R.id.rBtn1km:
                        setRadius(TAG_1KM);
                        searchBtn(getCurrentFocus());
                        break;
                    case R.id.rBtn3km:
                        setRadius(TAG_3KM);
                        searchBtn(getCurrentFocus());
                        break;
                    case R.id.rBtn5km:
                        setRadius(TAG_5KM);
                        searchBtn(getCurrentFocus());
                        break;
                    default:
                        break;
                }
            }
        });



        searchBasketEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchBtn(getCurrentFocus());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchBasketEdit.getWindowToken(), 0);
                return true;
            }
        });

        searchBasketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBasketBtn.setBackgroundResource(R.drawable.btnsearch2);//눌렸음 색깔변경

                searchBtn(getCurrentFocus());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchBasketBtn.getWindowToken(), 0);
            }
        });

        searchBasketBtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("searchBasketBtnYest", "성공");
                if (checkNumber != -1) {
                    String department = mLocationList.get(checkNumber).title;
                    Intent intent = new Intent();
                    intent.putExtra("MART", department);
                    setResult(1, intent);
                }
                finish();
            }
        });

        searchBasketBtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void searchBtn(View v){
        if (checkButton) {
            checkButton = false;

            searchName = searchBasketEdit.getText().toString();
            if (searchName.equals("")) {
                Toast.makeText(SettingBasketLocation.this, "검색명을 입력하지 않으셨습니다.", Toast.LENGTH_SHORT).show();
                searchBasketBtn.setBackgroundResource(R.drawable.btnsearch);//색깔 원상태로변경
                checkButton = true;

            } else {
                checkGps = settingLocation();
                if (checkGps) {
                    Log.d("settingbasket쪽 =...", "검색어 입력했다.");
                    Log.d("searchName : ", searchName);

                    searchLocation(searchName);
                }else{
                    checkButton = true;
                }
            }
        } else {
            Toast.makeText(SettingBasketLocation.this, "매장을 출력중입니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean settingLocation () {
        Log.d ( "###SetBasketLocation", "settinglocation 시작" );


        gps = new GPSInfo ( SettingBasketLocation.this );
        Log.d ( "*** GPSInfo ***", "lat : 일로 안오냐일로 안와?"+gps.isGPSEnabled () );
        if (gps.isGPSEnabled ()==true)
        {
            Log.d ( "GPS latitude", "" + gps.getLatitude () );
            Log.d ( "GPS longitude", "" + gps.getLongitude () );

            if (gps.getLatitude () == 0 && gps.getLongitude () == 0) {
                showToast("사용자 위치 파악중입니다.");
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
                    checkButton = true;
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

    public void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SettingBasketLocation.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public boolean settingLocation() {
//        gps = new GPSInfo(SettingBasketLocation.this);
//        if (gps.isGetLocation()) {
//
//            if (gps.getLatitude() == 0 && gps.getLongitude() == 0) {
//                Toast.makeText(SettingBasketLocation.this, "사용자 위치 파악중입니다.", Toast.LENGTH_SHORT).show();
//
//                return false;
//            } else {
//                setLatitude(gps.getLatitude());
//                setLongitude(gps.getLongitude());
//                return true;
//            }
//        }
//        return false;
//    }

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
                        Toast.makeText(SettingBasketLocation.this, "API_KEY의 제한 트래픽이 초과되었습니다.", Toast.LENGTH_SHORT).show();
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
        mLocationList = new ArrayList<Item>();
        mLocationList = arr;

        viewLocationList(mLocationList);
    }

    public void SaveLocation(List<Item> arr) {
        ArrayList<Item> tempList = new ArrayList<Item>();

        for (int i = 0; i < arr.size(); ++i) {
            Item item = arr.get(i);
            if (item.category.contains("마트") || item.category.contains("슈퍼")) {
                tempList.add(item);
            }
        }
        SortLocation(tempList);
    }

    public void viewLocationList(final ArrayList<Item> arr) {
        final ListView searchBasketList = (ListView) findViewById(R.id.searchBasketList);
        searchBasketList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        mSettingAdapter = new SettingBasketAdapter(arr, getLayoutInflater());

        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 해당 작업을 처리함
                        if (arr.size() == 0)
                            Toast.makeText(SettingBasketLocation.this, "마트를 찾을 수 없습니다.\n다시 시도해주세요", Toast.LENGTH_SHORT).show();

                        searchBasketList.setAdapter(mSettingAdapter);
                        searchBasketList.setOnItemClickListener(mItemClickListener);
                        searchBasketBtn.setBackgroundResource(R.drawable.btnsearch);//색깔 원상태로변경
                    }
                });
            }
        }).start();

        checkButton = true;
    }

    public AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mSettingAdapter.setAllchecked(false);
            mSettingAdapter.setChecked(position);
            mSettingAdapter.notifyDataSetChanged();

            checkNumber = position;
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        recycleView(findViewById(R.id.ySettingLocationLayout));
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