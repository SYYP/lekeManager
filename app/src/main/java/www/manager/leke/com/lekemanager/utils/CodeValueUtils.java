package www.manager.leke.com.lekemanager.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ypu
 * on 2020/5/14 0014
 */
public class CodeValueUtils {
    Map<String, String> mStringMap = new HashMap<>();
    private volatile static CodeValueUtils mSingleton = null;

    private CodeValueUtils() {

    }
    public static CodeValueUtils getInstance() {
        if (mSingleton == null) {
            synchronized (CodeValueUtils.class) {
                if (mSingleton == null) {
                    mSingleton = new CodeValueUtils();
                }
            }
        }
        return mSingleton;

    }
    public String getCodeValues(String code) {
        if(mStringMap.isEmpty()){
            mStringMap.put("BA02", "待编辑");
            mStringMap.put("BA03", "编辑中");
            mStringMap.put("BA05", "待校对");
            mStringMap.put("BA09", "待审核");
        }
        for (Map.Entry<String, String> stringEntry : mStringMap.entrySet()) {
            if (code.equals(stringEntry.getKey())) {
                return stringEntry.getValue();
            }
        }
        return null;
    }
}
