package com.example.shoppingonline.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

public class CheckConnection {

    public static boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                haveConnectedWifi=true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                haveConnectedMobile=true;
            }
        } else {
            // not connected to the internet
        }
            return haveConnectedWifi || haveConnectedMobile;
    }
    public static void showToast(Context context,String thongBao){
        Toast.makeText(context,thongBao,Toast.LENGTH_SHORT).show();
    }
}
