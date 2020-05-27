package www.manager.leke.com.lekemanager.read;

import android.content.Context;
import android.graphics.RectF;

import com.onyx.android.sdk.common.request.BaseCallback;
import com.onyx.android.sdk.common.request.BaseRequest;
import com.onyx.android.sdk.reader.host.options.BaseOptions;
import com.onyx.android.sdk.reader.host.request.CloseRequest;
import com.onyx.android.sdk.reader.host.request.CreateViewRequest;
import com.onyx.android.sdk.reader.host.request.GammaCorrectionRequest;
import com.onyx.android.sdk.reader.host.request.GetTableOfContentRequest;
import com.onyx.android.sdk.reader.host.request.GotoPageRequest;
import com.onyx.android.sdk.reader.host.request.OpenRequest;
import com.onyx.android.sdk.reader.host.request.ScaleByRectRequest;
import com.onyx.android.sdk.reader.host.request.ScaleToPageCropRequest;
import com.onyx.android.sdk.reader.host.wrapper.Reader;
import com.onyx.android.sdk.reader.utils.PagePositionUtils;
import java.util.Collections;
import www.manager.leke.com.lekemanager.reading.ReadingBean;
import www.manager.leke.com.lekemanager.utils.DrmCertificateFactory;
import www.manager.leke.com.lekemanager.utils.FileUtil;
import www.manager.leke.com.lekemanager.utils.FileUtils;
import www.manager.leke.com.lekemanager.utils.GsonUitls;
import www.manager.leke.com.lekemanager.utils.LogUtils;
import www.manager.leke.com.lekemanager.utils.SpUtils;
import www.manager.leke.com.lekemanager.utils.StringUtils;

/**
 * 功能：解析PDF
 * 作者: YUAN_YE
 * 日期: 2019/4/23
 * 时间: 9:55
 */

public class ReaderPresenter implements ReaderContract.ReaderPresenter {
    private final String TAG = ReaderPresenter.class.getName();

    private ReaderContract.ReaderView readerView;

    private Reader reader;

    // pdf总页码
    private int mTotals;

    //当前页码
    private int mCutter;

    private String mPdfId;

    // 是否需要裁剪和放大pdf
    private boolean mIsEnlargeAndTailoring = false;

    // 是否需要对pdf设置Gama值
    private boolean mIsGama = false;

    /**
     * 加载题目PDF大小
     */
    private int mExerciesW = -1;
    private int mExerciesH = -1;

    public ReaderPresenter(ReaderContract.ReaderView readerView) {
        this.readerView = readerView;
    }

    @Override
    public void openDocument(String documentPath, String booId) {
        mPdfId = booId;
        //解密PDF文件
        DrmCertificateFactory factory = new DrmCertificateFactory(readerView.getViewContext());
//        if (!StringUtils.isEmpty(booId)) {
//
//            String keys = DataCacheUtils.getString(UIUtils.getContext(), "pdf_keys");
//            if (!StringUtils.isEmpty(keys) && keys.contains(booId)) {
//                try {
//                    JSONObject object = new JSONObject(keys);
//                    String key = object.getString(booId);
//                    //设置秘钥
//                    factory.setKey(key);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        SpUtils.putString("keys"+ mInfo.getBookId() + "pdf", mDataPackUrlInfo.getAtchEncryptKey());
        String key = SpUtils.getString("keys" + booId + "pdf");
        LogUtils.e("袁野。。。DRM。。key。：：：" + key);
        if (!StringUtils.isEmpty(key)) {
            factory.setKey(key);
        }
        /***
         *  解析PDF
         */
        OpenRequest openRequest = new OpenRequest(documentPath, new BaseOptions(), factory, false);
        getReader().submitRequest(readerView.getViewContext(), openRequest, new BaseCallback() {
            @Override
            public void done(BaseRequest baseRequest, Throwable throwable) {
                if (throwable == null) {
                    if (readerView.getContentView() != null) {
                        setDocumentViewRect(readerView.getContentView().getWidth(), readerView.getContentView().getHeight());
                        System.out.println("wwwwwwwwwwwwww.mReaderView.getContentView().getWidth()==" + readerView.getContentView().getWidth());
                        System.out.println("y .. mReaderView.getContentView().getHeight()==" + readerView.getContentView().getHeight());
                    } else {
                        readerView.showThrowable(throwable);
                    }
                } else {
                    LogUtils.e(TAG, "openRequest:" + throwable.toString());
                    readerView.showThrowable(throwable);
                }
            }
        });
    }

    @Override
    public void setDocumentViewRect(int width, int height) {

        if (mExerciesH != -1 || mExerciesW != -1) {
            width = mExerciesW;
            height = mExerciesH;
            LogUtils.e(TAG, "加载题目 ，PDF 设置大小mExerciesW ，mExerciesH .." + mExerciesW + "," + mExerciesH);
        }

        CreateViewRequest createViewRequest = new CreateViewRequest(width, height);
        getReader().submitRequest(getContext(), createViewRequest, new BaseCallback() {
            @Override
            public void done(BaseRequest baseRequest, Throwable throwable) {
                if (throwable == null) {
                    mTotals = getReader().getNavigator().getTotalPage();
                    readerView.openDocumentFinsh();
                } else {
                    readerView.showThrowable(throwable);
                }
            }
        });
    }

    @Override
    public void gotoPage(final int page) {
        mCutter = page;
        LogUtils.e(TAG, "gotoPage:" + page);

        GotoPageRequest gotoPageRequest = new GotoPageRequest(page);
        getReader().submitRequest(getContext(), gotoPageRequest, new BaseCallback() {
            @Override
            public void done(BaseRequest baseRequest, Throwable throwable) {

                if (throwable == null) {
                    //进行裁边和放大处理
                    if (mIsEnlargeAndTailoring) {
                        requestEnlargeAndTailoring(-0.02f);
                    } else if (mIsGama) {
                        requestGama();
                    } else {
                        readerView.updatePage(page, getReader().getViewportBitmap().getBitmap(), getReadingBean());
                    }
                } else {
                    LogUtils.e(TAG, "gotoPageRequest:" + throwable.toString());
                    readerView.showThrowable(throwable);
                }
            }
        });
    }

    private void requestEnlargeAndTailoring(final float delta) {
        // 获取当前页码的 文档结构大小
        final ScaleToPageCropRequest request = new ScaleToPageCropRequest((mCutter + ""));
        getReader().submitRequest(readerView.getViewContext(), request, new BaseCallback() {
            @Override
            public void done(BaseRequest baseRequest, Throwable throwable) {
                if (throwable == null) {
                    RectF viewportInDoc = request.getReaderViewInfo().viewportInDoc;
                    final RectF rect = new RectF();
                    float offset = viewportInDoc.width() * delta;
                    rect.set(viewportInDoc.left + offset,
                            viewportInDoc.top + offset,
                            viewportInDoc.right - offset,
                            viewportInDoc.bottom - offset);


                    /***
                     * 进行自适应和缩放
                     */
                    final ScaleByRectRequest request = new ScaleByRectRequest(mCutter + "", rect);
                    getReader().submitRequest(getContext(), request, new BaseCallback() {
                        @Override
                        public void done(BaseRequest baseRequest, Throwable throwable) {

                            if (throwable == null) {
                                if (mIsGama) {
                                    requestGama();
                                } else {
                                    readerView.updatePage(mCutter, getReader().getViewportBitmap().getBitmap(), getReadingBean());
                                }
                            } else {
                                LogUtils.e(TAG, "ScaleByRectRequest:" + throwable.toString());
                                readerView.showThrowable(throwable);
                            }
                        }
                    });
                } else {
                    LogUtils.e(TAG, "ScaleToPageCropRequest:" + throwable.toString());
                    readerView.showThrowable(throwable);
                }
            }
        });
    }


    private void requestGama() {
        int defaultGamma = 100; // gamma correction ranges between [100, 200], 100 means no gamma correction, 200 is max gamma correction
        int globalGamma = defaultGamma; // globalGamma is not used yet
        int imageGamma = 200; // imageGamma works only when textGamma is not set
        int textGamma = 170; // text gamma works for PDF texts
        int glyphEmbolden = 1; // ranges from [0, 5], 0 means no embolden, 5 is max embolden
        GammaCorrectionRequest gammaRequest = new GammaCorrectionRequest(defaultGamma, globalGamma, textGamma, glyphEmbolden);
        getReader().submitRequest(getContext(), gammaRequest, new BaseCallback() {
            @Override
            public void done(BaseRequest baseRequest, Throwable throwable) {
                if (throwable == null) {
                    mIsGama = false;
                    readerView.updatePage(mCutter, getReader().getViewportBitmap().getBitmap(), getReadingBean());
                    LogUtils.e("添加gama值");
                } else {
                    LogUtils.e(TAG, "gammaRequest:" + throwable.toString());
                    readerView.showThrowable(throwable);
                }
            }
        });
    }


    @Override
    public Reader getReader() {
        if (reader == null) {
            reader = new Reader();
        }
        return reader;
    }

    @Override
    public void getDirectory() {
        final GetTableOfContentRequest request = new GetTableOfContentRequest();
        getReader().submitRequest(getContext(), request, new BaseCallback() {
            @Override
            public void done(BaseRequest baseRequest, Throwable throwable) {
                if (throwable == null) {
                    readerView.updateDirectory(request.getReaderUserDataInfo().getTableOfContent());
                } else {
                    LogUtils.e(TAG, "GetTableOfContentRequest:" + throwable.toString());
                    readerView.showThrowable(throwable);
                }
            }
        });
    }

    @Override
    public void close() {
        final CloseRequest closeRequest = new CloseRequest();
        if (reader != null) {
            reader.submitRequest(getContext(), closeRequest, new BaseCallback() {
                @Override
                public void done(BaseRequest baseRequest, Throwable throwable) {
                    if (throwable == null) {
                        //close success
                        LogUtils.e(TAG, "close success:");
                    }
                }
            });
        }
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    private Context getContext() {
        return readerView.getViewContext();
    }

    /**
     * 获取  PDF 总页码
     *
     * @return 总页码
     */
    public int getTotals() {
        return mTotals;
    }

    public void setIsEnlargeAndTailoring(boolean isEnlargeAndTailoring) {
        this.mIsEnlargeAndTailoring = isEnlargeAndTailoring;
    }

    public void setIsGama(boolean isGama) {
        this.mIsGama = isGama;
    }


    public void setExerciesW(int exerciesW) {
        this.mExerciesW = exerciesW;
    }

    public void setExerciesH(int exerciesH) {
        this.mExerciesH = exerciesH;
    }


    /**
     * 解析 点读文件
     *
     * @return 返回解析后的配置文件信息
     */
    public ReadingBean getReadingBean() {
        ReadingBean bean = null;

        if (FileUtil.exists(FileUtil.getConfigurePath() + mPdfId + "/") && FileUtils.exists(FileUtil.getAudioPath() + mPdfId + "/")) {
            LogUtils.e(TAG, "该书 mPdfId:" + mPdfId + ":本地有点读文件包  ,执行 判断当前 页码是否有对应的 点读配置文件");
            //文件角标1开始 ，pdf角标0开始
            int index = mCutter + 1;
            String filePath = FileUtil.getConfigurePath() + mPdfId + "/" + index + ".txt";
            if (FileUtils.exists(filePath)) {
                LogUtils.e(TAG, "找到当前页码对应的点读配置文件");
                //解析点读文件 返回上层接口 回调 使用
                String config = FileUtils.readerFile(filePath);
                if (!StringUtils.isEmpty(config)) {
                    LogUtils.e("config  ..config: ==" + config);
                    bean = GsonUitls.fromJson(config, ReadingBean.class);

                    if (bean.getVoiceInfos() != null && bean.getVoiceInfos().size() > 0) {
                        LogUtils.e(TAG, "排序");
                        Collections.sort(bean.getVoiceInfos());
                    } else {
                        LogUtils.e(TAG, " 解析实体bean集合存在问题");
                    }
                } else {
                    LogUtils.e(TAG, "当前页码的配置文件 出现问题。。。。。");
                }
            } else {
                LogUtils.e(TAG, "当前页码 没有点读的配置文件");
            }

        } else {
            LogUtils.e(TAG, "mPdfId:" + mPdfId + "没有点读文件夹");
        }
        return bean;
    }

    public int getPageTextCounts() throws Exception {
        try {
//            GetPageTextRequest mPageTextRequest = new GetPageTextRequest(mCutter);
//            LogUtils.e("GetPageTextRequest  mCutter==" +mCutter);
//            mPageTextRequest.execute(getReader());
            if (reader == null) {
                return 0;
            }
            String text = getReader().getDocument().getPageText(PagePositionUtils.fromPageNumber(mCutter));
            return text.length();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}