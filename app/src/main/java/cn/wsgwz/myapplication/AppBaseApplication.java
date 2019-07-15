package cn.wsgwz.myapplication;


import cn.wsgwz.basemodule.BaseApplication;
import cn.wsgwz.myapplication.dagger.DaggerAppComponent;

public class AppBaseApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.create()
                .inject(this);
    }
}
