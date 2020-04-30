package www.manager.leke.com.lekemanager.utils;

import com.google.gson.Gson;

public class GsonUitls {
    public static String toJson(Object src) {
        return new Gson().toJson(src);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return new Gson().fromJson(json, classOfT);
    }
}
