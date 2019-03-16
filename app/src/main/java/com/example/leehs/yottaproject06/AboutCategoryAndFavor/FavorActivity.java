package com.example.leehs.yottaproject06.AboutCategoryAndFavor;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.leehs.yottaproject06.AboutMaps.GPSInfo;
import com.example.leehs.yottaproject06.AboutMaps.MapApiConst;
import com.example.leehs.yottaproject06.FindLocation.Item;
import com.example.leehs.yottaproject06.FindLocation.OnFinishSearchListener;
import com.example.leehs.yottaproject06.FindLocation.Searcher;
import com.example.leehs.yottaproject06.ForSearch.SearchActivity;
import com.example.leehs.yottaproject06.ForSearch.SearchProduct;
import com.example.leehs.yottaproject06.InfoActivity;
import com.example.leehs.yottaproject06.ListItem;
import com.example.leehs.yottaproject06.MainActivity;
import com.example.leehs.yottaproject06.R;
import com.example.leehs.yottaproject06.ToastMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

/**
 * Created by MyCom on 2016-01-19.
 */
public class FavorActivity extends Activity implements AdapterView.OnItemClickListener {

    Button favor1, favor2, favor3, must, hot;
    TextView yViewFavorText;
    private ArrayList<String> ySavedMartList;
    int mustclick = 0;

    public static final String PRODUCT_INDEX = "PRODUCT";
    public ArrayList<ListItem> searchItemAll;
    public ArrayList<ListItem> searchItem;
    public static ArrayList<ListItem> selectItem;
    GridView gridBox;
    private List<SearchProduct> ySearchData;

    // 애플리케이션 전체에서 사용할 값
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor sharedEditor;
    private ArrayList<HashMap<String, Bitmap>> bitList;

    ArrayList<String> yFavorList;
    ViewFlipper flipper;

    String dateNow;
    /////////////////////////////////////////////////////////////////////////////////////////////////
    int addcount;
    int onPostExecutecount = 0;
    /////////////////////////
    GPSInfo gps;
    boolean checkButton;
    boolean checkGps;

    public static boolean change = false;
    boolean first = true;

    double latitude = 0;
    double longitude = 0;
    int radius = 3000;
    int page = 1;

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

    int btn = 1;
    int clickMustHot = 0;
    boolean checkButtonClick;
    boolean musthot;
    public ArrayList<ListItem> mustList;
    public ArrayList<ListItem> hotList;
    private ArrayList<HashMap<String, Bitmap>> mustbitList;
    private ArrayList<HashMap<String, Bitmap>> hotbitList;

    private ToastMessage yToast;

    private Button yViewFilpperBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favor_main);

        yViewFavorText = (TextView) findViewById(R.id.yViewFavorText);
        yViewFavorText.setBackgroundDrawable(new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.title3)));

        must = (Button) findViewById(R.id.must);
        hot = (Button) findViewById(R.id.hot);
        favor1 = (Button) findViewById(R.id.favor1);  //Item1,2,3버튼
        favor2 = (Button) findViewById(R.id.favor2);
        favor3 = (Button) findViewById(R.id.favor3);

        yViewFilpperBtn = (Button)findViewById(R.id.VFC);

        musthot = true;
        checkButtonClick = true;
        checkGps=false;

        clickMustHot = 1;

        dateNow = getDateString();

        searchItemAll = new ArrayList<ListItem>();
        searchItem = new ArrayList<ListItem>();
        ySearchData = new Vector<SearchProduct>();
        bitList = new ArrayList<HashMap<String, Bitmap>>();   //이미지 저장 리스트
        mustbitList = new ArrayList<HashMap<String, Bitmap>>(); // must일때 이미지 저장
        hotbitList = new ArrayList<HashMap<String, Bitmap>>(); // hot일때 이미지 저장
        mustList = new ArrayList<ListItem>();   //must 리스트
        hotList = new ArrayList<ListItem>(); // hot 리스트

        ///////선호제품//////////////////
        sharedPref = getSharedPreferences ( "pref", Activity.MODE_PRIVATE );//폰 메모리에 저장
        sharedEditor = sharedPref.edit ();

        //ViewFlipper 객체 참조
        flipper = (ViewFlipper) findViewById(R.id.flipper);

        //총 10개의 이미지 중 3개만 XML에서 ImageView로 만들었었음.
        //소스코드에서 ViewFipper에게 나머지 7개의 이미지를 추가하기위해
        //ImageView 7개를 만들어서 ViewFlipper에게 추가함
        //layout_width와 layout_height에 대한 특별한 지정이 없다면
        //기본적으로 'match_parent'로 설정됨.
        for (int i = 1; i < 5; i++) {
            ImageView img = new ImageView(this);
            img.setImageResource(R.drawable.view1 + i);
            flipper.addView(img);

            Animation showIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
            // 왼쪽 방향 에니메이션 지정
            //flipper.setInAnimation(AnimationUtils.loadAnimation(this,
              //      R.anim.push_left_in));
            //flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
             //       R.anim.push_left_out));

            flipper.setInAnimation(showIn);
            flipper.setOutAnimation(this, android.R.anim.fade_out);
            //   toggle_Flipping = (ViewFlipper) findViewById(R.id.toggle_auto);

            flipper.setFlipInterval(4000);//플리핑 간격(1000ms)
            flipper.startFlipping();//자동 Flipping 시작
        }

        gridBox = (GridView) findViewById(R.id.gridView1);

        yToast = new ToastMessage(FavorActivity.this);
    }

    public void SetFavorText() {
        // 관심상품 폰 내에 저장되어있는 이름 받아오기
        String yMyFavor = "";
        addcount = 0;

        if (yFavorList.size() == 0) {
            checkButtonClick = true;
        }

        for (int i = 0; i < yFavorList.size(); ++i) {
            yMyFavor += yFavorList.get(i);
            String idx = "" + i;

            getTextInfo(yFavorList.get(i),i);

            checkButtonClick = false;//돌아간 작업이 완료된 후에 클릭 할 수 있게함
        }
    }

    private void getTextInfo(final String fname,final int idx) {
        class phpDown extends AsyncTask<String, Integer, String>
        {
            ProgressDialog asyncDialog = new ProgressDialog(FavorActivity.this);

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

                Log.d("doInBackground","ㄴ");
                StringBuilder jsonHtml = new StringBuilder();
                try {
                    String name = URLEncoder.encode(urls[0].toString(), "utf-8");

                    URL url = new URL("http://220.68.231.118/hyeongun/favor.php?name=" + name + "&goods_date=" + dateNow);


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

            public void onPostExecute(String str) {
                String name, price, distance, department, id, count, category, date, date2;
                int p;
                distance = "";
                try {
                    JSONObject jsonObj = new JSONObject(str);
                    JSONArray items = jsonObj.getJSONArray("result");
                    Log.d("onSuccessSingle", "onPostExecute 성공" + items.length());

                    for (int i = 0; i < items.length(); ++i) {
                        JSONObject jo = items.getJSONObject(i);
                        name = jo.getString("name");
                        price = jo.getString("price");
                        department = jo.getString("department");
                        id = jo.getString("id");
                        count = jo.getString("pcount");
                        category = jo.getString("category");
                        date = jo.getString("goods_date");

                        //date2 = date.replace("2016-", "");
                        //date = date2;

                        Log.d("InfoText안","ㄴ"+yFavorList.get(idx));
                        Log.d("InfoText안","ㄴ"+name);
                        if (yFavorList.get(idx).equals(name) == true) {
                            searchItemAll.add(new ListItem(name, price, distance, department, id, count, category, date));
                        }

                    }
                } catch (JSONException je) {
                    je.printStackTrace();
                }

                onPostExecutecount++;
                Log.d("onSuccessSingle", "onPostExecutecount 성공" + onPostExecutecount);

                if (onPostExecutecount == yFavorList.size()) {
                    if (searchItemAll.size() != 0) {
                        if (addcount == 0) {
                            searchItem.add(new ListItem(searchItemAll.get(0).getData(0), searchItemAll.get(0).getData(1), searchItemAll.get(0).getData(2), searchItemAll.get(0).getData(3), searchItemAll.get(0).getData(4),
                                    searchItemAll.get(0).getData(5), searchItemAll.get(0).getData(6), searchItemAll.get(0).getData(8)));
                            addcount++;
                        }
                        Compare();
                        for (int j = 0; j < searchItem.size(); ) {
                            j = searchLocation(searchItem.get(j).getData(3), j);
                            getImage(searchItem.get(j).getData(0));
                            j++;
                            //onPostExecutecount++;
                        }
                    }
                    if (searchItemAll == null || searchItemAll.size()==0) {
                        checkButtonClick=true;
                    }
                }
                asyncDialog.dismiss();
            }
        }/////////////////////////////////
        phpDown yPhpDown = new phpDown();
        yPhpDown.execute(fname);
    }

    private class searchWeb extends AsyncTask<String, Void, String> {//must,hot 서버에서 text(must,hot)가져오기

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();

            try {
                URL url = new URL("http://220.68.231.91/wook/searchtext_must.php?musthot=" + clickMustHot);

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
            String price = "0", distance = "", department = "", id = "", count1 = "", category = "", date = "";
            try {
                JSONObject jsonObj = new JSONObject(str);
                JSONArray items = jsonObj.getJSONArray("result");

                Log.d("여기는 searchWeb ","onPostExecute"+items.length());

                for (int i = 0; i < items.length(); ++i) {
                    JSONObject jo = items.getJSONObject(i);
                    String name = jo.getString("name");

                    if (clickMustHot == 1) { //must일때 must리스트에 저장
                        mustList.add(new ListItem(name, price, distance, department, id, count1, category, date));

                    } else if (clickMustHot == 2) { //hot일때 hot리스트에 저장
                        hotList.add(new ListItem(name, price, distance, department, id, count1, category, date));

                    }
                }

                if (clickMustHot == 1) {
                    for (int i = 0; i < mustList.size(); i++) {
                        getImage(mustList.get(i).getData(0));
                    }
                }
                if (clickMustHot == 2) {
                    for (int i = 0; i < hotList.size(); i++) {
                        getImage(hotList.get(i).getData(0));
                    }
                }
            } catch (JSONException je) {
                je.printStackTrace();
            }
        }
    }

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
//                        Log.d("onSuccess에서", "name : " + searchItem.get(num).getDepart());
                        Log.d("onSuccess에서", "" + item.distance);

                        searchItem.get(num).setDistance(String.valueOf(item.distance));
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

    public void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(FavorActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void PrintArray() { //그리드뷰 출력
        if (musthot == true) //must or hot이 선택 되었을때 들어옴
        {
            searchItem.clear();
            bitList.clear();

            if (clickMustHot == 1) //1일때 must를 보여줌
            {
                for (int i = 0; i < mustList.size(); i++) {
                    searchItem.add(new ListItem(mustList.get(i).getData(0), mustList.get(i).getData(1), mustList.get(i).getData(2), mustList.get(i).getData(3)
                            , mustList.get(i).getData(4), mustList.get(i).getData(5), mustList.get(i).getData(6), mustList.get(i).getData(8)));
                }
                for (int i = 0; i < mustbitList.size(); i++) {
                    bitList.add(mustbitList.get(i));
                }
            } else if (clickMustHot == 2) //2일때 hot을 보여줌
            {
                for (int i = 0; i < hotList.size(); i++) {
                    searchItem.add(new ListItem(hotList.get(i).getData(0), hotList.get(i).getData(1), hotList.get(i).getData(2), hotList.get(i).getData(3)
                            , hotList.get(i).getData(4), hotList.get(i).getData(5), hotList.get(i).getData(6), hotList.get(i).getData(8)));
                }
                for (int i = 0; i < hotbitList.size(); i++) {
                    bitList.add(hotbitList.get(i));
                }
            }
        }

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
                    1 //여기선 카운트가 필요 없기 때문에 임의로 1을 넣어줬는데 혹시나 추후에 문제가 생기면 LHS한테 말해주세요.
            ));
        }
        if (musthot == true) {
            //  if(ySearchData == null)
            gridBox.setAdapter(new MustHotAdapter(ySearchData, getLayoutInflater(), false));
            //musthot=false;
        } else {
            // if(ySearchData == null)
            gridBox.setAdapter(new FavorAdapter(ySearchData, getLayoutInflater(), false));
            //musthot=false;
        }
        checkButton = true;

        checkButtonClick = true;
        gridBox.setOnItemClickListener(this); //정상적으로 리스트가 출력된 이후에야 리스트가 클릭되도록.
    }

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
                if (musthot == true) {
                    bitList.clear();
                    //must나hot일때 서로 비워줘야 화면에 각자 출력됨
                }

                if (b != null) {
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

                if (musthot == true) {
                    if (clickMustHot == 1) {
                        for (int i = 0; i < bitList.size(); i++)
                            mustbitList.add(bitList.get(i));
                    } else if (clickMustHot == 2) {
                        for (int i = 0; i < bitList.size(); i++)
                            hotbitList.add(bitList.get(i));
                    }
                }

                /*
                여기가 사실 핵심인데 이렇게 해놓은 이유는 비동기 스레드가 서로 다르게 돌아가기 때문에
                메인스레드에서 최종 결과를 출력하려고 하면 그때는 아직 bitList나 searchItem이 완전하게 정보를 받아오지 않았을 수도 있습니다.
                메인 스레드와 비동기 스레드는 서로 다르게 돌아가고 그렇다고 비동기 스레드가 돌아가는 동안 메인 스레드를 멈출 수 없기 때문에
                비동기 스레드에서 작업을 다 끝냈을 경우 최종 결과를 출력합니다.
                또한 searchItem.size() == bitList.size() 이라는 조건을 걸어놓은 이유는 goods 테이블과 images 테이블, 두 테이블 간에 서로 id를 통해서 데이터를 동기화 시켜놨기 때문에
                두 ArrayList의 결과(크기)가 같아질 경우에만 출력해야 문제가 없어집니다.
                 */

                if (musthot == true) {
                    if (clickMustHot == 1) {
                        if (mustList.size() == mustbitList.size()) {
                            PrintArray();
                        }
                    } else if (clickMustHot == 2) {
                        if (hotList.size() == hotbitList.size()) {
                            PrintArray();
                        }
                    }
                }
                if (searchItem.size() == bitList.size()) {
                    PrintArray();
                }
            }

            //////나중에 제품에 있는 id와 연동해서 선택된 이미지만 저장한 다음 출력해줄 수 있도록 수정해야 할 듯.
            @Override
            protected Bitmap doInBackground(String... params) {
                String pname = params[0];
                try {
                    //한글 검색 원활히 되도록 인코딩한 값을 검색어로 넘김.
                    pname = URLEncoder.encode(pname, "utf-8");
                } catch (Exception e) {
                }
                String add = "http://220.68.231.91/wook/getimage.php?pname=" + pname;

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

    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        if (musthot == true) {
            SearchActivity.musthotchoice = true; //must나 hot의 그리드뷰가 클릭 되었을때 트루
            if (clickMustHot == 1) {
                if (mustList.size() != mustbitList.size()) { //덜들어오면 다시 돌 수 있게 return해서 다시돌림
                    Log.d("SearchActivity", "비트맵이 정상적으로 불려지지 않았습니다 : " + mustList.size());
                    return;
                }
            } else if (clickMustHot == 2) {
                if (hotList.size() != hotbitList.size()) {//덜들어오면 다시 돌 수 있게 return해서 다시돌림
                    Log.d("SearchActivity", "비트맵이 정상적으로 불려지지 않았습니다 : " + hotList.size());
                    return;
                }
            }
        } else {
            if (searchItem.size() != bitList.size()) {
                Log.d("SearchActivity", "비트맵이 정상적으로 불려지지 않았습니다 : " + bitList.size());
                return;
            }
        }
        selectItem = new ArrayList<ListItem>();

        if (clickMustHot == 1 || clickMustHot == 2) { //must,hot그리드뷰안에 아이템을 클릭시 검색창으로 갈 수 있게 띄어 줌
            SearchActivity.musthotname = searchItem.get(position).getData(0);
            Intent intent = new Intent(FavorActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {//item1,2,3,일때 인포를 띄어줌
            Intent intent = new Intent(FavorActivity.this, InfoActivity.class);
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

            Log.d("SearchActivity", "셀렉트 네임 : " + name);
            Log.d("SearchActivity", "셀렉트 네임 : " + price);
            Log.d("SearchActivity", "셀렉트 네임 : " + date);
            Log.d("SearchActivity", "셀렉트 네임 : " + department);

            forImage = sendImage;

            intent.putExtra("IMAGE", forImage);//선택한것의 이미지를 info로 보내줌
            intent.putExtra("TEXT", new ListItem(name, price, distance, department, pid, count, category, date));
            //name, price, distance, department, pid, count, category, date정보를 info로 보내줌


            startActivity(intent);
            GetSelectItem(name, department);
        }
    }

    public void GetSelectItem(String selectName, String selectDepartment) {
        Log.d("SearchActivity", "셀렉트 네임 : " + selectName);
        Log.d("SearchActivity", "셀렉트 마트 : " + selectDepartment);
        Log.d("SearchActivity", "searchItemAll: " + searchItemAll.size());
        for (int i = 0; i < searchItemAll.size(); i++) {
            if ((searchItemAll.get(i).getData(0)).equals(selectName) && (searchItemAll.get(i).getData(3)).equals(selectDepartment)) {// && (searchItemAll.get(i).getData(0)).equals(selectDepartment) ){
                selectItem.add(new ListItem(searchItemAll.get(i).getData(0), searchItemAll.get(i).getData(1), searchItemAll.get(i).getData(2),
                        searchItemAll.get(i).getData(3), searchItemAll.get(i).getData(4), searchItemAll.get(i).getData(5),
                        searchItemAll.get(i).getData(6), searchItemAll.get(i).getData(8)));
            }
        }
    }


    public void Compare() {
        int count;
        Log.d("SearchActivity", "Compare" + searchItem.size());

        for (int i = 0; i < searchItemAll.size(); i++) {
            for (int j = 0; j < searchItem.size(); j++) {

                if (searchItem.get(j).getData(0).equals(searchItemAll.get(i).getData(0)) && searchItem.get(j).getData(3).equals(searchItemAll.get(i).getData(3))) {
                    if (Integer.valueOf(searchItem.get(j).getData(5)).intValue() < Integer.valueOf(searchItemAll.get(i).getData(5)).intValue()) {
                        searchItem.get(j).changeList(searchItemAll.get(i));
                    }
                } else {
                    count = 0;
                    for (int n = 0; n < searchItem.size(); n++) {
                        if (searchItemAll.get(i).getData(0).equals(searchItem.get(n).getData(0)) && searchItemAll.get(i).getData(3).equals(searchItem.get(n).getData(3))) {
                        } else {
                            count++;
                            Log.d("search", "count" + count);
                            if (count == searchItem.size()) {
                                for(int z=0 ;z<ySavedMartList.size();z++) {
                                    if(ySavedMartList.get(z)==searchItemAll.get(i).getData(3))
                                    searchItem.add(new ListItem(searchItemAll.get(i).getData(0), searchItemAll.get(i).getData(1), searchItemAll.get(i).getData(2), searchItemAll.get(i).getData(3), searchItemAll.get(i).getData(4),
                                            searchItemAll.get(i).getData(5), searchItemAll.get(i).getData(6), searchItemAll.get(i).getData(8)));
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < searchItem.size(); i++) {
            Log.d("searchItem", "=======" + searchItem.get(i).getData(0));
            Log.d("searchItem", "=======" + searchItem.get(i).getData(3));
        }
    }  //~Comapre()

    public String getDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -0);
        String day = sdf.format(c.getTime());
        return day;
    }

    public boolean settingLocation () {
        gps = new GPSInfo ( FavorActivity.this );
        Log.d ( "*** GPSInfo ***", "lat : 일로 안오냐일로 안와?"+gps.isGPSEnabled() );
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

    public void onResume() {
        super.onResume();
        checkGps = settingLocation();
        //Log.d ( "일로 오니?", "일료농로" + settingLocation());
       if (checkGps) {
            Log.d("일로 오니?", "일료농로");
            SearchActivity.musthotname = null;
            if (first == true || change == true) {

                clickMustHot = 1;
                musthot = true;
                onPostExecutecount = 0;
                first = false;
                change = false;
                yFavorList = new ArrayList<String>();
                checkButtonClick = false;

                onPostExecutecount = 0;
                ySearchData.clear();
                // if(ySearchData == null)
                gridBox.setAdapter(new FavorAdapter(ySearchData, getLayoutInflater(), false));

                bitList.clear();
                searchItemAll.clear();
                searchItem.clear();
                favor1.setBackgroundResource(R.drawable.favor2); //바꾸기
                favor2.setBackgroundResource(R.drawable.favor2); //다른버튼은 원상태
                favor3.setBackgroundResource(R.drawable.favor2); //다른버튼은 원상태
                must.setBackgroundResource(R.drawable.favor);
                hot.setBackgroundResource(R.drawable.favor2);

                if (mustclick == 0) {
                    mustclick = 1;
                    searchWeb sW = new searchWeb();
                    sW.execute();
                } else {
                    first = true;
                    PrintArray();
                }
            } else {
                PrintArray();
            }
       } //~ onResume()
        ySavedMartList = new ArrayList<String>();
        int yMartSize = sharedPref.getInt("MartSize", 0);
        for (int i = 0; i < yMartSize; i++) {
            ySavedMartList.add(sharedPref.getString("Mart_" + i, null));
            Log.d("InfoActivity", i + "번째 저장된 마트 : " + ySavedMartList.get(i));
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////비우기
    public void btnErase(View v) {
        change = true;
        if (btn == 1) {
            yFavorList.clear();
            searchItem.clear();
            sharedEditor.putString("ListSize1", "" + yFavorList.size());
            sharedEditor.commit();

            for (int i = 0; i < yFavorList.size(); ++i) {
                sharedEditor.remove("List1_" + i);
                sharedEditor.commit();
                sharedEditor.putString("List1_" + i, yFavorList.get(i));
                sharedEditor.commit();
            }
            sharedEditor.commit();
            PrintArray();
        } else if (btn == 2) {
            yFavorList.clear();
            searchItem.clear();
            sharedEditor.putString("ListSize2", "" + yFavorList.size());
            sharedEditor.commit();

            for (int i = 0; i < yFavorList.size(); ++i) {
                sharedEditor.remove("List2_" + i);
                sharedEditor.commit();
                sharedEditor.putString("List2_" + i, yFavorList.get(i));
                sharedEditor.commit();
            }
            sharedEditor.commit();
            PrintArray();
        } else if (btn == 3) {
            yFavorList.clear();
            searchItem.clear();
            sharedEditor.putString("ListSize3", "" + yFavorList.size());
            sharedEditor.commit();

            for (int i = 0; i < yFavorList.size(); ++i) {
                sharedEditor.remove("List3_" + i);
                sharedEditor.commit();
                sharedEditor.putString("List3_" + i, yFavorList.get(i));
                sharedEditor.commit();
            }
            sharedEditor.commit();
            PrintArray();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////버튼 클릭하면
    public void OnClicktopList1(View v) {
        if (checkButtonClick == true) {
            btn = 1;
            onPostExecutecount = 0;
            musthot = false;
            yFavorList = new ArrayList<String>();
            clickMustHot = 0;

            onPostExecutecount = 0;
            ySearchData.clear();
            gridBox.setAdapter(new FavorAdapter(ySearchData, getLayoutInflater(), false));

            bitList.clear();
            searchItemAll.clear();
            searchItem.clear();
            favor1.setBackgroundResource(R.drawable.favor); //바꾸기
            favor2.setBackgroundResource(R.drawable.favor2); //다른버튼은 원상태
            favor3.setBackgroundResource(R.drawable.favor2); //다른버튼은 원상태
            must.setBackgroundResource(R.drawable.favor2);
            hot.setBackgroundResource(R.drawable.favor2);

            String yTempGet = sharedPref.getString("ListSize1", "");
            Log.d("InfoActivity", "선호 카운트1 : " + yTempGet);

            if (yTempGet.equals("")) {
                yToast.showToast("탭1에 저장된 선호제품이 없습니다!",Toast.LENGTH_SHORT);
                return;
            }
            int yGetListSize = Integer.parseInt(yTempGet);

            for (int i = 0; i < yGetListSize; i++) {
                yFavorList.add(sharedPref.getString("List1_" + i, null));
                Log.d("InfoActivity", i + "번째 선호 내용물 : " + yFavorList.get(i));
            }
            SetFavorText();
        }
    }

    public void OnClicktopList2(View v) {
        if (checkButtonClick == true) {
            btn = 2;
            onPostExecutecount = 0;
            clickMustHot = 0;
            musthot = false;
            yFavorList = new ArrayList<String>();

            onPostExecutecount = 0;
            ySearchData.clear();
            // if(ySearchData == null)
            gridBox.setAdapter(new FavorAdapter(ySearchData, getLayoutInflater(), false));

            bitList.clear();
            searchItemAll.clear();
            searchItem.clear();
            favor1.setBackgroundResource(R.drawable.favor2); //바꾸기
            favor2.setBackgroundResource(R.drawable.favor); //다른버튼은 원상태
            favor3.setBackgroundResource(R.drawable.favor2); //다른버튼은 원상태
            must.setBackgroundResource(R.drawable.favor2);
            hot.setBackgroundResource(R.drawable.favor2);

            String yTempGet = sharedPref.getString("ListSize2", "");
            Log.d("InfoActivity", "선호 카운트2 : " + yTempGet);

            if (yTempGet.equals("")) {
                yToast.showToast("탭2에 저장된 선호제품이 없습니다!",Toast.LENGTH_SHORT);
                return;
            }
            int yGetListSize = Integer.parseInt(yTempGet);

            for (int i = 0; i < yGetListSize; i++) {
                yFavorList.add(sharedPref.getString("List2_" + i, null));
                Log.d("InfoActivity", i + "번째 선호 내용물 : " + yFavorList.get(i));
            }
            SetFavorText();
        }
    }

    public void OnClicktopList3(View v) {
        if (checkButtonClick == true) {
            btn = 3;
            onPostExecutecount = 0;
            clickMustHot = 0;
            musthot = false;
            yFavorList = new ArrayList<String>();

            onPostExecutecount = 0;
            ySearchData.clear();
            //if(ySearchData == null)
            gridBox.setAdapter(new FavorAdapter(ySearchData, getLayoutInflater(), false));

            bitList.clear();
            searchItemAll.clear();
            searchItem.clear();
            favor1.setBackgroundResource(R.drawable.favor2); //바꾸기
            favor2.setBackgroundResource(R.drawable.favor2); //다른버튼은 원상태
            favor3.setBackgroundResource(R.drawable.favor); //다른버튼은 원상태
            must.setBackgroundResource(R.drawable.favor2);
            hot.setBackgroundResource(R.drawable.favor2);


            String yTempGet = sharedPref.getString("ListSize3", "");
            Log.d("InfoActivity", "선호 카운트3 : " + yTempGet);

            if (yTempGet.equals("")) {
                yToast.showToast("탭3에 저장된 선호제품이 없습니다!",Toast.LENGTH_SHORT);
                return;
            }
            int yGetListSize = Integer.parseInt(yTempGet);

            for (int i = 0; i < yGetListSize; i++) {
                yFavorList.add(sharedPref.getString("List3_" + i, null));
                Log.d("InfoActivity", i + "번째 선호 내용물 : " + yFavorList.get(i));
            }
            SetFavorText();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public boolean onKeyDown(int keyCode, KeyEvent event) //뒤로가기
    {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "한번 더 누르면 앱이 종료 됩니다.", Toast.LENGTH_SHORT).show ();
        }
        return false;
    }


    ////여기부터 must hot/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    int hotclick = 0;


    public void Must (View v) {
        btn = 0;
        checkGps=settingLocation();
        if (checkButtonClick == true) //버튼을 눌렀을때 동작 되면 작업이 수행될때 까지 다른 버튼 안눌림
        {
            checkButtonClick = false;
            bitList.clear ();
            musthot = true; //true면 must
            clickMustHot = 1;
            favor1.setBackgroundResource ( R.drawable.favor2 ); //색바꾸기
            favor2.setBackgroundResource ( R.drawable.favor2 ); //다른버튼은 원상태
            favor3.setBackgroundResource ( R.drawable.favor2 ); //다른버튼은 원상태
            must.setBackgroundResource ( R.drawable.favor );
            hot.setBackgroundResource ( R.drawable.favor2 );
            Log.d ( "InfoActivity", "번째 선호 내용물 : " + checkGps);
            if (checkGps) {
                if (mustclick == 0) {
                    mustclick = 1;
                    searchWeb sW = new searchWeb ();
                    sW.execute ();
                } else {
                    PrintArray ();
                }
            } else {
                PrintArray ();
            }
        }
    }

    public void Hot (View v) {
        btn = 0;
        checkGps=settingLocation();
        if (checkButtonClick == true) {
            checkButtonClick = false;
            bitList.clear ();
            clickMustHot = 2;
            favor1.setBackgroundResource ( R.drawable.favor2 ); //바꾸기
            favor2.setBackgroundResource ( R.drawable.favor2 ); //다른버튼은 원상태
            favor3.setBackgroundResource ( R.drawable.favor2 ); //다른버튼은 원상태
            must.setBackgroundResource ( R.drawable.favor2 );
            hot.setBackgroundResource ( R.drawable.favor );
            musthot = true;

            Log.d("뭐냐야양", "" + checkGps);
            if (checkGps) {
                if (hotclick == 0) {
                    hotclick = 1;
                    searchWeb sW = new searchWeb ();
                    sW.execute ();
                } else {
                    PrintArray ();
                }
            } else {
                PrintArray ();
            }
        }
    }

    public void FavorImgOnClick01(View v){
        //https://7565e1326981685302b622425bb6147a4a23c27c.googledrive.com/host/0B__3yZsgbjAeSVpYU2RxbGVhTzA/view1.png
        Intent intent = new Intent(FavorActivity.this, LargeImageActivity.class );
        intent.putExtra("IMAGE", 1);
        startActivity(intent);
    }
    public void FavorImgOnClick02(View v){
        //https://7565e1326981685302b622425bb6147a4a23c27c.googledrive.com/host/0B__3yZsgbjAeSVpYU2RxbGVhTzA/view1.png
        Intent intent = new Intent(FavorActivity.this, LargeImageActivity.class );
        intent.putExtra("IMAGE",2);
        startActivity(intent);
    }
    public void FavorImgOnClick03(View v){
        //https://7565e1326981685302b622425bb6147a4a23c27c.googledrive.com/host/0B__3yZsgbjAeSVpYU2RxbGVhTzA/view1.png
        Intent intent = new Intent(FavorActivity.this, LargeImageActivity.class );
        intent.putExtra("IMAGE",3);
        startActivity(intent);
    }
    public void FavorImgOnClick04(View v){
        //https://7565e1326981685302b622425bb6147a4a23c27c.googledrive.com/host/0B__3yZsgbjAeSVpYU2RxbGVhTzA/view1.png
        Intent intent = new Intent(FavorActivity.this, LargeImageActivity.class );
        intent.putExtra("IMAGE",4);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recycleView(findViewById(R.id.yViewFavorText));
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

    boolean click=true;
    public void ViewFlipperClose(View v)
    {
        if(click == true) {
            yViewFilpperBtn.setBackgroundDrawable(new BitmapDrawable(getResources(),
                    BitmapFactory.decodeResource(getResources(), R.drawable.viewflipper_down)));

            flipper.setVisibility(View.GONE);
            click=false;
        }
        else if(click == false) {
            yViewFilpperBtn.setBackgroundDrawable(new BitmapDrawable(getResources(),
                    BitmapFactory.decodeResource(getResources(), R.drawable.viewflipper_up)));
            flipper.setVisibility(View.VISIBLE);
            click=true;
        }
    }
}