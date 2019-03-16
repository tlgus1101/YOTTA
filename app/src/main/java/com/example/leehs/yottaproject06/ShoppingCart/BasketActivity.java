package com.example.leehs.yottaproject06.ShoppingCart;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leehs.yottaproject06.FindLocation.Item;
import com.example.leehs.yottaproject06.InsertDataToServer.InsertDataToServer;
import com.example.leehs.yottaproject06.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyCom on 2016-01-05.
 */

/*
장바구니입니다.
잘못 추가한 제품에 대해서는 클릭해서 제거할 수 있습니다.
 */
public class BasketActivity extends Activity {

    final static String SERVER_ADDRESS = "http://220.68.231.118/wook/searchtext.php?name=";

    private List<Product> mCartList;
    private ArrayList<Product> chooseList;
    private ProductAdapter mProductAdapter;

    private TextView exitGoodsCount;
    private TextView exitTotalPrice;
    private TextView allGoodsCount;
    private TextView allTotalPrice;

    private ListView chooseListView;

    private int yNowPosition = -1;

    TextView chooseMart;
    boolean yBtnSelectedCheck = true;
    String settingMart;

    ArrayList<Item> mLocationList;

    double latitude = 0;
    double longitude = 0;
    int radius = 1000;
    int page = 1;

    SharedPreferences sharedInfo;
    SharedPreferences.Editor editor;

    public static boolean yCheckMartInit = false;

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
        setContentView(R.layout.basket_main);

        chooseListView = (ListView) findViewById(R.id.ChooseListView);
        chooseMart = (TextView) findViewById(R.id.chooseMart);
        settingMart = null;
        mCartList = ShoppingHelper.getCart();


        mLocationList = new ArrayList<Item>();
        chooseList = new ArrayList<Product>();
        sharedInfo = getSharedPreferences("martKey", MODE_PRIVATE);
        editor = sharedInfo.edit();

        Log.d("oncreate sharedInfo : ", "" + (sharedInfo.getString("martKey", "")));
        if (sharedInfo.getString("martKey", "") != "") {
            settingMart = sharedInfo.getString("martKey", "");
            chooseMart.setText(settingMart);
        }

        Button controlBtn = (Button) findViewById(R.id.controlBtn);
        controlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DoShoppingBasket", "마트설정 시작");
                settingMart = null;

                Intent intent = new Intent(BasketActivity.this, SettingBasketLocation.class);
                startActivityForResult(intent, 1);
            }
        });

        Log.d("@@@@@ BasketActivity", "onCreate 호출");
    } //여기까지가 onCreate


    public void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BasketActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Compare() {
        Log.d("@@@@@ Compare", "시작");

        chooseList.clear();

        for (int i = 0; i < mCartList.size(); ) {
            //////////// mCartList에 있는 제품명 검색을 AsyncTask로 실행
            boolean check = false;
            Log.d("Compare 오니", "Compare 오니");
            check = searchProduct(mCartList.get(i).GetName(), mCartList.get(i).GetDepartment(), i);
            if (check) {
                ++i;
            }
        }
    }  //~Comapre()

    private boolean searchProduct(String pName, String dName, int num) {
        String departmentName = dName;
        final int pNum = num;

        class findProduct extends AsyncTask<String, Integer, String> {
            @Override
            protected String doInBackground(String... urls) {
                String productName = urls[0];
                StringBuilder jsonHtml = new StringBuilder();
                try {
                    productName = URLEncoder.encode(productName, "UTF-8");

                    // 연결 url 설정
                    URL url = new URL(SERVER_ADDRESS + productName);
                    //URL url = new URL("http://220.68.231.140/wook/searchtext.php?name=" + searchName);
                    Log.d("BasketActivity", url.toString());

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
                Log.d("doInBackground", jsonHtml.toString());
                return jsonHtml.toString();
            }

            public void onPostExecute(String str) {
                Product product;
                String name, price, distance, department, id, count, category, date;
                boolean checkToday = false;
                try {
                    Log.d("PostExecute", "실행 됨?");
                    JSONObject root = new JSONObject(str);
                    JSONArray ja = root.getJSONArray("result");
                    JSONObject jo;

                    jo = ja.getJSONObject(0);
                    name = jo.getString("name");
                    department = jo.getString("department");
                    price = jo.getString("price");
                    count = jo.getString("pcount");
                    date = jo.getString("goods_date");

                    if (department.equals(settingMart)) {
                        if (date.equals(equals(InsertDataToServer.getDateString()))) {
                            checkToday = true;
                        } else {
                            checkToday = false;
                        }
                        product = new Product(name, mCartList.get(pNum).GetBitmap(), department, Integer.parseInt(price), Integer.parseInt(count), mCartList.get(pNum).GetPicker(), checkToday, true);
                    } else {
                        product = new Product(name, mCartList.get(pNum).GetBitmap(), department, Integer.parseInt(price), Integer.parseInt(count), mCartList.get(pNum).GetPicker(), false);
                    }
                    Log.d("ja.length", "" + ja.length());
                    for (int i = 0; i < ja.length(); ++i) {
                        jo = ja.getJSONObject(i);
                        name = jo.getString("name");
                        price = jo.getString("price");
                        department = jo.getString("department");
                        count = jo.getString("pcount");
                        date = jo.getString("goods_date");

                        if (Integer.parseInt(count) < 0) {
                            Log.d("SearchActivity", "입력된 카운트가 3개 미만일 경우, 신뢰도가 없는 가격이기 때문에 서버로부터 데이터를 받아오지 않습니다.");
                        } else {
                            if (department.equals(settingMart)) {
                                if (date.equals(equals(InsertDataToServer.getDateString()))) {
                                    checkToday = true;
                                    if (product.GetDate() == false) {
                                        product = new Product(name, mCartList.get(pNum).GetBitmap(), department, Integer.parseInt(price), Integer.parseInt(count), mCartList.get(pNum).GetPicker(), checkToday, true);
                                    } else if (product.GetCount() <= Integer.parseInt(count)) {
                                        product = new Product(name, mCartList.get(pNum).GetBitmap(), department, Integer.parseInt(price), Integer.parseInt(count), mCartList.get(pNum).GetPicker(), checkToday, true);
                                    }
                                } else {
                                    if (product.GetDate()) {
                                    } else {
                                        if (product.GetCount() <= Integer.parseInt(count)) {
                                            checkToday = false;
                                            product = new Product(name, mCartList.get(pNum).GetBitmap(), department, Integer.parseInt(price), Integer.parseInt(count), mCartList.get(pNum).GetPicker(), checkToday, true);
                                        }
                                    }
                                }
                            } else {
                                if (product == null) {
                                    product = new Product(name, mCartList.get(pNum).GetBitmap(), department, Integer.parseInt(price), Integer.parseInt(count), mCartList.get(pNum).GetPicker(), checkToday, false);
                                }
                            }
                        }
                        Log.d("i 는 얼마나 돌까...", "" + i);
                        if (i + 1 == ja.length()) {
                            Log.d("JSON 파싱 다했는데..", "저장 됬니?");
                            chooseList.add(product);
                        }
                    }//~for

                    viewProduct();
                    SetInfo();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        findProduct task = new findProduct();
        task.execute(pName);

        return true;
    }

    public void showMartSetting() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(BasketActivity.this);

        alertDialog.setTitle("마트 설정");
        alertDialog.setMessage("마트 설정이 되어있지 않습니다. \n마트 설정을 해주시길 바랍니다.");

        alertDialog.setNeutralButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(BasketActivity.this, SettingBasketLocation.class);
                startActivityForResult(intent, 0);
            }
        });
        alertDialog.show();
    }

    /*
    MainActivity에서 3번째 탭호스트(장바구니)를 클릭할때마다 동적으로 불러와서
    실시간으로 장바구니 정보를 업데이트 할 수 있도록 합니다.
     */

    public void DoShoppingBasket() {
        Log.d("@@@DoShoppingBasket", "시작");

        Log.d("밖밖", "ㄴㅁㅇㄴㅁㅇㄴㅁㅇ" + mCartList.size());
        if (mCartList.size() == 0) {
            Log.d("초기혼란ㅇㄹ", "ㄴㅁㅇㄴㅁㅇㄴㅁㅇ" + mCartList.size());
            //마이페이지에서 초기화하고 다시 장바구니로 되돌아 갔을 때, 내용물을 비우기 위해.
            chooseList.clear();
            mProductAdapter = new ProductAdapter(chooseList, getLayoutInflater(), true);
            chooseListView.setAdapter(mProductAdapter);

            //if형을 만들고 boolean타입으로 걸러내면 될 듯. 그럼 static으로 선언해야 되는데 그게 되려나.
            if (yCheckMartInit) {
                yCheckMartInit = false;
                editor.remove("martKey");
                editor.commit();
                settingMart = null;

                chooseMart.setText(settingMart);
            } else {
                Log.d("BaseketActivity", "settingMart ?" + settingMart);
            }
        }

        exitGoodsCount = (TextView) findViewById(R.id.exitGoodsCount);
        exitTotalPrice = (TextView) findViewById(R.id.exitTotalPrice);
        allGoodsCount = (TextView) findViewById(R.id.allGoodsCount);
        allTotalPrice = (TextView) findViewById(R.id.allTotalPrice);

        //chooseList.clear();
        SetInfo();
        viewProduct();

        if (mCartList.size() != 0) {
            Log.d("sharedInfo.getString : ", "" + (sharedInfo.getString("martKey", "")));
            Log.d("settingMart : ", "" + settingMart);
            if (settingMart != null) {
                chooseMart.setText(settingMart);
                Compare();

                //선택한 제품 제거.
                Button yRemoveButton = (Button) findViewById(R.id.ButtonRemoveFromCart);
                yRemoveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Loop through and remove all the products that are selected
                        // Loop backwards so that the remove works correctly
                        if (chooseList.size() != 0) {
                            for (int i = chooseList.size() - 1; i >= 0; i--) {

                                if (chooseList.get(i).GetSelect()) {
                                    chooseList.remove(i);
                                    mCartList.remove(i);

                                    SetInfo();
                                }
                            }
                            mProductAdapter.notifyDataSetChanged();
                        }
                    }
                });

                //제품 전체 선택
                Button ySelectAllButton = (Button) findViewById(R.id.ButtonAllFromCart);
                ySelectAllButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Loop through and remove all the products that are selected
                        // Loop backwards so that the remove works correctly
                        if (chooseList.size() != 0) {
                            if (yBtnSelectedCheck) {
                                //전체선택
                                for (int i = chooseList.size() - 1; i >= 0; i--) {

                                    if (!chooseList.get(i).GetSelect()) {
                                        chooseList.get(i).SetSeelect(true);
                                    }
                                }
                                yBtnSelectedCheck = false;
                            } else {
                                //전체 선택 해제
                                for (int i = chooseList.size() - 1; i >= 0; i--) {

                                    if (chooseList.get(i).GetSelect()) {
                                        chooseList.get(i).SetSeelect(false);
                                    }
                                }
                                yBtnSelectedCheck = true;
                            }
                            mProductAdapter.notifyDataSetChanged();
                        }
                    }
                });
            } else {
                showMartSetting();
            }
        }
    }

    public void viewProduct() {
        Log.d("@@@@@ viewProduct", "시작");

        // Make sure to clear the selections
        for (int i = 0; i < chooseList.size(); i++) {
            chooseList.get(i).SetSeelect(false);
        }

        for (int i = 0; i < chooseList.size(); ++i) {
            Log.d("nearCartList", chooseList.get(i).GetName());
        }

        if (mCartList.size() != 0 && chooseList.size() != 0) {
            // Create the list
            mProductAdapter = new ProductAdapter(chooseList, getLayoutInflater(), true);
            chooseListView.setAdapter(mProductAdapter);

        /*
             롱클릭해서 장바구니에서 수량을 수정할 수 있도록.
         */
            chooseListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    yNowPosition = position;
                    Product ySelectedProduct = chooseList.get(position);

                    Intent intent = new Intent(BasketActivity.this, AdjustActivity.class);
                    intent.putExtra("NAME", ySelectedProduct.GetName());
                    intent.putExtra("PICKER", "" + ySelectedProduct.GetPicker());
                    intent.putExtra("PRICE", "" + ySelectedProduct.GetPrice());
                    intent.putExtra("IMAGE", ySelectedProduct.GetBitmap());
                    Parcelable yForSendImage = ySelectedProduct.GetBitmap();
                    intent.putExtra("IMAGE", yForSendImage);
                    startActivityForResult(intent, 0);

                    mProductAdapter.notifyDataSetChanged();

                    return true;
                }
            });

            chooseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Product selectedProduct = chooseList.get(position);
                    if (selectedProduct.GetSelect() == true)
                        selectedProduct.SetSeelect(false);
                    else
                        selectedProduct.SetSeelect(true);

                    mProductAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    /*
    해당 레이아웃에 있는 텍스트 정보를 변경시킵니다.
     */

    public void SetInfo() {
        Log.d("@@@@@ SetInfo", "시작");

        int eTotalPrice = 0;
        int aTotalPrice = 0;
        int eCount = 0;
        int aCount = 0;

        for (int i = 0; i < chooseList.size(); ++i) {
            if (chooseList.get(i).GetYesNo()) {
                ++eCount;
            }
            ++aCount;
        }
        exitGoodsCount.setText("구매 가능한 물품 : " + eCount + "개");
        allGoodsCount.setText("모든 물품 : " + aCount + "개");

        for (int i = 0; i < chooseList.size(); ++i) {
            if (chooseList.get(i).GetYesNo()) {
                Log.d("SetInfo", "" + chooseList.get(0).GetCount());
                eTotalPrice += (chooseList.get(i).GetPrice() * chooseList.get(i).GetPicker());
            }
            aTotalPrice += (chooseList.get(i).GetPrice() * chooseList.get(i).GetPicker());
        }
        exitTotalPrice.setText("총 가격 : " + eTotalPrice + "원");
        allTotalPrice.setText("총 가격 : " + aTotalPrice + "원");
    }

    /*
        장바구니를 롱클릭해서 갯수를 수정하면 그 값을 받아와서 반영시킴.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //만약 취소해서 아무 값도 넘어오지 않는다면 동작시키지 않는다.
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 0:
                if (data != null) {
                    int yGetCount = Integer.parseInt(data.getStringExtra("RESULT"));
                    chooseList.get(yNowPosition).setPicker(yGetCount);

                    final ListView chooseListView = (ListView) findViewById(R.id.ChooseListView);
                    mProductAdapter = new ProductAdapter(chooseList, getLayoutInflater(), true);
                    chooseListView.setAdapter(mProductAdapter);

                    SetInfo();
                }
                break;
            case 1:
                settingMart = data.getStringExtra("MART");
                if (sharedInfo.getString("martKey", "") == "") {
                    editor.putString("martKey", settingMart);
                    editor.commit();
                } else {
                    editor.remove("martKey");
                    editor.putString("martKey", settingMart);
                    editor.commit();
                }
                if (settingMart != null) {
                    chooseMart.setText(settingMart);
                }

                DoShoppingBasket();
                break;
        }
    } //~onActivityResult

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "한번 더 누르면 앱이 종료 됩니다.", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}