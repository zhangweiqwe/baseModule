package cn.wsgwz.basemodule.dagger;


import cn.wsgwz.basemodule.utilities.manager.UserManager;
import cn.wsgwz.basemodule.widgets.dialog.LoadingDialogFragment;
import dagger.Module;
import dagger.Provides;

@Module
public class BaseCommonModule {
    @Provides
    UserManager provideUserManager() {
        return UserManager.getInstance();
    }


    @Provides
    LoadingDialogFragment provideLoadingDialogFragment(){
        return new LoadingDialogFragment();
    }
}
