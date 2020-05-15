package www.manager.leke.com.lekemanager.http;


import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import www.manager.leke.com.lekemanager.base.BaseResult;
import www.manager.leke.com.lekemanager.bean.BookOssBean;
import www.manager.leke.com.lekemanager.bean.LoginBean;
import www.manager.leke.com.lekemanager.bean.MainBookMessageBean;
import www.manager.leke.com.lekemanager.bean.SumbitBookStatus;
import www.manager.leke.com.lekemanager.net.DownRequest;
import www.manager.leke.com.lekemanager.net.OssInfoBean;

public interface ServerApi {
    /**
     * 图书下载
     */
    @POST("bookStore")
    Observable<BaseResult<List<OssInfoBean>>> downloadBook2(@Body DownRequest req);

    @GET("mobile/book/atchOSS")
    Observable<BaseResult<BookOssBean>> getBookOss(@Query("bookId") int bookId, @Query("deviceModel") String deviceModel);

    /**
     * 登录接口
     */
    @POST("login")
    @FormUrlEncoded
    Observable<BaseResult<LoginBean>> getLogin(@Field("login") String account, @Field("password") String password);

    /**
     * 取得当前用户信息
     */
    @GET("user")
    Observable<BaseResult<LoginBean>> getInfoMation();

    /**
     * 【61001】【对应管理pad端有更新】取得个人待处理图书列表
     */
    @GET("book/personal/pending/list")
    Observable<BaseResult<List<MainBookMessageBean>>> getMainBookMessage(@Query("bookStatus") String bookStatus, @Query("coverDeviceModel") String coverDeviceModel);

    /**
     * 【61012】【已完成】更新图书状态
     */
    @POST("book/status/update")
    Observable<BaseResult<Object>> getUpdateStatus(@Body SumbitBookStatus sumbitBookStatus);

    /**
     * zaozhi
     * 【62001】【对应管理pad端有更新】取得个人待处理点读列表
     */
    @GET("bookaudio/personal/pending/list")
    Observable<BaseResult<List<MainBookMessageBean>>> getBookAudio(@Query("audioStatus") String audioStatus, @Query("coverDeviceModel") String coverDeviceModel);


    /**
     * 【63001】【对应管理pad端有更新】取得个人待处理题库（练习册）列表
     */
    @GET("questionbank/personal/pending/list")
    Observable<BaseResult<List<MainBookMessageBean>>> getQuestionBank(@Query("bookOptor") int bookOptor,@Query("coverDeviceModel")String coverDeviceModel);
}
