package cn.wsgwz.basemodule.dagger;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;

import javax.inject.Inject;
import javax.inject.Named;

import cn.wsgwz.basemodule.BaseActivity;
import cn.wsgwz.basemodule.BaseApplication;
import cn.wsgwz.basemodule.utilities.manager.UserManager;
import dagger.Module;
import dagger.Provides;

@Module
public class BaseCommonModule {


    /*private BaseActivity baseActivity;


    @Inject
    public BaseCommonModule(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }*/

    @Provides
    UserManager provideUserManager() {
        return UserManager.getInstance();
    }

    /*@Provides
    ConnectivityManager provideConnectivityManager(){
        return (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Provides
    NetworkRequest provideNetworkRequest(){
        return new NetworkRequest.Builder().build();
    }*/
}
