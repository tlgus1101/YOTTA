package com.example.leehs.yottaproject06.ForSettings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.leehs.yottaproject06.R;
import com.example.leehs.yottaproject06.ShoppingCart.BasketActivity;
import com.example.leehs.yottaproject06.ShoppingCart.Product;
import com.example.leehs.yottaproject06.ShoppingCart.ShoppingHelper;
import com.example.leehs.yottaproject06.ToastMessage;

import java.util.List;

/**
 * Created by 402-4 on 2016-01-18.
 */
public class MyPageActivity extends Activity{

    LinearLayout yMypageLayout;
    Button yNotify, ySetting_mart, yMypagebtn;
    ToastMessage yToast;
    List<Product> yBasketList;

    // 애플리케이션 전체에서 사용할 값
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor sharedEditor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        yMypageLayout = (LinearLayout) findViewById(R.id.yMypageLayout);
        yMypageLayout.setBackgroundDrawable(new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.mypage2)));

        sharedPref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        sharedEditor = sharedPref.edit();

        yNotify = (Button) findViewById(R.id.yNotify);
        ySetting_mart = (Button) findViewById(R.id.ySetting_mart);
        yMypagebtn = (Button) findViewById(R.id.yMypagebtn);

        yToast = new ToastMessage(MyPageActivity.this);


        String yTempGet1 = sharedPref.getString("ListSize1", "");
        String yTempGet2 = sharedPref.getString("ListSize2", "");
        String yTempGet3 = sharedPref.getString("ListSize3", "");
        Log.d("MyPageActivity", "선호 카운트1 : " + yTempGet1);
        Log.d("MyPageActivity", "선호 카운트2 : " + yTempGet2);
        Log.d("MyPageActivity", "선호 카운트3 : " + yTempGet3);

    }

    //모든 정보 초기화해주는 버튼.
    public void InitailizeSavedInfo(View v){
        //1.내 마트설정 정보 초기화 , 2. 선호제품 정버 초기화.
        AlertDialog.Builder dlg = new AlertDialog.Builder(MyPageActivity.this);
        dlg.setTitle("마트설정");
        dlg.setMessage("저장된 모든 정보를 제거하시겠습니까?\n저장된 모든 정보가 제거됩니다!");
        dlg.setIcon(R.mipmap.minilogo);

        dlg.setNegativeButton("취소", null);
        dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //전체 마트정보 제거
                int yGetMartSize = sharedPref.getInt("MartSize", 0);
                sharedEditor.remove("MartSize");

                for (int i = 0; i < yGetMartSize; ++i) {
                    sharedEditor.remove("Mart_" + i);
                }

                //대형마트정보 제거
                int yHyperMartSize = sharedPref.getInt("HyperMartSize", 0);
                sharedEditor.remove("HyperMartSize");
                for (int i = 0; i < yHyperMartSize; ++i) {
                    sharedEditor.remove("HyperMart_" + i);
                }
                //중형마트정보 제거
                int yMiddleMartSize = sharedPref.getInt("MiddleMartSize", 0);
                sharedEditor.remove("MiddleMartSize");
                for (int i = 0; i < yMiddleMartSize; ++i) {
                    sharedEditor.remove("MiddleMart_" + i);
                }
                //소형마트정보 제거
                int ySmallMartSize = sharedPref.getInt("SmallMartSize", 0);
                sharedEditor.remove("SmallMartSize");
                for (int i = 0; i < ySmallMartSize; ++i) {
                    sharedEditor.remove("SmallMart_" + i);
                }

                //여기부터는 저장된 선호제품 지우기.
                String yTempGet = sharedPref.getString("ListSize1", "");
                sharedEditor.remove("ListSize1");
                Log.d("MyPageActivity", "선호 카운트1 : " + yTempGet);

                if (yTempGet.equals("")) {
                    Log.d("MyPageActivity", "1번에 저장된 선호제품이 없습니다. : " + yTempGet);
                } else {
                    int yGetListSize = Integer.parseInt(yTempGet);

                    for (int i = 0; i < yGetListSize; i++) {
                        sharedEditor.remove("List1_" + i);
                    }
                }

                yTempGet = sharedPref.getString("ListSize2", "");
                sharedEditor.remove("ListSize2");
                Log.d("MyPageActivity", "선호 카운트2 : " + yTempGet);

                if (yTempGet.equals("")) {
                    Log.d("MyPageActivity", "2번에 저장된 선호제품이 없습니다. : " + yTempGet);
                } else {
                    int yGetListSize = Integer.parseInt(yTempGet);

                    for (int i = 0; i < yGetListSize; i++) {
                        sharedEditor.remove("List2_" + i);
                    }
                }

                yTempGet = sharedPref.getString("ListSize3", "");
                sharedEditor.remove("ListSize3");
                Log.d("MyPageActivity", "선호 카운트3 : " + yTempGet);

                if (yTempGet.equals("")) {
                    Log.d("MyPageActivity", "3번에 저장된 선호제품이 없습니다. : " + yTempGet);
                } else {
                    int yGetListSize = Integer.parseInt(yTempGet);

                    for (int i = 0; i < yGetListSize; i++) {
                        sharedEditor.remove("List3_" + i);
                    }
                }

                //장바구니 초기화
                yBasketList = ShoppingHelper.getCart();
                yBasketList.clear();
                String haha = sharedPref.getString("martKey", "");

                BasketActivity.yCheckMartInit = true;

                sharedEditor.commit();
                yToast.showToast("모든 정보가 성공적으로 초기화됐습니다!", Toast.LENGTH_SHORT);
            }
        });
        dlg.show();
    }

    public void notifyClick(View v) {
        startActivity(new Intent(this, com.example.leehs.yottaproject06.ForSettings.NotifyActivity.class));
    }

    public void martSettings(View v) {
        Intent intent = new Intent(this, com.example.leehs.yottaproject06.ForSettings.MartOptionActivity.class);
        startActivity(intent);
    }

    public void menualClick(View v) {
        Intent intent = new Intent(this, MenualActivity.class);
        startActivity(intent);
    }

    public void mypageClick(View v) {
        startActivity(new Intent(this, FAQActivity.class));
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Toast.makeText(this, "한번 더 누르면 앱이 종료 됩니다.", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recycleView(findViewById(R.id.yMypageLayout));
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
