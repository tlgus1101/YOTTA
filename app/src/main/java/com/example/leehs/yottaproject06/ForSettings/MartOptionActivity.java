package com.example.leehs.yottaproject06.ForSettings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.leehs.yottaproject06.AboutMaps.GPSInfo;
import com.example.leehs.yottaproject06.AboutMaps.MapApiConst;
import com.example.leehs.yottaproject06.AboutMaps.ShowLocation;
import com.example.leehs.yottaproject06.FindLocation.Item;
import com.example.leehs.yottaproject06.FindLocation.OnFinishSearchListener;
import com.example.leehs.yottaproject06.FindLocation.Searcher;
import com.example.leehs.yottaproject06.ForSearch.SearchActivity;
import com.example.leehs.yottaproject06.ForSearch.SearchAdapter;
import com.example.leehs.yottaproject06.InsertDataToServer.InsertActivity;
import com.example.leehs.yottaproject06.ListItem;
import com.example.leehs.yottaproject06.R;

import com.example.leehs.yottaproject06.R;
import com.example.leehs.yottaproject06.ToastMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by MyCom on 2016-01-19.
 */
public class MartOptionActivity  extends Activity {

    public static final String HYPERMART = "대형마트";
    public static final String MIDDLEMART = "대형 마트"; //둘다 대형마트에서 추려내지만 switch문에서 같은 대형마트가 있으면 문제되기 때문에 띄어쓰기를 했고, 다음에 쿼리 날렸을 때 받는 결과는 같기 때문에 문제되지 않을듯.
    public static final String SMALLMART = "슈퍼";

    private Button ySaveBtn, ySelectAllBtn, yFinishBtn, yInitBtn;
    private Button yHyperMarketBtn, yMiddleMarketBtn, ySmallMarketBtn;
    private RadioButton yRadio1KmBtn, yRadio3KmBtn, yRadio5KmBtn;
    private ListView yHyperMartListView, yMiddleMartListView, ySmallMartListView;

    private ArrayList<String> ySavedHyperMartList;
    private ArrayList<String> ySavedMiddleMartList;
    private ArrayList<String> ySavedSmallMartList;

    private List<MartInfo> ySetHyperMartInfo,ySetMiddleMartInfo,ySetSmallMartInfo;

    private List<Item> yAllMartList;
    private ArrayList<Item> yHyperMartList;
    private ArrayList<Item> yMiddleMartList;
    private ArrayList<Item> ySmallMartList;

    private MartAdapter yMartAdapter;
    private MartAdapter yHyperMartAdapter;
    private MartAdapter yMiddleMartAdapter;
    private MartAdapter ySmallMartAdapter;


    // 애플리케이션 전체에서 사용할 값
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor sharedEditor;

    //////////////////마트설정하기 위한 변수들//////////////////////////////////////////

    GPSInfo gps;
    double uLatitude;
    double uLongitude;
    double dLatitude;
    double dLongitude;
    double uDistance;

    boolean checkGps;

    double latitude = 0;
    double longitude = 0;
    int radius = 100;
    int page = 1;

    private String yCheckMart = null;

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

    //////////////////마트설정하기 위한 변수들//////////////////////////////////////////

    ToastMessage yToast;
    boolean yBtnSelectedCheck = true;

    boolean yHyperTempCheck = true;
    boolean yMiddleTempCheck = true;
    boolean ySmallTempCheck = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moption_main);
        // Initiate Widget

        //다이얼로그를 띄우고 밖을 클릭했을 시 꺼지는 것을 방지함.
        this.setFinishOnTouchOutside(false);

        //리스트뷰를 각각 분류하기 위해서 3개로 만듬.
        yHyperMartListView = (ListView)findViewById(R.id.yHyperMartListView);
        yMiddleMartListView = (ListView)findViewById(R.id.yMiddleMartListView);
        ySmallMartListView = (ListView)findViewById(R.id.ySmallMartListView);

        yRadio1KmBtn = (RadioButton)findViewById(R.id.radio1KmBtn);
        yRadio3KmBtn = (RadioButton)findViewById(R.id.radio3KmBtn);
        yRadio5KmBtn = (RadioButton)findViewById(R.id.radio5KmBtn);

        yInitBtn = (Button)findViewById(R.id.yInitBtn);
        yFinishBtn = (Button)findViewById(R.id.yFinishBtn);
        ySelectAllBtn = (Button)findViewById(R.id.ySelectAllBtn);
        ySaveBtn = (Button) findViewById(R.id.ySaveBtn);
        yHyperMarketBtn = (Button)findViewById(R.id.yHyperMarketBtn);
        yMiddleMarketBtn = (Button)findViewById(R.id.yMiddleMarketBtn);
        ySmallMarketBtn = (Button)findViewById(R.id.ySmallMarketBtn);

        /////라디오 버튼을 먼저 누르도록 유도하기 위해 버튼을 투명화시킴.
        yHyperMarketBtn.setVisibility(View.INVISIBLE);
        yMiddleMarketBtn.setVisibility(View.INVISIBLE);
        ySmallMarketBtn.setVisibility(View.INVISIBLE);
        ySaveBtn.setVisibility(View.INVISIBLE);
        ySelectAllBtn.setVisibility(View.INVISIBLE);
        yFinishBtn.setVisibility(View.INVISIBLE);

        sharedPref = getSharedPreferences("pref", Activity.MODE_PRIVATE);

        sharedEditor = sharedPref.edit();

        yHyperMartList = new ArrayList<Item>();
        yMiddleMartList = new ArrayList<Item>();
        ySmallMartList = new ArrayList<Item>();

        ySetHyperMartInfo = new Vector<MartInfo>();
        ySetMiddleMartInfo = new Vector<MartInfo>();
        ySetSmallMartInfo = new Vector<MartInfo>();

        yToast = new ToastMessage(MartOptionActivity.this);

        Radio3KmBtnOnClcik(getCurrentFocus());
        HyperMarketOnClick(getCurrentFocus());

        //1km짜리 라디오 버튼 클릭시,
        yRadio1KmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //radius에 거리 설정.
                radius = 1000;

                //여기서 boolean형 만들어서 true로 해놓은 다음에 최초에 띄우면 다시 false로 만들고 라디오 버튼을 조작하지 않는 이상 계속 false로 해서 리스트뷰를 초기화하지 않도록 만들면 될 듯.
                yHyperTempCheck = true;
                yMiddleTempCheck = true;
                ySmallTempCheck = true;

                //마지막에 클릭한 거리에 대한 정보를 저장.
                sharedEditor.remove("MartDistance");
                sharedEditor.putInt("MartDistance", 1);

                yHyperMarketBtn.setVisibility(View.VISIBLE);
                yMiddleMarketBtn.setVisibility(View.VISIBLE);
                ySmallMarketBtn.setVisibility(View.VISIBLE);

                //라디오버튼만 눌러도 목록을 출력할 수 있도록 만듬.
                if(yCheckMart != null){
                    switch (yCheckMart){
                        case HYPERMART:
                            HyperMarketOnClick(getCurrentFocus());
                            break;
                        case MIDDLEMART:
                            MiddleMarketOnClick(getCurrentFocus());
                            break;
                        case SMALLMART:
                            SmallMarketOnClick(getCurrentFocus());
                            break;
                    }
                }
            }
        });

        //3KM짜리 라디오 버튼은 메소드로 뺌.

        //5km짜리 라디오 버튼 클릭시,
        yRadio5KmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //radius에 거리 설정.
                radius = 5000;

                yHyperTempCheck = true;
                yMiddleTempCheck = true;
                ySmallTempCheck = true;

                //마지막에 클릭한 거리에 대한 정보를 저장.
                sharedEditor.remove("MartDistance");
                sharedEditor.putInt("MartDistance", 5);

                yHyperMarketBtn.setVisibility(View.VISIBLE);
                yMiddleMarketBtn.setVisibility(View.VISIBLE);
                ySmallMarketBtn.setVisibility(View.VISIBLE);

                //라디오버튼만 눌러도 목록을 출력할 수 있도록 만듬.
                if(yCheckMart != null){
                    switch (yCheckMart){
                        case HYPERMART:
                            HyperMarketOnClick(getCurrentFocus());
                            break;
                        case MIDDLEMART:
                            MiddleMarketOnClick(getCurrentFocus());
                            break;
                        case SMALLMART:
                            SmallMarketOnClick(getCurrentFocus());
                            break;
                    }
                }
            }
        });

        //대형마트,중형마트,소형마트 버튼은 메소드로 뺌.

        //종료
        yFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //전체선택 클릭시.
        ySelectAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (yCheckMart) {
                    case HYPERMART: {
                        if(ySetHyperMartInfo.size() == 0)
                            return; //저장된 마트정보가 아무것도 없을 경우 그냥 return합니다.

                        if (yBtnSelectedCheck) {
                            //전체선택
                            for (int i = ySetHyperMartInfo.size() - 1; i >= 0; i--) {

                                if (!ySetHyperMartInfo.get(i).GetSelect()) {
                                    ySetHyperMartInfo.get(i).SetSelect(true);
                                }
                            }
                            yBtnSelectedCheck = false;
                        } else {
                            //전체 선택 해제
                            for (int i = ySetHyperMartInfo.size() - 1; i >= 0; i--) {

                                if (ySetHyperMartInfo.get(i).GetSelect()) {
                                    ySetHyperMartInfo.get(i).SetSelect(false);
                                }
                            }
                            yBtnSelectedCheck = true;
                        }
                        yHyperMartAdapter.notifyDataSetChanged();
                    }
                    break;
                    case MIDDLEMART:{
                        if(ySetMiddleMartInfo.size() == 0)
                            return; //저장된 마트정보가 아무것도 없을 경우 그냥 return합니다.

                        if (yBtnSelectedCheck) {
                            //전체선택
                            for (int i = ySetMiddleMartInfo.size() - 1; i >= 0; i--) {

                                if (!ySetMiddleMartInfo.get(i).GetSelect()) {
                                    ySetMiddleMartInfo.get(i).SetSelect(true);
                                }
                            }
                            yBtnSelectedCheck = false;
                        } else {
                            //전체 선택 해제
                            for (int i = ySetMiddleMartInfo.size() - 1; i >= 0; i--) {

                                if (ySetMiddleMartInfo.get(i).GetSelect()) {
                                    ySetMiddleMartInfo.get(i).SetSelect(false);
                                }
                            }
                            yBtnSelectedCheck = true;
                        }
                        yMiddleMartAdapter.notifyDataSetChanged();
                    }
                    break;
                    case SMALLMART:{
                        if(ySetSmallMartInfo.size() == 0)
                            return; //저장된 마트정보가 아무것도 없을 경우 그냥 return합니다.

                        if (yBtnSelectedCheck) {
                            //전체선택
                            for (int i = ySetSmallMartInfo.size() - 1; i >= 0; i--) {

                                if (!ySetSmallMartInfo.get(i).GetSelect()) {
                                    ySetSmallMartInfo.get(i).SetSelect(true);
                                }
                            }
                            yBtnSelectedCheck = false;
                        } else {
                            //전체 선택 해제
                            for (int i = ySetSmallMartInfo.size() - 1; i >= 0; i--) {

                                if (ySetSmallMartInfo.get(i).GetSelect()) {
                                    ySetSmallMartInfo.get(i).SetSelect(false);
                                }
                            }
                            yBtnSelectedCheck = true;
                        }
                        ySmallMartAdapter.notifyDataSetChanged();
                    }
                    break;
                }
            }
        });

        //설정저장 버튼 클릭시.
        ySaveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int yCheck = 0;

                if(ySetHyperMartInfo.size() != 0){ //대형마틐
                    ySavedHyperMartList.clear(); //내가 저장했던 마트목록을 저장했다가 체크해줬으니, 다시 저장하기 전에 기존의 리스트는 모두 제거.

                    Log.d("HYPER " , "MartInfo SIZE : " + ySetHyperMartInfo.size());
                    for(int i = 0 ; i < ySetHyperMartInfo.size() ; ++i){
                        MartInfo yCurMart = ySetHyperMartInfo.get(i);
                        if (yCurMart.GetSelect() == true){
                            //이미 저장된 마트의 경우 추가로 저장하지 않음.
                            for(int j = 0 ; j < ySavedHyperMartList.size() ; ++j){
                                if(ySavedHyperMartList.get(j).equals(yCurMart.GetMartName())){
                                    ++yCheck;
                                }
                            }
                            if(yCheck ==0){
                                Log.d("MartOptionActivity", "HSSS 저장된 마트 : " + yCurMart.GetMartName());
                                ySavedHyperMartList.add(yCurMart.GetMartName());
                            }
                            yCheck = 0; //체크하기 위한 변수를 다시 0으로 초기화
                        }
                    }

                    sharedEditor.putInt("HyperMartSize", ySavedHyperMartList.size());

                    for (int i = 0; i < ySavedHyperMartList.size(); ++i) {
                        sharedEditor.remove("HyperMart_" + i);
                        sharedEditor.putString("HyperMart_" + i, ySavedHyperMartList.get(i));
                    }
                    yCheck = 0;
                }else{
                    Log.d("MartInfo SIZE : " + ySetHyperMartInfo.size(),"Hyper마트의 사이즈가 0이므로 초기화하지 않습니다.");
                }//대형마트

                if(ySetMiddleMartInfo.size() != 0){ //중형마트
                    ySavedMiddleMartList.clear(); //내가 저장했던 마트목록을 저장했다가 체크해줬으니, 다시 저장하기 전에 기존의 리스트는 모두 제거.

                    Log.d("MIDDLE ", "MartInfo SIZE : " + ySetMiddleMartInfo.size());
                    for(int i = 0 ; i < ySetMiddleMartInfo.size() ; ++i){
                        MartInfo yCurMart = ySetMiddleMartInfo.get(i);
                        if (yCurMart.GetSelect() == true){
                            //이미 저장된 마트의 경우 추가로 저장하지 않음.
                            for(int j = 0 ; j < ySavedMiddleMartList.size() ; ++j){
                                if(ySavedMiddleMartList.get(j).equals(yCurMart.GetMartName())){
                                    ++yCheck;
                                }
                            }
                            if(yCheck ==0){
                                Log.d("MartOptionActivity", "HSSS 저장된 마트 : " + yCurMart.GetMartName());
                                ySavedMiddleMartList.add(yCurMart.GetMartName());
                            }
                            yCheck = 0; //체크하기 위한 변수를 다시 0으로 초기화
                        }
                    }

                    sharedEditor.putInt("MiddleMartSize", ySavedMiddleMartList.size());

                    for (int i = 0; i < ySavedMiddleMartList.size(); ++i) {
                        sharedEditor.remove("MiddleMart_" + i);
                        sharedEditor.putString("MiddleMart_" + i, ySavedMiddleMartList.get(i));
                    }
                    yCheck = 0;
                }else{
                    Log.d("MartInfo SIZE : " + ySetMiddleMartInfo.size(),"Middle마트의 사이즈가 0이므로 초기화하지 않습니다.");
                }//중형마트
                if(ySetSmallMartInfo.size() != 0){ //소형마트
                    ySavedSmallMartList.clear(); //내가 저장했던 마트목록을 저장했다가 체크해줬으니, 다시 저장하기 전에 기존의 리스트는 모두 제거.

                    Log.d("SMALL ", "MartInfo SIZE : " + ySetSmallMartInfo.size());
                    for(int i = 0 ; i < ySetSmallMartInfo.size() ; ++i){
                        MartInfo yCurMart = ySetSmallMartInfo.get(i);
                        if (yCurMart.GetSelect() == true){
                            //이미 저장된 마트의 경우 추가로 저장하지 않음.
                            for(int j = 0 ; j < ySavedSmallMartList.size() ; ++j){
                                if(ySavedSmallMartList.get(j).equals(yCurMart.GetMartName())){
                                    ++yCheck;
                                }
                            }
                            if(yCheck ==0){
                                Log.d("MartOptionActivity", "HSSS 저장된 마트 : " + yCurMart.GetMartName());
                                ySavedSmallMartList.add(yCurMart.GetMartName());
                            }
                            yCheck = 0; //체크하기 위한 변수를 다시 0으로 초기화
                        }
                    }

                    sharedEditor.putInt("SmallMartSize", ySavedSmallMartList.size());

                    for (int i = 0; i < ySavedSmallMartList.size(); ++i) {
                        sharedEditor.remove("SmallMart_" + i);
                        sharedEditor.putString("SmallMart_" + i, ySavedSmallMartList.get(i));
                    }

                    yCheck = 0;
                }else{
                    Log.d("MartInfo SIZE : " + ySetSmallMartInfo.size(),"Small마트의 사이즈가 0이므로 초기화하지 않습니다.");
                }//소형마트

                int yCheckNum = 0;
                for (int i = 0; i < ySavedHyperMartList.size(); ++i) {
                    sharedEditor.remove("Mart_" + i);
                    sharedEditor.putString("Mart_" + i, ySavedHyperMartList.get(i));
                    ++yCheckNum;
                }

                for (int i = 0; i < ySavedMiddleMartList.size(); ++i) {
                    sharedEditor.remove("Mart_" + yCheckNum);
                    sharedEditor.putString("Mart_" + yCheckNum, ySavedMiddleMartList.get(i));
                    ++yCheckNum;
                }

                for (int i = 0; i < ySavedSmallMartList.size(); ++i) {
                    sharedEditor.remove("Mart_" + yCheckNum);
                    sharedEditor.putString("Mart_" + yCheckNum, ySavedSmallMartList.get(i));
                    ++yCheckNum;
                }

                if(yCheckNum == 0 ){
                    yToast.showToast("설정하신 마트가 없습니다!",Toast.LENGTH_SHORT);
                }else{
                    yToast.showToast("마트설정이 저장되었습니다.",Toast.LENGTH_SHORT);
                }

                sharedEditor.putInt("MartSize", yCheckNum);
                sharedEditor.commit();

                yHyperTempCheck = true;
                yMiddleTempCheck = true;
                ySmallTempCheck = true;

                //초기화 된 리스트를 다시 띄워줌.
                switch (yCheckMart) {
                    case HYPERMART:
                        HyperMarketOnClick(getCurrentFocus());
                        break;
                    case MIDDLEMART:
                        MiddleMarketOnClick(getCurrentFocus());
                        break;
                    case SMALLMART:
                        SmallMarketOnClick(getCurrentFocus());
                        break;
                }

                setResult(123);
            }
        });

        yInitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(MartOptionActivity.this);
                dlg.setTitle("마트설정");
                dlg.setMessage("저장된 모든 마트정보를 초기화시키시겠습니까?");
                dlg.setIcon(R.mipmap.minilogo);

                dlg.setNegativeButton("취소", null);
                dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sharedEditor.remove("MartSize");
                        int totalSize = ySavedHyperMartList.size() + ySavedMiddleMartList.size() + ySavedSmallMartList.size();
                        for (int i = 0; i < totalSize; ++i) {
                            sharedEditor.remove("Mart_" + i);
                        }

                        for (int i = 0; i < ySavedHyperMartList.size(); ++i) {
                            sharedEditor.remove("HyperMart_" + i);
                        }
                        for (int i = 0; i < ySavedMiddleMartList.size(); ++i) {
                            sharedEditor.remove("MiddleMart_" + i);
                        }
                        for (int i = 0; i < ySavedSmallMartList.size(); ++i) {
                            sharedEditor.remove("SmallMart_" + i);
                        }

                        sharedEditor.remove("HyperMartSize");
                        sharedEditor.remove("MiddleMartSize");
                        sharedEditor.remove("SmallMartSize");

                        sharedEditor.remove("MartDistance");
                        sharedEditor.commit();

                        ySavedHyperMartList.clear();
                        ySavedMiddleMartList.clear();
                        ySavedSmallMartList.clear();

                        yToast.showToast("모든 마트정보가 초기화됐습니다.", Toast.LENGTH_SHORT);

                        yHyperTempCheck = true;
                        yMiddleTempCheck = true;
                        ySmallTempCheck = true;

                        //초기화 된 리스트를 다시 띄워줌.
                        switch (yCheckMart) {
                            case HYPERMART:
                                HyperMarketOnClick(getCurrentFocus());
                                break;
                            case MIDDLEMART:
                                MiddleMarketOnClick(getCurrentFocus());
                                break;
                            case SMALLMART:
                                SmallMarketOnClick(getCurrentFocus());
                                break;
                        }

                        setResult(123);
                    }
                });
                dlg.show();
            }
        });

        /////////////대형마트 리스너
        yHyperMartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                //Toast.makeText(getApplicationContext(),position+"번째 눌림.",Toast.LENGTH_SHORT).show();

                MartInfo yCurMart = ySetHyperMartInfo.get(position);
                //Toast.makeText(getApplicationContext(), yCurMart.GetMartName(), Toast.LENGTH_SHORT).show();

                if (yCurMart.GetSelect() == true) {
                    yCurMart.SetSelect(false);
                    for (int i = 0; i < ySetHyperMartInfo.size(); ++i) {
                        if (yCurMart.GetMartName().equals(ySetHyperMartInfo.get(i).GetMartName())) {
                            ySetHyperMartInfo.get(i).SetSelect(false);
                        }
                    }
                } else {
                    yCurMart.SetSelect(true);
                    for (int i = 0; i < ySetHyperMartInfo.size(); ++i) {
                        if (yCurMart.GetMartName().equals(ySetHyperMartInfo.get(i).GetMartName())) {
                            ySetHyperMartInfo.get(i).SetSelect(true);
                        }
                    }
                }
                yHyperMartAdapter.notifyDataSetChanged();
            }
        });

        //롱클릭을 통해서 기존에 저장되어 있던 마트에 대해서 제거할 수 있음.
        yHyperMartListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //만약 저장된 마트 중, 내 주변거리에 있는 마트가 있다면 그 마트를 롱클릭해서 제거했을 때 리스트에 체크되어 있던 체크도 같이 해제해줘야 할 것.
                final MartInfo yCurMart = ySetHyperMartInfo.get(position);

                //저장된 마트가 아니면 동작하지 않도록 return해버리기. 저장된 유무는 SavedCheck()를 이용하면 될 듯.
                if (yCurMart.SavedCheck()) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(MartOptionActivity.this);
                    dlg.setTitle("마트설정");
                    dlg.setMessage("저장된 마트를 제거하시겠습니까?");
                    dlg.setIcon(R.mipmap.minilogo);

                    dlg.setNegativeButton("취소", null);
                    dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean yInitCheck = false;

                            for (int i = 0; i < ySavedHyperMartList.size(); ++i) {
                                if (yCurMart.GetMartName().equals(ySavedHyperMartList.get(i))) {
                                    ySavedHyperMartList.remove(i);
                                    yInitCheck = true;
                                }
                            }
                            //변경사항이 있어서 제거를 했을때만 새롭게 리스트뷰를 띄워줌.
                            if (yInitCheck) {
                                Log.d("asdasdasd","Asdasdasdassd");
                                yHyperTempCheck = true;
                                SetListView(yHyperMartList);
                            }
                        }
                    });
                    dlg.show();
                } else {
                    //Toast.makeText(MartOptionActivity.this, "저장안된 마트임! ," + yCurMart.GetMartName(), Toast.LENGTH_SHORT).show();
                    checkGps = settingLocation();
                    if (checkGps) {
                        ShowMap(yCurMart.GetMartName());
                    }
                    return false;
                }
                return true;
            }
        });

        ////////////////////////중형마트 리스너
        yMiddleMartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                //Toast.makeText(getApplicationContext(),position+"번째 눌림.",Toast.LENGTH_SHORT).show();

                MartInfo yCurMart = ySetMiddleMartInfo.get(position);
                //Toast.makeText(getApplicationContext(), yCurMart.GetMartName(), Toast.LENGTH_SHORT).show();

                if (yCurMart.GetSelect() == true) {
                    yCurMart.SetSelect(false);
                    for (int i = 0; i < ySetMiddleMartInfo.size(); ++i) {
                        if (yCurMart.GetMartName().equals(ySetMiddleMartInfo.get(i).GetMartName())) {
                            ySetMiddleMartInfo.get(i).SetSelect(false);
                        }
                    }
                } else {
                    yCurMart.SetSelect(true);
                    for (int i = 0; i < ySetMiddleMartInfo.size(); ++i) {
                        if (yCurMart.GetMartName().equals(ySetMiddleMartInfo.get(i).GetMartName())) {
                            ySetMiddleMartInfo.get(i).SetSelect(true);
                        }
                    }
                }
                yMiddleMartAdapter.notifyDataSetChanged();
            }
        });

        //롱클릭을 통해서 기존에 저장되어 있던 마트에 대해서 제거할 수 있음.
        yMiddleMartListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //만약 저장된 마트 중, 내 주변거리에 있는 마트가 있다면 그 마트를 롱클릭해서 제거했을 때 리스트에 체크되어 있던 체크도 같이 해제해줘야 할 것.
                final MartInfo yCurMart = ySetMiddleMartInfo.get(position);

                //저장된 마트가 아니면 동작하지 않도록 return해버리기. 저장된 유무는 SavedCheck()를 이용하면 될 듯.
                if (yCurMart.SavedCheck()){
                    AlertDialog.Builder dlg = new AlertDialog.Builder(MartOptionActivity.this);
                    dlg.setTitle("마트설정");
                    dlg.setMessage("저장된 마트를 제거하시겠습니까?");
                    dlg.setIcon(R.mipmap.minilogo);

                    dlg.setNegativeButton("취소", null);
                    dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean yInitCheck = false;

                            for (int i = 0; i < ySavedMiddleMartList.size(); ++i) {
                                if (yCurMart.GetMartName().equals(ySavedMiddleMartList.get(i))) {
                                    ySavedMiddleMartList.remove(i);
                                    yInitCheck = true;
                                }
                            }
                            //변경사항이 있어서 제거를 했을때만 새롭게 리스트뷰를 띄워줌.
                            if (yInitCheck) {
                                yMiddleTempCheck = true;
                                SetListView(yMiddleMartList);
                            }
                        }
                    });
                    dlg.show();
                }else{
                    //Toast.makeText(MartOptionActivity.this, "저장안된 마트임! ," + yCurMart.GetMartName(), Toast.LENGTH_SHORT).show();
                    checkGps = settingLocation();
                    if (checkGps) {
                        ShowMap(yCurMart.GetMartName());
                    }
                    return false;
                }
                return true;
            }
        });

        ////////////////////////소형마트 리스너
        ySmallMartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                //Toast.makeText(getApplicationContext(),position+"번째 눌림.",Toast.LENGTH_SHORT).show();

                MartInfo yCurMart = ySetSmallMartInfo.get(position);
                //Toast.makeText(getApplicationContext(), yCurMart.GetMartName(), Toast.LENGTH_SHORT).show();

                if (yCurMart.GetSelect() == true) {
                    yCurMart.SetSelect(false);
                    for (int i = 0; i < ySetSmallMartInfo.size(); ++i) {
                        if (yCurMart.GetMartName().equals(ySetSmallMartInfo.get(i).GetMartName())) {
                            ySetSmallMartInfo.get(i).SetSelect(false);
                        }
                    }
                } else {
                    yCurMart.SetSelect(true);
                    for (int i = 0; i < ySetSmallMartInfo.size(); ++i) {
                        if (yCurMart.GetMartName().equals(ySetSmallMartInfo.get(i).GetMartName())) {
                            ySetSmallMartInfo.get(i).SetSelect(true);
                        }
                    }
                }
                ySmallMartAdapter.notifyDataSetChanged();
            }
        });

        //롱클릭을 통해서 기존에 저장되어 있던 마트에 대해서 제거할 수 있음.
        ySmallMartListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //만약 저장된 마트 중, 내 주변거리에 있는 마트가 있다면 그 마트를 롱클릭해서 제거했을 때 리스트에 체크되어 있던 체크도 같이 해제해줘야 할 것.
                final MartInfo yCurMart = ySetSmallMartInfo.get(position);

                //저장된 마트가 아니면 동작하지 않도록 return해버리기. 저장된 유무는 SavedCheck()를 이용하면 될 듯.
                if (yCurMart.SavedCheck()){
                    AlertDialog.Builder dlg = new AlertDialog.Builder(MartOptionActivity.this);
                    dlg.setTitle("마트설정");
                    dlg.setMessage("저장된 마트를 제거하시겠습니까?");
                    dlg.setIcon(R.mipmap.minilogo);

                    dlg.setNegativeButton("취소", null);
                    dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean yInitCheck = false;

                            for (int i = 0; i < ySavedSmallMartList.size(); ++i) {
                                if (yCurMart.GetMartName().equals(ySavedSmallMartList.get(i))) {
                                    ySavedSmallMartList.remove(i);
                                    yInitCheck = true;
                                }
                            }
                            //변경사항이 있어서 제거를 했을때만 새롭게 리스트뷰를 띄워줌.
                            if (yInitCheck) {
                                ySmallTempCheck = true;
                                SetListView(ySmallMartList);
                            }
                        }
                    });
                    dlg.show();
                }else{
                    //Toast.makeText(MartOptionActivity.this, "저장안된 마트임! ," + yCurMart.GetMartName(), Toast.LENGTH_SHORT).show();
                    checkGps = settingLocation();
                    if (checkGps) {
                        ShowMap("또또 할인마트");
                    }
                    return false;
                }
                return true;
            }
        });

    } //~onCreate

    //3km짜리 라디오 버튼 클릭시(초기화를 위해 메소드로 뺌),
    public void Radio3KmBtnOnClcik(View v){
        yRadio3KmBtn.setChecked(true);
        //radius에 거리 설정.
        radius = 3000;

        yHyperTempCheck = true;
        yMiddleTempCheck = true;
        ySmallTempCheck = true;

        //마지막에 클릭한 거리에 대한 정보를 저장.
        sharedEditor.remove("MartDistance");
        sharedEditor.putInt("MartDistance", 3);

        yHyperMarketBtn.setVisibility(View.VISIBLE);
        yMiddleMarketBtn.setVisibility(View.VISIBLE);
        ySmallMarketBtn.setVisibility(View.VISIBLE);

        //라디오버튼만 눌러도 목록을 출력할 수 있도록 만듬.
        if(yCheckMart != null){
            switch (yCheckMart){
                case HYPERMART:
                    HyperMarketOnClick(getCurrentFocus());
                    break;
                case MIDDLEMART:
                    MiddleMarketOnClick(getCurrentFocus());
                    break;
                case SMALLMART:
                    SmallMarketOnClick(getCurrentFocus());
                    break;
            }
        }
    }

    //대형마트 버튼 클릭시(초기화를 위해 메소드로 뺌),
    public void HyperMarketOnClick(View v){
        yHyperMarketBtn.setBackgroundResource(R.drawable.tab1);
        yMiddleMarketBtn.setBackgroundResource(R.drawable.tab3);
        ySmallMarketBtn.setBackgroundResource(R.drawable.tab3);
        yHyperMartList.clear();
        ySaveBtn.setVisibility(View.VISIBLE);
        ySelectAllBtn.setVisibility(View.VISIBLE);
        yFinishBtn.setVisibility(View.VISIBLE);

        yHyperMartListView.setVisibility(View.VISIBLE);

        yMiddleMartListView.setVisibility(View.GONE);
        ySmallMartListView.setVisibility(View.GONE);

        checkGps = settingLocation();
        if (checkGps) {
            yCheckMart = HYPERMART;
            searchLocation(HYPERMART);
            //메인스레드에서 서브스레가 돌린 결과를  어떻게 알수있을까. 이미지를 셋팅하는 작업은 오직 메인스레드에서만 가능함...
        }
    }

    //중형마트 버튼 클릭시(초기화를 위해 메소드로 뺌),
    public void MiddleMarketOnClick(View v){
        yHyperMarketBtn.setBackgroundResource(R.drawable.tab3);
        yMiddleMarketBtn.setBackgroundResource(R.drawable.tab1);
        ySmallMarketBtn.setBackgroundResource(R.drawable.tab3);
        yMiddleMartList.clear();
        ySaveBtn.setVisibility(View.VISIBLE);
        ySelectAllBtn.setVisibility(View.VISIBLE);
        yFinishBtn.setVisibility(View.VISIBLE);

        yMiddleMartListView.setVisibility(View.VISIBLE);

        yHyperMartListView.setVisibility(View.GONE);
        ySmallMartListView.setVisibility(View.GONE);

        checkGps = settingLocation();
        if (checkGps) {
            yCheckMart = MIDDLEMART;
            searchLocation(MIDDLEMART);
        }
    }

    //소형마트 버튼 클릭시(초기화를 위해 메소드로 뺌),
    public void SmallMarketOnClick(View v){
        yHyperMarketBtn.setBackgroundResource(R.drawable.tab3);
        yMiddleMarketBtn.setBackgroundResource(R.drawable.tab3);
        ySmallMarketBtn.setBackgroundResource(R.drawable.tab1);
        ySmallMartList.clear();
        ySaveBtn.setVisibility(View.VISIBLE);
        ySelectAllBtn.setVisibility(View.VISIBLE);
        yFinishBtn.setVisibility(View.VISIBLE);

        ySmallMartListView.setVisibility(View.VISIBLE);

        yHyperMartListView.setVisibility(View.GONE);
        yMiddleMartListView.setVisibility(View.GONE);

        checkGps = settingLocation();
        if (checkGps) {
            yCheckMart = SMALLMART;
            searchLocation(SMALLMART);
        }
    }

    //토스트 띄어주기 위한 메소드
    public void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean settingLocation() {
        gps = new GPSInfo(MartOptionActivity.this);
        if (gps.isGetLocation()) {
            if (gps.getLatitude() == 0 && gps.getLongitude() == 0) {
                yToast.showToast("사용자 위치 파악중입니다.\n마트선택을 다시 해주십시오.", Toast.LENGTH_SHORT);
                return false;
            } else {
                setLatitude(gps.getLatitude());
                setLongitude(gps.getLongitude());
                return true;
            }
        }
        return false;
    }

    //지도띄우기
    public void ShowMap(final String MartName) {
        Searcher searcher = new Searcher();

        searcher.searchKeywordSingle(getApplicationContext(), MartName, getLatitude(), getLongitude(), radius, page,
                MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) { }

                    @Override
                    public void onSuccessSingle(Item item) {
                        setdLatitude(item.latitude);
                        setdLongitude(item.longitude);
                        uDistance = radius;

                        Log.d("InfoActivity", "distance lat : " + getdLatitude());
                        Log.d("InfoActivity", "distance lng : " + getdLongitude());
                        Log.d("InfoActivity", "distance : " + uDistance);

                        Intent intent = new Intent(MartOptionActivity.this, ShowLocation.class);
                        intent.putExtra("ULAT", getLatitude());
                        intent.putExtra("ULNG", getLongitude());
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

    //department가 키워드고, 대형의 경우 department에 대형마트 / 중/소형의 경우 department에 슈퍼마켓.
    //대형ArrayList와 소형ArrayList를 저장시켜서 관리할 수 있도록.
    public void searchLocation(final String department) {
        Searcher searcher = new Searcher();
        searcher.searchKeyword(getApplicationContext(), department, getLatitude(), getLongitude(), radius, page,
                MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        Log.d("onSuccess", "Search 성공");
                        Log.d("MartOptionActivity", "받은 radius는 : " + radius);
                        Log.d("MartOptionActivity", "받은 department는 : " + department);
                        Log.d("MartOptionActivity", "받은 사이즈는 : " + itemList.size());

                        yAllMartList = itemList;
                        Log.d("MartOptionActivity", "yAllMartList 사이즈 : " + yAllMartList.size());

                        switch (yCheckMart){
                            case HYPERMART:
                            {
                                //ySetHyperMartInfo.clear();

                                for(int i = 0 ; i < yAllMartList.size() ; ++i){
                                    if(yAllMartList.get(i).title.contains("홈플러스")){
                                        if(yAllMartList.get(i).title.contains("익스프레스")){
                                            Log.d("MartOptionActivity","익스프레스는 대형마트가 아님!");
                                        }else{
                                            yHyperMartList.add(yAllMartList.get(i));
                                        }
                                    }else if(yAllMartList.get(i).title.contains("롯데마트")){
                                        yHyperMartList.add(yAllMartList.get(i));
                                    }else if(yAllMartList.get(i).title.contains("이마트")){
                                        if(yAllMartList.get(i).title.contains("에브리데이")){
                                            Log.d("MartOptionActivity","에브리데이는 대형마트가 아님!");
                                        }else{
                                            yHyperMartList.add(yAllMartList.get(i));
                                        }
                                    }
                                } //~for문
                                SetListView(yHyperMartList);
                            }
                            break;//~~~~~~~~~~~~~~~~~대형마트

                            case MIDDLEMART:
                            {
                                for(int i = 0 ; i < yAllMartList.size() ; ++i){
                                    if(yAllMartList.get(i).title.contains("홈플러스")){
                                        if(yAllMartList.get(i).title.contains("익스프레스")){
                                            yMiddleMartList.add(yAllMartList.get(i));
                                        }else{
                                            Log.d("MartOptionActivity","그냥 홈플러스는 대형마트임.");
                                        }
                                    }else if(yAllMartList.get(i).title.contains("롯데마트")){
                                        Log.d("MartOptionActivity", "롯데마트는 대형마트임.");
                                    }else if(yAllMartList.get(i).title.contains("이마트")){
                                        if(yAllMartList.get(i).title.contains("에브리데이")){
                                            yMiddleMartList.add(yAllMartList.get(i));
                                        }else{
                                            Log.d("MartOptionActivity","그냥 이마트는 대형마트임.");
                                        }
                                    }else{
                                        yMiddleMartList.add(yAllMartList.get(i));
                                    }
                                } //~for문
                                SetListView(yMiddleMartList);
                            }
                            break;//~~~~~~~~~~~~~~~~~중형마트

                            case SMALLMART:
                            {
                                for(int i = 0 ; i < yAllMartList.size() ; ++i) {
                                    ySmallMartList.add(yAllMartList.get(i));
                                }
                                SetListView(ySmallMartList);
                            }
                            break;//~~~~~~~~~~~~~~~~~소형마트
                        } //~switch문
                    }

                    @Override
                    public void onSuccessSingle(Item item) {
                        Log.d("onSuccessSingle", "onSuccessSingle 성공");
                    }

                    @Override
                    public void onFail() {
                        Log.d("onFail", "Search 실패");
                        showToast("API_KEY의 제한 트래픽이 초과되었습니다.");
                    }
                });
        Log.d("searchLocation 끝", "searchLocation 끝");
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

    public void SortSavedMart(ArrayList<String> arrName, ArrayList<Double> arrDistance) {
        int last = arrName.size() - 1;
        Double tempDistance;
        String tempName;

        for (int i = last; i > 0; --i) {
            for (int j = 0; j < i; ++j) {
                if ((arrDistance.get(j)) > (arrDistance.get(j + 1))) {
                    tempName = arrName.get(j + 1);
                    arrName.set(j + 1, arrName.get(j));
                    arrName.set(j, tempName);

                    tempDistance = arrDistance.get(j + 1);
                    arrDistance.set(j + 1, arrDistance.get(j));
                    arrDistance.set(j, tempDistance);
                }
            }
        }
    }

    public void SetListView(ArrayList<Item> arr){
        switch (yCheckMart){
            case HYPERMART:
            {
                if(yHyperTempCheck) {
                    ySetHyperMartInfo.clear();

                    for (int i = 0; i < ySavedHyperMartList.size(); ++i) {
                        ySetHyperMartInfo.add(new MartInfo(
                                ySavedHyperMartList.get(i), "" + 0.0
                                , getResources().getDrawable(GetHyperMartImage(ySavedHyperMartList.get(i))),
                                true
                        ));
                        Log.d("기존에 저장됐던 마트(" + ySavedHyperMartList.size(), ") 마트명 :" + ySetHyperMartInfo.get(i).GetMartName());
                    }

                    SortLocation(arr); //마트를 거리순 출력.
                    for (int i = 0; i < arr.size(); ++i) {
                        if (arr.size() == yHyperMartList.size()) { //터지는 현상을 방지함.
                            ySetHyperMartInfo.add(new MartInfo(
                                    arr.get(i).title, "" + arr.get(i).distance
                                    , getResources().getDrawable(GetHyperMartImage(arr.get(i).title)),
                                    false
                            ));
                        }
                    }

                    ///////////////////////여기서 해줘야되는데
                    if (ySavedHyperMartList.size() > 0) {
                        for (int i = 0; i < ySetHyperMartInfo.size(); ++i) {
                            ySetHyperMartInfo.get(i).SetSelect(false);
                        }//먼저 다 false로 하고

                        for (int i = 0; i < ySetHyperMartInfo.size(); ++i) {
                            for (int j = 0; j < ySavedHyperMartList.size(); ++j) {
                                if (ySavedHyperMartList.get(j).equals(ySetHyperMartInfo.get(i).GetMartName())) {
                                    ySetHyperMartInfo.get(i).SetSelect(true);
                                }//일치하는 마트만 true로.
                            }
                        }
                    }else if(ySavedHyperMartList.size() ==0){
                        for (int i = 0; i < ySetHyperMartInfo.size(); ++i) {
                            ySetHyperMartInfo.get(i).SetSelect(false);
                        }
                    }

                    yHyperMartAdapter = new MartAdapter(ySetHyperMartInfo, getLayoutInflater(), true);
                }else{
                    Log.d("대형마트 Keep", ", Size는" + ySetHyperMartInfo.size());
                }
            }
            break;

            case MIDDLEMART: {
                if (yMiddleTempCheck) {
                    ySetMiddleMartInfo.clear();

                    for (int i = 0; i < ySavedMiddleMartList.size(); ++i) {
                        ySetMiddleMartInfo.add(new MartInfo(
                                ySavedMiddleMartList.get(i), "" + 0.0
                                , getResources().getDrawable(GetMiddleMartImage(ySavedMiddleMartList.get(i))),
                                true
                        ));
                        Log.d("기존에 저장됐던 마트(" + ySavedMiddleMartList.size(), ") 마트명 :" + ySetMiddleMartInfo.get(i).GetMartName());
                    }

                    SortLocation(arr); //마트를 거리순 출력.
                    for (int i = 0; i < arr.size(); ++i) {
                        if (arr.size() == yMiddleMartList.size()) { //터지는 현상을 방지함.
                            ySetMiddleMartInfo.add(new MartInfo(
                                    arr.get(i).title, "" + arr.get(i).distance
                                    , getResources().getDrawable(GetMiddleMartImage(arr.get(i).title)),
                                    false
                            ));
                        }
                    }

                    ///////////////////////여기서 해줘야되는데
                    if (ySavedMiddleMartList.size() > 0) {
                        for (int i = 0; i < ySetMiddleMartInfo.size(); ++i) {
                            ySetMiddleMartInfo.get(i).SetSelect(false);
                        }//먼저 다 false로 하고

                        for (int i = 0; i < ySetMiddleMartInfo.size(); ++i) {
                            for (int j = 0; j < ySavedMiddleMartList.size(); ++j) {
                                if (ySavedMiddleMartList.get(j).equals(ySetMiddleMartInfo.get(i).GetMartName())) {
                                    ySetMiddleMartInfo.get(i).SetSelect(true);
                                }//일치하는 마트만 true로.
                            }
                        }
                    }else if(ySavedMiddleMartList.size() ==0){
                        for (int i = 0; i < ySetMiddleMartInfo.size(); ++i) {
                            ySetMiddleMartInfo.get(i).SetSelect(false);
                        }
                    }

                    yMiddleMartAdapter = new MartAdapter(ySetMiddleMartInfo, getLayoutInflater(), true);
                }else {
                    Log.d("중형마트 Keep", ", Size는" + ySetMiddleMartInfo.size());
                }
            }
            break;

            case SMALLMART: {
                if (ySmallTempCheck) {
                    ySetSmallMartInfo.clear();

                    for (int i = 0; i < ySavedSmallMartList.size(); ++i) {
                        ySetSmallMartInfo.add(new MartInfo(
                                ySavedSmallMartList.get(i), "" + 0.0
                                , getResources().getDrawable(GetSmallMartImage(ySavedSmallMartList.get(i))),
                                true
                        ));
                        Log.d("기존에 저장됐던 마트(" + ySavedSmallMartList.size(), ") 마트명 :" + ySetSmallMartInfo.get(i).GetMartName());
                    }

                    SortLocation(arr); //마트를 거리순 출력.
                    for (int i = 0; i < arr.size(); ++i) {
                        if (arr.size() == ySmallMartList.size()) { //터지는 현상을 방지함.
                            ySetSmallMartInfo.add(new MartInfo(
                                    arr.get(i).title, "" + arr.get(i).distance
                                    , getResources().getDrawable(GetSmallMartImage(arr.get(i).title)),
                                    false
                            ));
                        }
                    }

                    ///////////////////////여기서 해줘야되는데
                    if (ySavedSmallMartList.size() > 0) {
                        for (int i = 0; i < ySetSmallMartInfo.size(); ++i) {
                            ySetSmallMartInfo.get(i).SetSelect(false);
                        }//먼저 다 false로 하고

                        for (int i = 0; i < ySetSmallMartInfo.size(); ++i) {
                            for (int j = 0; j < ySavedSmallMartList.size(); ++j) {
                                if (ySavedSmallMartList.get(j).equals(ySetSmallMartInfo.get(i).GetMartName())) {
                                    ySetSmallMartInfo.get(i).SetSelect(true);
                                }//일치하는 마트만 true로.
                            }
                        }
                    }else if(ySavedSmallMartList.size() ==0){
                        for (int i = 0; i < ySetSmallMartInfo.size(); ++i) {
                            ySetSmallMartInfo.get(i).SetSelect(false);
                        }//먼저 다 false로 하고
                    }

                    ySmallMartAdapter = new MartAdapter(ySetSmallMartInfo, getLayoutInflater(), true);
                } else {
                    Log.d("소형마트 Keep", ", Size는" + ySetSmallMartInfo.size());
                }
            }
            break;
        } //~~~~switch

        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        /*
                        여기에 swtich문을 걸어서 대/중/소형마트에 대해서 각각 다르게 리스트뷰에 달아야 할 듯.
                        또 리스트뷰를 나눠놨으니까 그에 따라서 리스너도 3개로 나눠야 할 듯.
                        */

                        Log.d("HYPER " , "MartInfo SIZE : " + ySetHyperMartInfo.size());
                        Log.d("MIDDLE ", "MartInfo SIZE : " + ySetMiddleMartInfo.size());
                        Log.d("SMALL", "MartInfo SIZE : " + ySetSmallMartInfo.size());

                        switch(yCheckMart){
                            case HYPERMART:
                                if(ySetHyperMartInfo.size() ==0)
                                    yToast.showToast("설정하신 범위내에 마트가 없습니다.", Toast.LENGTH_SHORT);

                                if (yHyperTempCheck) {
                                    yHyperMartListView.setAdapter(yHyperMartAdapter);
                                    yHyperTempCheck = false;
                                }
                                break;
                            case MIDDLEMART:
                                if(ySetMiddleMartInfo.size() ==0)
                                    yToast.showToast("설정하신 범위내에 마트가 없습니다.", Toast.LENGTH_SHORT);

                                if (yMiddleTempCheck) {
                                    yMiddleMartListView.setAdapter(yMiddleMartAdapter);
                                    yMiddleTempCheck = false;
                                }
                                break;
                            case SMALLMART:
                                if(ySetSmallMartInfo.size() ==0)
                                    yToast.showToast("설정하신 범위내에 마트가 없습니다.", Toast.LENGTH_SHORT);

                                if (ySmallTempCheck) {
                                    ySmallMartListView.setAdapter(ySmallMartAdapter);
                                    ySmallTempCheck = false;
                                }
                                break;
                        }
                    }
                });
            }
        }).start();
    }

    //앱 내부(drawable)에 저장된 마트 이미지를 조건에 맞게 리턴받기 위한 메서드.
    public int GetHyperMartImage(String yGetMartName){
        int yMartImage = 0;
        if(yGetMartName.contains("이마트")){
            yMartImage = R.drawable.emart;
        }else if(yGetMartName.contains("롯데마트")){
            yMartImage = R.drawable.lottemart;
        }else if(yGetMartName.contains("홈플러스")){
            yMartImage = R.drawable.homeplus;
        }else{
            yMartImage = R.drawable.no;
        }
        return yMartImage;
    } // ~GetMartImage

    public int GetMiddleMartImage(String yGetMartName){
        int yMartImage = 0;
        if(yGetMartName.contains("에브리데이")){
            yMartImage = R.drawable.everyday;
        }else if(yGetMartName.contains("킴스")){
            yMartImage = R.drawable.kims;
        }else if(yGetMartName.contains("하나로")){
            yMartImage = R.drawable.hanaro;
        }else{
            yMartImage = R.drawable.no;
        }
        return yMartImage;
    } // ~GetMartImage

    public int GetSmallMartImage(String yGetMartName){
        int yMartImage = 0;
        if(yGetMartName.contains("GS25")){
            yMartImage = R.drawable.gs25;
        }else if(yGetMartName.contains("GS수퍼마켓")){
            yMartImage = R.drawable.gssuper;
        }else if(yGetMartName.contains("999")){
            yMartImage = R.drawable.lotte999;
        }else if(yGetMartName.contains("CU")){
            yMartImage = R.drawable.cu;
        }else if(yGetMartName.contains("세븐")){
            yMartImage = R.drawable.seven_eleven;
        }else{
            yMartImage = R.drawable.no;
        }
        return yMartImage;
    } // ~GetMartImage


    public void onResume(){
        super.onResume();

        ySavedHyperMartList = new ArrayList<String>();
        ySavedMiddleMartList = new ArrayList<String>();
        ySavedSmallMartList = new ArrayList<String>();

        int yHyperMartSize = sharedPref.getInt("HyperMartSize", 0);
        if(yHyperMartSize == 0){
            //Log.d("MartOptionActivity","현재 대형마트에 저장된 목록이 없습니다.");
        }else{
            for(int i = 0 ; i < yHyperMartSize ; i++)
            {
                ySavedHyperMartList.add(sharedPref.getString("HyperMart_" + i, null));
            }
        } //////////대형마트

        int yMiddleMartSize = sharedPref.getInt("MiddleMartSize", 0);
        if(yMiddleMartSize == 0){
            //Log.d("MartOptionActivity","현재 중형마트에 저장된 목록이 없습니다.");
        }else{
            for(int i = 0 ; i < yMiddleMartSize ; i++)
            {
                ySavedMiddleMartList.add(sharedPref.getString("MiddleMart_" + i, null));
            }
        } //////////중형마트

        int ySmallMartSize = sharedPref.getInt("SmallMartSize", 0);
        if(ySmallMartSize == 0){
            //Log.d("MartOptionActivity","현재 소형마트에 저장된 마트목록이 없습니다.");
        }else{
            for(int i = 0 ; i < ySmallMartSize ; i++)
            {
                ySavedSmallMartList.add(sharedPref.getString("SmallMart_" + i, null));
            }
        } //////////소형마트
    }
}
