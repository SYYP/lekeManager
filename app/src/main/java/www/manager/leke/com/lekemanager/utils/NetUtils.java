package www.manager.leke.com.lekemanager.utils;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;


/**
 * 功能：网络的状态判断
 * 作者: YUAN_YE
 * 日期: 2019/4/23
 * 时间: 10:44
 */
public class NetUtils {

    /**
     * 判断网络是否连接
     *
     * @return
     */
    public static boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) UIUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.getType() == 1) {
            return networkInfo.isAvailable();
        }
        return false;
    }


    /**
     * 这里得到信号强度就靠wifiinfo.getRssi()；这个方法。得到的值是一个0到-100的区间值，是一个int型数据，其中0到-50表示信号最好，-50到-70表示信号偏差，小于-70表示最差，有可能连接不上或者掉线，一般Wifi已断则值为-200。
     * 获取当前连接的网络信息
     *
     * @return
     */
    public static int getConnectionInfoRssi() {
        WifiManager wifiManager = (WifiManager) UIUtils.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifiManager.getConnectionInfo().getRssi();
    }


    /**
     * 判断是否有可用的网络
     */
    public static boolean isNetAvailable() {
        ConnectivityManager mgr = getConnManager();
        NetworkInfo[] info = mgr.getAllNetworkInfo();
        if (info != null) {
            for (NetworkInfo anInfo : info) {
                if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取网络连接管理者ConnectivityManager
     */
    private static ConnectivityManager getConnManager() {
        Context context = UIUtils.getContext();
        if (context == null) {
            return null;
        }
        Object service = context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != service) {
            return (ConnectivityManager) service;
        }
        return null;
    }




    /***
     * 关闭和打开WIFI
     * @param enabled
     */
    public static void changeWiFi(boolean enabled) {
        LogUtils.d("changeWiFi enabled = " + enabled);
        try {
            WifiManager wifiManager = (WifiManager) UIUtils.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(enabled);
            if (enabled)
                wifiManager.reconnect();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    /**
     * 进入系统的设置
     */
    public static void gotoSystemSetting() {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.onyx.android.settings", "com.onyx.android.libsetting.view.activity.DeviceMainSettingActivity"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            UIUtils.getContext().startActivity(intent);


//            if (DeviceTypeGlobal.DEVICE_TYPE_SYSTEM.equalsIgnoreCase(DeviceTypeGlobal.DEVICE_TYPE_EDU)
//                    || DeviceTypeGlobal.DEVICE_TYPE_SYSTEM.equalsIgnoreCase(DeviceTypeGlobal.DEVICE_TYPE_PL107)) {
//                Intent intent = new Intent();
//                intent.setComponent(new ComponentName("com.onyx.android.settings", "com.onyx.android.libsetting.view.activity.DeviceMainSettingActivity"));
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                UIUtils.getContext().startActivity(intent);
//
//            } else {
//               Intent intent = new Intent("onyx.settings.action.network");
////                Intent intent = new Intent("onyx.settings.action.datetime");
//                intent.setComponent(new ComponentName("com.onyx", "com.onyx.setting.ui.SettingContainerActivity"));
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                UIUtils.getContext().startActivity(intent);
//            }
        } catch (Exception e) {
            UIUtils.showToastSafe("打开 系统设置失败", 0);
            e.printStackTrace();
        }
    }
}