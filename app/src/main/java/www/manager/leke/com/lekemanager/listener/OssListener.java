package www.manager.leke.com.lekemanager.listener;

/**
 * 功能：
 * 作者: YUAN_YE
 * 日期: 2019/7/1
 * 时间: 10:17
 */
public interface OssListener {
    void onSuccess(int progress);

    void onFinish();

    void onFailure(String type);
}
