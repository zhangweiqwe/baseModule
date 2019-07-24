package cn.wsgwz.basemodule.dagger;


import cn.wsgwz.basemodule.BaseApplication;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Component(modules = {
        BaseCommonModule.class,
        BaseActivityModule.class,
        AndroidSupportInjectionModule.class,
        AndroidInjectionModule.class
})
public interface BaseAppComponent extends AndroidInjector<BaseApplication> {

}
