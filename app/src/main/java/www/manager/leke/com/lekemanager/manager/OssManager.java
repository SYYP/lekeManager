package www.manager.leke.com.lekemanager.manager;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import www.manager.leke.com.lekemanager.bean.AliOssInfo;
import www.manager.leke.com.lekemanager.listener.OssListener;
import www.manager.leke.com.lekemanager.utils.FileUtil;
import www.manager.leke.com.lekemanager.utils.LogUtils;
import www.manager.leke.com.lekemanager.utils.UIUtils;

/**
 * 功能：OSS框架
 * 作者: YUAN_YE
 * 日期: 2019/7/1
 * 时间: 9:02
 */
public class OssManager {

    private OSSCredentialProvider mOSSCredentialProvider;
    private ClientConfiguration mClientConfiguration;
    private OSSClient mOSSClient;
    private OSSAsyncTask<GetObjectResult> mOSSAsyncTask;
    private static OssManager mInstance;
    private AliOssInfo mOssInfo;

    private String TAG = OssManager.class.getName();

    private OssManager() {

    }

    public synchronized static OssManager getInstance() {
        if (mInstance == null) {
            mInstance = new OssManager();
        }
        return mInstance;
    }


    private void initConfig() {

        if (null == mClientConfiguration) {
            mClientConfiguration = new ClientConfiguration();
            mClientConfiguration.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
            mClientConfiguration.setSocketTimeout(15 * 1000); // socket超时，默认15秒
            mClientConfiguration.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
            mClientConfiguration.setMaxErrorRetry(2);
            OSSLog.enableLog();
        }

        mOSSCredentialProvider = new OSSFederationCredentialProvider() {
            @Override
            public OSSFederationToken getFederationToken() {
                return new OSSFederationToken(mOssInfo.getAccessKeyId(), mOssInfo.getAccessKeySecret(), mOssInfo.getSecurityToken(), mOssInfo.getExpiration());
            }
        };

        mOSSClient = new OSSClient(UIUtils.getContext(), mOssInfo.getEndpoint(), mOSSCredentialProvider, mClientConfiguration);

    }

    public synchronized void asyDownFile(AliOssInfo ossInfo, OssListener listener) {
        mOssInfo = ossInfo;
        LogUtils.e(TAG, "mOssInfo....." + mOssInfo.toString());
        initConfig();

        GetObjectRequest get = new GetObjectRequest(ossInfo.getBucketName(), ossInfo.getObjectKey());
        mOSSAsyncTask = mOSSClient.asyncGetObject(get, new OSSCompletedCallback<GetObjectRequest, GetObjectResult>() {
            @Override
            public void onSuccess(GetObjectRequest getObjectRequest, GetObjectResult getObjectResult) {
                LogUtils.e(TAG, "onSuccess .........................");
                InputStream inputStream = getObjectResult.getObjectContent();
                byte[] buffer = new byte[2048];
                FileOutputStream fos = null;
                long length = getObjectResult.getContentLength();
                LogUtils.e(TAG, "getContentLength......." + length);


                if (length == 0) {
                    FileUtil.delFileOrFolder(mOssInfo.getSavePath());
                    LogUtils.e(TAG, " error .....getContentLength == 0");
                    UIUtils.post(() -> listener.onFailure(mOssInfo.getFileType()));
                    return;
                }

                double rate = (double) 100 / length;
                int total = 0;
                int len;

                try {
                    fos = new FileOutputStream(mOssInfo.getSavePath());
                    while ((len = inputStream.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        total += len;
                        int finalTotal = total;
                        UIUtils.post(() -> listener.onSuccess((int) (finalTotal * rate)));

                    }
                    UIUtils.post(() -> listener.onFinish());

                } catch (IOException e) {
                    LogUtils.e(TAG, "IOException..." + e.toString());
                    FileUtil.delFileOrFolder(mOssInfo.getSavePath());
                    UIUtils.post(() -> listener.onFailure(mOssInfo.getFileType()));

                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            inputStream.close();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(GetObjectRequest getObjectRequest, ClientException clientException, ServiceException serviceException) {
                if (clientException != null) {
                    // 本地异常如网络异常等
                    LogUtils.e(TAG, "地异常如网络异常等 .........." + clientException.toString());
                }

                if (serviceException != null) {
                    LogUtils.e(TAG, "服务异常.........." + serviceException.toString());
                }

                FileUtil.delFileOrFolder(mOssInfo.getSavePath());
                UIUtils.post(() -> listener.onFailure(mOssInfo.getFileType()));
            }
        });
    }


    public void cancel(String id  ) {
        if (mOSSAsyncTask != null) {
            try {

                switch (mOssInfo.getFileType()) {
                    case "book":
                        FileUtil.delFileOrFolder(FileUtil.getReaderPath() + id + ".pdf");
                        break;

                    case "audio":
                        FileUtil.delFileOrFolder(FileUtil.getAudioPath() + id + ".zip");
                        break;

                    case "configure":
                        FileUtil.delFileOrFolder(FileUtil.getConfigurePath() + id + ".zip");
                        break;
                }

                mOSSAsyncTask.cancel();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
