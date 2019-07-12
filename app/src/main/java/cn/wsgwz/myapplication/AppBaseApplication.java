package cn.wsgwz.myapplication;

import javax.inject.Inject;

import cn.wsgwz.basemodule.BaseApplication;
import cn.wsgwz.myapplication.dagger.DaggerAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class AppBaseApplication extends BaseApplication implements HasAndroidInjector {
    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;
    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.create()
                .inject(this);
    }
    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }
}
