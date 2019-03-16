package com.example.leehs.yottaproject06.AboutMaps;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Log;


/**
 * Created by User on 2016-01-07.
 */

///// GPS를 통해 사용자 위치 파악 (Background 동작)
public class GPSInfo extends Service implements LocationListener {


    // 애플리케이션 전체에서 사용할 값
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor sharedEditor;

    private final Context mContext;

    // 현재 GPS 사용유무
    boolean isGPSEnabled = false;
    // 네트워크 사용유무
    boolean isNetworkEnabled = false;
    // GPS 상태값
    boolean isGetLocation = false;

    protected LocationManager locationManager;
    Location location;
    double lat; // 위도
    double lon; // 경도

    // 최소 GPS 정보 업데이트 거리 10미터
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

    // 최소 GPS 정보 업데이트 시간
    private static final long MIN_TIME_BW_UPDATES = 100 * 60 * 1;


    public GPSInfo(Context context) {
        Log.d("***GPSInfo", "@@@WelCome GPSInfo!");

        this.mContext = context;
        getLocation();
        Log.d("***GPSInfo", "@@@Bye GPSInfo!");

    }

    ////// 위치 정보 파악 메서드(+GPS, Network 사용 유무 파악)
    public Location getLocation() {
        Log.d("getLocation", "getLocation 시작");

        NetworkStatus networkStatus = new NetworkStatus();

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("getLocation", "getLocation if문에서 걸림");

            return null;
        }
        try {
            Log.d("getLocation", "getLocation try 들어갔고");

            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // GPS 정보 가져오기
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            Log.d("GPSInfo", "GPS 가능여부 : " + isGPSEnabled);
            isNetworkEnabled = networkStatus.getNetworkStatus(mContext);
            Log.d("GPSInfo", "Network 가능여부 : " + isNetworkEnabled);

            if (isNetworkEnabled == true && isGPSEnabled == false) {
                showSettingsAlert();
                this.isGPSEnabled = false;
                this.isNetworkEnabled = true;
                this.isGetLocation = true;
                Log.d("GPSInfo뭐냐아", "isGetLocation");
            } else if (!isNetworkEnabled && !isGPSEnabled) {
                Log.d("GPSInfo netcheck", "isGetLocation: " + isGetLocation);
                showNetSettingsAlert();
                this.isGetLocation = false;
                this.isGPSEnabled = false;
                this.isNetworkEnabled = false;
            } else if (isGPSEnabled == true && !isNetworkEnabled) {
                showNetSettingsAlert();
                // GPS 와 네트워크사용이 가능하지 않을때 소스 구현
                if (locationManager != null) {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        // 위도 경도 저장
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                    }
                }
                this.isGetLocation = false;
                this.isGPSEnabled = true;
                this.isNetworkEnabled = false;
            } else if (isGPSEnabled == true && isNetworkEnabled == true) {
                this.isGetLocation = true;
                this.isGPSEnabled = true;
                this.isNetworkEnabled = true;
                // 네트워크 정보로 부터 위치값 가져오기
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            // 위도 경도 저장
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    }
                }


                // 현재 네트워크 상태 값 알아오기
                //            isNetworkEnabled = locationManager
                //                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);


//                if (isGPSEnabled)
//                {
//                    locationManager.requestLocationUpdates ( LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this );
//                    if (locationManager != null) {
//                        location = locationManager.getLastKnownLocation ( LocationManager.GPS_PROVIDER );
//                        if (location != null) {
//                            lat = location.getLatitude ();
//                            lon = location.getLongitude ();
//                        }
//                    }
//                }
//                else if(!isGPSEnabled)
//                {
//                    lat=  Double.parseDouble ( sharedPref.getString ( "Latitude_", "" ));
//                    lon= Double.parseDouble ( sharedPref.getString ( "Longitude_", ""));
//                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("getLocation", "getLocation 끝");

        return location;
    }

    /**
     * GPS 종료
     */
    public void stopUsingGPS() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (locationManager != null) {
            locationManager.removeUpdates(GPSInfo.this);
        }
    }

    /**
     * 위도값을 가져옵니다.
     */
    public double getLatitude() {
        if (location != null) {
            lat = location.getLatitude();
        }
        return lat;
    }

    /**
     * 경도값을 가져옵니다.
     */
    public double getLongitude() {
        if (location != null) {
            lon = location.getLongitude();
        }
        return lon;
    }

    /**
     * GPS 나 wife 정보가 켜져있는지 확인합니다.
     */
    public boolean isGetLocation() {
        return this.isGetLocation;
    }

    public boolean isGPSEnabled() {
        return this.isGPSEnabled;
    }

    public boolean isNetworkEnabled() {
        return this.isNetworkEnabled;
    }

    public void showNetSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        Log.d("GPSInfo alert", "isGetLocation: " + isGetLocation);

        alertDialog.setTitle("Network 사용유무셋팅");
        alertDialog.setMessage("Network 셋팅이 되지 않았을수도 있습니다. 설정창으로 가시겠습니까?");

        // OK 를 누르게 되면 설정창으로 이동합니다.
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                        mContext.startActivity(intent);
                    }
                });
        // Cancle 하면 종료 합니다.
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    /**
     * GPS 정보를 가져오지 못했을때
     * 설정값으로 갈지 물어보는 alert 창
     */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        alertDialog.setTitle("GPS 사용유무셋팅");
        alertDialog.setMessage("GPS 셋팅이 되지 않았을수도 있습니다. 설정창으로 가시겠습니까?");

        // OK 를 누르게 되면 설정창으로 이동합니다.
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(intent);
                    }
                });
        // Cancle 하면 종료 합니다.
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub

    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }
}