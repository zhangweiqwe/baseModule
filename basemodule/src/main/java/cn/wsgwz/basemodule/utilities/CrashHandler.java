package cn.wsgwz.basemodule.utilities;

import android.content.Context;
import android.os.Process;
import cn.wsgwz.basemodule.BaseConst;
import cn.wsgwz.basemodule.utilities.manager.FileManager;

import java.io.*;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Create by ChenHao on 2018/6/299:30
 * use : 应用异常处理类
 * 使用方式： 在Application 中初始化  CrashHandler.getInstance().init(this);
 */
public class CrashHandler implements UncaughtExceptionHandler {


    private static CrashHandler sInstance = new CrashHandler();
    private UncaughtExceptionHandler mDefaultCrashHandler;


    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return sInstance;
    }

    public void init(Context context) {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        // LLog.Companion.d(LOG_TAG, ex + "   " + ex.getMessage());
        if (mDefaultCrashHandler != null) {
            if (ex != null) {
                String logStr = new SimpleDateFormat(BaseConst.SIMPLE_DATA_FORMAT).format(new Date()) + "\n" + collectExceptionInfo(ex) + "\n\n\n#########################\n\n\n\n\n\n\n";
                FileManager.Companion.getInstance().saveLog(logStr, true);
            }

            try {
                mDefaultCrashHandler.uncaughtException(thread, ex);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Process.killProcess(Process.myPid());
        }

    }


    /**
     * 获取捕获异常的信息
     */
    private String collectExceptionInfo(Throwable ex) {
        Writer mWriter = new StringWriter();
        PrintWriter mPrintWriter = new PrintWriter(mWriter);
        ex.printStackTrace(mPrintWriter);
        //ex.printStackTrace();
        Throwable mThrowable = ex.getCause();
        // 迭代栈队列把所有的异常信息写入writer中
        while (mThrowable != null) {
            mThrowable.printStackTrace(mPrintWriter);
            // 换行 每个个异常栈之间换行
            mPrintWriter.append("\r\n");
            mThrowable = mThrowable.getCause();
        }
        // 记得关闭
        mPrintWriter.close();
        return mWriter.toString();
    }


}

