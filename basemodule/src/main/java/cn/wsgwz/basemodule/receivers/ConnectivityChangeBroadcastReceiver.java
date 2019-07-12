package cn.wsgwz.basemodule.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.wsgwz.basemodule.utilities.LLog;
import cn.wsgwz.basemodule.utilities.manager.ConnectivityChangeListenerManager;


public class ConnectivityChangeBroadcastReceiver extends BroadcastReceiver {

    public static final String TAG = "ConnectivityChangeBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {


        LLog.d(TAG,"onReceive");


        ConnectivityChangeListenerManager.getInstance().notifyConnectivityChange();



       /* ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            Toast.makeText(context, "当前网络可用"+Util.isMeteredNetwork(context), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "当前网络不可用", Toast.LENGTH_SHORT).show();
        }*/

       /* ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            switch (networkInfo.getType()) {
                case ConnectivityManager.TYPE_MOBILE:
                    Toast.makeText(context, "正在使用2G/3G/4G网络", Toast.LENGTH_SHORT).show();
                    break;
                case ConnectivityManager.TYPE_WIFI:
                    Toast.makeText(context, "正在使用wifi上网", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        } else {
            Toast.makeText(context, "当前无网络连接", Toast.LENGTH_SHORT).show();
        }*/
    }
}
