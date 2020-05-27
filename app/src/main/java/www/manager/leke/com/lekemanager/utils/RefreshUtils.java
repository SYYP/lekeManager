package www.manager.leke.com.lekemanager.utils;

import android.view.View;

import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.api.device.epd.UpdateMode;

/**
 * 功能：墨水屏幕的 刷新操作
 * 作者: YUAN_YE
 * 日期: 2019/4/23
 * 时间: 10:46
 */
public class RefreshUtils {
    private static boolean flag = true;
    public static boolean isEnterA2Mode = false;
    /**全局刷新*/
    public static void invalidate(View view) {
        if (flag) {
            try {
                EpdController.invalidate(view, UpdateMode.GC);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public static void enterA2Mode() {
        isEnterA2Mode = true;
        EpdController.applyApplicationFastMode(RefreshUtils.class.getName(), true, true, UpdateMode.ANIMATION_QUALITY, Integer.MAX_VALUE);
    }

    public static void enterNormalMode() {
        isEnterA2Mode = false;
        EpdController.applyApplicationFastMode(RefreshUtils.class.getName(), false, true);
    }
}
