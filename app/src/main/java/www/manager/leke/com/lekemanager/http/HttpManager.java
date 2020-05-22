package www.manager.leke.com.lekemanager.http;


import android.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import www.manager.leke.com.lekemanager.base.BaseResult;
import www.manager.leke.com.lekemanager.bean.BookMessageDetail;
import www.manager.leke.com.lekemanager.bean.BookOssBean;
import www.manager.leke.com.lekemanager.bean.LoginBean;
import www.manager.leke.com.lekemanager.bean.MainBookMessageBean;
import www.manager.leke.com.lekemanager.bean.QuestionListBean;
import www.manager.leke.com.lekemanager.bean.SumbitBookStatus;
import www.manager.leke.com.lekemanager.bean.SumbitQuestion;
import www.manager.leke.com.lekemanager.bean.SumbitaudioBook;
import www.manager.leke.com.lekemanager.configuration.HtppConfiguration;
import www.manager.leke.com.lekemanager.dialog.LoadingProgressDialog;
import www.manager.leke.com.lekemanager.utils.LogUtils;
import www.manager.leke.com.lekemanager.utils.SystemUtils;

public final class HttpManager {

    private static HttpManager mInstace;
    private ServerApi mServerApi;
    //LOG
    private MyHttpLoggingInterceptor mLogInterceptor;
    public static LoadingProgressDialog loadingProgressDialog;

    private HttpManager() {
        initLog();
        initOkHttp();
    }

    private void initLog() {
        mLogInterceptor = new MyHttpLoggingInterceptor();
//        if (BuildConfig.DEBUG) {
        mLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        } else {
//            mLogInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
//        }
    }

    private void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        RetryIntercepter retryIntercepter = new RetryIntercepter(HtppConfiguration.HTTP_RETRY);
        builder.readTimeout(HtppConfiguration.HTTP_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(HtppConfiguration.HTTP_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(HtppConfiguration.HTTP_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(mLogInterceptor).addInterceptor(retryIntercepter)
                .retryOnConnectionFailure(true);

        OkHttpClient mClient = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(mClient)
                .baseUrl(HtppConfiguration.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mServerApi = retrofit.create(ServerApi.class);
    }

    public static synchronized HttpManager getInstace() {

        if (mInstace == null) {
            mInstace = new HttpManager();
        }
        return mInstace;
    }

    public ServerApi getServerApi() {
        return mServerApi;
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * 下载pdf
     *
     * @param bookId
     * @param deviceModel
     * @return
     */
    public Observable<BookOssBean> getBookOss(int bookId, String deviceModel) {
        LogUtils.e("271 【51005】【已完成】管理端取得图书OSS下载地址 ",
                "bookId:" + bookId + ","
                        + "deviceModel:" + deviceModel);

        return getServerApi().getBookOss(bookId, deviceModel)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxLekeResultHelper.handleResult());
    }

    /**
     * 登录接口
     *
     * @param
     * @return
     */
    public Observable<LoginBean> getLogin(String account, String password) {
        LogUtils.e("【00001】【已完成】用户登录 ",
                "bookId:" + account + ","
                        + "deviceModel:" + password);
        return getServerApi().getLogin(account, password)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxLekeResultHelper.handleResult());
    }

    /**
     * 获取信息接口
     *
     * @param
     * @return
     */
    public Observable<LoginBean> getInformation() {
        LogUtils.e("【51001】 【已完成】取得当前登录用户的个人信息");
        return getServerApi().getInfoMation()
                .compose(RxSchedulersHelper.io_main())
                .compose(RxLekeResultHelper.handleResult());
    }

    /**
     * 获取首页图书列表接口
     *
     * @param
     * @return
     */
    public Observable<Pair<Integer, List<MainBookMessageBean>>> getMainBookMessage(String booStatus) {

        LogUtils.e("【61001】【对应管理pad端有更新】取得个人待处理图书列表 "
                + "bookStatus" + booStatus);
        return getServerApi().getMainBookMessage(booStatus, SystemUtils.getDeviceModel())
                .compose(RxSchedulersHelper.io_main())
                .compose(RxLekeResultHelper.handleResult_new());

    }

    /**
     * 提交图书，修改状态
     *
     * @param
     * @return
     */
    public Observable<Object> getUpdateStatus(SumbitBookStatus sumbitBookStatus) {
        LogUtils.e("【61012】【已完成】更新图书状态 "
                + "sumbitBookStatus" + sumbitBookStatus.toString());
        return getServerApi().getUpdateStatus(sumbitBookStatus)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxLekeResultHelper.handleResult());
    }

    /**
     * 【62001】【对应管理pad端有更新】取得个人待处理点读列表
     * <p>
     * * @return
     */
    public Observable<Pair<Integer, List<MainBookMessageBean>>> getBookAudio(String audioStatus) {
        LogUtils.e("【62001】【对应管理pad端有更新】取得个人待处理点读列表"
                + "audioStatus" + audioStatus);
        return getServerApi().getBookAudio(audioStatus, SystemUtils.getDeviceModel())
                .compose(RxSchedulersHelper.io_main())
                .compose(RxLekeResultHelper.handleResult_new());
    }

    /**
     * 【63001】【对应管理pad端有更新】取得个人待处理题库（练习册）列表
     *
     * @param
     * @return
     */
    public Observable<Pair<Integer, List<MainBookMessageBean>>> getQuestionBank(Integer bookOptor) {
        LogUtils.e("【63001】【对应管理pad端有更新】取得个人待处理题库（练习册）列表 "
                + "bookOptor" + bookOptor);
        return getServerApi().getQuestionBank(bookOptor, SystemUtils.getDeviceModel())
                .compose(RxSchedulersHelper.io_main())
                .compose(RxLekeResultHelper.handleResult_new());

    }

    /***
     *
     * @param
     * @return
     * 【62013】【已完成】更新点读状态
     */
    public Observable<Object> getAudioStatus(SumbitaudioBook sumbitaudioBook) {
        LogUtils.e("【62013】【已完成】更新点读状态  "
                + "sumbitaudioBook" + sumbitaudioBook.toString());
        return getServerApi().getSetReadbookStatus(sumbitaudioBook)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxLekeResultHelper.handleResult_new());
    }

    /**
     * 【63013】【已完成】题库（练习册）编辑提交
     *
     * @param
     * @return
     */
    public Observable<Object> getQuestionSumbit(int qBankBookId, String entryLogInfo) {
        LogUtils.e("【63013】【已完成】题库（练习册）编辑提交  "
                + "qBankBookId" + qBankBookId + ""
                + "entryLogInfo" + entryLogInfo);
        return getServerApi().getQuestionbankSubmit(qBankBookId, entryLogInfo)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxLekeResultHelper.handleResult());
    }

    /***
     *【63014】【已完成】题库（练习册）校对提交
     * @param
     * @return
     */
    public Observable<Object> getQuestionInspect(SumbitQuestion sumbitQuestion) {
        LogUtils.e("【63014】【已完成】题库（练习册）校对提交 "
                + "sumbitQuestion" + sumbitQuestion.toString() + ""
        );
        return getServerApi().getQuestionInspect(sumbitQuestion)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxLekeResultHelper.handleResult());
    }

    /**
     * 【63015】【已完成】题库（练习册）审核提交
     *
     * @param sumbitQuestion
     * @return
     */
    public Observable<Object> getQuestionApprove(SumbitQuestion sumbitQuestion) {
        LogUtils.e("【63015】【已完成】题库（练习册）审核提交 "
                + "sumbitQuestion" + sumbitQuestion.toString() + "");
        return getServerApi().getQuestionApprove(sumbitQuestion)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxLekeResultHelper.handleResult());
    }

    /**
     * 【61006】【已完成】取得指定ID的图书信息
     *
     * @return
     */
    public Observable<BookMessageDetail> getBookmessageDetail(int bookId) {
        LogUtils.e("【61006】【已完成】取得指定ID的图书信息  "
                + "sumbitQuestion" + bookId + "");
        return getServerApi().getBookMesaageDetail(bookId)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxLekeResultHelper.handleResult());
    }

    /**
     * 【51002】 【已完成】更新用户密码
     *
     * @param oldPwassword
     * @param newPassWord
     * @return
     */
    public Observable<Object> getRevisePwd(String oldPwassword, String newPassWord) {
        LogUtils.e(" 【51002】 【已完成】更新用户密码  "
                + "oldPwassword" + oldPwassword + "" + "newPassWord" + newPassWord);
        return getServerApi().getRevisePwd(oldPwassword, newPassWord)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxLekeResultHelper.handleResult());
    }

    /**
     *  【63007】【已完成】取得题库（练习册）章节题目数
     * @param qBankBookId
     * @param qStatusCode
     * @return
     */
    public Observable<HashMap<String, Integer>> getQuestionAmount(Integer qBankBookId, String qStatusCode) {
        LogUtils.e("63007】【已完成】取得题库（练习册）章节题目数" +
                "qBankBookId" + qBankBookId + "qStatusCode" + qStatusCode);
        return getServerApi().getQuestionAmoumt(qBankBookId, qStatusCode)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxLekeResultHelper.handleResult());
    }

    /**
     *   【63010】【已完成】取得题库（练习册）题目列表
     * @param qBankBookId
     * @param chapterId
     * @param qStatusCode
     * @return
     */
    public Observable<Pair<Integer, BaseResult<List<QuestionListBean>>>> getQuestionList(int qBankBookId , int chapterId, String qStatusCode){
        LogUtils.e(" 【63010】【已完成】取得题库（练习册）题目列表" +
                "qBankBookId" + qBankBookId + "qStatusCode" + qStatusCode+"chapterId"+chapterId);
        return getServerApi().getQuestionBankList(qBankBookId,chapterId,qStatusCode)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxLekeResultHelper.handleResult_new());

    }


}