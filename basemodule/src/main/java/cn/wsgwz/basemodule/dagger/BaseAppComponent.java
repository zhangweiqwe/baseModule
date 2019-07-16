package cn.wsgwz.basemodule.dagger;


import cn.wsgwz.basemodule.BaseApplication;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Component(modules = {
        BaseCommonModule.class,
        BaseActivityModule.class,  // 用于绑定项目中的Activity
        AndroidSupportInjectionModule.class,  // 用于绑定扩展的组件，如v4
        AndroidInjectionModule.class})  // 用于绑定普通的组件
public interface BaseAppComponent extends AndroidInjector<BaseApplication> {

}
