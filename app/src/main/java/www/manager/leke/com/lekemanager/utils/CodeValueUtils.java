package www.manager.leke.com.lekemanager.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ypu
 * on 2020/5/14 0014
 */
public class CodeValueUtils {
    Map<String, String> mStringMap = new HashMap<>();
    Map<String, String> mReadMap;
    Map<String, Integer> mQuestion;
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

    //图书模块
    public String getCodeValues(String code) {
        if (mStringMap.isEmpty()) {
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

    //点读模块
    public String getReadCodeValues(String code) {
        if (mReadMap == null) {
            mReadMap = new HashMap<>();
        }
        if (mReadMap.isEmpty()) {
            mReadMap.put("BT02", "待编辑");
            mReadMap.put("BT03", "编辑中");
            mReadMap.put("BT05", "待校对");
            mReadMap.put("BT09", "待审核");
        }
        for (Map.Entry<String, String> stringEntry : mReadMap.entrySet()) {
            if (stringEntry.getKey().equals(code)) {
                return stringEntry.getValue();
            }
        }
        return null;
    }

    //题库目录模块
    public Integer getQestionNumber(String mlId, HashMap<String, Integer> map) {
        Integer resuly = null;
        mQuestion = map;
        if(mQuestion!=null) {
            if (mQuestion.containsKey(mlId)) {
                Integer integer = mQuestion.get(mlId);
                resuly = integer;
                return resuly;
            }
        }
        return resuly;
    }
}
