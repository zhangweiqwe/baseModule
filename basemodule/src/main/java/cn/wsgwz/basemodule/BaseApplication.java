package cn.wsgwz.basemodule;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;

import androidx.core.app.NotificationCompat;
import androidx.multidex.MultiDex;

import cn.wsgwz.basemodule.dagger.DaggerBaseAppComponent;
import cn.wsgwz.basemodule.utilities.CrashHandler;
import cn.wsgwz.basemodule.utilities.LLog;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class BaseApplication extends Application  implements HasAndroidInjector {

    private static final String TAG = BaseApplication.class.getSimpleName();

    private static BaseApplication mInstance = null;

    private static Thread mUiThread;
    private static Handler mMainHandler;
    private SharedPreferences mPref;

    private static final List<Activity> activities = new ArrayList<>();

    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;
    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }

    public static void runOnUiThread(Runnable action) {
        if (Thread.currentThread() != mUiThread) {
            mMainHandler.post(action);
        } else {
            action.run();
        }
    }

    public void login() {

    }

    public static SharedPreferences getPreferences() {
        return mInstance.mPref;
    }


    public static final void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static final boolean removeActivity(Activity activity) {
        return activities.remove(activity);
    }

    public static final List<Activity> getActivities() {
        return activities;
    }

    public String getDefaultBaseUrl() {
        return getResources().getStringArray(R.array.base_url_entryValues)[0];
    }

    public final String getBaseUrl() {
        return getPreferences().getString("base_url", getDefaultBaseUrl());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    public void onCreate() {
        super.onCreate();

        LLog.d(TAG, getCurrentProcessName());
        DaggerBaseAppComponent.create()
                .inject(this);
        mInstance = this;
        mUiThread = Thread.currentThread();
        mMainHandler = new Handler();
        mPref = PreferenceManager.getDefaultSharedPreferences(this);

        CrashHandler.getInstance().init(this);



        if (getCurrentProcessName().equals(getPackageName())) {
            initNotificationChannel();
            if (BuildConfig.DEBUG) {
                initDebugNotification();
            }
        } else {

        }


    }

    protected String getCurrentProcessName() {
        int pid = android.os.Process.myPid();
        String processName = null;
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
            if (process.pid == pid) {
                processName = process.processName;
            }
        }
        return processName;
    }


    private void initDebugNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, BaseConst.NOTIFICATION_CHANNEL_ID);
        Intent intent = new Intent(this, TestToolSettingActivity.class);//点击之后进入MainActivity
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = builder.setSmallIcon(R.drawable.ic_adb_black_24dp)//设置小图标
                .setTicker(getString(R.string.click_to_enter_the_test_tool))//设置文字
                .setContentTitle(getString(R.string.click_to_enter_the_test_tool))
                .setWhen(System.currentTimeMillis())//通知的时间
                .setAutoCancel(false)//点击后消失
                .setOngoing(true)
                .setContentIntent(pendingIntent)//设置意图
                .build();//创建通知对象完成
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notification.hashCode(), notification);//显示通知
    }

    private void initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) mInstance.getSystemService(Context.NOTIFICATION_SERVICE);
            // 用户可以看到的通知渠道的名字.
            CharSequence name = getPackageName();
            // 用户可以看到的通知渠道的描述
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(BaseConst.NOTIFICATION_CHANNEL_ID, name, importance);
            // 配置通知渠道的属性
            //mChannel.setDescription(description);
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            // 设置通知出现时的震动（如果 android 设备支持的话）
            mChannel.enableVibration(true);
            //mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            //最后在notificationmanager中创建该通知渠道
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }


}
