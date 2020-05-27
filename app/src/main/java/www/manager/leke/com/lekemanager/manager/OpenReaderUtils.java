package www.manager.leke.com.lekemanager.manager;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.yanzhenjie.nohttp.tools.NetUtils;

import java.util.List;

import rx.functions.Action1;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.activity.ReadPdfBookActivity;
import www.manager.leke.com.lekemanager.base.BaseFragmentActivity;
import www.manager.leke.com.lekemanager.bean.AliOssInfo;
import www.manager.leke.com.lekemanager.bean.BookOssBean;
import www.manager.leke.com.lekemanager.bean.OpenBookInfo;
import www.manager.leke.com.lekemanager.dialog.BaseDialogs;
import www.manager.leke.com.lekemanager.dialog.DialogUtils;
import www.manager.leke.com.lekemanager.dialog.ProgressDialog;
import www.manager.leke.com.lekemanager.http.HttpManager;
import www.manager.leke.com.lekemanager.listener.OssListener;
import www.manager.leke.com.lekemanager.net.DownRequest;
import www.manager.leke.com.lekemanager.net.OssInfoBean;
import www.manager.leke.com.lekemanager.utils.Contacts;
import www.manager.leke.com.lekemanager.utils.FileUtil;
import www.manager.leke.com.lekemanager.utils.FileUtils;
import www.manager.leke.com.lekemanager.utils.LogUtils;
import www.manager.leke.com.lekemanager.utils.SpUtils;
import www.manager.leke.com.lekemanager.utils.StringUtils;
import www.manager.leke.com.lekemanager.utils.SystemUtils;
import www.manager.leke.com.lekemanager.utils.UIUtils;

/**
 * 功能：打开阅读
 * 作者: YUAN_YE
 * 日期: 2019/7/1
 * 时间: 11:14
 */
public class OpenReaderUtils {
    private String TAG = OpenReaderUtils.class.getName();
    public static String EVENT_DELETE_BOOK = "event_delete_book";


    //下载完PDF 后的状态.
    private boolean mFlagOpenState = false;

    private BookOssBean mOss;
    private ProgressDialog mPrgDg;

    private OpenBookInfo mInfo;
    private DialogUtils mDialogUtils;

    public OpenReaderUtils(OpenBookInfo info) {
        mInfo = info;
    }

    public void openReader() {
        mFlagOpenState = true;
        LogUtils.e(TAG, "直接下载图书....");
        if (NetUtils.isWifiConnected()) {
            getOssMsg();
        } else {
            LogUtils.e(TAG, "当前 没有网络  判断 本地是否有图书");
            if (FileUtils.exists(FileUtil.getReaderPath() + mInfo.getBookId() + ".pdf")) {
                LogUtils.e(TAG, "本地有图书 跳转");
                jump();
            } else {
                LogUtils.e(TAG, "本地无图书 ，需要提示用户链接网络");
                UIUtils.showToastSafe(R.string.no_net);
            }
        }
    }


    /**
     * 获取图书OSS信息
     */
    private void getOssMsg() {
        mOss = null;
        getBookOss();
        LogUtils.e(TAG, "获取OSS 信息");
        //获取服务器接口

    }

    private void getBookOss() {
        HttpManager.getInstace().getBookOss(mInfo.getBookId(), SystemUtils.getDeviceModel()).subscribe(bookOssBean -> {
            if (bookOssBean != null) {
                mOss = bookOssBean;
                checkLocalMedia();
                Log.d(TAG, mOss.toString());
            } else {
                LogUtils.e(TAG, "BookOssBean = null");
                UIUtils.showToastSafe("该书已下架", Toast.LENGTH_LONG);
            }
        }, throwable -> {
            throwable.printStackTrace();
            LogUtils.e(TAG, " throwable");
            UIUtils.showToastSafe("发生错误未知", Toast.LENGTH_LONG);
        });

    }

    private void jump() {
        if (mInfo.isAutoOpean()) {
            if (null != BaseFragmentActivity.getForegroundActivity()) {
                Bundle extras = new Bundle();
                extras.putParcelable(ReadPdfBookActivity.class.getName(), mInfo);
                BaseFragmentActivity.getForegroundActivity().startIntentWithExtras(ReadPdfBookActivity.class, extras);
            }
        } else {
            String time = "";
            if (Contacts.READ.equals(mInfo.getType())) {
                time = mOss.getAudioConfigUrlInfo().getExpiration();
            } else {
                time = mOss.getDataPackUrlInfo().getExpiration();
            }
            if (mDialogUtils == null) {
                mDialogUtils = new DialogUtils(BaseFragmentActivity.getForegroundActivity());
            }
            mDialogUtils.setTitle("暂无更新");
            mDialogUtils.setTime("最后更新时间：" + time);
            mDialogUtils.setBtnName("知道了");
            mDialogUtils.showUpdateBook();
            mDialogUtils.setBookOnClick(new DialogUtils.BookOnClick() {
                @Override
                public void OnClickListener(BaseDialogs mDialogs) {
                    mDialogs.dismiss();
                }
            });
        }
    }


    /**
     * 下载图书
     */
    private void doFileBook() {
        AliOssInfo ossInfo = new AliOssInfo();
        ossInfo.setAccessKeyId(mOss.getDataPackUrlInfo().getAccessKeyId());
        ossInfo.setAccessKeySecret(mOss.getDataPackUrlInfo().getAccessKeySecret());
        ossInfo.setSecurityToken(mOss.getDataPackUrlInfo().getSecurityToken());
        ossInfo.setExpiration(mOss.getDataPackUrlInfo().getExpiration());
        ossInfo.setBucketName(mOss.getDataPackUrlInfo().getAtchBucket());
        ossInfo.setObjectKey(mOss.getDataPackUrlInfo().getAtchRemotePath());
        ossInfo.setEndpoint(Contacts.OSSPATH + mOss.getDataPackUrlInfo().getRegion() + Contacts.OSSASFFIX);

        ossInfo.setFileType("book");
        ossInfo.setSavePath(FileUtil.getReaderPath() + mInfo.getBookId() + ".pdf");

        OssManager.getInstance().asyDownFile(ossInfo, new BookOssListenerImp("图书"));


    }

    /**
     * 下载音频
     */
    private void doFileMedia() {
        AliOssInfo ossInfo = new AliOssInfo();
        ossInfo.setAccessKeyId(mOss.getAudioDataUrlInfo().getAccessKeyId());
        ossInfo.setAccessKeySecret(mOss.getAudioDataUrlInfo().getAccessKeySecret());
        ossInfo.setSecurityToken(mOss.getAudioDataUrlInfo().getSecurityToken());
        ossInfo.setExpiration(mOss.getAudioDataUrlInfo().getExpiration());
        ossInfo.setBucketName(mOss.getAudioDataUrlInfo().getAtchBucket());
        ossInfo.setObjectKey(mOss.getAudioDataUrlInfo().getAtchRemotePath());
        ossInfo.setEndpoint(Contacts.OSSPATH + mOss.getAudioDataUrlInfo().getRegion() + Contacts.OSSASFFIX);
        ossInfo.setFileType("audio");
        ossInfo.setSavePath(FileUtil.getAudioPath() + mInfo.getBookId() + ".zip");
        OssManager.getInstance().asyDownFile(ossInfo, new MediaOssListenerImp("音频"));
    }


    /**
     * 下载 配置文件
     */
    private void doFileConfig() {
        AliOssInfo ossInfo = new AliOssInfo();
        ossInfo.setAccessKeyId(mOss.getAudioConfigUrlInfo().getAccessKeyId());
        ossInfo.setAccessKeySecret(mOss.getAudioConfigUrlInfo().getAccessKeySecret());
        ossInfo.setSecurityToken(mOss.getAudioConfigUrlInfo().getSecurityToken());
        ossInfo.setExpiration(mOss.getAudioConfigUrlInfo().getExpiration());
        ossInfo.setBucketName(mOss.getAudioConfigUrlInfo().getAtchBucket());
        ossInfo.setObjectKey(mOss.getAudioConfigUrlInfo().getAtchRemotePath());
        ossInfo.setEndpoint(Contacts.OSSPATH + mOss.getAudioConfigUrlInfo().getRegion() + Contacts.OSSASFFIX);
        ossInfo.setFileType("configure");
        ossInfo.setSavePath(FileUtil.getConfigurePath() + mInfo.getBookId() + ".zip");
        OssManager.getInstance().asyDownFile(ossInfo, new ConfigOssListenerImp("配置"));

    }

    private class BookOssListenerImp implements OssListener {
        private String title;

        public BookOssListenerImp(String title) {
            this.title = title;
        }

        @Override
        public void onSuccess(int progress) {
            LogUtils.e(TAG, "onSuccess. .. BookOssListenerImp.......progress:.." + progress);
            if(mInfo.isProgress()) {
                setProgress(progress);
            }
        }

        @Override
        public void onFinish() {
            LogUtils.e(TAG, "onFinish.....BookOssListenerImp.....");
            SpUtils.putString(OpenReaderUtils.class.getName() + mInfo.getBookId() + "pdf" + "md5", mOss.getDataPackUrlInfo().getAtchMd5());
            SpUtils.putString("keys" + mInfo.getBookId() + "pdf", mOss.getDataPackUrlInfo().getAtchEncryptkey());
            LogUtils.e("袁野。。。save。。key。：：：" + mOss.getDataPackUrlInfo().getAtchEncryptkey());
                if (mPrgDg != null) {
                    mPrgDg.dismiss();
                }
                mPrgDg = null;
                UIUtils.showToastSafe("更新完成!");
            if (mFlagOpenState) {
                jump();
            }
        }

        @Override
        public void onFailure(String type) {
            LogUtils.e(TAG, "onFailure..........");
            if (mInfo.isProgress()) {
                if (mPrgDg != null) {
                    mPrgDg.dismiss();
                }
                mPrgDg = null;
            }
            UIUtils.showToastSafe("图书下载失败", Toast.LENGTH_LONG);
        }
    }

    private class MediaOssListenerImp implements OssListener {

        private String title;

        public MediaOssListenerImp(String title) {
            this.title = title;
        }

        @Override
        public void onSuccess(int progress) {
//            LogUtils.e(TAG, "onSuccess....MediaOssListenerImp....progress:.." + progress);
         //   setProgress(progress);
        }

        @Override
        public void onFinish() {
            LogUtils.e(TAG, "onFinish........MediaOssListenerImp..");
            SpUtils.putString(OpenReaderUtils.class.getName() + mInfo.getBookId() + "audio" + "md5", mOss.getAudioDataUrlInfo().getAtchMd5());
            LogUtils.e(TAG, "解压 音频文件 ..........");
            String srcPath = FileUtil.getAudioPath() + mInfo.getBookId() + ".zip";
            String destPath = FileUtil.getAudioPath() + mInfo.getBookId();
            try {
                ThreadManager.getSinglePool().execute(() -> {
                    boolean result = FileUtil.unZip3(srcPath, destPath);
                    LogUtils.e("解压结果。。。" + result);
                    UIUtils.post(() -> {

                        if (result) {
                            LogUtils.e(TAG, "解压成功  ，，，检测 配置文件.");
                            FileUtils.delFileOrFolder(srcPath);
                            checkLocalConfig();
                        } else {
                            FileUtils.delFileOrFolder(srcPath);
                            FileUtils.delFileOrFolder(destPath);
                            UIUtils.showToastSafe("下载失败", Toast.LENGTH_LONG);
                        }
                        if (mInfo.isProgress()) {
                            if (mPrgDg != null) {
                                mPrgDg.dismiss();
                            }
                            mPrgDg = null;
                        }

                    });
                });
            } catch (Exception e) {
                e.printStackTrace();
                FileUtils.delFileOrFolder(srcPath);
                FileUtils.delFileOrFolder(destPath);
                UIUtils.showToastSafe("下载失败", Toast.LENGTH_LONG);
                if (mInfo.isProgress()) {
                    if (mPrgDg != null) {
                        mPrgDg.dismiss();
                    }
                    mPrgDg = null;
                }
            }
        }

        @Override
        public void onFailure(String type) {
            LogUtils.e(TAG, "onFailure.....MediaOssListenerImp.....");
            UIUtils.showToastSafe("点读文件下载失败", Toast.LENGTH_LONG);
        }
    }

    private class ConfigOssListenerImp implements OssListener {

        private String title;

        public ConfigOssListenerImp(String title) {
            this.title = title;
        }

        @Override
        public void onSuccess(int progress) {
//            LogUtils.e(TAG, "onSuccess...ConfigOssListenerImp.....progress:.." + progress);
          //  setProgress(progress);
        }

        @Override
        public void onFinish() {
            LogUtils.e(TAG, "onFinish........ConfigOssListenerImp..");
            SpUtils.putString(OpenReaderUtils.class.getName() + mInfo.getBookId() + "config" + "md5", mOss.getAudioConfigUrlInfo().getAtchMd5());
            LogUtils.e(TAG, "解压 配置文件 ..........");
            String srcPath = FileUtil.getConfigurePath() + mInfo.getBookId() + ".zip";
            String destPath = FileUtil.getConfigurePath() + mInfo.getBookId();
            try {
                ThreadManager.getSinglePool().execute(() -> {
                    boolean result = FileUtil.unZip3(srcPath, destPath);
                    LogUtils.e("解压结果。。。" + result);
                    UIUtils.post(() -> {
                        if (result) {
                            LogUtils.e(TAG, "解压成功  ，，，检测图书.");
                            FileUtils.delFileOrFolder(srcPath);
                            checkLocalBook();
                        } else {
                            FileUtils.delFileOrFolder(srcPath);
                            FileUtils.delFileOrFolder(destPath);
                            UIUtils.showToastSafe("下载失败", Toast.LENGTH_LONG);
                        }


                    });

                });
            } catch (Exception e) {
                e.printStackTrace();
                FileUtils.delFileOrFolder(srcPath);
                FileUtils.delFileOrFolder(destPath);
                UIUtils.showToastSafe("下载失败", Toast.LENGTH_LONG);
            }
        }

        @Override
        public void onFailure(String type) {
            LogUtils.e(TAG, "onFailure........ConfigOssListenerImp..");
            UIUtils.showToastSafe("点读文件下载失败", Toast.LENGTH_LONG);
        }
    }

    private synchronized void setProgress(int progress) {
        if (mPrgDg == null) {
            mPrgDg = new ProgressDialog(BaseFragmentActivity.getContext()) {
                @Override
                public void cancel() {
                    OssManager.getInstance().cancel(String.valueOf(mInfo.getBookId()));
                }
            };
        }
        if (!mPrgDg.isShowing()) {
            mPrgDg.show();
        }


        mPrgDg.setProgress(progress);

    }


    /***
     * 检测图书是否需要下载
     */
    private void checkLocalBook() {
        if (mOss.getDataPackUrlInfo() == null) {
            LogUtils.e(TAG, "getDataPackUrlInfo = null");
            UIUtils.showToastSafe("该书已下架", Toast.LENGTH_LONG);
        } else {
            // 准备下载图书
            LogUtils.e(TAG, "准备下载图书");
            if (FileUtils.exists(FileUtil.getReaderPath() + mInfo.getBookId() + ".pdf")) {
                LogUtils.e(TAG, "本地有书 ,校验");
                LogUtils.e(TAG, "本地有书 ,校验:" + mOss.getDataPackUrlInfo().getAtchMd5());
                LogUtils.e(TAG, "本地有书 ,校验:" + SpUtils.getString(OpenReaderUtils.class.getName() + mInfo.getBookId() + "pdf" + "md5"));
                String key = SpUtils.getString("keys" + mInfo.getBookId() + "pdf");
                if (StringUtils.isEmpty(key)) {
                    LogUtils.e(TAG, "本地有书 ,没有秘钥需要更新)");
                    //更新秘钥
                    SpUtils.putString("keys" + mInfo.getBookId() + "pdf", mOss.getDataPackUrlInfo().getAtchEncryptkey());
                } else {
                    LogUtils.e(TAG, "本地有书 有钥匙 对比秘钥");
                    if (!key.equalsIgnoreCase(mOss.getDataPackUrlInfo().getAtchEncryptkey())) {
                        LogUtils.e(TAG, "本地有书 服务器秘钥和本地秘钥不相同 删除图书下载");
                        FileUtils.delFileOrFolder(FileUtil.getReaderPath() + mInfo.getBookId() + ".pdf");
                        //如果有更新弹dialog
                        doFileBooks();

                        return;
                    }
                }
                if (!StringUtils.isEmpty(mOss.getDataPackUrlInfo().getAtchMd5())) {
                    LogUtils.e(TAG, "服务器有md5值 匹配本地md5");
                    if (!mOss.getDataPackUrlInfo().getAtchMd5().equalsIgnoreCase(SpUtils.getString(OpenReaderUtils.class.getName() + mInfo.getBookId() + "pdf" + "md5"))) {
                        LogUtils.e(TAG, "本地有书 ,校验不相同 需要下载PDF");
                        FileUtils.delFileOrFolder(FileUtil.getReaderPath() + mInfo.getBookId() + ".pdf");
                        doFileBooks();
                    } else {
                        LogUtils.e(TAG, "本地有书 ,校验相同 不需要下载PDF");
                        if (mFlagOpenState) {
                            LogUtils.e(TAG, "本地有书 ,校验相同 进度阅读UI");
                            jump();
                        }
                    }
                } else {
                    LogUtils.e(TAG, "服务器md5值 为空");
                    if (mFlagOpenState) {
                        jump();
                    }
                }
            } else {
                doFileBook();
            }
        }
    }

    private void doFileBooks() {
        if (mDialogUtils == null) {
            mDialogUtils = new DialogUtils(BaseFragmentActivity.getForegroundActivity());
        }
        mDialogUtils.setTitle("PDF更新，是否下载新版本？");
        mDialogUtils.setBtnName("点击更新");
        mDialogUtils.setTime(mOss.getDataPackUrlInfo().getExpiration());
        mDialogUtils.showUpdateBook();
        mDialogUtils.setBookOnClick(new DialogUtils.BookOnClick() {
            @Override
            public void OnClickListener(BaseDialogs mDialogs) {
                mDialogs.dismiss();
                doFileBook();
            }
        });
    }

    /***
     * 检测 音频文件
     */
    private void checkLocalMedia() {
        //检测服务器音频配置文件
        if (null == mOss.getAudioConfigUrlInfo() || null == mOss.getAudioDataUrlInfo()) {
            LogUtils.e(TAG, "该书没有点读 文件getAudioConfigUrlInfo  ， getAudioDataUrlInfo= null");
            if (Contacts.READ.equals(mInfo.getType())) {
                UIUtils.showToastSafe("该书没有点读文件！");
            }else {
                checkLocalBook();
            }
        } else {
            LogUtils.e(TAG, "该书支持 点读 检测点读文件");
            LogUtils.e(TAG, "执行检测 音频文件");
            if (FileUtils.exists(FileUtil.getAudioPath() + mInfo.getBookId() + "/")) {
                LogUtils.e(TAG, "本地音频文件存在 ");
                if (!StringUtils.isEmpty(mOss.getAudioConfigUrlInfo().getAtchMd5())) {
                    LogUtils.e(TAG, "服务器有音频文件MD5值  ，开始校验。。。本地音频文件MD5值");

                    if (!mOss.getAudioDataUrlInfo().getAtchMd5().equalsIgnoreCase(SpUtils.getString(OpenReaderUtils.class.getName() + mInfo.getBookId() + "audio" + "md5"))) {
                        LogUtils.e(TAG, "服务器MD5值 和本地 音频文件MD5值不相同 需要重新下载音频文件");
                        LogUtils.e(TAG, "删除本地音频文件");
                        FileUtils.delFileOrFolder(FileUtil.getAudioPath() + mInfo.getBookId() + "/");
                        LogUtils.e(TAG, "下载音频文件 ");
                        doFileMedias();
                    } else {
                        LogUtils.e(TAG, "服务器MD5值 和本地 音频文件MD5值相同 继续校验");
                        LogUtils.e(TAG, "执行检测配置文件 ");
                        checkLocalConfig();
                    }
                } else {
                    LogUtils.e(TAG, "服务器无MD5值 ");
                    LogUtils.e(TAG, "执行检测配置文件 ");
                    //检测配置文件
                    checkLocalConfig();
                }
            } else {
                LogUtils.e(TAG, "下载音频文件 ");
                doFileMedia();
            }
        }
    }

    /**
     * 检测 配置文件
     */
    private void checkLocalConfig() {
        LogUtils.e(TAG, "该书支持 点读 检测点读文件");
        LogUtils.e(TAG, "执行检测 本地配置文件");


        if (FileUtils.exists(FileUtil.getConfigurePath() + mInfo.getBookId() + "/")) {
            LogUtils.e(TAG, "本地配置文件存在 ");
            if (!StringUtils.isEmpty(mOss.getAudioConfigUrlInfo().getAtchMd5())) {
                LogUtils.e(TAG, "服务器有配置文件MD5值  ，开始校验。。。本地配置文件MD5值");

                if (!mOss.getAudioConfigUrlInfo().getAtchMd5().equalsIgnoreCase(SpUtils.getString(OpenReaderUtils.class.getName() + mInfo.getBookId() + "config" + "md5"))) {
                    LogUtils.e(TAG, "服务器MD5值 和本地 配置文件MD5值不相同 需要重新下载配置文件");
                    LogUtils.e(TAG, "删除本地配置文件");
                    FileUtils.delFileOrFolder(FileUtil.getConfigurePath() + mInfo.getBookId() + "/");
                    LogUtils.e(TAG, "下载配置文件 ");
                    doFileConfig();

                } else {
                    LogUtils.e(TAG, "服务器MD5值 和本地 配置文件MD5值相同 继续校验");
                    LogUtils.e(TAG, "执行检测 图书 ");
                    checkLocalBook();
                }
            } else {
                LogUtils.e(TAG, "服务器无MD5值 ");
                LogUtils.e(TAG, "执行检测图书文件 ");
                checkLocalBook();
            }
        } else {
            LogUtils.e(TAG, "下载配置文件 ");
            doFileConfig();
        }
    }

    private void doFileMedias() {
        if (mDialogUtils == null)
            mDialogUtils = new DialogUtils(BaseFragmentActivity.getForegroundActivity());
        mDialogUtils.setTitle("点读文件有更新，是否更新新版本？");
        mDialogUtils.setTime(mOss.getAudioConfigUrlInfo().getExpiration());
        mDialogUtils.setBtnName("点击更新");
        mDialogUtils.showUpdateBook();

        mDialogUtils.setBookOnClick(new DialogUtils.BookOnClick() {
            @Override
            public void OnClickListener(BaseDialogs mDialogs) {
                mDialogs.dismiss();
                doFileMedia();

            }
        });
    }
}







