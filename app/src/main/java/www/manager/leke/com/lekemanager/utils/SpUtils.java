package www.manager.leke.com.lekemanager.utils;

import android.content.SharedPreferences;

import www.manager.leke.com.lekemanager.manager.AppManager;


public class SpUtils {

    private static final String XML_NAME = "readerDemo";
    private static SharedPreferences mSp;
    private static SharedPreferences.Editor mEt;

    private static SharedPreferences getSharedPreferences() {
        if (mSp == null) {
            mSp = AppManager.getAppContext().getSharedPreferences(XML_NAME, AppManager.getAppContext().MODE_PRIVATE);
        }
        return mSp;
    }

    private static SharedPreferences.Editor getEditor() {
        if (mEt == null) {
            mEt = getSharedPreferences().edit();
        }
        return mEt;
    }

    public static void putInt(String key, int value) {
        getEditor().putInt(key, value);
        getEditor().commit();
    }

    public static int getInt(String key, int defaultValue) {
        return getSharedPreferences().getInt(key, defaultValue);
    }


    public static String getString(String key) {
        return  getSharedPreferences().getString(key, "");
    }
    public static String getString(String key, String defaultValue) {
        return getSharedPreferences().getString(key, defaultValue);
    }

    public static void putString(String key, String value) {
        getEditor().putString(key, value);
        getEditor().commit();
    }
    public static String getUserId() {
        return SpUtils.getString("userId");
    }
    public static void setUserId(String userId) {
        SpUtils.putString("userId", userId);
    }

    public static boolean clearXml() {
        return getEditor().clear().commit();
    }
}
