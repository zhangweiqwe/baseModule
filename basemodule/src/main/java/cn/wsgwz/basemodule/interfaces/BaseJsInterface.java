package cn.wsgwz.basemodule.interfaces;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import cn.wsgwz.basemodule.BaseApplication;
import cn.wsgwz.basemodule.data.User;
import cn.wsgwz.basemodule.utilities.manager.UserManager;
import cn.wsgwz.basemodule.utilities.NetworkUtil;

public class BaseJsInterface {
    public Context context;

    private UserManager userManager = UserManager.getInstance();
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
        return userManager.getCurrentUserToken();
    }

    @JavascriptInterface
    public void login() {
        BaseApplication.getInstance().login();
    }

    @JavascriptInterface
    public boolean isAndroid() {
        return true;
    }

    @JavascriptInterface
    public void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public boolean isMeteredNetwork() {
        return NetworkUtil.isMeteredNetwork(context);
    }

}
