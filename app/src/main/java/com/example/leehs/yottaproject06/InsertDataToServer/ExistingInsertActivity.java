package com.example.leehs.yottaproject06.InsertDataToServer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leehs.yottaproject06.ListItem;
import com.example.leehs.yottaproject06.R;
import com.example.leehs.yottaproject06.ToastMessage;

/**
 * Created by MyCom on 2016-01-07.
 */

/*이 액티비티는 기존에 존재하는 가격정보에 대해서 가격정보를 입력합니다.
  때문에 사용자가 추가로 이미지, 제품명을 입력하지 않고 (기존에 있던 정보를 받아옵니다)
  가격정보만 입력하게 됩니다. 이때 기존에 존재하는 가격정보면 DB에 새롭게 추가하지 않고
  count만 증가시킵니다.
  */
public class ExistingInsertActivity extends Activity {
    public static final String UPLOAD_URL = "http://220.68.231.118/wook/getimage.php";
    public static final String UPLOAD_KEY = "image";

    LinearLayout yInsertExistingLayout;

    ListItem listItem;
    String inputPhone;
    Bitmap bitImage;
    ImageView imgGoodsView;
    TextView textGoodsName;

    EditText editPrice;
    String price;

    InsertDataToServer yInsertData;
    IncludeData yInclude;

    static ToastMessage yToast;
    static boolean yExistCheck; //static과 마지막 타협입니다.. ㅠ (LHS)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_exist);

        //다이얼로그를 띄우고 밖을 클릭했을 시 꺼지는 것을 방지함.
        this.setFinishOnTouchOutside(false);

        yInsertExistingLayout = (LinearLayout)findViewById(R.id.yInsertExistingLayout);
        yInsertExistingLayout.setBackgroundDrawable(new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.register_text)));

        yInsertData = new InsertDataToServer();

        String pn;
        TelephonyManager tMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        inputPhone = tMgr.getLine1Number();

        pn= inputPhone.replace("+82","0");
        inputPhone=pn;

        Intent intent = getIntent();
        listItem = (ListItem)intent.getSerializableExtra("TEXT");
        bitImage = (Bitmap)intent.getParcelableExtra("IMAGE");

        imgGoodsView = (ImageView)findViewById(R.id.imgGoodsView);
        textGoodsName = (TextView)findViewById(R.id.textGoodsName);
        editPrice = (EditText)findViewById(R.id.editPrice);

        imgGoodsView.setImageBitmap(bitImage);
        textGoodsName.setText(listItem.getData(0));
        editPrice.setText(listItem.getData(1));

        yToast = new ToastMessage(ExistingInsertActivity.this);
        yExistCheck = false;
    }

    /*
    종료 버튼입니다.
     */
    public void btnFinishOnClick(View v){
        finish();
    }

    /*
     가격정보를 입력하고 ok버튼을 누르면 가격정보가 올라가게 됩니다.
     */
    public void btnInertOnClick(View v){
        price = editPrice.getText().toString();

        if(price.isEmpty()){
            Toast.makeText(this,"가격을 입력해주세요.",Toast.LENGTH_SHORT).show();
        }
        else {
            yInclude = new IncludeData(bitImage, listItem.getData(0), price, listItem.getData(3), listItem.getData(6),inputPhone);
            yExistCheck = true;
            yInsertData.uploadData(yInclude);
            //insertToDatabase(listItem.getData(0), price, "1111", "산본 이마트");

            //Toast.makeText(getApplicationContext(), "업로드에 성공하였습니다!",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public static void SuccessUpload(){
        //Toast.makeText(getApplicationContext(), "업로드에 성공하였습니다!", Toast.LENGTH_SHORT).show();
        //ToastMessage yToastForOut = new ToastMessage(InsertActivity.this);
        if(yToast != null)
            yToast.showToast("업로드에 성공하였습니다!", Toast.LENGTH_SHORT);
    }
    public static void FailUpload(){
        if(yToast != null)
            yToast.showToast("업로드에 실패하였습니다!\n다시 시도해주세요.",Toast.LENGTH_SHORT);
    }

    /*
        이건 추후에 토의해서 여기에 이상한 이미지가 들어가 있으면
        클릭해서 수정할 수 있또록 만들어보자.
     */
    public void ChangeImageOnClick(View v){
        Toast.makeText(getApplicationContext(), "나중에 여기 클릭하면 이미지 수정할 수 있도록",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recycleView(findViewById(R.id.yInsertExistingLayout));
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