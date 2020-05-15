package www.manager.leke.com.lekemanager.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * 功能：IO操作
 * 作者: YUAN_YE
 * 日期: 2019/4/23
 * 时间: 10:44
 */
public class IOUtils {

    public static void close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
