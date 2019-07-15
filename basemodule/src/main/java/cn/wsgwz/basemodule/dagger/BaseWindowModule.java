package cn.wsgwz.basemodule.dagger;


import cn.wsgwz.basemodule.utilities.manager.UserManager;
import dagger.Module;
import dagger.Provides;

@Module
public class BaseWindowModule {

    private final BaseWindowDelegate baseWindowDelegate;

    public BaseWindowModule(BaseWindowDelegate baseWindowDelegate) {
        this.baseWindowDelegate = baseWindowDelegate;
    }


    @Provides
    public String provideString() {
        return "provideString";
    }


    /*@Provides
    ConnectivityManager provideConnectivityManager(){
        return (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Provides
    NetworkRequest provideNetworkRequest(){
        return new NetworkRequest.Builder().build();
    }*/
}
