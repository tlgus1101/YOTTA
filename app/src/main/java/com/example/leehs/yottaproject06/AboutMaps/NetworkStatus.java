package com.example.leehs.yottaproject06.AboutMaps;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Administrator on 2016-02-03.
 */
public class NetworkStatus {

    public boolean getNetworkStatus(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo lte_4g = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);

        boolean blte_4g = false;
        if(lte_4g != null){
            blte_4g = lte_4g.isConnected();
        }
        if(mobile != null){
            if(mobile.isConnected() || wifi.isConnected() || blte_4g){
                return true;
            }else{
                if(wifi.isConnected() || blte_4g){
                    return true;
                }
            }
        }
        Log.d("getWifiStatus", "" + blte_4g);
        return false;
    }
}
