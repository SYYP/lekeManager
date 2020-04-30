package www.manager.leke.com.lekemanager.utils.exercosesUtils;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.download.DownloadQueue;

/**
 * 功能：
 * 作者: YUAN_YE
 * 日期: 2019/4/30
 * 时间: 9:33
 */
public class CallServer {
    private static CallServer callServer;
    /**
     * 下载队列.
     */
    private static DownloadQueue downloadQueue;


    /**
     * 下载队列.
     */
    public static DownloadQueue getDownloadInstance() {
        if (downloadQueue == null)
            downloadQueue = NoHttp.newDownloadQueue(1);
        return downloadQueue;
    }
}