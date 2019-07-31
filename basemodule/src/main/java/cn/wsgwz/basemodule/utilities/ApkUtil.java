package cn.wsgwz.basemodule.utilities;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import cn.wsgwz.basemodule.BaseConst;

import java.io.File;

public class ApkUtil {

    public static final Intent getInstallIntent(Context context, File file) {
        //PendingIntent.getActivity(context, 0, AppInstall.getIntent(context, MainService.getDownloadFile(context, fileName)), PendingIntent.FLAG_UPDATE_CURRENT)

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //provider authorities
            Uri apkUri = FileProvider.getUriForFile(context, BaseConst.INSTANCE.getFILE_PROVIDER(), file);
            //Granting Temporary Permissions to a URI
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        return intent.putExtra(Intent.EXTRA_INSTALLER_PACKAGE_NAME, context.getApplicationInfo().packageName);

    }


    public static final PendingIntent getInstallPendingIntent(Context context, File file) {
        return PendingIntent.getActivity(context, 0, getInstallIntent(context, file), PendingIntent.FLAG_UPDATE_CURRENT);
    }


    public static int getVersionCode(Context context) {
        int code = 0;
        if (context != null) {
            PackageManager manager = context.getPackageManager();
            try {
                PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
                code = info.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        return code;
    }

    public static String getVersionName(Context context) {
        String name = null;
        if (context != null) {
            PackageManager manager = context.getPackageManager();
            try {
                PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
                name = info.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }


        return name;
    }


}
