package cn.wsgwz.basemodule.utilities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import java.lang.reflect.Field;

public class WindowUtil {
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = (int) context.getResources().getDimension(resourceId);
        }
        return result;
        //return DensityUtil.dp2px(context,45);
    }

    public static void setStatusBarTransparent(Activity activity){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            try {
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                field.setInt(activity.getWindow().getDecorView(), Color.TRANSPARENT);  //改为透明
            } catch (Exception e) {}
        }
    }
}
