package cn.wsgwz.myapplication.dagger;

import cn.wsgwz.myapplication.Main2Activity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

// 注意这里用的是抽象类和抽象方法
@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract Main2Activity main2Activity();  // 绑定 MainActivity



    /*@ContributesAndroidInjector
    abstract BlankFragment blankFragment();  // 绑定 BlankFragment*/
}
