package com.example.leehs.yottaproject06.InsertDataToServer;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import com.example.leehs.yottaproject06.ShoppingCart.BasketActivity;
import com.example.leehs.yottaproject06.ToastMessage;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by MyCom on 2016-01-11.
 */

/*
    불필요하게 코드가 중복되는 것을 방지하기 위해
    데이터를 서버로 전송할때 사용하는 AsyncTask를 InsertDataToServer클래스로 따로 뺐음.
    데이터를 서버로 전송하고 싶을때는 이 클래스를 인스턴스화해서 사용하면 됨.
 */
public class InsertDataToServer {

    public static final String UPLOAD_URL = "http://220.68.231.118/wook/upimage.php";
    public static final String UPLOAD_KEY = "image";
    public static final String UPLOAD_KEY_PNAME = "pname";

    public boolean ySetResult = false;

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void uploadData(final IncludeData yInclude){
        class UploadImage extends AsyncTask<Bitmap,Void,String>{

            //ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(getApplicationContext(), "Uploading...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

                /*
                    두 개의 AsyncTask를 사용하기 때문에 스레드가 꼬이는 것을 방지하기 위해서 메인스레드 -> A스레드 -> B스레드 순으로 동작시킵니다.
                    때문에 UploadImage의 내부에서 InsertData를 동작시킵니다.
                 */
                if( s.equals("이미지 업로드에 성공하였습니다!")){
                    insertToDatabase(yInclude.GetName(), yInclude.GetPrice(), yInclude.GetDepart(), yInclude.GetCategory(),yInclude.GetPhone());
                }
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();
                data.put(UPLOAD_KEY, uploadImage);
                data.put(UPLOAD_KEY_PNAME, yInclude.GetName());

                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }
        }
        ySetResult = false;

        UploadImage ui = new UploadImage();
        ui.execute(yInclude.GetImage());
    }


    public static String getDateString()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 0);
        String day = sdf.format(c.getTime());

        return day;
    }

    public void insertToDatabase(String name, String price ,String department,String category,String phone){
        /*
        InsertData는 서버로 텍스트 정보를 전송하는 AysncTask입니다.
         */
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            //RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(InsertActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //super.onPostExecute("http://220.68.231.128/insert.php");
                //loading.dismiss();

                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

                if(s.equals("success")){
                    //ySetResult = true;
                    if(ExistingInsertActivity.yExistCheck)
                        ExistingInsertActivity.SuccessUpload();
                    else
                        InsertActivity.SuccessUpload();
                }else{
                    if(ExistingInsertActivity.yExistCheck)
                        ExistingInsertActivity.FailUpload();
                    else
                        InsertActivity.FailUpload();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                try{
                    String name = (String)params[0];
                    String price = (String)params[1];
                    String department = (String)params[2];
                    String date = getDateString();
                    String category = (String)params[3];
                    String phone =(String)params[4];

                    /*
                    Log.d("InsertDataToServer","===========================");
                    Log.d("InsertDataToServer","name : " + name);
                    Log.d("InsertDataToServer","price : " + price);
                    Log.d("InsertDataToServer","department : " + department);
                    Log.d("InsertDataToServer","date : " + date);
                    Log.d("InsertDataToServer","category : " + category);
                    Log.d("InsertDataToServer","===========================");
                    */

                    String link="http://220.68.231.118/wook/uptext.php";
                    String data  = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                    data += "&" + URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode(price, "UTF-8");
                    data += "&" + URLEncoder.encode("department", "UTF-8") + "=" + URLEncoder.encode(department, "UTF-8");
                    data += "&" + URLEncoder.encode("goods_date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8");
                    data += "&" + URLEncoder.encode("category", "UTF-8") + "=" + URLEncoder.encode(category, "UTF-8");
                    data += "&" + URLEncoder.encode("PhoneNumber", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write( data );
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                }
                catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        InsertData task = new InsertData();
        task.execute(name, price, department, category,phone);
    }
}