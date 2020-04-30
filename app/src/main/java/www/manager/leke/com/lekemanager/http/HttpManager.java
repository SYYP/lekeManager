package www.manager.leke.com.lekemanager.http;

import java.util.List;
import java.util.concurrent.TimeUnit;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import www.manager.leke.com.lekemanager.configuration.HtppConfiguration;

public final class HttpManager {

    private static HttpManager mInstace;
    private ServerApi mServerApi;
    //LOG
    private MyHttpLoggingInterceptor mLogInterceptor;

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
//
//    public Observable<List<Student>> login(NewLoginReq req) {
//        return getServerApi().login(req)
//                .compose(RxSchedulersHelper.<BaseResult<List<Student>>>io_main())
//                .compose(RxResultHelper.<List<Student>>handleResult());
//    }
//
//    public Observable<List<OssInfoBean>> downloadBook(String bookId, String atchTypeCode) {
//        return getServerApi().downloadBook(bookId, atchTypeCode)
//                .compose(RxSchedulersHelper.<BaseResult<List<OssInfoBean>>>io_main())
//                .compose(RxResultHelper.<List<OssInfoBean>>handleResult());
//    }
//
//
//    public Observable<List<OssInfoBean>> downloadBook2(DownRequest request) {
//        LogUtils.e("FH", "!!!!!调用ServerApi下载图书:downloadBook");
//        return getServerApi().downloadBook2(request)
//                .compose(RxSchedulersHelper.<BaseResult<List<OssInfoBean>>>io_main())
//                .compose(RxResultHelper.<List<OssInfoBean>>handleResult());
//    }
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