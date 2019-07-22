package cn.wsgwz.myapplication.dagger;

import cn.wsgwz.myapplication.MainActivity;
import cn.wsgwz.myapplication.TestDownloadActivity;
import cn.wsgwz.myapplication.TestExitActivity;
import cn.wsgwz.myapplication.TestProgressLayoutActivity;
import cn.wsgwz.myapplication.TestTouchActivity;
import cn.wsgwz.myapplication.TestUploadActivity;
import cn.wsgwz.myapplication.TestUserManagerActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

// 注意这里用的是抽象类和抽象方法
@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract TestProgressLayoutActivity testProgressLayoutActivity();

    @ContributesAndroidInjector
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector
    abstract TestDownloadActivity testDownloadActivity();


    @ContributesAndroidInjector
    abstract TestUploadActivity testUploadActivity();

    @ContributesAndroidInjector
    abstract TestUserManagerActivity testUserManagerActivity();


    @ContributesAndroidInjector
    abstract TestExitActivity testExitActivity();


    @ContributesAndroidInjector
    abstract TestTouchActivity testTouchActivity();

    /*@ContributesAndroidInjector
    abstract BlankFragment blankFragment();  // 绑定 BlankFragment*/
}
