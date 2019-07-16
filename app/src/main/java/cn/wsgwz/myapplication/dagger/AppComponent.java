package cn.wsgwz.myapplication.dagger;


import javax.inject.Singleton;
import cn.wsgwz.basemodule.dagger.BaseCommonModule;
import cn.wsgwz.myapplication.AppBaseApplication;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        BaseCommonModule.class,
        ActivityModule.class,  // 用于绑定项目中的Activity
        AndroidSupportInjectionModule.class,  // 用于绑定扩展的组件，如v4
        AndroidInjectionModule.class})  // 用于绑定普通的组件
public interface AppComponent extends AndroidInjector<AppBaseApplication> {

}
