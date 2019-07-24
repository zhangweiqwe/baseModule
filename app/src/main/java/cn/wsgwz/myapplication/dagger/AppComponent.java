package cn.wsgwz.myapplication.dagger;


import javax.inject.Singleton;

import cn.wsgwz.myapplication.AppBaseApplication;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        CommonModule.class,
        ActivityModule.class,
        AndroidSupportInjectionModule.class,
        AndroidInjectionModule.class})
public interface AppComponent extends AndroidInjector<AppBaseApplication> {

}
