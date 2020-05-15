package www.manager.leke.com.lekemanager.base;

/**
 * 功能：
 * 作者: YUAN_YE
 * 日期: 2019/6/12
 * 时间: 14:48
 */
public class ServerCode {
    //网络请求成功
    public static int success = 100000;
    //查询不到该设备信息 ,设备未绑定
    public static int not_find_device = 210001;
    //token过期
    public static int token_invalid = 100202;
    //错误码，被解绑
    public static int untie_device = 100103;
    //该学生已经提交作答了
    public static int already_commit = 202003;
    // 模拟登录post请求报错的提示
    public static int cosplay_post_error = 100201;
}
