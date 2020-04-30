package www.manager.leke.com.lekemanager.configuration;


import www.manager.leke.com.lekemanager.utils.SpUtils;
import www.manager.leke.com.lekemanager.utils.SystemUtils;

public class HtppConfiguration {

    //请求 超时 时间
    public static final int HTTP_CONNECTION_TIMEOUT = 15 * 1000;
    //线上
    public static String BASE_URL;
    public static String ENDPOINT;

    //预发布：
    //public static final String BASE_URL = "https://api.schoolpad.cn/";
    //public static final String ENDPOINT = "http://oss-cn-shanghai.aliyuncs.com";

    // 网络请求出错的时候 尝试次数
    public static final int HTTP_RETRY = 3;
    public static final String DEVICE_MODEL = SystemUtils.getDeviceModel();
    public static String ANSWER_PIC_HOST;

    public static void initUrl() {
        if (SpUtils.getInt("url", 0) == 0) {
            BASE_URL = "http://api.edu-pad.com.cn/";
            ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";
            ANSWER_PIC_HOST = ".oss-cn-beijing.aliyuncs.com/";
        } else {
            BASE_URL = "https://api.schoolpad.cn/";
            ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";
            ANSWER_PIC_HOST = ".oss-cn-beijing.aliyuncs.com/";
        }

//        if (SpUtils.getInt("url", 0) == 0) {
//            BASE_URL = "http://api.learningpad.cn/";
//            ENDPOINT = "http:/oss-cn-shanghai.aliyuncs.com";
//            ANSWER_PIC_HOST = ".oss-cn-shanghai.aliyuncs.com/";
//        } else {
//            BASE_URL = "https://api.learningpad.cn/";
//            ENDPOINT = "http://oss-cn-shanghai.aliyuncs.com" ;
//            ANSWER_PIC_HOST = ".oss-cn-shanghai.aliyuncs.com/";
//        }
    }
}