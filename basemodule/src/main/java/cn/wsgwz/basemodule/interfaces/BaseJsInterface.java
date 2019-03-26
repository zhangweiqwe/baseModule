package cn.wsgwz.basemodule.interfaces;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;
import cn.wsgwz.basemodule.data.User;
import cn.wsgwz.basemodule.utilities.manager.UserManager;
import cn.wsgwz.basemodule.utilities.AndroidBug5497Workaround;
import cn.wsgwz.basemodule.utilities.NetworkUtil;

public class BaseJsInterface {
    public Context context;

    public BaseJsInterface(Context context) {
        this.context = context;
    }

    /*@JavascriptInterface
    public void assistActivity() {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            AndroidBug5497Workaround.assistActivity(activity);
        }
    }*/

    @JavascriptInterface
    public String getToken() {
        User user = UserManager.Companion.getInstance().getCurrentUser();

        if (user == null) {
            return null;
        } else {
            return user.getToken();
        }
    }

    @JavascriptInterface
    public void login() {
        if (context instanceof BaseWindowInterface) {
            BaseWindowInterface baseWindowInterface = (BaseWindowInterface) context;
            baseWindowInterface.login();
        }
    }

    @JavascriptInterface
    public boolean isAndroid() {
        return true;
    }

    @JavascriptInterface
    public void toast(String msg) {
        if (context instanceof BaseWindowInterface) {
            BaseWindowInterface baseWindowInterface = (BaseWindowInterface) context;
            baseWindowInterface.toast(msg);
        }
    }

    @JavascriptInterface
    public boolean isMeteredNetwork() {
        return NetworkUtil.isMeteredNetwork(context);
    }

}
