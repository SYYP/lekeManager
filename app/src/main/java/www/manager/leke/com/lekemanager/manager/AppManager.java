package www.manager.leke.com.lekemanager.manager;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;
import android.text.TextUtils;


import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.yanzhenjie.nohttp.NoHttp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import www.manager.leke.com.lekemanager.configuration.HtppConfiguration;
import www.manager.leke.com.lekemanager.utils.FileUtil;
import www.manager.leke.com.lekemanager.utils.FileUtils;
import www.manager.leke.com.lekemanager.view.PhoneReaderApp;


public class AppManager extends PhoneReaderApp {
    private static Context mContext;
    public static Handler mMainThreadHandler;
    private static int mMainThreadId = -1;

    private static Thread mMainThread;
    private static Looper mMainLooper;

    public static Context getContext() {
        return mContext;
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }


    public static Thread getMainThread() {
        return mMainThread;
    }

    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        NoHttp.initialize(this);
        mContext = AppManager.this;
        mMainThreadHandler = new Handler();
        initDir();
        NetManager.getInstance().registerReceiver(mContext);
        mMainThreadId = android.os.Process.myTid();
        HtppConfiguration.initUrl();
        //  initBugly();
        MultiDex.install(this);
        if (inMainProcess(this)) {
            initThreadData();
            FileUtil.creatUserDir();
        }
    }

    public static boolean inMainProcess(Context context) {
        String packageName = context.getPackageName();
        String processName = getProcessName(context);
        return packageName.equals(processName);
    }

    private void initThreadData() {
        mMainThreadHandler = new Handler();
        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainLooper = getMainLooper();
    }

    private void initBugly() {
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
//        String processName = getProcessName(android.os.Process.myPid());
//        // 设置是否为上报进程
//        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
//        strategy.setUploadProcess(processName == null || processName.equals(packageName));

    }


    public static Context getAppContext() {
        return mContext;
    }

    private void initDir() {
        FileUtils.creatBookDir();
        FileUtils.creatMediaDir();
        FileUtils.creatMediaJsonDir();
        FileUtils.creatMediaMp3Dir();
        FileUtils.creatExercisesDir();
    }


    public static boolean postHandler(Runnable runnable) {
        boolean result;
        result = mMainThreadHandler.post(runnable);
        return result;
    }

    public static boolean postDelayedHandler(Runnable runnable, long delay) {
        boolean result;
        result = mMainThreadHandler.postDelayed(runnable, delay);
        return result;
    }

    /**
     * 把任务从主线程的消息队列中删除
     */
    public static void removeCallbacks(Runnable runnable) {
        mMainThreadHandler.removeCallbacks(runnable);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        NetManager.getInstance().unregisterReceiver(mContext);
    }

    public static int getMainThreadId() {
        return mMainThreadId;
    }

    private static String getProcessName(Context context) {
        String processName = null;
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        while (true) {
            for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName;
                    break;
                }
            }
            if (!TextUtils.isEmpty(processName)) {
                return processName;
            }
        }
    }
}