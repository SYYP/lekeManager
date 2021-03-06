package www.manager.leke.com.lekemanager.manager;

import android.util.SparseIntArray;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadRequest;

import java.util.ArrayList;
import java.util.List;

import www.manager.leke.com.lekemanager.utils.FileUtil;
import www.manager.leke.com.lekemanager.utils.MD5Utils;
import www.manager.leke.com.lekemanager.utils.exercosesUtils.CallServer;

/**
 * 功能：下载题
 * 作者: YUAN_YE
 * 日期: 2019/7/1
 * 时间: 17:08
 */
public class DownLoadTopicManager {
    private List<DownloadRequest> mDownloadRequests = new ArrayList<>();
    private SparseIntArray mIndexs = new SparseIntArray();

    /**
     * @param url 下载地址
     */
    public void loadExercises(String url, DownloadListener downloadListener) {
        cancel();
        mDownloadRequests.clear();
        mIndexs.clear();
        //如果使用断点续传的话，一定要指定文件名喔。
        // url 下载地址。
        // fileFolder 保存的文件夹。
        // fileName 文件名。
        // isRange 是否断点续传下载。
        // isDeleteOld 如果发现存在同名文件，是否删除后重新下载，如果不删除，则直接下载成功。
        String md5Name = MD5Utils.MD5Encode(url);
        DownloadRequest request = NoHttp.createDownloadRequest(url, FileUtil.getExercisesPath() + md5Name.substring(0, 2) + "/", MD5Utils.MD5Encode(url) + ".pdf", true, false);
        mDownloadRequests.add(request);
        // what 区分下载。
        // downloadRequest 下载请求对象。
        // downloadListener 下载监听。
        CallServer.getDownloadInstance().add(10, request, downloadListener);
        mIndexs.put(0, 0);
    }

    /**
     * @param url 下载地址
     */
    public void loadTaskAttach(String url, int taskId , String fileName, DownloadListener downloadListener) {
        cancel();
        mDownloadRequests.clear();
        mIndexs.clear();
        //如果使用断点续传的话，一定要指定文件名喔。
        // url 下载地址。
        // fileFolder 保存的文件夹。
        // fileName 文件名。
        // isRange 是否断点续传下载。
        // isDeleteOld 如果发现存在同名文件，是否删除后重新下载，如果不删除，则直接下载成功。

        DownloadRequest request = NoHttp.createDownloadRequest(url, FileUtil.getTaskCacheDir() + taskId + "/", fileName, true, false);
        mDownloadRequests.add(request);
        // what 区分下载。
        // downloadRequest 下载请求对象。
        // downloadListener 下载监听。
        CallServer.getDownloadInstance().add(10, request, downloadListener);
        mIndexs.put(0, 0);
    }




    /**
     * 取消下载
     */
    public void cancel() {
        if (mDownloadRequests != null && mDownloadRequests.size() > 0) {
            for (DownloadRequest request : mDownloadRequests) {
                request.cancel();
            }
        }
    }

}
