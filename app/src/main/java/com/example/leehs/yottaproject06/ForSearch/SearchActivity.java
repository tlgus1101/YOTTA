package com.example.leehs.yottaproject06.ForSearch;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leehs.yottaproject06.AboutCategoryAndFavor.CategoryActivity;
import com.example.leehs.yottaproject06.AboutMaps.GPSInfo;
import com.example.leehs.yottaproject06.AboutMaps.MapApiConst;
import com.example.leehs.yottaproject06.FindLocation.Item;
import com.example.leehs.yottaproject06.FindLocation.OnFinishSearchListener;
import com.example.leehs.yottaproject06.FindLocation.Searcher;
import com.example.leehs.yottaproject06.ForSettings.MartOptionActivity;
import com.example.leehs.yottaproject06.InfoActivity;
import com.example.leehs.yottaproject06.InsertDataToServer.InsertDataToServer;
import com.example.leehs.yottaproject06.InsertDataToServer.InsertLocationDialog;
import com.example.leehs.yottaproject06.ListItem;
import com.example.leehs.yottaproject06.R;
import com.example.leehs.yottaproject06.ToastMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by Lee HS on 2015-12-25.
 */

/*
서버로 부터 입력한 텍스트 및 이미지 정보를 받아옵니다.
받아올 때는 단순히 받아오지 않고 버블소트를 통해서 가격순/거리순으로 나열된 정보를 선택해서 받아올 수 있습니다.
 */
public class SearchActivity extends ListActivity {

    public static final int PRODUCT_NAME = 1;
    public static final int PRODUCT_CATEGORY = 2;

    private static final int PRICE = 0;
    private static final int DISTANCE = 1;

    private static final int MARTOPTIONCHECK = 123;

    /////////////////////////

    private Button yCategoryBtn, yBtnSearch, ySettingBtn;

    boolean checkButton;
    boolean checkGps;

    /////////////////////////
    public static GPSInfo gps;
    public static double dis;
    ToastMessage toast;

    double latitude = 0;
    double longitude = 0;
    int radius = 3000;
    int page = 1;
    ArrayList<Item> mMartLocationList;
    ArrayList<Item> mSuperLocationList;
    String settingDistance = "1000"; //형운아 나중에 고쳐줘 형운아 나중에 고쳐줘 형운아 나중에 고쳐줘 형운아 나중에 고쳐줘 형운아 나중에 고쳐줘 형운아 나중에 고쳐줘 형운아 나중에 고쳐줘 형운아 나중에 고쳐줘 형운아 나중에 고쳐줘 형운아 나중에 고쳐줘 형운아 나중에 고쳐줘 형운아 나중에 고쳐줘

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

    //////////////////////////
    public static ArrayList<ListItem> searchItem;
    public static ArrayList<ListItem> selectItem;
    public ArrayList<ListItem> searchItemAll;

    EditText yEditProductName;
    RadioButton rBtnPrice;
    RadioButton rBtnDistance;

    int sNum = -1;
    String searchName = "";

    /////////////////////////////// 이하 리스트뷰에 넣기

    ListView listBox;

    ///// 이하 이미지 추가하기

    private List<SearchProduct> ySearchData;

    private ArrayList<HashMap<String, Bitmap>> bitList;

    // 애플리케이션 전체에서 공유되는 값.
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor sharedEditor;

    // 마트설정에서 체크한 마트의 설정 정보를 담기 위한 ArrayList
    private ArrayList<String> ySavedMartList;

    public static int ySetCategory = -1;
    private int yLastGetCategory = -1; //라디오버튼을 눌러서 카테고리 검색을 하기 위한 변수.
    boolean yCheckRadio = false;
    boolean yMartSavedCheck = false; //핸드폰에 저장된 마트가 있을때만 true로.


    public static String musthotname;
    public static boolean musthotchoice;

    //리스트뷰를 동적으로 불러오기 위한 변수들.
    private SearchAdapter ySearchAdapter;
    private LayoutInflater mInflater;
    private View mView;
    private Button yFooterBtn;

    private boolean yUnderTenCheck = false;
    private int yFromSize = 0; //10개 이상의 리스트를 출력할 때, 시작과 끝을 명시적으로 확인해주는 변수.
    private int yToSize = 0; //10개 이상의 리스트를 출력할 때, 시작과 끝을 명시적으로 확인해주는 변수.
    private boolean yCheckFirst = true;

    private int yListEndCheckNum = 0; //중요한 변수
    //리스트뷰를 동적으로 불러오기 위한 변수들.

    boolean yCheckPrint = true;
    boolean yAdmitClick = false;

    public static int ySearchLocationWait = 0; //AsyncTask의 동기화를 위해서 어쩔수 없이 static을 사용했습니다.............
    private int yLastSavedDistance = 0;//내 마트설정에서 마지막으로 저장한 km정보
    private TextView yLastSavedDistanceTextView;

    boolean yMartOptionCheck;
    boolean yMartOptionCategoryCheck;
    String ySearchSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main);

        yCategoryBtn = (Button) findViewById(R.id.yCategoryBtn);
        yBtnSearch = (Button) findViewById(R.id.yBtnSearch);
        ySettingBtn = (Button) findViewById(R.id.ySettingBtn);
        yCategoryBtn.setBackgroundDrawable(new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.category)));
        ySettingBtn.setBackgroundDrawable(new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.setting)));


        toast = new ToastMessage(SearchActivity.this);
        //textView = (TextView)findViewById(R.id.textview1);
        yEditProductName = (EditText) findViewById(R.id.ediText01);
        rBtnPrice = (RadioButton) findViewById(R.id.radio_price);

        //rBtnRegion = (RadioButton)findViewById(R.id.radio_region);
        rBtnDistance = (RadioButton) findViewById(R.id.radio_distance);

        listBox = (ListView) findViewById(android.R.id.list);

        checkButton = true;

        ySearchData = new Vector<SearchProduct>();
        searchItem = new ArrayList<ListItem>();
        searchItemAll = new ArrayList<ListItem>();
        selectItem = new ArrayList<ListItem>();
        bitList = new ArrayList<HashMap<String, Bitmap>>();

        //리스트뷰를 동적으로 불러오기 위한 변수들.
        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = mInflater.inflate(R.layout.search_footer, null);
        yFooterBtn = (Button) mView.findViewById(R.id.yFooterBtn);
        listBox.addFooterView(mView, -1, true);
        listBox.setOnItemClickListener(yListviewOnItemClick); //정상적으로 리스트가 출력된 이후에야 리스트가 클릭되도록.

        //ySearchAdapter = new SearchAdapter(ySearchData, getLayoutInflater(), false);
        //listBox.setAdapter(ySearchAdapter);

        //최근에 설정한 거리 설정을 저장하기 위한 변수 및 텍스트뷰.
        yLastSavedDistanceTextView = (TextView) findViewById(R.id.yLastSavedDistanceTextView);

        yMartOptionCategoryCheck = false;
        yMartOptionCheck = false;
        ySearchSave = null;

        //액티비티간에 정보를 저장,공유하기 위한 변수
        sharedPref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        sharedEditor = sharedPref.edit();

        /*
            검색명을 입력하고 돋보기 버튼을 누르면 바로 검색버튼이 클릭되서 좀 더 편하게 검색할 수 있도록 했습니다.
         */
        yEditProductName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                SearchBtn(getCurrentFocus());
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(yEditProductName.getWindowToken(), 0);
                return true;
            }
        });

        //에디트텍스트에 입력된 내용이 있으면 클릭만 해도 가격순으로 검색해줌.
        rBtnPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (yCheckRadio) {
                    toast.showToast("가격순으로 정렬합니다.", Toast.LENGTH_SHORT);
                    CategorySearch(yLastGetCategory);
                } else if (!yEditProductName.getText().toString().equals("") && yCheckRadio == false) {
                    toast.showToast("가격순으로 정렬합니다.", Toast.LENGTH_SHORT);
                    SearchBtn(getCurrentFocus());
                }
            }
        });

        //에디트텍스트에 입력된 내용이 있으면 클릭만 해도 거리순으로 검색해줌.
        rBtnDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yCheckRadio) {
                    toast.showToast("거리순으로 정렬합니다.", Toast.LENGTH_SHORT);
                    CategorySearch(yLastGetCategory);
                } else if (!yEditProductName.getText().toString().equals("") && yCheckRadio == false) {
                    toast.showToast("거리순으로 정렬합니다.", Toast.LENGTH_SHORT);
                    SearchBtn(getCurrentFocus());
                }
            }
        });

        mMartLocationList = new ArrayList<Item>();
        mSuperLocationList = new ArrayList<Item>();

    }

    public void BtnMartSettingOnClick(View v) {
        //기존에 마트설정 버튼을 버튼 누르면 무조건 재검색하게 했었는데 intent로 결과값을 받아서 변화가 있을 때만 수정하게끔 변경함.

        if(!yEditProductName.getText().toString().equals(""))
            ySearchSave = yEditProductName.getText().toString();

        Intent intent = new Intent(this, MartOptionActivity.class);
        startActivityForResult(intent,MARTOPTIONCHECK);
    }

    public void SearchBtn(View v) {
        if (checkButton) {
            checkButton = false;

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(yBtnSearch.getWindowToken(), 0);

        /*
        라디오버튼을 통해서 가격별/거리순을 구별하기 위함입니다.
         */
            if (rBtnPrice.isChecked()) {
                sNum = PRICE;
            }
            //else if (rBtnRegion.isChecked()) { sNum = Search.valueOf("REGION").getNum(); }
            else if (rBtnDistance.isChecked()) {
                sNum = DISTANCE;
            }

            searchName = yEditProductName.getText().toString();
            if (searchName.equals("")) {
                Toast.makeText(SearchActivity.this, "검색명을 입력하지 않으셨습니다.", Toast.LENGTH_SHORT).show();
            } else {
                checkGps = settingLocation();
                if (checkGps) {
                    yBtnSearch.setBackgroundResource(R.drawable.btnsearch2);
                    //검색명을 빠지지 않고 입력했을 경우, 해당 검색명에 대해서 mysql서버로 검색요청을 합니다.
                    getTextInfo(searchName, PRODUCT_NAME);
                    yCheckRadio = false;
                }
            }
        } else {
            checkButton = true;
            SearchBtn(getCurrentFocus());
        }
    }

    public void categoryClick(View v) {
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivityForResult(intent, 0);
    }

    public boolean settingLocation () {
        Log.d ( "###SearchActivity", "settinglocation 시작" );

        gps = new GPSInfo ( SearchActivity.this );
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
//        gps = new GPSInfo(SearchActivity.this);
//        if (gps.isGetLocation()) {
//            Log.d("GPS latitude", "" + gps.getLatitude());
//            Log.d("GPS longitude", "" + gps.getLongitude());
//
//            if (gps.getLatitude() == 0 && gps.getLongitude() == 0) {
//                showToast("사용자 위치 파악중입니다.");
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

    public int searchLocation(String department, final int num) {
        Searcher searcher = new Searcher();
        searcher.searchKeywordSingle(getApplicationContext(), department, getLatitude(), getLongitude(), radius, page,
                MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        Log.d("onSuccess", "Search 성공");
                    }

                    @Override
                    public void onSuccessSingle(Item item) {
                        Log.d("onSuccessSingle", "SearchSingle 성공");
                        Log.d("onSuccess에서", "name : " + searchItem.get(num).getDepart());
                        Log.d("onSuccess에서", "" + item.distance);

                        searchItem.get(num).setDistance(String.valueOf(item.distance));
                        if (num == searchItem.size() - 1) {
                            switch (sNum) {
                                case PRICE:
                                    BubbleSort(searchItem, 1);
                                    break;
                                case DISTANCE:
                                    BubbleSort(searchItem, 2);
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onFail() {
                        Log.d("onFail", "Search 실패");
                        showToast("API_KEY의 제한 트래픽이 초과되었습니다.");
                    }
                });
        Log.d("searchLocation 끝", "searchLocation 끝");
        return num;
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
    }

    public void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SearchActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //어떤 리스트 아이템이 눌렸는지 확인하는 메소드입니다. 기존에는 OnItemClickListener를 implements해서 썼는데 OnScrollListener를 사용하기 위해 대체함.
    AdapterView.OnItemClickListener yListviewOnItemClick = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parentView, View clickedView, int position, long id) {
            /*
            Log.d("bitList.size() : "+ bitList.size(), "searchItem.size() : " + searchItem.size());
            if (searchItem.size() != bitList.size()) {
                if(bitList.size() % 10 != 0 && bitList.size() == 0){
                    Log.d("searchItem : " + searchItem.size(), ",비트맵이 정상적으로 불려지지 않았습니다 : " + bitList.size());
                    return;
                }
            }
            */

            if (!yAdmitClick) {
                Log.d("searchItem : " + searchItem.size(), ",비트맵이 정상적으로 불려지지 않았습니다 : " + bitList.size());
                return;
            }

            //마트옵션을 위해 값을 저장해 둠.
            if(!yEditProductName.getText().toString().equals(""))
                ySearchSave = yEditProductName.getText().toString();

            /*
               마트 선택해서 검색하는 기능이 추가되면서 각가의 기능을 yForMartSearch를 이용해서 true일 경우
               마트 선택했을 때의 검색기능이, false일 경우 일반 검색기능으로 구분합니다.
             */
            Intent intent = new Intent(SearchActivity.this, InfoActivity.class);
            String name, price, distance, department, pid, count, category, date;
            Bitmap sendImage = null;
            Parcelable forImage;

            name = searchItem.get(position).getData(0);
            price = searchItem.get(position).getData(1);
            distance = searchItem.get(position).getData(2);
            department = searchItem.get(position).getData(3);
            pid = searchItem.get(position).getData(4);
            count = searchItem.get(position).getData(5);
            category = searchItem.get(position).getData(6);
            date = searchItem.get(position).getData(8);

            for (int i = 0; i < bitList.size(); ++i) {
                if (bitList.get(i).containsKey(name)) {
                    sendImage = bitList.get(i).get(name);
                }
            }

            forImage = sendImage;

            intent.putExtra("IMAGE", forImage);
            intent.putExtra("TEXT", new ListItem(name, price, distance, department, pid, count, category, date));
            //intent.putExtra("IMAGE",sendImage);

            startActivity(intent);
            GetSelectItem(name, department);
        }
    };

    /*
    검색 결과에 대해서 아무것도 찾지 못했을 경우
    검색결과가 없다는 안내문구와 함께
    기존 서버에 없는 제품 정보에 대해서 입력할 수 있도록 합니다.
     */
    public void MakeDialog() {
        ySearchData.clear();

        AlertDialog mDialog;

        AlertDialog.Builder dlg = new AlertDialog.Builder(SearchActivity.this);
        mDialog = dlg.create();

        if(yMartSavedCheck){
            dlg.setTitle("설정된 마트에 등록된 가격정보가 없습니다.");
        }else{
            dlg.setTitle("가격정보가 없습니다.");
        }

        dlg.setMessage("가격을 등록 하시겠습니까?");
        dlg.setIcon(R.drawable.logo2);
        listBox.removeHeaderView(listBox);

        mDialog.setCanceledOnTouchOutside(true);

        //메인 리스트뷰 검색결과 초기화
        listBox.setAdapter(new SearchAdapter(ySearchData, getLayoutInflater(), false));

        dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(SearchActivity.this, InsertLocationDialog.class);
                intent.putExtra("name", yEditProductName.getText().toString());
                startActivity(intent);
            }
        });
        dlg.setNegativeButton("취소", null);
        dlg.show();
    }


    /*
    최종적으로 이미지 정보와 제품(텍스트) 정보를 받아왔을 경우,
    리스트 뷰에 이 정보들을 추가해서 검색 결과에 띄울 수 있도록 합니다.
    Custom_List_Adapter은 리스트뷰에 정보를 띄위기 위한 어댑터이며,
    Custom_List_Data은 정보를 담기 위한 클래스입니다.
     */
    public void PrintArrayUnderTen() {
        Log.d("SearchActivity", "PrintArrayUnderTen 실행!");
        ySearchData.clear();

        Bitmap yImage = null;
        HashMap<String, Bitmap> yForGetHash = new HashMap<String, Bitmap>();

        for (int i = 0; i < searchItem.size(); ++i) {
            for (int j = 0; j < bitList.size(); ++j) {
                if (bitList.get(j).containsKey(searchItem.get(i).getData(0))) {
                    yImage = bitList.get(j).get(searchItem.get(i).getData(0));
                    break;
                }
            }

            ySearchData.add(new SearchProduct(yImage,
                    searchItem.get(i).getData(0), Integer.parseInt(searchItem.get(i).getData(1)),
                    searchItem.get(i).getData(3), searchItem.get(i).getData(2), searchItem.get(i).getData(8),
                    Integer.parseInt(searchItem.get(i).getData(5))
            ));
        }

        ySearchAdapter = new SearchAdapter(ySearchData, getLayoutInflater(), false);
        if (ySearchAdapter != null)
            listBox.setAdapter(new SearchAdapter(ySearchData, getLayoutInflater(), false));

        yFooterBtn.setVisibility(View.VISIBLE);
        yFooterBtn.setText("마지막 출력결과입니다.");
        yFooterBtn.setEnabled(false);
        checkButton = true;
        yCheckPrint = true;
        yAdmitClick = true;
    }

    public void PrintArrayAboveTen() {

        ySearchData.clear();

        Bitmap yImage = null;

        for (int j = yFromSize; j < yToSize; ++j) {
            for (int i = 0; i < searchItem.size(); ++i) {
                //Log.d("i : " +i , "yToSize : " +yToSize +"yFromSize : " + yFromSize);
                if (bitList.get(j).containsKey(searchItem.get(i).getData(0))) {
                    yImage = bitList.get(j).get(searchItem.get(i).getData(0));
                    break;
                }
            }

            ySearchData.add(new SearchProduct(yImage,
                    searchItem.get(j).getData(0), Integer.parseInt(searchItem.get(j).getData(1)),
                    searchItem.get(j).getData(3), searchItem.get(j).getData(2), searchItem.get(j).getData(8),
                    Integer.parseInt(searchItem.get(j).getData(5))
            ));
            yImage = null;
        }

        if (yCheckFirst) {
            //처음 10개만 우선 set해준다.
            Log.d("PrintArrayAboveTen 최초실행", "Bit Size : " + bitList.size());
            ySearchAdapter = new SearchAdapter(ySearchData, getLayoutInflater(), false);
            if (ySearchAdapter != null)
                listBox.setAdapter(ySearchAdapter);
            yCheckFirst = false;
        } else {
            Log.d("SearchActivity", "PrintArrayAboveTen 실행!, ySearchData : " + ySearchData.size());

            //mFooterListView.setAdapter(new SearchAdapter(ySearchData, getLayoutInflater(), false));
            ySearchAdapter = new SearchAdapter(ySearchData, getLayoutInflater(), false);
            if (ySearchAdapter != null)
                listBox.setAdapter(ySearchAdapter);
            listBox.setSelectionFromTop(yToSize - 10, 0);
        }

        yFooterBtn.setVisibility(View.VISIBLE);
        checkButton = true;
        yCheckPrint = true;
        yAdmitClick = true;
    }

    public void FooterBtnOnClik(View v) {
        //Log.d("while문 들어가기 전", "yListEndCheckNum : " + yListEndCheckNum);
        do {
            if (searchItem.size() <= yListEndCheckNum) {
                yFooterBtn.setText("마지막 출력결과입니다.");
                yFooterBtn.setEnabled(false);
                break;
            }
            //Log.d("while문 도는중", "yListEndCheckNum : " + yListEndCheckNum);
            getImage(searchItem.get(yListEndCheckNum).getData(0));
            ++yListEndCheckNum;
        } while (yListEndCheckNum % 10 != 0);
        //Log.d("while문 탈출", "yListEndCheckNum : " + yListEndCheckNum);
    }

    //////////////////////////////////////////////////////// 이하 이미지 출력
    private void getImage(final String pname) {
        class GetImage extends AsyncTask<String, Void, Bitmap> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(SearchActivity.this, "Uploading...", null,true,true);
            }

            @Override
            protected void onPostExecute(Bitmap b) {
                super.onPostExecute(b);
                //loading.dismiss();
                if (b != null) {
                    //Log.d("bitList 오냐", "여기와?");
                    /*
                    바로 이미지를 출력하지 않고 bitList에 비트맵 정보를 추가하는 이유는
                    리스트뷰를 출력하기 위한 어댑터에 Bitmap을 매개변수로 넘겨야하기 때문입니다.
                     */

                    //이미지 테이블 컬럼에 제품명이 추가되었으므로 이제 id로 매칭하지 않고 제품명으로 매칭해줌.
                    HashMap<String, Bitmap> yForAdd = new HashMap<String, Bitmap>();

                    yForAdd.put(pname, b);
                    bitList.add(yForAdd);
                }else{
                    Bitmap resized = null;
                    b = BitmapFactory.decodeResource(getResources(), R.drawable.no);
                    //Log.d("after asdsadsadsa"," : "+b.getRowBytes());
                    resized = Bitmap.createScaledBitmap(b, 400, 300, true);
                    //Log.d("after asdsadsadsa"," : "+resized.getRowBytes());
                    HashMap<String, Bitmap> yForAdd = new HashMap<String, Bitmap>();
                    yForAdd.put(pname, resized);
                    bitList.add(yForAdd);
                    resized = null;
                }

                /*
                여기가 사실 핵심인데 이렇게 해놓은 이유는 비동기 스레드가 서로 다르게 돌아가기 때문에
                메인스레드에서 최종 결과를 출력하려고 하면 그때는 아직 bitList나 searchItem이 완전하게 정보를 받아오지 않았을 수도 있습니다.
                메인 스레드와 비동기 스레드는 서로 다르게 돌아가고 그렇다고 비동기 스레드가 돌아가는 동안 메인 스레드를 멈출 수 없기 때문에
                비동기 스레드에서 작업을 다 끝냈을 경우 최종 결과를 출력합니다.
                또한 searchItem.size() == bitList.size() 이라는 조건을 걸어놓은 이유는 goods 테이블과 images 테이블, 두 테이블 간에 서로 id를 통해서 데이터를 동기화 시켜놨기 때문에
                두 ArrayList의 결과(크기)가 같아질 경우에만 출력해야 문제가 없어집니다.
                 */
                Log.d("searchItem size ", "size :" + searchItem.size());
                Log.d("bitList size ", "size :" + bitList.size());

                //10개 미만일 경우, 그냥 출력해준다.
                if (searchItem.size() == bitList.size() && yUnderTenCheck) {
                    //Log.d("여기서 프린트...","PrintArrayUnderTen 실행?");
                    PrintArrayUnderTen();
                    yUnderTenCheck = false;
                }

                //10개 이상일 경우, 10개씩 받아오면서 출력할 때.
                if (bitList.size() % 10 == 0 && bitList.size() != 0) {
                    yFromSize = 0;
                    //yFromSize = bitList.size() -10;
                    //yFromSize = ;
                    yToSize = bitList.size();
                    Log.d("yFromSize : " + yFromSize, "yToSize" + yToSize);
                    PrintArrayAboveTen();
                }

                //10개 이상일 경우, 모든 결과를 다 받고나서 최종적으로 출력해줄 때,
                if (searchItem.size() == bitList.size() && bitList.size() != 0) {
                    //yFromSize = bitList.size() - 10;
                    yFromSize = 0;
                    yToSize = bitList.size();
                    PrintArrayAboveTen();
                }
            }

            //////나중에 제품에 있는 id와 연동해서 선택된 이미지만 저장한 다음 출력해줄 수 있도록 수정해야 할 듯.
            @Override
            protected Bitmap doInBackground(String... params) {
                String pname = params[0];
                try {
                    //한글 검색 원활히 되도록 인코딩한 값을 검색어로 넘김.
                    Log.d("검색 키워드 in image : ", ""+pname);
                    pname = URLEncoder.encode(pname, "utf-8");
                    Log.d("검색 키워드 in image : ", ""+pname);
                } catch (Exception e) {
                }
                String add = "http://220.68.231.118/wook/getimage.php?pname=" + pname;

                Log.d("SearchActivity", add);
                URL url = null;
                Bitmap image = null;
                try {
                    url = new URL(add);
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return image;
            }
        }
        GetImage gi = new GetImage();
        gi.execute(pname);
    }

    private void getTextInfo(final String sname, final int whichSearch) {
        class phpDown extends AsyncTask<String, Integer, String> {
            ProgressDialog asyncDialog = new ProgressDialog(SearchActivity.this);

            @Override
            protected void onPreExecute() {
                asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                asyncDialog.setMessage("로딩중입니다..");
                asyncDialog.setCanceledOnTouchOutside(false);

                // show dialog
                asyncDialog.show();
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... urls) {
                String search = urls[0];
                StringBuilder jsonHtml = new StringBuilder();
                try {
                    // 연결 url 설정
                    URL url = new URL(urls[1] + search);
                    //URL url = new URL("http://220.68.231.140/wook/searchtext.php?name=" + searchName);
                    Log.d("SearchActivity", url.toString());

                    // 커넥션 객체 생성
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    // 연결되었으면.
                    if (conn != null) {
                        conn.setConnectTimeout(10000);
                        conn.setUseCaches(false);
                        // 연결되었음 코드가 리턴되면.
                        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                            for (; ; ) {
                                // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                                String line = br.readLine();
                                if (line == null) break;
                                // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
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

            public void onPostExecute(String str) {
                String name, price, distance, department, id, count, category, date;
                try {
                    InitInAsyncTask();

                    JSONObject root = new JSONObject(str);
                    JSONArray ja = root.getJSONArray("result");
                    JSONObject jo;
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        name = jo.getString("name");
                        price = jo.getString("price");
                        distance = "0";
                        department = jo.getString("department");
                        id = jo.getString("id");
                        count = jo.getString("pcount");
                        category = jo.getString("category");
                        date = jo.getString("goods_date");

                        if(Integer.parseInt(count) < 3){
                            Log.d("SearchActivity","입력된 카운트가 3개 미만일 경우, 신뢰도가 없는 가격이기 때문에 서버로부터 데이터를 받아오지 않습니다.");
                        }else{
                            if (yMartSavedCheck == true) {
                                for (int j = 0; j < ySavedMartList.size(); ++j) {
                                    //Log.d("SearchActivity",j+"번째 값 : "+ySavedMartList.get(j));
                                    //Log.d("SearchActivity","서버로 부터 받아온 마트 : "+department);
                                    if (department.contains(ySavedMartList.get(j))) {
                                        searchItemAll.add(new ListItem(name, price, distance, department, id, count, category, date));
                                    }
                                }
                            } else {
                                searchItemAll.add(new ListItem(name, price, distance, department, id, count, category, date));
                            } //~if - else문
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (searchItemAll.size() != 0) {
                    Compare();
                    /*
                    for (int i = 0; i < searchItem.size(); ++i) {
                        Log.d("SearchActivity", i + "번째 제품 :  " + searchItem.get(i).getData(0) + "(" + searchItem.get(i).getData(3) + ")");
                    }
                    */
                }
                if (searchItem.size() == 0) {
                    //Log.d("SearchActivity", "서치 아이템은 " + searchItem.size());
                    //등록된 상품이 없다면
                    yCheckPrint = true;
                    MakeDialog();
                } else {
                /*
                    내가 선택한 마트만 다시 출력되게끔 해주는 부분.
                 */

                    for (int j = 0; j < searchItem.size(); ++j) {
                        if (searchItem.get(j).getData(3).contains("이마트") || searchItem.get(j).getData(3).contains("롯데마트") ||
                                searchItem.get(j).getData(3).contains("홈플러스")) {
                            if (searchItem.get(j).getData(3).contains("에브리데이") || searchItem.get(j).getData(3).contains("999") ||
                                    searchItem.get(j).getData(3).contains("익스프레스")) {
                                Log.d("case 1","에브리데이,999,익스프레스 : " + searchItem.get(j).getData(3));
                                searchLocation(searchItem.get(j).getData(3), j);
                            } else {
                                //대형마트 일때만 내부적으로 검색을 돌림.
                                Log.d("case 2","대형마트 : " + searchItem.get(j).getData(3));
                                Mart.SearchMart(searchItem.get(j).getData(3));
                            }
                        } else {
                            Log.d("case 3","짜잘한애들 : " + searchItem.get(j).getData(3));
                            searchLocation(searchItem.get(j).getData(3), j);
                        }
                    }
                    CallResult();
                }
                yBtnSearch.setBackgroundResource(R.drawable.btnsearch); //정화 추가본.
                asyncDialog.dismiss();
            }
        } //~phpDown

        //버튼 연타를 막기위해 정상적으로 출력됐을 때만 yCheckPrint를 true로 바꿔줌.
        if (yCheckPrint) {
            yCheckPrint = false;
            phpDown task = new phpDown();
            String urlText = "";

            //만약 텍스트로 검색하면 case PRODUCT_NAME로 검색하고, 카테고리로 검새하면 case PRODUCT_CATEGORY로 검색하게 된다.
            switch (whichSearch) {
                case PRODUCT_NAME:
                    urlText = "http://220.68.231.118/wook/searchtext.php?name=";
                    break;
                case PRODUCT_CATEGORY:
                    urlText = "http://220.68.231.118/wook/searchcategory.php?category=";
                    break;
            }
            String ySearch = sname;

            if (whichSearch == PRODUCT_NAME) {
                try {
                    //한글 검색 원활히 되도록 인코딩한 값을 검색어로 넘김.
                    Log.d("검색 키워드 in text : ", ""+ySearch);
                    ySearch = URLEncoder.encode(searchName, "utf-8");
                    Log.d("검색 키워드 in text : ", ""+ySearch);
                } catch (Exception e) {
                }
            }
            task.execute(ySearch, urlText);
        } else {
            Log.d("현재 yCheckPrint : " + yCheckPrint, "버튼연타방지");
        }
    } //~getTextInfo

    private void InitInAsyncTask() {
        ySearchData.clear();
        searchItemAll.clear();
        bitList.clear();
        searchItem.clear();
        //Log.d("SearchActivity","BitList 제대로 초기화 됐나 : " +bitList.size());
        /////////////
        yListEndCheckNum = 0;
        //mFooterText.setText("제품 정보를 받아오고 있습니다."); //한번 검색결과를 모두 출력한 다음, 제품정보를 모두 출력했다고 명시해준다음에 다시 검색했을 경우 초기값을 명시해준다.
        yUnderTenCheck = false;
        yCheckFirst = true;
        yFromSize = 0;
        yToSize = 0;
        yFooterBtn.setEnabled(true);
        yFooterBtn.setText("결과 더보기");
        yFooterBtn.setVisibility(View.INVISIBLE);
        ySearchLocationWait = 0;
        yAdmitClick = false;
    }

    private void CallResult() {
        //위치정보를 다 받아올때까지 기다리는 것.
        while (ySearchLocationWait != searchItem.size()) {
            //Log.d("SearchActivity","동기화 맞추기 위해 while문 도는중");
            //부하를 줄이기 위해
            try {
                Thread.sleep(400);
            } catch (Exception e) {
            }
        }

        if(sNum == DISTANCE){
            BubbleSort(searchItem, 2);
        }else if(sNum == PRICE){
            BubbleSort(searchItem, 1);
        }

        RemoveFarMart(3000); //너무 거리가 먼 마트를 제거하는 메소드. 매개변수로는 거리를 넘겨주면 됨. 현재 7km로 설정함.

        //만약 데이터를 걸러냈는데 사이즈가 0이면
        if (searchItem.size() == 0) {
            yCheckPrint = true;
            MakeDialog();
            return;
        }

        ////// 거리 구하기 완료됨.
        if (searchItem.size() < 10 && searchItem.size() != 0) { //10개 이하의 제품일 경우 그냥 받아버림.
            yUnderTenCheck = true;
            for (int i = 0; i < searchItem.size(); ++i) {
                //Log.d("여기서 하면 위치 다 받았나?",""+searchItem.get(i).getData(2));
                getImage(searchItem.get(i).getData(0));
            }
        } else {
            //10개 이상일 경우, 동적으로 10개씩 출력해줘야함.
            for (int i = 0; i < 10; ++i) {
                //Log.d("여기서 하면 위치 다 받았나?",""+searchItem.get(i).getData(2));
                getImage(searchItem.get(i).getData(0)); //이미지를 로드 0~9 10개 호출하고
            }
            yListEndCheckNum = 10;
        }
    }

    //검색결과 중 너무 먼 거리의 마트를 걸러내는 메소드.
    public void RemoveFarMart(int getNum){
        //160227. 만약 마트설정을 저장했으면 거리가 멀었을 때 나오지 않게하는 기능을 적용하지 않음.
        if(yMartSavedCheck)
            return;

        //전체검색 시, 거리가 너무 먼 마트에 한해서 걸러내는 작업.
        int count = 0 ;
        //전체검색할 때, 7km이상의 마트는 걸러내고 받아옴.

        while(searchItem.size() != 0){
            if(Double.parseDouble(searchItem.get(count).getDistance()) >= getNum || Double.parseDouble(searchItem.get(count).getDistance()) ==0) {
                //Log.d(searchItem.get(count).getName() + "의 거리는", searchItem.get(count).getDistance() + "이기 때문에 제거합니다!!!!!");
                searchItem.remove(count);
                count = 0;
            }else{
                //Log.d(searchItem.get(count).getName() + "의 거리는", searchItem.get(count).getDistance() + "이기 때문에 남겨둡니다!!!!!");
                ++count;
            }
            //Log.d("현재 리스트의 크기는 " + searchItem.size() ,"입니다.");

            if(searchItem.size() == count){
                Log.d("현재 리스트의 크기는 " + searchItem.size() ,"이고, count는 "+count+"이므로 탈출!");
                break;
            }
        }
    }

    public void Compare() {
        int count;

        searchItem.add(new ListItem(searchItemAll.get(0).getData(0), searchItemAll.get(0).getData(1), searchItemAll.get(0).getData(2), searchItemAll.get(0).getData(3), searchItemAll.get(0).getData(4),
                searchItemAll.get(0).getData(5), searchItemAll.get(0).getData(6), searchItemAll.get(0).getData(8)));

        for (int i = 0; i < searchItemAll.size(); i++) {
            for (int j = 0; j < searchItem.size(); j++) {

                //Log.d("SearchActivity","============================= ");
                //Log.d("SearchActivity",j+"번째 서치 이름 : "+searchItem.get(j).getData(0)+", 카운트 및 날짜"+searchItem.get(j).getData(5)+" / "+ searchItem.get(j).getData(8));
                //Log.d("SearchActivity",i+"번째 올 이름 : "+searchItemAll.get(i).getData(0)+", 카운트 및 날짜"+searchItemAll.get(i).getData(5)+" / "+ searchItemAll.get(i).getData(8));

                if (searchItem.get(j).getData(0).equals(searchItemAll.get(i).getData(0)) && searchItem.get(j).getData(3).equals(searchItemAll.get(i).getData(3))) {
                    //오늘날짜로 일치하는 놈이 있으면 그놈을 최우선순위 가격으로 보여주고, 없으면 카운트가 높은 놈으로 보여준다. 근데 단순 카운트만 높은애면 좀 문제가 있을 것 같다
                    if (searchItemAll.get(i).getData(8).equals(InsertDataToServer.getDateString())) {
                        //Log.d("SearchActivity","요기요!!!");

                        //날짜가 처음 일치하는 값은 그냥 변경해주고, 그 다음부터는 count 크기를 비교한 다음에 카운트 값이 더 클 경우에 searchItem에 추가해준다.
                        if (!searchItem.get(j).getData(8).equals(InsertDataToServer.getDateString())) {
                            searchItem.get(j).changeList(searchItemAll.get(i));
                        } else if (Integer.parseInt(searchItem.get(j).getData(5)) < Integer.parseInt(searchItemAll.get(i).getData(5))) {
                            searchItem.get(j).changeList(searchItemAll.get(i));
                        }
                    } //날짜가 일치하지 않을 경우, 기존처럼 카운트 값이 큰 순서대로 추가한다.
                    else if (!searchItem.get(j).getData(8).equals(InsertDataToServer.getDateString())) {
                        if (Integer.valueOf(searchItem.get(j).getData(5)).intValue() < Integer.valueOf(searchItemAll.get(i).getData(5)).intValue())
                            searchItem.get(j).changeList(searchItemAll.get(i));
                    }
                    //Log.d("SearchActivity","알고리즘 후에,...."+j+"번째 서치 이름 : "+searchItem.get(j).getData(0)+", 카운트 및 날짜"+searchItem.get(j).getData(5)+" / "+ searchItem.get(j).getData(8));
                    //Log.d("SearchActivity","============================= ");
                } else {
                    count = 0;
                    for (int n = 0; n < searchItem.size(); n++) {
                        if (searchItemAll.get(i).getData(0).equals(searchItem.get(n).getData(0)) && searchItemAll.get(i).getData(3).equals(searchItem.get(n).getData(3))) {
                        } else {
                            count++;
                            if (count == searchItem.size()) {
                                searchItem.add(new ListItem(searchItemAll.get(i).getData(0), searchItemAll.get(i).getData(1), searchItemAll.get(i).getData(2), searchItemAll.get(i).getData(3), searchItemAll.get(i).getData(4),
                                        searchItemAll.get(i).getData(5), searchItemAll.get(i).getData(6), searchItemAll.get(i).getData(8)));
                            }
                        }
                    }
                }
            }
        }
    }  //~Comapre()

    /*
이 메소드는 선택적으로 버블정렬을 할 수 있습니다.
비교하고 싶은 내용을 2번째 인자로 넘겨서 실행할 수 있습니다.
예를 들어 가격별로 비교하고 싶다면 1을 넘기면 되고
거리순이라면 2, 카운트 순이라면 5를 넘겨주면 됩니다.
 */
    public static void BubbleSort(ArrayList<ListItem> arr, int nIndex) {
        int last = arr.size() - 1;
        ListItem temp;

        if (nIndex == 1) {
            for (int i = last; i > 0; i--) {
                for (int n = 0; n < i; n++) {
                    if (Double.parseDouble ( arr.get ( n ).getData ( nIndex ) ) == Double.parseDouble ( arr.get ( n + 1 ).getData ( nIndex ) )) {
                        Log.d ( "BubbleSort", "여기로 와이건 거리??" + Double.parseDouble ( arr.get ( n ).getData ( nIndex ) ) + "      " + Double.parseDouble ( arr.get ( n + 1 ).getData ( nIndex ) ) );
                        if (Double.parseDouble(arr.get(n).getData(2)) > Double.parseDouble(arr.get(n + 1).getData(2))) {
                            Log.d("BubbleSort", "여기로 와카운트?" + Double.parseDouble(arr.get(n).getData(2)) + "      " + Double.parseDouble(arr.get(n + 1).getData(2)));
                            temp = new ListItem(arr.get(n + 1).getData(0), arr.get(n + 1).getData(1),
                                    arr.get(n + 1).getData(2), arr.get(n + 1).getData(3), arr.get(n + 1).getData(4)
                                    , arr.get(n + 1).getData(5), arr.get(n + 1).getData(6), arr.get(n + 1).getData(8));
                            arr.get(n + 1).changeList(arr.get(n));
                            arr.get(n).changeList(temp);
                        }
                    } else if (Integer.parseInt(arr.get(n).getData(nIndex)) > Integer.parseInt(arr.get(n + 1).getData(nIndex))) {
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
                    if (Double.parseDouble(arr.get(n).getData(nIndex)) == Double.parseDouble(arr.get(n + 1).getData(nIndex))) {
                        Log.d("BubbleSort", "여기로 와이건 거리??" + Double.parseDouble(arr.get(n).getData(nIndex)) + "      " + Double.parseDouble(arr.get(n + 1).getData(nIndex)));
                        if (Double.parseDouble(arr.get(n).getData(5)) < Double.parseDouble(arr.get(n + 1).getData(5))) {
                            Log.d("BubbleSort", "여기로 와카운트?" + Double.parseDouble(arr.get(n).getData(5)) + "      " + Double.parseDouble(arr.get(n + 1).getData(5)));
                            temp = new ListItem(arr.get(n + 1).getData(0), arr.get(n + 1).getData(1),
                                    arr.get(n + 1).getData(2), arr.get(n + 1).getData(3), arr.get(n + 1).getData(4)
                                    , arr.get(n + 1).getData(5), arr.get(n + 1).getData(6), arr.get(n + 1).getData(8));
                            arr.get(n + 1).changeList(arr.get(n));
                            arr.get(n).changeList(temp);
                        }
                    } else if (Double.parseDouble(arr.get(n).getData(nIndex)) > Double.parseDouble(arr.get(n + 1).getData(nIndex))) {
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

    //////////////////////////////////////////////////////// 황정남씨

    public void GetSelectItem(String selectName, String selectDepartment) {
        //Log.d("SearchActivity","셀렉트 네임 : "+selectName);
        //Log.d("SearchActivity", "셀렉트 마트 : " + selectDepartment);
        for (int i = 0; i < searchItemAll.size(); i++) {
            if ((searchItemAll.get(i).getData(0)).equals(selectName) && (searchItemAll.get(i).getData(3)).equals(selectDepartment)) {// && (searchItemAll.get(i).getData(0)).equals(selectDepartment) ){
                selectItem.add(new ListItem(searchItemAll.get(i).getData(0), searchItemAll.get(i).getData(1), searchItemAll.get(i).getData(2),
                        searchItemAll.get(i).getData(3), searchItemAll.get(i).getData(4), searchItemAll.get(i).getData(5),
                        searchItemAll.get(i).getData(6), searchItemAll.get(i).getData(8)));
            }
        }
    }
    /////////////////////////////////////////////////////// 여기까지 황정남씨

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "한번 더 누르면 앱이 종료 됩니다.", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void CategorySearch(int yGetCategory) {
        if (rBtnPrice.isChecked()) {
            sNum = PRICE;
        } else if (rBtnDistance.isChecked()) {
            sNum = DISTANCE;
        }

        checkGps = settingLocation();
        if (checkGps) {
            //검색명을 빠지지 않고 입력했을 경우, 해당 검색명에 대해서 mysql서버로 검색요청을 합니다.
            getTextInfo("" + yGetCategory, PRODUCT_CATEGORY);
        }
    }

    public void GoToMartOption() {
        AlertDialog.Builder dlg = new AlertDialog.Builder(SearchActivity.this);
        dlg.setTitle("마트설정");
        dlg.setMessage("저장된 마트가 없습니다.\n 마트설정을 하시겠습니까?");
        dlg.setIcon(R.mipmap.minilogo);

        dlg.setNegativeButton("취소", null);
        dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BtnMartSettingOnClick(getCurrentFocus());
            }
        });
        dlg.show();
    }

    public void SearchAfterSettingMartOption(){
        if(yMartOptionCategoryCheck && listBox != null && ySearchAdapter != null){
            //카테고리 재검색
            yMartOptionCategoryCheck = false;
            CategorySearch(yLastGetCategory);
        }else if(yMartOptionCheck && listBox != null && ySearchAdapter != null && ySearchSave != null ){
            //텍스트 재검색
            yMartOptionCheck = false;
            yEditProductName.setText(ySearchSave);
            SearchBtn(getCurrentFocus());
            ySearchSave = null;
        }else{
            yMartOptionCheck = false;
            yMartOptionCategoryCheck = false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == MARTOPTIONCHECK){
            //텍스트 검색을 한 흔적이 있거나 라디오버튼검색이 true로 되어있을 경우에만 다시 검색함.

            if(yEditProductName.getText().toString() != null || yCheckRadio || ySearchSave != null){
                if(yCheckRadio){
                    yMartOptionCategoryCheck = true;
                }else if( yEditProductName.getText().toString() != null){
                    yMartOptionCheck = true;
                }
                Log.d("onActivityResult if문" ,", yMartOptionCategoryCheck : " + yMartOptionCategoryCheck +", yMartOptionCheck : " + yMartOptionCheck );
            }else{
                yMartOptionCategoryCheck = false;
                yMartOptionCheck = false;
                Log.d("onActivityResult else문" ,", yMartOptionCategoryCheck : " + yMartOptionCategoryCheck +", yMartOptionCheck : " + yMartOptionCheck );
            }
        }
    }

    //액티비티간에 정보를 저장,공유하기 위한 메소드
    public void onResume() {
        super.onResume();

        if (musthotchoice == true) { //Must, Hot이 트루일때, Text로 가져옴.
            if (musthotname != null) {
                yEditProductName.setText("");
                yEditProductName.setText(musthotname);
                musthotchoice = false;
            }
        }
        /*
        if (musthotchoice == true) { //Must, Hot이 트루일때, Text로 가져옴.
            if (musthotname != null) {
                yEditProductName.setText("");
                yEditProductName.setText(musthotname);
                musthotchoice = false;
            }
        }else if(ySearchSave != null) {
            yEditProductName.setText(ySearchSave);
        }else {
            yEditProductName.setText("");
        }
        */

        //카테고리 검색
        if (ySetCategory != -1) {
            yCheckRadio = true;
            yLastGetCategory = ySetCategory;
            CategorySearch(yLastGetCategory);
            yEditProductName.setText("");
            ySetCategory = -1; //초기화
        }

        //////////////////////////////////////////////////////저장된 마트 설정
        ySavedMartList = new ArrayList<String>();

        //최초 어플을 시작했을때, 마트설정에 아무것도 설정되어 있지 않다면 강제로 모든 마트를 설정해줌.

        int yMartSize = sharedPref.getInt("MartSize", 0);

        //내 마트설정에서 마지막으로 저장한 거리값을 텍스트로 명시해줌.
        yLastSavedDistance = sharedPref.getInt("MartDistance", 0);
        if (yMartSize == 0) {
            yLastSavedDistanceTextView.setText("설정없음");
        } else if (yLastSavedDistance == 1) {
            yLastSavedDistanceTextView.setText("1KM");
        } else if (yLastSavedDistance == 3) {
            yLastSavedDistanceTextView.setText("3KM");
        } else if (yLastSavedDistance == 5) {
            yLastSavedDistanceTextView.setText("5KM");
        } else {
            yLastSavedDistanceTextView.setText("설정없음");
        }

        Log.d("MartOptionActivity", "모든 마트의 수 : " + yMartSize);
        if (yMartSize == 0) {
            Log.d("MartOptionActivity", "저장된 마트 목록이 없기 때문에 return합니다.");
            yLastSavedDistanceTextView.setText("설정없음"); //아무런 마트도 설정되어 있지 않으면 '설정없음'으로 바꿔준다.
            yMartSavedCheck = false;

            //마트설정하고 나서 만약 초기화하고 전체검색으로 했을 경우, 전체검색을 해줌.
            SearchAfterSettingMartOption();
            return;
        }


        for (int i = 0; i < yMartSize; i++) {
            ySavedMartList.add(sharedPref.getString("Mart_" + i, null));
            Log.d("InfoActivity", i + "번째 저장된 마트 : " + ySavedMartList.get(i));
        }
        yMartSavedCheck = true;
        SearchAfterSettingMartOption();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recycleView(findViewById(R.id.yCategoryBtn));
        recycleView(findViewById(R.id.ySettingBtn));
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