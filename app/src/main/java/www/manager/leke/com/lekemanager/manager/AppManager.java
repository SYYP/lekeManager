package www.manager.leke.com.lekemanager.manager;

import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.text.TextUtils;


import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.yanzhenjie.nohttp.NoHttp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import www.manager.leke.com.lekemanager.configuration.HtppConfiguration;
import www.manager.leke.com.lekemanager.utils.FileUtils;
import www.manager.leke.com.lekemanager.view.PhoneReaderApp;


public class AppManager extends PhoneReaderApp {
    private static Context mContext;
    public static Handler mMainThreadHandler;
    private static int mMainThreadId = -1;

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
        initBugly();
        MultiDex.install(this);
    }

    private void initBugly() {
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        //例如，只允许在MainActivity上显示更新弹窗，其他activity上不显示弹窗; 如果不设置默认所有activity都可以显示弹窗。
     //   Bugly.init(getApplicationContext(),"92bddf9e9b", false);
//        public static String strToastYourAreTheLatestVersion = "你已经是最新版了";
//        public static String strToastCheckUpgradeError = "检查新版本失败，请稍后重试";
//        public static String strToastCheckingUpgrade = "正在检查，请稍候...";
//        public static String strNotificationDownloading = "正在下载";
//        public static String strNotificationClickToView = "点击查看";
//        public static String strNotificationClickToInstall = "点击安装";
//        public static String strNotificationClickToRetry = "点击重试";
//        public static String strNotificationDownloadSucc = "下载完成";
//        public static String strNotificationDownloadError = "下载失败";
//        public static String strNotificationHaveNewVersion = "有新版本";
//        public static String strNetworkTipsMessage = "你已切换到移动网络，是否继续当前下载？";
//        public static String strNetworkTipsTitle = "网络提示";
//        public static String strNetworkTipsConfirmBtn = "继续下载";
//        public static String strNetworkTipsCancelBtn = "取消";
//        public static String strUpgradeDialogVersionLabel = "版本";
//        public static String strUpgradeDialogFileSizeLabel = "包大小";
//        public static String strUpgradeDialogUpdateTimeLabel = "更新时间";
//        public static String strUpgradeDialogFeatureLabel = "更新说明";
//        public static String strUpgradeDialogUpgradeBtn = "立即更新";
//        public static String strUpgradeDialogInstallBtn = "安装";
//        public static String strUpgradeDialogRetryBtn = "重试";
//        public static String strUpgradeDialogContinueBtn = "继续";
//        public static String strUpgradeDialogCancelBtn = "下次再说";

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

    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}