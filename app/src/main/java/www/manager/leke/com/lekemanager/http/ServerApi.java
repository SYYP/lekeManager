package www.manager.leke.com.lekemanager.http;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
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
import www.manager.leke.com.lekemanager.net.DownRequest;
import www.manager.leke.com.lekemanager.net.OssInfoBean;

public interface ServerApi {
    /**
     * 图书下载
     */
    @POST("bookStore")
    Observable<BaseResult<List<OssInfoBean>>> downloadBook2(@Body DownRequest req);

    /**
     *  【51005】【已完成】管理端取得图书OSS下载地址
     * @param bookId
     * @param deviceModel
     * @return
     */
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
    Observable<BaseResult<List<MainBookMessageBean>>> getQuestionBank(@Query("bookOptor") Integer bookOptor, @Query("coverDeviceModel") String coverDeviceModel);

    /**
     * 【62013】【已完成】更新点读状态
     */
    @POST("bookaudio/status/update")
    Observable<BaseResult<Object>> getSetReadbookStatus(@Body SumbitaudioBook sumbitaudioBook);

    /**
     * 【63013】【已完成】题库（练习册）编辑提交
     */

    @POST("questionbank/item/modify/submit")
    @FormUrlEncoded
    Observable<BaseResult<Object>> getQuestionbankSubmit(@Field("qBankBookId") int qBankBookId, @Field("entryLogInfo") String entryLogInfo);

    /**
     * 【63014】【已完成】题库（练习册）校对提交
     */
    @POST("questionbank/item/inspect/submit")
    Observable<BaseResult<Object>> getQuestionInspect(@Body SumbitQuestion sumbitQuestion);

    /**
     * 【63015】【已完成】题库（练习册）审核提交
     */
    @POST("questionbank/item/approve/submit")
    Observable<BaseResult<Object>> getQuestionApprove(@Body SumbitQuestion sumbitQuestion);

    /**
     * 【61006】【已完成】取得指定ID的图书信息
     *
     * @param bookId
     * @return
     */
    @GET("book/get")
    Observable<BaseResult<BookMessageDetail>> getBookMesaageDetail(@Query("bookId") int bookId);

    /**
     * 【51002】 【已完成】更新用户密码
     */
    @POST("user/password/update")
    @FormUrlEncoded
    Observable<BaseResult<Object>> getRevisePwd(@Field("oldPassword") String oldPassword, @Field("newPassword") String newPassword);

    /***
     *  【63007】【已完成】取得题库（练习册）章节题目数
     */
    @GET("questionbank/chapter/amount")
    Observable<BaseResult<HashMap<String, Integer>>> getQuestionAmoumt(@Query("qBankBookId") Integer qBankBookId, @Query("qStatusCode") String qStatusCode);

    /**
     *  【63010】【已完成】取得题库（练习册）题目列表
     */
       @GET("questionbank/item/list")
      Observable<BaseResult<List<QuestionListBean>>> getQuestionBankList(@Query("qBankBookId")int qBankBookId,@Query("chapterId") int chapterId,@Query("qStatusCode") String qStatusCode);
}
