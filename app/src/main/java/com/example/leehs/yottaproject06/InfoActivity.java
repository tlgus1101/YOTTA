package com.example.leehs.yottaproject06;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leehs.yottaproject06.AboutCategoryAndFavor.FavorActivity;
import com.example.leehs.yottaproject06.AboutCategoryAndFavor.FavorDialog;
import com.example.leehs.yottaproject06.AboutMaps.GPSInfo;
import com.example.leehs.yottaproject06.AboutMaps.MapApiConst;
import com.example.leehs.yottaproject06.AboutMaps.ShowLocation;
import com.example.leehs.yottaproject06.FindLocation.Item;
import com.example.leehs.yottaproject06.FindLocation.OnFinishSearchListener;
import com.example.leehs.yottaproject06.FindLocation.Searcher;
import com.example.leehs.yottaproject06.ForSearch.Mart;
import com.example.leehs.yottaproject06.ForSearch.SearchActivity;
import com.example.leehs.yottaproject06.InsertDataToServer.ExistingInsertActivity;
import com.example.leehs.yottaproject06.InsertDataToServer.InsertDataToServer;
import com.example.leehs.yottaproject06.ShoppingCart.ProductDetailsActivity;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by MyCom on 2016-01-05.
 */

/*
제품의 검색결과에서 어떤 제품을 클릭했을 경우,
그 제품의 상세정보를 보기 위한 액티비티입니다.
이 액티비티는 기존의 상품정보를 받아왔기 때문에
여기서 가격정보를 추가할 경우, ExistingInsertActivity를 실행시켜서
가격만 입력해도 가격정보를 추가할 수 있도록 해줍니다.
 */
public class InfoActivity extends AppCompatActivity {
    private static final String SERVER_ADDRESS = "http://220.68.231.91/hyeongun/graph.php";
    public static final String PRODUCT_INDEX = "PRODUCT";

    //////////////////////////////////////////////////////////////////////////////////////////////
    public static final int TAG_FIRST = 0;

    public static final int TAG_NAME = 0;
    public static final int TAG_PRICE = 1;
    public static final int TAG_DISTANCE = 2;
    public static final int TAG_DEPARTMENT = 3;
    public static final int TAG_ID = 4;
    public static final int TAG_COUNT = 5;
    public static final int TAG_CATEGORY = 6;
    public static final int TAG_YESNO = 7;
    public static final int TAG_DATE = 8;
    //////////////////////////////////////////////////////////////////////////////////////////////

    TextView name;
    TextView mart;
    TextView won;
    TextView money1;
    TextView money2;
    TextView person1;
    TextView person2;
    TextView locate;
    ImageView imgView;
    TextView yDate1;
    TextView yDate2;

    ListItem listItem;
    Bitmap bitImage;

    Button locationBtn, yFavorBtn, yAddBasketBtn;

    boolean checkGps;

    private GPSInfo gps;
    double uLatitude;
    double uLongitude;
    double dLatitude;
    double dLongitude;
    double uDistance;

    int radius = 3000;
    int page = 1;
    String str = "1000";

    ArrayList<Item> mMartLocationList;
    ArrayList<Item> mSuperLocationList;

    public double getuLongitude() {
        return uLongitude;
    }

    public void setuLongitude(double longitude) {
        this.uLongitude = longitude;
    }

    public double getuLatitude() {
        return uLatitude;
    }

    public void setuLatitude(double latitude) {
        this.uLatitude = latitude;
    }

    public double getdLongitude() {
        return dLongitude;
    }

    public void setdLongitude(double longitude) {
        this.dLongitude = longitude;
    }

    public double getdLatitude() {
        return dLatitude;
    }

    public void setdLatitude(double latitude) {
        this.dLatitude = latitude;
    }

    List<double[]> values;
    double[] yPriceGraph;
    String yDateGraph[];
    searchWeb graphTask;
    ToastMessage toast;

    // 애플리케이션 전체에서 사용할 값
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor sharedEditor;

    public static int favorDialognum = 0;

    public static int[] Dial = new int[3];

    ArrayList<String> yFavorList1;
    ArrayList<String> yFavorList2;
    ArrayList<String> yFavorList3;
    public ArrayList<ListItem> selectItem;

    private ImageView yInfoTopLayout;

    public static double yGetFromInsideLatitude = 0;
    public static double yGetFromInsideLongitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        yInfoTopLayout = (ImageView) findViewById(R.id.yInfoTopLayout);
        yInfoTopLayout.setBackgroundDrawable(new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.info_bar)));

        selectItem = new ArrayList<ListItem>();

        if (SearchActivity.selectItem != null)//SearchActivity에 selectitem이 들어있을경우에
        {
            if (SearchActivity.selectItem.size() != 0) {
                for (int i = 0; i < SearchActivity.selectItem.size(); i++) {
                    selectItem.add(SearchActivity.selectItem.get(i));
                }
                SearchActivity.selectItem.clear();
            } else//없으면 FavorActivity에서 가져옴
            {
                for (int i = 0; i < FavorActivity.selectItem.size(); i++) {
                    selectItem.add(FavorActivity.selectItem.get(i));
                }
                FavorActivity.selectItem.clear();
            }
        } else if (FavorActivity.selectItem.size() != 0)//FavorActivity.selectItem.size()가 0이 아닐때 FavorActivity에서 가져옴
        {
            for (int i = 0; i < FavorActivity.selectItem.size(); i++) {
                selectItem.add(FavorActivity.selectItem.get(i));
            }
            FavorActivity.selectItem.clear();
        }

        Intent intent = getIntent();
        listItem = (ListItem) intent.getSerializableExtra("TEXT");
        bitImage = (Bitmap) intent.getParcelableExtra("IMAGE");
        toast = new ToastMessage(InfoActivity.this);

        checkGps = false;

        yFavorBtn = (Button) findViewById(R.id.yFavorBtn);
        yAddBasketBtn = (Button) findViewById(R.id.yAddBasketBtn);

        //UI에 있는 name이런거 연결
        name = (TextView) findViewById(R.id.name);
        mart = (TextView) findViewById(R.id.mart);
        won = (TextView) findViewById(R.id.won);
        money1 = (TextView) findViewById(R.id.money1);
        money2 = (TextView) findViewById(R.id.money2);
        person1 = (TextView) findViewById(R.id.person1);
        person2 = (TextView) findViewById(R.id.person2);
        locate = (TextView) findViewById(R.id.locate);
        imgView = (ImageView) findViewById(R.id.ImageView);
        yDate1 = (TextView) findViewById(R.id.yDate1);
        yDate2 = (TextView) findViewById(R.id.yDate2);

        locationBtn = (Button) findViewById(R.id.locationBtn);

        name.setText(listItem.getData(0));
        mart.setText(listItem.getData(3));
        //마트에 따른 컬러설정
        if(listItem.getData(3).contains("이마트")){
            if(listItem.getData(3).contains("에브리데이")) {
                Log.d("SearchAdapter","에브리데이는 이마트랑 다름.");
                mart.setTextColor(0xFF000000); //이외의 정보는 검은색으로 출력.
            }else{
                mart.setTextColor(0xFFE6B611); //어두운 노랑
            }
        }else if(listItem.getData(3).contains("롯데마트")){
            if(listItem.getData(3).contains("999")){
                Log.d("SearchAdapter","999는 롯데마트랑 다름.");
                mart.setTextColor(0xFF000000); //이외의 정보는 검은색으로 출력.
            }else{
                mart.setTextColor(0xFF9e0000); //빨강
            }
        }else if(listItem.getData(3).contains("홈플러스")){
            if(listItem.getData(3).contains("익스프레스")){
                Log.d("SearchAdapter","익스프레스는 홈플러스랑 다름.");
                mart.setTextColor(0xFF000000); //이외의 정보는 검은색으로 출력.
            }else{
                mart.setTextColor(0xFF1313A6); //어두운 파랑
            }
        }else{
            mart.setTextColor(0xFF000000); //이외의 정보는 검은색으로 출력.
        }

        if (listItem.getData(8).equals(InsertDataToServer.getDateString()) == false) {
            Toast.makeText(getApplicationContext(), "현재 등록된 날짜는 금일의 가격이 아닙니다!", Toast.LENGTH_SHORT).show();
            Log.d("SearchAdapter", "날짜가 일치하기 때문에 텍스트 색깔을 변경합니다 - " + listItem.getData(8));
            won.setTextColor(0xFF808080);
        }

        won.setText(listItem.getData(1));

        imgView.setImageBitmap(bitImage);

        int num = Integer.parseInt(listItem.getData(1));

        //그래프에 사용되는 변수
        values = new ArrayList<double[]>();
        yPriceGraph = new double[7]; //가격과 날짜는 7일치만 받기 때문에 정적으로 선언.
        yDateGraph = new String[7];
        //그래프에 사용되는 변수

        ///////선호제품//////////////////
        sharedPref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        sharedEditor = sharedPref.edit();
        ///////선호제품//////////////////


        /////////////////////////////////////////////////////////퍼센트 구하기
        person1.setText(Percent1(selectItem));
        person2.setText(Percent2(selectItem, Percent1(selectItem)));
        money1.setText(PriceInfo1(selectItem));
        money2.setText(PriceInfo2(selectItem, PriceInfo1(selectItem)));
        yDate1.setText("(" + GetDate1(selectItem) + ")");
        yDate2.setText("(" + GetDate2(selectItem, GetDate1(selectItem)) + ")");
        ///////////그래프 구하기

        for (int i = 1; i < 7; i++) {
            yDateGraph[i - 1] = getDateString(6 - i);
        }

        String date = GetDate1(selectItem).replace("2016-", "");

        //오늘 날짜일 때 받음
        if (date.equals(getDateString(0))) {
            yPriceGraph[5] = Integer.valueOf(PriceInfo1(selectItem)).doubleValue();
        }
        graphTask = new searchWeb();
        graphTask.execute(SERVER_ADDRESS);

        ///////GPS
        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGps = settingLocation();
                if (checkGps) {
                    if (listItem.getData(3).contains("이마트") || listItem.getData(3).contains("롯데마트") ||
                            listItem.getData(3).contains("홈플러스")) {
                        if (listItem.getData(3).contains("에브리데이") || listItem.getData(3).contains("999") ||
                                listItem.getData(3).contains("익스프레스")) {
                            searchLocation(listItem.getData(3));
                        } else {
                            //대형마트 일때만 내부적으로 검색을 돌림.
                            Mart.SearchMartInInfo(listItem.getData(3));

                            Log.d("sadsadsadsa","는 : " + listItem.getData(2));

                            Intent intent = new Intent(InfoActivity.this, ShowLocation.class);
                            intent.putExtra("ULAT", getuLatitude());
                            intent.putExtra("ULNG", getuLongitude());
                            intent.putExtra("DLAT", yGetFromInsideLatitude);
                            intent.putExtra("DLNG", yGetFromInsideLongitude);
                            intent.putExtra("DISTANCE", Double.parseDouble(listItem.getData(2)));

                            startActivity(intent);
                        }
                    } else {
                        searchLocation(listItem.getData(3));
                    }


                }
            }
        });

        yAddBasketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap sendImage = bitImage;
                Parcelable forImage = sendImage;

                Intent intent = new Intent(InfoActivity.this, ProductDetailsActivity.class);
                intent.putExtra(PRODUCT_INDEX, new ListItem(listItem.getData(TAG_NAME), listItem.getData(TAG_PRICE),
                        listItem.getData(TAG_DISTANCE), listItem.getData(TAG_DEPARTMENT), listItem.getData(TAG_ID)
                        , listItem.getData(TAG_COUNT), listItem.getData(TAG_CATEGORY), listItem.getData(TAG_DATE)));
                intent.putExtra("IMAGE", forImage);
                startActivity(intent);
            }
        });
    }

    public boolean settingLocation () {
        Log.d ( "###InfoActivity", "settinglocation 시작" );

        gps = new GPSInfo ( InfoActivity.this );
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
                setuLatitude(gps.getLatitude());
                setuLongitude(gps.getLongitude());

                sharedEditor.remove ( "Latitude_" );
                sharedEditor.remove ( "Longitude_" );

                Log.d ( "*** GPSInfo ***", "lat : " + getuLatitude() );
                Log.d ( "*** GPSInfo ***", "lon : " + getuLongitude() );

                sharedEditor.putString ( "Latitude_", "" + getuLatitude() );
                sharedEditor.commit ();
                sharedEditor.putString ( "Longitude_", "" + getuLongitude() );
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
                    setuLatitude(Double.parseDouble(lat));
                    setuLongitude(Double.parseDouble(lot));

                    Log.d("*** GPSInfo ***", "lat : " + lat);
                    Log.d("*** GPSInfo ***", "lon : " + lot);
                    Log.d("*** GPSInfo ***", "getLatitude : " + getuLatitude());
                    Log.d("*** GPSInfo ***", "getLongitude : " + getuLongitude());

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
//        yGps = new GPSInfo(InfoActivity.this);
//        if (yGps.isGetLocation()) {
//            if (yGps.getLatitude() == 0 && yGps.getLongitude() == 0) {
//                showToast("사용자 위치 파악중입니다.");
//
//                return false;
//            } else {
//                setuLatitude(yGps.getLatitude());
//                setuLongitude(yGps.getLongitude());
//                return true;
//            }
//        }
//        return false;
//    }

    public void searchLocation(final String MartName) {
        Searcher searcher = new Searcher();

        searcher.searchKeywordSingle(getApplicationContext(), MartName, getuLatitude(), getuLongitude(), radius, page,
                MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) { }

                    @Override
                    public void onSuccessSingle(Item item) {
                        setdLatitude(item.latitude);
                        setdLongitude(item.longitude);
                        uDistance = item.distance;

                        Log.d("InfoActivity", "distance lat : " + getdLatitude());
                        Log.d("InfoActivity", "distance lng : "+getdLongitude());
                        Log.d("InfoActivity", "distance : "+uDistance);

                        Intent intent = new Intent(InfoActivity.this, ShowLocation.class);
                        intent.putExtra("ULAT", getuLatitude());
                        intent.putExtra("ULNG", getuLongitude());
                        intent.putExtra("DLAT", getdLatitude());
                        intent.putExtra("DLNG", getdLongitude());
                        intent.putExtra("DISTANCE", uDistance);

                        startActivity(intent);
                    }

                    @Override
                    public void onFail() {
                        Log.d("CategoryActivity", "Search 실패");
                        showToast("API_KEY의 제한 트래픽이 초과되었습니다.");
                    }
                });
    }


    public void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(InfoActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    ///////////////////////////////////////////////1등, 2등 가격 보여줄때 누르면 메시지
    public void btnInfoOnClick(View v) {
        toast.showToast("가장 많이 등록된 가격을 보여줍니다", Toast.LENGTH_SHORT);
    }

    ///////////////////////////////////////////////////////////////////
    /*
   기존에 존재하는 정보일 경우, 추가적으로 정보를 입력하지 않고 가격정보만 입력해도 가격정보가 올라가게끔 하기 위한 액티비티를 실행시켜줍니다.
    */
    public void ExistingInsertOnClick(View v) {
        Intent intent = new Intent(this, ExistingInsertActivity.class);
        Parcelable forImage = bitImage;

        intent.putExtra("IMAGE", forImage);
        intent.putExtra("TEXT", new ListItem(listItem.getData(0), listItem.getData(1),
                listItem.getData(2), listItem.getData(3), listItem.getData(4),
                listItem.getData(5), listItem.getData(6), listItem.getData(8)));
        startActivity(intent); //만약 InfoActivity에서 정보를 다시 받아오려면 추후에 startActivity가 아닌 startActivityForResult메소드를 이용할 것 !!!!!!


//        if (mMartLocationList.get(0).distance > Double.parseDouble(str) || mSuperLocationList.get(0).distance > Double.parseDouble(str)) {
//            showToast("현재 마트가 아닙니다.");
//        } else if (mMartLocationList.get(0).distance > mSuperLocationList.get(0).distance) {
//            if (mSuperLocationList.get(0).title.equals(listItem.getData(3))) {
//                Intent intent = new Intent(this, ExistingInsertActivity.class);
//                Parcelable forImage = bitImage;
//
//                intent.putExtra("IMAGE", forImage);
//                intent.putExtra("TEXT", new ListItem(listItem.getData(0), listItem.getData(1),
//                        listItem.getData(2), listItem.getData(3), listItem.getData(4),
//                        listItem.getData(5), listItem.getData(6), listItem.getData(8)));
//                startActivity(intent); //만약 InfoActivity에서 정보를 다시 받아오려면 추후에 startActivity가 아닌 startActivityForResult메소드를 이용할 것 !!!!!!
//            } else {
//                showToast("현재 마트가 아닙니다.");
//            }
//        } else if (mSuperLocationList.get(0).distance > mMartLocationList.get(0).distance) {
//            if (mMartLocationList.get(0).title.equals(listItem.getData(3))) {
//                Intent intent = new Intent(this, ExistingInsertActivity.class);
//                Parcelable forImage = bitImage;
//
//                intent.putExtra("IMAGE", forImage);
//                intent.putExtra("TEXT", new ListItem(listItem.getData(0), listItem.getData(1),
//                        listItem.getData(2), listItem.getData(3), listItem.getData(4),
//                        listItem.getData(5), listItem.getData(6), listItem.getData(8)));
//                startActivity(intent); //만약 InfoActivity에서 정보를 다시 받아오려면 추후에 startActivity가 아닌 startActivityForResult메소드를 이용할 것 !!!!!!
//            } else {
//                showToast("현재 마트가 아닙니다.");
//            }
//        }
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
        for (int i = 0; i < mMartLocationList.size(); ++i) {
            Log.d("sort location name", mMartLocationList.get(i).title);
        }
    }

    public void SaveLocationMart(List<Item> arr) {
        mMartLocationList.clear();
        for (int i = 0; i < 5; ++i) {
            Item item = arr.get(i);
            mMartLocationList.add(i, item);
        }
        SortLocation(mMartLocationList);
    }

    public void SaveLocationSuper(List<Item> arr) {
        mSuperLocationList.clear();
        for (int i = 0; i < 5; ++i) {
            Item item = arr.get(i);
            mSuperLocationList.add(i, item);
        }
        SortLocation(mSuperLocationList);
    }


    public void FavorBtnOnClick(View v) {
        Intent intent = new Intent(this, FavorDialog.class);
        startActivity(intent);
    }

    public void Favor() {
        Log.d("InfoActivity", " Favor(); 언제 들어오니: " + favorDialognum);
        if (favorDialognum == 1) {
            Log.d("InfoActivity", "입력할 카테코리 값은 : " + listItem.getData(0));
            Log.d("InfoActivity", "현재 사이즈는 : " + yFavorList1.size());
            int yCheck1 = 0;
            FavorActivity.change = true;

            //sharedEditor에 데이터 넣고, commit하면 됨.

            for (int i = 0; i < yFavorList1.size(); ++i) {
                if (listItem.getData(0).equals(yFavorList1.get(i))) {
                    //yFavorBtn.setText("관심등록");
                    toast.showToast("관심등록을 해제합니다.", Toast.LENGTH_SHORT);
                    yFavorList1.remove(i);
                    ++yCheck1;
                    break;
                }
            }

            if (yCheck1 == 0) {
                toast.showToast("찜목록에 추가했습니다!.", Toast.LENGTH_SHORT);
                //yFavorBtn.setText("등록됨");
                yFavorList1.add(listItem.getData(0));
            }

            sharedEditor.putString("ListSize1", "" + yFavorList1.size());

            for (int i = 0; i < yFavorList1.size(); ++i) {
                sharedEditor.remove("List1_" + i);
                sharedEditor.putString("List1_" + i, yFavorList1.get(i));
            }
            sharedEditor.commit();
        }

        if (favorDialognum == 2) {
            Log.d("InfoActivity", "입력할 카테코리 값은 : " + listItem.getData(0));
            Log.d("InfoActivity", "현재 사이즈는 : " + yFavorList2.size());
            int yCheck2 = 0;
            FavorActivity.change = true;

            //sharedEditor에 데이터 넣고, commit하면 됨.

            for (int i = 0; i < yFavorList2.size(); ++i) {
                if (listItem.getData(0).equals(yFavorList2.get(i))) {
                    yFavorBtn.setText("관심등록");
                    toast.showToast("관심등록을 해제합니다.", Toast.LENGTH_SHORT);
                    yFavorList2.remove(i);
                    ++yCheck2;
                    break;
                }
            }

            if (yCheck2 == 0) {
                toast.showToast("찜목록에 추가했습니다!.", Toast.LENGTH_SHORT);
                // yFavorBtn.setText("등록됨");
                yFavorList2.add(listItem.getData(0));
            }

            sharedEditor.putString("ListSize2", "" + yFavorList2.size());

            for (int i = 0; i < yFavorList2.size(); ++i) {
                sharedEditor.remove("List2_" + i);
                sharedEditor.putString("List2_" + i, yFavorList2.get(i));
            }
            sharedEditor.commit();
        }

        if (favorDialognum == 3) {
            Log.d("InfoActivity", "입력할 카테코리 값은 : " + listItem.getData(0));
            Log.d("InfoActivity", "현재 사이즈는 : " + yFavorList3.size());
            int yCheck3 = 0;
            FavorActivity.change = true;

            //sharedEditor에 데이터 넣고, commit하면 됨.

            for (int i = 0; i < yFavorList3.size(); ++i) {
                if (listItem.getData(0).equals(yFavorList3.get(i))) {
                    yFavorBtn.setText("관심등록");
                    toast.showToast("관심등록을 해제합니다.", Toast.LENGTH_SHORT);
                    yFavorList3.remove(i);
                    ++yCheck3;
                    break;
                }
            }

            if (yCheck3 == 0) {
                toast.showToast("찜목록에 추가했습니다!.", Toast.LENGTH_SHORT);
                // yFavorBtn.setText("등록됨");
                yFavorList3.add(listItem.getData(0));
            }

            sharedEditor.putString("ListSize3", "" + yFavorList3.size());

            for (int i = 0; i < yFavorList3.size(); ++i) {
                sharedEditor.remove("List3_" + i);
                sharedEditor.putString("List3_" + i, yFavorList3.get(i));
            }
            sharedEditor.commit();
        }
    }


    private class searchWeb extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            Log.d("여기는 들어오나요오오오옹", "searchWeb");
            StringBuilder jsonHtml = new StringBuilder();
            try {
                String searchname = URLEncoder.encode(listItem.getData(0), "utf-8");
                String searchmart = URLEncoder.encode(listItem.getData(3), "utf-8");

                Log.d("여기는 들어오나요오오오옹", "이름" + searchname);
                Log.d("여기는 들어오나요오오오옹", "마트" + searchmart);

                URL url = new URL(urls[0] + "?name=" + searchname + "&department=" + searchmart);

                Log.d("여기는 들어오나요오오오옹", " url" + url);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for (; ; ) {
                            String line = br.readLine();
                            if (line == null) break;
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return jsonHtml.toString();
        }

        protected void onPostExecute(String str) {
            String da, department, da2;
            int p;

            try {
                JSONObject jsonObj = new JSONObject(str);
                JSONArray items = jsonObj.getJSONArray("result");

                Log.d("여기는 들어오나요오오오옹", "들어온" + items.length());

                for (int i = 0; i < items.length(); ++i) {
                    JSONObject jo = items.getJSONObject(i);
                    da = jo.getString("goods_date");
                    p = jo.getInt("price");
                    department = jo.getString("department");

                    Log.d("여기는 들어오나요오오오옹", "" + department);
                    Log.d("여기는 들어오나요오오오옹", "" + listItem.getData(3));
                    da2 = da.replace("2016-", "");
                    da = da2;

                    for (int j = 0; j < 6; j++) {
                        String day = yDateGraph[j];
                        if (yDateGraph[j].equals(da)) {
                            yPriceGraph[j] = p;
                        }
                    }
                }
                values.add(yPriceGraph);
            } catch (JSONException je) {
                je.printStackTrace();
            }
            Graph();
        }
    }

    ///날짜
    public String getDateString(int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd", Locale.KOREA);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -i);
        String day = sdf.format(c.getTime());
        return day;
    }

    public void Graph() {
        /* 그래프 출력을 위한 그래픽 속성 지정객체 */
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

        // 상단 표시 제목과 글자 크기
        renderer.setChartTitle("가격변동폭");
        renderer.setChartTitleTextSize(50);

        // 분류에 대한 이름
        String[] titles = new String[]{""};

        // 항목을 표시하는데 사용될 색상값
        int[] colors = new int[]{Color.BLUE};

        // 분류명 글자 크기 및 각 색상 지정
        renderer.setLegendTextSize(30);
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(colors[i]);
            renderer.addSeriesRenderer(r);
        }

        //라인 표시
        renderer.setShowGrid(true);
        //x,y축 스크롤 표시값
        String str = SetHighestPrice(selectItem);
        double num = Double.parseDouble(str);

        double[] panLimits = {0, 10, 0, num + 1000}; //MinimumX, MaximumX, MinimumY, MaximumY
        renderer.setPanLimits(panLimits);

        // X,Y축 항목이름과 글자 크기
        //renderer.setXTitle("월");
        //renderer.setYTitle("판매량");
        //renderer.setAxisTitleTextSize(30);

        // 수치값 글자 크기 / X축 최소,최대값 / Y축 최소,최대값
        renderer.setLabelsTextSize(30);
        renderer.setXAxisMin(0);
        renderer.setXAxisMax(7);
        renderer.setYAxisMin(0);
        renderer.setYAxisMax(num + 1500);

        //그래프 위에 값 표시하기
        renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
        renderer.getSeriesRendererAt(0).setChartValuesTextSize(50);
        renderer.getSeriesRendererAt(0).setChartValuesTextAlign(Paint.Align.CENTER);
        renderer.getSeriesRendererAt(0).setColor(Color.BLACK);

        // X,Y축 라인 색상
        renderer.setAxesColor(Color.GRAY);
        // 상단제목, X,Y축 제목, 수치값의 글자 색상
        renderer.setLabelsColor(Color.GRAY);

        for (int i = 1; i < 7; i++) {
            renderer.addXTextLabel(i, getDateString(6 - i));
        }


        //여백의 색상을 설정
        renderer.setMarginsColor(Color.WHITE);

        // X축의 표시 간격
        renderer.setXLabels(0);
        // Y축의 표시 간격
        renderer.setYLabels(5);

        // X,Y축 정렬방향
        renderer.setXLabelsAlign(Paint.Align.LEFT);
        renderer.setYLabelsAlign(Paint.Align.LEFT);
        // X,Y축 스크롤 여부 ON/OFF
        renderer.setPanEnabled(false, true); //첫번째 인수가 좌우 , 두번째 인수가 상하
        //renderer.setPanEnabled(true, true);
        // ZOOM기능 ON/OFF
        renderer.setZoomEnabled(true, true);
        // ZOOM 비율
        renderer.setZoomRate(1.0f);
        // 막대간 간격
        renderer.setBarSpacing(0.2f);

        // 설정 정보 설정
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        for (int i = 0; i < titles.length; i++) {
            CategorySeries series = new CategorySeries(titles[i]);
            double[] v = values.get(i);
            int seriesLength = v.length;
            for (int k = 0; k < seriesLength; k++) {
                series.add(v[k]);
            }
            dataset.addSeries(series.toXYSeries());
        }

        // 그래프 객체 생성
        GraphicalView gv = ChartFactory.getBarChartView(this, dataset,
                renderer, BarChart.Type.DEFAULT);

        // 그래프를 LinearLayout에 추가
        LinearLayout llBody = (LinearLayout) findViewById(R.id.llBody);
        llBody.addView(gv);
    }

    public void onResume() {
        super.onResume();

        Favor();
        favorDialognum = 0;
        for (int i = 0; i < 3; i++) {
            Dial[i] = 0;
        }

        yFavorList1 = new ArrayList<String>();
        yFavorList2 = new ArrayList<String>();
        yFavorList3 = new ArrayList<String>();

        int yGetListSize1 = 0;
        int yGetListSize2 = 0;
        int yGetListSize3 = 0;

        String yTempGet1 = sharedPref.getString("ListSize1", "");
        String yTempGet2 = sharedPref.getString("ListSize2", "");
        String yTempGet3 = sharedPref.getString("ListSize3", "");

        Log.d("InfoActivity", "선호 카운트 : " + yTempGet1);
        Log.d("InfoActivity", "선호 카운트 : " + yTempGet2);
        Log.d("InfoActivity", "선호 카운트 : " + yTempGet3);

        if (!yTempGet1.equals("")) {
            yGetListSize1 = Integer.parseInt(yTempGet1);
            for (int i = 0; i < yGetListSize1; i++) {
                yFavorList1.add(sharedPref.getString("List1_" + i, null));
                Log.d("InfoActivity", i + "번째 선호 내용물 : " + yFavorList1.get(i));
            }

            for (int i = 0; i < yFavorList1.size(); ++i) {
                if (listItem.getData(0).equals(yFavorList1.get(i))) {
                    // yFavorBtn.setText("등록됨");
                    Dial[0] = 1;
                    break;
                }
            }
        }
        if (!yTempGet2.equals("")) {
            yGetListSize2 = Integer.parseInt(yTempGet2);

            for (int i = 0; i < yGetListSize2; i++) {
                yFavorList2.add(sharedPref.getString("List2_" + i, null));
                Log.d("InfoActivity", i + "번째 선호 내용물 : " + yFavorList2.get(i));
            }

            for (int i = 0; i < yFavorList2.size(); ++i) {
                if (listItem.getData(0).equals(yFavorList2.get(i))) {
                    // yFavorBtn.setText("등록됨");
                    Dial[1] = 2;
                    break;
                }
            }
        }
        if (!yTempGet3.equals("")) {
            yGetListSize3 = Integer.parseInt(yTempGet3);

            for (int i = 0; i < yGetListSize3; i++) {
                yFavorList3.add(sharedPref.getString("List3_" + i, null));
                Log.d("InfoActivity", i + "번째 선호 내용물 : " + yFavorList3.get(i));
            }

            for (int i = 0; i < yFavorList3.size(); ++i) {
                if (listItem.getData(0).equals(yFavorList3.get(i))) {
                    // yFavorBtn.setText("등록됨");
                    Dial[2] = 3;
                    break;
                }
            }
        }
    }


    /////////////////////////////////////////////////////////퍼센트 구하기(사람)
    public String Percent1(ArrayList<ListItem> arr) {
        String yPCount;
        int count = 0;

        ArrayList<ListItem> yForCompareList = new ArrayList<ListItem>();

        for (int i = 0; i < arr.size(); ++i) {
            if (arr.get(i).getData(8).equals(InsertDataToServer.getDateString())) {
                yForCompareList.add(new ListItem(arr.get(i).getData(0), arr.get(i).getData(1), arr.get(i).getData(2),
                        arr.get(i).getData(3), arr.get(i).getData(4), arr.get(i).getData(5),
                        arr.get(i).getData(6), arr.get(i).getData(8)));
                ++count;
            }
        }

        if (yForCompareList.size() > 0) {
            BubbleSort(yForCompareList, 5);
            yPCount = yForCompareList.get(yForCompareList.size() - 1).getData(5);
        } else if (arr.size() > 0) {
            BubbleSort(arr, 5);
            yPCount = arr.get(arr.size() - 1).getData(5);
        } else {
            yPCount = "0";
        }

        return yPCount;
    }

    public String Percent2(ArrayList<ListItem> arr, String first) {
        String yPCount;
        int count = 0;

        ArrayList<ListItem> yForCompareList = new ArrayList<ListItem>();

        for (int i = 0; i < arr.size(); ++i) {
            if (arr.get(i).getData(8).equals(InsertDataToServer.getDateString())) {
                yForCompareList.add(new ListItem(arr.get(i).getData(0), arr.get(i).getData(1), arr.get(i).getData(2),
                        arr.get(i).getData(3), arr.get(i).getData(4), arr.get(i).getData(5),
                        arr.get(i).getData(6), arr.get(i).getData(8)));
                ++count;
            }
        }

        if (yForCompareList.size() > 1) {
            BubbleSort(yForCompareList, 5);
            yPCount = yForCompareList.get(yForCompareList.size() - 2).getData(5);
        } else if (arr.size() > 1) {
            //1에서 넣었던거 빼야됨.
            BubbleSort(arr, 5);
            if (first != arr.get(arr.size() - 1).getData(5)) {
                yPCount = arr.get(arr.size() - 1).getData(5);
            } else {
                yPCount = arr.get(arr.size() - 2).getData(5);
            }
        } else {
            yPCount = "0";
        }

        return yPCount;
    }

    /////////////////////////////////////////////////////////1등 가격
    public String PriceInfo1(ArrayList<ListItem> arr) {
        String yPrice;
        int count = 0;

        ArrayList<ListItem> yForCompareList = new ArrayList<ListItem>();

        Log.d("FavorActivity", "arr.size()" + arr.size());

        for (int i = 0; i < arr.size(); ++i) {
            if (arr.get(i).getData(8).equals(InsertDataToServer.getDateString())) {
                yForCompareList.add(new ListItem(arr.get(i).getData(0), arr.get(i).getData(1), arr.get(i).getData(2),
                        arr.get(i).getData(3), arr.get(i).getData(4), arr.get(i).getData(5),
                        arr.get(i).getData(6), arr.get(i).getData(8)));
                ++count;
            }
        }

        if (yForCompareList.size() > 0) {
            BubbleSort(yForCompareList, 5);
            yPrice = yForCompareList.get(yForCompareList.size() - 1).getData(1);
        } else if (arr.size() > 0) {
            BubbleSort(arr, 5);
            yPrice = arr.get(arr.size() - 1).getData(1);
        } else {
            yPrice = "0";
        }

        return yPrice;
    }

    //2등가격
    public String PriceInfo2(ArrayList<ListItem> arr, String first) {
        String yPrice;
        int count = 0;

        ArrayList<ListItem> yForCompareList = new ArrayList<ListItem>();

        for (int i = 0; i < arr.size(); ++i) {
            if (arr.get(i).getData(8).equals(InsertDataToServer.getDateString())) {
                yForCompareList.add(new ListItem(arr.get(i).getData(0), arr.get(i).getData(1), arr.get(i).getData(2),
                        arr.get(i).getData(3), arr.get(i).getData(4), arr.get(i).getData(5),
                        arr.get(i).getData(6), arr.get(i).getData(8)));
                ++count;
            }
        }

        if (yForCompareList.size() > 1) {
            BubbleSort(yForCompareList, 5);
            yPrice = yForCompareList.get(yForCompareList.size() - 2).getData(1);
        } else if (arr.size() > 1) {
            //1에서 넣었던거 빼야됨.
            BubbleSort(arr, 5);
            if (first != arr.get(arr.size() - 1).getData(1)) {
                yPrice = arr.get(arr.size() - 1).getData(1);
            } else {
                yPrice = arr.get(arr.size() - 2).getData(1);
            }
        } else {
            yPrice = "0";
        }

        return yPrice;
    }

    public String GetDate1(ArrayList<ListItem> arr) {
        String yDate;
        int count = 0;

        ArrayList<ListItem> yForCompareList = new ArrayList<ListItem>();

        for (int i = 0; i < arr.size(); ++i) {
            if (arr.get(i).getData(8).equals(InsertDataToServer.getDateString())) {
                yForCompareList.add(new ListItem(arr.get(i).getData(0), arr.get(i).getData(1), arr.get(i).getData(2),
                        arr.get(i).getData(3), arr.get(i).getData(4), arr.get(i).getData(5),
                        arr.get(i).getData(6), arr.get(i).getData(8)));
                ++count;
            }
        }

        if (yForCompareList.size() > 0) {
            BubbleSort(yForCompareList, 5);
            yDate = yForCompareList.get(yForCompareList.size() - 1).getData(8);
        } else if (arr.size() > 0) {
            BubbleSort(arr, 5);
            yDate = arr.get(arr.size() - 1).getData(8);
        } else {
            yDate = "";
        }

        return yDate;
    }

    public String GetDate2(ArrayList<ListItem> arr, String first) {
        String yDate;
        int count = 0;

        ArrayList<ListItem> yForCompareList = new ArrayList<ListItem>();


        for (int i = 0; i < arr.size(); ++i) {
            if (arr.get(i).getData(8).equals(InsertDataToServer.getDateString())) {
                yForCompareList.add(new ListItem(arr.get(i).getData(0), arr.get(i).getData(1), arr.get(i).getData(2),
                        arr.get(i).getData(3), arr.get(i).getData(4), arr.get(i).getData(5),
                        arr.get(i).getData(6), arr.get(i).getData(8)));
                ++count;
            }
        }

        if (yForCompareList.size() > 1) {
            BubbleSort(yForCompareList, 5);
            yDate = yForCompareList.get(yForCompareList.size() - 2).getData(8);
        } else if (arr.size() > 1) {
            //1에서 넣었던거 빼야됨.
            BubbleSort(arr, 5);

            if (first != arr.get(arr.size() - 1).getData(8)) {
                yDate = arr.get(arr.size() - 1).getData(8);
            } else {
                yDate = arr.get(arr.size() - 2).getData(8);
            }
        } else {
            yDate = "";
        }

        return yDate;
    }

    /////////////////////////////////////////////////////// 여기까지 황정남씨

    /*
     이 메소드는 선택적으로 버블정렬을 할 수 있습니다.
     비교하고 싶은 내용을 2번째 인자로 넘겨서 실행할 수 있습니다.
     예를 들어 가격별로 비교하고 싶다면 1을 넘기면 되고
     거리순이라면 2, 카운트 순이라면 5를 넘겨주면 됩니다.
      */
    public void BubbleSort(ArrayList<ListItem> arr, int nIndex) {
        int last = arr.size() - 1;
        ListItem temp;

        if (nIndex == 1) {
            for (int i = last; i > 0; i--) {
                for (int n = 0; n < i; n++) {
                    if (Integer.parseInt(arr.get(n).getData(nIndex)) > Integer.parseInt(arr.get(n + 1).getData(nIndex))) {
                        temp = new ListItem(arr.get(n + 1).getData(0), arr.get(n + 1).getData(1),
                                arr.get(n + 1).getData(2), arr.get(n + 1).getData(3), arr.get(n + 1).getData(4)
                                , arr.get(n + 1).getData(5), arr.get(n + 1).getData(6), arr.get(n + 1).getData(8));
                        arr.get(n + 1).changeList(arr.get(n));
                        arr.get(n).changeList(temp);
                    }
                }
            }
        } else {
            for (int i = last; i > 0; i--) {
                for (int n = 0; n < i; n++) {
                    if (Double.parseDouble(arr.get(n).getData(nIndex)) > Double.parseDouble(arr.get(n + 1).getData(nIndex))) {
                        temp = new ListItem(arr.get(n + 1).getData(0), arr.get(n + 1).getData(1),
                                arr.get(n + 1).getData(2), arr.get(n + 1).getData(3), arr.get(n + 1).getData(4)
                                , arr.get(n + 1).getData(5), arr.get(n + 1).getData(6), arr.get(n + 1).getData(8));
                        arr.get(n + 1).changeList(arr.get(n));
                        arr.get(n).changeList(temp);
                    }
                }
            }
        }
    }

    /*
        형운이가 만든 그래프에 가장 높은 가격 찾아내는 메소드
     */
    public String SetHighestPrice(ArrayList<ListItem> arr) {
        String yPrice;

        BubbleSort(arr, 1);

        yPrice = arr.get(arr.size() - 1).getData(1);
        return yPrice;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recycleView(findViewById(R.id.yInfoTopLayout));
    }

    private void recycleView(View view) {
        if (view != null) {
            Drawable bg = view.getBackground();
            if (bg != null) {
                bg.setCallback(null);
                ((BitmapDrawable) bg).getBitmap().recycle();
                view.setBackgroundDrawable(null);
            }
        }
    }
}