package com.example.leehs.yottaproject06;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity
{
    // 애플리케이션 전체에서 사용할 값
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor sharedEditor;

    TextView txtphone;
    EditText editemail;
    private static final String SERVER_ADDRESS = "http://220.68.231.91/test/sihyun_text2.php";
    JSONArray items;
    searchWeb task;
    String phoneNumber;
    String pn;

    ToastMessage yToast;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        sharedPref = getSharedPreferences("pref", Activity.MODE_PRIVATE);//폰 메모리에 저장
        sharedEditor = sharedPref.edit();

        yToast = new ToastMessage(JoinActivity.this);

        TelephonyManager tMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        phoneNumber = tMgr.getLine1Number();

        pn= phoneNumber.replace("+82","0");
        phoneNumber=pn;

        txtphone = (TextView) findViewById(R.id.txtphone);
        txtphone.setText(phoneNumber);

        editemail = (EditText) findViewById(R.id.txtemail);

        task = new searchWeb();
        task.execute(SERVER_ADDRESS);

        Log.d("MainActivity", "여기까지 오나2");
    }

    public void next(View v) {
        String phone = txtphone.getText().toString();
        String email = editemail.getText().toString();

        sharedEditor.putString("join" , "true");
        sharedEditor.commit();
        /*
        if(phone.isEmpty() == false && email.isEmpty()== false) {
            insertToDatabase(phone, email);

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext()," 번호와 이메일을 모두 입력해 주세요 ", Toast.LENGTH_LONG).show();
        }
        */

        //이메일 유효성 검사
        if(EmailCheck(email)){
            if(phone.isEmpty() == false && email.isEmpty()== false) {
                insertToDatabase(phone, email);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext()," 번호와 이메일을 모두 입력해 주세요 ", Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(getApplicationContext(),"올바른 형식이 아닙니다.", Toast.LENGTH_LONG).show();
        }
    }

    public static boolean EmailCheck(String email) {
        if (email==null) return false;
        boolean b = Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", email.trim());
        return b;
    }

    private void insertToDatabase(String phone,String email)
    {
        class InsertData extends AsyncTask<String, Void, String>
        {
            ProgressDialog loading;
            //RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                loading = ProgressDialog.show(JoinActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                //super.onPostExecute("http://220.68.231.128/insert.php");
                loading.dismiss();

                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                yToast.showToast(s, Toast.LENGTH_SHORT);
            }

            @Override
            protected String doInBackground(String... params)
            {

                try
                {
                    String phone = (String)params[0];
                    String email = (String)params[1];
                    //int price = Integer.parseInt(price_t);

                    String link="http://220.68.231.91/test/sihyun_text.php";
                    String data  = URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8");
                    data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");

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

                catch(Exception e)
                {
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        InsertData task = new InsertData();
        task.execute(phone, email);
    }

    private class searchWeb extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... urls)
        {
            StringBuilder jsonHtml = new StringBuilder();
            String phonenum = phoneNumber;
            try
            {
                URL url = new URL(urls[0] + "?phone="+phonenum);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                if(conn != null)
                {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK)
                    {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for(;;)
                        {
                            String line = br.readLine();
                            if(line == null) break;
                            jsonHtml.append(line + "\n");
                            Log.d("MainActivity", "여기까지 오나3");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }

            return jsonHtml.toString();
        }
        protected void onPostExecute(String str)
        {
            String n;
            try
            {
                JSONObject jsonObj = new JSONObject(str);
                items = jsonObj.getJSONArray("result");

                Log.d("MainActivity", "여기까지 오나");

                Log.d("MainActivity", "여기까지 오나dasdasd"+items.length());
                Log.d("MainActivity","핸드폰에서 받아온 번호 : " +phoneNumber);
                for (int i = 0; i < items.length(); ++i)
                {
                    Log.d("MainActivity","여기까지 오나4");

                    JSONObject jo = items.getJSONObject(i);
                    n = jo.getString("phone");
                    Log.d("MainActivity","서버에서 받아온 번호 : " +n);
                    Log.d("MainActivity","핸드폰에서 받아온 번호 : " +phoneNumber);

                    if(phoneNumber.equals(n))
                    {
                        Log.d("MainActivity","여기까지 오나5");
                        sharedEditor.putString("join" , "true");
                        sharedEditor.commit();
                        startActivity(new Intent(JoinActivity.this, MainActivity.class));
                        finish();
                    }
                }
            }
            catch (JSONException je)
            {
                je.printStackTrace();
            }
        }
    }

}