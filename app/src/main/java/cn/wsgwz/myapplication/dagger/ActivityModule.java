package cn.wsgwz.myapplication.dagger;

import cn.wsgwz.myapplication.Main2Activity;
import cn.wsgwz.myapplication.TestDownloadActivity;
import cn.wsgwz.myapplication.TestUploadActivity;
import cn.wsgwz.myapplication.TestUserManagerActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

// 注意这里用的是抽象类和抽象方法
@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract Main2Activity main2Activity();

    @ContributesAndroidInjector
    abstract TestDownloadActivity testDownloadActivity();


    @ContributesAndroidInjector
    abstract TestUploadActivity testUploadActivity();

    @ContributesAndroidInjector
    abstract TestUserManagerActivity testUserManagerActivity();

    /*@ContributesAndroidInjector
    abstract BlankFragment blankFragment();  // 绑定 BlankFragment*/
}
