package www.manager.leke.com.lekemanager.http;


import android.util.Pair;

import java.util.List;
import java.util.concurrent.TimeUnit;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import www.manager.leke.com.lekemanager.base.BaseResult;
import www.manager.leke.com.lekemanager.bean.BookOssBean;
import www.manager.leke.com.lekemanager.bean.LoginBean;
import www.manager.leke.com.lekemanager.bean.MainBookMessageBean;
import www.manager.leke.com.lekemanager.bean.SumbitBookStatus;
import www.manager.leke.com.lekemanager.configuration.HtppConfiguration;
import www.manager.leke.com.lekemanager.dialog.LoadingProgressDialog;
import www.manager.leke.com.lekemanager.net.DownRequest;
import www.manager.leke.com.lekemanager.net.OssInfoBean;
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
        LogUtils.e("271 【12009】【已完成】获取图书 OSS下载地址 ",
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
    public Observable<Pair<Integer, List<MainBookMessageBean>>> getQuestionBank(int bookOptor) {
        LogUtils.e("【63001】【对应管理pad端有更新】取得个人待处理题库（练习册）列表 "
                + "bookOptor" + bookOptor);
        return getServerApi().getQuestionBank(bookOptor, SystemUtils.getDeviceModel())
                .compose(RxSchedulersHelper.io_main())
                .compose(RxLekeResultHelper.handleResult_new());

    }

    public Observable<List<OssInfoBean>> downloadBook2(DownRequest request) {
        LogUtils.e("FH", "!!!!!调用ServerApi下载图书:downloadBook");
        return getServerApi().downloadBook2(request)
                .compose(RxSchedulersHelper.<BaseResult<List<OssInfoBean>>>io_main())
                .compose(RxLekeResultHelper.<List<OssInfoBean>>handleResult());
    }


//
//
//    public Observable<List<BookInfoBean>> getBookShelf(BookShelfRequest req) {
//        return getServerApi().getBookShelf(req)
//                .compose(RxSchedulersHelper.<BaseResult<List<BookInfoBean>>>io_main())
//                .compose(RxResultHelper.<List<BookInfoBean>>handleResult());
//    }
//
//
//    /**
//     * 获取版本号
//     */
//    public Observable<Version> getVersion() {
//        LogUtils.e("!!!!!调用ServerApi获取版本号:getVersion");
//        return getServerApi().getVersion()
//                .compose(RxSchedulersHelper.<BaseResult<Version>>io_main())
//                .compose(RxResultHelper.<Version>handleResult());
//    }
//
//    public Observable<List<BookInfo>> queryBook(String bookId) {
//        LogUtils.e("FH", "!!!!!调用ServerApi获取图书信息:queryBook");
//        return getServerApi().queryBook(bookId)
//                .compose(RxSchedulersHelper.<BaseResult<List<BookInfo>>>io_main())
//                .compose(RxResultHelper.<List<BookInfo>>handleResult());
//    }
//
//    public Observable<List<BookInfo>> queryBookTitle(String bookCategoryMatch, String bookItemStatus) {
//        LogUtils.e("FH", "!!!!!调用ServerApi获取图书信息:queryBook");
//        return getServerApi().queryBookTitle(bookCategoryMatch, bookItemStatus)
//                .compose(RxSchedulersHelper.<BaseResult<List<BookInfo>>>io_main())
//                .compose(RxResultHelper.<List<BookInfo>>handleResult());
//    }
//
//    public Observable<List<OriginQuestionItem>> queryItem(String bookId, String cursorId) {
//        LogUtils.e("FH", "!!!!!调用ServerApi获取图书信息:queryBook");
//        return getServerApi().queryItem(bookId, cursorId)
//                .compose(RxSchedulersHelper.<BaseResult<List<OriginQuestionItem>>>io_main())
//                .compose(RxResultHelper.<List<OriginQuestionItem>>handleResult());
//    }
//}
}