package cn.wsgwz.basemodule.dagger;

import cn.wsgwz.basemodule.BaseActivity;
import cn.wsgwz.basemodule.BaseWebViewActivity;
import cn.wsgwz.basemodule.NetworkDataActivity;
import cn.wsgwz.basemodule.TestToolSettingActivity;
import cn.wsgwz.basemodule.widgets.suspension.SuspensionWindowActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

// 注意这里用的是抽象类和抽象方法
@Module
public abstract class BaseActivityModule {
    @ContributesAndroidInjector
    abstract TestToolSettingActivity testToolSettingActivity();

    @ContributesAndroidInjector
    abstract NetworkDataActivity networkDataActivity();

    @ContributesAndroidInjector
    abstract BaseWebViewActivity baseWebViewActivity();

    @ContributesAndroidInjector
    abstract SuspensionWindowActivity suspensionWindowActivity();


    @ContributesAndroidInjector
    abstract BaseActivity baseActivity();



    /*@ContributesAndroidInjector
    abstract BlankFragment blankFragment();  // 绑定 BlankFragment*/
}
