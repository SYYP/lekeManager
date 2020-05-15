package www.manager.leke.com.lekemanager.manager;

import android.view.View;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.onyx.android.sdk.utils.NetworkUtil;

import java.util.List;
import rx.functions.Action1;
import www.manager.leke.com.lekemanager.base.BaseFragmentActivity;
import www.manager.leke.com.lekemanager.bean.DownOssBean;
import www.manager.leke.com.lekemanager.configuration.GlobalConstant;
import www.manager.leke.com.lekemanager.dialog.BaseDialogs;
import www.manager.leke.com.lekemanager.dialog.DialogUtils;
import www.manager.leke.com.lekemanager.http.HttpManager;
import www.manager.leke.com.lekemanager.listener.DownOssistener;
import www.manager.leke.com.lekemanager.net.DownRequest;
import www.manager.leke.com.lekemanager.net.OssInfoBean;
import www.manager.leke.com.lekemanager.utils.FileUtils;
import www.manager.leke.com.lekemanager.utils.LogUtils;
import www.manager.leke.com.lekemanager.utils.SpUtils;

import static www.manager.leke.com.lekemanager.configuration.HtppConfiguration.DEVICE_MODEL;

//
public class DownVoiceView {

    private String mBookId;
    private String mType;
    private Runnable mExceptionRun;
    private Runnable mSuccessRun;
    private DownOssBean mOssBean = null;
 ;
    /**
     * @param booId 图书id
     * @param type  请求类型  附件类型（默认BM04为图书，BM06为音频zip包，BM07为音频位置文件包）
     */
    public void requestBookAndVoiceFiels(final String booId, String type, Runnable exceptionRun, Runnable successRun) {
        mOssBean = null;
        mBookId = booId;
        mType = type;
        mSuccessRun = successRun;
        mExceptionRun = exceptionRun;


        if (!NetworkUtil.isWiFiConnected(AppManager.getAppContext())) {
            BaseFragmentActivity.showToast("网络断开");
            return;
        }
        BaseFragmentActivity.getCurrentActivity().showLoadingDialog();
        DownRequest request = new DownRequest();
        request.setBookId(booId);
        request.setAtchTypeCode(type);
        downBookTast(request);
    }


    //    附件类型（默认BM04为图书，BM06为音频zip包，BM07为音频位置文件包）
    private void downBookTast(DownRequest request) {
        HttpManager.getInstace().downloadBook2(request).
                compose(BaseFragmentActivity.getCurrentActivity().<List<OssInfoBean>>bindToLifecycle()).subscribe(new Action1<List<OssInfoBean>>() {
            @Override
            public void call(List<OssInfoBean> ossInfoBeans) {
                LogUtils.e("查询 成功");
                BaseFragmentActivity.getCurrentActivity().dimissLoadingDialog();
                if (ossInfoBeans != null && ossInfoBeans.size() > 0) {
                    for (OssInfoBean bean : ossInfoBeans) {
                       if (bean.getAtchSupport().equalsIgnoreCase(DEVICE_MODEL)) {
                            mOssBean = new DownOssBean();
                            mOssBean.setBookId(mBookId);
                            mOssBean.setAccessKeyId(bean.getAccessKeyId());
                            mOssBean.setAccessKeySecret(bean.getAccessKeySecret());
                            mOssBean.setSecurityToken(bean.getSecurityToken());
                            mOssBean.setExpiration(bean.getExpiration());
                            mOssBean.setObjectKey(bean.getAtchRemotePath());
                            mOssBean.setBucketName(bean.getAtchBucket());
                            mOssBean.setType(mType);

                            switch (mType) {
                                case "BM04":
                                    LogUtils.e("BM04....");
                                    mOssBean.setSaveFilePath(FileUtils.getAppBookFileDirPath() + mBookId + ".pdf");
                                    SpUtils.putString(mBookId + GlobalConstant.BOOK_ID_KEY, bean.getAtchEncryptKey());
                                    break;
                                case "BM06":
                                    LogUtils.e("BM06....");
                                    mOssBean.setSaveFilePath(FileUtils.getMediaMp3Path() + mBookId + ".zip");
                                    break;
                                case "BM07":
                                    LogUtils.e("BM07.....");
                                    mOssBean.setSaveFilePath(FileUtils.getMediaJsonPath() + mBookId + ".zip");
                                    break;
                            }

                            break;
                        }
                    }
                    if (mOssBean != null) {
                        confirm();
                    } else {
                        outExcePtionMethed();
                        BaseFragmentActivity.showToast("没有支持的设备型号");
                        LogUtils.e("没有支持的设备型号");
                    }
                } else {
                    outExcePtionMethed();
                    BaseFragmentActivity.showToast("服务器未返回数据");
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                LogUtils.e("查询 下载地址 失败");
                BaseFragmentActivity.getCurrentActivity().dimissLoadingDialog();
                BaseFragmentActivity.showToast("查询 下载地址 失败");
                outExcePtionMethed();
            }
        });
    }
    private void confirm() {
        downBook();
    }

    private void downBook() {
        DownOssManager.getInstance().downBookAsy(mOssBean, new DownOssistener() {
            @Override
            public void onSuccess(final int progress) {
                LogUtils.e("下载....progress..." + progress);
                mProgressListener.OnClickListener(progress);
            }

            @Override
            public void onFinish() {
                LogUtils.e("下载....onFinish");
                outSuccessMethed();
            }

            @Override
            public void onFailure(GetObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                LogUtils.e("下载....onFailure");
                AppManager.postHandler(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }

    private void outExcePtionMethed() {
        AppManager.postHandler(new Runnable() {
            @Override
            public void run() {
                AppManager.postHandler(mExceptionRun);
            }
        });
    }


    public interface ProgressListener {
        void OnClickListener( int projress);
    }

    private ProgressListener mProgressListener;

    public void setListener(ProgressListener progressListener) {

        this.mProgressListener = progressListener;
    }
    private void outSuccessMethed() {
        AppManager.postHandler(new Runnable() {
            @Override
            public void run() {
                AppManager.postHandler(mSuccessRun);
            }
        });
    }
}
