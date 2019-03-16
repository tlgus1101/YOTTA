package com.example.leehs.yottaproject06.InsertDataToServer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leehs.yottaproject06.AboutCategoryAndFavor.CategoryDialogActivity;
import com.example.leehs.yottaproject06.AboutMaps.GPSInfo;
import com.example.leehs.yottaproject06.R;
import com.example.leehs.yottaproject06.ToastMessage;

import java.io.IOException;
import java.util.Set;

/**
 * Created by Lee HS on 2015-12-25.
 */
public class InsertActivity extends Activity {

    //int yGetPicker;

    LinearLayout yInsertLayout;

    double latitude = 0;
    double longitude = 0;
    int radius = 3000;
    int page = 1;
    public GPSInfo gps;
    boolean checkGps;

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

    //////////////////////////////////////////////////////////////////////////////////////////////

    //과일/채소/견과 -> 1~10번대(FRUITS)
    public static final int CATEGORY_FRUITS_FRUIT = 1; //과일
    public static final int CATEGORY_FRUITS_RICE = 2; //쌀/잡곡
    public static final int CATEGORY_FRUITS_NUTS = 3; //견과류
    public static final int CATEGORY_FRUITS_VEGETABLE = 4; //채소

    //가공식품 -> 11~20번대(PROCESSED)
    public static final int CATEGORY_PROCESSED_RAMYUN = 11;
    public static final int CATEGORY_PROCESSED_SNACK = 12;
    public static final int CATEGORY_PROCESSED_INSTANTnCAN = 13;
    public static final int CATEGORY_PROCESSED_SIDE = 14;
    public static final int CATEGORY_PROCESSED_SEASONING = 15;
    public static final int CATEGORY_PROCESSED_BREAD = 16;

    //냉장/냉동식품 -> 21~30번대(FROZEN)
    public static final int CATEGORY_FROZEN_WATERS = 21;
    public static final int CATEGORY_FROZEN_DRINKS = 22;
    public static final int CATEGORY_FROZEN_FROZENPRODUCT = 23;

    //유제품 -> 31~40번대(DAIRY)
    public static final int CATEGORY_DAIRY_MILK = 31;
    public static final int CATEGORY_DAIRY_YOGURT = 32;
    public static final int CATEGORY_DAIRY_ICECREAM = 33;
    public static final int CATEGORY_DAIRY_CHEESE = 34;

    //해산물 -> 41~50번대(SEEFOOD)
    public static final int CATEGORY_SEEFOOD_FISHES = 41;
    public static final int CATEGORY_SEEFOOD_CRUSTACEA = 42;
    public static final int CATEGORY_SEEFOOD_VACUUMPACKING = 43;

    //정육/계란 -> 51~60번대(FRESHES)
    public static final int CATEGORY_FRESHES_DOMESTIC = 51;
    public static final int CATEGORY_FRESHES_IMPORT = 52;
    public static final int CATEGORY_FRESHES_EGGS = 53;

    /////////////////////////////////////////////////////////////////////////////////////////////

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_GALLERY = 2;

    private static final int PICK_IMAGE_REQUEST = 3;

    public static int yGetCategory = -1;
    public int ySetCategory = -1;

    private ImageView imgview;
    private Bitmap resized;

    private EditText editName, ea, editPrice;
    private Button buttonCamera, buttonGallery, btnCategroy;
    private String inputName, inputPrice, inputDepart, inputPhone;
    private TextView yMeasureText; //단위를 변경하기 위한 변수.

    private Bitmap bitmap;
    private Uri filePath;

    private Button btnOK;
    private Button btnSelect;

    InsertDataToServer yInsertData;
    IncludeData yInclude;

    static ToastMessage yToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_main);

        //다이얼로그를 띄우고 밖을 클릭했을 시 꺼지는 것을 방지함.
        this.setFinishOnTouchOutside(false);

        String pn;

        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        inputPhone = tMgr.getLine1Number();
        //inputPhone = "01036744084";
        pn= inputPhone.replace("+82", "0");
         inputPhone=pn;


        yInsertLayout = (LinearLayout) findViewById(R.id.yInsertLayout);
        yInsertLayout.setBackgroundDrawable(new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.register_text)));

        editName = (EditText) findViewById(R.id.text_name);
        ea = (EditText) findViewById(R.id.text_ea);
        editPrice = (EditText) findViewById(R.id.text_price);

        ea.setText("1");
        imgview = (ImageView) findViewById(R.id.imageView1);

        buttonCamera = (Button) findViewById(R.id.btn_take_camera);
        buttonGallery = (Button) findViewById(R.id.btn_select_gallery);

        btnOK = (Button) findViewById(R.id.button);
        btnSelect = (Button) findViewById(R.id.btnselect);
        btnCategroy = (Button) findViewById(R.id.btnCategroy);

        yMeasureText = (TextView) findViewById(R.id.yMeasureText);
        yInsertData = new InsertDataToServer();

        // setupUI();

        Intent intent = getIntent();
        String name = intent.getStringExtra("product");
        editName.setText(name);
        inputDepart = intent.getStringExtra("mart");

        yToast = new ToastMessage(InsertActivity.this);
    }

    public void BtnCategoryOnClick(View v) {
        Intent intent = new Intent(this, CategoryDialogActivity.class);
        startActivityForResult(intent, 0);
    }

    /*
    + 버튼을 눌러서 이미지를 불러올 방식을 선택하게끔 다이얼로그를 띄워줍니다.
    선택의 종류에는 갤러리에서 불러오기와 카메라로 찍기가 있습니다.
     */
    public void btnSelectOnClick(View v) {
        final String items[] = {"갤러리", "카메라"};
        AlertDialog.Builder dlg = new AlertDialog.Builder(InsertActivity.this);
        dlg.setTitle("이미지 선택");
        dlg.setIcon(R.mipmap.logo);
        dlg.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: //갤러리 선택
                        showFileChooser();
                        break;
                    case 1: //카메라 선택
                         // 카메라 호출
                        try {
                            Log.d("InsertActivity", "삼성전용");
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.setComponent(new ComponentName("com.sec.android.app.camera", "com.sec.android.app.camera.Camera"));
                            startActivityForResult(intent, PICK_FROM_CAMERA);
                        } catch (RuntimeException e) {
                            Log.d("InsertActivity", "엘지");
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.setComponent(new ComponentName("com.lge.camera", "com.lge.camera.CameraAppLauncher"));
                            startActivityForResult(intent, PICK_FROM_CAMERA);
                        } catch (Exception e) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_FROM_CAMERA);
                        }
                }
                dialog.cancel();
            }
        });
        dlg.show();
    }


    public void btnInsetOnClick(View view) {
        inputName = editName.getText().toString();
        inputName = inputName + " " + ea.getText().toString() + yMeasureText.getText();
        inputPrice = editPrice.getText().toString();

        BitmapDrawable bitmaptest;

        if (inputName.isEmpty() || inputPrice.isEmpty()) { //|| distance.isEmpty() || depart.isEmpty() )
            //Toast.makeText(getApplicationContext(), "정보를 정확히 입력해주세요!", Toast.LENGTH_LONG).show();
            yToast.showToast("정보를 정확히 입력해주세요!",Toast.LENGTH_SHORT);
        } else if (ySetCategory == -1) {
            //Toast.makeText(getApplicationContext(), "카테고리를 선택해주세요!", Toast.LENGTH_LONG).show();
            yToast.showToast("카테고리를 선택해주세요!",Toast.LENGTH_SHORT);
        } else {
            //resized = null;

            if (btnSelect.getText().equals(" ")) {
                Log.d("InsertActivity", "이미지가 없어서 노이미지로 대체함.");
                bitmaptest = (BitmapDrawable) getResources().getDrawable(R.drawable.no);
                resized = Bitmap.createScaledBitmap(bitmaptest.getBitmap(), 400, 300, true);
            }
            String category = "" + ySetCategory;
            yInclude = new IncludeData(resized, inputName, inputPrice, inputDepart, category, inputPhone);
            yInsertData.uploadData(yInclude);

            ySetCategory = -1; //초기화
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

    // 갤러리에서 이미지를 불러올 경우
    public void btnGalleryOnclick(View v) {
        showFileChooser();
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    /*
    카메라 및 갤러리에서 이미지를 선택하면 요청코드를 보내는데
    그 요청코드를 받아서 각각 요청받은 코드에 대해서 맞는 작업을 수행합니다.
     */
    Object url;
    boolean urlTF = true;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("InsertActivity","카메라@@@@");
        Log.d("InsertActivity","requestCode :: "+ requestCode);
        Log.d("InsertActivity","resultCode :: "+ resultCode);
        Log.d("InsertActivity","data :: "+ data);

        try {
            if (data != null) {
                Bundle extras = data.getExtras();

                if (extras != null) {
                    Set keys = extras.keySet();

                    for (String _key : extras.keySet()) {
                        url = extras.get(_key);
                        Camera(requestCode, resultCode, data);
//                        Intent intent = new Intent();
//                        intent.setType("image/*");
//                        intent.setAction((String)extras.get(_key));
//                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_FROM_CAMERA);
                    }
                } else if (extras == null) {
                    urlTF = false;
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    Camera(requestCode, resultCode, data);

                }
            }

        } catch (Exception e) {
            Log.d("InsertActivity", "e" + e);
        }
    }

    public void Camera(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        //갤러리 전송일 경우,
        if (requestCode == PICK_IMAGE_REQUEST || requestCode == PICK_FROM_CAMERA /*&& resultCode == RESULT_OK && data != null && data.getData() != null*/) {

            btnSelect.setVisibility(View.GONE);
            btnSelect.setText("");
            if (data.getData() != null) {
                filePath = data.getData();
            } else if (data.getData() == null) {
                filePath = (Uri) url;
            }
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //이미지가 너무 클 경우, 인코딩을 해줌.
                //Log.d("InsertActivity", "으헝헝ㅎ : " + bitmap.getWidth());
                //Log.d("InsertActivity", "으핳ㅇ하 : " + bitmap.getRowBytes());
                if (bitmap.getWidth() > 400 || bitmap.getRowBytes() > 1200 ) {
                    Log.d("HEIGHT", "" + bitmap.getWidth());
                    Log.d("SIZE", "" + bitmap.getRowBytes());
                    resized = Bitmap.createScaledBitmap(bitmap, 400, 300, true);
                } else {
                    resized = bitmap;
                }

                imgview.setImageBitmap(resized);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void ReImageOnClick(View v){
        btnSelectOnClick(getCurrentFocus());
    }

    public void btnFinish(View v) {
        finish();
    }

    //버튼을 클릭해서 본인이 원하는 단위를 입력할 수 있도록 만듬.
    public void MeasureOnClick(View v) {
        int ySelect = 0;
        String yForSelect = "";
        final String items[] = {"개입", "마리", "봉", "통", "단", "mL", "g"};
        AlertDialog.Builder dlg = new AlertDialog.Builder(InsertActivity.this);
        dlg.setTitle("단위선택");
        dlg.setIcon(R.mipmap.logo);
        yForSelect = yMeasureText.getText().toString();
        switch (yForSelect) {
            case "개입":
                ySelect = 0;
                break;
            case "마리":
                ySelect = 1;
                break;
            case "봉":
                ySelect = 2;
                break;
            case "통":
                ySelect = 3;
                break;
            case "단":
                ySelect = 4;
                break;
            case "mL":
                ySelect = 5;
                break;
            case "g":
                ySelect = 6;
                break;
        }
        dlg.setSingleChoiceItems(items, ySelect, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: //개입
                        yMeasureText.setText("개입");
                        break;
                    case 1: //마리
                        yMeasureText.setText("마리");
                        break;
                    case 2: //봉
                        yMeasureText.setText("봉");
                        break;
                    case 3: //통
                        yMeasureText.setText("통");
                        break;
                    case 4: //단
                        yMeasureText.setText("단");
                        break;
                    case 5: //mL
                        yMeasureText.setText("mL");
                        break;
                    case 6: //g
                        yMeasureText.setText("g");
                        break;
                }
                dialog.cancel();
            }
        });
        dlg.show();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (yGetCategory != -1) {

            switch (yGetCategory) {
                //과일/채소/견과 -> 1~10번대(FRUITS)
                case CATEGORY_FRUITS_FRUIT: //과일
                    btnCategroy.setText("과일");
                    ySetCategory = yGetCategory;
                    yMeasureText.setText("g");
                    break;
                case CATEGORY_FRUITS_RICE: //쌀/잡곡
                    btnCategroy.setText("쌀/잡곡");
                    ySetCategory = yGetCategory;
                    yMeasureText.setText("g");
                    break;
                case CATEGORY_FRUITS_NUTS: //견과류
                    btnCategroy.setText("건과일,견과류");
                    ySetCategory = yGetCategory;
                    yMeasureText.setText("g");
                    break;
                case CATEGORY_FRUITS_VEGETABLE: //채소
                    btnCategroy.setText("채소");
                    ySetCategory = yGetCategory;
                    yMeasureText.setText("g");
                    break;

                //가공식품 -> 11~20번대(PROCESSED)
                case CATEGORY_PROCESSED_RAMYUN: //라면
                    btnCategroy.setText("라면");
                    ySetCategory = yGetCategory;
                    yMeasureText.setText("개입");
                    break;
                case CATEGORY_PROCESSED_SNACK: //과자/껌/캔디류
                    btnCategroy.setText("과자/껌/캔디류");
                    ySetCategory = yGetCategory;
                    yMeasureText.setText("개입");
                    break;
                case CATEGORY_PROCESSED_INSTANTnCAN: //즉석식품/통조림
                    btnCategroy.setText("즉석식품/통조림");
                    ySetCategory = yGetCategory;
                    yMeasureText.setText("개입");
                    break;
                case CATEGORY_PROCESSED_SIDE: //김치/반찬
                    btnCategroy.setText("김치/반찬");
                    yMeasureText.setText("g");
                    ySetCategory = yGetCategory;
                    break;
                case CATEGORY_PROCESSED_SEASONING: //조미료/소스
                    btnCategroy.setText("조미료/소스");
                    yMeasureText.setText("g");
                    ySetCategory = yGetCategory;
                    break;
                case CATEGORY_PROCESSED_BREAD: //빵류(잼,크림)
                    btnCategroy.setText("빵류(잼,크림)");
                    ySetCategory = yGetCategory;
                    yMeasureText.setText("개입");
                    break;

                //냉장/냉동식품 -> 21~30번대(FROZEN)
                case CATEGORY_FROZEN_WATERS: //물/음료
                    btnCategroy.setText("물/음료");
                    ySetCategory = yGetCategory;
                    yMeasureText.setText("mL");
                    break;
                case CATEGORY_FROZEN_DRINKS: //주류
                    btnCategroy.setText("주류");
                    ySetCategory = yGetCategory;
                    yMeasureText.setText("mL");
                    break;
                case CATEGORY_FROZEN_FROZENPRODUCT: //냉동식품
                    btnCategroy.setText("냉동식품");
                    ySetCategory = yGetCategory;
                    yMeasureText.setText("g");
                    break;

                //유제품 -> 31~40번대(DAIRY)
                case CATEGORY_DAIRY_MILK: //우유
                    btnCategroy.setText("우유");
                    ySetCategory = yGetCategory;
                    yMeasureText.setText("mL");
                    break;
                case CATEGORY_DAIRY_YOGURT: //요구르트
                    btnCategroy.setText("요구르트");
                    ySetCategory = yGetCategory;
                    yMeasureText.setText("g");
                    break;
                case CATEGORY_DAIRY_ICECREAM: //아이스크림
                    btnCategroy.setText("아이스크림");
                    ySetCategory = yGetCategory;
                    yMeasureText.setText("개입");
                    break;
                case CATEGORY_DAIRY_CHEESE: //치즈
                    btnCategroy.setText("치즈");
                    ySetCategory = yGetCategory;
                    yMeasureText.setText("g");
                    break;

                //해산물 -> 41~50번대(SEEFOOD)
                case CATEGORY_SEEFOOD_FISHES: //생선/회
                    btnCategroy.setText("생선/회");
                    ySetCategory = yGetCategory;
                    yMeasureText.setText("마리");
                    break;
                case CATEGORY_SEEFOOD_CRUSTACEA: //갑각류
                    btnCategroy.setText("갑각류");
                    ySetCategory = yGetCategory;
                    yMeasureText.setText("g");
                    break;
                case CATEGORY_SEEFOOD_VACUUMPACKING: //진공포장 제품
                    btnCategroy.setText("진공포장 제품");
                    ySetCategory = yGetCategory;
                    yMeasureText.setText("g");
                    break;

                //정육/계란 -> 51~60번대(FRESHES)
                case CATEGORY_FRESHES_DOMESTIC: //국산
                    btnCategroy.setText("국산");
                    ySetCategory = yGetCategory;
                    yMeasureText.setText("g");
                    break;
                case CATEGORY_FRESHES_IMPORT: //수입산
                    btnCategroy.setText("수입산");
                    ySetCategory = yGetCategory;
                    yMeasureText.setText("g");
                    break;
                case CATEGORY_FRESHES_EGGS: //계란류
                    btnCategroy.setText("계란류");
                    ySetCategory = yGetCategory;
                    yMeasureText.setText("개입");
                    break;
            }
            yGetCategory = -1;//초기화
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recycleView(findViewById(R.id.yInsertLayout));
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