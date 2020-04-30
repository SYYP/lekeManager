package www.manager.leke.com.lekemanager.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class SystemUtils {

    public static String getDeviceModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取当前应用版本号
     * @param mContext
     * @return
     */
    public static int getVersionCode(Context mContext) {
        int result = 1;
        try {
            if (mContext == null) {
                return result;
            }
            // 获取packagemanager的实例
            PackageManager packageManager = mContext.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (Exception e) {
            return result;
        }
    }
}
